package genetico;

public class Tabuleiro {
	private int[][] estado;
	
	public int[][] getEstado() {
		return estado;
	}

	public void setEstado(int[][] estado) {
		this.estado = estado;
	}



	public void imprimir(){
		String tab;
		
		System.out.println("--------------------------------");
		for (int linha = 0; linha < estado.length; linha++) {

			for (int coluna = 0; coluna < estado.length; coluna++) {
				tab = (estado[linha][coluna] == 0) ? " " : "1";
				System.out.print("| " + tab + " ");
			}
			System.out.println("|\n--------------------------------");
		}
		System.out.println("Custo " + getCusto());
	}

	public int getCusto() {
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
}
