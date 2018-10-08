package fyp_algo;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.Random;
import fyp_algo.Constants.*;

public class Greedy {

	private ArrayList<Agv> idleAgv;
	private ArrayList<Containers> containers;
	private ArrayList<Containers> q_yc1, q_yc2, q_yc3, q_yc4, q_qc_unload;
	
	public Greedy(ArrayList<Agv> idleAgv, ArrayList<Containers> containers, ArrayList<Containers> q_yc1,
			ArrayList<Containers> q_yc2, ArrayList<Containers> q_yc3, ArrayList<Containers> q_yc4, 
			ArrayList<Containers> q_qc_unload)
	{
		this.idleAgv = idleAgv;
		this.containers = containers;
		this.q_yc1 = q_yc1;
		this.q_yc2 = q_yc2;
		this.q_yc3 = q_yc3;
		this.q_yc4 = q_yc4;
		System.out.println("greedy set");
	}
	
	public void findNearest(Agv agv){
		//CRANETYPE = {"YC1", "YC2", "YC3", "YC4", "QC_LOAD", "QC_UNLOAD"}
		/* ctrol point yc1 - check yc1 first, if queue not empty. then others - queue not empty && no one on that index
		 * 
		 * */
		String c = agv.getCtrlPt();
		switch(c){
		case "YC1": checkYC1(agv); break;
		case "YC2": checkYC2(agv); break;
		case "YC3": checkYC3(agv); break;
		case "YC4": checkYC4(agv); break;
		case "QC_LOAD": checkQCLoad(agv); break;
		case "QC_UNLOAD": checkQCUnload(agv); break;
		default: checkNull(agv); break;
		}
	}
	
	public void checkYC1(Agv agv){
		if(q_yc1.isEmpty() == false){
			transportContainer(agv, q_yc1, true);
			
			}else if(q_yc2.isEmpty() == false){
				transportContainer(agv, q_yc2, false);
				
			}else if(q_yc3.isEmpty() == false){
				transportContainer(agv, q_yc3, false);
				
			}else if(q_qc_unload.isEmpty() == false){
				transportContainer(agv, q_qc_unload, false);
				
			}else if(q_yc4.isEmpty() == false){
				transportContainer(agv, q_yc4, false);
				
			}
	}
	
	public void checkYC2(Agv agv){
		if(q_yc2.isEmpty() == false){
			transportContainer(agv, q_yc2, true);
			//if agv position == pick up position, only transport. else, need to move to pickup location 
			
			}else if(q_yc3.isEmpty() == false){
				transportContainer(agv, q_yc3, false);
				
			}else if(q_yc4.isEmpty() == false){
				transportContainer(agv, q_yc4, false);
				
			}else if(q_qc_unload.isEmpty() == false){
				transportContainer(agv, q_qc_unload, false);
				
			}else if(q_yc1.isEmpty() == false){
				transportContainer(agv, q_yc1, false);
			}
	}
	
	public void transportContainer(Agv agv, ArrayList<Containers> container, boolean samePos){
		Containers c = container.get(0); //get the first container in line
		containers.remove(0);	//remove the first object from queue
		idleAgv.remove(agv); 	//remove agv from idle queue 
		if(samePos){//only need to transport 
			int dropOffx = c.getDropOffx();
			int dropOffy = c.getDropOffy();
			agv.setDestx(dropOffx);
			agv.setDesty(dropOffy);
			while(Constants.AGV_POS[dropOffy][dropOffx] == 1){
				//wait 
				break;
			}
			agv.agvMove();
		}else{//need to move to pick up location first before drop off
			int pickUpx = c.getPickUpx();
			int pickUpy = c.getPickUpy();
			agv.setDestx(pickUpx);
			agv.setDesty(pickUpy);
			while(Constants.AGV_POS[pickUpy][pickUpx] == 1){
				//wait 
				break;
			}
			agv.agvMove();
			int dropOffx = c.getDropOffx();
			int dropOffy = c.getDropOffy();
			agv.setDestx(dropOffx);
			agv.setDesty(dropOffy);
			while(Constants.AGV_POS[dropOffy][dropOffx] == 1){
				//wait 
				break;
			}
			agv.agvMove();
		}
		
		idleAgv.add(agv); 	//add the now idle agv to the back of the array 
	}
	
	public void movetoContainer(Agv agv, ArrayList<Containers> container){
		Containers c = container.get(0);
		containers.remove(0);
		idleAgv.remove(agv);
		int pickUpx = c.getPickUpx();
		int pickUpy = c.getPickUpy();
		agv.setDesty(pickUpy);
		agv.setDestx(pickUpx);
		while(Constants.AGV_POS[pickUpy][pickUpx] == 1){
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		agv.agvMove();
	}
	
	public void checkYC3(Agv agv){
		if(!q_yc3.isEmpty()){
			transportContainer(agv, q_yc3, true);
		
			}else if(q_yc4.isEmpty() == false){
				transportContainer(agv, q_yc4, false);
				
			}else if(q_qc_unload.isEmpty() == false){
				transportContainer(agv, q_qc_unload, false);
				
			}else if(q_yc1.isEmpty() == false){
				transportContainer(agv, q_yc1, false);
				
			}else if(q_yc2.isEmpty() == false){
				transportContainer(agv, q_yc2, false);
				
			}
	}
	
	public void checkYC4(Agv agv){
		if(!q_yc4.isEmpty()){
			transportContainer(agv, q_yc4, true);
			
			}else if(q_qc_unload.isEmpty() == false){
				transportContainer(agv, q_qc_unload, false);
				
			}else if(q_yc1.isEmpty() == false){
				transportContainer(agv, q_yc1, false);
				
			}else if(q_yc2.isEmpty() == false){
				transportContainer(agv, q_yc2, false);
				
			}else if(q_yc3.isEmpty() == false){
				transportContainer(agv, q_yc3, false);
				
			}
	}
	
	public void checkQCLoad(Agv agv){
		if(!q_qc_unload.isEmpty()){
			transportContainer(agv, q_qc_unload, false);
			
			}else if(q_yc1.isEmpty() == false){
				transportContainer(agv, q_yc1, false);
				
			}else if(q_yc2.isEmpty() == false){
				transportContainer(agv, q_yc2, false);
				
			}else if(q_yc3.isEmpty() == false){
				transportContainer(agv, q_yc3, false);
				
			}else if(q_yc4.isEmpty() == false){
				transportContainer(agv, q_yc4, false);
				
			}
	}
	
	public void checkQCUnload(Agv agv){
		if(!q_qc_unload.isEmpty()){
			transportContainer(agv, q_qc_unload, false);
			
			}else if(q_yc1.isEmpty() == false){
				transportContainer(agv, q_yc2, false);
				
			}else if(q_yc2.isEmpty() == false){
				transportContainer(agv, q_yc2, false);
				
			}else if(q_yc3.isEmpty() == false){
				transportContainer(agv, q_yc3, false);
				
			}else if(q_yc4.isEmpty() == false){
				transportContainer(agv, q_yc4, false);
				
			}
	}
	
	public void checkNull(Agv agv){
		if(!q_qc_unload.isEmpty()){
			transportContainer(agv, q_qc_unload, false);
			
			}else if(q_yc1.isEmpty() == false){
				transportContainer(agv, q_yc1, false);
				
			}else if(q_yc2.isEmpty() == false){
				transportContainer(agv, q_yc2, false);
				
			}else if(q_yc3.isEmpty() == false){
				transportContainer(agv, q_yc3, false);
				
			}else if(q_yc4.isEmpty() == false){
				transportContainer(agv, q_yc4, false);
				
			}
	}
	
	
}
