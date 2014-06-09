package am;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author leandro
 */
public class MetodosBDistancia {

    public ArrayList<double[]> treinamento;
    public ArrayList<String> rotulos;
    public int k;

    public MetodosBDistancia(ArrayList<double[]> dados, ArrayList<String> rotulos, int k) throws IllegalArgumentException {
        if (dados.size() != rotulos.size()) {
            throw new IllegalArgumentException("Tamanho dos dados e rotulos diferentes");
        }
        this.treinamento = dados;
        this.rotulos = rotulos;
        this.k = k;
    }

    public String umVizinhoMaisProximo(double[] objeto) throws IllegalArgumentException{
        if (treinamento.get(0).length != objeto.length) {
            throw new IllegalArgumentException("Ponto de teste incorreto");
        }

        String classe = "";
        double distanciaMin = Double.POSITIVE_INFINITY;
        

        for (int linha = 0; linha < treinamento.size(); linha++) {

            double[] ponto = new double[treinamento.get(0).length];
            System.arraycopy(treinamento.get(linha), 0, ponto, 0, treinamento.get(0).length);

            if (distanciaEuclidiana(ponto, objeto) < distanciaMin) {
                distanciaMin = distanciaEuclidiana(ponto, objeto);
                classe = rotulos.get(linha);
            }
        }

        return classe;
    }

    public String kVizinhosMaisProximos(double[] objeto) throws IllegalArgumentException {
        if (treinamento.get(0).length != objeto.length) {
            throw new IllegalArgumentException("Ponto de teste incorreto");
        }
        return kVizinhosMaisProximos(objeto, k);
    }

    public String kVizinhosMaisProximos(double[] objeto, int k) throws IllegalArgumentException {
        if (treinamento.get(0).length != objeto.length) {
            throw new IllegalArgumentException("Ponto de teste incorreto");
        }
        String classe;
        Map<Double, Ponto> kVizinhos = new TreeMap<>();

        //Calcula a distancia euclidiana para cada um dos pontos        
        double distancia;
        Map<Double, Ponto> distanciasPontos = new TreeMap<>(Collections.reverseOrder());
        for (int linha = 0; linha < treinamento.size(); linha++) {
            
            double[] ponto = new double[treinamento.get(0).length];
            System.arraycopy(treinamento.get(linha), 0, ponto, 0, treinamento.get(0).length);

            distancia = distanciaEuclidiana(ponto, objeto);

            Ponto nPonto = new Ponto();            
            nPonto.coordenada = new double[treinamento.get(0).length];
            System.arraycopy(ponto, 0, nPonto.coordenada, 0, treinamento.get(0).length);
            nPonto.rotulo = rotulos.get(linha);
            distanciasPontos.put((1 / distancia), nPonto);
        }

        //Pega os k-vizinhos mais próximos ordenanos em ordem decrescente
        for (Map.Entry entry : distanciasPontos.entrySet()) {
            if (k == 0) {
                break;
            }
            kVizinhos.put((Double) entry.getKey(), (Ponto) entry.getValue());
            k--;
        }

        //Separa os rótulos em um ArrayList para encontrar a moda
        List<String> kRotulos = new ArrayList<>();
        for (Map.Entry<Double, Ponto> entry : kVizinhos.entrySet()) {
            kRotulos.add(entry.getValue().rotulo);
        }
        classe = moda(kRotulos);

        for (Map.Entry<Double, Ponto> entry : kVizinhos.entrySet()) {
            //System.out.println(entry.getKey() + "/" + entry.getValue().rotulo);
        }

        return classe;
    }

    private double distanciaEuclidiana(double[] a, double[] b) {
        double soma = 0.0;
        for (int i = 0; i < a.length; i++) {
            soma += Math.pow((a[i] - b[i]), 2);
        }

        return Math.sqrt(soma);
    }

    private String moda(List<String> rotulos) {
        String moda = "";
        int ocorrencia = 0;

        //Calcula o número de ocorrências para cada rótulo
        while (!rotulos.isEmpty()) {
            int cont = 0;

            String primeira = rotulos.get(0);
            for (String rotulo : rotulos) {
                if (primeira.equals(rotulo)) {
                    cont++;
                }
            }

            if (cont > ocorrencia) {
                moda = primeira;
                ocorrencia = cont;
            }

            //remove o elemento atual na lista de rótulos
            List<String> remover = new ArrayList<>();
            remover.add(primeira);
            rotulos.removeAll(remover);
        }

        return moda;
    }    
}
