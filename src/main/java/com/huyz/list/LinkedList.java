package com.huyz.list;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Create at 2018年8月16日 下午3:16:14
 *
 * @autuor huyz
 *
 * @version 1.0
 *
 *          ProjectName Interview
 *
 *          Description:
 * 
 */
public class LinkedList<T> implements List<T> {

	private Node<T> last;

	private Node<T> first;

	private int size;

	public LinkedList() {
		super();
	}

	static class Node<T> {
		Node<T> prew;
		Node<T> next;
		T item;

		public Node(Node<T> prew, T item, Node<T> next) {
			super();
			this.prew = prew;
			this.next = next;
			this.item = item;
		}

	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size <= 0;
	}

	@Override
	public boolean contains(Object o) {
		Node<T> item = first;
		if (item != null && item.equals(o)) {
			return true;
		}
		for (int i = 0; i < size; i++) {

		}
		return false;
	}

	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return null;
	}

	@Override
	public boolean add(T e) {
		linkedLast(e);
		return true;
	}

	private void linkedLast(T t) {
		final Node<T> l = last;
		Node<T> newnode = new Node<>(l, t, null);
		last = newnode;// 将最后一个节点换成新加的节点
		if (l == null) {// 第一次添加
			first = newnode;
		} else {
			l.next = newnode;
		}
		size++;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("[");
		Node<T> item = first;
		sb.append(item.item.toString() + ",");
		for (int i = 0; i < size; i++) {
			item = item.next;
			if (item != null) {
				if (i == size - 2) {
					sb.append(item.item.toString());
				} else {
					sb.append(item.item.toString() + ",");
				}
			}
		}
		return sb.append("]").toString();
	}

	@Override
	public boolean remove(Object o) {

		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public T get(int index) {
		if (!check(index)) {
			throw new IllegalArgumentException();
		}
		return getNode(index).item;
	}

	private Node<T> getNode(int index) {
		Node<T> item = first;
		for (int i = 0; i < index; i++) {
			item = item.next;
		}
		return item;
	}

	private boolean check(int index) {
		if (index < 0 || index > size) {
			throw new IllegalArgumentException();
		}
		return true;
	}

	@Override
	public T set(int index, T element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(int index, T element) {
		// TODO Auto-generated method stub

	}

	@Override
	public T remove(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int indexOf(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int lastIndexOf(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ListIterator<T> listIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		// TODO Auto-generated method stub
		return null;
	}
}
