package binary_tree;

import tree.TreeInterface;

/**
 * 
 * Utilizei como base a implementação recursiva disponibilizada neste tutorial e adaptei com o uso do generics
 * que ele pede na interface.
 * tutorial: https://www.baeldung.com/java-binary-tree
 * 
 * Neste tutorial segue uma outra implementação de árvore utilizando uma fila
 * para ordenar os elementos:
 * https://www.geeksforgeeks.org/binary-tree-set-1-introduction/
 */
public class BinaryTree<R extends Comparable<R>> implements TreeInterface<R> {

	private Node<R> root;
	
	public BinaryTree(Node<R> root) {
		this.root = root;
	}
	
	@Override
	public void insert(R item) {
		addRecursive(this.root, item);
	}

	@Override
	public void delete(Comparable<R> key) {
		deleteRecursive(this.root, key);
	}

	@Override
	public R search(Comparable<R> key) {
		return searchNodeRecursive(this.root, key);
	}

	@Override
	public void showTree() {
		inorder(this.root);
	}

	@Override
	public void height() {
		System.out.println("Binary tree height: " + sumRecursiveHeight(this.root));
	}

	@Override
	public void degree() {
		System.out.println("Binary tree tree degree: " + getBalance(this.root));
	}

	/**
	 * Adiciona uma nova chave na árvore de forma recursiva, utilizando o algoritmo
	 * de inserção BST (chaves menores serão inseridas a esquerda e as maiores a direita).
	 */
	private Node<R> addRecursive(Node<R> current, R key){
		if(current == null)
			return new Node<R>(key);
		
		R currentKey = current.getKey();
		if(key.compareTo(currentKey) == -1){
			current.setLeft( addRecursive(current.getLeft(), key) );
		}
		else if(key.compareTo(currentKey) == 1){
			current.setRight( addRecursive(current.getRight(), key) );
		}
		else{
			return current;
		}
		
		return current;
	}
	
	/**
	 * Para busca, utilizei o algoritmo de busca BFS Inorder Traversal
	 * em que a busca é iniciada pelos últimos nós da arvore (nós folhas ou nós sem filhos).
	 * 
	 * Tutorial que utilizei:
	 * https://www.geeksforgeeks.org/bfs-vs-dfs-binary-tree/ 
	 */
	private R searchNodeRecursive(Node<R> current, Comparable<R> key){
		if(current == null){
			return null;
		}
		
		R currentKey = current.getKey();
		if(key.compareTo(currentKey) == 0){
			return currentKey;
		}
		else if(key.compareTo(currentKey) == -1){
			return searchNodeRecursive(current.getLeft(), key);
		
		}else{
			return searchNodeRecursive(current.getRight(), key);
		}	
	}
	
	/**
	 * A deleção também é feita de forma recursiva respeitando a propriedade 
	 * de ordenação da árvore.
	 */
	private Node<R> deleteRecursive(Node<R> current, Comparable<R> key){
		if(current == null){
			return null;
		}
		
		R currentKey = current.getKey();
		if(key.compareTo(currentKey) == 0){
			if(current.getLeft() == null && current.getRight() == null)
				return null;
			
			else if(current.getRight() == null)
					return current.getLeft();
			
			else if(current.getLeft() == null)
					return current.getRight();
			
			else{
				R smallestValue = findSmallestValue(current);
				current.setKey(smallestValue);
				current.setRight( deleteRecursive(current.getRight(), smallestValue) );
				return current;
			}
		}
		
		if(key.compareTo(currentKey) == -1){
			current.setLeft( deleteRecursive(current.getLeft(), key) );
			return current;
		}
		
		current.setRight( deleteRecursive(current.getRight(), key));
		return current;
	}
	
	
	private R findSmallestValue(Node<R> root){
		if(root.getLeft() == null)
			return root.getKey();
		else
			return findSmallestValue(root.getLeft());
	}
	
	
	private int sumRecursiveHeight(Node<R> current){
		if(current == null)
			return 0;
		else{
			int leftHeight = sumRecursiveHeight(current.getLeft());
			int rightHeight = sumRecursiveHeight(current.getRight());
			
			return Math.max(leftHeight, rightHeight) + 1;
		}
			
	}
	
    private void inorder(Node<R> temp) 
    { 
        if (temp == null) 
            return; 
        
        inorder(temp.getLeft()); 
        System.out.println(temp.getKey());
        inorder(temp.getRight());
    }
   	
   	private int getBalance(Node<R> node){
		if(node == null)
			return 0;
		
		return height(node.getLeft()) - height(node.getRight());
	}
	
	private int height(Node<R> node){
		if(node == null)
			return 0;
		
		return node.getHeight();
	}
}
