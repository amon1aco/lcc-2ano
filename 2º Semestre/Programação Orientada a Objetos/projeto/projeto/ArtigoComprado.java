import java.io.Serializable;

/**
 * Classe ArtigoComprado que irá receber as informações dos Artigos que já foram comprados
 */
public class ArtigoComprado implements Serializable {
   private Utilizador vendedor;
   private Utilizador comprador;
   private Artigo artigo;
   
   /** 
     * Função Construtor com parametros 
     */
   public ArtigoComprado(Utilizador vendedor, Utilizador comprador, Artigo artigo) {
      this.vendedor = vendedor;
      this.comprador = comprador;
      this.artigo = artigo;
   }

   /**
     * Getters e Setters da classe ArtigoComprado
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
   
   public Artigo getArtigo() {
      return artigo;
   }

   public void setArtigo(Artigo artigo) {
      this.artigo = artigo;
   }

   /**
    * ToString que vai printar no ecrã as informações do Artigo Comprado
    */
   @Override
   public String toString() {
      return "ArtigoComprado { " +
              "vendedor= " + vendedor.getNome() +
              ", comprador= " + comprador.getNome() +
              ", artigo= " + artigo +
              '}';
   }
}











