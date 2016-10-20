package ufc.cc.sd.q03;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente {
	private String host;
	private int porta;
	   
	public Cliente (String host, int porta) {
	    this.host = host;
	    this.porta = porta;
	}
	
	public void executa(){
		try {
			Socket cliente = new Socket("localhost", 6666);
			//Thread para receber mensagens do servidor
			Receber r = new Receber(cliente.getInputStream());
			Thread t = new Thread(r);
			t.start();
			
			//Lê msgs do teclado e manda pro servidor
			Scanner scan = new Scanner(System.in);
			PrintStream msg = new PrintStream(cliente.getOutputStream());
			while(scan.hasNext()){
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
		Cliente cli = new Cliente("localhost", 6666);
		cli.executa();
	}
}
