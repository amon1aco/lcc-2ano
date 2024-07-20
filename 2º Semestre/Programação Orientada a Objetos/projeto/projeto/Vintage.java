import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * Clasee Vintage responsavel por todas as criações novas, seja utilizador, transportadora ou artigos
 */
public class Vintage implements Serializable{
    private ArrayList<Utilizador> utilizadores = new ArrayList<>();
    private ArrayList<Artigo> artigos = new ArrayList<>();
    private ArrayList<Integer> numeros = new ArrayList<>();
    private List<Integer> codigosDisponiveis = new ArrayList<>();
    private Data data = new Data();
    private Marketplace marketplace = new Marketplace();
    private ArrayList<Transportadora> transportadoras = new ArrayList<>(); 
    private HashMap<Integer, Utilizador> mapaUtilizadores;
    private ArrayList<Integer> cod = new ArrayList<>();
    private int counter;

    /** 
     * Função Construtor com parametros 
     */
    public Vintage(){
        this.utilizadores = new ArrayList<>();
        this.artigos = new ArrayList<>();
        this.numeros = new ArrayList<>();
        this.data = new Data();
        this.mapaUtilizadores = new HashMap<Integer, Utilizador>();
        this.cod = new ArrayList<>();
        this.counter = 100;
    }

    /** 
     * Função Construtor com parametros dada uma data
     */
    public Vintage(Data data){
        this.utilizadores = new ArrayList<>();
        this.artigos = new ArrayList<>();
        this.numeros = new ArrayList<>();
        this.data = data;
        this.mapaUtilizadores = new HashMap<Integer, Utilizador>();
        this.cod = new ArrayList<>();
        this.counter = 100;
        
        for (Transportadora transportadora : transportadoras) {
            transportadora.setData(data);
        }
    }

    /**
     * função que irá criar um novo utilizador
     */
    public Utilizador criarUtilizador(String mail ,String nome, String morada, int nrFiscal) {
        int codigo;
        while (cod.contains(counter)) {
            counter++;
        }
        codigo = counter;
        counter++;
        Utilizador utilizador = new Utilizador(mail, nome ,morada, nrFiscal, marketplace, data, codigo);
        this.utilizadores.add(utilizador);
        return utilizador;
    }
    
    /**
     * função que irá criar umas novas sapatilhas
     */
    public Sapatilhas criarSapatilha(float preco, int nrDonos, String estado, String marca, int ano, int tamanho, 
                                     float desconto, boolean caracteristicas, String cor, Utilizador vendedor,Transportadora transp){
       Sapatilhas sapatilha = new Sapatilhas(preco, nrDonos, estado, marca, ano, tamanho, desconto, caracteristicas, cor, vendedor,transp);
        this.artigos.add(sapatilha);
        return sapatilha;
    }
    
    /**
     * função que irá criar uma nova mala
     */
    public Malas criarMala(float preco, int nrDonos, String estado, String marca, float desconto, int ano, 
                           String material, int volume, Utilizador vendedor, Transportadora transp){
        Malas mala = new Malas(preco, nrDonos, estado, marca, desconto, ano, volume, material, vendedor,transp);
        this.artigos.add(mala);
        return mala;
    }

    /**
     * função que irá criar uma nova t-shirt
     */
    public Tshirt criarTshirt(float preco, int nrDonos, String estado, String marca, float desconto, 
                              String tamanho, String padrao, Utilizador vendedor, Transportadora transp){
        Tshirt tshirt = new Tshirt(preco, nrDonos, estado, marca, desconto, tamanho, padrao, vendedor, transp);
        this.artigos.add(tshirt);
        return tshirt;
    }

    /**
     * função que irá criar uma nova transportadora
     */
    public Transportadora criarTransportadora(String nome, float valorPequena, float valorMedia, float valorGrande, float imposto, float margemLucro, Data data){
        Transportadora transp = new Transportadora(nome,valorPequena,valorMedia,valorGrande,imposto,margemLucro,data);
        this.addTransportadora(transp);
        return transp;
    }
    
    /**
     * função que irá remover um utilizador
     */
    public void removerUtilizador(int codigo) {
        try{
            this.utilizadores.remove(codigo-100);//como eu quero que ele comece no numero 100 
            //e a minha lista so tem tamanho 5 entao tenho de fazer o por exemplo 101-100 para me remover o codigo 101...
            codigosDisponiveis.add(codigo);
            Collections.sort(codigosDisponiveis);
        } catch (IndexOutOfBoundsException e){
            System.out.println("\n\tNão há utilizadores para remover!! \n");
        }
    }
    
