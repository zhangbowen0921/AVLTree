package com.zbowen;

import com.zbowen.printer.BinaryTrees;
import com.zbowen.tree.AVLTree;
import com.zbowen.tree.BST;
import com.zbowen.tree.BinaryTree.Visitor;

public class Main {

	public static void main(String[] args) {
		//test1();
		int[] arr = {61, 1, 42, 81, 89, 97, 10, 20, 93, 86, 38, 5, 17, 53, 6, 72, 49, 44};
		BST<Integer> bst = new AVLTree<>();
		for (int i = 0; i < arr.length; i++) {
			bst.add(arr[i]);
		}
		for (int i = 0; i < arr.length; i++) {
			System.out.println("【"+arr[i]+"】");
			bst.remove(arr[i]);
			BinaryTrees.println(bst);
			System.out.println("-------------------------------------");
		}
		System.out.println();
		
	}

	public static void test1() {
		int[] arr = {8, 4, 13, 2, 6, 10, 7};
		BST<Integer> bst = new BST<>();
		for (int i = 0; i < arr.length; i++) {
			bst.add(arr[i]);
		}
		
		BinaryTrees.print(bst);
		System.out.println();
		System.out.print("前序：");
		bst.preorder(new Visitor<Integer>() {
			
			@Override
			protected boolean visit(Integer element) {
				System.out.print(element+" ");
				if (element==6) return true;
				return false;
			}
		});
		System.out.print("\n中序：");
		bst.infixorder(new Visitor<Integer>() {
			
			public boolean visit(Integer element) {
				System.out.print(element+" ");
				if (element==6) return true;
				return false;
			}
		});
		System.out.print("\n后序：");
		bst.epilogue(new Visitor<Integer>() {
			
			@Override
			protected boolean visit(Integer element) {
				System.out.print(element+" ");
				if (element==6) return true;
				return false;
			}
		});
	}
	
}
