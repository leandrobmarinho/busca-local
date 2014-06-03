package subEnc_tempSimulada;
import java.util.ArrayList;
import java.util.Collections;

public class Problema {
	private int[][] estadoInicial;

	public Problema() {
		/*
		estadoInicial = new int[][] {
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 1, 0, 0, 0, 0 }, 
				{ 1, 0, 0, 0, 1, 0, 0, 0 },
				{ 0, 1, 0, 0, 0, 1, 0, 1 }, 
				{ 0, 0, 1, 0, 0, 0, 1, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0 } };*/
		
		estadoInicial = criaTabuleiroAleatorio();
	}

	public No vizinho(int[][] no) {
		ArrayList<No> sucessores = new ArrayList<No>();
		int posL = 0, posC = 0;
		int[][] copiaNo = clonaMatriz(no);
		No resultado = null;

		for (int coluna = 0; coluna < no.length; coluna++) {

			/* procura pela rainha na coluna */
			for (int linha = 0; linha < no.length; linha++) {

				if (copiaNo[linha][coluna] == 1) {
					/* remove a rainha da posicao [linha][coluna] */
					copiaNo[linha][coluna] = 0;

					posL = linha;
					posC = coluna;

					break;
				}
			}

			for (int linha = 0; linha < no.length; linha++) {
				if ( (linha != posL) || (coluna != posC)) {
					/* move a rainha para a posicao [linha][coluna] */
					copiaNo[linha][coluna] = 1;

					int[][] novaMatriz = clonaMatriz(copiaNo); 
					No sucessor = new No(novaMatriz, calculaValor(copiaNo));
					sucessores.add(sucessor);

					copiaNo[linha][coluna] = 0;
				}
			}

			/* volta a rainha para posicao anterior */
			copiaNo[posL][posC] = 1;
		}
						
		if ( sucessores.size() > 0){
			Collections.sort(sucessores);
			int menorValor = sucessores.get(0).getValor();					
			
			ArrayList<No> resultados = new ArrayList<No>();
			for (No r : sucessores) {
				if ( r.getValor() == menorValor){
					resultados.add(r);
				}
			}
			Collections.shuffle(resultados);
			resultado = resultados.get(0);
		}
		

		return resultado;
	}
	
	public int calculaValor(int[][] estado) {
		int valor = 0;
		int posL = 0, posC = 0;
		int tamanho = estado.length;

		for (int coluna = 0; coluna < estado.length; coluna++) {

			/* procura pela rainha na coluna */
			for (int linha = 0; linha < estado.length; linha++) {

				if (estado[linha][coluna] == 1) {
					posL = linha;
					posC = coluna;

					break;
				}
			}

			/* calcula os pares de rainhas em ataque */
			for (int i = 1; i < tamanho; i++) {

				// verifica na diagonal superior
				if ( ( ( (posL - i) >= 0) && (posC + i) < tamanho)
						&& estado[posL - i][posC + i] == 1) {
					valor += 1;
				}

				// verifica na diagonal inferior
				if ((((posL + i) < tamanho) && (posC + i) < tamanho)
						&& estado[posL + i][posC + i] == 1) {
					valor += 1;
				}

				// verifica o lado direito
				if (((posC + i) < tamanho) && estado[posL][posC + i] == 1) {
					valor += 1;
				}
			}

		}

		return valor;
	}

	public int[][] getEstadoInicial() {
		return this.clonaMatriz(estadoInicial);
	}

	public void setEstadoInicial(int[][] estadoInicial) {
		this.estadoInicial = estadoInicial;
	}

	public int[][] clonaMatriz(int[][] list) {
		int[][] clone = new int[list.length][list.length];

		for (int linha = 0; linha < list.length; linha++) {

			for (int coluna = 0; coluna < list.length; coluna++) {
				clone[linha][coluna] = list[linha][coluna];
			}
		}

		return clone;
	}
	
	private int[][] criaTabuleiroAleatorio(){
		int[][] tabuleiro = new int[8][8];		
		int linha;
		
		for(int coluna = 0; coluna < tabuleiro.length; coluna ++){
			
			linha = (int) (0 + Math.random() * tabuleiro.length);;
			tabuleiro[linha][coluna] = 1;
		}
		
		return tabuleiro;
	}

	public No vizinhoAleatorio(int[][] no) {
		ArrayList<No> sucessores = new ArrayList<No>();
		int posL = 0, posC = 0;
		int[][] copiaNo = clonaMatriz(no);
		No resultado = null;

		for (int coluna = 0; coluna < no.length; coluna++) {

			/* procura pela rainha na coluna */
			for (int linha = 0; linha < no.length; linha++) {

				if (copiaNo[linha][coluna] == 1) {
					/* remove a rainha da posicao [linha][coluna] */
					copiaNo[linha][coluna] = 0;

					posL = linha;
					posC = coluna;

					break;
				}
			}

			for (int linha = 0; linha < no.length; linha++) {
				if ( (linha != posL) || (coluna != posC)) {
					/* move a rainha para a posicao [linha][coluna] */
					copiaNo[linha][coluna] = 1;

					int[][] novaMatriz = clonaMatriz(copiaNo); 
					No sucessor = new No(novaMatriz, calculaValor(copiaNo));
					sucessores.add(sucessor);

					copiaNo[linha][coluna] = 0;
				}
			}

			/* volta a rainha para posicao anterior */
			copiaNo[posL][posC] = 1;
		}
						
		if ( sucessores.size() > 0){
			Collections.sort(sucessores);
			resultado = sucessores.get(0);
		}

		return resultado;
	}

}
