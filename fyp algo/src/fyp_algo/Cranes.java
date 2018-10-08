package fyp_algo;
import fyp_algo.Constants.*;
import java.util.PriorityQueue;

public class Cranes {
	private final int y;
	private final int x;
	private String craneType;
	private boolean pickUp;
	private boolean dropOff;
	private PriorityQueue<Containers> local_p_queue = new PriorityQueue<Containers>();
	
	public Cranes(int y, int x, String craneType){
		this.y = y;
		this.x = x;
		this.craneType = craneType;
		if(craneType.equals(Constants.CRANETYPE[4])){	//qc_load
			dropOff = true;
			pickUp = false; 
		}else if(craneType.equals(Constants.CRANETYPE[5])){	//qc_unload
			dropOff = false;
			pickUp = true;
			
		}else{
			dropOff = true;
			pickUp = true;
			
		}
	}
	
	public String getCraneType(){
		return craneType;
	}

	public boolean getPickUp(){
		return pickUp;
	}
	
	public boolean getDropOff(){
		return dropOff;
	}
	
	public PriorityQueue<Containers> getPriorityQueue(){
		return local_p_queue; 
	}
}