    /**
     * função que irá remover um artigo
     */
    public void removerArtigo(int codigo) {
        try {
            this.artigos.remove(codigo-100);//como eu quero que ele comece no numero 100 
            //e a minha lista so tem tamanho 5 entao tenho de fazer o por exemplo 101-100 para me remover o codigo 101...
            codigosDisponiveis.add(codigo);
            Collections.sort(codigosDisponiveis);
        } catch (IndexOutOfBoundsException e){
            System.out.println("\n\tNÃO há artigos para remover!! \n");
        }
    }

    /**
     * Getters e Setters da classe Vintage
     */
    public ArrayList<Utilizador> getUtilizadores() {
        return this.utilizadores;
    }

    public void setUtilizadores(ArrayList<Utilizador> utilizadores) {
        this.utilizadores = utilizadores;
    }

    public ArrayList<Artigo> getArtigos() {
        return this.artigos;
    }

    public void setArtigos(ArrayList<Artigo> artigos) {
        this.artigos = artigos;
    }

    public ArrayList<Integer> getNumeros() {
        return this.numeros;
    }

    public void setNumeros(ArrayList<Integer> numeros) {
        this.numeros = numeros;
    }

    public List<Integer> getCodigosDisponiveis() {
        return this.codigosDisponiveis;
    }

    public void setCodigosDisponiveis(List<Integer> codigosDisponiveis) {
        this.codigosDisponiveis = codigosDisponiveis;
    }

