
import java.util.Scanner;

public class Fence {
	public static void main(String[] args){
		
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int k = sc.nextInt();
		
		int[] heights = new int[n + 2]; //heights[0] == idx heights[heights.length -1] == max_height
		for(int i = 1; i <= n ; i++){
			heights[i] = sc.nextInt();
		}
		heights[heights.length -1] = Integer.MAX_VALUE;
		
		//calculate starting height
		int height = 0;
		for(int i = 1; i <= k; i++){
			height += heights[i];
		}
		heights[heights.length -1] = height;
		heights[0] = 1;
		
		for(int startIdx = 2; startIdx <= heights.length -1 -k; startIdx++){
			height = height - heights[startIdx -1] + heights[startIdx + k -1];
			if(height < heights[heights.length -1]) {
				heights[heights.length -1] = height;
				heights[0] = startIdx;
			}
		}
		
		System.out.println(heights[0]);
		sc.close();
	}
}
