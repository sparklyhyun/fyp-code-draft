package fyp_algo;

import java.util.*;

public class Draft_greedy {
	/* AGV will be located initially at any of the control points
	 * 
	 * 
	 * */

	
	//global priority queue for all containers (considers pick up points)
	public PriorityQueue<Containers> q_priority = new PriorityQueue<Containers>();
	
	//priority queue at each pick up points (considers drop off points) 
	/*
	public PriorityQueue<Containers> q_yc1 = new PriorityQueue<Containers>();
	public PriorityQueue<Containers> q_yc2 = new PriorityQueue<Containers>();
	public PriorityQueue<Containers> q_yc3 = new PriorityQueue<Containers>();
	public PriorityQueue<Containers> q_yc4 = new PriorityQueue<Containers>();
	public PriorityQueue<Containers> q_qc_unload  = new PriorityQueue<Containers>();
	*/
	
	//there are 4 agvs in the system for now 
	public Agv agv1, agv2, agv3, agv4;
	
	//store the agv in the list, contain agv1, 2, 3, 4 in order (index) 
	public ArrayList<Agv> agv_list = new ArrayList<Agv>(); 
	
	//array of length 4, to store the travel time of agv1, agv2, agv3, agv4 respectively
	public int[] agv_travel_time = new int[4];
	
	public Draft_greedy(){
		//get the first item in the global priority queue
		Containers c = q_priority.poll();
		Cranes pickup = c.getPickUpPoint(); 
		
		//first, populate the priority queue at pick up points 
		Iterator i = q_priority.iterator();
		while(i.hasNext()){
			Containers c = i.next();	//get the next item, do not remove them from the global queue
			Cranes pickup = c.getPickUpPoint();
			pickup.getPriorityQueue().add(c); 
		}
		
		
		//1.
		//schedule tasks to vehicles according to the distance (not real time) 
		while(!q_priority.isEmpty() ){
			Containers c = q_priority.poll();
			Cranes pickup = c.getPickUpPoint(); 
			
			//calculate traveling time of each agv to pick up point
			//assign task to agv with the shortest travel time 			
			int minIndex = 0;	 //index of agv with min travel time to next pickup  
			int minValue = 0;
			for(int j=0; j<4; j++){
				Agv agv = agv_list.get(j);
				agv_travel_time[j] = calculateTravelTime(agv, pickup); 
			}
			
			//simple comparison of travel time 
			for(int k=0; k<4; k++){
				if(agv_travel_time[k] < minValue){
					minValue = agv_travel_time[k];
					minIndex = k;
				}
			}
			
			
			agv_list.get(minIndex).getTaskList().add(c);		//add the task into the task list of the nearest agv
			agv_list.get(minIndex).updateTotalCost(minValue);	//update the total travel time of the agv (until this task)
			pickup.getPriorityQueue().remove(c);				//remove the task from local priority list (priority list of individual pick up points)
		}
		
		//2.
		//real-time scheduling of tasks to agvs 
		Agv idleAgv[] = new Agv[2];	//index 0 - previous idle, index 1 - current
		Agv dummy = new Agv(5);
		idleAgv[0] = dummy;		//initialize
		while(!q_priority.isEmpty()){
			Containers c = q_priority.poll();
			Cranes pickup = c.getPickUpPoint(); 
		
			//wait for an agv to go idle
			while(true){
				if(agv1.getIdle() || agv2.getIdle() || agv3.getIdle()|| agv4.getIdle()){
					if(agv1.getIdle()){
						idleAgv[1] = agv1;
					}else if(agv2.getIdle()){
						idleAgv[1] = agv2;
					}else if(agv3.getIdle()){
						idleAgv[1] = agv3;
					}else if(agv4.getIdle()){
						idleAgv[1] = agv4;
					}
					break;
				} 
			}
			
			//if one agv continues to be idle, store the distance left until new agv becomes idle 
			//else, reset the total cost 
			if(idleAgv[0].getAgvNum() != idleAgv[1].getAgvNum()){	
				agv1.resetTotalCostReal();
				agv2.resetTotalCostReal();
				agv3.resetTotalCostReal();
				agv4.resetTotalCostReal();
			}
			
			int minIndex = 0;	 //index of agv with min travel time to next pickup  
			int minValue = 0;
			for(int j=0; j<4; j++){
				Agv agv = agv_list.get(j);
				agv_travel_time[j] = calculateTravelTime_Real(agv, pickup) + agv.getDistLeft(); 
				//agv.getDistLeft = distance(travel time) left until current drop off
			}
			
			//simple comparison of travel time 
			for(int k=0; k<4; k++){
				if(agv_travel_time[k] < minValue){
					minValue = agv_travel_time[k];
					minIndex = k;
				}
			}
			
			agv_list.get(minIndex).getTaskList().add(c);		//add the task into the task list of the nearest agv
			agv_list.get(minIndex).updateTotalCostReal(minValue);	//update the total travel time of the agv 
			pickup.getPriorityQueue().remove(c);				//remove the task from local priority list (priority list of individual pick up points)
			
		}
		
	}
	
	public int  calculateTravelTime(Agv agv, Cranes pickUp){
		//agv.get_travel_time = travel time from the previous drop off point to the next pick up point 
		//agv.getTotalCost = total travel time for all the tasks assigned to the agv so far 
		int travelTime = agv.getTotalCost() + agv.getTravelTime(pickUp);
		return travelTime; 
	}
	
	public int calculateTravelTime_Real(Agv agv, Cranes pickUp){
		//calculate the travel time in real time, called once an agv goes idle  
		int travelTime = agv.getTotalCostReal() + agv.getTravelTime(pickUp);
	}
}
