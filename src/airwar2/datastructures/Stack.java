package Data_Structures;

public class Stack {

	private StackNode first;
	int size;
	
	public Stack(){
		
		first=null;
		size=0;
	}
	
	public boolean is_empty(){
		return first==null;
	}
	
	public void push(int element){
	
		StackNode node= new StackNode(element); 
		node.next=first;
		first=node;
		size ++;
	}
	public int pop(){
		
		int aux=first.data;
		first=first.next;
		size --;
		return aux;
	}
	
	public int first(){
		
		return first.data;
	}
	
	public int size(){
		
		return size;
	}
	
	public void clean(){
		while(!is_empty()){
			pop();
		}
	}
	
	
	
}
