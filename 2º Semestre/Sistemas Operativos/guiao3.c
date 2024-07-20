//guiao 3

#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>
#include <string.h>

//1
/*int main(int agrc, char**agrv){
    int return_exec;
    char* exec_agrs[] = {"/bin/ls","ls", NULL};

    printf("mensagem 1\n");

    return_exec = execl("/bin/ls","ls","-l", NULL);
    //return_exec = execlp("ls","ls","-l","NULL");
    //return_exec = execv("/bin/ls",exec_agrs);
    //return_exec = execvp("ls",exec_agrs);

    printf("mensagem 2\n");

    return 0;
} */

// 2
/*int main(int agrc, char**agrv){
    int return_exec;
    char* exec_agrs[] = {"/bin/ls","ls", NULL};
    int status;

    printf("mensagem 1\n");

    int res = fork();
    if(res == 0){
        return_exec = execl("/bin/ls","ls","-l", NULL);
        //return_exec = execlp("ls","ls","-l","NULL");
        //return_exec = execv("/bin/ls",exec_agrs);
        //return_exec = execvp("ls",exec_agrs);
        _exit(0);
    }
    else {
        int wait_pid = wait(&status);
        printf("O processo com o pid %d retornou %d\n",res,WEXITSTATUS(status));
    }

    printf("mensagem 2\n");

    return 0;
} */

//3
/*
int main(int agrc, char**agrv){
    int return_exec;
    int status;

    printf("mensagem 1\n");

    for(int i = 0; i< agrc-1; i++){
        int res = fork();
        if(res == 0){
            return_exec = execlp(agrv[i+1],agrv[i],NULL);
            _exit(0);
        }
    }

    for(int j = 0; j<agrc-1;j++){
        int ret = wait(&status);
        printf("O processo com o pid %d retornou %d\n",ret,j+1);
    }
    
    printf("mensagem 2\n");

    return 0;
} */


/*int main(int agrc, char** agrv){
    char comando1[] = "ls -l -a -h";
    char comando2[] = "sleep 10";
    char comando3[] = "ps";
    int ret
    
    printf("A executar myssystem para %s\n",comando1);
    ret = myssystem(comando1);
    printf("Return value: %d\n\n",ret);

    printf("A executar myssystem para %s\n",comando1);
    ret = myssystem(comando2);
    printf("Return value: %d\n\n",ret);

    printf("A executar myssystem para %s\n",comando1);
    ret = myssystem(comando3);
    printf("Return value: %d\n\n",ret);
} */

//4
int main(int agrc, char** agrv){
    
    char comando1[] = "ls -l -a -h";
    char comando2[] = "sleep 10";
    char comando3[] = "ps";
    int ret;

    printf("a executar myssystem para %s\n",comando1);
    ret = mysystem(comando1);
    printf("Return value: %d\n\n",ret);

    printf("a executar myssystem para %s\n",comando2);
    ret = mysystem(comando2);
    printf("Return value: %d\n\n",ret);

    printf("a executar myssystem para %s\n",comando3);
    ret = mysystem(comando3);
    printf("Return value: %d\n\n",ret);
}
/*
int mysystem(char* comand){
    int return_exec;
    char* exec_agrs[];
    char *string;
    char* cmd = strdup(comand);
    int status;
    int i = 0;

    while((string = strsep (&cmd," ")) != NULL){
        exec_agrs[i] = string;
        i++;
    }

    int res = fork();
    if(res == 0){
        return_exec = execv(exec_agrs[0],exec_agrs);
    }
    else {
        int wait_pid = wait(&status);
        printf("O processo com o pid %d retornou %d\n",res,WEXITSTATUS(status));
    }
    return 0;
} */

int mysystem(char* comand){
    int return_value;
    int return_exec;
    char* exec_agrs[10000];
    char *string;
    char* cmd = strdup(comand);
    int status;
    int i = 0;

    while((string = strsep (&cmd," ")) != NULL){
        exec_agrs[i] = string;
        i++;
    }
    exec_agrs[i] = NULL;

    pid_t pid = fork();
    if(pid == 0){
        return_exec = execvp(exec_agrs[0],exec_agrs);
        _exit(return_exec);
    }
    else {

        if(pid != -1){
            pid_t terminated_pid = waitpid(pid,&status,0);

            if(WIFEXITED(status)){
                return_value = WEXITSTATUS(status);
            } else {
                return_value = -1;
            }
        }
        else {
            return_value = -1;
        }
    }
    return return_value;
}


