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
public class Perceptron {

    public ArrayList<double[]> amostras;
    public ArrayList<Double> saidas;
    public double[] pesos;
    public double net;
    private final double taxaAprendizagem = 0.01;
    private final int MAX_EPOCAS = 10000;

    Perceptron(ArrayList<double[]> dadosTreinamento, ArrayList<Double> saidasTreinamento) {
        amostras = dadosTreinamento;
        saidas = saidasTreinamento;
        pesos = new double[dadosTreinamento.get(0).length];
        this.inicializarPesos();
    }

    public boolean treinar() {
        boolean erro;
        int epoca = 0;
        do {
            erro = false;
            for (int i = 0; i < amostras.size(); i++) {

                net = executar(amostras.get(i));

                if (net != saidas.get(i)) {
                    corrigirPesos(amostras.get(i), saidas.get(i), net);
                    erro = true;
                }
            }
            epoca++;
        } while ((erro == true) && (epoca < MAX_EPOCAS));
        return erro;
    }

    public double executar(double[] amostra) {
        double rede = 0;

        for (int i = 0; i < amostra.length; i++) {
            rede += pesos[i] * amostra[i];
        }

        if (rede >= 0) {
            return -1;
        }
        return 0;
    }

    private void inicializarPesos() {
        for (int i = 0; i < pesos.length; i++) {
            pesos[i] = 1 * (double) Math.random();
        }
    }

    private void corrigirPesos(double[] ponto, double saida, double rede) {
        for (int i = 0; i < pesos.length; i++) {
            pesos[i] = pesos[i] + taxaAprendizagem * (saida - rede) * ponto[i];
        }
    }
}
