![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%20N&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> [BOJ 21609 가장 긴 증가하는 부분수열2](https://www.acmicpc.net/problem/12015)

<br>
<br>

# **Code**

```java
package daily.y_2023.m_03.d_09.bj_12015_가장긴증가하는부분수열2;
//1020~1110 포기
//
/*

 */
import java.util.Arrays;
import java.util.StringTokenizer;
import java.io.*;
public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        int[] seq = new int[N];
        int[] LIS = new int[N];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i=0;i<N;i++){
            seq[i] = Integer.parseInt(st.nextToken());
        }

        //LIS 초기 값으로 첫 번째 수열의 값을 갖는다.
        LIS[0] = seq[0];
        int lengthOfLIS = 1;

        for(int i=1;i<N;i++){//2번째 부터 순회
            int key = seq[i];//배열의 현재 값

            //LIS의 마지막 값이 배열의 현재 값보다 작다
            if(LIS[lengthOfLIS - 1]<key){
                LIS[lengthOfLIS++] = key;//LIS에 key 추가하고 lengthOfLIS증가
            }else{
                //Lower Bound 이분탐색을 진행한다.
                //lower bound: 찾는 값(배열의 현재 값) 이상인 첫 번째 인덱스
                int lo = 0;
                int hi = lengthOfLIS;
                while(lo<hi){
                    int mid = (lo+hi)/2;

                    if(LIS[mid]<key){
                        lo = mid+1;
                    }
                    else{
                        hi = mid;
                    }
                }
                LIS[lo]=key;
            }
        }
        System.out.println(lengthOfLIS);
    }

}

```

<br>
<br>

# **🔑Description**
- LIS =  가장 긴 증가하는 부분 수열

  

1. LIS에 수열의 첫 번째 값 삽입

2. 수열의 끝까지 for loop

   1. cur = 이번 값

   - cur<=LIS[-1] 이면, lowerbound(cur)을 cur로 대체
   - cur>LIS[-1]이면 LIS에 cur 삽입 

<br>
<br>

# **📑Related Issues**

> 
>
> 

<br>
<br>

# **🕛Resource**

| Memory  | Time  | Implementation Time |
| ------- | ----- | ------------------- |
| 96132KB | 544ms |                     |
