#||||||||||||||||||||||||||||||||||Multiplos|||||||||||||||||||||||||||||||||#
# Solução Fábio: 100%
import itertools

def multiplos(n, d):
    init_list = list(range(1, n+1))
    possibilities = itertools.permutations(init_list)
    count = 0

    for p in possibilities:
        number = int(''.join(map(str, p)))  # converte a tupla num número inteiro
        if number % d == 0:
            count += 1

    return count

#----------------------------------------------------------------------------#

# Solução Edu: 100%
def multiplos(n,d):
    return search(n,d)

def complete(n,c):
    return len(c) == n 

def extensions(n,c): 
    return [x for x in range(1,n+1) if x not in c]

def valid(d,c):
    st = ""
    for x in c:
        st += str(x)
    return int(st) % d == 0 


def search(n,d): 
    c = [] 
    return aux(n,d,c)

def aux(n,d,c): 
    if complete(n,c): 
        return valid(d,c) 
    p = 0
    for x in extensions(n,c): 
        c.append(x) 
        p += aux(n,d,c)     # True = 1, False = 0
        c.pop() 
    return p

#|||||||||||||||||||||||||||||||||||Amigos|||||||||||||||||||||||||||||||||||#
# Solução Edu: 90%
def amigos(conhecidos):
    if not conhecidos:
        return 0
    adj = {}
    for a, b in conhecidos:
        adj[a] = set()
        adj[b] = set()
    for a, b in conhecidos:
        adj[a].add(b)
        adj[b].add(a)
    return search(adj)

def complete(c,i):
    return len(c) == i

def extensions(adj, c):
    ret = []
    if c == []:
        for x in adj.keys():
            ret.append(x)
    else:
        for x in adj.keys():
            if x not in c and existearesta(c, x, adj):
                ret.append(x)
    return ret

def existearesta(c, x, adj):
    for p in c:
        if x != p and x not in adj[p]:
            return False
    return True

def valid(adj, c):
    for i in range(0,len(c)):
        for j in range(i+1,len(c)):
            if not c[i] in adj[c[j]]:
                return False
    return True

def search(p): 
    c = [] 
    i = len(p)
    while i != 2:
        if aux(p,c,i): 
            return len(c)
        i-=1
    return 2 

def aux(p, c, i):
    if complete(c,i):
        print(c)
        return valid(p, c)
    for x in extensions(p, c):
        c.append(x)
        if aux(p,c,i):
            return True
        c.pop()
    return False

#|||||||||||||||||||||||||||||||||||||Anel|||||||||||||||||||||||||||||||||||#
# Solução Edu: 100%
def anel(n):
    return search(n)

def complete(p,c):
    return len(c) == p 

def extensions(p,c):
    if c == []:
        return [x for x in range(1,p+1) if x not in c] 
    ret = []
    for x in range(1,p+1):
        if x not in c and primo(x+c[-1]):
            ret.append(x)
    return ret

def valid(c):
    if not primo(c[0]+c[-1]):
        return False
    for i in range(1,len(c)):
        val = c[i-1]+c[i]
        if not primo(val):
            return False
    return True

def search(p): 
    c = [] 
    if aux(p,c): 
        return c 

def aux(p,c): 
    if complete(p,c):
        return valid(c) 
    for x in extensions(p,c): 
        c.append(x) 
        # print(c)
        if aux(p,c): 
            return True
        c.pop() 
    return False

def primo(val):
    if val < 2:
        return False
    for i in range(2,val):
        if val % i == 0:
            return False
    return True

#|||||||||||||||||||||||||||||||||Hamilton|||||||||||||||||||||||||||||||||||#
# Solução Edu: 100%
def hamilton(arestas):
    adj = {}
    for a,b in arestas:
        adj[a] = set()
        adj[b] = set()
    for a,b in arestas:
        adj[a].add(b)
        adj[b].add(a)
    return search(adj)

def complete(adj,c): 
    for vert in adj.keys():
        if vert not in c:
            return False
    return True

def extensions(adj,c):
    ret = []
    if c == []:
        for x in adj.keys():
            if x not in c:
                ret.append(x)
    else:
        for x in adj.keys():
            if x not in c and c[-1] in adj[x]:
                ret.append(x)
    return ret

def valid(adj,c):
    return c[-1] in adj[c[0]]

def search(p): 
    c = [] 
    if aux(p,c): 
        return c 

def aux(p,c): 
    if complete(p,c): 
        return valid(p,c) 
    for x in extensions(p,c): 
        c.append(x) 
        if aux(p,c): 
            return True
        c.pop() 
    return False

#||||||||||||||||||||||||||||||||Superstring|||||||||||||||||||||||||||||||||#
# Solução Edu: 100%
def extensions(strings, N, k, ls):
    return [x for x in strings if x not in ls]

