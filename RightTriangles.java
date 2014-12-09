import java.util.*;
public class RightTriangles{

	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		
		int n, m;
		n = sc.nextInt();
		m = sc.nextInt();
		int[][] field = new int[n + 1][m + 1];
		for(int i = 0; i < n; i ++){
			String input = sc.next();
			for(int j = 0; j < m; j++){
				//find the total number of stars in each row and each column. 
				//then go through the field checking how many right triangles each star 
				//contributes to and sum these up
				if(input.charAt(j) == '*'){
					field[i][j] = 1;
					field[i][m]++;
					field[n][j]++;
				}
			}
		}
		
		long result = 0;
		for(int i = 0; i < n; i++){
			for(int j = 0; j < m; j++){
			
				if(field[i][j] != 0){
					//all possible combinations of the right triangles that can be made
					//not including star at i, j itself
					result += (field[i][m] - 1) * (field[n][j] - 1);
				}
			}
		}
		System.out.println(result);
	}
}
