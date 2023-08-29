# Nowcoder-Code
记录牛客网中级+高级班所写的题目代码，总结和复习用

给K出MP一算组法正扩整展数题，目你二从第一个数向最后一个数方向跳跃，每次至少跳跃1格，每个数的值表示你 从这个位置可以跳跃的最大长度。计算如何以最少的跳跃次数跳到最后一个数。 输入描述： 第一行表示有多少个数n 第二行开始依次是1到n个数，一个数一行 输出描述： 输出一行，表示最少跳跃的次数。 输入 7 2 3 2 1 2 1 5 输出 3

```java
public static int jump(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int jump = 0; // 跳了多少步
		int cur = 0; // jump步内，右边界
		int next = 0; // jump+1步内，右边界
		for (int i = 0; i < arr.length; i++) {
			if (cur < i) {
				jump++;
				cur = next;
			}
			next = Math.max(next, i + arr[i]);
		}
		return jump;
	}
```

给定两个有序数组arr1和arr2，再给定一个整数k，返回来自arr1和arr2的两个数相加和最 大的前k个，两个数必须分别来自两个数组。 【举例】 arr1=[1,2,3,4,5]，arr2=[3,5,7,9,11]，k=4。 返回数组[16,15,14,14] 

【要求】 时间复杂度达到 O(klogk)

```java
// 放入大根堆中的结构
	public static class Node {
		public int index1;// arr1中的位置
		public int index2;// arr2中的位置
		public int value;// arr1[index1] + arr2[index2]的值

		public Node(int i1, int i2, int sum) {
			index1 = i1;
			index2 = i2;
			value = sum;
		}
	}

	// 生成大根堆的比较器
	public static class MaxHeapComp implements Comparator<Node> {
		@Override
		public int compare(Node o1, Node o2) {
			return o2.value - o1.value;
		}
	}

	public static int[] topKSum(int[] arr1, int[] arr2, int topK) {
		if (arr1 == null || arr2 == null || topK < 1) {
			return null;
		}
		topK = Math.min(topK, arr1.length * arr2.length);
		int[] res = new int[topK];
		int resIndex = 0;
		PriorityQueue<Node> maxHeap = new PriorityQueue<>(new MaxHeapComp());
		HashSet<String> positionSet = new HashSet<String>();
		int i1 = arr1.length - 1;
		int i2 = arr2.length - 1;
		maxHeap.add(new Node(i1, i2, arr1[i1] + arr2[i2]));
		positionSet.add(i1 + "_" + i2);
		while (resIndex != topK) {
			Node curNode = maxHeap.poll();
			assert curNode != null;
			res[resIndex++] = curNode.value;
			i1 = curNode.index1;
			i2 = curNode.index2;
			if (!positionSet.contains((i1 - 1) + "_" + i2)) {
				positionSet.add((i1 - 1) + "_" + i2);
				maxHeap.add(new Node(i1 - 1, i2, arr1[i1 - 1] + arr2[i2]));
			}
			if (!positionSet.contains(i1 + "_" + (i2 - 1))) {
				positionSet.add(i1 + "_" + (i2 - 1));
				maxHeap.add(new Node(i1, i2 - 1, arr1[i1] + arr2[i2 - 1]));
			}
		}
		return res;
	}
```