    public Data getData() {
        return this.data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Marketplace getMarketplace() {
        return this.marketplace;
    }

    public void setMarketplace(Marketplace marketplace) {
        this.marketplace = marketplace;
    }

    public ArrayList<Transportadora> getTransportadoras() {
        return transportadoras;
    }

    public void setTransportadoras(ArrayList<Transportadora> transportadoras) {
        this.transportadoras = transportadoras;
    }

    public Transportadora getTransportadora(String nomeTransportadora) {
        for (Transportadora transportadora : transportadoras) {
            if (transportadora.getNome().equals(nomeTransportadora)) {
                return transportadora;
            }
        }
        return null;
    }

    public void addTransportadora(Transportadora transportadora){
        transportadora.setData(data);
        transportadoras.add(transportadora);
    }

    public void removeTransportadora(Transportadora transportadora){
        transportadoras.remove(transportadora);
    }

    public HashMap<Integer, Utilizador> getMapaUtilizadores() {
        return mapaUtilizadores;
    }
    
    public void setMapaUtilizadores(HashMap<Integer, Utilizador> mapaUtilizadores) {
        this.mapaUtilizadores = mapaUtilizadores;
    }

    public Utilizador getUtilizadorByKey(int key) {
        return mapaUtilizadores.get(key);
    }
    
    public void addUtilizadorMapa(int senha, Utilizador utilizador){
        this.mapaUtilizadores.put(senha, utilizador);
    }
    
    public void removeUtilizadorMapa(int senha){
        this.mapaUtilizadores.remove(senha);
    }

    /**
     * Função que irá retornar o utilizador que mais faturou
     */
    public Utilizador geUtilizadorMaisFaturou(){        
        Utilizador utilizadorMaisFaturou = null;        
        float maisFaturou = 0;        
        for(Utilizador utilizador : utilizadores){            
            float faturacao = utilizador.getFaturamentoTotal();            
            if(faturacao > maisFaturou){                
                maisFaturou = faturacao;                
                utilizadorMaisFaturou = utilizador;            
            }        
        }        
        return utilizadorMaisFaturou;    
    }
    
    /**
     * Função que irá avançar a data num dia
     */
    public void vintageAvancarDia(){
        data.avancarDia(); 
        for (Transportadora transportadora : transportadoras) {
            transportadora.realizarEntrega();
        }
    }
    
    /**
     * Função que irá avançar a data para uma data em expecifico
     */
    public void vintageAvancarParaData(int dia, int mes, int ano){
        Data data_recebida = new Data(dia, mes, ano);
        if(data_recebida.dataValida(data)){
            while(!this.data.equals(data_recebida)){
                vintageAvancarDia();
            }
        } else {
            System.out.println("Data invalida");
        }
    }

    /**
     * Função que irá validar uma dada data
     */
    public boolean validaData(int dia, int mes, int ano) {
        boolean validaData = true;       
        // Verificar se o mês está dentro do intervalo válido
        if (mes < 1 || mes > 12) validaData = false;
        // Verificar se o dia está dentro do intervalo válido
        else if (dia < 1 || dia > 31) validaData = false;
        // Verificar se o dia está dentro do intervalo válido para o mês específico
        else if ((mes == 4 || mes == 6 || mes == 9 || mes == 11) && dia > 30) validaData = false;
             else if (mes == 2 && mes > 28) {
                    validaData = false;
        }
        return validaData;
    }
    
    /**
     * Função que irá retornar o utilizador que mais faturou de sempre
     */
    public Utilizador encontrarUtilizadorFaturacaoVendasDeSempre() {
        Utilizador utilizadorComMaisVendas = null;
        float maiorValorVendas = 0;
        for (Utilizador utilizador : utilizadores) {
            if (utilizador.getValorVendas() > maiorValorVendas) {
                maiorValorVendas = utilizador.getValorVendas();
                utilizadorComMaisVendas = utilizador;
            }
        }
        if (utilizadorComMaisVendas != null) {
            System.out.println("Utilizador com mais vendas: ");
            System.out.println("Código: " + utilizadorComMaisVendas.getCodigo());
            System.out.println("Nome: " + utilizadorComMaisVendas.getNome());
        } else {
            System.out.println("Não há utilizadores com vendas registradas.");
        }
        return utilizadorComMaisVendas;
    }

    /**
     * Função que irá retornar o utilizador que mais faturou desde uma dada data
     */
    public Utilizador encontrarUtilizadorFaturacaoDesdeUmaData(Data data) {
        Utilizador utilizadorComMaiorFaturacao = null;
        float maiorFaturacao = 0;
        // Percorrer todos os utilizadores
        for (Utilizador utilizador : utilizadores) {
            float faturacao = 0;
            // Percorrer as faturas do utilizador
            for (Fatura fatura : utilizador.getFaturas()) {
                // Verificar se a data da fatura está dentro do intervalo desejado
                //if (fatura.getData().compareTo(data.dataformat()) <= 0) {
                    if(data.compararDatasString(fatura.getData()) <= 0){
                    // Verificar se o utilizador é o vendedor da fatura
                    if (fatura.getVendedor().equals(utilizador)) {
                        // Adicionar o valor da fatura à faturação do utilizador
                        faturacao += fatura.getLinhaArtigos().getPrecoNoDesc();
                    }
                }
            }
            // Verificar se a faturação do utilizador é a maior até agora
            if (faturacao > maiorFaturacao) {
                maiorFaturacao = faturacao;
                utilizadorComMaiorFaturacao = utilizador;
            }
        } 
        return utilizadorComMaiorFaturacao;
    }
    
    public int nrTotalVendas(){ // Da vinted
        return marketplace.getNrVendas();
    }

    public float nrTotalFaturacao(){ // Da vinted
        return marketplace.getTotalArrecadado();
    }

    /**
     * Função que irá retornar a transportadora que mais faturou
     */
    public Transportadora getTransportadoraMaiorFaturacao() {
        Transportadora transpMaiorFaturacao = null;
        float maiorFaturacao = 0;
        for (Transportadora transp : transportadoras) {
            if(transp.getFaturacao() > maiorFaturacao) {
            //    System.out.println("AAAAAA: " + transp);
                maiorFaturacao = transp.getFaturacao();
                transpMaiorFaturacao = transp;
            }
        }
        return transpMaiorFaturacao;
    }

    /**
     * Função que irá retornar o registo de encomendas por utilizador
     */
    public String obterRegistoEncomendas(int codigo) {
        for (Utilizador pessoa : utilizadores) {
            if(pessoa.getCodigo() == codigo) {
                HashMap<String, Encomenda> registoEncomendas = pessoa.getRegistoEncomendas();
                String resultado = "Registo de Encomendas:\n";
                for (Map.Entry<String, Encomenda> entry : registoEncomendas.entrySet()) {
                    String data = entry.getKey();
                    Encomenda encomenda = entry.getValue();
                    resultado += "Data: " + data + ", " + encomenda.toString() + "\n";
                }
                return resultado;
            }
        }
        return "Não foi encontrado nenhum vendedor com o código fornecido.";
    } 

    /**
     * Função que irá retornar os utilizadores por ordem de maior vendedor para menor vendedor dado um intervalo de datas
     */
    public ArrayList<Utilizador> obterMaioresVendedores(Data dataInicial, Data dataFinal) {
        Map<Utilizador, Float> faturacaoPorUtilizador = new HashMap<>();
        
        // Percorrer todos os utilizadores
        for (Utilizador utilizador : utilizadores) {
            float faturacao = 0;
            // Percorrer as faturas do utilizador
            for (Fatura fatura : utilizador.getFaturas()) {
                // Verificar se a data da fatura está dentro do intervalo desejado
                if (dataInicial.compararDatasString(fatura.getData()) >= 0 && dataFinal.compararDatasString(fatura.getData()) <= 0) {
                    // Verificar se o utilizador é o vendedor da fatura
                    if (fatura.getVendedor().equals(utilizador)) {
                        // Adicionar o valor da fatura à faturação do utilizador
                        faturacao += fatura.getLinhaArtigos().precoFinal;  
                    }
                }
            }
            // Armazenar a faturação do utilizador no Map
            faturacaoPorUtilizador.put(utilizador, faturacao);
        }
        // Criar uma lista dos utilizadores ordenados por faturação (ordem decrescente)
        ArrayList<Utilizador> maioresVendedores = new ArrayList<>(faturacaoPorUtilizador.keySet());
        for (int i = 0; i < maioresVendedores.size() - 1; i++) {
            for (int j = i + 1; j < maioresVendedores.size(); j++) {
                Utilizador vendedor1 = maioresVendedores.get(i);
                Utilizador vendedor2 = maioresVendedores.get(j);
                // Comparar as faturações dos vendedores
                float faturacao1 = faturacaoPorUtilizador.get(vendedor1);
                float faturacao2 = faturacaoPorUtilizador.get(vendedor2);
                // Se a faturação do vendedor2 for maior, trocar as posições
                if (faturacao2 > faturacao1) {
                    maioresVendedores.set(i, vendedor2);
                    maioresVendedores.set(j, vendedor1);
                }
            }
        }
        return maioresVendedores;
    }

    /**
     * Função que irá retornar os utilizadores por ordem de maior comprador para menor comprador dado um intervalo de datas
     */
    public ArrayList<Utilizador> obterMaioresCompradores(Data dataInicial, Data dataFinal) {
        Map<Utilizador, Float> gastoPorUtilizador = new HashMap<>();
        // Percorrer todos os utilizadores
        for (Utilizador utilizador : utilizadores) {
            float gasto = 0;
            // Percorrer as faturas do utilizador
            for (Fatura fatura : utilizador.getFaturas()) {
                // Verificar se a data da fatura está dentro do intervalo desejado
                if (dataInicial.compararDatasString(fatura.getData()) >= 0 && dataFinal.compararDatasString(fatura.getData()) <= 0) {
                    // Verificar se o utilizador é o comprador da fatura
                    if (fatura.getComprador().equals(utilizador)) {
                        // Adicionar o valor da fatura ao gasto do utilizador
                        gasto += fatura.getLinhaArtigos().getPrecoFinal();
                    }
                }
            }
            // Armazenar o gasto do utilizador no Map
            gastoPorUtilizador.put(utilizador, gasto);
        }
        // Criar uma lista dos utilizadores
        ArrayList<Utilizador> maioresCompradores = new ArrayList<>(gastoPorUtilizador.keySet());
        // Ordenar a lista de maiores compradores manualmente
        for (int i = 0; i < maioresCompradores.size() - 1; i++) {
            for (int j = i + 1; j < maioresCompradores.size(); j++) {
                Utilizador comprador1 = maioresCompradores.get(i);
                Utilizador comprador2 = maioresCompradores.get(j);
                // Comparar os gastos dos compradores
                float gasto1 = gastoPorUtilizador.get(comprador1);
                float gasto2 = gastoPorUtilizador.get(comprador2);
                // Se o gasto do comprador2 for maior, trocar as posições
                if (gasto2 > gasto1) {
                    maioresCompradores.set(i, comprador2);
                    maioresCompradores.set(j, comprador1);
                }
            }
        }
        return maioresCompradores;
    }
}