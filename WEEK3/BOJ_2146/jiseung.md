
![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%202146&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **πProblem**

> BOJ 2146 λ€λ¦¬ λ§λ€κΈ°
> 

<br>
<br>

# π»**Code**

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	
	public static void main(String[] args) throws Exception {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    	int n = Integer.parseInt(br.readLine());
    	int[][] map = new int[n][n]; 		// μ§λ
    	for(int i=0; i<n; i++) {
    		StringTokenizer st = new StringTokenizer(br.readLine());
    		for(int j=0; j<n; j++) {
    			map[i][j] = Integer.parseInt(st.nextToken());
    		}
    	}
    	int[] dr = {-1, 0 ,1 ,0};
    	int[] dc = {0, -1, 0 ,1};
    	int count = 0; 						// λλ₯μ κ°μ
    	boolean[][] visited = new boolean[n][n];
    	for(int i=0; i<n; i++) {			// μλ‘ λ€λ₯Έ λλ₯μμ νννκΈ° μν΄ λλ₯μ λλ₯ λ²νΈλ₯Ό νμ
    		for(int j=0; j<n; j++) {
    			if(visited[i][j] || map[i][j] == 0) { // μ΄λ―Έ νμνκ±°λ λ°λ€λ©΄ κ±΄λλ
    				continue;
    			}
    			Queue<int[]> q = new LinkedList<>();
    			q.add(new int[] {i, j});
    			count++;					// λλ₯ κ°μ ++
    			while(!q.isEmpty()) {
    				int[] pair = q.poll();
    				if(visited[pair[0]][pair[1]]) {
    					continue;
    				}
    				visited[pair[0]][pair[1]] = true;
    				map[pair[0]][pair[1]] = count;	// λλ₯ λ²νΈ νμ
    				for(int k=0; k<4; k++) {
    					int newx = pair[0] + dr[k];
    					int newy = pair[1] + dc[k];
    					if(newx < 0 || newx >= n || newy < 0 || newy >= n) {
    						continue;
    					}
    					if(map[newx][newy] == 0) {
    						continue;
    					}
    					q.add(new int[] {newx, newy});
    				}
    			}
    		}
    	}
    	int answer = n*n; // λ€λ¦¬μ κΈΈμ΄
    	for(int i=0; i<n; i++) {
    		for(int j=0; j<n; j++) {
    			if(map[i][j] == 0) { // λ°λ€λ©΄ κ±΄λλ
    				continue;
    			}
    			Queue<int[]> q = new LinkedList<>();	// νμ¬ μΉΈ μ£Όλ³ λ°λ€λ₯Ό μ μ₯ 
    			for(int k=0; k<4; k++) {
    				int newi = i+dr[k];
    				int newj = j+dc[k];
    				if(newi<0 || newi>=n || newj<0 || newj>=n) {
    					continue;
    				}
    				if(map[newi][newj] == 0) {
    					q.add(new int[] {newi, newj, 0}); // λ°λ³΅λ¬Έμ λμλ μ μ₯λ λ°λ€κ° μλ€λ©΄ λλ₯μ κ°μ₯μλ¦¬κ° μλλ―λ‘ νμ X
    				}
    			}
    			visited = new boolean[n][n];
    			while(!q.isEmpty()) {
    				int r = q.peek()[0];
    				int c = q.peek()[1];
    				int d = q.poll()[2];
    				if(map[r][c] > 0 && map[r][c] != map[i][j]) { // λλ₯μ΄λ©΄μ νμμ μΆλ°ν λλ₯μ΄ μλ
    					answer = Math.min(answer, d); 			// μ΅μκ° λΉκ΅
    					break;									// bfsλκΉ μ’λ£
    				}
    				if(visited[r][c] || d >= answer || map[r][c] == map[i][j]) { // μ΄λ―Έ λ°©λ¬Ένκ±°λ, λ μ§§μ λ€λ¦¬λ₯Ό μ΄λ―Έ μ°Ύμκ±°λ, νμμ μΆλ°ν λλ₯μ΄λ©΄ κ±΄λλ
    					continue;
    				}
    				visited[r][c] = true;				// λ°©λ¬Έ μ²λ¦¬
    				for(int k=0; k<4; k++) {
    					int newr = r+dr[k];
    					int newc = c+dc[k];
    					if(newr<0 || newr>=n || newc<0 || newc>=n) {
    						continue;
    					}
    					q.add(new int[] {newr, newc, d+1});
    				}
    			}
    		}
    	}
    	bw.write(answer + "\n");
    	bw.flush();
        bw.close();
        br.close();
    }
}
```

<br>
<br>

# **πDescription**

> λ¨Όμ  μ§λμ λλ₯λ³λ‘ λ²νΈλ₯Ό λΆμ¬μ λμ€μ μλ‘ λ€λ₯Έ λλ₯μμ μ μ μλλ‘ νλ€. 
λ€μ μ§λλ₯Ό νμνλ©΄μ μ£Όλ³μ λ°λ€κ° μλ λλ₯ μΉΈμμ λ°λ€λ‘ BFSλ₯Ό νλ€. λμ€μ λ€λ₯Έ λλ₯μ λ§λλ©΄ νμμ μ€μ§νκ³  κ±°λ¦¬λ₯Ό μ μ₯νλ€.
λ€μ λλ₯ μΉΈμμλ BFSλ₯Ό λ°λ³΅νμ§λ§ μ΄μ μ μ μ₯λ κ±°λ¦¬λ³΄λ€ λ©λ¦¬ νμνμ§λ μλλ‘ νλ€.
> 

<br>
<br>

# **πRelated Issues**

> λλ₯λ³λ‘ λ²νΈλ₯Ό λΆμΌ λλ λλ₯ κ°μ λ€λ¦¬λ₯Ό νμν  λλ BFSλ₯Ό μ¬μ©νλλ° λ κ³Όμ μ νλλ‘ ν©μΉκ³  μΆμμ§λ§ μ½μ§ μμλ€.
μ£Όλ³μ λ°λ€κ° μλ λλ₯ μΉΈμμ μμν  μ§ μλλ©΄ μ£Όλ³μ λλ₯μ΄ μλ λ°λ€ μΉΈμμ BFSλ₯Ό μμν μ§ κ³ λ―Όνμλ€.
> 

<br>
<br>

# **πResource**

| Memory | Time |
| --- | --- |
| 139376KB | 316ms |