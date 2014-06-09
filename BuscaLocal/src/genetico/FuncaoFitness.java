package genetico;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;

public class FuncaoFitness extends FitnessFunction {

	int linha;
	
	@Override
	protected double evaluate(IChromosome cromossomo) {
		
		Tabuleiro tabuleiro = new Tabuleiro();
		int[][] estado = new int[8][8];
		
		for (int coluna = 0; coluna < cromossomo.getGenes().length; coluna++) {
			linha = (Integer) cromossomo.getGene(coluna).getAllele();			
			
			estado[linha][coluna] = 1;
		}
		
		tabuleiro.setEstado(estado);		
		
		return 28 - tabuleiro.getCusto();
	}

}
