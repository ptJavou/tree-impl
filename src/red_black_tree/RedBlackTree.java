package red_black_tree;

import java.util.LinkedList;
import java.util.Queue;

import red_black_tree.Node.COLOR;
import tree.TreeInterface;

/**
 * Utilizei como base a implementação em C++ disponibilizada nestes tutoriais e adaptei em Java
 * com o uso do generics que ele pede na interface.
 * 
 * Ele explica bem detalhadamente como é realizada as rotações dos nós e das
 * cores para que a árvore se mantenha balanceada.
 * 
 * https://www.geeksforgeeks.org/red-black-tree-set-1-introduction-2/
 * https://www.geeksforgeeks.org/red-black-tree-set-2-insert/
 * https://www.geeksforgeeks.org/red-black-tree-set-3-delete-2/
 * 
 */
public class RedBlackTree<T extends Comparable<T>> implements TreeInterface<T> {

	private Node<T> root = null;

	@Override
	public void insert(T item) {
		recursiveInsert(item);

	}

	@Override
	public void delete(Comparable<T> key) {
		deleteByVal(key);

	}

	@SuppressWarnings("unchecked")
	@Override
	public T search(Comparable<T> key) {
		Node<T> sNode = recursiveSearch(key);
		if (sNode.getKey().compareTo((T) key) != 0)
			return null;

		return sNode.getKey();
	}

	@Override
	public void showTree() {
		printInOrder();
		System.out.println();
		printLevelOrder();
	}

	@Override
	public void height() {
		System.out.println("Red Black tree height: " + sumRecursiveHeight(this.root));

	}

	@Override
	public void degree() {
		System.out.println("Red Black tree degree: " + getBalance(this.root));

	}

	// Insere um elemento na árvore
	private void recursiveInsert(T n) {
		Node<T> newNode = new Node<T>(n);

		if (root == null) {
			// Caso a raiz seja nula insere o elemento na raiz
			newNode.setColor(COLOR.BLACK);
			root = newNode;
		} else {
			Node<T> temp = recursiveSearch(n);

			if (temp.getKey().compareTo(n) == 0) {
				// retorna o valor que ja existe
				return;
			}

			// se o valor não for encontrado o nó temp será o nó a ser inserido
			// o novo elemento.

			// conecta o novo nó ao nó pai
			newNode.setParent(temp);

			if (n.compareTo(temp.getKey()) == -1)
				temp.setLeft(newNode);
			else
				temp.setRight(newNode);

			// realiza as rotações se necessário
			fixRedRed(newNode);
		}
	}

	//deleta o nó passado como parâmetro
	private void deleteNode(Node<T> v) {
		Node<T> u = BSTreplace(v);

		//true caso os nós u e v forem pretos
		boolean uvBlack = ((u == null || u.getColor() == COLOR.BLACK) && (v.getColor() == COLOR.BLACK));
		Node<T> parent = v.getParent();

		
		if (u == null) {
			// se u for nulo, v é um nó folha
			if (v == root) {
				root = null;
			} else {
				if (uvBlack) {
					// se u e v forem pretos
					// v é um nó folha, ajusta para que ambos não seja pretos
					fixDoubleBlack(v);
				} else {
					// caso u ou v forem vermelhos
					if (v.sibling() != null)
						v.sibling().setColor(COLOR.RED);
				}

				// deleta v da árvore
				if (v.isOnLeft()) {
					parent.setLeft(null);
				} else {
					parent.setRight(null);
				}
			}

			return;
		}

		if (v.getLeft() == null || v.getRight() == null) {
			// caso v possuir um nó filho
			if (v == root) {
				v.setKey(u.getKey());
				v.setLeft(null);
				v.setRight(null);

			} else {
				if (v.isOnLeft()) {
					parent.setLeft(u);
				} else {
					parent.setRight(u);
				}

				u.setParent(parent);
				if (uvBlack) {
					// se u e v forem pretos
					// v é um nó folha, ajusta para que ambos não seja pretos
					fixDoubleBlack(u);
				} else {
					// u ou v é vermelho, seta u como preto 
					u.setColor(COLOR.BLACK);
				}
			}
			return;
		}
		
		//caso v tenha 2 nós filhos, troca o valor com o sucessor
		swapValues(u, v);
		deleteNode(u);
	}

