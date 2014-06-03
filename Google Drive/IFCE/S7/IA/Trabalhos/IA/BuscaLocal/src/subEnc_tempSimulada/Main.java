package subEnc_tempSimulada;
public class Main {

	public static void main(String[] args) {

		Problema problema = new Problema();
		Busca busca = new Busca(problema);
		
		System.out.println("\n === Estado Inicial === \n");
		imprimir(problema.getEstadoInicial());
		
		System.out.println("\n === Resultado Subida da Enconsta ==== \n");		
		No solucao = busca.subida_da_encosta();
		imprimir(solucao.getEstado());
		System.out.println("Custo : " + solucao.getValor());
		
		
		System.out.println("\n === Tempera Simulada ==== \n");
		int tolerancia = 0;
		int temperatura = 1000;
		solucao = busca.tempera_simulada(tolerancia, temperatura);
		imprimir(solucao.getEstado());
		System.out.println("Custo : " + solucao.getValor());
	}

	public static void imprimir(int[][] tabuleiro) {
		String quadrado;
		System.out.println("--------------------------------");
		for (int linha = 0; linha < tabuleiro.length; linha++) {

			for (int coluna = 0; coluna < tabuleiro.length; coluna++) {
				quadrado = (tabuleiro[linha][coluna] == 0) ? " " : "1";
				System.out.print("| " + quadrado + " ");
			}
			System.out.println("|\n--------------------------------");
		}
	}
		
}
