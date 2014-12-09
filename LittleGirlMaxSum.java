import java.util.*;

public class LittleGirlMaxSum {

	public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int q = sc.nextInt();
        int[] input = new int[n + 1];
        int[] frequencies = new int[n + 2];   
        
        for (int i = 1; i <= n; ++i)
            input[i] = sc.nextInt();      
        
        for (int i = 0; i < q; ++i) {
        	int l = sc.nextInt();
        	int r = sc.nextInt();
        	
        	frequencies[l]++;
        	frequencies[r + 1]--;                        
        }
        for (int i = 1; i <= n; ++i) 
        	frequencies[i] = frequencies[i] + frequencies[i - 1];        
        
        Arrays.sort(input);
        Arrays.sort(frequencies, 1, n+1);
        
        long sum = 0;
        for (int i = 1; i <= n ; ++i)
            sum += ((long)frequencies[i])*(long)input[i];                      
        
        System.out.println(sum);
    }
}
