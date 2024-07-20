
/**
 * Classe Main que irá chamar uma nova interface e inicializa a data
 */
public class main {
    
    public static void main(String[] args) {
        Data data = new Data(17,04,23); 

        new Interface(data).run();
    }
}
