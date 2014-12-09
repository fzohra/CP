import java.util.Scanner;

public class PaintingFence {
	
	 public static void main(String[] args) throws Exception
	 {
    	  Scanner sc = new Scanner(System.in);
		    int planks = sc.nextInt();
        int[] heights = new int[planks + 1];

        for (int i = 1; i <= planks; ++i){
        	heights[i] = sc.nextInt();
        }
        int result = findMin(heights, 1, planks, 0);
        System.out.println(result);
	 }
	 
	//recursively compute the min number of planks needed in a given range.
	public static int findMin(int[] heights, int min, int max, int height)
  {
		    //base case: finished checking the range
        if (min > max) 
        	return 0;
        
        int pos = min;
        int min_height = heights[min];
        //find the minimum heights from the range for horizontal strokes
        for (int i= min+1; i <= max; ++i)
        {
            if (heights[i] < min_height)
            {
                pos = i;
                min_height = heights[i];
            }
        }
        
        int vertical_strokes = max-min +1;
        //find minimum for each of these ranges
        int horizontal_before = findMin(heights,min,pos-1,min_height);
        int horizontal_after = findMin(heights,pos+1,max,min_height);
        return Math.min(vertical_strokes, horizontal_before + horizontal_after + (min_height - height));
    }
   
}
