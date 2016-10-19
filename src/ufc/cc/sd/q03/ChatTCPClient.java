package ufc.cc.sd.q03;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatTCPClient {

	public static void main(String[] args)
	{
		try {
			Socket sock = new Socket("localhost",6666); //criando socket local
			// Criando duas threads, uma pra envio e outra pra receber
			ThreadEnvio threadEnvio = new ThreadEnvio(sock);
			Thread thread = new Thread(threadEnvio);
			thread.start();
			
			ThreadRecebe recieveThread = new ThreadRecebe(sock);
			Thread thread2 =new Thread(recieveThread);
			thread2.start();
		} catch (Exception e) {System.out.println(e.getMessage());} 
	}
}
class ThreadRecebe implements Runnable{
	//Instanciando
	Socket socket = null;
	BufferedReader receber = null;
	
	//construtor
	public ThreadRecebe(Socket sock) {
		this.socket = sock;
	}
	
	//metodo da classe runnable
	public void run() {
		try{
		receber = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		String msgRecebida = null;
			while((msgRecebida = receber.readLine())!= null){
				System.out.println("do Servidor: " + msgRecebida);
			}
		}catch(Exception e){System.out.println(e.getMessage());
	}
	}
}

class ThreadEnvio implements Runnable{
	Socket socket = null;
	PrintWriter print = null;
	BufferedReader brinput = null;
	
	public ThreadEnvio(Socket sock){
		this.socket = sock;
	}
	
	//metodo de runnable
	public void run(){
		try{
			if(socket.isConnected())
			{
				System.out.println("Cliente conectado a " + socket.getInetAddress());
				this.print = new PrintWriter(socket.getOutputStream(), true);	
			
			while(true){
				
				brinput = new BufferedReader(new InputStreamReader(System.in));
				String msgServidor = null;
				msgServidor = brinput.readLine();
				this.print.println(msgServidor);
				this.print.flush(); // limpar o printWriter
			
				if(msgServidor.equals("SAIR"))
				break;
			}
		socket.close();}}catch(Exception e){System.out.println(e.getMessage());}
	}
}

