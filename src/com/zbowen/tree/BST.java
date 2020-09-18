package com.zbowen.tree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

import com.zbowen.printer.BinaryTreeInfo;
import com.zbowen.tree.BinaryTree.Node;

@SuppressWarnings("all")
public class BST<E> extends BinaryTree<E>  {
	
	private Comparator<E> comparator;

	public BST() {
		this(null);
	}

	public BST(Comparator<E> comparator) {
		this.comparator = comparator;
	}

	
	public boolean contains(E element) {
		return node(element)!=null;
	}
	
	@SuppressWarnings("unchecked")
	private int compare(E e1, E e2) {
		if (comparator != null)
			return comparator.compare(e1, e2);
		return ((Comparable<E>) e1).compareTo(e2);
	}
	
	public void add(E element) {
		elementNotNullCheck(element);
		size++;
		if (root == null) {
			root = createNode(element, null);
			// 子类实现恢复平衡
			afterAdd(root);
			return;
		}
		Node<E> node = root;
		Node<E> parent = null;
		int cmp = 0;
		while (node != null) {
			cmp = compare(element, node.element);
			parent = node;
			if (cmp < 0) {
				node = node.left;
			} else if (cmp > 0) {
				node = node.right;
			} else {
				node.element = element;
				return;
			}
		}
		
		Node<E> newNode = createNode(element, parent);
		if (cmp < 0) {
			parent.left = newNode;
		} else {
			parent.right = newNode;
		}
		//让子类去 恢复平衡
		afterAdd(newNode);
	}
	
	protected void afterAdd(Node<E> node) { }
	
	protected void afterRemove(Node<E> node) { }
	
	protected Node<E> createNode(E element, Node<E> parent) {
		return new Node<E>(element, parent);
	}

	public void remove(E element) {
		Node<E> node = node(element);
		remove(node);
	}

	
	
	/**
	 * 根据元素获取节点
	 * 
	 * @param element
	 * @return
	 */
	private Node<E> node(E element) {
		if (root == null)
			return root;
		Node<E> node = root;
		while (node != null) {
			int compare = compare(element, node.element);
			if (compare > 0) {
				node = node.right;
			} else if (compare < 0) {
				node = node.left;
			} else {
				return node;
			}
		}
		return node;
	}

	

	/***
	 * 删除某个节点
	 * 
	 * @param node
	 */
	private void remove(Node<E> node) {
		if (node == null) return;
		
		if (node.hasTwoChildren()) {
			Node<E> precursor = precursorNode(node);
			node.element = precursor.element;
			node = precursor;
		}
		
		Node<E> child = node.left != null ? node.left : node.right;
		if (child != null) {
			child.parent = node.parent;
			if (node.parent == null) { // 说明node是度为1点节点并且是根节点
				root = child;
			} else if (node.parent.left == node) {
				node.parent.left = child;
			} else {
				node.parent.right = child;
			}
			afterRemove(node);
		} else if (node.parent == null) { // 说明node是叶子节点 也是根节点
			root = null;
			afterRemove(node);
		} else { // 说明node是叶子节点 不是根节点
			if (node.parent.left == node) {
				node.parent.left = null;
			} else {
				node.parent.right = null;
			}
			afterRemove(node);
		}
		size--;
	}

	
	private void elementNotNullCheck(E element) {
		if (element == null) {
			throw new IllegalArgumentException("element must not be null");
		}
	}
}
