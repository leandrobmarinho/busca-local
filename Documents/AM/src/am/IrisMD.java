package am;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class IrisMD {

    public static ArrayList<double[]> amostras = new ArrayList<>();
    public static ArrayList<String> saidas = new ArrayList<>();

    public static void lerArquivo(String arquivo) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(arquivo));
            String str;

            while (in.ready()) {
                str = in.readLine();

                String[] aux = str.split(",");

                double[] linha = new double[aux.length - 1];
                for (int coluna = 0; coluna < aux.length - 1; coluna++) {
                    linha[coluna] = Double.parseDouble(aux[coluna]);
                }
                amostras.add(linha);
                saidas.add(aux[aux.length - 1]);
            }
            in.close();

        } catch (IOException e) {
            
        }
    }

    public static void estatisticas(double porcentagem) {        
        //Sorteia os indices do arquivo
        ArrayList<Integer> indicesAleatorios = new ArrayList<>();        
        for(int i=0; i<amostras.size(); i++){
            indicesAleatorios.add(i);
        }        
        Collections.shuffle(indicesAleatorios);
        
        //Seleciona o conjunto de TREINAMENTO
        ArrayList<double[]> dadosTreinamento = new ArrayList<>();
        ArrayList<String> rotulosTreinamento = new ArrayList<>();
        for(int i = 0; i<((int)(amostras.size()*porcentagem));i++){
            dadosTreinamento.add(amostras.get(indicesAleatorios.get(i)));
            rotulosTreinamento.add(saidas.get(indicesAleatorios.get(i)));
        }
        
        //Seleciona o conjunto de TESTE
        ArrayList<double[]> dadosTeste = new ArrayList<>();
        ArrayList<String> rotulosTeste = new ArrayList<>();
        for(int i = ((int)(amostras.size()*porcentagem)) + 1; i<amostras.size(); i++){
            dadosTeste.add(amostras.get(indicesAleatorios.get(i)));
            rotulosTeste.add(saidas.get(indicesAleatorios.get(i)));
        }
        
        String resultado;
        int numAcerto1Vizinho = 0;
        int numAcertoKVinhos = 0;
        
        MetodosBDistancia mbd = new MetodosBDistancia(dadosTreinamento, rotulosTreinamento, 5);
        //computa o número de acertos para os dois algoritmos
        double[] ponto = new double[amostras.get(0).length];
        for (int indice = 0 ; indice < dadosTeste.size(); indice++) {
            System.arraycopy(dadosTeste.get(indice), 0, ponto, 0, dadosTeste.get(0).length);

            resultado = mbd.umVizinhoMaisProximo(ponto);
            if (resultado.equals(rotulosTeste.get(indice))) {
                numAcerto1Vizinho++;
            }

            resultado = mbd.kVizinhosMaisProximos(ponto);
            if (resultado.equals(rotulosTeste.get(indice))) {
                numAcertoKVinhos++;
            }
        }

        System.out.println("Para " + dadosTeste.size() + " pontos de teste");
        System.out.println("Com 1 Viziho Mais próximo:");
        System.out.println("Acertou : " + numAcerto1Vizinho);
        System.out.println("-----------------------------------");
        System.out.println("Com " + mbd.k + "-vizinhos mais próximos");
        System.out.println("Acertou : " + numAcertoKVinhos);        
        
    }

    public static void main(String[] args) {
        try {            
            lerArquivo("iris.data");
            estatisticas(0.6);
            /*
             double[] ponto = {7.0,3.2,4.7,1.4};
             MetodosBDistancia mbd = new MetodosBDistancia(dados, rotulos, 5);
             System.out.println("1-Vizinho mais próximo "+mbd.umVizinhoMaisProximo(ponto));
             System.out.println("K-Vizinho mais próximo "+mbd.kVizinhosMaisProximos(ponto));
             */
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}