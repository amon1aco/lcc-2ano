# Treino para o Torneio 1


"""
Implemente uma função que dado um dicionário com as preferências dos alunos
por projectos (para cada aluno uma lista de identificadores de projecto, 
por ordem de preferência), aloca esses alunos aos projectos. A alocação
é feita por ordem de número de aluno, e cada projecto só pode ser feito
por um aluno. A função deve devolver a lista com os alunos que não ficaram
alocados a nenhum projecto, ordenada por ordem de número de aluno.
"""

def possivel (proj, alloc):
    for p in proj:
        if p not in alloc: 
            return p
    return None #Caso nao seja possivel atribuir nenhum dos projetos ao aluno

def aloca(prefs):
    retorna = []
    proj_alocados = []
    chaves = sorted(prefs.keys())

    for aluno in chaves: 
        p = prefs[aluno]
        x = possivel(p,proj_alocados)
        if x == None:
            retorna.append(aluno)
        else:
            proj_alocados.append(x)

    return retorna


'''
Defina uma função que, dada uma lista de nomes de pessoas, devolva essa lista ordenada 
por ordem crescente do número de apelidos (todos menos o primeiro nome). No caso de pessoas com o mesmo número de apelidos,
devem ser listadas por ordem lexicográfica do nome completo.
'''

def nr_de_apelidos(nome):
    lista = list(nome.split(" "))
    return len(lista) - 1

def apelidos(nomes):
    nomes.sort()
    nomes.sort(key = nr_de_apelidos)
    return nomes


'''
Podemos usar um (multi) grafo para representar um mapa de uma cidade: 
cada nó representa um cruzamento e cada aresta uma rua.

Pretende-se que implemente uma função que lista os cruzamentos de uma cidade 
por ordem crescente de criticidade: um cruzamento é tão mais crítico quanto 
maior o número de ruas que interliga.

A entrada consistirá numa lista de nomes de ruas (podendo assumir que os nomes de ruas são únicos). 
Os identificadores dos cruzamentos correspondem a letras do alfabeto, e cada rua começa (e acaba) no cruzamento 
identificado pelo primeiro (e último) caracter do respectivo nome.

A função deverá retornar uma lista com os nomes dos cruzamentos por ordem crescente de criticidade, 
listando para cada cruzamento um tuplo com o respectivo identificador e o número de ruas que interliga.
Apenas deverão ser listados os cruzamentos que interliguem alguma rua, e os cruzamentos com o mesmo 
nível de criticidade deverão ser listados por ordem alfabética.
'''

def conta_ruas(ruas,letra):
    r = 0

    for i in range(len(ruas)):
        if((ruas[i])[0] == letra): r +=1
        elif((ruas[i])[-1] == letra): r+= 1
    return r

def mostra_ruas(ruas):
    retorna = []
    for c in range(len(ruas)):
        if (ruas[c])[0] not in retorna:
            retorna.append((ruas[c])[0])
        if (ruas[c])[-1] not in retorna:
            retorna.append((ruas[c][-1]))
    return retorna                                                         

def cruzamentos(ruas):
    retorna = []
    list = mostra_ruas(ruas)
    for i in range(len(list)):
        caracter = list[i]
        val = conta_ruas(ruas,caracter)
        retorna.append((caracter,val))

    retorna.sort(key = lambda x: x[0])
    retorna.sort(key = lambda x: x[1]) 
    return retorna


"""
Implemente uma função que formata um programa em C.
O código será fornecido numa única linha e deverá introduzir
um '\n' após cada ';', '{', ou '}' (com excepção da última linha).
No caso do '{' as instruções seguintes deverão também estar identadas
2 espaços para a direita.
"""


def format_cod_first(codigo):                                  # 40% APENAS
    retorna = ""
    for i in range(len(codigo)):
        if(codigo[i] == " " and codigo[i+1] == " "): 
            continue
        if(codigo[i-1] == " " and codigo[i] == " "):
            continue
        retorna += codigo[i]
    
    return retorna
    

def formata(codigo):
    new_cod = format_cod_first(codigo)
    retorna = ""
    control = 0
    for i in range(len(new_cod)):
        
        
        if(new_cod[i] == ";"):
            
            retorna += new_cod[i]
            if(i < len(new_cod)-1): 
                retorna += "\n"
            i += 1
            if(i+1>= len(new_cod)): control = 0
            if(control == 1): retorna += "  "
            

        elif(new_cod[i] == "{"):
            control = 1
            retorna += new_cod[i]
        
            if(i < len(new_cod)-1): 
                retorna += '\n'
            retorna += "  "
            i += 1
        
        elif(new_cod[i] == "}"):
            retorna += new_cod[i]
            if(i < len(new_cod)-1): 
                retorna += '\n'
            i += 1
            control = 0

        else:
            retorna += new_cod[i]
    
    return retorna


