package MLP;

import java.util.ArrayList;
import java.util.List;


public class Neuronio {

	static int ID = 0;
	private int numero;
	private List<Conexao> conexoesEntrada;
	private List<Conexao> conexoesSaida;
	private boolean neuronioSaida;
	
	private double bias;

	private double sinalDeSaida;

	public Neuronio() {
		this.numero = ID++;
		this.conexoesEntrada = new ArrayList<>();
		this.conexoesSaida = new ArrayList<>();
		this.neuronioSaida = false;
	}

	
	public double funcaoDeAtivacao(double campoInduzido) {
		int k = 1;

		this.sinalDeSaida = 1 / (1 + Math.exp(-k * campoInduzido));

		return this.sinalDeSaida;

	}

	public int getNumero() {
		return numero;
	}
	
	
	




	public double getBias() {
		return bias;
	}


	public void setBias(double bias) {
		this.bias = bias;
	}


	public List<Conexao> getConexoesEntrada() {
		return conexoesEntrada;
	}


	public void setConexoesEntrada(List<Conexao> conexoesEntrada) {
		this.conexoesEntrada = conexoesEntrada;
	}


	public List<Conexao> getConexoesSaida() {
		return conexoesSaida;
	}


	public void setConexoesSaida(List<Conexao> conexoesSaida) {
		this.conexoesSaida = conexoesSaida;
	}

	

	public double getSinalDeSaida() {
		return sinalDeSaida;
	}


	public void setSinalDeSaida(double sinalDeSaida) {
		this.sinalDeSaida = sinalDeSaida;
	}


	public static void main(String args[]) {

	}


	public boolean isNeuronioSaida() {
		return neuronioSaida;
	}


	public void setNeuronioSaida(boolean neuronioSaida) {
		this.neuronioSaida = neuronioSaida;
	}

}