package com.zbowen;

public interface IBinaryTree<E> {

	int size();  //是否为空
	boolean isEmpty(); //是否为空
	void clear(); //清空所有元素
	boolean contains(E element); //是否包含某元素
	void add(E element); //添加元素
	void remove(E element); //删除元素
	

}
