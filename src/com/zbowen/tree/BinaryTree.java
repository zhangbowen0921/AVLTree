package com.zbowen.tree;

import java.util.LinkedList;
import java.util.Stack;
import com.zbowen.printer.BinaryTreeInfo;
import com.zbowen.tree.AVLTree.AVLNode;

@SuppressWarnings("all")
public class BinaryTree<E> implements BinaryTreeInfo {

	protected Node<E> root;
	protected int size;
	
	public static abstract class Visitor<E> {

		protected boolean stop;
    	/**
    	 * 如果返回true 就停止遍历
    	 * @return
    	 */
		protected abstract boolean visit(E element);
    };
	
    static class Node<E> {

    	E element;
    	Node<E> left;
    	Node<E> right;
    	Node<E> parent;
    	
		public Node(E element, Node<E> parent) {
			super();
			this.element = element;
			this.parent = parent;
		}
		
		public boolean isLeaf() {
			return this.left == null && this.right == null;
		}
		
		public boolean hasTwoChildren() {
			return this.left != null && this.right != null;
		}
		
		public boolean isLeftChild() {
			return parent !=null && this == parent.left;
		}
		
		public boolean isRightChild() {
			return parent !=null && this == parent.right;
		}
	}
    
	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public void clear() {
		size = 0;
		root = null;
	}
	
	public int getHeight() {
		if (root == null) return 0;
		LinkedList<Node<E>> queue = new LinkedList<>();
		queue.add(root);
		int count = 1;
		int height = 0;
		while (!queue.isEmpty()) {
			Node<E> pop = queue.poll();
			count--;
			if (pop.left != null) {
				queue.offer(pop.left);
			}
			if (pop.right != null) {
				queue.offer(pop.right);
			}
			if (count==0) {
				height++;
				count = queue.size();
			}
		}
		return height;
	}
	
	
	/**
	 * 获取某个节点的前驱节点
	 * 
	 * @param node
	 * @return
	 */
	protected Node<E> precursorNode(Node<E> node) {
		if (node == null)
			return null;
		if (node.left != null) {
			// 该节点左节点都不等于空 那么前驱节点只能出现在该节点的左子树中
			node = node.left;
			while (node != null) {
				if (node.right == null) {
					return node;
				}
				node = node.right;
			}
		}
		while (node.parent != null && node == node.parent.left) {
			node = node.parent;
		}
		return node.parent;
	}

	/**
	 * 获取某个节点的后继节点
	 * 
	 * @param node
	 * @return
	 */
	protected Node<E> successorNode(Node<E> node) {
		if (node == null)
			return null;
		if (node.left != null) {
			// 该节点右节点都不等于空 那么后继节点只能出现在该节点的右子树中
			node = node.right;
			while (node != null) {
				if (node.left == null) {
					return node;
				}
				node = node.left;
			}
		}
		while (node.parent != null && node == node.parent.right) {
			node = node.parent;
		}
		return node.parent;
	}
	
	@Override
	public Object root() {
		return root;
	}

	
	@Override
	public Object left(Object node) {
		return ((Node<E>) node).left;
	}

	@Override
	public Object right(Object node) {
		return ((Node<E>) node).right;
	}

	@Override
	public Object string(Object obj) {
		Node<E> node = (Node<E>) obj;
		if (obj instanceof AVLNode) {
			AVLNode<E> avl = (AVLNode<E>)obj;
			return avl.element+"_p("+(avl.parent==null?"null":avl.parent.element)+")"+"_h("+avl.height+")";
		}
		return node.element+"_p("+(node.parent==null?"null":node.parent.element)+")";
	}

	/**
	 * 前序遍历 非递归
	 * 
	 * @param root 根节点
	 */
	private void preorderTraversal(Node<E> root) {
		Stack<Node<E>> stack = new Stack<Node<E>>();
		stack.add(root);
		while (!stack.isEmpty()) {
			Node<E> pop = stack.pop();
			System.out.println(pop.element);
			if (pop.right != null) {
				stack.add(pop.right);
			}
			if (pop.left != null) {
				stack.add(pop.left);
			}
		}
	}

	/**
	 * 前序遍历 
	 * 
	 */
	public void preorder(Visitor<E> visitor) {
		if (visitor == null) return;
		preorder(root, visitor);
	}
	
	private void preorder(Node<E> node, Visitor<E> visitor) {
		if (node == null || visitor.stop) return;
		visitor.stop = visitor.visit(node.element);
		preorder(node.left, visitor);
		preorder(node.right, visitor);
	}

	/**
	 * 中序遍历
	 * 
	 * @param root
	 */
	private void infixorderTraversal(Node<E> root) {
		Stack<Node<E>> stack = new Stack<Node<E>>();
		Node<E> node = root;
		while (node != null | !stack.isEmpty()) {
			while (node != null) {
				stack.push(node);
				node = node.left;
			}
			Node<E> pop = stack.pop();
			System.out.println(pop.element);
			if (pop.right != null) {
				node = pop.right;
			}
		}
	}
	
	/**
	 * 中序遍历
	 * 
	 * @param root
	 */
	public void infixorder(Visitor<E> visitor) {
		if (visitor == null) return;
		infixorder(root, visitor);
	}
	
	private void infixorder(Node<E> node, Visitor<E> visitor) {
		if (node==null || visitor.stop) return;
		infixorder(node.left, visitor);
		if (visitor.stop) return;
		visitor.stop = visitor.visit(node.element);
		infixorder(node.right, visitor);
	}
	

	/**
	 * 后序遍历
	 * 
	 * @param root 根节点
	 */
	private void epilogueTraversal(Node<E> root) {
		Stack<Node<E>> stack = new Stack<>();
		LinkedList<Node<E>> list = new LinkedList<>();
		Node<E> node = root;
		while (node != null || !stack.isEmpty()) {
			while (node != null) {
				stack.push(node);
				node = node.left;
			}
			Node<E> peek = stack.peek();
			if (peek.right != null) {
				boolean contains = list.contains(peek);
				if (contains) {
					Node<E> pop = stack.pop();
					System.out.println(pop.element);
				} else {
					list.add(peek);
					node = peek.right;
				}
			} else {
				Node<E> pop = stack.pop();
				System.out.println(pop.element);
			}

		}
	}
	
	
	/**
	 * 后序遍历
	 * @param visitor
	 */
	public void epilogue(Visitor<E> visitor) {
		if (visitor == null) return;
		epilogue(root, visitor);
	}
	
	private void epilogue(Node<E> node, Visitor<E> visitor) {
		if (node==null || visitor.stop) return;
		infixorder(node.left, visitor);
		infixorder(node.right, visitor);
		if (visitor.stop) return;
		visitor.stop = visitor.visit(node.element);
	}
	
	/**
	 * 层序遍历
	 * @param node 根节点
	 */
	public void sequence(Visitor<E> visitor) {
		if (visitor==null) return;
		sequenceTraversal(root, visitor);
	}
	
	private void sequenceTraversal(Node<E> node, Visitor<E> visitor) {
		if (node == null)
			return;
		LinkedList<Node<E>> queue = new LinkedList<>();
		queue.add(node);
		while (!queue.isEmpty()) {
			Node<E> poll = queue.poll();

			if (visitor.stop) return;
			visitor.visit(poll.element);
			
			if (poll.left != null) {
				queue.offer(poll.left);
			}
			if (poll.right != null) {
				queue.offer(poll.right);
			}
		}
	}

}
