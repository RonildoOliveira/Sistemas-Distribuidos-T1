package ufc.cc.sd.q03;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ChatTCPCliente {
	private String host;
	private int porta;
	   
	public ChatTCPCliente (String host, int porta) {
	    this.host = host;
	    this.porta = porta;
	}
	
	public void executa(){
		try {
			Scanner scanN = new Scanner(System.in);
			String nome = null;
			Socket cliente = new Socket("localhost", 6666);
			System.out.println("Diga seu nome: ");
			nome = scanN.nextLine();
			System.out.println("Bem vindo " + nome);
	
			//Thread to receive mesages from server
			Receber r = new Receber(cliente.getInputStream());
			Thread t = new Thread(r);
			t.start();
			
			//Lê msgs do teclado e manda pro servidor
			Scanner scan = new Scanner(System.in);
			PrintStream msg = new PrintStream(cliente.getOutputStream());
			while(scan.hasNextLine()){
				msg.println(scan.nextLine());
			}
			
			msg.close();
			scan.close();
			cliente.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	  
	public class Receber implements Runnable {
		
		private InputStream servidor;
		 
		public Receber(InputStream servidor) {
			this.servidor = servidor;
		}
		   
		public void run() {
		// recebe msgs do servidor e imprime no console
		Scanner s = new Scanner(this.servidor);
			while (s.hasNextLine()) {
				System.out.println(s.nextLine());
			}
		}
	}
	
	public static void main(String[] args) {
		ChatTCPCliente cli = new ChatTCPCliente("localhost", 6666);
		cli.executa();
	}
}
