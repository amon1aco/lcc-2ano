import java.util.Map;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.Serializable;

/** 
 * Classe Transportadora que define as Transportadoras e recebe parametros para tal
 */
public class Transportadora implements Serializable {
    
    private String nome;
    private float valorPequena;
    private float valorMedia;
    private float valorGrande;
    private float imposto;
    private float margemLucro;
    private Data data;
    private boolean processada = false;
    private Map <String,ArrayList<ArtigoComprado>> entregas;
    private float faturacao;
    
    /** 
     * Função Construtor com parametros 
     */
    public Transportadora(String nome, float valorPequena, float valorMedia, float valorGrande, float imposto, float margemLucro, Data data) {
        this.nome = nome;
        this.valorPequena = valorPequena;
        this.valorMedia = valorMedia;
        this.valorGrande = valorGrande;
        this.imposto = imposto;
        this.margemLucro = margemLucro;
        this.data = data;
        this.entregas = new HashMap<>();
        this.faturacao = 0;
    }

    DecimalFormat df = new DecimalFormat("#.00");

    /**
     * Getters e Setters da classe Transportadora
     */
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public float getValorPequena() {
        return valorPequena;
    }
    
    public void setValorBasePequena(float valorPequena) {
        this.valorPequena = valorPequena;
    }
    
    public float getValorMedia() {
        return valorMedia;
    }
    
    public void setValorBaseMedia(float valorMedia) {
        this.valorMedia = valorMedia;
    }
    
    public float getValorGrande() {
        return valorGrande;
    }
    
    public void setValorGrande(float valorGrande) {
        this.valorGrande = valorGrande;
    }
    
    public float getImposto() {
        return imposto;
    }
    
    public void setImposto(float imposto) {
        this.imposto = imposto;
    }
    
    public float getMargemLucro() {
        return margemLucro;
    }
    
    public void setMargemLucro(float margemLucro) {
        this.margemLucro = margemLucro;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public float getFaturacao() {
        return faturacao;
    }
    
    public void setFaturacao(float faturacao) {
        this.faturacao = faturacao;
    }

    public void addFaturacao(float faturacao) {
        this.faturacao += faturacao;
    }

    public boolean isProcessada() {
        return processada;
    }

    public void setProcessada(boolean processada) {
        this.processada = processada;
    }
    
    /** 
     * Funções auxiliares 
     */
    public void adicionarEntrega(String data, ArrayList<ArtigoComprado> artigosComprados) {
        entregas.put(data, artigosComprados);
    }

    public void removerEntrega(String data) {
        entregas.remove(data);
    }

    public void inicializarEntregas(String data) {
        entregas.put(data, new ArrayList<ArtigoComprado>());
    }
    
    public void adicionarEntrega2(String data, ArtigoComprado artigo) {
        ArrayList<ArtigoComprado> entregasData = entregas.get(data);
        if (entregasData == null) {
            entregasData = new ArrayList<ArtigoComprado>();
            entregas.put(data, entregasData);
        }
        entregasData.add(artigo);
    }  

    /** 
     * Função que calcula o preço de Expedição, recebendo a quantidade de artigos e tendo em conta outros fatores 
     */
    public float PrecoExpedicao(int quantidadeArtigos){
        float val;
        if (quantidadeArtigos == 0) return 0;
        if (quantidadeArtigos == 1) {
            val = valorPequena;
        } else if (quantidadeArtigos >= 2 && quantidadeArtigos <= 5) {
            val = valorMedia;
        } else {
            val = valorGrande;
        }
        float precoSemImposto = val + margemLucro;
        float precoComImposto = precoSemImposto * (1 + imposto);
        addFaturacao(precoComImposto);
        return precoComImposto;
    }

    /** 
     * Função que tendo a transportadora irá fazer a entrega da encomenda já tendo em conta todos os fatores com preços...
     */
    public void realizarEntrega() {     
        ArrayList<ArtigoComprado> artigosVendidos = entregas.get(data.dataformat());
        if (artigosVendidos != null) {
            for (ArtigoComprado artigoComprado : artigosVendidos) {
                Artigo artigo = artigoComprado.getArtigo();
                Utilizador vendedor = artigoComprado.getVendedor();
                vendedor.registarVenda(artigo);
                vendedor.addValorVendas(artigo.getPrecoNoDesc());
                Utilizador comprador = artigoComprado.getComprador();
                comprador.addProdutoCompra(artigo);
               // comprador.addValorCompras(artigo.getPrecoFinal());
                System.out.println("Encomenda Entregue com sucesso!");
                // Ir ao utilizador buscar a respectiva fatura e passar o estado pra 1
                for (Fatura fatura : vendedor.getFaturas()) {
                    if (fatura.getLinhaArtigos().equals(artigo)) {
                        fatura.setEstado(1);
                        break; 
                    }
                }
            }
        }
    }
    
    /** 
     * ToString que irá printar todas as informações da classe Transportadora e das entregas feitas por transportadora
     */
    public String toString() {
        String entregasStr = "Entregas:\n";
        for (Map.Entry<String, ArrayList<ArtigoComprado>> entry : entregas.entrySet()) {
            entregasStr += "Data de Entrega: " + entry.getKey() + ", Artigos: " + entry.getValue() + "\n";
        }
        return "Transportadora: " + this.nome +"\n" +
                "\nValor Entrega Pequena: " + this.valorPequena +
                "\nValor Entrega Media: " + this.valorMedia +
                "\nValor Entrega Grande: " + this.valorGrande +
                "\nImposto: " + this.imposto +
                "\nMargem de Lucro: " + this.margemLucro +
                "\nFaturacao total: " + this.df.format(faturacao) +
                "\nProdutos em Entrega:\n" + entregasStr;
    }
}
