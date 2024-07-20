#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/time.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <fcntl.h>
#include <limits.h>
#include <sys/stat.h>
#include <string.h>

#define MAX_LINE_SIZE 100

struct program_info {
    int tipo;           // 1 pro execute, 2 pro status, -1 status inverso, 3 pipeline, 4 stats_time_func, 5 stats_command_func, 6 stats_uniq_func
    char program_name[MAX_LINE_SIZE];
    int execution_time_start;
    int execution_time_end;
    int exit_status;
    pid_t pid; 
    struct program_info* next;
    int terminado;      // 0 nao, 1 sim
};

struct program_info* head_info = NULL;

void add_program_info(char* program_name, int execution_time_start, int execution_time_end, int exit_status, pid_t pid) {
    struct program_info* new_node = (struct program_info*) malloc(sizeof(struct program_info));
    strcpy(new_node->program_name, program_name);
    new_node->execution_time_start = execution_time_start;
    new_node->execution_time_end = execution_time_end;
    new_node->exit_status = exit_status;
    new_node->pid = pid;
    new_node->tipo = 2;
    new_node->terminado = 0;
    new_node->next = NULL;
    
    if (head_info == NULL) {
        head_info = new_node;
        return;
    }
    struct program_info* current = head_info;
    while (current->next != NULL) {
        current = current->next;
    }
    current->next = new_node;
}

void print_program_info() {     // STATUS    // Recebe o tempo que foi feito o pedido pra calcular o tempo
    struct program_info* current = head_info;
    while (current != NULL) {
        printf("-------PRINT---------\n");
        printf("Programa: %s\n", current->program_name);
        //printf("Tempo de inicio: %d ms\n", current->execution_time_start);
        //printf("Tempo de fim: %d ms\n", current->execution_time_end);
        printf("Tempo de execucao ate ao momento: %d ms\n", current->execution_time_end-current->execution_time_start); 
        printf("Status de saida: %d\n", current->exit_status);
        printf("PID do processo filho: %d\n", current->pid);
        printf("Tipo da struct: %d\n", current->tipo);
        if(current->terminado == 0){
            printf("Ainda nao terminou!\n");
        } else {
            printf("Já terminou!\n");
        }
        printf("--------------------\n");
        current = current->next;
    }
}

void limpar_prog_info_list() {
    struct program_info* current = head_info;
    while (current != NULL) {
        struct program_info* next = current->next;
        free(current);
        current = next;
    }
    head_info = NULL;
}

// Funcao para enviar struct para o fifo
void enviar_info_programa(int fd_fifo,char* nome_programa, int tempo_inicio, int tempo_fim, int status,pid_t pid,int tipo, int terminado) {
    struct program_info str_aux;
    strncpy(str_aux.program_name, nome_programa, MAX_LINE_SIZE-1);
    str_aux.execution_time_start = tempo_inicio;
    str_aux.execution_time_end = tempo_fim;
    str_aux.exit_status = status;
    str_aux.pid = pid;
    str_aux.tipo = tipo;
    str_aux.next = NULL;
    str_aux.terminado = terminado;
    
    if (write(fd_fifo, &str_aux, sizeof(struct program_info)) == -1) {
        perror("Erro ao enviar estrutura para o servidor");
    }
}

int exec_command(char*arg){     // Função dada nas aulas
    char* exec_args[10]; char* string; int exec_ret = 0; int i = 0;
    char *command = strdup(arg);string = strtok(command," ");

    while(string != NULL){
        exec_args[i] = string; string = strtok(NULL, " "); i++;
    }
    exec_args[i] = NULL;
    exec_ret = execvp(exec_args[0],exec_args);
    return exec_ret;
}

void parse_command(char *command, char *parsed_cmds[]) {
    char *cmd = strdup(command);
    cmd = strtok(cmd, "|");
    int i = 0;

    while (cmd != NULL) {
        parsed_cmds[i++] = cmd;
        cmd = strtok(NULL, "|");
    }
}

