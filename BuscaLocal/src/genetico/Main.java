package genetico;

import genetico.AlgotimoGenetico;

import org.jgap.InvalidConfigurationException;

public class Main {

    public static void main(String[] args) {
        try {

            Tabuleiro tabuleiro = AlgotimoGenetico.resolve();
            tabuleiro.imprimir();

        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
