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
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
 
public class ChatNBTCPCifraServer {
 
	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
 
		Selector selector = Selector.open();
 
		ServerSocketChannel serverSocketChannel = 
				ServerSocketChannel.open();
		InetSocketAddress inetSocketAddress = 
				new InetSocketAddress("localhost", 1111);
 
		// Binds the channel's socket to a local address and configures the socket to listen for connections
		serverSocketChannel.bind(inetSocketAddress); 
		serverSocketChannel.configureBlocking(false);
 
		int ops = serverSocketChannel.validOps();
		SelectionKey selectionKey = serverSocketChannel.register(selector, ops, null);
 
		while (true) {
 
			System.out.println("Servidor ativo...");
			// Selects a set of keys whose corresponding channels are ready for I/O operations
			selector.select();
 
			// token representing the registration of a SelectableChannel with a Selector
			Set<SelectionKey> selectsKeys = selector.selectedKeys();
			Iterator<SelectionKey> iteratorKey = selectsKeys.iterator();
 
			while (iteratorKey.hasNext()) {
				SelectionKey selectKey = iteratorKey.next();
 
				// Tests whether this key's channel is ready to accept a new socket connection
				if (selectKey.isAcceptable()) {
					
					SocketChannel socketChannelClient = serverSocketChannel.accept();
					socketChannelClient.configureBlocking(false);
 
					// Operation-set bit for read operations
					socketChannelClient.register(selector, SelectionKey.OP_READ);
					System.out.println("Connection Accepted: " + socketChannelClient.getLocalAddress() + "\n");

				} else if (selectKey.isReadable()) {
					
					SocketChannel socketChannelClient = 
							(SocketChannel) selectKey.channel();
					
					ByteBuffer byteBuffer = ByteBuffer.allocate(256);
					socketChannelClient.read(byteBuffer);
					String result = new String(byteBuffer.array()).trim();
 
					//Close connection when he client sends a #
					if (result.equals("#")) {
						socketChannelClient.close();
						System.out.println("Conexao parada");
					}
					
				    else{
				    	StringBuilder builder = new StringBuilder();

				    	for (int i = 0; i < result.length(); i++) {
				            char c = (char)(result.charAt(i) + Character.getNumericValue('_'));
				            builder.append(c);
				        }
				        System.out.println("Recebido: [" + builder.toString() + "]");
					}
				}
				iteratorKey.remove();
			}
		}
	}
 
}