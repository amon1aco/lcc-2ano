# Treino 3

""" Programação Dinâmica """

"""
Implemente uma função que dada uma sequência de inteiros, determinar o 
comprimento da maior sub-sequência (não necessariamente contígua) que se 
encontra ordenada de forma crescente.

Sugere-se que comece por implementar uma função auxiliar recursiva que determina 
o comprimento da maior sub-sequência crescente que inclui o primeiro elemento
da sequência, sendo o resultado pretendido o máximo obtido aplicando esta
função a todos os sufixos da sequência de entrada.
"""
# Crescente Recursiva: 
def crescente(seq):
    if len(seq) == 0:  # Verifica se a sequência está vazia
        return 0
    return auxiliar(seq, float('-inf'), 0)
    
    
def auxiliar(seq, anterior, indice):
    if indice == len(seq):  # Verifica se chegamos ao final da sequência
        return 0
    
    inclui = 0
    if seq[indice] > anterior:  # Verifica se o elemento atual é maior que o anterior
        inclui = 1 + auxiliar(seq, seq[indice], indice + 1)
    
    exclui = auxiliar(seq, anterior, indice + 1)
    
    return max(inclui, exclui)

# Crescente com memoization
def maior_subsequencia_crescente(seq, memo):
    if not seq:
        return 0

    if tuple(seq) in memo:
        return memo[tuple(seq)]

    maximo = 1
    for i in range(1, len(seq)):
        if seq[i] > seq[0]:
            sub_maximo = maior_subsequencia_crescente(seq[i:], memo)
            maximo = max(maximo, 1 + sub_maximo)

    memo[tuple(seq)] = maximo
    return maximo


def crescente(seq):
    maximo = 0
    memo = {}
    for i in range(len(seq)):
        sub_maximo = maior_subsequencia_crescente(seq[i:], {})
        maximo = max(maximo, sub_maximo)

    return maximo

# Crescente com P.dinâmica
def crescente(lista):
    n = len(lista)
    if n == 0:
        return 0
    cache = [1 for x in range(n+1)]
    cache[0] = 0

    for x in range(2, n+1):
        for y in range(x-1, 0, -1):
            if lista[x-1] >= lista[y-1]:
                cache[x] = max(cache[x], cache[y] + 1)
    
    return max(cache)


"""
Implemente uma função que, dada uma frase cujos espaços foram retirados, 
tenta recuperar a dita frase. Para além da frase (sem espaços nem pontuação), 
a função recebe uma lista de palavras válidas a considerar na reconstrução 
da dita frase. Deverá devolver a maior frase que pode construir inserindo
espaços na string de entrada e usando apenas as palavras que foram indicadas 
como válidas. Por maior entende-se a que recupera o maior prefixo da string
de entrada. Só serão usados testes em que a maior frase é única.
"""
# Recursiva
def espaca(frase,palavras):
    return aux(frase,palavras,"",0)[1:]

def aux(frase,palavras,str,y):
    if frase == "":
        return str
    
    if y >= len(frase):
        return ""
    
    if frase[:y+1] in palavras:
        new_str = str + " " + frase[:y+1]
        return max(aux(frase[y+1:],palavras,new_str,0), aux(frase, palavras, str, y+1), key=lambda x: len(x))
    
    return aux(frase,palavras,str,y+1)

# Memoization
def espaca(frase, palavras):
    cache = {}
    return aux(frase, palavras, "", 0,cache)[1:]

def aux(frase, palavras, str, y,cache):
    # Verifica se já processou essa sub-sequência antes
    if (frase, y) in cache:
        return cache[(frase, y)]

    # Caso base: frase vazia ou final da frase
    if frase == "":
        return str

    # Caso base: final da frase
    if y >= len(frase):
        return ""

    # Verifica se a sub-sequência atual é uma palavra válida
    if frase[:y+1] in palavras:
        new_str = str + " " + frase[:y+1]
        result = max(aux(frase[y+1:], palavras, new_str, 0,cache), aux(frase, palavras, str, y+1,cache), key=lambda x: len(x))
    else:
        result = aux(frase, palavras, str, y+1,cache)

    # Armazena o resultado no cache
    cache[(frase, y)] = result
    return result



