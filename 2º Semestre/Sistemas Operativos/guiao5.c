// guiao5.c


#include <stdio.h>
#include <fcntl.h>
#include <unistd.h>
#include <limits.h>
#include <sys/types.h>
#include <sys/stat.h>

#define MAX_LINE_SIZE 1024

//int mkfifo(const char *pathname, mode_t mode);

//-1
int main(){
    int res = mkfifo("fifo",0666);
    if(res == -1){
        perror("mkfifo");
    }
    return 0;
}


int main(){

    int fd_fifo = open("fifo", O_WRONLY);
    
    if(fd_fifo < 0){
        perror("open");
    } else {
        printf("Open FIFO for writing...\n");
    }

    int bytes_read = 0;
    char buffer[MAX_LINE_SIZE];
    while((bytes_read = read(0,&buffer,MAX_LINE_SIZE)) > 0){
        int bytes_writen = write(fd_fifo,&buffer,bytes_read);
        printf("Written to FIFO %d bytes...\n", bytes_writen);
    }
    close(fd_fifo);
    return 0;
}

int main(){

    int fd_fifo = open("fifo", O_RDONLY);

    if(fd_fifo < 0){
        perror("open");
    } else {
        printf("Opened FIFO for reading...\n");
    }

    int bytes_read = 0
    char buffer[MAX_LINE_SIZE];

    while((bytes_read = read(fd_fifo,&buffer,MAX_LINE_SIZE)) > 0){
        int bytes_writen = write(1,&buffer,MAX_LINE_SIZE)
    }

    close(fd_fifo);
    unlink("fifo");

    return 0;
}

//-2 
//cliente5.c e servidor5.c