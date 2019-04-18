package test;

import avl_tree.AVLTree;
import avl_tree.Node;

public class TestAVLTree {

	public static void main(String[] args) {
		Node<Item> root = new Node<Item>(new Item(1, "Itm1", 99.99));
		AVLTree<Item> tree = new AVLTree<>(root);
		
		tree.insert(new Item(5, "itm5", 99.99));
		tree.insert(new Item(34, "itm34", 99.99));
		tree.insert(new Item(2, "itm2", 99.99));
		tree.insert(new Item(1, "itm1", 99.99));
		
        
		tree.showTree();
	}
	
}
