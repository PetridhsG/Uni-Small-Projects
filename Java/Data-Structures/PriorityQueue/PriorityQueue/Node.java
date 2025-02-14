
public class Node<T>{
	
	protected Node<T> next = null;
	protected T data;
	
	Node(T data){
		this.data = data;
	}
	
	public T getData() {
        return data;
    }
	
	void setNext(Node<T> next) {
		this.next = next;
	}
	
	public Node<T> getNext() {
		return next;
	}
	
}