'''
Neste problem pretende-se que defina uma função que, dada uma string com palavras, 
devolva uma lista com as palavras nela contidas ordenada por ordem de frequência,
da mais alta para a mais baixa. Palavras com a mesma frequência devem ser listadas 
por ordem alfabética.
'''

def conta_apar(texto, palavra):
    palavras_lista = texto.split(" ")
    retorna = 0
    for i in range(len(palavras_lista)):
        if(palavras_lista[i] == palavra): retorna += 1
    return retorna


def frequencia(texto):
    conj = {}
    conj = set(texto.split(" "))
    lista_de_palavras = list(conj)
    lista_de_pares = []
    for i in range(len(lista_de_palavras)):
        lista_de_pares.append((lista_de_palavras[i],conta_apar(texto,lista_de_palavras[i])))
    
    lista_de_pares.sort(key = lambda x: x[0])
    lista_de_pares.sort(key = lambda x: x[1], reverse=True)
    
    retorna = []
    for i in range(len(lista_de_pares)):
        retorna.append((lista_de_pares[i])[0])

    return retorna


'''
Implemente uma função que calcula a tabela classificativa de um campeonato de
futebol. A função recebe uma lista de resultados de jogos (tuplo com os nomes das
equipas e os respectivos golos) e deve devolver a tabela classificativa (lista com
as equipas e respectivos pontos), ordenada decrescentemente pelos pontos. Em
caso de empate neste critério, deve ser usada a diferença entre o número total
de golos marcados e sofridos para desempatar, e, se persistir o empate, o nome
da equipa.
'''

def cria_dict(jogo):
    retorna = {}
    for i in range(len(jogo)):
        if((jogo[i])[0]) not in retorna: retorna[(jogo[i])[0]] = 0
        if((jogo[i])[2],0) not in retorna: retorna[(jogo[i])[0]] = 0
    return retorna


def tabela(jogos):
    pontos = cria_dict(jogos)
    golos = cria_dict(jogos)
    for i in range(len(jogos)):
        if jogos[i][1] > jogos[i][3]: 
            pontos[jogos[i][0]] += 3
            golos[jogos[i][0]] += jogos[i][1]
            golos[jogos[i][0]] -= jogos[i][3]
            golos[jogos[i][2]] += jogos[i][3]
            golos[jogos[i][2]] -= jogos[i][1]
        elif jogos[i][1] < jogos[i][3]:
            pontos[jogos[i][2]] += 3 
            golos[jogos[i][0]] += jogos[i][1]
            golos[jogos[i][0]] -= jogos[i][3]
            golos[jogos[i][2]] += jogos[i][3]
            golos[jogos[i][2]] -= jogos[i][1]
        else:
            pontos[jogos[i][0]] += 1
            pontos[jogos[i][2]] += 1
            golos[jogos[i][0]] += jogos[i][1]
            golos[jogos[i][0]] -= jogos[i][3]
            golos[jogos[i][2]] += jogos[i][3]
            golos[jogos[i][2]] -= jogos[i][1]

    retorna = list(pontos.items())
    retorna.sort(key = lambda x: x[0])
    retorna.sort(key = lambda x: golos[x[0]], reverse=True)
    retorna.sort(key = lambda x: x[1], reverse=True)
    return retorna


"""
Um hacker teve acesso a um log de transações com cartões de
crédito. O log é uma lista de tuplos, cada um com os dados de uma transação,
nomedamente o cartão que foi usado, podendo alguns dos números estar
ocultados com um *, e o email do dono do cartão.

Pretende-se que implemente uma função que ajude o hacker a 
reconstruir os cartões de crédito, combinando os números que estão
visíveis em diferentes transações. Caso haja uma contradição nos números 
visíveis deve ser dada prioridade à transção mais recente, i.é, a que
aparece mais tarde no log.

A função deve devolver uma lista de tuplos, cada um com um cartão e um email,
dando prioridade aos cartões com mais digitos descobertos e, em caso de igualdade
neste critério, aos emails menores (em ordem lexicográfica).
"""

def junta(velho,novo):
    resultado = ""
    for i in range(len(novo)):
        if novo[i] != "*":
            resultado += novo[i]
        else:
            resultado += velho[i]
    
    return resultado 

def sortkey(k):
    (a,b) = k
    return (a.count("*"),b)
        

def hacker(log):
    res = {}

    for (num,mail) in log:
        if mail not in res: 
            res[mail] = num 
        else:
            res[mail] = junta(res[mail], num)
    
    return sorted([(a,b) for (b,a) in res.items()], key = sortkey)


"""
Implemente uma função que calcula o horário de uma turma de alunos.
A função recebe dois dicionários, o primeiro associa a cada UC o
respectivo horário (um triplo com dia da semana, hora de início e
duração) e o segundo associa a cada aluno o conjunto das UCs em
que está inscrito. A função deve devolver uma lista com os alunos que
conseguem frequentar todas as UCs em que estão inscritos, indicando
para cada um desses alunos o respecto número e o número total de horas
semanais de aulas. Esta lista deve estar ordenada por ordem decrescente
de horas e, para horas idênticas, por ordem crescente de número.
"""


