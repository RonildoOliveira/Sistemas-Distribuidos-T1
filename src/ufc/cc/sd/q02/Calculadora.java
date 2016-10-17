package ufc.cc.sd.q02;

public class Calculadora {

	public static void main(String[] args) {
		String exp = "34+12";
				
		if(exp.contains("+")){
			System.out.println("mais");
			exp = exp.replace("+", "#");
			
			String[] parts = exp.split("#");
			String part1 = parts[0];
			String part2 = parts[1];
			
			System.out.println(Integer.parseInt(part1));
			System.out.println(Integer.parseInt(part2));
			
		}
		if(exp.contains("-")){System.out.println("menos");}
		if(exp.contains("*")){System.out.println("vezes");}
		if(exp.contains("/")){System.out.println("divid");}
		
	}
}
