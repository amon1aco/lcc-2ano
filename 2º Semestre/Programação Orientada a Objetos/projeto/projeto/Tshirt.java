/** 
 * Classe Tshirt que define o ultimo tipo de artigos que são as Tshirts que recebe todos os parametros da classe artigo e ainda recebe alguns parametros expecificos
 */
public class Tshirt extends Artigo {
    private String tamanho; // S, M, L, XL, etc.
    private String padrao;
    private float desconto;

    /** 
     * Função Construtor com parametros 
     */
    public Tshirt(float preco, int nrDonos, String estado, String marca, float desconto, String tamanho, String padrao, Utilizador vendedor, Transportadora transp) {
        super(preco, nrDonos, estado, marca, vendedor, transp);
        this.tamanho = tamanho;
        this.padrao = padrao;
        this.desconto = desconto();

        this.precoNoDesc = this.precoBase - this.desconto;
        this.taxas = 1 + (this.precoNoDesc * 0.05f);
        this.precoFinal = this.precoNoDesc + this.taxas;
    }

    /** 
     * Função que calcula o desconto expecifico para as tshirts
     */
    public float desconto() {
        if (this.padrao.equals("liso")) {
            return 0.0f;
        }
        if (super.getNrDonos() > 0 && !this.padrao.equals("liso")) {
            return super.getPrecoBase()/2;
        }
        return 0f;
    }

    /**
     * Getters e Setters da classe Tshirt
     */
    public float getDesconto(){
        return this.desconto;
    }
        
    public void setDesconto(float desconto){
        this.desconto = desconto;
    }

    public String getTamanho() {
        return this.tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public String getPadrao() {
        return this.padrao;
    }

    public void setPadrao(String padrao) {
        this.padrao = padrao;
    }    

    public boolean isNovo() {
        return (super.getNrDonos() == 0);
    }

    /** 
     * ToString que irá printar todas as informações da classe Tshirt
     */
    public String toString() {
        return "Tshirt: " +
            "\n\tPreco Base: " + df.format(getPrecoBase()) +
            "\n\tPreco com Desconto: " + (this.getPrecoBase() - desconto()) +
            "\n\tPreco Final: " + df.format(getPrecoFinal()) +
            "\n\tTaxas: " + df.format(taxas) +
            "\n\tTamanho: " + this.getTamanho() +
            "\n\tPadrao: " + this.getPadrao() +
            "\n\tCondicao: " + this.isNovo() +
            "\n\tEstado: " + super.getEstado() +
            "\n\tMarca: " + super.getMarca() +
            "\n\tNrDonos: " + super.getNrDonos() +
            "\n\tDesc: " + df.format(this.getDesconto()) +
            "\n\tCodigo: " + super.getCodigo();
    }
}





