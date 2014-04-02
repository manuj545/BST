/*
 * this class implements binary search tree
 */


import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;


public class BST<Key extends Comparable<Key>, Value> {

	private Node root;				// defines a root node
	private class Node
	{
		private Key key;			// every node has a key and a value
		private Value val;
		private int count;			// this helps in maintaings the no. of nodes in the tree
		private Node leftChild;		
		private Node rightChild;
		public Node(Key key,Value val, int count)	//constructor to intialize the node
		{
			this.key = key;
			this.val = val;
			this.count = count;
			this.leftChild = null;
			this.rightChild = null;
		}

	}
	/*
	 * this functions return the rot key of the tree
	 */
	public Key getRootKey()				
	{
		return root.key;
	}
	
	/*
	 * this function is used to return value for a given key
	 */
	public Value get(Key k)
	{
		Node currentNode = root;		// start with the root node	
		while(currentNode != null)		// copare each node until you hit the empty node
		{
			int comp = k.compareTo(currentNode.key);		// compare the given key and the key at the current node
			if(comp < 0)
				currentNode = currentNode.leftChild;
			else if (comp > 0)
				currentNode = currentNode.rightChild;
			else
				return currentNode.val;
		}
		return null;			//  key not found
	}
	
	/*
	 * this function insert a key in the tree with given value
	 * the logic is to use another recursive put method
	 * 
	 */
	public void put(Key key,Value val)
	{
		root = put(root,key,val);
	}
	private Node put(Node currentRoot,Key key,Value val)
	{
		if (currentRoot == null)			// if root is null then insert a new nodw an returthe new node
			return new Node(key,val,1);
		int comp = key.compareTo(currentRoot.key);
		if (comp < 0)
			currentRoot.leftChild = put(currentRoot.leftChild,key,val);		// this calls put recursively ans tell it to insert the new npde in the left half and just asks it to return the new root node of the left subtree
		else if (comp > 0)
			currentRoot.rightChild = put(currentRoot.rightChild,key,val);
		else
			currentRoot.val = val;		// if the node already exists then update the  value
		currentRoot.count = 1 + size(currentRoot.leftChild) + size(currentRoot.rightChild);
		return currentRoot;

	}
	public int size()
	{
		return size(root);
	}
	private int size(Node x)
	{
		if(x == null)
			return 0;
		else
			return x.count;
	}
	/*
	 * this function returns the rank of the key
	 * 1.e where is the key situated if the key are arranged in ascending order.
	 */
	public int rank(Key k)
	{
		return rank(root,k);
	}
	private int rank(Node x, Key k)
	{
		if(x == null)
			return 0;
		int comp = k.compareTo(x.key);
		if(comp < 0)
			return rank(x.leftChild,k);
		else if (comp > 0)
			return 1 + size(x.leftChild) + rank(x.rightChild,k);
		else
			return size(x.leftChild);
	}
	/*
	 * this function returns the min of key of the tree.
	 * just keep moving left until you hit null
	 */
	public Key min()
	{
		return min(root).key;
	}
	private Node min(Node n)
	{
		Node currentRoot = n;
		while(currentRoot.leftChild != null)
		{
			currentRoot = currentRoot.leftChild;
		}
		return currentRoot;
	}
	/*
	 * this function returns the floor of the key
	 * e.f if you pass 5. the function will return the next smallest key greater then 5 present in the tree
	 */
	public Key floor(Key k)
	{
		Node x = floor(root, k);
		if (x == null)
			return null;
		return x.key;

	}
	private Node floor(Node x,Key k)
	{
		if (x == null) 
			return null;
		int comp = k.compareTo(x.key);
		if (comp == 0)
			return x;
		else if (comp < 0)
			return floor(x.leftChild,k);

		Node temp = floor(x.rightChild,k);		//if the floor lies in the rightsubtree then eithr it could be
		if(temp != null)					// the root nde of the right subtree or any value that is less then the 
			return temp;						// key in the rightsubtree
		else
			return x;


	}

	public Key ceileing(Key k)
	{
		Node t = ceileing(root,k);
		if (t == null) 
			return null;
		else
			return t.key;
	}
	private Node ceileing(Node x , Key k)
	{
		if (x == null) 
			return null;
		int comp = k.compareTo(x.key);

		if (comp == 0)
			return x;
		else if (comp > 0)
		{
			return ceileing(x.rightChild,k);

		}else
		{
			Node temp = ceileing(x.leftChild,k);
			if(temp != null)
				return temp;
			else
				return x;


		}

	}
	
	/*
	 * this function implemets iterable 
	 * aso that ucliens can iterateon the object and can get all the keys presetn in the tree
	 */
	public  Iterable<Key> getkeys()
	{
		Queue<Key> q = new LinkedList<Key>();		// as Quesue is an interface cannot isntatiate
		// it directly. so we created an object forone of its implemented class
		inorder(root,q);
		return q;
	}
	private void inorder(Node x, Queue<Key> q)
	{
		if (x == null)
			return ;
		inorder(x.leftChild,q);
		q.add(x.key);
		inorder(x.rightChild,q);
	}
	public void  deleteMin()
	{
		root = deleteMin(root);
	}
	
	/*
	 * this function deletes the minimum key in the subtree
	 */
	private Node deleteMin(Node x)
	{
		if(x.leftChild == null)
			return x.rightChild;
		x.leftChild = deleteMin(x.leftChild);
		x.count = 1 + size(x.leftChild) + size(x.rightChild);
		return x;
	}
	/*
	 * this function deletes a key in the tree.
	 * it uses hibbard deletion
	 */
	public void delete(Key k)
	{
		root = delete(root,k);
	}
	private Node delete(Node x,Key k )
	{
		
		if (x == null)
			return null;
		int comp = k.compareTo(x.key);
		if(comp < 0)
			x.leftChild = delete(x.leftChild, k);
		else if(comp > 0)
			x.rightChild = delete(x.rightChild,k);
		else
		{	if(x.rightChild == null)
			return x.leftChild;
		if(x.leftChild == null)
			return x.rightChild;

		Node temp = x;			// saving the refrence of the key to be deleted
		x = min(temp.rightChild);			// this find the min. key in the right subtree, this will replace the deleted key
		x.leftChild = temp.leftChild;		
		x.rightChild = deleteMin(temp.rightChild);
		}	
		x.count = 1 + size(x.leftChild) + size(x.rightChild);
		return x;


	}
	public static void main(String [] args)
	{
		BST<Integer,String> obj = new BST<>();
		obj.put(11, "abhishek");
		obj.put(22, "manuj");
		obj.put(5, "doda");
		obj.put(2, "doda");
		obj.put(3, "doda");
		obj.put(10, "doda");
		obj.put(6, "doda");
		obj.put(15, "doda");
		/*System.out.println(obj.get(3));
		System.out.println(obj.min());
		System.out.println(obj.floor(16));
		System.out.println(obj.ceileing(122));
		System.out.println(obj.size());
		System.out.println(obj.rank(5));*/
		Iterable<Integer> i = obj.getkeys();
		for(int x:i)
			System.out.println(x);
		obj.delete(11);

		System.out.println("after deleting min");
		/* i = obj.getkeys();
		for(int x:i)
		    System.out.println(x);*/
		System.out.println(obj.getRootKey());
	}
}