#DinamicCoding              - 80%
def espaca(frase,palavras):
    n = len(frase)
    cache = ["" for x in range(n+1)]
    
    for x in range(n):
        for y in range(x+1, n+1):
            pal = frase[x:y]
            if pal in palavras:
                ant = cache[x]
                if x != 0 and not ant:
                    continue
                if not cache[y]:
                    cache[y] = ant + " "*(ant != "") + pal

    return cache[n]



"""
Um ladrão assalta uma casa e, dado que tem uma capacidade de carga limitada, 
tem que decidir que objectos vai levar por forma a maximizar o potencial lucro. 

Implemente uma função que ajude o ladrão a decidir o que levar.
A função recebe a capacidade de carga do ladrão (em Kg) seguida de uma lista 
dos objectos existentes na casa, sendo cada um um triplo com o nome, o valor de 
venda no mercado negro, e o seu peso. Deve devolver o máximo lucro que o ladrão
poderá  obter para a capacidade de carga especificada.
"""

#Def Recursiva
def ladrao(capacidade, objetos):
    # Caso base: não há mais objetos ou a capacidade é zero
    if not objetos or capacidade == 0:
        return 0
    
    # Verifica se o próximo objeto pode ser levado ou não
    (nome, valor, peso) = objetos[0]
    lucro = 0
    if peso <= capacidade:
        lucro = valor + ladrao(capacidade - peso, objetos[1:])
    
    # Compara o lucro obtido levando o objeto com o lucro obtido sem levá-lo
    return max(lucro, ladrao(capacidade, objetos[1:]))

#Memoization    - Já passa em 100%
def ladrao(capacidade, objetos):
    return aux(capacidade,objetos,{})

def aux(capacidade,objetos,dict):
    if not objetos or capacidade == 0:
        return 0
    
    if (capacidade, tuple(objetos)) in dict:
        return dict[(capacidade, tuple(objetos))]
    
    (nome, valor, peso) = objetos[0]
    lucro = 0
    if peso <= capacidade:
        lucro = valor + aux(capacidade - peso, objetos[1:],dict)
    lucro_sem_obj = aux(capacidade, objetos[1:], dict)
    dict[(capacidade, tuple(objetos))] = max(lucro, lucro_sem_obj)
    return dict[(capacidade, tuple(objetos))]

#PD BottomUp
def ladrao(capacidade, objetos):
    n = len(objetos)
    cache = [[0 for j in range(capacidade+1)] for i in range(n+1)]

    for i in range(1, n+1):
        for j in range(1, capacidade+1):
            (nome, valor, peso) = objetos[i-1]
            if peso <= j:
                cache[i][j] = max(valor + cache[i-1][j-peso], cache[i-1][j])
            else:
                cache[i][j] = cache[i-1][j]

    return cache[n][capacidade]


"""
Implemente uma função que determina qual a probabilidade de um robot regressar 
ao ponto de partido num determinado número de passos. Sempre que o robot dá um 
passo tem uma determinada probabilidade de seguir para cima ('U'), baixo ('D'), 
esquerda ('L') ou direita ('R'). A função recebe o número de passos que o 
robot vai dar e um dicionário com probabilidades de se movimentar em cada uma
das direcções (as chaves são os caracteres indicados entre parêntesis).
O resultado deve ser devolvido com a precisao de 2 casas decimais.
"""

# Def recrusiva
def probabilidade(passos,probs):
    return aux(passos, 0, 0, probs)

def aux(passos, x, y, probs):
    if passos == 0:
        return 1 if x == 0 and y == 0 else 0

    prob = 0
    for direcao, prob_dir in probs.items():
        if direcao == 'U':
            prob += prob_dir * aux(passos-1, x, y+1, probs)
        elif direcao == 'D':
            prob += prob_dir * aux(passos-1, x, y-1, probs)
        elif direcao == 'L':
            prob += prob_dir * aux(passos-1, x-1, y, probs)
        elif direcao == 'R':
            prob += prob_dir * aux(passos-1, x+1, y, probs)

    return round(prob, 2)


# Memoization   - 90%
def probabilidade(passos,probs):
    return aux(passos, 0, 0, probs,{})

