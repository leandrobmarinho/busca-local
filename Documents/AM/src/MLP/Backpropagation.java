package MLP;

import MLP.dados.Registro;

public class Backpropagation {
	
	private double n;
	
	public Backpropagation(double n){
		this.n = n;
	}
	
	public void treinamento(Rede rede){
				
		//forward
		//camada de entrada
		Neuronio neuronioAtual = null;
		int neuroniosPrimeiraCamada = rede.getNeuroniosPorCamada().get(0);
		int i;
		int iNeuronioAtual;
		for(Registro registro : rede.getCamadaDeEntrada().getDadosDeEntrada()){
			
			for(i = 0; i < neuroniosPrimeiraCamada; i++){
				neuronioAtual = rede.getNeuronios().get(i);				
				this.processarEntradasCamadaEntrada(neuronioAtual, registro);
																								
			}
			iNeuronioAtual = i;			
			
			for(Neuronio neuronio : rede.getNeuronios()){
				if(neuronio.getNumero() >= iNeuronioAtual){
					this.processarEntradas(neuronio, rede);
					if(neuronio.isNeuronioSaida()){
						System.out.println("sinal de saída neuronio "+neuronio.getNumero()+": "+ neuronio.getSinalDeSaida());
						this.ajustarPesos(neuronio, registro);
					}
				}
			}
			
			
		}				
		
	}
	
	
	public void processarEntradasCamadaEntrada(Neuronio neuronio, Registro registro){
		double campoInduzido = 0;
		
		double dado = 0;
		
		for(Conexao conexao : neuronio.getConexoesEntrada()){
			
			dado = registro.getTupla().get(conexao.getOrigem());
			campoInduzido = campoInduzido + (dado * conexao.getW());
		}
		neuronio.setSinalDeSaida(neuronio.funcaoDeAtivacao(campoInduzido));
	}
	
	
	
	public void processarEntradas(Neuronio neuronio, Rede rede){		
		double campoInduzido = 0;
		double estimulo = 0;
		for(Conexao conexao : neuronio.getConexoesEntrada()){
			
			estimulo = rede.getNeuronioPorNumero(conexao.getOrigem()).getSinalDeSaida();
			
			campoInduzido = campoInduzido + (conexao.getW() * estimulo);
		}
		campoInduzido = neuronio.getBias() + campoInduzido;
		neuronio.setSinalDeSaida(neuronio.funcaoDeAtivacao(campoInduzido));
				
	}
	
	
	public void ajustarPesos(Neuronio neuronio, Registro registro){
		double delta = 0;
		double saidaGerada = neuronio.getSinalDeSaida();
		double saidaReal = registro.getSaida();
		int k = 1;
		delta = k*saidaGerada*(1 - saidaGerada)*(saidaReal - saidaGerada);
		
		for(Conexao conexao : neuronio.getConexoesEntrada()){
			conexao.setW(conexao.getW() + this.n*delta*saidaGerada);
		}
		
	}
	

}