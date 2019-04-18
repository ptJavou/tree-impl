package avl_tree;

import tree.TreeInterface;

/**
 * Utilizei  como base a implementação disponibilizada neste tutorial e adaptei com o uso do generics que
 * ele pede na interface.
 * 
 * Ele explica bem detalhadamente como é realizada as rotações dos nós para que a árvore
 * se mantenha balanceada.
 * 
 * https://www.geeksforgeeks.org/avl-tree-set-1-insertion/
 * https://www.geeksforgeeks.org/avl-tree-set-2-deletion/
 * 
 */
public class AVLTree<T extends Comparable<T>> implements TreeInterface<T> {

	private Node<T> root;
	
	public AVLTree(Node<T> root) {
		this.root = root;
	}
	
	@Override
	public void insert(T item) {
		addRecursive(root, item);
	}

	@Override
	public void delete(Comparable<T> key) {
		deleteRecursive(root, key);
	}

	@Override
	public T search(Comparable<T> key) {
		return searchNodeRecursive(this.root, key);
	}

	@Override
	public void showTree() {
		inorder(this.root);
	}

	@Override
	public void height() {
		System.out.println("AVL tree height: " + sumRecursiveHeight(this.root));
	}

	@Override
	public void degree() {
		System.out.println("AVL tree degree: " + getBalance(this.root));
	}
	
	private int height(Node<T> node){
		if(node == null)
			return 0;
		
		return node.getHeight();
	}
	
	private int getBalance(Node<T> node){
		if(node == null)
			return 0;
		
		return height(node.getLeft()) - height(node.getRight());
	}
	
	private Node<T> minValueNode(Node<T> node){
		Node<T> current = node;
		
		while(current.getLeft() != null)
			current = current.getLeft();
		
		return current;
	}

	private Node<T> addRecursive(Node<T> node, T key){
		if(node == null)
			return (new Node<T>(key));
		
		if(key.compareTo(node.getKey()) == -1){
			node.setLeft( addRecursive(node.getLeft(), key) );
		
		}else if(key.compareTo(node.getKey()) == 1){
			node.setRight( addRecursive(node.getRight(), key) );
		}
		
		node.setHeight( 1 + Math.max(height(node.getLeft()), height(node.getRight())) );
		
		int balance = getBalance(node);
		
		//nó na esquerda esquerda
		if(balance > 1 && key.compareTo(node.getLeft().getKey()) == -1)
			return rightRotate(node);
			
		//nó na direita direita
		if(balance < -1 && key.compareTo(node.getRight().getKey()) == 1)
			return leftRotate(node);
		
		//nó na esquerda direita
		if(balance > 1 && key.compareTo(node.getLeft().getKey()) == 1){
			node.setLeft(leftRotate(node.getLeft()));
			return rightRotate(node);
		}
		
		//nó na direita esquerda
		if(balance < -1 && key.compareTo(node.getRight().getKey()) == -1){
			node.setRight(rightRotate(node.getRight()));
			return leftRotate(node);
		}
		
		return node;
	}
	
	public Node<T> deleteRecursive(Node<T> root, Comparable<T> key){
		
		if(root == null)
			return root;
		
		if(key.compareTo(root.getKey()) == -1)
			root.setLeft( deleteRecursive(root.getLeft(), key) );
		
		else if(key.compareTo(root.getKey()) == 1)
			root.setRight( deleteRecursive(root.getRight(), key) );
		
		else{
			//1º caso: o nó tem apenas um filho ou nehum filho
			if(root.getLeft() == null || root.getRight() == null){
				
				//apenas um filho
				Node<T> temp = null;
				if(temp == root.getLeft())
					temp = root.getRight();
				
				else
					temp = root.getLeft();
				
				//sem filhos
				if(temp == null){
					temp = root;
					root = null;
				
				}else
					root = temp;
			
			}else{
				//2º caso: o nó tem dois filhos e o menor esta a direita
				Node<T> temp = minValueNode(root.getRight());
				root.setKey( temp.getKey() );
				
				root.setRight( deleteRecursive(root.getRight(), temp.getKey()) );
			}
		}
		
		//se a árvore possuir somente o nó raiz
		if(root == null)
			return root;
		
		//atualiza a altura do nó atual na árvore
		root.setHeight( Math.max( height(root.getLeft()) , height(root.getRight())) + 1);
		
		//obtém o balanço do nó atual 
		//(esta variável de balanço irá dizer se será necessário realizar alguma rotação ao deletar uma chave)
		int balance = getBalance(root);
		
		//caso precise fazer rotações ao deletar
		//1º caso: nó na esquerda esquerda
		if(balance > 1 && getBalance(root.getLeft()) >= 0)
			return rightRotate(root);
		
		//2º caso: nó na esquerda direita
		if(balance > 1 && getBalance(root.getLeft()) < 0){
			root.setLeft( leftRotate(root.getLeft()) );
			return rightRotate(root);
		}
		
		//3º caso: nó na direita direita
		if(balance < -1 && getBalance(root.getRight()) <= 0)
			return leftRotate(root);
		
		//4º caso: nó na direita esquerda
		if(balance < -1 && getBalance(root.getRight()) > 0){
			root.setRight( rightRotate(root.getRight()) );
			return leftRotate(root);
		}
			
		return root;
	}
	
	//Rotação de um nó para a direita
	private Node<T> rightRotate(Node<T> y){
		Node<T> x = y.getLeft();
		Node<T> T2 = x.getRight();
		
		x.setRight(y);
		y.setLeft(T2);
		
		y.setHeight( 1 + Math.max(height(y.getLeft()), height(y.getRight())));
		x.setHeight( 1 + Math.max(height(x.getLeft()), height(x.getRight())));
		
		return x;
	}
	
	//Rotação de um nó pra a esquerda
	private Node<T> leftRotate(Node<T> x) { 
        Node<T> y = x.getRight(); 
        Node<T> T2 = y.getLeft();
   
        y.setLeft(x); 
        x.setRight(T2); 
  
        y.setHeight( 1 + Math.max(height(y.getLeft()), height(y.getRight())));
		x.setHeight( 1 + Math.max(height(x.getLeft()), height(x.getRight())));
  
        return y; 
	}
	
	//Calcula o tamanho da árvode dado um determinado nó
	private int sumRecursiveHeight(Node<T> current){
		if(current == null)
			return 0;
		else{
			int leftHeight = sumRecursiveHeight(current.getLeft());
			int rightHeight = sumRecursiveHeight(current.getRight());
			
			return Math.max(leftHeight, rightHeight) + 1;
		}
			
	}
	
    private void inorder(Node<T> temp) 
    { 
        if (temp == null) 
            return; 
        
        inorder(temp.getLeft()); 
        System.out.println(temp.getKey());
        inorder(temp.getRight());
    }
    
    /**Utiliza o mesmo algorítmo de busca mencionado na Binary Tree
    *
    *(reconmendável criar uma classe pai e extender para evitar a duplicação 
    * deste método e de outros que se repetem em ambas as classes).
    */
    private T searchNodeRecursive(Node<T> current, Comparable<T> key){
		if(current == null){
			return null;
		}
		
		T currentKey = current.getKey();
		if(key.compareTo(currentKey) == 0){
			return currentKey;
		}
		else if(key.compareTo(currentKey) == -1){
			return searchNodeRecursive(current.getLeft(), key);
		
		}else{
			return searchNodeRecursive(current.getRight(), key);
		}	
	} 
	
}
