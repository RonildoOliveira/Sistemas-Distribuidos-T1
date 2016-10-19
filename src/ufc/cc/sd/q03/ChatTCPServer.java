package ufc.cc.sd.q03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatTCPServer {
	
	public static void main(String[] args) throws IOException {
		int port = 6666;
		System.out.println("Esperando conexões na porta - " + port);
		
		ServerSocket ss = new ServerSocket(port); //Instanciando Socket
		Socket clienteSocket = ss.accept(); //Socket pra aceitar conexões
		
		System.out.println("Conexão recebida de " + clienteSocket.getInetAddress());
		
		//cria duas threads para enviar e receber do cliente
		ThreadRecebeCliente receber = new ThreadRecebeCliente(clienteSocket);
		Thread thread = new Thread(receber);
		thread.start();
		
		ThreadEnviaCliente send = new ThreadEnviaCliente(clienteSocket);
		Thread thread2 = new Thread(send);
		thread2.start();
		//ss.close();
	}

}

class ThreadRecebeCliente implements Runnable{
	Socket clienteSocket = null;
	BufferedReader buffReader = null;
	
	//construtor
	public ThreadRecebeCliente(Socket clienteSocket){
		this.clienteSocket = clienteSocket;
	}
	
	
	public void run() {
		try{
			buffReader = new BufferedReader(new InputStreamReader(this.clienteSocket.getInputStream()));		
		
			String mensagem;
				while(true){
					//atribuindo mensagem do cliente para variável mensagem
					while((mensagem = buffReader.readLine())!= null){
						if(mensagem.equals("exit")){
							break;//fecha o socket se exit
						}
						System.out.println("do Cliente: " + mensagem);//mostra a mensagem do cliente
					}
				this.clienteSocket.close();
				System.exit(0);
				}		
			} catch(Exception ex){System.out.println(ex.getMessage());}
	}
} // fim da classe ThreadRecebeCliente


class ThreadEnviaCliente implements Runnable{
	PrintWriter pwPrintWriter;
	Socket clienteSocket = null;
	
	//construtor
	public ThreadEnviaCliente(Socket clienteSocket){
		this.clienteSocket = clienteSocket;
	}
	
	//método da clase runnable
	public void run() {
		try{
		pwPrintWriter =new PrintWriter(new OutputStreamWriter(this.clienteSocket.getOutputStream()));
			while(true){
				String mensagemParaCliente = null;
				BufferedReader input = new BufferedReader(new InputStreamReader(System.in));//pegar entrada do usuário
				
				mensagemParaCliente = input.readLine();// pegar a mensagem para enviar pro cliente
				
				pwPrintWriter.println(mensagemParaCliente);//envia a mensagem pro Cliente com printWriter
				pwPrintWriter.flush();//descarrega o PrintWriter
			}
		} catch(Exception ex){System.out.println(ex.getMessage());}	
	}
}

