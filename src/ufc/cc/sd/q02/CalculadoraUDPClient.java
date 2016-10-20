package ufc.cc.sd.q02;

import java.net.*;
import java.io.*;
public class CalculadoraUDPClient{
	public static void main(String args[]){ 
		// args give message contents and destination hostname
		DatagramSocket aSocket = null;
		try {
			aSocket = new DatagramSocket();
			String nome = "397";
			byte [] m = nome.getBytes();
			InetAddress aHost = InetAddress.getByName("localhost");
			int serverPort = 6666;		                                                 
			
			DatagramPacket request =
					new DatagramPacket(m,  nome.length(), aHost, serverPort);
			aSocket.send(request);
			
			byte[] buffer = new byte[1000];
			DatagramPacket reply = new DatagramPacket(buffer, buffer.length);	
			aSocket.receive(reply);
			System.out.println("Reply: " + new String(reply.getData()));	
		}catch (SocketException e){System.out.println("Socket: " + e.getMessage());
		}catch (IOException e){System.out.println("IO: " + e.getMessage());
		}finally {if(aSocket != null) aSocket.close();}
	}		      	
}
