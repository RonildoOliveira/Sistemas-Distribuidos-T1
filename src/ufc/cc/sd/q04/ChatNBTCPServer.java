package ufc.cc.sd.q04;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
 
public class ChatNBTCPServer {
 
	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
 
		// Multiplecador de of SelectableChannel objects
		Selector selector = Selector.open(); // selector is open here
 
		// ServerSocketChannel: selectable channel for stream-oriented listening sockets
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		InetSocketAddress inetSocketAddress = new InetSocketAddress("localhost", 1111);
 
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
			Set<SelectionKey> crunchifyKeys = selector.selectedKeys();
			Iterator<SelectionKey> crunchifyIterator = crunchifyKeys.iterator();
 
			while (crunchifyIterator.hasNext()) {
				SelectionKey myKey = crunchifyIterator.next();
 
				// Tests whether this key's channel is ready to accept a new socket connection
				if (myKey.isAcceptable()) {
					
					SocketChannel socketChannelClient = serverSocketChannel.accept();
					socketChannelClient.configureBlocking(false);
 
					// Operation-set bit for read operations
					socketChannelClient.register(selector, SelectionKey.OP_READ);
					System.out.println("Connection Accepted: " + socketChannelClient.getLocalAddress() + "\n");

				} else if (myKey.isReadable()) {
					
					SocketChannel socketChannelClient = (SocketChannel) myKey.channel();
					ByteBuffer byteBuffer = ByteBuffer.allocate(256);
					socketChannelClient.read(byteBuffer);
					String result = new String(byteBuffer.array()).trim();
 
					System.out.println("Recebido: [" + result + "]");
 
					//condicao de parada (cliente envia #)
					if (result.equals("#")) {
						socketChannelClient.close();
						System.out.println("Conexao parada");
					}
				}
				crunchifyIterator.remove();
			}
		}
	}
 
}