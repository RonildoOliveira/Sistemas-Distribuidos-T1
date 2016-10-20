package ufc.cc.sd.q03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ChatTCPClient extends Thread{
	
	public static Socket sock;
	private static String nome;
	private static Scanner scan = new Scanner(System.in);
	
	
	public static void main(String[] args)
	{
		System.out.println("Diga seu nome: ");
		nome = scan.nextLine();
		try {
			
			sock = new Socket("localhost",6666); //criando socket local																																																																																																																																																																																																																																																																																																																																									
			System.out.println("Cliente conectado ao servidor!");
			
			// Criando duas threads, uma pra envio e outra pra receber
			ThreadEnvio threadEnvio = new ThreadEnvio(sock.getOutputStream());
			Thread thread = new Thread(threadEnvio);
			thread.start();
			
			ThreadRecebe recieveThread = new ThreadRecebe(sock.getInputStream());
			Thread thread2 =new Thread(recieveThread);
			thread2.start();
			
		} catch (Exception e) {System.out.println(e.getMessage());} 
	}
	
	public String getNome(){
		return this.nome;
	}
}

class ThreadEnvio implements Runnable{
	//Instanciando
	private PrintStream printStream;
	private Scanner scanner;
	private String msg;
	
	//construtor
	public ThreadEnvio(OutputStream output) {
		printStream = new PrintStream(output);
	}
	
	//método da classe thread
	public void run() {
		scanner = new Scanner(System.in);
		
		while(true){
			msg = scanner.nextLine();
			printStream.println(msg);
		}
	}
}

class ThreadRecebe implements Runnable{
	private BufferedReader entrada;
	private String msg; 
	
	public ThreadRecebe(InputStream input){
		entrada = new BufferedReader(new InputStreamReader(input));
	}
	
	//método de runnable
	public void run(){
		try {
			msg = entrada.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(msg !=null)
			System.out.println(msg);
	}
}
// Testando uma lógica se pode da certo, desconsidere por enquanto isso
//class ReceberMsgServidor implements Runnable{
//	private BufferedReader entrada;
//	private String msg;
//	
//	public ReceberMsgServidor(InputStream in) {
//		entrada = new BufferedReader(new InputStreamReader(in));
//	}
//	
//	@Override
//	public void run() {
//		String[] refat;
//		while(true){
//			try {
//				msg = entrada.readLine();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		refat = msg.split(" - ");
//		}
//	}
//	
//}

