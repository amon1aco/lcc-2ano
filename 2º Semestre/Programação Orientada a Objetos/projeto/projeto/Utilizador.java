import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.Serializable;

/** 
 * Classe Utilizador que define os Utilizadores e recebe parametros para tal
 */
public class Utilizador implements Serializable {
    private String email;
    private String nome;
    private String morada;
    private int nrFiscal;
    private ArrayList<Artigo> produtosVenda; // PRODUTOS À VENDA
    private HashMap<String, ArrayList<Artigo>> produtosCompra = new HashMap<>();
    private ArrayList<Artigo> vendas;          // PRODUTOS vendidos
    private Marketplace marketplace;
    private Data data;
    private float valor_vendas;
    private float valor_compras;
    private static int codigoU = 100;
    private final int codigobarrasU;
    private ArrayList<Fatura> faturas;
    private HashMap<String,Encomenda> registoEncomendas;
    private Encomenda carrinho = new Encomenda(); 

    DecimalFormat df = new DecimalFormat("#.00");
    
    /** 
     * Função Construtor com parametros 
     */
    public Utilizador(String email, String nome, String morada, int nrFiscal,
                        Marketplace marketplace, Data data, int codigo) {
        this.codigobarrasU = codigo;
        this.email = email;
        this.nome = nome;
        this.morada = morada;
        this.nrFiscal = nrFiscal;
        this.produtosVenda = new ArrayList<>();
        this.produtosCompra = new HashMap<>();
        this.vendas = new ArrayList<>();
        this.marketplace = marketplace;
        this.data = data;
        this.valor_vendas = 0;
        this.valor_compras = 0;
        this.faturas = new ArrayList<>();
        this.registoEncomendas = new HashMap<>();
    }

