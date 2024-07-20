/** 
 * Classe Sapatilhas que define outro tipo de artigos que são as sapatilhas que recebe todos os parametros da classe artigo e ainda recebe alguns parametros expecificos
 */
public class Sapatilhas extends Artigo {
    private int tamanho;
    private boolean caracteristicas; // True = atacadores / false = atilhos
    private String cor;
    private float desconto;
    private int ano;

    /** 
     * Função Construtor com parametros 
     */
    public Sapatilhas(float preco, int nrDonos, String estado, String marca, int ano, int tamanho, float desconto, boolean caracteristicas, String cor, Utilizador vendedor,Transportadora transp) {
        super(preco, nrDonos, estado, marca, vendedor,transp);
        this.tamanho = tamanho;
        this.caracteristicas = caracteristicas;
        this.cor = cor;
        this.desconto = desconto();

        this.precoNoDesc = this.precoBase - this.desconto;
        this.taxas = 1 + (this.precoNoDesc * 0.05f);
        this.precoFinal = this.precoNoDesc + this.taxas;
    }

    /** 
     * Função que calcula o desconto expecifico para as sapatilhas
     */
    public float desconto() {
        desconto = 0;

        if (this.getNrDonos() > 0 && this.getTamanho() > 45) {
        desconto = (this.precoBase / 10 + 5f);
        }                                                 
        else if (this.getNrDonos() > 0 && this.getTamanho() <= 45) {
            desconto = (this.precoBase / 10); 
        }                                                            
        else if (this.getNrDonos() == 0 && this.getTamanho() > 45) {
            desconto = 5;
        }                                                      
        return desconto;
    }

    /**
     * Getters e Setters da classe Malas
     */
    public float getDesconto(){
        return this.desconto;
    }

    public void setDesconto(float desc){
        this.desconto = desc;
    } 

    public int getAno() {
        return this.ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getTamanho() {
        return this.tamanho;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public boolean getCaracteristicas() {
        return this.caracteristicas;
    }

    public void setCaracteristicas(boolean caracteristicas) {
        this.caracteristicas = caracteristicas;
    } 

    public boolean isCaracteristicas() {
        return this.caracteristicas;
    }

    public String getCor() {
        return this.cor;
    }

    public void setCor(String cor) {
        this.setCor(cor);
    }

    public boolean isNovo(){
        if(super.getNrDonos() == 0) return true;
        return false;
    }

    /** 
     * ToString que irá printar todas as informações da classe Sapatilhas
     */
    public String toString() {
        String caracteristicasStr;
        if(this.getCaracteristicas()){
            caracteristicasStr = "Com atacadores";
        }
        else{
            caracteristicasStr = "Sem atacadores";
        }
        return "Sapatilhas:\n\t" +
        "Preco Base: " + df.format(getPrecoBase()) +
        "\n\tPreco com Desconto: " + (this.getPrecoBase() - desconto()) +
        "\n\tPreco Final: " + df.format(getPrecoFinal()) +
        "\n\tTaxas: " + df.format(taxas) +
        "\n\tTamanho: " + this.getTamanho() +
        "\n\tCaracteristicas: " + caracteristicasStr +
        "\n\tCor: " + this.getCor() +
        "\n\tEstado: " + super.getEstado() +
        "\n\tMarca: " + super.getMarca() +
        "\n\tAno: " + this.getAno() +
        "\n\tNrDonos: " + super.getNrDonos() +
        "\n\tDesc: " + df.format(this.getDesconto()) +
        "\n\tCodigo: " + super.getCodigo();
    }
}



