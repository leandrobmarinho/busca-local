package MLP;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.crypto.Data;

import sun.reflect.generics.tree.Tree;

import MLP.dados.Entrada;

public class Rede {

	private int numeroDeCamadas; //camadas escondidas e camada de saída
	private List<Integer> neuroniosPorCamada; 
	private int numeroDeAtributos;
	private int neuroniosEscondidos;
	private int neuroniosSaida;
	private List<Conexao> conexoes;
	private List<Neuronio> neuronios;
	private Entrada camadaDeEntrada;
	private int posicaoSaidaDaTupla;
		
	private Backpropagation algoritmo;
	
	
	public Rede(int camadas, List<Integer> neuroniosPorCamada,
			int neuroniosEscondidos, int neuroniosSaida, int saida, double taxa) throws IOException {
		
		this.numeroDeCamadas = camadas;
		this.neuroniosPorCamada = neuroniosPorCamada;		
		this.conexoes = new ArrayList<>();
		this.neuronios = new ArrayList<>();
		this.camadaDeEntrada = new Entrada(saida, true);
		this.neuroniosEscondidos = neuroniosEscondidos;
		this.neuroniosSaida = neuroniosSaida;
		this.algoritmo = new Backpropagation(taxa);
		this.numeroDeAtributos = this.camadaDeEntrada.getDadosDeEntrada().get(0).getTupla().size();
		this.gerarConexoes();
		this.gerarNeuronios();
	}

	
	public void gerarConexoes(){		
		double w = 0;
		
		int origem = this.numeroDeAtributos;
		int camadaAtual = 0;
		int neuronioAtual = 0;
		int quantidadeAnterior = 0;
		int destinoMax = 0;
		Conexao novaConexao = null;
		int i = 0;
		int j = 0;
		int iInicial = 0;
		int jInicial = 0;
		
		for (Integer quantidade : this.neuroniosPorCamada) {						
			
			if(camadaAtual > 0){
				jInicial = neuronioAtual - quantidadeAnterior;
				origem = neuronioAtual ;
				iInicial = neuronioAtual ;
				destinoMax = quantidade + neuronioAtual ;								
			}else{
				destinoMax = quantidade;
				iInicial = 0;
				jInicial = 0;				
			}
			
			for (i = iInicial; i < destinoMax; i++) {
				for (j = jInicial; j < origem; j++) {
					 w = Math.random();
					 if(Math.random() > 0.5){ //Isso é necessário?
						 w = -w;
					 }
				    
					novaConexao = new Conexao(j, i, w);				

					this.conexoes.add(novaConexao);
				}
				neuronioAtual = neuronioAtual + 1;
			}							
			quantidadeAnterior = quantidade;
			camadaAtual = camadaAtual + 1;
		}
		
	}
	
	
	public void gerarNeuronios() {
		
		int totalNeuronios = this.neuroniosEscondidos + this.neuroniosSaida;
		int neuroniosDeEntrada = this.neuroniosPorCamada.get(0);
		double bias = 0;
		for (int i = 0; i < totalNeuronios; i++) {
			
			Neuronio neuronio = new Neuronio();
			if(i >= neuroniosDeEntrada){
				bias = Math.random();
				if(Math.random() > 0.5){
					bias = -bias;
				}
				neuronio.setBias(bias);
			}
			for (Conexao conexao : this.conexoes) {
				if (conexao.getDestino() == neuronio.getNumero()) {
					neuronio.getConexoesEntrada().add(conexao);
				}
			}
			
			this.neuronios.add(neuronio);
			
		}
		this.neuronios.get(this.neuroniosEscondidos).setNeuronioSaida(true);//só para 1 camada 
		this.neuronios.get(this.neuroniosEscondidos + 1).setNeuronioSaida(true);			
	
	}
	
	
	public Neuronio getNeuronioPorNumero(int numero){
		Neuronio neuronioProcurado = null;
		for(Neuronio neuronio : this.neuronios){
			if(numero == neuronio.getNumero()){
				neuronioProcurado = neuronio;
				break;
			}
		}
		return neuronioProcurado;
	}
	
	public Backpropagation getAlgoritmo() {
		return algoritmo;
	}

	
	


	public int getNumeroDeAtributos() {
		return numeroDeAtributos;
	}


	public int getNeuroniosEscondidos() {
		return neuroniosEscondidos;
	}


	public void setNeuroniosEscondidos(int neuroniosEscondidos) {
		this.neuroniosEscondidos = neuroniosEscondidos;
	}


	public int getNeuroniosSaida() {
		return neuroniosSaida;
	}


	public void setNeuroniosSaida(int neuroniosSaida) {
		this.neuroniosSaida = neuroniosSaida;
	}


	public List<Conexao> getConexoes() {
		return conexoes;
	}


	public void setConexoes(List<Conexao> conexoes) {
		this.conexoes = conexoes;
	}


	public Entrada getCamadaDeEntrada() {
		return camadaDeEntrada;
	}

	public int getNumeroDeCamadas() {
		return numeroDeCamadas;
	}


	public void setNumeroDeCamadas(int numeroDeCamadas) {
		this.numeroDeCamadas = numeroDeCamadas;
	}

	
	
	public int getPosicaoSaidaDaTupla() {
		return posicaoSaidaDaTupla;
	}


	public List<Integer> getNeuroniosPorCamada() {
		return neuroniosPorCamada;
	}


	public List<Neuronio> getNeuronios() {
		return neuronios;
	}


	public static void main(String args[]){
		
		List<Integer> neuroniosPorCamada = new ArrayList<Integer>();
		neuroniosPorCamada.add(3);
		neuroniosPorCamada.add(2);
		

		int numeroDeCamadas = neuroniosPorCamada.size();
		int cont = 0;
		double taxaAprendizagem = 0.1;
		
		for(Integer quantidade : neuroniosPorCamada){
			cont = cont + quantidade;
		}
		int neuroniosEscondidos = cont - neuroniosPorCamada.get(neuroniosPorCamada.size() - 1);
		
		int neuroniosSaida = neuroniosPorCamada.get(neuroniosPorCamada.size() - 1);
		int posicaoDaSaida = 4; //posição na tupla onde se encontra a resposta fornecida para a rede 
		try {
			Rede rede = new Rede(numeroDeCamadas,
					neuroniosPorCamada, neuroniosEscondidos, neuroniosSaida, posicaoDaSaida, taxaAprendizagem);
			
			System.out.println("quantidade de conexões: "+ rede.getConexoes().size());
			rede.getAlgoritmo().treinamento(rede);
                        
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
}