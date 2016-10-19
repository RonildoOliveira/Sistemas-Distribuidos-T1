package ufc.cc.sd.q04;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.SocketChannel;
public class ChatNBTCPServer {
	
	// Cria um canal de socket não bloqueante para um host e uma porta específica.
	// connect() é chamando no novo canal antes dele retorna. 
	public static SocketChannel createSocketChannel(String hostName, int port) throws IOException { 
		// Cria um canal de socket não bloqueante
		SocketChannel sChannel = SocketChannel.open(); sChannel.configureBlocking(false); 
		// Envia o pedido de conexão para o servidor; este método é não bloqueante 
		sChannel.connect(new InetSocketAddress(hostName, port)); 
		
		return sChannel; 
	} 
	
	public static void main (String args[]) {
		try{
			int serverPort = 7896; // the server port
			ServerSocket listenSocket = new ServerSocket(serverPort);
			
			SocketChannel sChannel = createSocketChannel("localhost", 8089); 
			while (!sChannel.finishConnect()) { 
				Socket clientSocket = listenSocket.accept();
				Connection c = new Connection(clientSocket);
			}
		} catch(IOException e) {System.out.println("Listen socket:"+e.getMessage());}
	}
}
class Connection extends Thread {
	DataInputStream in;
	DataOutputStream out;
	Socket clientSocket;
	public Connection (Socket aClientSocket) {
		try {
			clientSocket = aClientSocket;
			in = new DataInputStream( clientSocket.getInputStream());
			out =new DataOutputStream( clientSocket.getOutputStream());
			this.start();
		} catch(IOException e) {System.out.println("Connection:"+e.getMessage());}
	}
	public void run(){
		try {			                 // an echo server

			String data = in.readUTF();	                  // read a line of data from the stream
			out.writeUTF(data);
		}catch (EOFException e){System.out.println("EOF:"+e.getMessage());
		} catch(IOException e) {System.out.println("readline:"+e.getMessage());
		} finally{ try {clientSocket.close();}catch (IOException e){/*close failed*/}}
		

	}
}
