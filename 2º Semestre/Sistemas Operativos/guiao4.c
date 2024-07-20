// SO - guiao4

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

#define MAX 100000

//1
/*
void pai_to_filho(){
    int value = 0;
    int fds[2];
    int return_pipe = pipe(fds);
    perror("Erro pipe! ");

    printf("PIPE: [0] = %d ; [1] = %d\n",fds[0],fds[1]);

    pid_t res = fork();

    if(res == 0){   // filho - consumidor
        printf("PIPE (filho): [0] = %d ; [1] = %d\n",fds[0],fds[1]);
        close(fds[1]); // fechar a etremidade de escrita
        printf("Filho: value é %d antes de ler do pipe...\n",value);
        int bytes_read = read(fds[0], &value, sizeof(int));

        printf("Filho: value é %d depois de ler do pipe...\n",value);
        close(fds[0]);
        _exit(0);
    }
    else {      // pai - produtor
        //printf("PIPE (pai): [0] = %d ; [1] = %d\n",fds[0],fds[1]);
        close(fds[0]); // fechar a etremidade de leitura
        int value = 20;
        int bytes_writen = write(fds[1],&value, sizeof(int));
        
        printf("PAI: escreveu %d bytes para o pipe...\n", bytes_writen);
        close(fds[1]);

        pid_t terminated_pid = wait(NULL);
        printf("PAI: o filho %d terminou a sua execução...\n",terminated_pid);
    }
}

void filho_to_pai(){
    int value = 0;
    int fds[2];
    int return_pipe = pipe(fds);
    perror("Erro pipe! ");

    printf("PIPE: [0] = %d ; [1] = %d\n",fds[0],fds[1]);

    pid_t res = fork();

    if(res == 0){   // filho - produtor
        close(fds[0]); // fechar a etremidade de leitura
        int value = 20;
        int bytes_writen = write(fds[1],&value, sizeof(int));
        
        printf("FILHO: escreveu %d bytes para o pipe...\n", bytes_writen);
        close(fds[1]);
        _exit(0);
    }
    else {      // pai - recetor
        printf("PIPE PAI: [0] = %d ; [1] = %d\n",fds[0],fds[1]);
        close(fds[1]); // fechar a etremidade de escrita
        printf("PAI: value é %d antes de ler do pipe...\n",value);
        int bytes_read = read(fds[0], &value, sizeof(int));

        printf("PAI: value é %d depois de ler do pipe...\n",value);
        close(fds[0]);
        pid_t terminated_pid = wait(NULL);
        printf("PAI: o filho %d terminou a sua execução...\n",terminated_pid);
    }
}

int main(int agrc, char** agrv){
    //exc1 
    //pai_to_filho();

    //exc3
    //filho_to_pai();

} */

struct aux{
    int pos;
    int app;
};


int main(int agrc, char * agrv[]){
    if(agrc < 2){
        printf("Usage: program <needle>\n");
        exit(-1);
    }
    
    pid_t pid;
    int needle = atoi(agrv[1]);
    int rows = 5;
    int cols = 20;
    int rand_max = 100;
    int status;
    int **matrix;
    int array_pid[rows];
    int count = 0;
    struct aux struct_aux;
    int ret[rows];


    int fildes[2];

    int return_pipe = pipe(fildes[2]);

    // Allocate and populate matrix with random numbers
    printf("Generating numbers from 0 to %d...\n", rand_max);
    matrix = (int **)malloc(sizeof(int*)*rows);
    for(int i = 0; i < rows; i++){
        matrix[i] = (int*)malloc(sizeof(int)*cols);
        for(int j = 0; j < cols; j++){
            matrix[i][j] = rand()%rand_max;
        }
    }

    for(int i = 0; i < rows; i++){
        int res = fork();
        array_pid[i] = res;
        if(res == 0){
            close(fildes[0]);
            for(int j = 0; j < cols; j++){
                if(matrix[i][j] == needle){
                    count++;
                }
            }
            struct_aux.pos = i;
            struct_aux.app = count;
            write(fildes[1], &struct_aux, sizeof(struct aux));

            close(fildes[1]);
            _exit(0);
        }
    }

    close(fildes[1]);
    
    while(read(fildes[0], &struct_aux, sizeof(struct aux)) > 0){
        ret[struct_aux.pos] = struct_aux.app;
    }
    
    close(fildes[0]);

    for(int i = 0; i<rows; i++){
        wait(&status);
    }

    for(int e = 0; e < rows; e++){
        printf("pos: %d\nocorrencias: %d\n",e,ret[e]);
    }

    printf("\n");
    for(int k = 0; k < rows; k++){
        for(int l = 0; l < cols; l++){
            printf(" %d ",matrix[k][l]);
        }
        printf("\n");
    }
    printf("\n");
    
    return 0;
} 