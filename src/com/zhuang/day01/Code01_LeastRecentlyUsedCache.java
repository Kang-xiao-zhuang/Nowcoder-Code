package com.zhuang.day01;

import java.util.HashMap;

public class Code01_LeastRecentlyUsedCache {

	// 节点
	public static class Node<V> {
		public V value;
		public Node<V> last;
		public Node<V> next;

		public Node(V value) {
			this.value = value;
		}
	}

	// 双向链表节点
	public static class NodeDoubleLinkedList<V> {
		private Node<V> head;
		private Node<V> tail;

		public NodeDoubleLinkedList() {
			this.head = null;
			this.tail = null;
		}

		public void addNode(Node<V> newNode) {
			if (newNode == null) {
				return;
			}
			if (this.head == null) {
				this.head = newNode;
			} else {
				this.tail.next = newNode;
				newNode.last = this.tail;
			}
			this.tail = newNode;
		}

		// 移动节点到末尾
		public void moveNodeToTail(Node<V> node) {
			if (this.tail == node) {
				return;
			}
			// 如果移动的是头节点
			if (this.head == node) {
				// 头结点指向 node.next
				this.head = node.next;
				// last 置为空
				this.head.last = null;
			} else {
				// 切断联系
				node.last.next = node.next;
				node.next.last = node.last;
			}
			// node 成为 tail 节点
			node.last = this.tail;
			node.next = null;
			this.tail.next = node;
			this.tail = node;
		}

		// 删除头节点
		public Node<V> removeHead() {
			if (this.head == null) {
				return null;
			}
			Node<V> res = this.head;
			if (this.head == this.tail) {
				this.head = null;
				this.tail = null;
			} else {
				this.head = res.next;
				res.next = null;
				this.head.last = null;
			}
			return res;
		}

	}

	public static class MyCache<K, V> {
		private final HashMap<K, Node<V>> keyNodeMap;
		private final HashMap<Node<V>, K> nodeKeyMap;
		private final NodeDoubleLinkedList<V> nodeList;
		private final int capacity;

		public MyCache(int capacity) {
			if (capacity < 1) {
				throw new RuntimeException("should be more than 0.");
			}
			this.keyNodeMap = new HashMap<>();
			this.nodeKeyMap = new HashMap<>();
			this.nodeList = new NodeDoubleLinkedList<>();
			this.capacity = capacity;
		}

		public V get(K key) {
			if (this.keyNodeMap.containsKey(key)) {
				Node<V> res = this.keyNodeMap.get(key);
				this.nodeList.moveNodeToTail(res);
				return res.value;
			}
			return null;
		}

		public void set(K key, V value) {
			if (this.keyNodeMap.containsKey(key)) {
				Node<V> node = this.keyNodeMap.get(key);
				node.value = value;
				this.nodeList.moveNodeToTail(node);
			} else {
				Node<V> newNode = new Node<V>(value);
				this.keyNodeMap.put(key, newNode);
				this.nodeKeyMap.put(newNode, key);
				this.nodeList.addNode(newNode);
				if (this.keyNodeMap.size() == this.capacity + 1) {
					this.removeMostUnusedCache();
				}
			}
		}

		private void removeMostUnusedCache() {
			Node<V> removeNode = this.nodeList.removeHead();
			K removeKey = this.nodeKeyMap.get(removeNode);
			this.nodeKeyMap.remove(removeNode);
			this.keyNodeMap.remove(removeKey);
		}

	}

	public static void main(String[] args) {
		MyCache<String, Integer> testCache = new MyCache<String, Integer>(3);
		testCache.set("A", 1);
		testCache.set("B", 2);
		testCache.set("C", 3);
		System.out.println(testCache.get("B"));
		System.out.println(testCache.get("A"));
		testCache.set("D", 4);
		System.out.println(testCache.get("D"));
		System.out.println(testCache.get("C"));
	}

}
