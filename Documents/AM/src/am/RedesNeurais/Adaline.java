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
public class Adaline {

    public ArrayList<double[]> amostras;
    public ArrayList<Double> saidas;
    public double[] pesos;
    public double net;
    private final double taxaAprendizagem = 0.05;
    private final double precisao = 0.0000;
    private final int MAX_EPOCAS = 10000;

    public Adaline(ArrayList<double[]> dadosTreinamento, ArrayList<Double> saidasTreinamento) {
        amostras = dadosTreinamento;
        saidas = saidasTreinamento;
        pesos = new double[dadosTreinamento.get(0).length];
        this.inicializarPesos();
    }

    public boolean treinar() {
        int epoca = 0;
        boolean erro;
        //Repetir as instrucoes equanto o erro estiver fora da precisao requerida.
        double eqmAnterior, eqmAtual;
        do {
            erro = false;
            // Calcular o EQM com os pesos atuais.
            eqmAnterior = Eqm.calculateEQM(amostras, saidas, pesos);
            
            for (int i = 0; i < amostras.size(); i++) {
                net = executar(amostras.get(i));
                
                if (net != saidas.get(i)) {
                    corrigirPesos(amostras.get(i), saidas.get(i), net);
                    erro = true;
                }
            }
            epoca++;

            // Calculamos oo EQM dos pesos atuais.
            eqmAtual = Eqm.calculateEQM(amostras, saidas, pesos);

        } while(  (Math.abs(eqmAtual - eqmAnterior) > precisao) && (epoca < MAX_EPOCAS) 
                && (erro == true));
        
        return erro;
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
    
    public double executar(double[] amostra) {
        double rede = 0;

        for (int i = 0; i < amostra.length; i++) {
            rede += pesos[i] * amostra[i];
        }

        if (rede >= 0) {//DEVE SER >= ?
            return 1;
        }
        return -1;
    }
}
