package airwar2.powerups;

public class PowerUps {
	

	private int data;
	private PowerUps next;
	
	public PowerUps(int d){
		
		data=d;
		next=null;
	}
	
	public int getData() {
		return this.data;
	}
	
	public PowerUps getNext() {
		return next;
	}
	
	public void setNext(PowerUps next) {
		this.next = next;
	}

}
