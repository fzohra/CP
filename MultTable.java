import java.util.Scanner;
public class MultTable {

	public static void main(String[] args){

		Scanner sc = new Scanner(System.in);
		long n = sc.nextLong();
		long m = sc.nextLong();
		long k = sc.nextLong();
		
		long result;
		if(k == 1 || k == m*n){
			result = k;
		}
		else{
			/* Do a binary search for the kth largest element */
			result = binary_search(n, m, k);
		}
		System.out.println(result);
		sc.close();
	}
	
	/* 
	 * Does a binary search for the kth largest elements over the
	 * range of products for an n by m table (from 1 to n*m)   
	 * */
	public static long binary_search(long n, long m, long k) {
		long low = 0;
		long high = n * m;
		long middle = 0, curr = 0;
		
		while (high > low) {
			middle = (low + high) / 2;
			curr = 0;
			
			for (long i = 1; i <= n; i++) {
				if(middle / i > m){
					curr += m;
				}
				else{
					curr += (middle/i); // middle
				}
			}
			if (curr < k) {
				//search the other half
				low = middle + 1;
			}
			else{
				high = middle ;
			}
		}
		return low;
	}
}