	// rotação para a esquerda
	void leftRotate(Node<T> x) {
		// nParent irá ser o nó filho á direita
		Node<T> nParent = x.getRight();

		// atualiza o nó raiz caso o nó x for o nó raiz
		if (x == root)
			root = nParent;

		x.moveDown(nParent);

		//conecta x com o sucessor esquerdo de nParent
		x.setRight(nParent.getLeft());
		//caso nParent não for nulo seta x como pai do nó esquerto de nParent
		if (nParent.getLeft() != null)
			nParent.getLeft().setParent(x);

		
		nParent.setLeft(x);
	}

	//rotação para a direita
	private void rightRotate(Node<T> x) {

		Node<T> nParent = x.getLeft();

		if (x == root)
			root = nParent;

		x.moveDown(nParent);

		x.setLeft(nParent.getRight());

		if (nParent.getRight() != null)
			nParent.getRight().setParent(x);

		nParent.setRight(x);
	}

	private void swapColors(Node<T> x1, Node<T> x2) {
		COLOR temp;
		temp = x1.getColor();
		x1.setColor(x2.getColor());
		x2.setColor(temp);
	}

	private void swapValues(Node<T> u, Node<T> v) {
		T temp;
		temp = u.getKey();
		u.setKey(v.getKey());
		v.setKey(temp);
	}

	// balançeia a árvore caso haja dois nós vermelhos consecutivos
	private void fixRedRed(Node<T> x) {

		if (x == root) {
			x.setColor(COLOR.BLACK);
			return;
		}

		Node<T> parent = x.getParent();
		Node<T> grandparent = parent.getParent();
		Node<T> uncle = x.uncle();

		if (parent.getColor() != COLOR.BLACK) {
			if (uncle != null && uncle.getColor() == COLOR.RED) {

				parent.setColor(COLOR.BLACK);
				uncle.setColor(COLOR.BLACK);
				grandparent.setColor(COLOR.RED);

				fixRedRed(grandparent);
			} else {

				if (parent.isOnLeft()) {
					if (x.isOnLeft()) {

						swapColors(parent, grandparent);
					} else {
						leftRotate(parent);
						swapColors(x, grandparent);
					}

					rightRotate(grandparent);
				} else {
					if (x.isOnLeft()) {

						rightRotate(parent);
						swapColors(x, grandparent);
					} else {
						swapColors(parent, grandparent);
					}


					leftRotate(grandparent);
				}
			}
		}
	}

	// encontra o primeiro nó que não tenha nó a sua esquerda
	// na subárvore passada como parâmetro
	private Node<T> successor(Node<T> x) {
		Node<T> temp = x;

		while (temp.getLeft() != null)
			temp = temp.getLeft();

		return temp;
	}

	// encontra o novo nó que subistituirá o nó deletado
	// mantendo a ordenação BST
	private Node<T> BSTreplace(Node<T> x) {
		// caso o nó tenha 2 filhos
		if (x.getLeft() != null && x.getRight() != null)
			return successor(x.getRight());

		// caso o nó seja folha(não possuir filhos)
		if (x.getLeft() == null && x.getRight() == null)
			return null;

		// caso o nó possuir somente um  nó filho
		if (x.getLeft() != null)
			return x.getLeft();
		else
			return x.getRight();
	}