    /**
     * Getters e Setters da classe Utilizador
     */
    public int getCodigo() {
        return this.codigobarrasU;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getMorada() {
        return morada;
    }

    public int getNrFiscal() {
        return nrFiscal;
    }

    public ArrayList<Artigo> getProdutosVenda() {
        return produtosVenda;
    }

    public HashMap<String, ArrayList<Artigo>> getProdutosCompra() {
        return produtosCompra;
    }

    public void setProdutosCompra(HashMap<String, ArrayList<Artigo>> produtosCompra) {
        this.produtosCompra = produtosCompra;
    }

    public ArrayList<Artigo> getVendas() {
        return vendas;
    }

    /** 
     * Funções auxiliares 
     */
    public void adicionarProdutoVenda(Artigo produto) {
        produto.setVendedor(this);
        produtosVenda.add(produto);
        marketplace.adicionarArtigo(produto);
    }

    public void removerProdutoVenda(int codigoProduto) {
        for (Artigo produto : produtosVenda) {
            if (produto.getCodigo() == codigoProduto) { // verifica se o código do produto é igual ao código do produto a ser removido
                produtosVenda.remove(produto);
                marketplace.removerArtigo(produto);
                break; // para a iteração após remover o produto
            }
        } 
    }
    
    public void addProdutoCompra(Artigo produto) {
        // verifica se a data já existe como chave no HashMap
        if (!produtosCompra.containsKey(data.dataformat())) {
            // se não existir, cria uma nova lista vazia de produtos para essa data
            produtosCompra.put(this.data.dataformat(), new ArrayList<Artigo>());
        }
        // adiciona o produto à lista correspondente à data
        produtosCompra.get(this.data.dataformat()).add(produto);
    }
    
    /**
     * Mais Getters e Setters da classe Utilizador
     */
    public ArrayList<Fatura> getFaturas() {
        return faturas;
    }

    public void setFaturas(ArrayList<Fatura> faturas) {
        this.faturas = faturas;
    }

    // Adicionar e Remover faturas
    public void adicionarFatura(Fatura fatura) {
        this.faturas.add(fatura);
    }

    public void removerFatura(Fatura fatura) {
        this.faturas.remove(fatura);
    }

    public void adicionarVenda(Artigo produto) {
        vendas.add(produto);
    }
    
    public void removerVenda(Artigo produto) {
        vendas.remove(produto);
    }
    
    public float getValorVendas() {
        return valor_vendas;
    }
    
    public void setValorVendas(float valor_vendas) {
        this.valor_vendas = valor_vendas;
    }
    public void addValorVendas(float valor) {
        this.valor_vendas += valor;
    }
    
    public float getValorCompras() {
        return valor_compras;
    }
    
    public void setValorCompras(float valor_compras) {
        this.valor_compras = valor_compras;
    }
    public void addValorCompras(float valor) {
        this.valor_compras += valor;
    }

    public HashMap<String, Encomenda> getRegistoEncomendas() {
        return registoEncomendas;
    }
    
    public void setRegistoEncomendas(HashMap<String, Encomenda> registoEncomendas) {
        this.registoEncomendas = registoEncomendas;
    }
    
    public void adicionarEncomenda(String chave, Encomenda encomenda) {
        registoEncomendas.put(chave, encomenda);
    }
    
    public void removerEncomenda(String chave) {
        registoEncomendas.remove(chave);
    }
    
    public Encomenda getCarrinho() {
        return carrinho;
    }

    public void setCarrinho(Encomenda carrinho) {
        this.carrinho = carrinho;
    }

    /** 
     * Funções auxiliares 
     */
    public void adicionarProdutoCompra(int codigoProduto) {
        Artigo produto = marketplace.getArtigoPorCodigo(codigoProduto);
        if (produto != null && !produto.getVendedor().equals(this) && produto.getVendedor().getProdutosVenda().contains(produto)) {
            carrinho.adicionarArtigo(produto);
            //carrinho.calcularPrecoArtigos();
        } else {
            System.out.println("Não foi possivel adicionar esse artigo ao carrinho!");
        }
    }

    public void efetuarCompra(ArrayList<Transportadora> transp) {
        if (carrinho.getArtigos().isEmpty()) {
            System.out.println("Carrinho vazio. Nenhuma compra realizada.");
            return;
        }
        // cria HashMaps para cada transportadora
       // HashMap<Transportadora, Float> totalPorTransp = new HashMap<>();
        HashMap<Transportadora, Integer> nrPorTransp = new HashMap<>();
        for (Transportadora t : transp) {
          //  totalPorTransp.put(t, 0f);
            nrPorTransp.put(t, 0);
        }
        // carrinho.setTransp(transp);
        for (Artigo artigo : carrinho.getArtigos()) {
            artigo.incrNrDonos();
            ArtigoComprado artigoComprado = new ArtigoComprado(artigo.getVendedor(), this, artigo);
    
            // adiciona artigoComprado à transportadora correspondente
            Transportadora t = artigo.getTransportadora(); // aqui você precisa determinar qual transportadora está associada ao artigo
            t.adicionarEntrega2(data.dataDaquiA2Dias(), artigoComprado);
    
            // atualiza os HashMaps correspondentes
            // float total = totalPorTransp.get(t) + artigo.getPrecoFinal();
            int nr = nrPorTransp.get(t) + 1;
            // totalPorTransp.put(t, total);
            nrPorTransp.put(t, nr);
    
            //artigo.getVendedor().registarVenda(artigo);
            //marketplace.removerArtigo(artigo);
            artigo.getVendedor().removerProdutoVenda(artigo.getCodigo());
            Fatura fatura = new Fatura(artigo.getVendedor(),this,artigo,0,this.data); // Estado 0, em envio
            artigo.getVendedor().adicionarFatura(fatura);
            this.adicionarFatura(fatura);
            marketplace.adicionarVenda(artigo.getTaxas()); 
        }
        // imprime o resultado para cada transportadora
        for (Transportadora t : transp) {
            System.out.println("Transportadora " + t.getNome() + ":");
            //System.out.println("- Valor total: " + totalPorTransp.get(t));
            System.out.println("- Número de artigos: " + nrPorTransp.get(t));
        }
        System.out.println("A compra foi efetuada e o preço final foi: " + df.format(carrinho.calcularPrecoFinal(nrPorTransp))+ "EUR");
        this.addValorCompras(carrinho.calcularPrecoFinal(nrPorTransp));
        carrinho.setPrecoFinal(carrinho.calcularPrecoFinal(nrPorTransp));
        this.adicionarEncomenda(data.dataformat(), carrinho);
        carrinho = new Encomenda();
    }

    public void registarVenda(Artigo venda) {   // Funcao usada no vendedor
        //this.adicionarVenda(venda);
        //venda.getVendedor().removerProdutoVenda(venda.getCodigo()); // chama o método atualizado removerProdutoVenda que recebe o código do produto
        venda.getVendedor().adicionarVenda(venda);
        //addValorVendas(venda.getPrecoNoDesc());
    } 

    public void fazerDevolucao(int codigoArtigo) {
        Artigo artigo = null;
        String dataAtual = data.dataformat();
        boolean encontrado = false;
        // Percorre o HashMap produtosCompra do utilizador
        for (Map.Entry<String, ArrayList<Artigo>> entry : produtosCompra.entrySet()) {
            ArrayList<Artigo> artigos = entry.getValue();
            // Procura o artigo pelo código fornecido
            for (Artigo a : artigos) {
                if (a.getCodigo() == codigoArtigo) {
                    artigo = a;
                    break;
                }
            }
            // Realiza as operações se o artigo for encontrado
            if (artigo != null) {
                // Verifica se está dentro do período de devolução
                if (entry.getKey().equals(dataAtual) || entry.getKey().equals(data.dataHa1Dia())
                        || entry.getKey().equals(data.dataHa2Dias())) {
                    // Encontra a fatura correspondente
                    Fatura fatura = null;
                    for (Fatura f : faturas) {
                        if (f.getLinhaArtigos().equals(artigo)) {
                            fatura = f;
                            break;
                        }
                    }
                    if (fatura != null) {
                        fatura.setEstado(2); // Altera o estado da fatura para 2
                    }
                    artigos.remove(artigo);
                    artigo.getVendedor().adicionarProdutoVenda(artigo);
                    artigo.getVendedor().removerVenda(artigo);
                    artigo.getVendedor().addValorVendas(-artigo.getPrecoNoDesc());
                    this.addValorCompras(-artigo.getPrecoFinal());
                    artigo.getTransportadora().addFaturacao(-artigo.getTransportadora().getMargemLucro());
                    marketplace.removerVenda(artigo.getTaxas()); 
                    System.out.println("Artigo devolvido com sucesso.");
                    encontrado = true;
                    break;
                } else {
                    System.out.println("Fora do tempo de devolução.");
                    encontrado = true;
                    break;
                }
            }
        }  
        if (!encontrado) {
            System.out.println("Impossível fazer a devolução. O artigo não foi encontrado na lista de compras.");
        }
    }
    
    public String valorVenda(){        
        float total = 0;        
        for (int i = 0; i < vendas.size(); i ++){            
            total += vendas.get(i).getPrecoFinal();        
        }        
        return "O valor total é de: " + total +"€";      
    }

    /** 
     * ToString que irá printar todas as informações da classe Utilizador, perfil, produtos comprados, vendidos, faturações, devoluções...
     */
    public String toString() {
        String produtosVendaStr = "Produtos à venda: ";
        for (Artigo produto : produtosVenda) {
            produtosVendaStr += "\n\t " + produto.toString() + "\n";
        }
        String produtosCompraStr = "Produtos comprados: ";
        for (Map.Entry<String, ArrayList<Artigo>> entry : produtosCompra.entrySet()) {
            String data = entry.getKey();
            ArrayList<Artigo> produtos = entry.getValue();
            produtosCompraStr += "\n\tData: " + data + "\n";
            if (!produtos.isEmpty()) {
                for (Artigo produto : produtos) {
                    produtosCompraStr += "\t\t" + produto.toString() + "\n";
                }
            } else {
                produtosCompraStr += "\t\tNenhum produto comprado nessa data ou Artigo Devolvido!\n";
            }
        }
        String vendasStr = "Produtos vendidos: ";
        for (Artigo produto : vendas) {
            vendasStr += "\n\t " + produto.toString() + "\n";
        }
        String faturasStr = "Faturas: ";
        for (Fatura f : faturas) {
            faturasStr += "\n\t " + f.toString() + "\n";
        }
        return "Utilizador: \n\tCodigo: " + codigobarrasU + "\n\tEmail: " + email + "\n\tNome: " + 
            nome + "\n\tMorada: " + morada + "\n\tnrFiscal: " + nrFiscal + 
            "\n\n" + produtosVendaStr + "\n" + produtosCompraStr + "\n" + 
            vendasStr + "\n" + "\nTotal em vendas: " + valor_vendas + "\n" + 
            "Total em Compras: " + valor_compras + "\n" + faturasStr + "\n";
    }

    /**
     * função que irá retornar a faturação total do utilizador
     */
    public float getFaturamentoTotal(){        
        float faturamento = 0;        
        for(Artigo artigo : vendas){            
            faturamento += artigo.getPrecoNoDesc();        
        }        
        return faturamento;    
    }
    
    public String marketplacetoString() {
        return marketplace.toString();
    }

    public Marketplace getMarketplace(){
        return this.marketplace;
    }
}
        


