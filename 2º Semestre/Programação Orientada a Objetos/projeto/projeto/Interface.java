import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Classe interface que é chamada pela main e é nela que é criada toda a interação entre utilizador e programa
 */
public class Interface {
    private Vintage vintage = new Vintage();
    private Data data = new Data();
    private String caminhoArquivo;
    
    /** 
     * Função Construtor com parametros 
     */
    public Interface(Data data){
        this.vintage = new Vintage(data);
        this.data = data;
        this.caminhoArquivo = "/Users/eduar/OneDrive/Ambiente de Trabalho/donned-ig/donned-ig/projeto/aquivo.txt";
        // this.caminhoArquivo = "/Users/gugafm11/Desktop/POO/arquivo.txt";
        //this.caminhoArquivo = "/Users/fabio/Desktop/projeto.arquivo.txt";
    }

    public void run(){
        // Gerenciar arquivos:
        Arquivos gerenciador = new Arquivos(caminhoArquivo);

        // Criar uma instância da classe Vintage
        // Vintage vintage = new Vintage();
        // Preencher a instância com os dados desejados

        // Salvar o estado da instância em um arquivo
        // gerenciador.salvarEstado(vintage);

        // Carregar o estado da instância a partir do arquivo
        Vintage vintageCarregado = (Vintage) gerenciador.carregarEstado();

        this.vintage = vintageCarregado;

        //System.out.println(vintage.getMapaUtilizadores());

        // Transportadora DST = new Transportadora("DST",0.50f,1,1.5f,0.15f,0.5f,data);
        // Transportadora MUNDIAL = new Transportadora("MUNDIAL",0.50f,1,1.5f,0.10f,0.2f,data);
        // vintage.addTransportadora(DST);
        // vintage.addTransportadora(MUNDIAL);

        Scanner scanner = new Scanner(System.in);
        int opcaoInicio = -1;
        Malas MALA = null;
        Sapatilhas SAPATILHA = null;
        Tshirt TSHRIT = null;
        Transportadora TRANSPORTADORA = null;

        /**
         * Menu Inicial (Informações e Opções fora do Login)
         */
        while (opcaoInicio != 0) {    
            System.out.println("--------MENU--------");
            System.out.println("1 - Criar Nova Conta");
            System.out.println("2 - Iniciar Sessão");
            System.out.println("3 - Criar Transportadora");
            System.out.println("4 - Avançar Dia");
            System.out.println("5 - Avançar Data");
            System.out.println("6 - Registo de Encomendas");
            System.out.println("7 - Ordenacao dos maiores Vendedores");
            System.out.println("8 - Utilizadores com maior Faturação a partir de uma Data");
            System.out.println("9 - Utilizador com maior Faturação desde Sempre");
            System.out.println("10 - Ordenacao dos maiores Compradores");
            System.out.println("11 - Total de faturação através da Vintage");
            System.out.println("12 - Transportadora com maior Faturação");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            opcaoInicio = scanner.nextInt();

            switch (opcaoInicio) {
                
                /**
                 * Opção para criar Conta
                 */
                case 1:
                    scanner.nextLine(); // Limpar o buffer
                    System.out.println("Digite o email do utilizador:");
                    String email = scanner.nextLine();
                    while (email.trim().isEmpty()) { // Verificar se o email é vazio ou somente contém espaços em branco
                        System.out.println("Por favor, insira um email válido:");
                        email = scanner.nextLine();
                    }
                    String nome;
                    while (true) {
                        System.out.println("Digite o nome do utilizador:");
                        nome = scanner.nextLine().trim();
                        if (nome.isEmpty()) {
                            System.out.println("Por favor, insira um nome válido:");
                            continue;
                        }
                        boolean nomeJaExiste = false;
                        for (Utilizador u : vintage.getMapaUtilizadores().values()) {
                            if (u.getNome().equals(nome)) {
                                nomeJaExiste = true;
                                System.out.println("Já existe um utilizador com este nome. Por favor, escolha outro nome.");
                                break;
                            }
                        }
                        if (!nomeJaExiste) {
                            break;
                        }
                    }
                    System.out.println("Digite a morada do utilizador:");
                    String morada = scanner.nextLine();
                    while (morada.trim().isEmpty()) { // Verificar se a morada é vazia ou somente contém espaços em branco
                        System.out.println("Por favor, insira uma morada válida:");
                        morada = scanner.nextLine();
                    }
                    System.out.println("Digite o nr fiscal:");
                    int nrfis = 0;
                    boolean entradaValidaU1 = false;
                    while (!entradaValidaU1) {
                        if (scanner.hasNextInt()) {
                            nrfis = scanner.nextInt();
                            entradaValidaU1 = true;
                        } else {
                            System.out.println("Por favor, insira apenas números para o nr fiscal:");
                            scanner.next(); // limpa a entrada inválida do usuário
                        }
                    }
                    scanner.nextLine();
                    Utilizador novoUtilizador = vintage.criarUtilizador(email, nome, morada, nrfis);
                    vintage.addUtilizadorMapa(novoUtilizador.getCodigo(), novoUtilizador);
                    System.out.println("Novo utilizador criado:");
                    System.out.println(novoUtilizador);
                    break;       
                
                /**
                 * Opção para Fazer Login
                 */
                case 2:
                    System.out.println("Lista de utilizadores:");
                    for (Utilizador u : vintage.getUtilizadores()) {
                        System.out.println(u.getNome());
                    }
                    System.out.println("Digite o nome do utilizador:");
                    scanner.nextLine();
                    String nomeUtilizador = scanner.nextLine();
                    boolean encontrado = false;
                    Utilizador utilizadorEscolhido = null;
                    while (!encontrado) {
                        for (Utilizador u : vintage.getUtilizadores()) {
                            if (u.getNome().equals(nomeUtilizador)) {
                                encontrado = true;
                                utilizadorEscolhido = u;
                                break;
                            }
                        }
                        if (!encontrado) {
                            System.out.println("Utilizador não encontrado. Digite o nome novamente:");
                            nomeUtilizador = scanner.nextLine();
                        }
                        break;
                    }
                    if(utilizadorEscolhido == null){
                        System.out.println("Digite um utilizador válido ou crie um!");
                        break;
                    }
                    // utilizador agora contém o utilizador escolhido pelo usuário
                    // scanner.nextLine(); // Limpar o buffer
                    int opcao2 = -1;
                    List<Integer> idsProdutosAdicionados = new ArrayList<>();
                    
                    /**
                     * Depois de fazer Login aparece o Menu do Utilizador e suas Opções
                     */
                    while(opcao2 != 0) {       
                            System.out.println("-------Menu do "+utilizadorEscolhido.getNome()+"-------");            
                            System.out.println("1 - Adicionar novo produto"); //
                            System.out.println("2 - Remover produto");
                            System.out.println("3 - Adicionar ao Carrinho");
                            System.out.println("4 - Ver Carrinho");
                            System.out.println("5 - Ver preço do Carrinho");
                            System.out.println("6 - Efetuar Compra");
                            System.out.println("7 - Efetuar Devolução");
                            System.out.println("8 - Ver Marketplace");
                            System.out.println("9 - Ver Perfil");
                            System.out.println("0 - Sair");
                            System.out.print("Escolha uma opção: ");
                            opcao2 = scanner.nextInt();
                        switch(opcao2){
                            
                            /**
                             * Opção para adicionar um novo produto para venda
                             */
                            case 1:
                                // scanner.nextLine(); // Limpar o buffer
                                System.out.println("Qual o tipo de produto que deseja adicionar? Digite '1' para sapatilhas, '2' para t-shirts ou '3' para malas.");
                                int tipoProduto;
                                while (true) {
                                    tipoProduto = scanner.nextInt();
                                    if (tipoProduto == 1 || tipoProduto == 2 || tipoProduto == 3) {
                                        break;
                                    } else {
                                        System.out.println("Opção inválida. Digite '1' para sapatilhas, '2' para t-shirts ou '3' para malas.");
                                    }
                                }
                                scanner.nextLine(); // Limpar o buffer
                                int codigoVendedor = utilizadorEscolhido.getCodigo();
                                Utilizador vendedorProduto = vintage.getUtilizadorByKey(codigoVendedor);
                                        
                                        /**
                                         * Switch case para o tipo de produto
                                         */
                                        switch (tipoProduto) {
                                            
                                            /**
                                             * Produto que pretende Adicionar são Sapatilhas
                                             */
                                            case 1:
                                                System.out.println("Digite o preço da sapatilha:");
                                                float precoBaseS = 0;
                                                boolean entradaValidaS1 = false;
                                                while (!entradaValidaS1) {
                                                    if (scanner.hasNextFloat()) {
                                                        precoBaseS = scanner.nextFloat();
                                                        entradaValidaS1 = true;
                                                    } else {
                                                        System.out.println("Por favor, insira um preço válido.");
                                                        scanner.next(); // limpa a entrada inválida do usuário
                                                    }
                                                }
                                                System.out.println("Digite o tamanho da sapatilha:");
                                                int tamanhoS = 0;
                                                boolean entradaValidaS2 = false;
                                                while (!entradaValidaS2) {
                                                    if (scanner.hasNextInt()) {
                                                        tamanhoS = scanner.nextInt();
                                                        entradaValidaS2 = true;
                                                    } else {
                                                        System.out.println("Por favor, insira um tamanho válido.");
                                                        scanner.next(); // limpa a entrada inválida do usuário
                                                    }
                                                }
                                                System.out.println("Digite o número de utilizadores:");
                                                int nrDonosS = 0;
                                                boolean entradaValidaS3 = false;
                                                while (!entradaValidaS3) {
                                                    if (scanner.hasNextInt()) {
                                                        nrDonosS = scanner.nextInt();
                                                        entradaValidaS3 = true;
                                                    } else {
                                                        System.out.println("Por favor, insira um número de utilizadores válido.");
                                                        scanner.next(); // limpa a entrada inválida do usuário
                                                    }
                                                }
                                                scanner.nextLine(); // consome a nova linha pendente
                                                System.out.println("Digite o estado do artigo (Mau, Bom ou Muito Bom):");
                                                String estadoS = "";
                                                while (true) {
                                                    String input = scanner.nextLine();
                                                    if (input.equalsIgnoreCase("Mau")) {
                                                        estadoS = "Mau"; break;
                                                    } else if (input.equalsIgnoreCase("Bom")) {
                                                        estadoS = "Bom"; break;
                                                    } else if (input.equalsIgnoreCase("Muito Bom")) {
                                                        estadoS = "Muito Bom"; break;
                                                    } else System.out.println("Opção inválida. Por favor, digite 'Mau', 'Bom' ou 'Muito Bom':");
                                                }
                                                System.out.println("Digite a marca do artigo: ");
                                                String marcaS = scanner.nextLine();
                                                boolean caracteristicasS = false;
                                                while (true) {
                                                    System.out.println("A sapatilha tem atacadores (S/N)?");
                                                    String input = scanner.next();
                                                    if (input.equalsIgnoreCase("S")) {
                                                        caracteristicasS = true; break;
                                                    } else if (input.equalsIgnoreCase("N")) {
                                                        caracteristicasS = false; break;
                                                    } else System.out.println("Opção inválida. Por favor, digite 'S' ou 'N':");
                                                }
                                                scanner.nextLine(); // Limpar o buffer
                                                System.out.println("Digite a cor da sapatilha:");
                                                String corS = scanner.nextLine();
                                                System.out.println("Digite o ano de fabricação da sapatilha:");
                                                int anoS = 0;
                                                boolean entradaValidaS4 = false;
                                                while (!entradaValidaS4) {
                                                    if (scanner.hasNextInt()) {
                                                        anoS = scanner.nextInt();
                                                        if (anoS <= 2023) {
                                                            entradaValidaS4 = true;
                                                        } else {
                                                            System.out.println("Por favor, insira um ano válido menor ou igual a 2023.");
                                                        }
                                                    } else {
                                                        System.out.println("Por favor, insira um ano válido.");
                                                        scanner.next(); // limpa a entrada inválida do usuário
                                                    }
                                                }
                                                float descontoS = 0;
                                                System.out.println("Escolha a transportadora da Sapatilha:");
                                                for (int i = 0; i < vintage.getTransportadoras().size(); i++) {
                                                    Transportadora transportadora = vintage.getTransportadoras().get(i);
                                                    System.out.println((i + 1) + " - " + transportadora.getNome());
                                                }
                                                
                                                int opcaoTransportadoraS = scanner.nextInt();
                                                Transportadora transportadoraS = null;
                                                if (opcaoTransportadoraS >= 1 && opcaoTransportadoraS <= vintage.getTransportadoras().size()) {
                                                    transportadoraS = vintage.getTransportadoras().get(opcaoTransportadoraS - 1);
                                                } else {
                                                    System.out.println("Opção inválida.");
                                                }
                                                if (transportadoraS != null) {
                                                    SAPATILHA = vintage.criarSapatilha(precoBaseS, nrDonosS, estadoS, marcaS, anoS, tamanhoS, descontoS, caracteristicasS, corS, vendedorProduto, transportadoraS);
                                                }
                                                vendedorProduto.adicionarProdutoVenda(SAPATILHA);
                                                System.out.println("Nova sapatilha adicionada à venda:");
                                                break;                                         
                                            
                                            /**
                                             * Produto que pretende Adicionar é uma T-shirt
                                             */
                                            case 2:
                                                System.out.println("Digite o preço da T-shirt:");
                                                float precoBaseT = 0;
                                                boolean entradaValidaT1 = false;
                                                while (!entradaValidaT1) {
                                                    if (scanner.hasNextFloat()) {
                                                        precoBaseT = scanner.nextFloat();
                                                        entradaValidaT1 = true;
                                                    } else {
                                                        System.out.println("Por favor, insira um preço válido.");
                                                        scanner.next(); // limpa a entrada inválida do usuário
                                                    }
                                                }
                                                scanner.nextLine(); // Limpar o buffer
                                                System.out.println("Digite o número de utilizadores:");
                                                int nrDonosT = 0;
                                                boolean entradaValidaT2 = false;
                                                while (!entradaValidaT2) {
                                                    if (scanner.hasNextInt()) {
                                                        nrDonosT = scanner.nextInt();
                                                        entradaValidaT2 = true;
                                                    } else {
                                                        System.out.println("Por favor, insira um número de utilizadores válido.");
                                                        scanner.next(); // limpa a entrada inválida do usuário
                                                    }
                                                }
                                                scanner.nextLine(); // consome a nova linha pendente
                                                System.out.println("Digite o estado do artigo (Mau, Bom ou Muito Bom):");
                                                String estadoT = "";
                                                while (true) {
                                                    String input = scanner.nextLine();
                                                    if (input.equalsIgnoreCase("Mau")) {
                                                        estadoT = "Mau"; break;
                                                    } else if (input.equalsIgnoreCase("Bom")) {
                                                        estadoT = "Bom"; break;
                                                    } else if (input.equalsIgnoreCase("Muito Bom")) {
                                                        estadoT = "Muito Bom"; break;
                                                    } else System.out.println("Opção inválida. Por favor, digite 'Mau', 'Bom' ou 'Muito Bom':");
                                                }
                                                System.out.println("Digite a marca da Tshirt:");
                                                String marcaT = scanner.nextLine();
                                                System.out.println("Digite o tamanho da T-shirt:");
                                                String tamanhoT = scanner.nextLine();
                                                while (!tamanhoT.equals("XXS") && !tamanhoT.equals("XS") && !tamanhoT.equals("S") && !tamanhoT.equals("M") && !tamanhoT.equals("L") && !tamanhoT.equals("XL") && !tamanhoT.equals("XXL")) {
                                                    System.out.println("Tamanho inválido. Por favor, digite um tamanho válido (XXS, XS, S, M, L, XL, XXL):");
                                                    tamanhoT = scanner.nextLine();
                                                }
                                                System.out.println("Digite o Padrão da T-shirt:");
                                                String padraoT = "";
                                                while (true) {
                                                    String input = scanner.nextLine();
                                                    if (input.equalsIgnoreCase("Liso")) {
                                                        padraoT = "Liso"; break;
                                                    } else if (input.equalsIgnoreCase("Riscas")) {
                                                        padraoT = "Riscas"; break;
                                                    } else if (input.equalsIgnoreCase("Palmeiras")) {
                                                        padraoT = "Palmeiras"; break;
                                                    } else System.out.println("Opção inválida. Por favor, digite 'Liso', 'Riscas' ou 'Palmeiras':");
                                                }
                                                int descontoT = 0;
                                                System.out.println("Escolha a transportadora da Tshirt:");
                                    
                                                for (int i = 0; i < vintage.getTransportadoras().size(); i++) {
                                                    Transportadora transportadora = vintage.getTransportadoras().get(i);
                                                    System.out.println((i + 1) + " - " + transportadora.getNome());
                                                }
                                                int opcaoTransportadoraT = scanner.nextInt();
                                                Transportadora transportadoraT = null;
                                                if (opcaoTransportadoraT >= 1 && opcaoTransportadoraT <= vintage.getTransportadoras().size()) {
                                                    transportadoraT = vintage.getTransportadoras().get(opcaoTransportadoraT - 1);
                                                } else {
                                                    System.out.println("Opção inválida.");
                                                }
                                                if (transportadoraT != null) {
                                                    TSHRIT = vintage.criarTshirt(precoBaseT, nrDonosT, estadoT, marcaT, descontoT, tamanhoT, padraoT, vendedorProduto, transportadoraT);
                                                }
                                                vendedorProduto.adicionarProdutoVenda(TSHRIT);
                                                System.out.println("Nova T-shirt adicionada à venda:");
                                                break;
                                            
                                            /**
                                             * Produto que pretende Adicionar é uma Mala
                                             */
                                            case 3:
                                                System.out.println("Digite o preço da Mala:");
                                                float precoBaseM = 0;
                                                boolean entradaValidaM1 = false;
                                                while (!entradaValidaM1) {
                                                    if (scanner.hasNextFloat()) {
                                                        precoBaseM = scanner.nextFloat();
                                                        entradaValidaM1 = true;
                                                    } else {
                                                        System.out.println("Por favor, insira um preço válido.");
                                                        scanner.next(); // limpa a entrada inválida do usuário
                                                    }
                                                }
                                                scanner.nextLine(); // Limpar o buffer
                                                System.out.println("Digite o número de utilizadores:");
                                                int nrDonosM = 0;
                                                boolean entradaValidaM2 = false;
                                                while (!entradaValidaM2) {
                                                    if (scanner.hasNextInt()) {
                                                        nrDonosM = scanner.nextInt();
                                                        entradaValidaM2 = true;
                                                    } else {
                                                        System.out.println("Por favor, insira um número de utilizadores válido.");
                                                        scanner.next(); // limpa a entrada inválida do usuário
                                                    }
                                                }
                                                scanner.nextLine(); // consome a nova linha pendente
                                                System.out.println("Digite o estado do artigo (Mau, Bom ou Muito Bom):");
                                                String estadoM = "";
                                                while (true) {
                                                    String input = scanner.nextLine();
                                                    if (input.equalsIgnoreCase("Mau")) {
                                                        estadoM = "Mau"; break;
                                                    } else if (input.equalsIgnoreCase("Bom")) {
                                                        estadoM = "Bom"; break;
                                                    } else if (input.equalsIgnoreCase("Muito Bom")) {
                                                        estadoM = "Muito Bom"; break;
                                                    } else System.out.println("Opção inválida. Por favor, digite 'Mau', 'Bom' ou 'Muito Bom':");
                                                }
                                                System.out.println("Digite a marca da Mala:");
                                                String marcaM = scanner.nextLine();
                                                System.out.println("Digite o volume da mala:");
                                                int volumeM = 0;
                                                boolean entradaValidaM3 = false;
                                                while (!entradaValidaM3) {
                                                    if (scanner.hasNextInt()) {
                                                        volumeM = scanner.nextInt();
                                                        entradaValidaM3 = true;
                                                    } else {
                                                        System.out.println("Por favor, insira um volume válido.");
                                                        scanner.next(); // limpa a entrada inválida do usuário
                                                    }
                                                }
                                                System.out.println("Digite o ano de fabricação da mala:");
                                                int anoM = 0;
                                                boolean entradaValidaM4 = false;
                                                while (!entradaValidaM4) {
                                                    if (scanner.hasNextInt()) {
                                                        anoM = scanner.nextInt();
                                                        if (anoM <= 2023) {
                                                            entradaValidaM4 = true;
                                                        } else {
                                                            System.out.println("Por favor, insira um ano válido menor ou igual a 2023.");
                                                        }
                                                    } else {
                                                        System.out.println("Por favor, insira um ano válido.");
                                                        scanner.next(); // limpa a entrada inválida do usuário
                                                    }
                                                }
                                                System.out.println("Digite o material da mala:");
                                                String materialM = scanner.nextLine();
                                                materialM = scanner.nextLine();
                                                float descontoM = 0;
                                                System.out.println("Escolha a transportadora da Mala:");
                                                for (int i = 0; i < vintage.getTransportadoras().size(); i++) {
                                                    Transportadora transportadora = vintage.getTransportadoras().get(i);
                                                    System.out.println((i + 1) + " - " + transportadora.getNome());
                                                }
                                                int opcaoTransportadoraM = scanner.nextInt();
                                                Transportadora transportadoraM = null;
                                                if (opcaoTransportadoraM >= 1 && opcaoTransportadoraM <= vintage.getTransportadoras().size()) {
                                                    transportadoraM = vintage.getTransportadoras().get(opcaoTransportadoraM - 1);
                                                } else {
                                                    System.out.println("Opção inválida.");
                                                }
                                                if (transportadoraM != null) {
                                                    MALA = vintage.criarMala(precoBaseM, nrDonosM, estadoM, marcaM, descontoM, anoM, materialM, volumeM, vendedorProduto, transportadoraM);
                                                }
                                                vendedorProduto.adicionarProdutoVenda(MALA);

                                                System.out.println("Nova mala adicionada à venda:");
                                                break;
                                                default:
                                                    System.out.println("Tipo de produto inválido.");
                                                    break;
                                                }
                                            break;
                            
                            /**
                             * Opção Para remover um produto de venda
                             */
                            case 2:
                                System.out.println("Artigos Anunciados");
                                System.out.println(utilizadorEscolhido.getProdutosVenda());
                                System.out.println("Digito o código do anúncio que pretende remover");
                                System.out.println(utilizadorEscolhido.getProdutosVenda());
                                int op = scanner.nextInt();
                                utilizadorEscolhido.removerProdutoVenda(op);
                                break;

                            /**
                             * Opção Para adicionar produtos ao carrinho
                             */
                            case 3:
                                if (vintage.getMapaUtilizadores().size() < 2) {
                                    System.out.println("Não tem produtos para adicionar.");
                                } else {
                                    for (Utilizador u : vintage.getMapaUtilizadores().values()) {
                                        if (u.getProdutosVenda().isEmpty()) {
                                            continue; // Ignora utilizadores sem produtos à venda
                                        }
                                       System.out.println(u.getProdutosVenda());
                                    }
                                    System.out.println("Códigos disponíveis");
                                    for(Artigo u: vintage.getMarketplace().getProdutosVenda()){
                                        System.out.println(u.getCodigo());
                                    }
                                    System.out.println("Digite o codigo do Produto que deseja adicionar ao Carrinho:");
                                    int op1 = scanner.nextInt();
                                    boolean produtoEncontrado = false;
                                    for (Utilizador u : vintage.getMapaUtilizadores().values()) {
                                        if (u.getCodigo() == utilizadorEscolhido.getCodigo()){
                                            continue;
                                        }
                                        if (u.getProdutosVenda().stream().anyMatch(p -> p.getCodigo() == op1)) {
                                            //anyMatch() é um método da classe Stream que verifica se algum elemento satisfaz uma determinada condição. 
                                            //No caso, estamos verificando se há algum elemento cujo código é igual ao op1. 
                                            //uma vez que usamos o operador == em vez de equals(), já que op1 é do tipo int.
                                            //contains() nao funciona porque nao recebe um int recebe um objeto
                                            utilizadorEscolhido.adicionarProdutoCompra(op1);
                                            produtoEncontrado = true;
                                            System.out.println("Artigo adicionado com sucesso!");
                                            idsProdutosAdicionados.add(op1);
                                            produtoEncontrado = true;
                                            for (Utilizador q : vintage.getMapaUtilizadores().values()) {
                                                if (u.getCodigo() == utilizadorEscolhido.getCodigo()){
                                                    continue;
                                                }
                                                if (q.getProdutosVenda().stream().anyMatch(p -> p.getCodigo() == op1)) {
                                                    q.removerProdutoVenda(op1);
                                                    break;
                                                }
                                            }
                                            break;
                                        }
                                    }
                                    if (!produtoEncontrado) {
                                        System.out.println("Esse produto não existe ou não está disponível para venda. Por favor, tente novamente.");
                                    }
                                }
                                break;

                            /**
                             * Opção Para ver o seu carrinho de compras
                             */
                            case 4:
                                System.out.println("Aqui está o seu Carrinho de Compras:");
                                System.out.println(utilizadorEscolhido.getCarrinho());
                                break;

                            /**
                             * Opção Para ver o montante total que tem no carrinho
                             */
                            case 5:
                                System.out.print("O preço do carrinho é de: ");
                                float precoTotal = utilizadorEscolhido.getCarrinho().getPrecoFinal();
                                DecimalFormat df = new DecimalFormat("#0.00");
                                System.out.println(df.format(precoTotal));
                                break;

                            /**
                             * Opção Para efeturar compra (compra todos os artigos que tiver no carrinho)
                             */
                            case 6:
                                System.out.println("Efetuar Compra:");
                                utilizadorEscolhido.efetuarCompra(vintage.getTransportadoras()); 
                                break;

                            /**
                             * Opção Para efeturar devolução de um artigo comprado(apenas funcionará 2 dias depois da compra)
                             */
                            case 7:
                                System.out.println("Efetuar Devolução:");
                                System.out.println("Lista de artigos comprados e Disponiveis para Devolução?");
                                System.out.println(utilizadorEscolhido.getProdutosCompra());
                                System.out.println("Digite o código do Artigo que prentende devolver");
                                int opc = scanner.nextInt();
                                utilizadorEscolhido.fazerDevolucao(opc);
                                break;

                            /**
                             * Opção Para observar o marketplace (todos os artigos disponiveis para venda)
                             */
                            case 8:
                                System.out.println("Marketplace:");  
                                System.out.println(utilizadorEscolhido.getMarketplace());
                                break;

                            /**
                             * Opção para o utilizador visualizar o seu perfil
                             */
                            case 9:
                                System.out.println(utilizadorEscolhido.toString());
                                break;

                            /**
                             * Opção fazer logout da conta e voltar ao menu inicial
                             */
                            case 0:
                                System.out.println("A Sair...");
                                break;
                            
                            /**
                             * Caso o input do utilizador não seja nenhuma das opçoes possivel retorna esta mensagem  e fecha
                             */
                            default:
                                System.out.println("Opção inválida.");
                                break;
                                    }
                            }
                    break;

                /**
                 * Opção Para criar uma nova transportadora
                 */
                case 3:
                    System.out.println();
                    System.out.println("Digite o nome da Transportadora:");
                    String nomeT = scanner.nextLine();
                    nomeT = scanner.nextLine();
                    System.out.println(nomeT);
                    System.out.println("Digite o preço da Embalagem Pequena:");
                    float pequenaT = 0;
                    boolean entradaValidaTT1 = false;
                    while (!entradaValidaTT1) {
                        if (scanner.hasNextFloat()) {
                            pequenaT = scanner.nextFloat();
                            entradaValidaTT1 = true;
                        } else {
                            System.out.println("Por favor, insira um valor válido.");
                            scanner.next(); // limpa a entrada inválida do usuário
                        }
                    }
                    System.out.println("Digite o preço da Embalagem Média:");
                    float mediaT = 0;
                    boolean entradaValidaTT2 = false;
                    while (!entradaValidaTT2) {
                        if (scanner.hasNextFloat()) {
                            mediaT = scanner.nextFloat();
                            entradaValidaTT2 = true;
                        } else {
                            System.out.println("Por favor, insira um valor válido.");
                            scanner.next(); // limpa a entrada inválida do usuário
                        }
                    }
                    System.out.println("Digite o preço da Embalagem Grande:");
                    float grandeT = 0;
                    boolean entradaValidaTT3 = false;
                    while (!entradaValidaTT3) {
                        if (scanner.hasNextFloat()) {
                            grandeT = scanner.nextFloat();
                            entradaValidaTT3 = true;
                        } else {
                            System.out.println("Por favor, insira um valor válido.");
                            scanner.next(); // limpa a entrada inválida do usuário
                        }
                    }
                    System.out.println("Digite o Imposto:");
                    float impostoT = 0;
                    boolean entradaValidaTT4 = false;
                    while (!entradaValidaTT4) {
                        if (scanner.hasNextFloat()) {
                            impostoT = scanner.nextFloat();
                            entradaValidaTT4 = true;
                        } else {
                            System.out.println("Por favor, insira um valor válido.");
                            scanner.next(); // limpa a entrada inválida do usuário
                        }
                    }
                    System.out.println("Digite a Margem de Lucro:");
                    float margemLucroT = 0;
                    boolean entradaValidaTT5 = false;
                    while (!entradaValidaTT5) {
                        if (scanner.hasNextFloat()) {
                            margemLucroT = scanner.nextFloat();
                            entradaValidaTT5 = true;
                        } else {
                            System.out.println("Por favor, insira um valor válido.");
                            scanner.next(); // limpa a entrada inválida do usuário
                        }
                    }
                    TRANSPORTADORA = vintage.criarTransportadora(nomeT, pequenaT, mediaT, grandeT, impostoT, margemLucroT, data);
                    System.out.println(vintage.getTransportadoras());
                    break;

                /**
                 * Opção Para avançar um dia da data
                 */
                case 4:
                    System.out.println();
                    vintage.vintageAvancarDia();
                    System.out.println("Foi avançado 1 dia");
                    break;
                
                /**
                 * Opção Para avançar para uma data pretendida
                 */
                case 5:
                    System.out.println("Por favor, insira a data para o qual pretende Avançar.");
                    System.out.println("Dia");
                    int dia = scanner.nextInt();
                    System.out.println("Mês");
                    int mes = scanner.nextInt();
                    System.out.println("Ano");
                    int ano = scanner.nextInt();
                    if (vintage.validaData(dia, mes, ano) == false) {
                        System.out.println("Data Inválida.");
                        break;
                    }
                    vintage.vintageAvancarParaData(dia, mes, ano);
                    System.out.print("A data foi mudada com sucesso.\n");
                    break;
                
                /**
                 * Opção para observar o registo de encomendas de cada utilizador
                 */
                case 6:
                    System.out.println();
                    System.out.println("Códigos disponíveis");
                    for(Utilizador u: vintageCarregado.getUtilizadores()){
                        System.out.print(u.getCodigo()+" - ");System.out.println(u.getNome());
                    }
                    System.out.println("Por favor, insira o código de utilizador.");
                    int codigo6 = scanner.nextInt();
                    System.out.println(vintage.obterRegistoEncomendas(codigo6));
                    break;

                /**
                 * Opção Para observar os maiores vendedores dada uma data de inicio e fim
                 */
                case 7:
                    System.out.println();
                    System.out.println("Por favor, insira as Datas.");
                    System.out.println("Dia de Inicio");
                    int diaI7 = scanner.nextInt();
                    System.out.println("Mês de Inicio");
                    int mesI7 = scanner.nextInt();
                    System.out.println("Ano de Inicio");
                    int anoI7 = scanner.nextInt();
                    Data dataI7 = new Data (diaI7, mesI7, anoI7);
                    if (vintage.validaData(diaI7, mesI7, anoI7) == false) {
                        System.out.println("Data Inválida.");
                        break;
                    }
                    System.out.println("Dia de Fim");
                    int diaF7 = scanner.nextInt();
                    System.out.println("Mês de Fim");
                    int mesF7 = scanner.nextInt();
                    System.out.println("Ano de Fim");
                    int anoF7 = scanner.nextInt();
                    Data dataF7 = new Data (diaF7, mesF7, anoF7);
                    if (vintage.validaData(diaF7, mesF7, anoF7) == false) {
                        System.out.println("Data Inválida.");
                        break;
                    }
                    ArrayList<Utilizador> maioresVendedores = vintage.obterMaioresVendedores(dataI7, dataF7);
                    System.out.println("Maiores Vendedores:");
                    for (Utilizador vendedor : maioresVendedores) {
                        System.out.println(vendedor.getNome());
                    }
                    break;

                /**
                 * Opção Para observar os maiores vendedores dada uma data de inicio
                 */
                case 8:
                    System.out.println();
                    System.out.println("Por favora insira a Data de inicio.");
                    System.out.println("Dia de Inicio");
                    int diaI8 = scanner.nextInt();
                    System.out.println("Mês de Inicio");
                    int mesI8 = scanner.nextInt();
                    System.out.println("Ano de Inicio");
                    int anoI8 = scanner.nextInt();
                    Data dataI8 = new Data (diaI8, mesI8, anoI8);
                    if (vintage.validaData(diaI8, mesI8, anoI8) == false) {
                        System.out.println("Data Inválida.");
                        break;
                    }
                    System.out.println(vintage.encontrarUtilizadorFaturacaoDesdeUmaData(dataI8));
                    break;

                /**
                 * Opção Para observar o maior vendedor de sempre
                 */
                case 9:
                System.out.println();
                    System.out.println(vintage.encontrarUtilizadorFaturacaoVendasDeSempre());
                    break;
                
                /**
                 * Opção Para observar os maiores compradores dada uma data de inicio e fim
                 */
                case 10:
                System.out.println();
                    System.out.println("Por favor, insira as Datas.");
                    System.out.println("Dia de Inicio");
                    int diaI9 = scanner.nextInt();
                    System.out.println("Mês de Inicio");
                    int mesI9 = scanner.nextInt();
                    System.out.println("Ano de Inicio");
                    int anoI9 = scanner.nextInt();
                    Data dataI9 = new Data (diaI9, mesI9, anoI9);
                    if (vintage.validaData(diaI9, mesI9, anoI9) == false) {
                        System.out.println("Data Inválida.");
                        break;
                    }
                    System.out.println("Dia de Fim");
                    int diaF9 = scanner.nextInt();
                    System.out.println("Mês de Fim");
                    int mesF9 = scanner.nextInt();
                    System.out.println("Ano de Fim");
                    int anoF9 = scanner.nextInt();
                    Data dataF9 = new Data (diaF9, mesF9, anoF9);
                    if (vintage.validaData(diaF9, mesF9, anoF9) == false) {
                        System.out.println("Data Inválida.");
                        break;
                    }
                    ArrayList<Utilizador> maioresCompradores = vintage.obterMaioresCompradores(dataI9, dataF9);
                    System.out.println("Maiores Compradores:");
                    for (Utilizador comprador : maioresCompradores) {
                        System.out.println(comprador.getNome());
                    }
                    break;

                /**
                 * Opção Para observar a faturação total da loja
                 */
                case 11:
                    System.out.println();
                    System.out.println("A vinted faturou um total de:");
                    float totalFaturacao = vintage.nrTotalFaturacao();
                    System.out.println(totalFaturacao);
                    System.out.println("Através de " + vintage.nrTotalVendas() + "Vendas");
                    break;
                
                /**
                 * Opção Para observar a transportadora que mais faturou de sempre
                 */
                case 12:
                    System.out.println();
                    System.out.println("A Transportadora que mais Faturou foi:");
                    Transportadora transpMaiorFaturacao = vintage.getTransportadoraMaiorFaturacao();
                    if (transpMaiorFaturacao != null) {
                        System.out.println(transpMaiorFaturacao.toString());
                    } else {
                        System.out.println("Nenhuma transportadora encontrada.");
                    }
                    break;
                
                /**
                 * Opção Para fechar o programa e guardar as alterações no ficheiro
                 * Caso não queira gravar nada tem de sair com control C
                 */
                case 0:
                    System.out.println("A Sair...");
                    // Salvar o estado no arquivo
                    gerenciador.salvarEstado(vintage);
                    break;
                }
            }   
        }
}