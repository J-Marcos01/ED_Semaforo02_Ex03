package controller;

import java.util.concurrent.Semaphore;

import br.edu.fateczl.quicksort.Operacoes;


/*
 * 3. Numa prova de triatlo moderno, o circuito se dá da seguinte maneira:
 *   
 *   pontos : 1º 250 , 2º 240 , 3º 230 .
 * 
- 3Km de corrida onde os atletas correm entre 20 e 25 m / 30 ms (3000 m de 20 a 25m p/ 30ms)

- 3 tiros ao alvo com pontuação de 0 a 10  ( 05 atletas por vez 03 tiros de 0 a 10 pontos cada , 01 tiro a cada 0,5 a 3s p/ tiro)
- 5 km de ciclismo onde os atletas pedalam entre 30 e 40 m/ 40 ms

25 atletas participam da prova e largam juntos, no entanto, apenas 5 armas de tiro estão a disposição. Cada atleta leva de 0,5 a 3s por tiro.
 Conforme os atletas finalizam o circuito de corrida, em ordem de chegada, pegam a arma para fazer os disparos.
  Uma vez encerrados os disparos, a arma é liberada para o próximo, e o atleta segue para pegar a bicicleta e continuar o circuito.
Para determinar o ranking final dos atletas, considera-se a seguinte regra:
- O primeiro que chegar recebe 250 pontos, o segundo recebe 240, o terceiro recebe 230, ... , o último recebe 10.
- Soma-se à pontuação de cada atleta, o total de pontos obtidos nos 3 tiros (somados)
Ordenar a pontuação e exibir o resultado final do maior pontuador para o menor.
 */
public class ThreadTriatlo extends Thread{
	
	private  int atleta ;
	private static int [] ptos=new int [26];
	private static Semaphore semaforoTiro;
	private static Semaphore semaforoPodio;
	private static int podio=3;
    private Operacoes op=new Operacoes();
	private static int chegada=0;
	public ThreadTriatlo(int atleta,Semaphore semaforoTiro,Semaphore semaforoPodio) {
		this.atleta=atleta;
		this.semaforoTiro=semaforoTiro;
		this.semaforoPodio=semaforoPodio;
				
	}
	
	public void run ()
	{
		
		correr();
		try {
			semaforoTiro.acquire();
			atirar();
		}catch (Exception e) {
			System.err.println(e.getMessage());
		}finally {
			semaforoTiro.release();
		}
		pedalar();
	}
	
	private void pedalar() {
		System.err.println("Atleta : "+atleta+" Iniciou a prova de bike .");
		int percusso=5000;
		int percorrido=0;
		
		while(percorrido<=percusso)
		{
			percorrido+=(int)((Math.random()*11)+30);
			System.out.println("Atleta : "+atleta +" -Pedalou : "+percorrido +"mts");
			
			try {
				sleep(40);
				}catch (Exception e) {
					System.out.println(e.getMessage());
				}		
			if(percorrido>=5000)
			{
				System.err.println("Atleta : "+atleta + " Terminou a prova de bike" );
				chegada ++;
				try {
					semaforoPodio.acquire();
					if(podio==0 && percorrido>=5000)
						{
							ptos[atleta]=250;
							System.err.println("Atleta : "+atleta+ " primeiro colocado "+ptos[atleta] +" ptos"  );
							podio++;
						}
					else if(podio==1 && percorrido>=5000)
						{
							ptos[atleta]=240;
							System.err.println("Atleta : "+atleta+ " segundo colocado "+ptos[atleta] +" ptos"  );
							podio++;
						}
					else if(podio==2 && percorrido>=5000)
						{
							ptos[atleta]=230;
							System.err.println("Atleta : "+atleta+ " terceiro colocado " +ptos[atleta] +" ptos" );
							podio++;
						}		
				} catch (InterruptedException e) {
					System.out.println(e.getMessage());
				}finally {
					semaforoPodio.release();
				}
				if(chegada==25)
				{
					op.quickSort(ptos, 0, 25);
					
					for(int i=25 ;i<1;i--)
					{
						System.out.println("Atleta" );
					}
					
					
				}
			}
		}
		
		
	}
	private void atirar() {
		System.err.println("Atleta : "+atleta+" Iniciou a prova de tiro .");
		int disparos=1;
		int tiro=0;
		while(disparos<4)
		{
				tiro=(int)((Math.random()*11)+0);
				ptos[atleta]+=tiro;
				System.out.println("Atleta : "+atleta+ " Efetuou o "+disparos+ "º tiro\ne fez pontos  :"+tiro+"ptos" );
				disparos++;
				if(disparos==4)
				{
					System.out.println("Atleta : "+atleta+ ", Encerrou a prova de tiro \ntotal de ptos  :"+ptos[atleta]+"ptos" );
				}
		}
		
	}
	private void correr() {
		int percusso=3000;
		int percorrido=0;
		
		while(percorrido<=percusso)
		{
			percorrido+=(int)((Math.random()*5.1)+20);
			System.out.println("Atleta : "+atleta +" -Correu : "+percorrido);
			
			try {
				sleep(30);
				}catch (Exception e) {
					System.out.println(e.getMessage());
				}		
			if(percorrido>=3000)
			{
				System.err.println("Atleta : "+atleta + " Terminou a prova de corrida" );
		
				try {
					semaforoPodio.acquire();
					if(podio==3 && percorrido>=3000)
						{
							ptos[atleta]=250;
							System.err.println("Atleta : "+atleta+ " primeiro colocado "+ptos[atleta] +" ptos"  );
							podio--;
						}
					else if(podio==2 && percorrido>=3000)
						{
							ptos[atleta]=240;
							System.err.println("Atleta : "+atleta+ " segundo colocado "+ptos[atleta] +" ptos"  );
							podio--;
						}
					else if(podio==1 && percorrido>=3000)
						{
							ptos[atleta]=230;
							System.err.println("Atleta : "+atleta+ " terceiro colocado " +ptos[atleta] +" ptos" );
							podio--;
						}		
				} catch (InterruptedException e) {
					System.out.println(e.getMessage());
				}finally {
					semaforoPodio.release();
				}
			}
		
				
		}
		
}
}	
