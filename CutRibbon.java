import java.util.Scanner;

public class CutRibbon {
	
	public static int[] memo;
	public static int a, b, c;
	public static int idx = 0;
	
	public static void main(String[] args){
		
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		a = sc.nextInt();
		b = sc.nextInt();
		c = sc.nextInt();
		
		memo = new int[n+1];
		for(int i =1; i< memo.length; i++){
			memo[i] = -2;
		}
		int result = opt(n);
		if(result < 0) result = 0;
		System.out.println(result);
		sc.close();
	}
	
	//OPT(n) = max(OPT(n-a), OPT(n-b), OPT(n-c));
	public static int opt(int n){
		if(memo[n] >= 0) return memo[n];
		if(memo[n] == -1) return -1; //invalid
		
		if(n-a >= 0){
			int n_a = opt(n-a);
			if(n_a >= 0) memo[n] = Math.max(n_a + 1, memo[n]);
			else memo[n] = Math.max(-1, memo[n]);
		}
		if(n-b >= 0){
			int n_b = opt(n-b);
			if(n_b >= 0) memo[n] =Math.max(n_b + 1, memo[n]);
			else memo[n] = Math.max(-1, memo[n]);
		}
		if(n-c >= 0){
			int n_c = opt(n-c);
			if(n_c >= 0) memo[n] = Math.max(n_c + 1, memo[n]);
			else memo[n] = Math.max(-1, memo[n]);
		}
		return memo[n];
		
	}
	
}
