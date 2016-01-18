import java.util.*;
import java.io.*;

class DFA {
	boolean recognizeString(int move[][], int accept_state[], String word) {
	  int len = word.length();
	  int accept = 0;
	  for (int i = 0; i < len; i++) {
	    accept = move[accept][word.charAt(i) - 'a'];
	  }
	  
	  for (int i = 0; i < accept_state.length; i++) {
	    if (accept_state[i] == accept) return true;
	  }
	  return false;
	}
	public static void main(String args[]) throws IOException {
		int n, m;
		BufferedReader in = new BufferedReader(new FileReader("DFA.in"));
		// BufferedReader in = new BufferedReader(new FileReader(".//src//DFA.in"));
		StringTokenizer st = new StringTokenizer(in.readLine());
		
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		
		while (n != 0) {
			int[][] move = new int[n][m];
			
			for(int i=0; i<n; i++) {
				st = new StringTokenizer(in.readLine());
				for (int j=0; j<m; j++)
					move[i][j] = Integer.parseInt(st.nextToken());
			}
			
			String[] temp = in.readLine().split("\\s");
			int[] accept = new int[temp.length];
			
			for (int i=0; i<accept.length; i++) 
			  accept[i] = Integer.parseInt(temp[i]);
			
			String word = in.readLine();
			
			while (word.compareTo("#") != 0) {
				DFA dfa = new DFA();
				if (dfa.recognizeString(move, accept, word))
				  System.out.println("YES");
				else 
				  System.out.println("NO");
				word = in.readLine();
			}
			
			st = new StringTokenizer(in.readLine());
			n = Integer.parseInt(st.nextToken());
			m = Integer.parseInt(st.nextToken());
		}
	}
}