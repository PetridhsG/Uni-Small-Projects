
import java.util.NoSuchElementException;

public class List<T> {

	private Node<T> head = null;
    private Node<T> tail = null;
    int length=0;

    public List() {}
    
    public List<T> copy(){		
    	List<T> x = new List<>();
        x.head = head;
        x.tail = tail;
        x.length = length;
		return x;
    }
    
    public boolean isEmpty() {
        return head == null;
    }
    
    int getLength() {
    	return length;
    }
    
    public void insertAtFront(T data) {
        Node<T> n = new Node<>(data);

        if (isEmpty()) {
            head = n;
            tail = n;
        } else {
            n.setNext(head);
            head = n;
        }
        length++;
    }
    
    public void insertAtBack(T data) {
        Node<T> n = new Node<>(data);

        if (isEmpty()) {
            head = n;
            tail = n;
        } else {
            tail.setNext(n);
            tail = n;
        }
        length++;
    }
    
    public T removeFromFront() throws NoSuchElementException {
        if (isEmpty())
        	throw new NoSuchElementException();

        T data = head.getData();

        if (head == tail)
            head = tail = null;
        else
            head = head.getNext();

        length--;
        return data;
    }
    
    public T removeFromBack() throws NoSuchElementException {
    	  if (isEmpty())
    		throw new NoSuchElementException();

        T data = tail.getData();

        if (head == tail)
            head = tail = null;
        else {
            Node<T> iterator = head;
            while (iterator.getNext() != tail)
                iterator = iterator.getNext();

            iterator.setNext(null);
            tail = iterator;
        }

        length--;
        return data;
    }
    
    public T peek() {
    	return head.getData();
    }
    
    public String toString() {
        if (isEmpty()) {
            return "List is empty :(";
        }

        Node<T> current = head;

        StringBuilder ret = new StringBuilder();

        // while not at end of list, output current node's data
        ret.append("HEAD -> ");

        while (current != null) {
            ret.append(current.data.toString());

            if (current.getNext() != null)
                ret.append(" -> ");

            current = current.next;
        }

        ret.append(" <- TAIL");

        return ret.toString();
    }

}