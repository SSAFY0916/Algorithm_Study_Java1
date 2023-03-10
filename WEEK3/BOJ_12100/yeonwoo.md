![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%20N&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **πProblem**

> [BOJ 12100 2048(Easy)](https://www.acmicpc.net/problem/12100)

<br>
<br>

# **Code**

```java
package bj_12100_2048easy;
//μ€κ³μμ=10:50
//μ€κ³μ’λ£=14:15
//κ΅¬νμ’λ£=15:50
//
import java.io.*;
import java.util.StringTokenizer;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static int[] dr = {-1,0,1,0};
	static int[] dc = {0,-1,0,1};
	
	
	static int n;
	static int[][] map;
	static int[][] cloneMap;
	static boolean[][] combined;
	
	static int maxBlock = Integer.MIN_VALUE;
	
	static int[] seq = new int[5];
	static boolean[] visited = new boolean[5];
	//2048 κ²μ κ΅¬ν
	//1. κ²μν μμ±
		//1-1 νμ΄ν¬κΈ°(N)μ λ§λ λ°°μ΄ μμ±
		//1-2 λ°°μ΄μ κ° λ°μ
		
	//2. μ΄λ κ΅¬ν
		//2-0 κ²°ν© μ΄κΈ°ν
		//2-1 λ°©ν₯μ ν
		//2-2 λ°©ν₯μ λ°λ₯Έ λΈλ‘μ΄λμμμ ν
		//2-3 μμλλ‘ μ΄λ
			//1.λ§΅ λ°μ΄λ λ€μ λΈλ­ λμ¬λκΉμ§ λ°©ν₯ νμ
			//*μ΄λμ€μ μ΄λ - κΈ°μ‘΄μμΉ μ­μ  μ μμΉ μ€μ 
			//2-1.μ΄ λ€μ μ’νκ° λ§΅ λ°μ΄κ±°λ λ°©λ¬Έμ’νλ©΄ νμΉΈμ μΌλ‘ μ΄λμ€μ νκ³  λ€μλΈλ‘
			//2-2. μ΄ λ€μ μ’νκ° λΉλ°©λ¬Έμ΄λ©΄ μ΄ λ€μμ’ν λ°©λ¬Έμ²λ¦¬νκ³  μ΄ λ€μμ’νλ‘ μ΄λμ€μ νκ³  λ€μλΈλ‘
	

	//3. 5λ² μ΄λμμΌ μ»μ μ μλ κ°μ₯ ν° λΈλ‘ κ΅¬ν
	//dfs μμ°νμ’
	
	public static void main(String[] args) throws IOException{
		//init();
		n = Integer.parseInt(br.readLine());
		map = new int[n][n];
		cloneMap = new int[n][n];
		combined = new boolean[n][n];
		
		StringTokenizer st;
		for(int i=0;i<n;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<n;j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				cloneMap[i][j] = map[i][j];
				maxBlock = Math.max(maxBlock, map[i][j]);
			}
		}
//		//move
//		printArr(map);
//		move(3);
//		printArr(map);
//		move(3);
//		printArr(map);
		dfs(0);
		System.out.println(maxBlock);
		
	}
	static void dfs(int depth) {
		if(depth==5) {
			for(int i=0;i<n;i++) {
				map[i] = cloneMap[i].clone();
			}
			
			for(int i=0;i<5;i++) {
//				System.out.println(seq[i]+" ");
				int dir = seq[i];
				move(dir);
//				printArr(map);
			}
//			System.out.println("===================================================");
			return;
		}
		
		for(int i=0;i<4;i++) {
			seq[depth] = i;
			dfs(depth+1);
		}
		
	}
	static void move(int d) {
		combined = new boolean[n][n];
		
		if(d==0) {
			for(int r=0;r<n;r++) {
				for(int c=0;c<n;c++) {
					//1.λ§΅ λ°μ΄λ λ€μ λΈλ­ λμ¬λκΉμ§ λ°©ν₯ νμ
					int[] nextRC = shoot(r,c,d);
					int nr = nextRC[0];
					int nc = nextRC[1];
					
					//2-1.μ΄ λ€μ μ’νκ° λ§΅ λ°μ΄κ±°λ λ°©λ¬Έμ’νλ©΄ νμΉΈμ μΌλ‘ μ΄λμ€μ νκ³  λ€μλΈλ‘
					if(!isIn(nr,nc) || combined[nr][nc] || map[r][c]!=map[nr][nc]) {
						changePosition(r,c,nr-dr[d],nc-dc[d]);
					}else {//κ²°ν© || μκΈ°μλ¦¬μ΄λ
						if(r==nr && c==nc) continue;//μκΈ°μλ¦¬μ΄λ
						changePosition(r,c,nr,nc);
						
						
						combined[nr][nc] = true;
						map[nr][nc]*=2;
						maxBlock = Math.max(map[nr][nc], maxBlock);
					}
					
				}
			}
		}
		else if(d==2) {
			for(int r=n-1;r>=0;r--) {
				for(int c=0;c<n;c++) {
					//1.λ§΅ λ°μ΄λ λ€μ λΈλ­ λμ¬λκΉμ§ λ°©ν₯ νμ
					int[] nextRC = shoot(r,c,d);
					int nr = nextRC[0];
					int nc = nextRC[1];
					
					//2-1.μ΄ λ€μ μ’νκ° λ§΅ λ°μ΄κ±°λ λ°©λ¬Έμ’νλ©΄ νμΉΈμ μΌλ‘ μ΄λμ€μ νκ³  λ€μλΈλ‘
					if(!isIn(nr,nc) || combined[nr][nc] || map[r][c]!=map[nr][nc]) {
						changePosition(r,c,nr-dr[d],nc-dc[d]);
					}else {//κ²°ν© || μκΈ°μλ¦¬μ΄λ
						if(r==nr && c==nc) continue;//μκΈ°μλ¦¬μ΄λ
						changePosition(r,c,nr,nc);
						
						
						combined[nr][nc] = true;
						map[nr][nc]*=2;
						maxBlock = Math.max(map[nr][nc], maxBlock);
					}
					
				}
			}
		}
		else if(d==1) {
			for(int c=0;c<n;c++) {
				for(int r=0;r<n;r++) {
					//1.λ§΅ λ°μ΄λ λ€μ λΈλ­ λμ¬λκΉμ§ λ°©ν₯ νμ
					int[] nextRC = shoot(r,c,d);
					int nr = nextRC[0];
					int nc = nextRC[1];
					
					//2-1.μ΄ λ€μ μ’νκ° λ§΅ λ°μ΄κ±°λ λ°©λ¬Έμ’νλ©΄ νμΉΈμ μΌλ‘ μ΄λμ€μ νκ³  λ€μλΈλ‘
					if(!isIn(nr,nc) || combined[nr][nc] || map[r][c]!=map[nr][nc]) {
						changePosition(r,c,nr-dr[d],nc-dc[d]);
					}else {//κ²°ν© || μκΈ°μλ¦¬μ΄λ
						if(r==nr && c==nc) continue;//μκΈ°μλ¦¬μ΄λ
						changePosition(r,c,nr,nc);
						
						
						combined[nr][nc] = true;
						map[nr][nc]*=2;
						maxBlock = Math.max(map[nr][nc], maxBlock);
					}
					
				}
			}
		}
		else if(d==3) {
			for(int c=n-1;c>=0;c--) {
				for(int r=0;r<n;r++) {
					//1.λ§΅ λ°μ΄λ λ€μ λΈλ­ λμ¬λκΉμ§ λ°©ν₯ νμ
					int[] nextRC = shoot(r,c,d);
					int nr = nextRC[0];
					int nc = nextRC[1];
					
					//2-1.μ΄ λ€μ μ’νκ° λ§΅ λ°μ΄κ±°λ λ°©λ¬Έμ’νλ©΄ νμΉΈμ μΌλ‘ μ΄λμ€μ νκ³  λ€μλΈλ‘
					if(!isIn(nr,nc) || combined[nr][nc] || map[r][c]!=map[nr][nc]) {
						changePosition(r,c,nr-dr[d],nc-dc[d]);
					}else {//κ²°ν© || μκΈ°μλ¦¬μ΄λ
						if(r==nr && c==nc) continue;//μκΈ°μλ¦¬μ΄λ
						changePosition(r,c,nr,nc);
						
						
						combined[nr][nc] = true;
						map[nr][nc]*=2;
						maxBlock = Math.max(map[nr][nc], maxBlock);
					}
					
				}
			}
		}
		
	}
	static void changePosition(int fromR, int fromC, int toR, int toC) {
//		System.out.printf("fromR = %d, fromC = %d, toR = %d, toC = %d\n",fromR,fromC,toR,toC);
//		if(fromR == toR && fromC == toC) return;
		int val = map[fromR][fromC];
		map[fromR][fromC] = 0;
		map[toR][toC] = val;
		
		
		
	}

	
	static int[] shoot(int r, int c, int d) {
		int nr = r+dr[d];
		int nc = c+dc[d];
		if(!isIn(nr,nc)) return new int[]{nr,nc};//λ²μμ΄κ³Ό
		if(map[nr][nc]!=0) return new int[] {nr,nc};//λ€λ₯ΈλΈλ‘
		return shoot(nr,nc,d);
	}
	static boolean isIn(int r, int c) {
		return r>=0 && r<n && c>=0 && c<n;
	}
	
	static void printArr(int[][] map) {
		System.out.println();
		
		for(int i=0;i<n;i++) {
			for(int j=0;j<n;j++) {
				System.out.printf("%d ", map[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}
}

```

<br>
<br>

# **πDescription**

> ```java
> //2048 κ²μ κ΅¬ν
> //1. κ²μν μμ±
> 	//1-1 νμ΄ν¬κΈ°(N)μ λ§λ λ°°μ΄ μμ±
> 	//1-2 λ°°μ΄μ κ° λ°μ
> 	
> //2. μ΄λ κ΅¬ν: moveν¨μ μμ λ°©ν₯λ³λ‘ if, else if λ‘ 4κ° κ΅¬ννμμ΅λλ€
> 	//2-0 κ²°ν© μ΄κΈ°ν
> 	//2-1 λ°©ν₯μ ν
> 	//2-2 λ°©ν₯μ λ°λ₯Έ λΈλ‘μ΄λμμμ ν
> 	//2-3 μμλλ‘ μ΄λ
> 		//1.λ§΅ λ°μ΄λ λ€μ λΈλ­ λμ¬λκΉμ§ λ°©ν₯ νμ
> 		//*μ΄λμ€μ μ΄λ - κΈ°μ‘΄μμΉ μ­μ  μ μμΉ μ€μ 
> 		//2-1.μ΄ λ€μ μ’νκ° λ§΅ λ°μ΄κ±°λ λ°©λ¬Έμ’νλ©΄ νμΉΈμ μΌλ‘ μ΄λμ€μ νκ³  λ€μλΈλ‘
> 		//2-2. μ΄ λ€μ μ’νκ° λΉλ°©λ¬Έμ΄λ©΄ μ΄ λ€μμ’ν λ°©λ¬Έμ²λ¦¬νκ³  μ΄ λ€μμ’νλ‘ μ΄λμ€μ νκ³  λ€μλΈλ‘
> ```
>
>
> ```java
> //3. 5λ² μ΄λμμΌ μ»μ μ μλ κ°μ₯ ν° λΈλ‘ κ΅¬ν
> //dfs μμ°νμ’
> 
> 
> κ°μ₯ ν° λΈλ­κ°μ 
>     1. μ²μμ Integer.MIN_VALUE ν λΉ
> 	2. map λ§λ€ λ Math.max(μ΅λλΈλ‘, νμ¬μΉΈκ°)μΌλ‘ νμΈ,
>     3. λΈλ‘ ν©μ³μ§ λ Math.max(μ΅λλΈλ‘, ν©μ³μ§κ°)μΌλ‘ νμΈ νμμ΅λλ€.
> ```

<br>
<br>

# **πRelated Issues**

> λΈλ‘ κ²°ν©μ λΈλ‘ μ΅λκ°μ κ°±μ νλ λ‘μ§μ λ§μ§λ§μ λ£μλλ°
>
> λ°©ν₯λ³ μΌμ΄μ€λ₯Ό λ°λ‘ κ΅¬νν΄λκ³  ν λ°©ν₯μλ§ μ΅λκ° κ°±μ  λ‘μ§μ λ£μ΄μ
>
> κ°μ΄ μ λλ‘ λμ€μ§ μλ λ¬Έμ λ₯Ό κ²ͺμμ΅λλ€
>
> 

<br>
<br>

# **πResource**

| Memory  | Time  | Implementation Time |
| ------- | ----- | ------------------- |
| 77828KB | 476ms | 5 Hour 00 Minutes   |
