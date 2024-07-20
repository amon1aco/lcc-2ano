import java.io.Serializable;

/**
 * Classe Data que irá implementar o formato da data usado na Loja Vintage
 */
public class Data implements Serializable{
    private int dia;
    private int mes;
    private int ano;
    
    /** 
     * Função Construtor com parametros inicializados a 0 
     */
    public Data() {
        this.dia = 0;
        this.mes = 0;
        this.ano = 0;
    }

    /** 
     * Função Construtor com parametros 
     */
    public Data(int dia, int mes, int ano) {
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
    }
    
    /**
     * Getters e Setters da classe Data
     */

    public int getDia() {
        return dia;
    }
    
    public void setDia(int dia) {
        this.dia = dia;
    }
    
    public int getMes() {
        return mes;
    }
    
    public void setMes(int mes) {
        this.mes = mes;
    }
    
    public int getAno() {
        return ano;
    }
    
    public void setAno(int ano) {
        this.ano = ano;
    }

    /** 
     * Funções auxiliares 
     */

    /**
     * Função irá avançar dias
     */
    public void avancarDia() {
        dia++;
        if (dia > getDiasNoMes()) {
            dia = 1;
            mes++;
            if (mes > 12) {
                dia = 1;
                mes = 1;
                ano++;
            }
        }
    }

    /**
     * Função irá retornar os dias que cada mês tem
     */
    private int getDiasNoMes() {
        if (mes == 2) {     // FEV tem sempre 28 aqui..
                return 28;
        }
        else if (mes == 4 || mes == 6 || mes == 9 || mes == 11) {
            return 30;
        } else {
            return 31;
        }
    }
    
    /**
     * Função irá retornar a data 1 dia depois de acordo com o formato dado
     */
    public String dataDaquiA1Dia() {
        int novoDia = this.dia + 1;
        int novoMes = this.mes;
        int novoAno = this.ano;
    
        int diasNoMesAtual = getDiasNoMes();
        if (novoDia > diasNoMesAtual) {
            novoDia -= diasNoMesAtual;
            novoMes++;
            if (novoMes > 12) {
                novoMes = 1;
                novoAno++;
            }
        }
        return String.format("%02d/%02d/%02d", novoDia, novoMes, novoAno);
    }
    
    /**
     * Função irá retornar a data 2 dias depois de acordo com o formato dado
     */
    public String dataDaquiA2Dias() {
        int novoDia = this.dia + 2;
        int novoMes = this.mes;
        int novoAno = this.ano;
    
        int diasNoMesAtual = getDiasNoMes();
        if (novoDia > diasNoMesAtual) {
            novoDia -= diasNoMesAtual;
            novoMes++;
            if (novoMes > 12) {
                novoMes = 1;
                novoAno++;
            }
        }
        return String.format("%02d/%02d/%02d", novoDia, novoMes, novoAno);
    }   // o %02 é pra caso o nr seja 1,2,3 adiciona o 0 antes

    /**
     * Função irá retornar a data 1 dia antes de acordo com o formato dado
     */
    public String dataHa1Dia() {
        int novoDia = this.dia - 1;
        int novoMes = this.mes;
        int novoAno = this.ano;
        if (novoDia < 1) {
            novoMes--;
            if (novoMes < 1) {
                novoMes = 12;
                novoAno--;
            }
            novoDia = getDiasNoMes();
        }
        return String.format("%02d/%02d/%02d", novoDia, novoMes, novoAno);
    }
    
    /**
     * Função irá retornar a data 2 dias antes de acordo com o formato dado
     */
    public String dataHa2Dias() {
        int novoDia = this.dia - 2;
        int novoMes = this.mes;
        int novoAno = this.ano;
    
        if (novoDia < 1) {
            novoMes--;
            if (novoMes < 1) {
                novoMes = 12;
                novoAno--;
            }
            novoDia = getDiasNoMes() + novoDia;
        }
        return String.format("%02d/%02d/%02d", novoDia, novoMes, novoAno);
    }
    
    /**
     * Função que recebe uma data e vê se essa data é igual à data atual
     */
    public boolean equals(Data outraData) {
        return (dia == outraData.dia) && (mes == outraData.mes) && (ano == outraData.ano);
    }

    /**
     * Função que constroi o formato da data e retorna a mesma nesse formato
     */
    public String toString() {
        return "Data: " + this.dia + "/" + this.mes + "/" + this.ano;
    }
    
    /**
     * Função que dá clone de uma data
     */
    public Data clone() {
        Data novaData = new Data();
        novaData.dia = this.dia;
        novaData.mes = this.mes;
        novaData.ano = this.ano;
        return novaData;
    }
    
    /**
     * Função irá validar ou não uma data, retorna true se as condições para uma data valida forem cumpridas e false caso contrario
     */
    public boolean dataValida(Data data) {
        if (ano < data.getAno() || (ano == data.getAno() && mes < data.getMes()) || (ano == data.getAno() && mes == data.getMes() && dia <= data.getDia())) {
            return false;
        }
        if (mes < 1 || mes > 12) {
            return false;
        }
        else {
            if (mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10 || mes == 12) {
                if (dia > 31 || dia < 1) return false;
                else return true;
            }
            else if (mes == 4 || mes == 6 || mes == 9 || mes == 11) {
                if (dia > 30 || dia < 1) return false;
                else return true;
            }
            else {
                if (dia > 28 || dia < 1) return false;
                else return true;
            }
        }   
    }  
    
    /**
     * Função irá retorar o formato da data
     */
    public String dataformat() {
        return String.format("%02d/%02d/%02d", dia, mes, ano);
    }

    /**
     * Função que compára a data data com uma outra data fornecida
     */
    public int compararData(Data outraData) {
        if (this.ano != outraData.ano) {
            return Integer.compare(this.ano, outraData.ano);
        } else if (this.mes != outraData.mes) {
            return Integer.compare(this.mes, outraData.mes);
        } else {
            return Integer.compare(this.dia, outraData.dia);
        }
    }

    /**
     * Função irá comparar 2 datas
     */
    public int compararDatasString(String outraData) {
        Data data1 = criarData(this.dataformat());
        Data data2 = criarData(outraData);
        return data1.compararData(data2);
    }

    /**
     * Função que recebe uma data em formato string e cria uma data de acordo com o formato preferido
     */
    private Data criarData(String dataString) {
        String[] partesData = dataString.split("/");
        int dia = Integer.parseInt(partesData[0]);
        int mes = Integer.parseInt(partesData[1]);
        int ano = Integer.parseInt(partesData[2]);
        return new Data(dia, mes, ano);
    }
}
