# Treino para o Torneio 2

'''
Implemente uma função que calcula a área de um mapa que é acessível por
um robot a partir de um determinado ponto.
O mapa é quadrado e representado por uma lista de strings, onde um '.' representa
um espaço vazio e um '*' um obstáculo.
O ponto inicial consistirá nas coordenadas horizontal e vertical, medidas a 
partir do canto superior esquerdo.
O robot só consegue movimentar-se na horizontal ou na vertical. 
'''
def area(p,mapa):
    queue = [p]
    area = 0

    movx = [1,-1,0,0]
    movy = [0,0,1,-1]

    for ponto in queue:
        (x,y) = ponto
        area += 1
        for i in range(4):
            if 0 <= x + movx[i] < len(mapa[0]) and 0 <= y + movy[i] < len(mapa):
                if ((x + movx[i],y + movy[i]) not in queue) and (mapa[y + movy[i]][x + movx[i]] == "."):
                    queue.append((x + movx[i],y + movy[i]))
    
    return area

'''
O objectivo deste problema é determinar quantos movimentos são necessários para 
movimentar um cavalo num tabuleiro de xadrez entre duas posições.
A função recebe dois pares de coordenadas, que identificam a origem e destino pretendido,
devendo devolver o número mínimo de saltos necessários para atingir o destino a partir da origem.
Assuma que o tabuleiro tem tamanho ilimitado.
'''
def saltos(o,d):
    (x,y) = o
    fila = [((x,y),0)]
    movs = [(2, 1), (1, 2), (-2, 1), (-1, 2), (2, -1), (1, -2), (-2, -1), (-1, -2)]
    visitados = []
    for posicao,movimentos in fila:
        if posicao == d:
            return movimentos
        if posicao not in visitados:
            visitados.append(posicao)
            (x,y) = posicao
            for dx, dy in movs:
                fila.append(((x + dx, y + dy), movimentos + 1))
    return -1

'''
Podemos usar um (multi) grafo para representar um mapa de uma cidade: 
cada nó representa um cruzamento e cada aresta uma rua.
Pretende-se que implemente uma função que calcula o tamanho de uma cidade, 
sendo esse tamanho a distância entre os seus cruzamentos mais afastados.
A entrada consistirá numa lista de nomes de ruas (podendo assumir que os 
nomes de ruas são únicos). Os identificadores dos cruzamentos correspondem a 
letras do alfabeto, e cada rua começa (e acaba) no cruzamento 
identificado pelo primeiro (e último) caracter do respectivo nome.

TAMANHO DAS PALAVRAS é o peso entre a primiera e a ultima letra
'''
def tamanho(ruas):                  
    grafo = build(ruas)
    dist = fw(grafo)
    maior = maior_dict(dist)
    return maior
    
def build(ruas):                             
    adj = {}
    for conj in ruas:
        adj = build_aux(conj,adj)

    return adj

def build_aux(arestas,adj):
    
    if arestas[0] not in adj: 
        adj[arestas[0]] = {}
    if arestas[-1] not in adj:
        adj[arestas[-1]] = {}
    if arestas[0] != arestas[-1]:
        if existe(adj,arestas[0],arestas[-1]) == 0:
            if adj[arestas[0]][arestas[-1]] > len(arestas):
                adj[arestas[0]][arestas[-1]] = len(arestas)
                adj[arestas[-1]][arestas[0]] = len(arestas)
        else:
            adj[arestas[0]][arestas[-1]] = len(arestas)
            adj[arestas[-1]][arestas[0]] = len(arestas)
            
    return adj

def fw(adj): 
    dist = {} 
    for o in adj: 
        dist[o] = {} 
        for d in adj: 
            if o == d: 
                dist[o][d] = 0 
            elif d in adj[o]: 
                dist[o][d] = adj[o][d] 
            else: 
                dist[o][d] = float("inf") 
    for k in adj: 
        for o in adj: 
            for d in adj: 
                if dist[o][k] + dist[k][d] < dist[o][d]: 
                    dist[o][d] = dist[o][k] + dist[k][d] 
    return dist
    
def maior_dict(dict):
    maior = -1
    for d1 in dict:
        for d2 in dict:
            if dict[d1][d2] > maior:
                maior = dict[d1][d2]

    return maior
    
