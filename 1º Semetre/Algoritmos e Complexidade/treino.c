// Preparação teste algoritmos 2022/23

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define NV 6
typedef struct aresta {
    int dest; 
    int custo;
    struct aresta *prox;
} *LAdj, *GrafoL [NV];

typedef int GrafoM [NV][NV];



void fromMat (GrafoM o, GrafoL d){
  int i,j;
  struct aresta* aux;
  for(i=0; i<NV; i++) d[i] = NULL;
  for(i = 0; i<NV; i++){
      for(j = 0; j<NV; j++){
          if(o[i][j] != 0){
             aux = malloc(sizeof(struct aresta));
             aux -> dest = j;
             aux -> custo = o[i][j];
             aux -> prox = d[i];
             d[i] = aux;
          }
      }
  }
}

void initGrafoL(GrafoL l){
  int i;
  for(i = 0; i<NV ;i++)
      l[i] = malloc(sizeof(struct aresta));
      l[i] = NULL;
}

void printGrafoL(GrafoL l){
  int i;
  for(i = 0; i<NV ;i++){
    if(l[i] == NULL) printf("O Grafo está a Null\n");
    else printf("Dest: %d Custo: %d\n",l[i]->dest, l[i]->custo);
  }
  printf("\n");
}

void printMatriz(GrafoM m){
  int i,j;
  for(i = 0; i<NV; i++){
      for(j = 0; j<NV; j++){
        printf("Em m[%d][%d]: %d %d\n",i,j,m[i][0],m[0][j]);
      }
  }
  printf("\n");
}

int main(){
    GrafoL l;
    GrafoM arr;
    
    for(int i = 0; i<NV; i++)
        for(int j = 0; j<NV; j++)
            arr[i][j] = 0;

arr[0][1] = 4;
arr[0][2] = 3;
arr[0][4] = 1;
arr[1][4] = 4;
arr[2][0] = 2;
arr[3][2] = 1;
arr[3][5] = 2;
arr[4][3] = 3;
arr[5][4] = 1;

    printMatriz(arr);
    printf("Inicialização:\n");
    initGrafoL(l);
    printGrafoL(l);
    printf("Matriz para Grafo:\n");
    fromMat(arr, l);
    printGrafoL(l);
    printf("Grafo para Matriz:\n");
   // inverte(l,arr);
    printMatriz(arr);
}