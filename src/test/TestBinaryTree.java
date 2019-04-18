package test;

import binary_tree.BinaryTree;
import binary_tree.Node;

public class TestBinaryTree {

	public static void main(String[] args) {
		Node<Item> root = new Node<Item>(new Item(1, "Itm1", 99.99));
		BinaryTree<Item> tree = new BinaryTree<Item>(root);
		
		tree.insert(new Item(5, "itm5", 99.99));
		tree.insert(new Item(34, "itm34", 99.99));
		tree.insert(new Item(52, "itm52", 99.99));
		tree.insert(new Item(123, "itm123", 99.99));
		tree.insert(new Item(2, "itm2", 99.99));
		tree.insert(new Item(1, "itm1", 99.99));
		
        
		tree.showTree();
	}
}
