import java.util.*;

public class LongPath {

	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		int mod = 1000000007;

		int n = sc.nextInt();
		long[][] meta = new long[2][n+1]; //arrival portal, portals to loops through
		int[] maze = new int[n+1];
		//If you ever land on an old block, you're going to repeat the same pattern to get out
		//hence you will have to run through the same loop.

		for(int i = 1; i < maze.length; i++){
			maze[i] = sc.nextInt();
		}
		sc.close();
		
		long portals =0, temp =0;
		int pos = 1, old_pos;
		while(pos < n+1){
			if(meta[1][pos] != 0){ //if visited twice before and recorded the # of portals
				portals += meta[1][pos++];
			}
			else if(maze[pos] == -1){ //if visiting for the second time
				temp = portals - meta[0][pos]; //number of portals it takes to visit again
				meta[1][pos++] = temp % mod;
				portals = (portals + temp) % mod;
			}
			else{ //visiting for the first time
				meta[0][pos] = portals % mod;
				old_pos = pos;
				pos = maze[pos];
				maze[old_pos] = -1; //mark as visited
				portals = (portals + 1) % mod; //so much mod. so much.
			}

		}

		System.out.println(portals); 
	}
}