def aulas(ucs,ucs_dict):                # TA FODA
    retorna = {}
    list_ucs = list(ucs)
    for i in range(len(list_ucs)):
        uc = list_ucs[i]
        wday = ucs_dict[uc][0]
        retorna[uc] = []
        
        if uc in list(ucs_dict):
            horas = ucs_dict[uc][1]
            if ucs_dict[uc][2] > 0:
                for i in range(ucs_dict[uc][2]):
                    retorna[wday].append(horas + i)
            
    return retorna
        


def horario(ucs,alunos):
    retorna = []
    dict_aux = {}
    aluno_list = list(alunos)
    
    for i in range(len(aluno_list)):
        dict_aux = aulas(alunos[aluno_list[i]],ucs)

    return dict_aux
        

    
    
            
    
ucs = {"la2": ("quarta",16,2), "pi": ("terca",15,1), "cp": ("terca",14,2),"so": ("quinta",9,3)}
alunos = {5000: {"la2","cp"}, 2000: {"la2","cp","pi"},3000: {"cp","poo"}, 1000: {"la2","cp","so"}}
self.assertEqual(horario(ucs,alunos),[(1000, 7), (5000, 4)])

ucs = {"la2": ("quarta",16,2), "pi": ("terca",15,1)}
alunos = {5000: {"la2","pi"}, 2000: {"pi","la2"}}
self.assertEqual(horario(ucs,alunos),[(2000, 3), (5000, 3)])








'''
Pretende-se que implemente uma função que detecte códigos ISBN inválidos. 
Um código ISBN é constituído por 13 digitos, sendo o último um digito de controlo.
Este digito de controlo é escolhido de tal forma que a soma de todos os digitos, 
cada um multiplicado por um peso que é alternadamente 1 ou 3, seja um múltiplo de 10.
A função recebe um dicionário que associa livros a ISBNs,
e deverá devolver a lista ordenada de todos os livros com ISBNs inválidos.
'''

def testa_num(val):
    soma = 0
    for i in range(len(val)-1):
        if i % 2 == 0: soma += int(val[i])
        if i % 2 != 0: soma += (int(val[i])*3)
    soma += int(val[-1])
    if soma % 10 == 0: return True
    return False


def isbn(livros):
    retorna = []
    livros_list = list(livros)
    for i in range(len(livros_list)):
        if testa_num(livros[livros_list[i]]) == False: 
            retorna.append(livros_list[i])
    
    retorna.sort()
    return retorna


'''
Neste problema prentede-se que implemente uma função que calcula o rectângulo onde se movimenta um robot.

Inicialmente o robot encontra-se na posição (0,0) virado para cima e irá receber uma sequência de comandos numa string.
Existem quatro tipos de comandos que o robot reconhece:
  'A' - avançar na direcção para o qual está virado
  'E' - virar-se 90º para a esquerda
  'D' - virar-se 90º para a direita 
  'H' - parar e regressar à posição inicial virado para cima
  
Quando o robot recebe o comando 'H' devem ser guardadas as 4 coordenadas (minímo no eixo dos X, mínimo no eixo dos Y, máximo no eixo dos X, máximo no eixo dos Y) que definem o rectângulo 
onde se movimentou desde o início da sequência de comandos ou desde o último comando 'H'.

A função deve retornar a lista de todas os rectangulos (tuplos com 4 inteiros)
'''

def robot(comandos):
    retorna = []

    cima = 1
    esq = 0
    dir = 0
    baixo = 0

    x = 0
    y = 0

    max_x = 0
    min_x = 0
    max_y = 0
    min_y = 0

    for i in range(len(comandos)):
        if(x > max_x): max_x = x
        if(x < min_x): min_x = x
        if(y > max_y): max_y = y
        if(y < min_y): min_y = y


        if comandos[i] == "A":
            if cima == 1: y += 1
            elif esq == 1: x -= 1
            elif dir == 1: x += 1
            elif baixo == 1: y -= 1
            
        elif comandos[i] == "E":
            if cima == 1: 
                cima = 0
                esq = 1
            elif esq == 1:
                esq = 0
                baixo = 1
            elif baixo == 1:
                baixo = 0
                dir = 1
            elif dir == 1:
                dir = 0
                cima = 1
                
        elif comandos[i] == "D":
            if cima == 1: 
                cima = 0
                dir = 1
            elif dir == 1:
                dir = 0
                baixo = 1
            elif baixo == 1:
                baixo = 0
                esq = 1
            elif esq == 1:
                esq = 0
                cima = 1
                
        elif comandos[i] == "H":
            cima = 1
            dir = esq = baixo = 0
            retorna.append((min_x,min_y,max_x,max_y))
            x = y = 0
            min_x = min_y = max_x = max_y = 0

    return retorna