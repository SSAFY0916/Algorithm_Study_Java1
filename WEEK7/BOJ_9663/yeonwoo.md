![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%20N&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 9663 N-Queen](https://www.acmicpc.net/problem/9663)

<br>
<br>

# **Code**

```java
package daily.y_2023.m_03.bj_9663_nQueen;
import java.io.*;
import java.util.Arrays;


public class Main {
    static int[] arr;
    static int N;
    static int count = 0;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) throws IOException{
        N = Integer.parseInt(br.readLine());
        arr = new int[N];//idx=열, arr[idx] = 행

        nQueen(0);//0열부터 행지정
        System.out.println(count);
    }

    public static void nQueen(int idx){
        if(idx==N){//
            count ++;
            return;
        }

        for(int i=0;i<N;i++){//depth에
            arr[idx] = i;//depth에 행지정
            if(possible(idx)){
                nQueen(idx+1);//다음 idx의 행값 지정하러 ㄱㄱ
            }
        }
    }
    
    static boolean possible(int idx){
        for(int i=0;i<idx;i++){//idx까지 지정된상태이므로 idx전까지 확인
            if(arr[i]==arr[idx]){//행이 같은, 열이 존재함
                return false;
            }
            //대각 -- 열의차 == 행의차
            if(Math.abs(arr[i]-arr[idx])==Math.abs(i-idx)){
                return false;
            }
        }
        return true;
    }
}

```

<br>
<br>

# **🔑Description**
- 백트래킹

1. arr[i] 는 i번째 퀸이 i열 arr[i]행에 있음을 뜻합니다

   1. 이게 가능한게 어짜피 행 또는 열을 공유하는 퀸은 N-Queen 규칙에서 존재할 수 없습니다

2. nQueen(idx)에서 idx열의 체스말의 행을 정합니다

   ```java
   for(int i=0;i<N;i++){//i는 놓고싶은 행
       arr[idx]=i;//행에 일단 놓고
       possible(idx);//놓아도 되는지가능성을 탐색합니다
       if(possible){//놓을 수 있다면
           nQueen(idx+1)//다음분기 진행
       }
   
   }
   ```

   #possible

   ```java
   for(int i=0;i<idx;i++){
   	arr[idx]와 행을 공유하는 열이 있는가
       arr[idx]와 대각선에 위치한 녀석이 있는가(행의차(절댓값)가 같고 열의차(절댓값)도 같으면 대각선임)
   }
   ```

   

<br>
<br>

# **📑Related Issues**

> 2차원배열 완전탐색 연습 필요
>
> 

<br>
<br>

# **🕛Resource**

| Memory | Time   | Implementation Time |
| ------ | ------ | ------------------- |
| 14492B | 5324ms |                     |
