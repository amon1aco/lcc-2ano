import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.Serializable;

/**
 * Classe Encomenda que irá gerar uma encomenda tendo em conta a lista de Artigos que receber, a dimensão da embalagem e o calculo do preço
 */
public class Encomenda implements Serializable {
    private ArrayList<Artigo> artigos;
    private int dimensaoEmbalagem;
    private float precoFinal;

    /** 
     * Função Construtor com parametros 
     */
    public Encomenda() {
        this.artigos = new ArrayList<>();
        this.dimensaoEmbalagem = 0;      
        this.precoFinal = 0;
    }

    DecimalFormat df = new DecimalFormat("#.00");

    /**
     * Get e Set do Preço Final
     */
    public float getPrecoFinal() {
        return precoFinal;
    }

    public void setPrecoFinal(float precoFinal) {
        this.precoFinal = precoFinal;
    }

    /** 
     * Função que irá receber um artigo e adicioná-lo à lista criada acima, incrementa a dimensão da embalagem e junta o preço do artigo ao preço que já tinha
     */
    public void adicionarArtigo(Artigo artigo) {
        this.artigos.add(artigo);
        this.dimensaoEmbalagem++;
        this.precoFinal += artigo.getPrecoFinal();
    }

    /** 
     * Função getArtigos que irá retornar todos os artigos presentes na lista
     */
    public ArrayList<Artigo> getArtigos() {
        return artigos;
    }

    /** 
     * Função que irá receber a transportadora, os artigos e tendo em conta os vários fatores presentes na transportadora irá calcular o preço final da encomenda
     */
    public float calcularPrecoFinal(HashMap<Transportadora, Integer> nrPorTransp) {
        float precoArtigos = 0;
        for (Artigo artigo : artigos) {
            if (artigo.isNovo()) {
                precoArtigos += artigo.getPrecoFinal() + 0.5;
            } else {
                precoArtigos += artigo.getPrecoFinal() + 0.25;
            }
        }
        float precoExpedicao = 0;
        for (Transportadora t : nrPorTransp.keySet()) {
            int nrEntregas = nrPorTransp.get(t);
            float preco = t.PrecoExpedicao(nrEntregas);
            precoExpedicao += preco;
        }
        this.precoFinal = precoArtigos + precoExpedicao;
        return this.precoFinal;
    }

    /**
    * ToString que vai printar no ecrã as informações da Encomenda
    */
    public String toString() {
        String output = "Encomenda: \n";
        output += "\n\tDimensao embalagem: " + this.dimensaoEmbalagem;
        output += "\n\tPreco final: " + df.format(this.precoFinal);
        output += "\n\tArtigos:";
        for (Artigo a : artigos) {
            output += "\n\t\t" + a.getCodigo();
        }
        return output;
    }
}