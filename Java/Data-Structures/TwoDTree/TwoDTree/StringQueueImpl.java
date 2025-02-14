import java.io.PrintStream;
import java.util.NoSuchElementException;

public class StringQueueImpl<T> implements StringQueue<T> {
	
	private class Node{				
		T data;
		Node next = null;
		Node(T data){					//node constructor
			this.data = data;
			next = null;
		}
	}
	
	private int size;					//size of queue
	private Node head = null;		
	private Node tail = null;
	
	public boolean isEmpty() {			//check if the queue is empty
		return head == null;
	}

	public void put(T item){			//put an element at the back of the queue
		Node t = tail;
		tail = new Node(item); 
		if(isEmpty()) {
			head = tail;
		}
		else {
			t.next = tail;
		}
		size ++;
	}

	public T get() throws NoSuchElementException{		//get an element from the front of the queue
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		else {
			T d = head.data;
			head = head.next ;
			size--;
			return d;
		}
	}

	public T peek() throws NoSuchElementException{    //peek the element from the front of the queue
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		return head.data;
	}

	public void printQueue(PrintStream stream) {	  //print the content of the queue
		if (isEmpty()) {
			System.out.println("The Stack is empty!!!");
		}
		else {
			int i = 0 ;
			Node x = head;
			stream.println("Head:" + x.data);
			while(x.next !=null) {
				if (i+2 == size) {
					x = x.next;
					stream.println("Tail:" + x.data);
				}
				else {
					x = x.next;
					stream.println("    :" + x.data);
					i++;
				}
			}
		}
	}

	public int size() {				//return the queue size
		return size;
	}
}
