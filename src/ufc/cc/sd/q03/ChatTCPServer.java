package ufc.cc.sd.q03;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Servidor{
	
	private List<PrintStream> clientes;
	private int porta = 6666;
	
	public Servidor(int porta){
		this.porta = porta;
		this.clientes = new ArrayList<PrintStream>();
	}
	
	public void executa(){
		try {
			//Abre socket servidor
			ServerSocket serv = new ServerSocket(this.porta);
			System.out.println("Servidor rodando na porta "+ serv.getLocalPort());
			Scanner scan = new Scanner(System.in);
			while(true){
				//Para conectar mais de um cliente
				//precisamos criar threads.
				Socket client = serv.accept();
				String nome = null;
				
				System.out.println("Diga seu nome: ");
				nome = scan.nextLine();
				System.out.println("Bem vindo " + nome);
				
				// adiciona saida do cliente à lista
			    PrintStream ps = new PrintStream(client.getOutputStream());
			    this.clientes.add(ps);
				
				//Cria uma thread do servidor para tratar a conexão
				Tratamento tratamento = new Tratamento(client.getInputStream(), this);
				
				Thread t = new Thread(tratamento);
				t.start();//inicia essa thread
			}			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void broadcastMsg(String msg){
		//envia msg pra todos conectados
		for(PrintStream cliente: this.clientes){
			cliente.println(msg);
		}
	}
	
	public class Tratamento implements Runnable { 
		   private InputStream cliente;
		   private Servidor servidor;
		 
		   public Tratamento(InputStream cliente, Servidor servidor) {
		     this.cliente = cliente;
		     this.servidor = servidor;
		   }
		 
		   public void run() {
		     // quando chegar uma msg, distribui pra todos
		     Scanner s = new Scanner(this.cliente);
		     while (s.hasNextLine()) {
		       servidor.broadcastMsg(s.nextLine());
		     }
		     s.close();
		   }
	}
	
	public static void main(String[] args) {
		Servidor s = new Servidor(6666);
		s.executa();
	}
}
