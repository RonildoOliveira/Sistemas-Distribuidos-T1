package ufc.cc.sd.q02;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
public class CalculadoraUDPServer{
    public static void main(String args[]){
    	
    	DatagramSocket aSocket = null;
		try{
			System.out.println("Servidor Online...");
	    	aSocket = new DatagramSocket(6666); // create socket at agreed port
	    	
			byte[] buffer = new byte[1000];
 			while(true){
 				
 				DatagramPacket request = new DatagramPacket(buffer, buffer.length);
  				aSocket.receive(request);
  				
  				String expression = handleExpression(new String(request.getData(),0,request.getLength()));

  				DatagramPacket reply = new DatagramPacket(expression.getBytes(), expression.length(), 
        				request.getAddress(), request.getPort());
    			
    			aSocket.send(reply);
    		}
		}catch (SocketException e){System.out.println("Socket: " + e.getMessage());
		}catch (IOException e) {System.out.println("IO: " + e.getMessage());
		}finally {if(aSocket != null) aSocket.close();}
    }

	private static String handleExpression(String expression){
		String part1 = null;
		String part2 = null;
		
		if(expression.contains("+")){
			try {
				expression = expression.replace("+", "#");
				String[] parts = expression.split("#");
				part1 = parts[0];
				part2 = parts[1];
				
				return String.valueOf(Integer.parseInt(part1)+Integer.parseInt(part2));
			} catch (Exception e) {
				return "NaN";
			}			
		}
		
		else if(expression.contains("-")){
			try {
				expression = expression.replace("-", "#");
				String[] parts = expression.split("#");
				part1 = parts[0];
				part2 = parts[1];
				
				return String.valueOf(Integer.parseInt(part1)-Integer.parseInt(part2));
			} catch (Exception e) {
				return "NaN";
			}
		}
		
		else if(expression.contains("*")){
			try {
				expression = expression.replace("*", "#");
				String[] parts = expression.split("#");
				part1 = parts[0];
				part2 = parts[1];
				
				return String.valueOf(Integer.parseInt(part1)*Integer.parseInt(part2));
			} catch (Exception e) {
				return "NaN";
			}
		}
		
		else if(expression.contains("/")){
			try {
				expression = expression.replace("/", "#");
				String[] parts = expression.split("#");
				part1 = parts[0];
				part2 = parts[1];
				
				return String.valueOf(Integer.parseInt(part1)/Integer.parseInt(part2));
			} catch (Exception e) {
				return "NaN";
			}
		}
		
		else{
			return "Not a Valid Expression";
		}
	}

    
}
