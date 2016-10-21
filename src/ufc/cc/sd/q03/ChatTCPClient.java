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
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ChatTCPClient {
	@SuppressWarnings("unused")
	private String host;
	@SuppressWarnings("unused")
	private int porta;
	   
	public ChatTCPClient (String host, int porta) {
	    this.host = host;
	    this.porta = porta;
	}
	
	public void executa(){
		try {
			Socket cliente = new Socket("localhost", 6666);
			//Thread to receive messages from server
			Receber r = new Receber(cliente.getInputStream());
			Thread t = new Thread(r);
			t.start();
			
			//Read messages and sends to server
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
		   
		@SuppressWarnings("resource")
		public void run() {
		// Receive messages from server and shows in the console
		Scanner s = new Scanner(this.servidor);
			while (s.hasNextLine()) {
				System.out.println(s.nextLine());
			}
		}
	}
	
	public static void main(String[] args) {
		ChatTCPClient cli = new ChatTCPClient("localhost", 6666);
		cli.executa();
	}
}