def search(strings, N, k, ls, resList, concatList):
    string = makeWord(ls, concatList)
    if len(string) == k:
        if any(map(lambda x: x not in ls, strings)):
            return False
        else:
            resList.append(string)
            return True
    elif len(string) > k:
        return False
    for x in extensions(strings, N, k, ls):
        ls.append(x)
        if search(strings, N, k, ls, resList, concatList):
            return True
        ls.pop()
    return False

def superstring(strings):
    res = "".join(strings)
    concatList = {}
    contidas = False
    for p in strings:
        for b in strings:
            if p != b:
                if p not in concatList:
                    concatList[p] = {}
                concatList[p][b] = concat(p,b)
                if concatList[p][b] != len(p):
                    contidas = True
    if not contidas:
        return res
    resList = [res]
    N = len(res)
    for k in range(1,N):
        if search(strings, N, k, [], resList, concatList):
            return resList[-1]
    return resList[-1]

def makeWord(ls, concatList):
    i = 0
    N = len(ls)
    if N == 0:
        return ""
    final = ""
    while i < N-1:
        pal = ls[i]
        nextPal = ls[i+1]
        final += pal[:concatList[pal][nextPal]]
        i+=1
    return final + ls[i]

def concat(a, b):
    Na = len(a)
    Nb = len(b)
    k = 0
    x = Na
    for i in range(Na):
        k = i
        for j in range(Nb):
            if k == Na:
                break
            elif a[k] == b[j]:
                k+=1
            else:
                break
        if k == Na:
            x = i
            break
    return x

#|||||||||||||||||||||||||||||||||||Sacos||||||||||||||||||||||||||||||||||||#
# Solução Edu: 80%
def extensions(peso, compras, k, i, sacos):
    return [x for x in range(k) if sacos[x] + compras[i] <= peso]

def search(peso, compras, N, k, i, sacos):
    if N == i:
        return True
    for x in extensions(peso, compras, k, i, sacos):
        sacos[x] += compras[i]
        if search(peso, compras, N, k, i+1, sacos):
            return True
        sacos[x] -= compras[i]
    return False

def sacos(peso,compras):
    N = len(compras)
    for k in range(1, len(compras)):
        sacos = [0 for _ in range(k)]
        if search(peso, compras, N, k, 0, sacos):
            return k
    return N

#----------------------------------------------------------------------------#

# Solução ChatGPT: 70%
def encontrar_combinacoes(sacos_atual, compras_restantes, peso, min_sacos):
    if len(compras_restantes) == 0:
        return min(min_sacos, len(sacos_atual))
    for i, saco in enumerate(sacos_atual):
        if saco + compras_restantes[0] <= peso:
            sacos_atual[i] += compras_restantes[0]
            min_sacos = encontrar_combinacoes(sacos_atual, compras_restantes[1:], peso, min_sacos)
            sacos_atual[i] -= compras_restantes[0]
    if compras_restantes[0] <= peso:
        min_sacos = encontrar_combinacoes(sacos_atual + [compras_restantes[0]], compras_restantes[1:], peso, min_sacos)
    return min_sacos

def sacos(peso, compras):
    min_sacos = len(compras)
    min_sacos = encontrar_combinacoes([], compras, peso, min_sacos)
    return min_sacos

#|||||||||||||||||||||||||||||||||||União||||||||||||||||||||||||||||||||||||#
# Solução Edu: 60%
def uniao(sets):
    res = set()
    for s in sets:
        res = res.union(s)
    return search(sets, res)

def extensions(p, c, i):
    ret = []
    for s in p:
        if s not in c:
            ret.append(s)
    return ret

def valid(p, c, res):
    resp = set()
    for s in c:
        resp = resp.union(s)
    return resp == res

def search(p, res):
    c = []
    i = 1
    while i <= len(p):
        if aux(p, c, res, i):
            return len(c)
        i += 1
    return -1

def aux(p, c, res, i):
    if i == 0:
        return valid(p, c, res)
    for x in extensions(p, c, i):
        c.append(x)
        # print(c)
        if aux(p, c, res, i-1):
            return True
        c.pop()
    return False

#|||||||||||||||||||||||||||||||||Cobertura||||||||||||||||||||||||||||||||||#
# Solução ChatGPT: 80%
def cobertura(arestas):
    vertices = set()
    grafo = {}
    
    # Criação do grafo
    for aresta in arestas:
        u, v = aresta
        if u not in grafo:
            grafo[u] = set()
        if v not in grafo:
            grafo[v] = set()
        grafo[u].add(v)
        grafo[v].add(u)
    
    # Ordenação dos vértices por grau decrescente
    vertices_ordenados = sorted(grafo.keys(), key=lambda x: len(grafo[x]), reverse=True)
    
    # Adiciona os vértices de grau máximo até cobrir todas as arestas
    for vertice in vertices_ordenados:
        if not all(v in vertices for v in grafo[vertice]):
            vertices.add(vertice)
    
    return len(vertices)

