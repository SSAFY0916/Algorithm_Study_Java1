
![header](https://capsule-render.vercel.app/api?type=waving&height=200&color=0:B2E6FF,100:FFB2D6&text=BOJ%202533&fontColor=FFFFFF&fontAlign=80&fontAlignY=35&fontSize=50)

# **🔒Problem**

> BOJ 2533 사회망 서비스(SNS)
> 

# 💻**Code**

```python
import sys
from collections import deque
import heapq

n = int(sys.stdin.readline())
tree = {i: [] for i in range(1, n + 1)}
childrens = [[] for _ in range(n+1)]
depths = [0] * (n+1)
parents = [0] * (n+1)
for _ in range(n-1):
    u, v = map(int, sys.stdin.readline().split())
    tree[u].append(v)
    tree[v].append(u)

dq = deque()
dq.append(1)
visited = [False] * (n + 1)
while dq:
    cur = dq.popleft()
    visited[cur] = True
    depths[cur] = depths[parents[cur]] + 1
    for child in tree[cur]:
        if visited[child]:
            continue
        parents[child] = cur
        childrens[cur].append(child)
        dq.append(child)

pq = []
for i in range(1, n+1):
    if not childrens[i]:
        heapq.heappush(pq, [-depths[parents[i]], parents[i]])

earlyadaptor = [False] * (n+1)
earlyadaptor[0] = 0
answer = 0
while pq:
    d, cur = heapq.heappop(pq)
    if earlyadaptor[cur] or cur == 0:
        continue
    flag = True
    for child in childrens[cur]:
        if not earlyadaptor[child]:
            flag = False
            break
    if flag:
        if earlyadaptor[parents[cur]]:
            heapq.heappush(pq, [d+2, parents[parents[cur]]])
        else:
            heapq.heappush(pq, [d+1, parents[cur]])
        continue
    earlyadaptor[cur] = True
    answer += 1
    heapq.heappush(pq, [d+2, parents[parents[cur]]])

print(answer)
```

# **🔑Description**

> 가장 처음에 문제를 접근했던 방법은 트리를 그리고 모든 짝수 깊이의 노드들을 얼리어답터로 만들거나 모든 홀수 깊이의 노드들을 얼리어답터로 만드는 것이었다.\
> 이 방법은 예제부터 틀렸었다\
> 두 번째 시도는 리프노드부터 한 칸씩 건너뛰면서 부모노드를 얼리어답터로 만드는 것이었다.\
> 리프노드의 부모, 리프노드의 부모의 부모의 부모, ... 이런 방식이었는데 틀렸다.\
> 세 번째 시도는 인접노드가 많은 노드들부터 얼리어답터로 만들면서 모두가 아이디어를 받아들일 때까지 반복하는 것이었는데 틀렸다,\
> 네 번째 시도는 지금의 방법인데 우선순위큐가 아닌 그냥 큐를 사용했었다.\
> bfs의 역방향 탐색과 같은 느낌이었는데 인덱스 순서대로 들어가다보니까 트리의 중간부터 탐색된 경우가 있었을 거라고 생각해서\
> 깊이 정보까지 함께 우선순위에 넣어서 밑에서부터 탐색하는 방식으로 풀어서 통과했다..

# **📑Related Issues**

> 오랜만에 파이썬으로도 시도했었는데 생각한 방법이 구현으로 잘 안돼서 어려웠고 방법도 잘 생각이 안났었다.\
> 재귀로 탐색하는 방법을 정훈님 설명으로 들었는데 맛있어 보였다.\
> 잘 안 풀려서 겁쟁이의 쉼터를 들렸는데 더 헷갈렸다.\
> 파이썬으로 푼 건 시간이 엄청나게 걸렸다.\
> 파이썬으로도 이왕 푼거 마크다운에는 파이썬 코드 붙여넣었습니다.

# **🕛Resource**

| Memory | Time ||
| --- | --- | --- |
| 431412`KB` | 8012`ms` |`Python`|
| 416000`KB` | 2656`ms` |`JAVA`|