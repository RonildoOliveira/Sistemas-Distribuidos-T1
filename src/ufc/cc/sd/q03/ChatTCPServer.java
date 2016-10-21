/**
 * 
 * UNIVERSIDADE FEDERAL DO CEARÁ - CAMPUS QUIXADÁ
 * CIÊNCIA DA COMPUTAÇÃO - SISTEMAS DISTRIBUÍDOS
 * 
 * PROF. PAULO REGO
 * 
 * DIEINISON JACK   #368339
 * RONILDO OLIVEIRA #366763
 * 
 * CÓDIGOS DISPONÍVEIS EM:
 * https://github.com/RonildoOliveira/Sistemas-Distribuidos-T1
 * 
 **/

package ufc.cc.sd.q03;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChatTCPServer{
	
	private List<PrintStream> clientes;
	private int porta = 6666;
	
	public ChatTCPServer(int porta){
		this.porta = porta;
		this.clientes = new ArrayList<PrintStream>();
	}
	
	@SuppressWarnings("resource")
	public void executa(){
		try {
			//Open socket server
			ServerSocket serv = new ServerSocket(this.porta);
			System.out.println("Server rodando na porta "+ serv.getLocalPort());
			Scanner scan = new Scanner(System.in);
			while(true){
				//Connect more clients with a thread
				Socket client = serv.accept();
				String nome = null;
				
				System.out.println("Diga seu nome: ");
				nome = scan.nextLine();
				System.out.println("Bem vindo " + nome);
				
				//Add the out client to list
			    PrintStream ps = new PrintStream(client.getOutputStream());
			    this.clientes.add(ps);
				
				//Make a thread in server to handle connection
				Tratamento tratamento = 
						new Tratamento(client.getInputStream(), this);
				
				Thread t = new Thread(tratamento);
				//Start the thread
				t.start();
			}			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void broadcastMsg(String msg){
		//Send messages to all 
		for(PrintStream cliente: this.clientes){
			cliente.println(msg);
		}
	}
	
	public class Tratamento implements Runnable { 
		   private InputStream cliente;
		   private ChatTCPServer servidor;
		 
		   public Tratamento(InputStream cliente, ChatTCPServer servidor) {
		     this.cliente = cliente;
		     this.servidor = servidor;
		   }
		 
		   public void run() {
		     // Broadcast
		     Scanner s = new Scanner(this.cliente);
		     while (s.hasNextLine()) {
		       servidor.broadcastMsg(s.nextLine());
		     }
		     s.close();
		   }
	}
	
	public static void main(String[] args) {
		ChatTCPServer s = new ChatTCPServer(6666);
		s.executa();
	}
}
