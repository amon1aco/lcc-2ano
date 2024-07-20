#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <unistd.h>  // chamadas ao sistema: defs e decls essenciais 
#include <fcntl.h>   // O_RDONLY, O_WRONLY, O_CREAT, O_* 
#include <string.h>

#define MAX_BUFFER 10
#define SIZE 1000
#define LINE_SIZE 8

typedef struct Person{
    char nome[200];
    int age;
}Pessoa;


//1
int mycp (){
    int fd_in = open("tmp-file", O_RDONLY);
    printf("resultado do open; %d\n", fd_in);

    int fd_out = open("tmp-file-copy", O_WRONLY | O_CREAT, 0644); 
    printf("resultado do open (fdout): %d\n", fd_out);
    
    char* buffer = malloc(sizeof(char)*512);
    int read_bytes = 0;
    int write_bytes = 0;
    
    while((read_bytes = read(fd_in, buffer, 512)) > 0){
            write_bytes += write (fd_out, buffer, read_bytes);
    }
    printf("resultado do write (%d)\n", write_bytes);

    close(fd_in);
    close(fd_out);
    return 0;
}

//2
int mycat(){
    char* buffer = malloc(sizeof(char)*512);
    int read_bytes = 0;

    while ((read_bytes = read(0, buffer, 512)) > 0) {
        write(1, buffer, read_bytes);
    }
    
    return 0;
}

//3
ssize_t myreadln(int fd, char *line, size_t size){ // fd = 0, lê apartir do stdin
    int pos = 0;
    int bytes_read = 0;

    while(pos < size && read(fd, line + pos, 1) > 0){
        bytes_read++;
        if(line[pos] == '\n') break;
        pos++;
    }

    return bytes_read;
}

//4
int mynl(int argc, char* argv[]) {
	int src;
	char buffer[SIZE], line_label[LINE_SIZE];   // Para não dar erro de max 10 basicamente o size agr tem 1000..
	ssize_t n_read;
	
	src = open(argv[1], O_RDONLY);
	
	for (int i = 1; n_read = myreadln(src, buffer, SIZE);) { // Não percebo como acaba o ciclo
		if (n_read == 1)
			sprintf(line_label, "%*c\t", LINE_SIZE-2, ' ');
		else
			sprintf(line_label, "%*d\t", LINE_SIZE-2, i++);
		
		write(STDIN_FILENO, line_label, 8);
		write(STDIN_FILENO, buffer, n_read);
	}

	return 0;
}


int main(int argc, char* argv[]){
   //mycp();
   //mycat();
   //char buffer[MAX_BUFFER];
   //myreadln(0,buffer,MAX_BUFFER);
   //mynl(argc, argv);

   char id[20]="";
    if(strcmp(argv[1],"-i") == 0){
        int res = new_person(argv[2],atoi(argv[3]));
        snprintf(id,20,"registo %d\n",res);
        write(STDOUT_FILENO, id, sizeof(id));
    }
    if(strcmp(argv[1],"-u") == 0){
        person_change_age(argv[2],atoi(argv[3]));
    }
}

int new_person(char* name, int age){
    Pessoa nova_pessoa;
    strcpy(nova_pessoa.nome, name);
    nova_pessoa.age= age;
    int fd = open("file_pessoas", O_WRONLY, O_CREAT, O_APPEND, 0644);
    lseek(fd,0,SEEK_END);
    write(fd,&nova_pessoa,sizeof(Pessoa));
    close(fd);
    return 0;
}

int person_change_age(char* nome, int age){
    int fd = open("file_pessoas", O_RDWR);

    Pessoa p;

    while(read(fd, &p, sizeof(Pessoa))){
        if(strcmp(p.nome, nome) == 0){
            p.age = age;
            lseek(fd, -sizeof(Pessoa), SEEK_CUR); 
            write(fd, &p, sizeof(Pessoa));
            return 0;
        }
    }
    return -1;
}

//int person_change_agev2(long pos, int age);