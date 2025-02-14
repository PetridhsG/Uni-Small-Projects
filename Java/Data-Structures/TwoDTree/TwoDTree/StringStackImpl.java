import java.io.PrintStream;
import java.util.NoSuchElementException;

public class StringStackImpl<T> implements StringStack<T> {
	
	private class Node{				
		T data;
		Node next = null;
		Node(T data,Node next){		//node constructor
			this.data = data;
			this.next = next;
		}
	}
	
	private int size;					//size of stack
	private Node head = null;		
	
	StringStackImpl(){}					//stack default constructor
		
	public boolean isEmpty(){			//check if the stack is empty
		return head == null;
	}
	
	public void push(T item) {			//push an element at the top of the stack
		head = new Node(item,head);
		size++;
	}
	
	public T pop() throws NoSuchElementException{     //pop an element from stack if the stack is not empty
		
		if (isEmpty()){
			throw new NoSuchElementException();
		}
		else {
		T d = head.data;
		head = head.next;
		size--;
		return d;
		}
	}
	
	public T peek() throws NoSuchElementException{ 	 //peek the top element of stack if the stack is not empty
		if (isEmpty()){
			throw new NoSuchElementException();
		}
		return head.data;
	}
	
	public void printStack(PrintStream stream) {	//print the content of the stack
		if (isEmpty()) {
			stream.println("The Stack is empty!!!");
		}
		else {
			Node x = head;
			stream.println("Head:" + x.data);
			while(x.next !=null) {
				x = x.next;
				stream.println("    :" + x.data);
			}
		}
	}
	
	public int size() {				//return the stack size
		return this.size;
	}
}
