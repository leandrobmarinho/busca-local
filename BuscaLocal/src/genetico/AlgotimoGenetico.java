package genetico;

import genetico.Tabuleiro;

import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.IntegerGene;

public class AlgotimoGenetico {

    private static final double VALOR_ESPERADO = 28;
    private static final int N_RAINHA = 8;
    private static final int POPULACAO = 300;
    private static final int EPOCA = 600;

    public static Tabuleiro resolve() throws InvalidConfigurationException {
        Tabuleiro tabuleiro = new Tabuleiro();

        Configuration conf = new DefaultConfiguration();
        conf.setPreservFittestIndividual(true);
        FitnessFunction minhaFuncao = new FuncaoFitness();
        conf.setFitnessFunction(minhaFuncao);

        Gene[] sampleGene = new Gene[N_RAINHA];
        for (int i = 0; i < sampleGene.length; i++) {
            sampleGene[i] = new IntegerGene(conf, 0, (N_RAINHA - 1));
        }
        IChromosome sampleChromosome = new Chromosome(conf, sampleGene);
        conf.setSampleChromosome(sampleChromosome);

        conf.setPopulationSize(POPULACAO);

        Genotype population = Genotype.randomInitialGenotype(conf);

        boolean rodaAlgotimo = true;

        int cont = 0;
        while (rodaAlgotimo && cont <= EPOCA) {
            population.evolve();
            IChromosome bestSolutionSoFar = population.getFittestChromosome();
            System.out.println(bestSolutionSoFar);

            if (bestSolutionSoFar.getFitnessValue() == VALOR_ESPERADO) {
                break;
            }
            cont++;
        }

        //monta a matriz
        int linha;
        int coluna = 0;
        int[][] estado = new int[N_RAINHA][N_RAINHA];
        for (Gene gene : population.getFittestChromosome().getGenes()) {

            linha = (Integer) gene.getAllele();
            estado[linha][coluna] = 1;
            coluna++;
        }

        tabuleiro.setEstado(estado);

        return tabuleiro;
    }
}
