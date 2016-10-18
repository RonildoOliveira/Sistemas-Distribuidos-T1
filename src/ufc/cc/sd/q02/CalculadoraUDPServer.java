package ufc.cc.sd.q02;

import java.net.*;
import java.io.*;

import javax.swing.JOptionPane;
public class CalculadoraUDPServer{
    public static void main(String args[]){
    	
    	DatagramSocket aSocket = null;
		try{
			System.out.println("ok");
	    	aSocket = new DatagramSocket(6666);
					// create socket at agreed port
			byte[] buffer = new byte[1000];
 			while(true){
 				
 				DatagramPacket request = new DatagramPacket(buffer, buffer.length);
  				aSocket.receive(request);
  				
  				String r = handleExpression(new String(request.getData(),0,request.getLength()));
  				
//    			DatagramPacket reply = new DatagramPacket(request.getData(), request.getLength(), 
//    				request.getAddress(), request.getPort());
    			   			    			
    			DatagramPacket reply = new DatagramPacket(r.getBytes(), r.length(), 
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
			return "Not a Valid Expression";
		}
	}

    
}
