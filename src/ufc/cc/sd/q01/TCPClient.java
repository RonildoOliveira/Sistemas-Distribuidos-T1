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

package ufc.cc.sd.q01;

import java.net.*;
import java.io.*;

public class TCPClient {
	public static void main (String args[]) {
		
		// Arguments supply message and host name
		Socket s = null;
		try{
			int serverPort = 7896;
			s = new Socket("localhost", serverPort);    
			DataInputStream in = new DataInputStream( s.getInputStream());
			DataOutputStream out = new DataOutputStream( s.getOutputStream());
			
	      	// UTF is a string encoding see 4.4
			out.writeUTF("Hello");
			
			// read a line of data from the stream
			String data = in.readUTF();	    
			System.out.println("Received: "+ data) ;
			
		}catch (UnknownHostException e){
			System.out.println("Socket:"+e.getMessage());
		}catch (EOFException e){
			System.out.println("EOF:"+e.getMessage());
		}catch (IOException e){
			System.out.println("readline:"+e.getMessage());
		}finally {
			if(s!=null) 
				try {
					s.close();
				}catch (IOException e){
					System.out.println("close:"+e.getMessage());
				}
		}
	}
}