int count_pipes(char *cmd) {
    int count = 0;
    for (int i = 0; cmd[i] != '\0'; i++) {
        if (cmd[i] == '|') {
            count++;
        }
    }
    return count+1;
}





int main(int argc, char *argv[]) {
    if (argc < 2) {     // nr de agrumentos minimo == 2
        printf("Argumentos insuficientes\n");
        return -1;
    }

    int fd_fifo = open("fifo", O_RDWR);     // Abre o fifo para leitura e escrita

    if(fd_fifo < 0){
        perror("open");
    } else {
        printf("FIFO aberto para escrita e leitura...\n");
    }

    if (strcmp(argv[1], "execute") == 0){
        if (argc < 4) {     // nr de agrumentos minimo == 4, nome execute -u/-p comando
            printf("Argumentos insuficientes\n");
            return -1;
        }
        if (strcmp(argv[2], "-u") == 0){    // execute de comando simples
            printf("A executar o comando: %s\n", argv[3]);

            // Pipe para enviar o tempo de inicio do programa do filho para o pai
            int start_pipe[2];
            if (pipe(start_pipe) == -1) {
                perror("pipe");
                return -1;
            }

            struct timeval start_time;      // Comentar
            struct timeval end_time;        // Comentar
            gettimeofday(&start_time, NULL);    // Coloca em start_time o tempo atual
            
            // Criar filho para executar o programa
            pid_t res = fork();
            if (res == -1) {    // Erro no fork()
                perror("fork");
                close(start_pipe[0]);
                close(start_pipe[1]);
                return -1;
            } else if (res == 0) {      // Processo filho
                close(start_pipe[0]);   // Fechar pipe de leitura

                pid_t pid_filho = getpid(); // guardar o pid do proprio processo

                // Forma de garantir que sao recebidos todos os parametros do comando
                char *args[argc+1];
                args[0] = argv[3];
                for (int i = 4; i < argc; i++) {       // Comeca em 4 porque so apartir do 4 e que sao os parametros do comando
                    args[i-3] = argv[i];               // i-3 para ir do 0 ate ao agrc
                }
                args[argc-3] = NULL;                   // agrs[agrc] = NULL, ultimo NULL para usar no "exec"

                // Tempo de inicio de execucao em segundos
                int start_ms_child = start_time.tv_sec * 1000 + start_time.tv_usec / 1000;

                // Envia o tempo de inicio pelo pipe
                if (write(start_pipe[1], &start_ms_child, sizeof(start_ms_child)) == -1) {
                    perror("write");
                    close(start_pipe[1]);
                    _exit(1);
                }

                // Enviar o programa com terminado == 0, logo antes do inicio da execucao do mesmo. Tipo da struct == 1
                enviar_info_programa(fd_fifo,argv[3], 1, start_ms_child, 0,pid_filho,1, 0); 

                // Executar
                execvp(args[0], args);
                perror("execvp");
                _exit(1);

            } else {        // Processo Pai
                close(start_pipe[1]);   // Fecha a extremidade de escrita
                
                int start_ms_parent;    

                // Le do pipe anonimo o tempo de inicio do programa
                if (read(start_pipe[0], &start_ms_parent, sizeof(start_ms_parent)) == -1) { 
                    perror("read");
                    close(start_pipe[0]);
                    return -1;
                }

                close(start_pipe[1]);   // Fecha a extremidade de leitura

                int status;
                waitpid(res, &status, 0);   // Espera que o processo filho termine
                gettimeofday(&end_time, NULL);      // Coloca em end_time o valor do tempo atual (fim de execucao)

                int end_ms = end_time.tv_sec * 1000 + end_time.tv_usec / 1000;      // Tempo de termino em segundos

                printf("Terminado em %d ms\n", end_ms-start_ms_parent);    // Tempo final - Tempo inicial em segundos
                                                                                                                                               

                enviar_info_programa(fd_fifo,argv[3], start_ms_parent, end_ms,status,res,1,1); // Envia estrutura com terminado == 1

                return 0;
            }

        } else if(strcmp(argv[2], "-p") == 0) {     // execute de uma pipeline

                printf("A executar o comando: %s\n", argv[3]);

                // Tratamento da string
                int nr_cmd = count_pipes(argv[3]);  // Conta o nr de comandos que ha
                char *cmd[nr_cmd];              
                parse_command(argv[3],cmd);         // cmd vai ficar com os comandos separados, exp: {"ls","wc -l"}
        

                struct timeval start_time;      // Mesma ideia do tempo
                struct timeval end_time;        // Mesma ideia do tempo
                gettimeofday(&start_time, NULL);

                // Neste caso para simplificar o processo pai vai ficar logo com o tempo de inicio antes do inicio da execucao da pipeline

                int start_ms = start_time.tv_sec * 1000 + start_time.tv_usec / 1000;    // Tempo de inicio em segundos

                ////////////////////////// Pipeline rever!!
                // Tratamento da pipeline
                int pipes[nr_cmd - 1][2];
                int status[nr_cmd];

                int child;

                // enviar programa por iniciar
                pid_t pid_pai = getpid();

                // Envia ao servidor o programa que esta prestes a executar, com terminado = 0 e tipo = 3
                enviar_info_programa(fd_fifo,argv[3], start_ms, 0, 0,pid_pai,3, 0);


                for (int c = 0; c < nr_cmd; c++){

                    if(c != nr_cmd - 1){ // Nao e o ultimo comando
                        pipe(pipes[c]);
                    }

                    child = fork();
                    if(child == 0){
                        if(c == 0){ // Primeiro comando
                            dup2(pipes[c][1], 1);
                            close(pipes[c][0]);
                            close(pipes[c][1]);
                        } else if(c == nr_cmd - 1){ // Ultimo comando
                            dup2(pipes[c-1][0], 0);
                            close(pipes[c-1][0]);
                            close(pipes[c-1][1]);
                        } else { // Comandos intermediarios
                            dup2(pipes[c-1][0], 0);
                            close(pipes[c-1][0]);
                            close(pipes[c-1][1]);

                            dup2(pipes[c][1], 1);
                            close(pipes[c][0]);
                            close(pipes[c][1]);
                        }

                        exec_command(cmd[c]);
                    } else {
                        if(c != 0){ // Nao e o primeiro comando
                            close(pipes[c-1][0]);
                            close(pipes[c-1][1]);
                        }
                    }
                }

                for (int i = 0; i < nr_cmd; i++) {
                    wait(&status[i]);
                }

            
                gettimeofday(&end_time, NULL);
                int end_ms2 = end_time.tv_sec * 1000 + end_time.tv_usec / 1000;     // Tempo de fim em seg


                printf("Terminado em %d ms\n", end_ms2-start_ms);   // Tempo de execucao = tempo fim - tempo inicio
                // envia novamente o programa como finalizado
                enviar_info_programa(fd_fifo,argv[3], start_ms, end_ms2, status[nr_cmd-1],pid_pai,3, 1);
           
        } else {       // Outro comando no execute que nao seja "-u" ou "-p" e desconhecido
           printf("Execute: Comando Desconhecido"); 
           return -1;
        }
    
    } else if (strcmp(argv[1], "status") == 0){     // Comando "status"

        char* status = "status";
        enviar_info_programa(fd_fifo,status, 0,0,0,0,2,0); // Envia apenas um pedido de status, para o servidor retornar a resposta

        struct program_info info; 
        int bytes_read = read(fd_fifo, &info, sizeof(struct program_info));
        if (bytes_read == -1) {
            perror("read");
        } else if(info.tipo == -1){
            printf("Nao ha programas a executar de momento!\n");    // Se o servidor retornar o tipo -1 e porque nao ha programas a executar no momento
        } else {
            limpar_prog_info_list();    // Limpa a lista ligada
            add_program_info(info.program_name,info.execution_time_start,info.execution_time_end, info.exit_status, info.pid);  // Adiciona a primeira struct
            while(bytes_read = read(fd_fifo, &info, sizeof(struct program_info)) > 0){      // Se tiver mais que uma struct vai adicionando
                if (info.tipo == -2) {      // Quando recebe uma struct com o tipo = -2, e apenas uma indicacao que ja terminou e da break para nao bloquear
                    break; // marcacao de fim de lista
                } else {
                    add_program_info(info.program_name,info.execution_time_start,info.execution_time_end, info.exit_status, info.pid); // vai adicionando
                }
            }
            if (bytes_read == -1) {
                perror("read");
            }
            print_program_info();   // Imprime a lista ligada que recebeu
        }     
    }
    else if (strcmp(argv[1], "stats-time") == 0){
        printf("Recebido stats-time\n");

        char* stats = "stats-time";

        for (int i = 2; i < argc; i++) {
            pid_t pid = atoi(argv[i]);      // atoi, converte de string para int
            if(i == argc-1){
                enviar_info_programa(fd_fifo, stats, 0, 0, argc, pid, 4, -1);
                //printf("aqui vai o ultimo cenas\n");
                //printf("VOU ENVIAR ISTO COMO UTLIMO %d\n",pid);
            }
            else {
                enviar_info_programa(fd_fifo, stats, 0, 0, argc, pid, 4, 0); // no lugar do status vai o nr de argumentos
                //printf("VOU ENVIAR ISTO %d\n",pid);
            }
        }

        struct program_info info;
        int bytes_read = read(fd_fifo, &info, sizeof(struct program_info));
        if (bytes_read == -1) {
            perror("read");
        } else {
            printf("Stats Time: %d\n", info.exit_status);
        } 
    }

    
    else if (strcmp(argv[1], "stats-command") == 0){
        if(argc < 3){
            printf("Nr de Parametros Insuficiente!\n");
            return 0;
        }
        printf("Recebido stats-command\n");

        char* stats2 = argv[2];

        for (int i = 3; i < argc; i++) {
            pid_t pid = atoi(argv[i]);      // atoi, converte de string para int
            if(i == argc-1){
                enviar_info_programa(fd_fifo, stats2, 0, 0, argc, pid, 5, -1);
            } else {
                enviar_info_programa(fd_fifo, stats2, 0, 0, argc, pid, 5, 0); // no lugar do status vai o nr de argumentos
            }
        }

        // receber estrutura e imprimir struct_info.exit_status
        struct program_info info;
        int bytes_read = read(fd_fifo, &info, sizeof(struct program_info));
        if (bytes_read == -1) {
            perror("read");
        } else {
            printf("Stats Command: %d\n", info.exit_status);
        }
    }
    else if (strcmp(argv[1], "stats-uniq") == 0){
        printf("Recebido stats-uniq\n");

        char* stats3 = "stats-uniq";

        for (int i = 2; i < argc; i++) {
            pid_t pid = atoi(argv[i]);      // atoi, converte de string para int
            if(i == argc-1){
                enviar_info_programa(fd_fifo, stats3, 0, 0, argc, pid, 6, -1);
            }
            else {
                enviar_info_programa(fd_fifo, stats3, 0, 0, argc, pid, 6, 0); // no lugar do status vai o nr de argumentos
            }
        }

        // receber estrutura e imprimir struct_info.exit_status
        struct program_info info;
        int bytes_read;
        while(bytes_read = read(fd_fifo, &info, sizeof(struct program_info)) > 0){
            if(info.terminado == -2){
                break;
            }
            else {
                printf("%s\n",info.program_name);
            }
        }
    }

    else {      // Outro comando diferente dos defenidos e dado como desconhecido
        printf("Comando Desconhecido");
        return -1;
    }

    close(fd_fifo);     // Fechar o fifo
    return 0;
}



