import java.io.*;

/**
 * Classe que vai ter como objetivo criar um ficheiro para escrever/guardar as informações, e removê-las
 */
public class Arquivos {
    private String caminhoArquivo;

    public Arquivos(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    /**
     * Usa a classe ObjectOutputStream para escrever o objeto no Arquivo
     */
    public void salvarEstado(Vintage objeto) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(caminhoArquivo))) {
            out.writeObject(objeto);
        } catch (IOException e) {
            e.printStackTrace();
        }
    } 
    
    /**
     * Lê o conteúdo do Arquivo, para tal usa a class ObjectInputStrean para fazer essa leitura
     */
    public Object carregarEstado() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(caminhoArquivo))) {
            return in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
    * Fase 4, gravar em ficheiro de texto
    */
    public void escreveFicheiroTexto(String nomeFicheiro) throws FileNotFoundException {
        PrintWriter fich = new PrintWriter(nomeFicheiro);
        fich.println("----- Vintage -----");
        fich.println(this.toString());
        fich.flush();
        fich.close();
    }
}