/**
 * bias só deve ser carregado para o MLP. Fraser também usa essa classe.
 *
 */
package MLP.dados;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Entrada {

    /**
     * @param args
     */
    private List<Registro> dadosDeEntrada;
    private int posicaoDaSaida;
    private boolean isMLP;

    public Entrada(int saida, boolean isMLP) throws IOException {
        this.dadosDeEntrada = new ArrayList<>();
        this.posicaoDaSaida = saida;
        this.isMLP = isMLP;
        long t1 = System.currentTimeMillis();
        this.lerArquivo();
        long t2 = System.currentTimeMillis();
        double deltaT = (t2 - t1);
        System.out.println("intervalo de tempo: " + deltaT);

    }

    public List<Registro> getDadosDeEntrada() {
        return dadosDeEntrada;
    }

    private void lerArquivo() throws IOException {

        String path = "iris.data";

        File arq = new File(path);
        InputStream is = new FileInputStream(arq);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String linha = br.readLine();
        int cont = 0;
        while (linha != null) {
            this.carregarUnidadesDeEntrada(linha);
            linha = br.readLine();
            cont++;

        }

        if (this.isMLP) {
            this.carregarSaidas();
            this.carregarBias();
        }

        /*	for(Registro reg : dadosDeEntrada){
			
         for(Double data : reg.getTupla()){
				
         if(reg.getTupla().indexOf(data) != reg.getTupla().size()-1){
         System.out.print(data+", ");
         }
         else{
         System.out.print(data);
         }
				
         }
         System.out.println();
			
         }	*/
        System.out.println(cont + " registros");

    }

    /*
    private void carregarUnidadesDeEntrada(String linha) {
        int inicio = 0;
        int fim = 0;
        boolean first = true;
        linha.trim();

        char[] linhaArray = linha.toCharArray();
        String linhaFinal = "";

        for (int i = 0; i < linha.length(); i++) {
            if (linhaArray[i] != ' ') {
                linhaFinal = linhaFinal.concat(String.valueOf(linhaArray[i]));
            }
        }

        linha = linhaFinal;

        Registro novoRegistro = new Registro();

        for (int i = 0; i < linha.length(); i++) {

            if (linha.charAt(i) == ',' || i == linha.length() - 1) {
                if (i != linha.length() - 1) {
                    fim = i;
                } else {
                    fim = i + 1;
                }
                if (first) {
                    novoRegistro.getTupla().add(Double.valueOf(linha.substring(inicio, fim)));
                    first = false;
                } else {
                    novoRegistro.getTupla().add(Double.valueOf(linha.substring(inicio + 1, fim)));
                }

                inicio = fim;
            }
        }
        this.dadosDeEntrada.add(novoRegistro);

    }*/

    private void carregarUnidadesDeEntrada(String linha) {
        String[] split = linha.split(",");

        Registro novoRegistro = new Registro();
        for (int i = 0; i < (split.length - 1); i++) {
            novoRegistro.getTupla().add(Double.parseDouble(split[i]));
        }

        if ("Iris-versicolor".equals(split[split.length - 1])) {
            novoRegistro.getTupla().add(1.0);
        } else {
            novoRegistro.getTupla().add(-1.0);
        }

        this.dadosDeEntrada.add(novoRegistro);

    }

    private void carregarSaidas() {

        Double saida = null;
        for (Registro registro : this.dadosDeEntrada) {
            saida = registro.getTupla().get(this.posicaoDaSaida);
            registro.getTupla().remove(this.posicaoDaSaida);
            registro.setSaida(saida);
        }
    }

    private void carregarBias() {

        for (Registro registro : this.dadosDeEntrada) {
            registro.getTupla().add(0, 1.00);
        }

    }

    public static void main(String[] args) {

        try {
            Entrada teste = new Entrada(4, true);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
