import java.util.*;

public class Dreamoon {

	public static void main(String[] args){
		
		Scanner sc = new Scanner(System.in);
		int target = 0;
		int pos = 0;
		int unknown = 0;
		String sent = sc.next();
		String recieved = sc.next();
		
		for(int i = 0; i < sent.length(); i++){
			if(sent.charAt(i) == '+')
				target++;
			else
				target--;
		}
		for(int i = 0; i < recieved.length(); i++){
			if(recieved.charAt(i) == '+')
				pos++;
			else if(recieved.charAt(i) == '-')
				pos--;
			else{
				unknown++;
			}
		}
		/*How many different ways can you select the number of moves needed from the number of moves possible to get to the target/ total possible ways
		  	target = 0
		  	pos = 0
		  	unknown = 2
		  	target - pos 0 //distance to goal
		  			    distance to goal + moves you have to make because of the ?s
		  				0 + (2-0)/2 = 1
		    Need to make 3 moves but still be 0 steps away from current position:
		    target = 3
		    pos = 3
		    target - cur = 0 //distance to goal
		   					0 +  (3 - 0)/2
			number of moves needed = number of steps to goal + moves you can make given the ?s 		
			number of moves needed = diff + (unknown -diff /2)
			unknown choose moves = unknown!/ moves! (unknown-moves)!
		*/
		int diff = Math.abs(target - pos);

		if(diff > unknown){
			System.out.println(0);
		}
		else{
			int moves = diff + (unknown - diff)/2;
			double n = factorial(unknown, moves);
			double d = factorial(moves, Integer.MAX_VALUE);
			double result = (n/d) / (Math.pow(2, unknown));			
			System.out.println(result);	
		}
	}
	
	public static double factorial(int n, int count){
		double result =1;
		while(n >= 1 && count-- != 0){
			result *= n--;
		}
		return result;
	}
	
}
