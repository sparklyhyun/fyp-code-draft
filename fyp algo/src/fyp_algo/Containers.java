package fyp_algo;
import java.util.Random;

import fyp_algo.Constants.*;

public class Containers {
	//type of container (loading or unloading)
	private String cType; //randomly choose between loading or unloading 
	
	private int pickUpx;
	private int pickUpy;
	private int dropOffx;
	private int dropOffy;
	
	private Cranes pickUpCrane;
	private Cranes dropOffCrane;
	
	private int cIndex;
	
	public Containers(int cIndex){
		//assign index (priority?)
		this.cIndex = cIndex;
		
		//randomly choose container type
		this.cType = Constants.LOADTYPE[new Random().nextInt(Constants.LOADTYPE.length)];
		
		//randomly generate drop off & pick up
		if(this.cType.equals("loading")){
			//if loading - pickup : yc1, 2, 3, 4, dropoff: qc_load
			//if unloading - pickup: qc_unload, dropoff: yc1, 2, 3, 4
			init_pickUp(true);
			init_dropOff(true);
		}else{
			init_pickUp(false);
			init_dropOff(false);
		}
		
		System.out.println("index : " + this.cIndex + " type: " + this.cType + ", pickup x:" + this.pickUpx + ", pickup y: " +this.pickUpy);
	}
	
	private void init_pickUp(boolean loading){
		//YC1, 2, 3, 4, QC_UNLOAD, randomly generate 
		//if loading, do not pick up from unload qc
		if(loading == false){
			this.pickUpx = 0;
			this.pickUpy = 0;
		}else{
			this.pickUpy = 1;
			Random rand = new Random(); 
			int randomPos = rand.nextInt(4);
			switch(randomPos){
			case 0: this.pickUpx = 0; break;
			case 1: this.pickUpx = 1; break;
			case 2: this.pickUpx = 2; break;
			case 3: this.pickUpx = 3; break;
			default: break;
			}
		}
	}
	
	private void init_dropOff(boolean loading){
		//YC1, 2, 3, 4, QC_LOAD, randomly generate 
		if(loading == true){
			this.dropOffx = 2;
			this.dropOffy = 0;
		}else{
			this.dropOffy = 1;
			Random rand = new Random(); 
			int randomPos = rand.nextInt(4);
			switch(randomPos){
			case 0: this.dropOffx = 0; break;
			case 1: this.dropOffx = 1; break;
			case 2: this.dropOffx = 2; break;
			case 3: this.dropOffx = 3; break;
			default: break;
			}
			}
		}
	
	public int getIndex(){
		return cIndex; 
	}
	
	public String getCtype(){
		return cType;
	}
	
	public int getPickUpx(){
		return this.pickUpx;
	}
	
	public int getPickUpy(){
		return this.pickUpy;
	}
		
	public int getDropOffx(){
		return this.dropOffx;
	}	
	
	public int getDropOffy(){
		return this.dropOffy;
	}
	
	public Cranes getPickUpPoint(){
		return pickUpCrane;
	}
	
	public Cranes getDropOffPoint(){
		return dropOffCrane;
	}

}
