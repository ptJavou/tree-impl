package red_black_tree;

public class Node<R extends Comparable<R>> {

	private R key;
	private int height;
	private COLOR color;
	private Node<R> left;
	private Node<R> right;
	private Node<R> parent;
	enum COLOR {RED, BLACK};
	
	
	public Node(R key){
		this.key = key;
		left = null;
		right = null;
		parent = null;
	}
	
	public Node<R> uncle(){
		if(parent == null || parent.getParent() == null)
			return null;
		
		if(parent.isOnLeft())
			return parent.getParent().getRight();
		else
			return parent.getParent().getLeft();
	}
	
	public Node<R> sibling(){
		if(parent == null)
			return null;
		
		if(isOnLeft())
			return parent.getRight();
		
		return parent.getLeft();
	}
	
	public void moveDown(Node<R> nParent){
		if(parent != null){
			if(isOnLeft())
				parent.setLeft(nParent);
			else
				parent.setRight(nParent);
		}
		
		nParent.setParent(nParent);
		parent = nParent;
	}
	
	public boolean hasRedChild(){
		return (left != null && left.getColor() == COLOR.RED) ||
				(right != null && right.getColor() == COLOR.RED);
	}
	public boolean isOnLeft(){
		return this == parent.getLeft();
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

	public Node<R> getParent() {
		return parent;
	}

	public void setParent(Node<R> parent) {
		this.parent = parent;
	}

	public COLOR getColor() {
		return color;
	}

	public void setColor(COLOR color) {
		this.color = color;
	}
	 
	
	
	
}
