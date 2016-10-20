package ufc.cc.sd.q03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatTCPServidor {
	
	public static void main(String[] args) throws IOException {
		int port = 6666;
		ServerSocket ss = new ServerSocket(port); //Instanciando Socket
		System.out.println("Servidor rodando na porta "+ ss.getLocalPort());
		
		Servidor server = new Servidor(ss,null);
		server.iniciarConexao();
		
	}

}

class Servidor extends Thread{
	Socket cliente;
	private ServerSocket ss;
	private Map<String, Socket> clientesOnline = new HashMap<String, Socket>();
	//Opicional
	//private Map<String, List<String>> salaBatePapo = new HashMap<String, List<String>>();
	private PrintStream enviar;
	private BufferedReader receber;
	private String msg;
	
	
	public Servidor(ServerSocket ss, Socket s){
		this.ss = ss;
		this.cliente = s;
	}
	
	public void iniciarConexao(){
		while(true){
			try {
				cliente = ss.accept();
				if(cliente.isConnected()){
					System.out.println("Cliente conectado: " + cliente.getInetAddress().getHostName());
					Servidor novoCliente = new Servidor(ss,this.cliente);
					novoCliente.start();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void run() {
		try {
			enviar = new PrintStream(cliente.getOutputStream());
			receber = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
			Socket clienteDestino;
			PrintStream enviarCliente;
			
			while(true){
				msg = receber.readLine();
				//continuar depois
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}