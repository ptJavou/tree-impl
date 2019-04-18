package tree;

public interface TreeInterface<T extends Comparable<T>> {

	void insert(T item);
	
	void delete(Comparable<T> key);
	
	T search(Comparable<T> key);
	
	void showTree();
	
	void height();
	
	void degree();
}