def existe(dict,a,b):
    for key in dict[a]:
        if key == b:
            return 0
            
    return 1
    
'''
O objectivo deste problema é determinar o tamanho do maior continente de um planeta.
Considera-se que pertencem ao mesmo continente todos os países com ligação entre si por terra. 
Irá receber uma descrição de um planeta, que consiste numa lista de fronteiras, onde cada fronteira
é uma lista de países que são vizinhos entre si. 
A função deverá devolver o tamanho do maior continente.
'''

def build(arestas):                             
    adj = {}
    for conj in arestas:
        adj = build_aux(conj,adj)

    return adj

def build_aux(arestas,adj):
    if(len(arestas) > 1):
        for i in range(len(arestas)):
            for j in range(i,len(arestas)):
                if(arestas[i] != arestas[j]):
                    if arestas[i] not in adj: 
                        adj[arestas[i]] = set()
                    if arestas[j] not in adj:
                        adj[arestas[j]] = set()
            
                    adj[arestas[i]].add(arestas[j])
                    adj[arestas[j]].add(arestas[i])
    else:
        adj[arestas[0]] = set()

    return adj

def dfs(adj,o): 
    return dfs_aux(adj,o,set(),{})  

def dfs_aux(adj,o,vis,pai): 
    vis.add(o)                  
    for d in adj[o]:                      
        if d not in vis:            
            pai[d] = o              
            dfs_aux(adj,d,vis,pai)  
    return pai

def maior(vizinhos):
    adj = build(vizinhos)
    maior = 0
    val = 0

    for pais in adj:
        val = len(dfs(adj,pais))+1
        if val > maior: maior = val
        ret = dfs(adj,pais)

    return maior

'''
O número de Erdos é uma homenagem ao matemático húngaro Paul Erdos,
que durante a sua vida escreveu cerca de 1500 artigos, grande parte deles em 
pareceria com outros autores. O número de Erdos de Paul Erdos é 0. 
Para qualquer outro autor, o seu número de Erdos é igual ao menor 
número de Erdos de todos os seus co-autores mais 1. Dado um dicionário que
associa artigos aos respectivos autores, implemente uma função que
calcula uma lista com os autores com número de Erdos menores que um determinado 
valor. A lista de resultado deve ser ordenada pelo número de Erdos, e, para
autores com o mesmo número, lexicograficamente.
'''
def erdos(artigos,n):
    dict = {"Paul Erdos":0}
    autores = ["Paul Erdos"]

    for autor in autores:
        for aut in artigos.values():
            if autor in aut:
                for everyaut in aut:
                    if everyaut not in dict and everyaut != autor:
                        dict[everyaut] = dict[autor]+1
                        autores.append(everyaut)
    
    autores = filtra(dict,n)
    autores.sort()
    autores.sort(key = lambda x: dict[x])
    return autores


def filtra(dict, n):
    ret = []
    for key,value in dict.items():
        if value <= n:
            ret.append(key)
    return ret

'''
Implemente uma função que calcula um dos caminhos mais curtos para atravessar
um labirinto. O mapa do labirinto é quadrado e representado por uma lista 
de strings, onde um ' ' representa um espaço vazio e um '#' um obstáculo.
O ponto de entrada é o canto superior esquerdo e o ponto de saída o canto
inferior direito. A função deve devolver uma string com as instruções para
atravesar o labirinto. As instruções podem ser 'N','S','E','O'.
'''
def caminho(mapa):
    queue = [(0,0,"")]
    movx = [1,-1,0,0]
    movy = [0,0,1,-1]
    x1 = 0
    y1 = 0
    visitados = []

    while x1 != len(mapa[0])-1 and y1 != len(mapa)-1:
        for x,y,str in queue:
            visitados.append((x,y))
            for i in range(4):
                if 0 <= x + movx[i] < len(mapa[0]) and 0 <= y + movy[i] < len(mapa):
                    if mapa[y + movy[i]][x + movx[i]] == " " and (x + movx[i],y + movy[i]) not in visitados:
                        if i == 0:
                            queue.append((x + movx[i],y + movy[i],str + "E"))
                        elif i == 1:
                            queue.append((x + movx[i],y + movy[i],str + "O"))
                        elif i == 2:
                            queue.append((x + movx[i],y + movy[i],str + "S"))
                        elif i == 3:
                            queue.append((x + movx[i],y + movy[i],str + "N"))
            x1 = x
            y1 = y  

    for x,y,str in queue:
        if x == len(mapa[0])-1 and y == len(mapa)-1:
            return str
    return "erro"

