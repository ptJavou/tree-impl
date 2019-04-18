package test;

import red_black_tree.Node;
import red_black_tree.RedBlackTree;

public class TestRedBlackTree {

	public static void main(String[] args) {
		Node<Item> root = new Node<Item>(new Item(1, "Itm1", 99.99));
		RedBlackTree<Item> tree = new RedBlackTree<Item>();
		
		tree.insert(root.getKey());
		tree.insert(new Item(5, "itm5", 99.99));
		tree.insert(new Item(34, "itm34", 99.99));
		tree.insert(new Item(52, "itm52", 99.99));
		tree.insert(new Item(123, "itm123", 99.99));
		tree.insert(new Item(2, "itm2", 99.99));
		tree.insert(new Item(1, "itm1", 99.99));
        
		tree.showTree();
	}
}
