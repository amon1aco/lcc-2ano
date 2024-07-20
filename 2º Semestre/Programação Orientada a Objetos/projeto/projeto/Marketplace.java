import java.util.ArrayList;
import java.io.Serializable;

/**
 * Classe Marketplace que irá tratar de mostrar todos os produtos disponiveis para venda
 */
public class Marketplace implements Serializable {
    
    private ArrayList<Artigo> produtosVenda;
    private int nr_vendas;
    private float total_arrecadado;
    
    /** 
     * Função Construtor com parametros 
     */
    public Marketplace() {
        produtosVenda = new ArrayList<Artigo>();
        this.nr_vendas = 0;
        this.total_arrecadado = 0;
    }

    /**
     * Getters e Setters da classe Marketplace
     */
    public ArrayList<Artigo> getProdutosVenda() {
        return produtosVenda;
    }

    public void setProdutosVenda(ArrayList<Artigo> produtosVenda) {
        this.produtosVenda = produtosVenda;
    }

    public void adicionarArtigo(Artigo artigo) {
        produtosVenda.add(artigo);
    }

    public void removerArtigo(Artigo artigo) {
        produtosVenda.remove(artigo);
    }
    
    public float getTotalArrecadado() {
        return total_arrecadado;
    }

    public void setTotalArrecadado(float total_arrecadado) {
        this.total_arrecadado = total_arrecadado;
    }

    public int getNrVendas() {
        return nr_vendas;
    }

    public void setNrVendas(int nr_vendas) {
        this.nr_vendas = nr_vendas;
    }

    public Artigo getArtigoPorCodigo(int codigo) {
        for (Artigo artigo : produtosVenda) {
            if (artigo.getCodigo() == codigo) {
                return artigo;
            }
        }
        return null; // Retorna nulo se nenhum artigo com o código informado for encontrado
    }
    
    /** 
     * Funções auxiliares 
     */

    /**
     * Quando adicionado algo para venda aumenta o valor arrecadado
     */
    public void adicionarVenda(float valor) {
        this.total_arrecadado += valor;
        this.nr_vendas++;
    }

    /**
     * Quando removido algo de venda decrementa o valor arrecadado
     */
    public void removerVenda(float valor) {
        this.total_arrecadado -= valor;
        this.nr_vendas--;
    }
    
    /** 
     * ToString que irá printar todos os artigos disponiveis no Marketplace(para venda)
     */
    public String toString() {
        String result = "Produtos à venda: \n";
        for (Artigo artigo : produtosVenda) {
            result += artigo.toString() + "\n";
        }
        return result;
    }
}
