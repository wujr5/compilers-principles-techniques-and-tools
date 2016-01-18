import java.util.*;
import java.io.*;

class NFA {
	boolean recognizeString(int move[][][], int accept_state[], String word) {
	  String accept = "";
	  int len = word.length();
	  for (int i = 0; i < len; i++) {
	    if (accept.length() == 0) {
	      for (int j = 0; j < move[0][word.charAt(i) - 'a' + 1].length; j++) {
	        accept += (char) ('0' + move[0][word.charAt(i) - 'a' + 1][j]);
	      }
	    } else {
	      String new_accept = "";
	      for (int k = 0; k < accept.length(); k++) {
	        for (int j = 0; j < move[accept.charAt(k) - '0'][word.charAt(i) - 'a' + 1].length; j++) {
	          new_accept += (char) ('0' + move[accept.charAt(k) - '0'][word.charAt(i) - 'a' + 1][j]);
	        }
	      }
	      accept = new_accept;
	    }
	  }
	  
	  for (int i = 0; i < accept_state.length; i++) {
	    String temString = "";
	    temString += (char) ('0' + accept_state[i]);
	    if (accept.contains(temString)) return true;
	  }
	  return false;
	}
	
	public static void main(String args[]) throws IOException {
		int n, m;
		// BufferedReader in = new BufferedReader(new FileReader(".//src//NFA.in"));
		BufferedReader in = new BufferedReader(new FileReader("NFA.in"));
		StringTokenizer st = new StringTokenizer(in.readLine());
		
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		
		while (n != 0) {
			int[][][] move = new int[n][m][];
			for(int i=0; i<n; i++) {
				String line = in.readLine();
				int k = 0;
				for (int j=0; j<m; j++) {
					while (line.charAt(k) != '{') k++;
					k++;
					String states = "";
					
					while (line.charAt(k) != '}') {
						states = states + line.charAt(k);
						k++;
					}
					
					states = states.trim();
					if (states.length() > 0) {
						String[] stateArray = states.split(",");
						move[i][j] = new int[stateArray.length];
						for (int l=0; l<move[i][j].length; l++) 
						  move[i][j][l] = Integer.parseInt(stateArray[l].trim());
					}
					else move[i][j] = new int[0];
				}
			}
			
			String[] temp = in.readLine().split("\\s");
			int[] accept = new int[temp.length];
			
			for (int i=0; i<accept.length; i++) 
			  accept[i] = Integer.parseInt(temp[i]);
			
			String word = in.readLine();
			
			while (word.compareTo("#") != 0) {
				NFA nfa = new NFA();
				if (nfa.recognizeString(move, accept, word)) 
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