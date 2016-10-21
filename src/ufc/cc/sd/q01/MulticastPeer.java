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

public class MulticastPeer{
	public static void main(String args[]){

		// Arguments give message contents and destination M cast group
		MulticastSocket s =null;
		try {
			InetAddress group = InetAddress.getByName(args[0]);
			s = new MulticastSocket(6789);
			s.joinGroup(group);
			byte [] m = args[0].getBytes();
			DatagramPacket messageOut = 
					new DatagramPacket(m, m.length, group, 6789);
			s.send(messageOut);	
			byte[] buffer = new byte[1000];
			for(int i=0; i<3; i++) {		
				// get messages from others in group
				DatagramPacket messageIn = 
						new DatagramPacket(buffer, buffer.length);
				s.receive(messageIn);
				System.out.println("Received: " + new String(messageIn.getData()));
			}
			s.leaveGroup(group);		
		}catch (SocketException e){
			System.out.println("Socket: " + e.getMessage());
		}catch (IOException e){
			System.out.println("IO: " + e.getMessage());
		}finally {
			if(s != null)
				s.close();
		}
	}		      	

}