def aux(passos, x, y, probs,dict):
    if passos == 0:
        return 1 if x == 0 and y == 0 else 0
    
    if ((x,y),passos) in dict:
            return dict[(x, y),passos]

    prob = 0
    for direcao, prob_dir in probs.items():
        if direcao == 'U':
            prob += prob_dir * aux(passos-1, x, y+1, probs,dict)
            dict[(x,y),passos] = prob
        elif direcao == 'D':
            prob += prob_dir * aux(passos-1, x, y-1, probs,dict)
            dict[(x,y),passos] = prob
        elif direcao == 'L':
            prob += prob_dir * aux(passos-1, x-1, y, probs,dict)
            dict[(x,y),passos] = prob
        elif direcao == 'R':
            prob += prob_dir * aux(passos-1, x+1, y, probs,dict)
            dict[(x,y),passos] = prob

    return round(dict[(x,y),passos],2)

#PD bottomUp
def probabilidade(passos,probabilidade):
    probs = {}
    
    # Movimentos possíveis vêm em pares (cima/baixo e esquerda/direita) 
    # porque tem de voltar sempre ao início
    if passos % 2 != 0:
        return 0
    
    limInf = (-passos//3)-2
    limSup = (passos//3)+2

    for x in range(limInf, limSup):
        for y in range(limInf, limSup):
            probs[0,x,y] = 0
    
    probs[0,0,0] = 1
    lado = ['L', 'R', 'U', 'D']
    dx = [-1,1,0, 0]
    dy = [ 0,0,1,-1]

    for p in range(1, passos+1):
        for x in range(limInf, limSup):
            for y in range(limInf, limSup):
                probs[p,x,y] = 0
                for k in range(4):
                    X = x + dx[k]
                    Y = y + dy[k]
                    if limInf <= X < limSup and limInf <= Y < limSup:
                        antiga = probabilidade[lado[k]]*probs[p-1,X,Y]
                        probs[p,x,y] += antiga
    
    return round(probs[passos,0,0],2)


"""
Um fugitivo pretende atravessar um campo no mínimo tempo possível (desde o 
canto superior esquerdo até ao canto inferior direito). Para tal só se poderá 
deslocar para a direita ou para baixo. No entanto, enquanto atravessa o campo 
pretende saquear ao máximo os bens deixados por fugitivos anteriores. Neste 
problema pretende-se que implemente uma função para determinar qual o máximo 
valor que o fugitivo consegue saquear enquanto atravessa o campo. 
A função recebe o mapa rectangular defindo com uma lista de strings. Nestas
strings o caracter '.' representa um espaço vazio, o caracter '#' representa 
um muro que não pode ser atravessado, e os digitos sinalizam posições onde há 
bens abandonados, sendo o valor dos mesmos igual ao digito.
Deverá devolver o valor máximo que o fugitivo consegue saquear enquanto 
atravessa o campo deslocando-se apenas para a direita e para baixo. Assuma que 
é sempre possível atravessar o campo dessa forma.
"""

# Def recursiva
def saque(mapa):
    return aux(mapa,0,0,0)

def aux(mapa,saque,x,y):
    if x > len(mapa[0]) or y > len(mapa):
        return -1
    
    if x == len(mapa[0]) or y == len(mapa):
        return saque 
    
    if mapa[y][x] == "#":
        return -1
    
    elif mapa[y][x] == ".":
        return max(aux(mapa,saque,x,y+1),aux(mapa,saque,x+1,y))
    
    else :
        return max(aux(mapa,saque+int(mapa[y][x]),x,y+1),aux(mapa,saque+int(mapa[y][x]),x+1,y))
    

# Memoization
def saque(mapa):
    if not mapa or not mapa[0]:
        return 0
    n = len(mapa)
    m = len(mapa[0])
    memo = [[-1 for j in range(m)] for i in range(n)]
    return aux(mapa, 0, 0, memo)

def aux(mapa, x, y, memo):
    if x >= len(mapa[0]) or y >= len(mapa):
        return 0
    
    if memo[y][x] != -1:
        return memo[y][x]
    
    if mapa[y][x] == "#":
        memo[y][x] = 0
    else:
        saq = 0
        if mapa[y][x].isdigit():
            saq = int(mapa[y][x])
        down = aux(mapa, x, y+1, memo)
        right = aux(mapa, x+1, y, memo)
        memo[y][x] = saq + max(down, right)
    
    return memo[y][x]

#PD bottomUp
def saque(mapa):
    n = len(mapa)
    m = len(mapa[0])
    cache = [[0 for x in range(m + 1)] for x in range(n + 1)]
    
    for y in range(n + 1):
        for x in range(m + 1):
            if x == 0 or y == 0:
                cache[y][x] = 0
            elif mapa[y-1][x-1] == '#':
                cache[y][x] = -1
            elif mapa[y-1][x-1] != '.':
                cache[y][x] = int(mapa[y-1][x-1]) + max(cache[y-1][x], cache[y-1][x-1], cache[y][x-1])
            else:
                cache[y][x] = max(cache[y-1][x], cache[y-1][x-1], cache[y][x-1])
            
    return cache[n][m]

#easy pd - 100%
def saque(mapa):
    dp = [[0 for n in range(len(mapa[0]))]for n in range(len(mapa))]

    for y in range(len(mapa)):
        for x in range(len(mapa[0])):
            if mapa[y][x] == "#":
                dp[y][x] = float("-inf")
            elif mapa[y][x] == ".":
                dp[y][x] = max(dp[y-1][x],dp[y][x-1])
            else:
                dp[y][x] = max(dp[y-1][x]+int(mapa[y][x]),dp[y][x-1]+int(mapa[y][x]))

    return dp[y][x]


"""
Implemente uma função que calula qual a subsequência (contígua e não vazia) de 
uma sequência de inteiros (também não vazia) com a maior soma. A função deve 
devolver apenas o valor dessa maior soma.

Sugere-se que começe por implementar (usando recursividade) uma função que 
calcula o prefixo de uma sequência com a maior soma. Tendo essa função 
implementada, é relativamente adaptá-la para devolver também a maior soma de toda
a lista.
"""

#Def Recursiva
def maxsoma(lista):
    if len(lista) == 0:
        return 0    
    
    soma = 0

    for v in lista:
        soma += v

    return max(soma,maxsoma(lista[1:]),maxsoma(lista[:-1]))

# Memoization
def maxsoma(lista):
    return aux(lista,{})

def aux(lista,dict):
    if len(lista) == 0:
        return 0 

    if tuple(lista) in dict:
        return dict[tuple(lista)]
    
    soma = 0

    for v in lista:
        soma += v

    dict[tuple(lista)] = soma
    max_soma = max(soma, aux(lista[1:], dict), aux(lista[:-1], dict))
    dict[tuple(lista)] = max_soma
    return max_soma

#PD BottomUp
def maxsoma(lista):
    n = len(lista)
    dp = [0] * n
    dp[0] = lista[0]
    for i in range(1, n):
        dp[i] = max(lista[i], dp[i-1] + lista[i])
    return max(dp)


"""
Um exemplo de um problema que pode ser resolvido de forma eficiente com 
programação dinâmica consiste em determinar, dada uma sequência arbitrária de 
números não negativos, se existe uma sub-sequência (não necessariamente contígua) 
cuja soma é um determinado valor. Implemente uma função que dado um valor e uma
listas de listas de números não negativos, devolva a lista com as listas com uma
sub-sequência cuja soma é o valor dado.
"""

# Def Recursiva
def validas(soma,listas):
    ret = []

    for lista in listas:
        if aux(soma,lista,0) == 1:
            ret.append(lista)

    return ret 

def aux(soma,lista,soma_atual):
    if soma_atual == soma:
        return 1
    
    if soma_atual > soma or len(lista) == 0:
        return 0
    
    return max(aux(soma,lista[1:],soma_atual+lista[0]),aux(soma,lista[1:],soma_atual))


# Memoization
def validas(soma,listas):
    ret = []
    memo = {}
    for lista in listas:
        if aux(soma, lista, tuple(range(len(lista))), memo):
            ret.append(lista)
    return ret

def aux(soma, lista, indices, memo):
    if soma == 0:
        return True
    if soma < 0 or not indices:
        return False
    if (soma, indices) in memo:
        return memo[(soma, indices)]
    if aux(soma-lista[indices[0]], lista, indices[1:], memo) or aux(soma, lista, indices[1:], memo):
        memo[(soma, indices)] = True
        return True
    memo[(soma, indices)] = False
    return False

# PD Bottom-up
def validas(soma, listas):
    ret = []
    dict = {}

    for lista in listas:
        if aux(soma, lista, dict) == 1:
            ret.append(lista)

    return ret

def aux(soma, lista, dict):
    n = len(lista)
    table = [[False for k in range(soma+1)] for k in range(n+1)]

    # Caso base: soma 0 é possível com qualquer lista vazia
    for i in range(n+1):
        table[i][0] = True

    # Preenchendo a tabela bottom-up
    for i in range(1, n+1):
        for j in range(1, soma+1):
            if j < lista[i-1]:
                table[i][j] = table[i-1][j]
            else:
                table[i][j] = table[i-1][j] or table[i-1][j-lista[i-1]]

    # Recuperando a resposta da tabela
    return table[n][soma]

"""
Um vendedor ambulante tem que decidir que produtos levará na sua próxima viagem.
Infelizmente, tem um limite de peso que pode transportar e, tendo isso em atenção, 
tem que escolher a melhor combinação de produtos a transportar dentro desse limite 
que lhe permitirá ter a máxima receita.

Implemente uma função que, dado o limite de peso que o vendedor pode transportar, 
e uma lista de produtos entre os quais ele pode escolher (assuma que tem à sua 
disposição um stock ilimitado de cada produto), devolve o valor de receita máximo
que poderá obter se vender todos os produtos que escolher transportar, e a lista
de produtos que deverá levar para obter essa receita (incluindo repetições, 
caso se justifique), ordenada alfabeticamente.

Cada produto consiste num triplo com o nome, o valor, e o peso.

Caso haja 2 produtos com a mesma rentabilidade por peso deverá dar prioridade 
aos produtos que aparecem primeiro na lista de entrada.
"""

# Recursiva
def vendedor(capacidade, produtos):
    return aux(capacidade, produtos, 0, [])

def aux(capacidade, produtos, cash, lista):
    if capacidade == 0 or not produtos:
        return cash, sorted(lista)
    
    if capacidade - produtos[0][2] < 0:
        return 0, 0
    
    new_lista = lista + [produtos[0][0]]
    return max(aux(capacidade - produtos[0][2], produtos, cash+produtos[0][1], new_lista),
               aux(capacidade - produtos[0][2], produtos[1:], cash+produtos[0][1], new_lista),
               aux(capacidade, produtos[1:], cash, lista),
               key=lambda x: x[0])

# Memoization 
def vendedor(capacidade,produtos):
    cache = {}
    result = aux(capacidade, produtos, cache)
    result[1].sort()
    return result

def aux(capacidade, produtos, cache):
    if capacidade not in cache:
        if capacidade == 0 or produtos == []:
            cache[capacidade] = 0,[]
        elif capacidade < produtos[0][2]:
            cache[capacidade] = aux(capacidade, produtos[1:], cache)
        else:
            b = aux(capacidade - produtos[0][2], produtos, cache)
            c = aux(capacidade - produtos[0][2], produtos[1:], cache)
            a = aux(capacidade, produtos[1:], cache)
            b = b[0] + produtos[0][1], b[1] + [produtos[0][0]]
            c = c[0] + produtos[0][1], c[1] + [produtos[0][0]]
    
            cache[capacidade] = max(b, c, a, key=lambda x: x[0])
    return cache[capacidade]

#PD Bottom-up
def vendedor(capacidade, produtos):
    n = len(produtos)
    dp = [[0 for k in range(capacidade+1)] for k in range(n+1)]
    escolhidos = [[[] for k in range(capacidade+1)] for k in range(n+1)]
    
    for y in range(1, n+1):
        for x in range(1, capacidade+1):
            peso = produtos[y-1][2]
            valor = produtos[y-1][1]
            sem_prod = dp[y-1][x]
            com_prod = 0 if x < peso else dp[y][x-peso] + valor
            if com_prod > sem_prod:
                dp[y][x] = com_prod
                escolhidos[y][x] = escolhidos[y][x-peso] + [produtos[y-1][0]]
            else:
                dp[y][x] = sem_prod
                escolhidos[y][x] = escolhidos[y-1][x]
    
    return dp[n][capacidade], sorted(escolhidos[n][capacidade])
