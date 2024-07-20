import java.io.Serializable;

/**
 * Classe Fatura que irá receber as informações das Faturações realizadas
 */
public class Fatura implements Serializable {
    private Utilizador vendedor;
    private Utilizador comprador;
    private Artigo linhaArtigos;
    private int estado; // 0 em entrega, 1 entregue, 2 devolvido
    private String data;
    
    /** 
     * Função Construtor com parametros 
     */
    public Fatura(Utilizador vendedor, Utilizador comprador, Artigo linhaArtigos, int estado, Data data) {
        this.vendedor = vendedor;
        this.comprador = comprador;
        this.linhaArtigos = linhaArtigos;
        this.estado = estado;
        this.data = data.dataformat();
    }

    /**
     * Getters e Setters da classe Fatura
     */
    public Utilizador getVendedor() {
        return vendedor;
    }

    public void setVendedor(Utilizador vendedor) {
        this.vendedor = vendedor;
    }

    public Utilizador getComprador() {
        return comprador;
    }
    
    public void setComprador(Utilizador comprador) {
        this.comprador = comprador;
    }

    public Artigo getLinhaArtigos() {
        return linhaArtigos;
    }

    public void setLinhaArtigos(Artigo linhaArtigos) {
        this.linhaArtigos = linhaArtigos;
    }
    
    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    public String getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data.dataformat();
    }
    
    /**
    * ToString que vai printar no ecrã as informações da Faturação
    */
    public String toString() {
        String estado_encomenda = "";
        switch (this.estado) {
            case 0:
                estado_encomenda = "Em Expedicao!\n";
                break;
            case 1:
                estado_encomenda = "Entregue!\n";
                break;
            case 2:
                estado_encomenda = "Devolucao Realizada!\n";
                break;
            default:
                estado_encomenda = "Estado inválido";
        }
        return "Fatura:\n" +
                "\tComprador: " + comprador.getNome() +
                "\n\tVendedor: " + vendedor.getNome() +
                "\n\tArtigo Comprado: " + linhaArtigos.getCodigo() +
                "\n\tPreco Aplicado sem Portes: " + linhaArtigos.getPrecoFinal() +
                "\n\tData: " + this.data +
                "\n\tEstado da encomenda: " + estado_encomenda;
    }
}

