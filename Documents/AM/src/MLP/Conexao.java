package MLP;

public class Conexao {

	
	
	private int origem;
	private int destino;
	private double estimulo;
	private double w;
	
	
	public Conexao(int entrada, int saida, double w){
		this.origem = entrada;
		this.destino = saida;
		this.w = w;		
	}
	
	
	
	
	public double getW() {
		return w;
	}


	public void setW(double w) {
		this.w = w;
	}


	public int getOrigem() {
		return origem;
	}


	public int getDestino() {
		return destino;
	}
	
	


	public double getEstimulo() {
		return estimulo;
	}




	public void setEstimulo(double estimulo) {
		this.estimulo = estimulo;
	}




	public static void main(String[] args) {
		int cont = 0;
		for(int i = 0; i < 10; i++){
			double w = Math.random();
			if(Math.random() > 0.5){
				cont++;
			}
			
		}
		System.out.println(cont);

	}

}