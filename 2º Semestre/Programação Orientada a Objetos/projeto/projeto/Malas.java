/** 
 * Classe Malas que define um tipo de artigos que são as malas que recebe todos os parametros da classe artigo e ainda recebe alguns parametros expecificos
 */
public class Malas extends Artigo {
    private int volume;
    private String material;
    private float desconto;
    private int ano;
    
    /** 
     * Função Construtor com parametros 
     */
    public Malas(float preco, int nrDonos, String estado, String marca, float desconto, int ano, int volume, String material, Utilizador vendedor, Transportadora transp) {
        super(preco, nrDonos, estado, marca, vendedor,transp);
        this.volume = volume;
        this.material = material;
        this.desconto = desconto();

        this.precoNoDesc = this.precoBase - this.desconto;
        this.taxas = 1 + (this.precoNoDesc * 0.05f);
        this.precoFinal = this.precoNoDesc + this.taxas;
    }
    
    /** 
     * Função que calcula o desconto expecifico para as malas
     */
    public float desconto() {        
        desconto = 0;    

        if(super.getNrDonos() > 0) desconto = (this.getPrecoBase() / this.getVolume());  
        else this.desconto = 0;
        if (desconto > 30.0f) desconto = 30.0f;
        this.setDesc(desconto);

        return desconto;    
    }

    /**
     * Getters e Setters da classe Malas
     */
    public int getVolume() {
        return this.volume;
    }

    public int getAno() {
        return this.ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getMaterial() {
        return this.material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public boolean isNovo(){
        if(super.getNrDonos() > 0) return true;
        return false;
    }

    public float getDesc(){
        return this.desconto;
    }
    
    public void setDesc(float desconto){
        this.desconto = desconto;
    }    
    
    /** 
     * ToString que irá printar todas as informações da classe Malas
     */
    public String toString(){       
        return "Malas: " + 
        "\n\tPreco Base: " + df.format(getPrecoBase()) +
        "\n\tPreco com Desconto: " + (this.getPrecoBase() - desconto()) +
        "\n\tPreco Final: " + df.format(getPrecoFinal()) +
        "\n\tTaxas: " + df.format(taxas) +
        "\n\tNrDonos: " + super.getNrDonos() +
        "\n\tEstado: " + super.getEstado() +
        "\n\tMarca: " + super.getMarca() +
        "\n\tDesconto: " + df.format(this.getDesc()) +
        "\n\tAno: " + this.getAno() + 
        "\n\tDimensões: " + this.getVolume() + 
        "\n\tMaterial: " + this.getMaterial() +
        "\n\tCodigo: " + this.getCodigo();
    }
}




