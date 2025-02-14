import java.io.PrintStream;
import java.util.NoSuchElementException;


public class QueueWithOnePointer<T> {

	private class Node{
		T data;
		Node next;
		Node(T data){
			this.data = data;	
		}	
	}
	
	private int size;					//size of queue
	private Node head ;	
	
	public boolean isEmpty() {			//check if the queue is empty
		return size == 0 ;
	}

	public void put(T item){			//put an element at the back of the queue
		if (isEmpty()) {
			head = new Node(item);
			head.next = head;
		}
		else {
			Node t = new Node(item);
			t.next = head.next;
			head.next = t;
			head = t;
		}
		size++;
	}

	public T get() throws NoSuchElementException{		//get an element from the front of the queue
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		else {
			T d = head.next.data ;
			head.next = head.next.next;
			size--;
			return d;
		}
	}

	public T peek() throws NoSuchElementException{    //peek the element from the front of the queue
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		else {
			return head.next.data;
		}
	}

	public void printQueue(PrintStream stream) {	  //print the content of the queue
		if (isEmpty()) {
			stream.println("The Queue is empty!!!");
		}
		else {
			Node x = head.next;
			stream.println("Head:" + x.data);
			for(int i = 0; i < size-1 ; i++) {
				x = x.next;
				stream.println("    :" + x.data);
			}
		}	
	}

	public int size() {				//return the queue size
		return size;
	}
}

