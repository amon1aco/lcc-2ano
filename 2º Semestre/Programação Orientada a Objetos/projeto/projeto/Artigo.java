import java.text.DecimalFormat;
import java.io.Serializable;

/** 
 * Classe Artigo Abstrata, ou seja não é possivel invocar um artigo, um artigo obrigatóriamente tem de ser uma classe filho dos artigos 
 */
public abstract class Artigo implements Serializable { 
    protected float precoBase; // Preco atribuido pelo vendedor
    protected float precoNoDesc; // Preco sem desconto
    protected float precoFinal; // Preco após taxas
    protected float taxas; // Taxa vinted
    private Utilizador vendedor;
    private int nrDonos;
    private String estado;  // Apenas acedido se nrDonos > 0
    private static int codigo = 100;   
    private final int codigobarras; // Codigo de barras unico para cada artigo
    private String marca;
    private Transportadora transp;  // Transportadora por onde o artigo irá ser enviado

    /** 
     * Função Construtor com parametros 
     */
    public Artigo(float precobase, int nrDonos, String estado, String marca, Utilizador vendedor,Transportadora transp){
        this.precoBase = precobase;
        this.vendedor = vendedor;
        this.nrDonos = nrDonos;
        this.estado = estado;
        this.marca = marca;
        this.transp = transp;
        this.codigobarras = codigo;
        codigo++;
        this.precoNoDesc = 0;
        this.precoFinal = 0;
        this.taxas = 0;
    }

    DecimalFormat df = new DecimalFormat("#.00");
    
    /**
     * Getters e Setters da classe Artigo
     */
    
    public float getPrecoBase() {
        return this.precoBase;
    }

    public void setPrecoBase(float precoBase) {
        this.precoBase = precoBase;
    }

    public Utilizador getVendedor() {
        return vendedor;
    }

    public void setVendedor(Utilizador vendedor) {
        this.vendedor = vendedor;
    }

    public int getNrDonos() {
        return nrDonos;
    }

    public void setNrDonos(int nrDonos) {
        this.nrDonos = nrDonos;
    }

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getCodigo() {
        return this.codigobarras;
    }

    /**
     * Nao ha setCodigo porque é inalterável.
     */

    public String getMarca() {
        return this.marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Transportadora getTransportadora() {
        return this.transp;
    }

    public void setTransportadora(Transportadora transp) {
        this.transp = transp;
    }

    public float getPrecoNoDesc() {
        return precoNoDesc;
    }

    public void setPrecoNoDesc(float precoNoDesc) {
        this.precoNoDesc = precoNoDesc;
    }

    public float getPrecoFinal() {
        return precoFinal;
    }

    public void setPrecoFinal(float precoFinal) {
        this.precoFinal = precoFinal;
    }

    public float getTaxas() {
        return taxas;
    }

    public void setTaxas(float taxas) {
        this.taxas = taxas;
    }

    /** 
     * Funções auxiliares 
     */
    public void incrNrDonos() {
        this.nrDonos++;
    }

    public boolean isNovo() {
        return nrDonos == 0;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Artigo) {
            Artigo artigo = (Artigo) obj;
            return this.codigobarras == artigo.getCodigo();
        }
        return false;
    }
}