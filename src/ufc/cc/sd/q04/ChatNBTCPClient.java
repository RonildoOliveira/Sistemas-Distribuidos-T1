package ufc.cc.sd.q04;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
 
public class ChatNBTCPClient {
 
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException, InterruptedException {
 
		InetSocketAddress inetSocketAddress = new InetSocketAddress("localhost", 1111);
		SocketChannel socketChanel = SocketChannel.open(inetSocketAddress);
 
		System.out.println("Conectando ao Servidor...");
 
		String msg = null;
		while (msg != "#") {
			
			msg = new Scanner(System.in).nextLine();
			
			byte[] message = new String(msg).getBytes();
			ByteBuffer buffer = ByteBuffer.wrap(message);
			socketChanel.write(buffer);
 
			System.out.println("Enviado: [" + msg + "]");
			buffer.clear();

		}
		socketChanel.close();
	}
}