'''
Implemente uma função que calcula o menor custo de atravessar uma região de
Norte para Sul. O mapa da região é rectangular, dado por uma lista de strings,
onde cada digito representa a altura de cada ponto. Só é possível efectuar 
movimentos na horizontal ou na vertical, e só é possível passar de um ponto
para outro se a diferença de alturas for inferior ou igual a 2, sendo o custo 
desse movimento 1 mais a diferença de alturas. O ponto de partida (na linha
mais a Norte) e o ponto de chegada (na linha mais a Sul) não estão fixados à
partida, devendo a função devolver a coordenada horizontal do melhor
ponto para iniciar a travessia e o respectivo custo. No caso de haver dois pontos
com igual custo, deve devolver a coordenada mais a Oeste.
'''
def travessia(mapa):            
    orla = [] # x,y,custo,inicial
    visitados = {}
    movs = [(1,0),(-1,0),(0,1),(0,-1)] # tmb dá jeito subir pelos vistos

    for k in range(len(mapa[0])):
        orla.append( (k,0,0,k) )
        visitados[k] = []

    for x,y,custo,inicial in orla:
        if (x,y) not in visitados[inicial]:
            visitados[inicial].append((x,y))
            for dx,dy in movs:
                newx = x+dx
                newy = y+dy
                if (newx,newy) not in visitados[inicial]:
                    if -1 < newx < len(mapa[0]) and -1 < newy < len(mapa):
                        dist = abs(int(mapa[y][x]) - int(mapa[newy][newx]))
                        if dist <= 2:
                            orla.append((newx,newy,custo + dist + 1,inicial))
     
    custoret = float("inf")
    inicialret = 0
    for x,y,custo,inicial in orla:
         if y == len(mapa)-1:
            if custoret > custo:
                custoret = custo
                inicialret = inicial
            elif custoret == custo and inicialret > inicial:
                inicialret = inicial
    
    return (inicialret,custoret)

'''
Implemente uma função que calcula o preço mais barato para fazer uma viagem de
autocarro entre duas cidades. A função recebe (para além das duas cidades) uma
lista de rotas de autocarro, onde cada rota é uma sequência de cidades por onde
passa o autocarro, intercalada com o custo para viajar entre cada par de cidades.
Assuma que cada rota funciona nos dois sentidos.
'''

def viagem(rotas,o,d):                  
    control1 = 0
    control2 = 0
    for lista in rotas:
        if o in lista:
            control1 = 1
        if d in lista:
            control2 = 1
    
    if control1 == 1 and control2 == 1:
        adj = {}
        adj = build(rotas)
        dist = fw(adj)
        return dist[o][d]
    else:
        return 0

def build(arestas):                             
    adj = {}
    for conj in arestas:
        adj = build_aux(conj,adj)

    return adj

def build_aux(arestas,adj):
    j = 0
    if(len(arestas) > 1):
        for i in range(0,len(arestas),2):
            j = i+2
            if(j < len(arestas)):
                if arestas[i] not in adj: 
                    adj[arestas[i]] = {}
                if arestas[j] not in adj:
                    adj[arestas[j]] = {}
        
                adj[arestas[i]][arestas[j]] = arestas[i+1]
                adj[arestas[j]][arestas[i]] = arestas[i+1]
    else:
        adj[arestas[0]] = {}
        
    return adj

def fw(adj): 
    dist = {} 
    for o in adj: 
        dist[o] = {} 
        for d in adj: 
            if o == d: 
                dist[o][d] = 0 
            elif d in adj[o]: 
                dist[o][d] = adj[o][d] 
            else: 
                dist[o][d] = float("inf") 
    for k in adj: 
        for o in adj: 
            for d in adj: 
                if dist[o][k] + dist[k][d] < dist[o][d]: 
                    dist[o][d] = dist[o][k] + dist[k][d] 
    return dist
