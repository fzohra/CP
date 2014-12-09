import java.util.Scanner;

public class BMO {
	    public static void main(String[] args) {
	        
	    	Scanner sc = new Scanner(System.in);
	        
	    	long n = sc.nextLong();
	      long k = sc.nextLong();

       	//find v for n and k
        if(n == 1){
        	System.out.println(1);
        }
        else{
        	System.out.println(binary_search(n, k));
        }
      } 

    	public static long binary_search(long n, long k) {
    		long low = 1;
    		long high = n;
    		long prev_v = 0, v = n, middle;
    		long sum =0;
    		
    		while ((low < high)){
  		      middle = (low + high)/2;
  		      if (middle == prev_v) break;
  		      
  		      sum = sum(middle, k);
  		      
  		      if (sum >= n){
  		        v = middle;
  		        high = middle;
  		      }
  		      else{
  		    	  //don't update v
  		        low = middle;
  		      }
  		      prev_v = middle;
  		  }
    		return v;
    	}

  	  //get the sum for the lines of code written if v lines were written first
      private static long sum(long v, long k){
          long sum = 0;
          long p = 1;
          
          while (v >= p){
              sum += v/p;
              p *= k;
          }
          return sum;
      }
}
