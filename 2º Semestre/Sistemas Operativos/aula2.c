// Aula prática 2

#include <stdio.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <unistd.h>


//pid_t fork(void);
/*
int main(int argc, char* argv[]){

    int i = 10;

    pid_t pid = fork();
    pid_t pid_pai = getppid();
    pid_t pid_filho = getpid();
    pid_t status;

    //printf("pai: %d \nfilho: %d\npid: %d\n",pid_pai, pid_filho,pid);

    printf("pid %d, pid_pai: %d\n",pid,pid_pai);

    if(pid == 0){
        i++;
        printf("i: %d\n",i);
        _exit(0);
    }
    else {
        i--;
        printf("i: %d\n",i);
        printf("pid_filho: %d\n",pid_filho);
        wait(&status);
    }

    printf("\ni: %d\n",i);
} */


/* // 3
int main(int argc, char* argv[]){
    
    int status = 0; 
    int procs = 10;

    for(int i = 0; i < procs; i++){
        int res = fork();
        if(res == 0){
            printf("Filho #%d (%d) -- Pai %d\n", i+1, getpid(), getppid());
            _exit(i+1);
        } else {
            int waitpid = wait(&status);

            if(WIFEXITED(status)){
                printf("Pai (%d): processo %d terminou c/ exit code %d\n", getpid(), waitpid, WEXITSTATUS(status));
            }
        }
        sleep(1);
    }
    return 0;
} */

    // 4
/*
int main(int agrc, char* agrv[]){

    int status = 0;
    int procs = 10;

    // Cria 10 processos filho
    for(int i = 0; i < procs; i++){
        int res = fork();
        
        if(res == 0){
            printf("Filho #%d (%d) -- Pai %d\n", i+1, getpid(), getppid());
            _exit(i+1);
        }
    }
    //sleep(1);
    for(int j = 0; j < procs; j++){
        int waitpid = wait(&status);
        if(WIFEXITED(status)){
                printf("Pai (%d): processo %d terminou c/ exit code %d\n", getpid(), waitpid, WEXITSTATUS(status));
        }
    }
} */

    // 5
   /*
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
        if(res == 0){
            for(int j = 0; j < cols; j++){
                //printf("linha: %d, pid: %d",i,getpid());
                if(matrix[i][j] == needle){
                    _exit(i);
                }
            }
            _exit(-1);
        }
    }
    for(int u = 0; u < rows; u++){
        int waitpid = wait(&status);
            if(WEXITSTATUS(status) != 255){
                printf("O valor está na linha %d, com o processo: %d\n",WEXITSTATUS(status),waitpid);
            }
            else {
                printf("Não encontrou\n");
            }
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
}  */

    // 6
    
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
            for(int j = 0; j < cols; j++){
                //printf("linha: %d, pid: %d",i,getpid());
                if(matrix[i][j] == needle){
                    _exit(i);
                }
            }
            _exit(-1);
        }
    }
    for(int u = 0; u < rows; u++){
        int wait_pid = waitpid(array_pid[u], &status, 0);
            if(WEXITSTATUS(status) != 255){
                printf("O valor está na linha %d, com o processo: %d\n",WEXITSTATUS(status),wait_pid);
            }
            else {
                printf("Não encontrou\n");
            }
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
