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

package ufc.cc.sd.q05;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
 
public class ChatNBTCPCifraClient {
 
	@SuppressWarnings("resource")
	public static void main(String[] args) 
			throws IOException, InterruptedException {
 
		InetSocketAddress inetSocketAddress = 
				new InetSocketAddress("localhost", 1111);
		SocketChannel socketChanel = 
				SocketChannel.open(inetSocketAddress);
 
		System.out.println("Conectando ao Servidor...");
 
		String msg = null;
		
		while (msg != "#") {
			msg = new Scanner(System.in).nextLine();
			
			byte[] message = new String(msg).getBytes();
			ByteBuffer buffer = ByteBuffer.wrap(message);
			socketChanel.write(buffer);
 
			System.out.println("Send: [" + msg + "]");
			buffer.clear();

		}
		socketChanel.close();
	}
}