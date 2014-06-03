package subEnc_tempSimulada;

public class No implements Comparable<No>{
	private int[][] estado;
	private int valor;
	
	No(int[][] estado, int valor){
		this.estado = estado;
		this.valor = valor;
	}	

	public int[][] getEstado() {
		return estado;
	}

	public void setEstado(int[][] estado) {
		this.estado = estado;
	}

	public int getValor() {
		return valor;
	}


	public void setValor(int valor) {
		this.valor = valor;
	}

	@Override
	public int compareTo(No o) {
		if (this.valor == o.getValor()){
			return 0;
		} else if (this.valor < o.getValor()){
			return -1;
		}else{
			return 1;
		}
	}
}
