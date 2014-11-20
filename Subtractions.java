import java.util.Scanner;

public class Subtractions {

	public static void main(String[] args){
		
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int a, b, min, max, result = 0, temp;
		while(n-->0){
			a = sc.nextInt();
			b = sc.nextInt();
			max = Math.max(a, b);
			min = Math.min(a, b);
			result = 0;
			while(true){
				result += max/min;
				if(max%min == 0){
					System.out.println(result);
					break;
				}
				/* take the min and the remainder as the previous min. */
				temp = max;
				max = min;
				min = temp%min;
			}
		}
		sc.close();
	}
	
}
