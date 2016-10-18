package ufc.cc.sd.q02;

import java.net.*;
import java.io.*;
public class CalculadoraTCPServer {
	
	public static void main (String args[]) {
		try{
			System.out.println("ok");
			int serverPort = 8590; // the server port
			ServerSocket listenSocket = new ServerSocket(serverPort);
			while(true) {
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
			
			//Convert the data to a valid expression
			out.writeUTF(handleExpression(data));
			
		}catch (EOFException e){System.out.println("EOF:"+e.getMessage());
		} catch(IOException e) {System.out.println("readline:"+e.getMessage());
		} finally{ try {clientSocket.close();}catch (IOException e){/*close failed*/}}
		

	}
	
	public String handleExpression(String expression){
		String part1 = null;
		String part2 = null;
		
		if(expression.contains("+")){
			expression = expression.replace("+", "#");
			String[] parts = expression.split("#");
			part1 = parts[0];
			part2 = parts[1];
			
			return String.valueOf(Integer.parseInt(part1)+Integer.parseInt(part2));
		}
		
		else if(expression.contains("-")){
			expression = expression.replace("-", "#");
			String[] parts = expression.split("#");
			part1 = parts[0];
			part2 = parts[1];			
			
			return String.valueOf(Integer.parseInt(part1)-Integer.parseInt(part2));
		}
		
		else if(expression.contains("*")){
			expression = expression.replace("*", "#");
			String[] parts = expression.split("#");
			part1 = parts[0];
			part2 = parts[1];
			
			return String.valueOf(Integer.parseInt(part1)*Integer.parseInt(part2));
		}
		
		else if(expression.contains("/")){
			expression = expression.replace("/", "#");
			String[] parts = expression.split("#");
			part1 = parts[0];
			part2 = parts[1];
			
			return String.valueOf(Integer.parseInt(part1)/Integer.parseInt(part2));
		}
		
		else{
			return "NaN";
		}
	}
}
