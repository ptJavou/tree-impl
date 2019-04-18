package binary_tree;

public class Node<R> {

	private R key;
	private int height;
	private Node<R> left;
	private Node<R> right;
	
	
	public Node(R key){
		this.key = key;
		left = null;
		right = null;
	}


	public R getKey() {
		return key;
	}


	public void setKey(R key) {
		this.key = key;
	}


	public Node<R> getLeft() {
		return left;
	}


	public void setLeft(Node<R> left) {
		this.left = left;
	}


	public Node<R> getRight() {
		return right;
	}


	public void setRight(Node<R> right) {
		this.right = right;
	}


	public int getHeight() {
		return height;
	}


	public void setHeight(int height) {
		this.height = height;
	}
	
	
}
