/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package am.RedesNeurais;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author leandro
 */
public class IrisRNA {

    public static ArrayList<double[]> amostras;
    public static ArrayList<Double> saidas;

    public static void lerArquivo(String arquivo) {
        amostras = new ArrayList<>();
        saidas = new ArrayList<>();
        try {
            BufferedReader in = new BufferedReader(new FileReader(arquivo));
            String str;

            while (in.ready()) {
                str = in.readLine();

                String[] aux = str.split(",");

                double[] linha = new double[aux.length-1];
                for (int coluna = 0; coluna < aux.length - 1; coluna++) {
                    linha[coluna] = Double.parseDouble(aux[coluna]);
                }
                amostras.add(linha);
                
                if ("Iris-versicolor".equals(aux[aux.length - 1])) {
                    saidas.add(1.0);
                } else {
                    saidas.add(-1.0);
                }
                
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
        ArrayList<Double> saidasTreinamento = new ArrayList<>();
        for(int i = 0; i<((int)(amostras.size()*porcentagem));i++){            
            dadosTreinamento.add(amostras.get(indicesAleatorios.get(i)));
            saidasTreinamento.add(saidas.get(indicesAleatorios.get(i)));
        }
        
        //Seleciona o conjunto de TESTE
        ArrayList<double[]> dadosTeste = new ArrayList<>();
        ArrayList<Double> saidasTeste = new ArrayList<>();
        for(int i = ((int)(amostras.size()*porcentagem)) + 1; i<amostras.size(); i++){
            dadosTeste.add(amostras.get(indicesAleatorios.get(i)));
            saidasTeste.add(saidas.get(indicesAleatorios.get(i)));
        }
        
        //Usado para estatística
        double resultado;
        int numAcertoPerceptron = 0;
        int numAcertoAdaline = 0;
        
        boolean convergiu;
        Perceptron perceptron = new Perceptron(dadosTreinamento, saidasTreinamento);
        convergiu = perceptron.treinar();
        if(!convergiu){
            System.out.println("O algoritmo Perceptron nao conseguiu convergir");
            return;
        }
        
        Adaline adaline = new Adaline(dadosTreinamento, saidasTreinamento);
        convergiu = adaline.treinar();
        if(!convergiu){
            System.out.println("O algoritmo Adaline nao conseguiu convergir");
            return;
        }
        
        //computa o número de acertos
        double[] ponto = new double[amostras.get(0).length];
        for (int indice = 0 ; indice < dadosTeste.size(); indice++) {
            System.arraycopy(dadosTeste.get(indice), 0, ponto, 0, dadosTeste.get(0).length);
            
            resultado = perceptron.executar(dadosTeste.get(indice));
            System.out.println(resultado);
            if(resultado == saidasTeste.get(indice))
                numAcertoPerceptron++;
                        
            resultado = adaline.executar(dadosTeste.get(indice));
            
            if(resultado == saidasTeste.get(indice))
                numAcertoAdaline++;
        }
        
        System.out.println("Perceptron - Acertou "+numAcertoPerceptron+" para "+dadosTeste.size()+" dados de teste.");
        System.out.println("Adaline - Acertou "+numAcertoAdaline+" para "+dadosTeste.size()+" dados de teste.");
    }

    public static void main(String[] args) {
        try {
            lerArquivo("iris.data");
            estatisticas(0.75);
            
        } catch (Exception e) {
            System.out.println("Erro");
        }
    }
}
