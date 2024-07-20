// 25/10/22

// Algoritmos de organização de um array
//
void Qsort(int v[], int N){
    int p;
    if(N>1){
        p = particion(V,N);
        Qsort(v,p);
        Qsort(v+p+1;N-p-1);
    }
}

// "particion" retorna o indice de um elem tal que todos os valores anteriores a esse indice são
// menores que o elemento.
// se retorna 0 quer dizer que o array esta inversamente ordenado
// se retorna N-1 quer dizer que o array já se encontra ordenado

// Algoritmos para descobrir o K-esimo maior termo de um array
//
int men(int x, int v[], int N){
    int i, c = 0;
    for(i = 0; i < N; i++){
        if(v[i] < x) c++;
    }
    return c;
}

int kesimo (int k, int v[], int N){
    int i;
    for(i = 0; i < N; i++){
        if(men(v[i],v,N) == k) return (v[i])
    }
}

// Solução do Tony Hoare
int kesimo (int k, int v[], int N){
    int p;
    if(N == 1) return(v[0]);
    else {
        p = particion(v,N);
        if(p == k)return(v[p]);
        if(k<p) return kesimo(k,v,p);
        if(k>p) return kesimo(k-p,v+p+1,N-p-1);
    }
}

// ----- // -----

// ADT - consultar o indice de uma chave, adicionar info a uma chave ou adicionar uma chave e apagar a chave/info
// python tem o dicionario para isso, perl tem o array associativo, em c:

// Tabelas de Hash
#define hsize 1000

typedef(struct celula){
    int estado; // 0 = livre, 1 = ocupado
    int elem;
} Celula;

typedef celula conjunto[hsize]

int hash(int chave, int s){
    return (chave%s);
}

void init conj(conjunto c){
    int i;
    for(i = 0; i < hsize; i++){
        c[i].estado = 0;
    }
}

int adiciona(int x, conjunto c){
    int v = hsize;
    while(c[p].estado == 1 && v>0){
        p = (p+1)%hsize;
        v--;
    }
    if(v == 0) return 1;
    c[p].estado = 1;
    c[p].elem = x;
    return 0;
}

int elem (int x, conjunt c){
    int p = hash(x,hsize)

    while(!(c[p].estado == 0 !! (c[p].estado == 1) && c[p].elemento == x)){
        p = (p+1) % hsize;
    }
    return (c[p].elemento == x && c[p].estado == 1);
}

// Arvores Binárias de procura
// São muito dificeis de equilibrar e ou balancear

typedef struct abin{
    int valor;
    int b; // caso b == -1 (arvore desbalanceada para a dir) == 0 (balanceada) == 1 (desbalanceada para a esq)
    struct abin
        *esq, *dir;
} *ABin

ABin insere(int x, ABin a){
    if(a == null){
        a = malloc(sizeof(ABin));
        a->valor = x;
        a->esq = a->dir = null;
    }
    else if(x<a->valor)
        a->esq = insere(x,a->esq);
    else a->dir = insere(x,a->dir);
    return a;
    // porém este tipo de inserção desbalanceia a árvore muito facilmente
}

#define BAL 0
#define DIR -1
#define ESQ 1

ABin insere(int x, ABin a, int *aumentou){
    if(a == NULL){
        a = malloc(sizeof(abin));
        a->valor = x; a->b = BAL;
        a->esq = a->dir = NULL;
        *aumentou = 1;
    }
    else if(x<a -> valor){
        x->esq = insere(x,a->esq,aumentou);
        if(a->b == DIR){
            a->b = BAL; *aumentou = 0;
        }
        else if(a->b == BAL){
            a->b = ESQ;
        }
        else {
            a = corrige (a);
            *aumentou = 0;
        }
    }
}

ABin rotDireita (ABin b){
    ABin a = b->esq;
    b->esq = a->dir;
    a->dir = b;
    return a;
}

ABin rotEsquerda (ABin b){
    (...)
}

ABin corrige(ABin A){
    if(a->esq->bal == ESQ){
        a = rotDireita(a);
        a->b = a->dir->b = BAL 
    } else { // a->esq-> b == DIR
        a->esq = rotEsquerda (a-> esq);
        a = rodaDireita(a);
    }
}

// Grafos
typedef struct aresta{
    int dest;
    int peso;
    struct aresta *prox;
} Aresta;

typedef Aresta *ListaA;
typedef ListaA Grafo[N];

int ha_caminho(Grafo g, int or, int dest, int v[]){
    struct aresta *aux; 
    v[or = 1];
    if(or == dest) return 1;
    for(aux = g[or]; aux != NULL; aux = aux -> prox){
        if(v[aux->dest] == 0 && ha_caminho(g,aux->dest,dest)) return 1;
    }
    return 0;
}

int hacaminho_main (Grafo g, int or, int dest){
    int visitado[N], i;
    for(i = 0; i<N; i++)visitado[i] = 0;
    ha_caminho(g,or,dest,visitado)
}

alcancaveis(Grafo g, int or, int v[], pais[]){
    int i;
    for(i = 0; i < N; i++){
        v[i] = 0;
        pais[i] = -1;
    }
    alcaux(g,or,dest,v,pais)
}

void alcaux(Grafo g, int or, int v[]){
    struct aresta *alcaux
    v[or] = 1;
    if(or == dest) return 1;
    for(aux = g[or]; aux != NULL; aux = aux -> prox){
        if(v[aux->dest == 0]){
            alcaux(g,aux->dest, v);
            pais[aux -> dest] = or;
        }
    }
}
// vetor pais regista os anteriores