	// balançeia a árvore caso haja dois nós pretos consecutivos
	private void fixDoubleBlack(Node<T> x) {
		if (x == root)
			return;

		Node<T> sibling = x.sibling();
		Node<T> parent = x.getParent();

		if (sibling == null) {

			fixDoubleBlack(parent);
		} else {
			if (sibling.getColor() == COLOR.RED) {

				parent.setColor(COLOR.RED);
				sibling.setColor(COLOR.BLACK);
				if (sibling.isOnLeft()) {

					rightRotate(parent);
				} else {

					leftRotate(parent);
				}
				fixDoubleBlack(x);
			} else {

				if (sibling.hasRedChild()) {
					// A: o nó possui somente 1 nó filho
					if (sibling.getLeft() != null && sibling.getLeft().getColor() == COLOR.RED) {
						if (sibling.isOnLeft()) {
							// rotação esquerda esquerda
							sibling.getLeft().setColor(sibling.getColor());
							sibling.setColor(parent.getColor());
							rightRotate(parent);
						} else {
							// rotação direita esquerda
							sibling.getLeft().setColor(parent.getColor());
							rightRotate(sibling);
							leftRotate(parent);
						}
					} else {
						if (sibling.isOnLeft()) {
							// rotação esquerda direita
							sibling.getRight().setColor(parent.getColor());
							leftRotate(sibling);
							rightRotate(parent);
						} else {
							// rotação direita direita
							sibling.getRight().setColor(sibling.getColor());
							sibling.setColor(parent.getColor());
							leftRotate(parent);
						}
					}
					parent.setColor(COLOR.BLACK);
				} else {
					// B: se possuir 2 nós filhos pretos
					sibling.setColor(COLOR.RED);
					if (parent.getColor() == COLOR.BLACK)
						fixDoubleBlack(parent);
					else
						parent.setColor(COLOR.BLACK);
				}
			}
		}
	}

	// exibe a árvore percorrendo seus nós de acordo com o 
	//algoritmo in level order
	private void levelOrder(Node<T> x) {
		if (x == null)
			return;

		Queue<Node<T>> q = new LinkedList<>();
		Node<T> curr;

		q.add(x);

		while (!q.isEmpty()) {
		
			curr = q.peek();
			q.remove();

			System.out.print(curr.getKey() + " ");

			if (curr.getLeft() != null)
				q.add(curr.getLeft());
			if (curr.getRight() != null)
				q.add(curr.getRight());
		}
	}

	// exibe a árvore percorrendo seus nós de acordo com o 
		//algoritmo in order.
	private void inorder(Node<T> x) {
		if (x == null)
			return;
		inorder(x.getLeft());
		System.out.print(x.getKey() + " ");
		inorder(x.getRight());
	}

	// busca por um determinado nó de acordo com o valor passado como parâmetro
	// caso não encontre retorna o último nó percorrido da árvore
	private Node<T> recursiveSearch(Comparable<T> n) {

		Node<T> temp = root;
		while (temp != null) {
			if (n.compareTo(temp.getKey()) == -1) {
				if (temp.getLeft() == null)
					break;
				else
					temp = temp.getLeft();
			} else if (n.compareTo(temp.getKey()) == 0) {
				break;
			} else {
				if (temp.getRight() == null)
					break;
				else
					temp = temp.getRight();
			}
		}

		return temp;
	}

	@SuppressWarnings("unchecked")
	private void deleteByVal(Comparable<T> n) {
		if (root == null)
			
			return;

		Node<T> v = recursiveSearch(n);

		if (v.getKey().compareTo((T) n) != 0) {
			System.out.print("Nenhum nó a ser deletado com o valor: " + n);
			return;
		}

		deleteNode(v);
	}

	private void printInOrder() {
		System.out.println("Inorder: ");
		if (root == null)
			System.out.print("Àrvore vazia.");		
		else
			inorder(root);
	}

	private void printLevelOrder() {
		System.out.println("Level order:");
		if (root == null)
			System.out.print("Àrvore vazia.");
		else
			levelOrder(root);
	}

	// Calcula o tamanho da árvode dado um determinado nó
	private int sumRecursiveHeight(Node<T> current) {
		if (current == null)
			return 0;
		else {
			int leftHeight = sumRecursiveHeight(current.getLeft());
			int rightHeight = sumRecursiveHeight(current.getRight());

			return Math.max(leftHeight, rightHeight) + 1;
		}
	}
	
	private int getBalance(Node<T> node){
		if(node == null)
			return 0;
		
		return height(node.getLeft()) - height(node.getRight());
	}
	
	private int height(Node<T> node){
		if(node == null)
			return 0;
		
		return node.getHeight();
	}
}