#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <sys/time.h>
#include <string.h>
#include <fcntl.h>
#include <limits.h>
#include <sys/stat.h>

#define MAX_LINE_SIZE 100

struct program_info {   // Programas a executar
    int tipo;           // 1 pro execute, 2 pro status
    char program_name[MAX_LINE_SIZE];
    int execution_time_start;
    int execution_time_end;
    int exit_status;
    pid_t pid; 
    struct program_info* next;
    int terminado; // 0 sn, 1 se sim, supostamente sempre 0 aqui // SEMPRE 0, 0 por terminar
};

struct program_info_terminated {   // Programas executados
    int tipo;           // 1 pro execute, 2 pro status // em principio não vai servir para nada aqui
    char program_name[MAX_LINE_SIZE];
    int execution_time_start;
    int execution_time_end;
    int exit_status;
    pid_t pid; 
    struct program_info_terminated* next;
    int terminado; // 0 sn, 1 se sim, supostamente sempre 1 aqui    // SEMPRE 1, 1 terminado
};

struct program_info* head_info = NULL;
struct program_info_terminated* head_terminated = NULL;


void add_program_info(char* program_name, int execution_time_start, int execution_time_end, int exit_status, pid_t pid) {
    struct program_info* new_node = (struct program_info*) malloc(sizeof(struct program_info));
    strcpy(new_node->program_name, program_name);
    new_node->execution_time_start = execution_time_start;
    new_node->execution_time_end = execution_time_end;
    new_node->exit_status = exit_status;
    new_node->pid = pid;
    new_node->tipo = 1;
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

void remove_program_info(pid_t pid) {
    struct program_info *current = head_info;
    struct program_info *prev = NULL;

    while (current != NULL) {
        if (current->pid == pid) {
            if (prev == NULL) {
                head_info = current->next;
            } else {
                prev->next = current->next;
            }
            free(current);
            return;
        }
        prev = current;
        current = current->next;
    }
}

void print_program_info(int executionStatus) {     // STATUS    // Recebe o tempo que foi feito o pedido pra calcular o tempo
    struct program_info* current = head_info;
    while (current != NULL) {
        printf("-------PRINT---------\n");
        printf("Programa: %s\n", current->program_name);
        //printf("Tempo de inicio: %d ms\n", current->execution_time_start);
        //printf("Tempo de fim: %d ms\n", current->execution_time_end);
        printf("Tempo de execucao ate ao momento: %d ms\n", current->execution_time_start - executionStatus); 
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

void add_program_info_terminated(char* program_name, int execution_time_start, int execution_time_end, int exit_status, pid_t pid) {
    struct program_info_terminated* new_node = (struct program_info_terminated*) malloc(sizeof(struct program_info_terminated));
    strcpy(new_node->program_name, program_name);
    new_node->execution_time_start = execution_time_start;
    new_node->execution_time_end = execution_time_end;
    new_node->exit_status = exit_status;
    new_node->pid = pid;
    new_node->tipo = 1;
    new_node->terminado = 1;
    new_node->next = NULL;
    
    if (head_terminated == NULL) {
        head_terminated = new_node;
        return;
    }
    struct program_info_terminated* current = head_terminated;
    while (current->next != NULL) {
        current = current->next;
    }
    current->next = new_node;
}

void remove_program_info_terminated(pid_t pid) {
    struct program_info_terminated *current = head_terminated;
    struct program_info_terminated *prev = NULL;

    while (current != NULL) {
        if (current->pid == pid) {
            if (prev == NULL) {
                head_terminated = current->next;
            } else {
                prev->next = current->next;
            }
            free(current);
            return;
        }
        prev = current;
        current = current->next;
    }
}

void print_program_info_terminated() {          // So para testar
    struct program_info_terminated* current = head_terminated;
    while (current != NULL) {
        printf("-------PRINT---------\n");
        printf("Programa: %s\n", current->program_name);
        printf("Tempo de inicio: %d ms\n", current->execution_time_start);
        printf("Tempo de fim: %d ms\n", current->execution_time_end);
        printf("Tempo de execucao ate ao momento: %d ms\n", current->execution_time_start - current->execution_time_end); 
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

void print_program_info_file(char* filename) {
    int fd = open(filename, O_RDONLY);
    if (fd == -1) {
        perror("Erro ao abrir arquivo");
        exit(EXIT_FAILURE);
    }
    struct program_info info;
    if (read(fd, &info, sizeof(struct program_info)) == -1) {
        perror("Erro ao ler arquivo");
        exit(EXIT_FAILURE);
    }
    printf("Programa: %s\n", info.program_name);
    printf("Tempo de execucao (ms): %ld\n", info.execution_time_end - info.execution_time_start);
    printf("Status de saida: %d\n", info.exit_status);
    close(fd);
}
int stats_time_func(pid_t terminated_pids[], int n_terminated_pids) {
    int tempo_total = 0;
    for(int i = 0; i < n_terminated_pids; i++){
        char filename[20];
        sprintf(filename, "%d.txt", (int)terminated_pids[i]);
        int fd = open(filename, O_RDONLY);
       
        if(fd != -1) {
            struct program_info info;
            int bytes_read = read(fd, &info, sizeof(struct program_info));
            if (info.pid == terminated_pids[i]) {
                int time = info.execution_time_end - info.execution_time_start;
                tempo_total += time;
            }
            close(fd);
        }
    }
    return tempo_total;
}
int stats_command_func(char* nome, pid_t terminated_pids[], int n_terminated_pids){
    int quantas = 0;
    for(int i = 0; i < n_terminated_pids; i++){
        char filename[20];
        sprintf(filename, "%d.txt", (int)terminated_pids[i]);
        int fd = open(filename, O_RDONLY);
        
        if(fd != -1) {
            struct program_info info;
            int bytes_read = read(fd, &info, sizeof(struct program_info));
            close(fd);

            if(strcmp(info.program_name, nome) == 0) {
                quantas++;
            }
        }
    }
    return quantas;
}

char** stats_uniq_func(pid_t terminated_pids[], int n_terminated_pids) {
    char** ret = (char**)malloc(sizeof(char*) * n_terminated_pids);
    int ret_size = 0;

    for (int i = 0; i < n_terminated_pids; i++) {
        char filename[20];
        sprintf(filename, "%d.txt", (int)terminated_pids[i]);

        int fd = open(filename, O_RDONLY);
        if (fd != -1) {
            struct program_info info;
            int bytes_read = read(fd, &info, sizeof(struct program_info));

            int found = 0;
            for (int j = 0; j < ret_size; j++) {
                if (strcmp(info.program_name, ret[j]) == 0) {
                    found = 1;
                    break;
                }
            }

            if (!found) {
                ret[ret_size] = (char*)malloc(sizeof(char) * (strlen(info.program_name) + 1));
                strcpy(ret[ret_size], info.program_name);
                ret_size++;
            }

            close(fd);
        }
    }

    return ret;
}


int main(int argc, char *argv[]) {
    
    // Criar fifo
    int res = mkfifo("fifo",0666);
    if(res == -1){
        perror("mkfifo");
    }

    struct timeval start_time;      // Comentar
    struct timeval end_time;        // Comentar
    gettimeofday(&start_time, NULL);    // Coloca em start_time o tempo atual
    
    // Abrir o fifo sempre que um cliente o fecha
    while (1) {
        int fd_fifo = open("fifo", O_RDWR);

        if (fd_fifo < 0) {
            perror("open");
        } else {
            printf("Fifo aberto para escrita e leitura...\n");
        }

        struct program_info info; 
        int bytes_read = read(fd_fifo, &info, sizeof(struct program_info));
        if (bytes_read == -1) {
            perror("read");
        } else if (info.tipo == 2) {            // Status
            printf("Comando \"Status\" recebido\n");
            printf("Opened FIFO for writing...\n");

            if(head_info == NULL){
                // No caso enviar para o status enviar isso
                //printf("Nenhum programa a executar no momento!\n");

                int res = fork();
                if (res == -1) {
                    perror("fork");
                    return -1;
                } if(res == 0){
                    char* status = "status";
                    enviar_info_programa(fd_fifo,status, 0, 0, 0,0,-1, 0);
                } else {
                  waitpid(res,NULL,0);  
                }
            
            } else {
                int res = fork();
                if (res == -1) {
                    perror("fork");
                    return -1;
                } if(res == 0){
                    gettimeofday(&end_time, NULL);
                    int end_time_seg = end_time.tv_sec * 1000 + end_time.tv_usec / 1000;
                    // print_program_info(info.execution_time_end);

                    // Passar informacao para o cliente a imprimir
                    // Escrever a mensagem no fifo
                    struct program_info* prog = head_info;
                    while (prog != NULL) {
                        enviar_info_programa(fd_fifo, prog->program_name, prog->execution_time_start, end_time_seg, prog->exit_status, prog->pid, prog->tipo, prog->terminado);
                        prog = prog->next;
                    }
                    struct program_info end_info;
                    enviar_info_programa(fd_fifo, "", 0, 0, 0, 0, -2, 0);
                } else {
                  waitpid(res,NULL,0);  
                }    
            }

        } else if (info.tipo == 4) {           //  4 stats_time_func, 5 stats_command_func, 6 stats_uniq_func
            printf("Comando \"Stats_time\" recebido\n");

            // Receber pids  
            // stats_time_func(pid_t terminated_pids[], int n_terminated_pids)
            // Testar se funciona, e enviar pro cliente
            pid_t pids[info.exit_status];
            pids[0] = info.pid;
            
            int val = info.exit_status;
            if (info.terminado != -1){
                for (int i = 1; i < val; i++){
                    struct program_info_terminated struct_aux;
                    int bytes_read = read(fd_fifo, &struct_aux, sizeof(struct program_info_terminated));
                    pids[i] = struct_aux.pid;
                    if(struct_aux.terminado == -1){
                       // printf("RECEBI O ULTIMO MANO\n");
                        break;
                    }
                }
            }
            //printf("VOU FAZER CONTAS\n");
            int ret = stats_time_func(pids, val);
            //printf("Resultado: %d\n",ret);

            
            int res = fork();
            if (res == -1) {
                perror("fork");
                return -1;
            } else if(res == 0){
                char* stats1 = "stats1";
                enviar_info_programa(fd_fifo,stats1,0, 0,ret,0,4,2);
            } else {
                waitpid(res,NULL,0);
            } 

        } else if (info.tipo == 5) {
            printf("Comando \"Stats_command\" recebido\n");
            
            // Receber pids  
            // stats_command_func(char* nome, pid_t terminated_pids[], int n_terminated_pids)
            // Testar se funciona, e enviar pro cliente
            //stats_command_func(char* nome, pid_t terminated_pids[], int n_terminated_pids)
            pid_t pids[info.exit_status];
            pids[0] = info.pid;
            char* nome_cmd = info.program_name;
            
            int val = info.exit_status;
            if (info.terminado != -1){
                for (int i = 1; i < val; i++){
                    struct program_info_terminated struct_aux;
                    int bytes_read = read(fd_fifo, &struct_aux, sizeof(struct program_info_terminated));
                    pids[i] = struct_aux.pid;
                    if(struct_aux.terminado == -1){
                       // printf("RECEBI O ULTIMO MANO\n");
                        break;
                    }
                }
            }
            //printf("VOU FAZER CONTAS\n");
            int ret = stats_command_func(nome_cmd,pids, val);
            printf("Resultado: %d\n",ret);

            
            int res = fork();
            if (res == -1) {
                perror("fork");
                return -1;
            } else if(res == 0){
                enviar_info_programa(fd_fifo,nome_cmd,0, 0,ret,0,5,2);
            } else {
                waitpid(res,NULL,0);
            } 

        } else if (info.tipo == 6) {
            printf("Comando \"Stats_uniq\" recebido\n");


 
            // Receber pids  
            // program_info_terminated* stats_uniq_func(pid_t* pids, int num_pids, char* file_name)
            // Testar se funciona, e enviar pro cliente

            pid_t pids[info.exit_status];
            pids[0] = info.pid;
            
            int val = info.exit_status;
            if (info.terminado != -1){
                for (int i = 1; i < val; i++){
                    struct program_info_terminated struct_aux;
                    int bytes_read = read(fd_fifo, &struct_aux, sizeof(struct program_info_terminated));
                    pids[i] = struct_aux.pid;
                    if(struct_aux.terminado == -1){
                       // printf("RECEBI O ULTIMO MANO\n");
                        break;
                    }
                }
            }
            //printf("VOU FAZER CONTAS\n");
            char** ret = stats_uniq_func(pids,val);
            char* stats3 = "stats3";
          // printf("Resultado: %d\n",ret);

            
            int res = fork();
            if (res == -1) {
                perror("fork");
                return -1;
            } else if(res == 0){
                int i = 0;
                while(ret[i] != NULL){
                    enviar_info_programa(fd_fifo,ret[i],0, 0,0,0,6,2);
                    i++;
                }
                enviar_info_programa(fd_fifo,stats3,0, 0,0,0,6,-2);
            } else {
                waitpid(res,NULL,0);
            } 
            
        }
        else {
            if(info.terminado == 0){
                printf("Programa Recebido: %s\n", info.program_name);
                printf("Pid do processo: %d\n", info.pid);
                //printf("Tempo de start: %d ms\n", info.execution_time_start);
                gettimeofday(&start_time, NULL);
                int start_time_sec = start_time.tv_sec * 1000 + start_time.tv_usec / 1000;
                add_program_info(info.program_name,start_time_sec,info.execution_time_end, info.exit_status, info.pid);
            } 
            else if(info.terminado == 1){
                printf("O Programa \"%s\" foi executado com sucesso!\n",info.program_name);
                printf("Pid usado: %d\n",info.pid);
                // Remover da lista dos programas em execucao
                remove_program_info(info.pid);

                // Adicionar na lista dos programas terminados
                add_program_info_terminated(info.program_name,info.execution_time_start,info.execution_time_end, info.exit_status, info.pid);
                // Escrever em ficheiro
                int fd;
                char filename[20]; // Defina o tamanho máximo do nome do arquivo como necessário
                sprintf(filename, "%d.txt", info.pid); // Cria o nome do arquivo usando o PID
                fd = open(filename, O_RDWR | O_CREAT | O_TRUNC, 0666); // Abre o arquivo para escrita e leitura, com permissões de acesso 0644
                if (fd == -1) {
                    perror("Erro ao abrir arquivo");
                    return -1;
                }
                if (write(fd, &info, sizeof(struct program_info)) == -1) {
                    perror("Erro ao escrever no arquivo");
                    return -1;
                } 
                //print_program_info_file(filename);    // Só para verificar que esta a escrever corretamente no file
                close(fd);
            } 
        }
        close(fd_fifo);
    } 
    unlink("fifo");
    return 0;
}



