/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package am.RedesNeurais;

import java.util.ArrayList;

/**
 *
 * @author leandro
 */
public class Eqm {

    public static double calculateEQM(ArrayList<double[]> amostras, ArrayList<Double> saidas, double[] pesos) {
        // 1. Obtendo a quantidade de padroes de treinamento.
        int quantidadePadroes = saidas.size();

        // 2. Inicializando a variavel EQM.
        double eqm = 0.0;

        // 3. Para cada amostra de treinamento realizamos o calculo
        // de "u" e posteriormente o calculo do somatorio do EQM.
        for (int k = 0; k < quantidadePadroes; k++) {
            // 3.1. Multiplicar o peso por uma dada amostra.
            // Pode ser usado um vetor para guardar os resultados.
            // Contudo, achei mais facil ja fazer a soma diretamente.
            double u = 0.0;

            for (int atributo = 0; atributo < amostras.get(0).length; atributo++) {
                u += pesos[atributo] * amostras.get(k)[atributo];
            }

            // 3.2. Realizando o calculo do EQM.
            eqm += Math.pow(saidas.get(k) - u, 2.0);
        }

        // 3. Calculado da media do EQM.
        double eqmMedio = eqm / quantidadePadroes;

        return eqmMedio;
    }
}
