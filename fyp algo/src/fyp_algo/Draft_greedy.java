package fyp_algo;

import java.util.*;

public class Draft_greedy {
	/* AGV will be located initially at any of the control points
	 * 
	 * 
	 * */
	/*
	private ArrayList<Agv> agvList = new ArrayList<Agv>(); //contains 4 agv (for now)
	private ArrayList<Containers> containers = new ArrayList<Containers>(); //contains list of jobs
	
	//work lists 
	private ArrayList<Containers> q_yc1 = new ArrayList<>();
	private ArrayList<Containers> q_yc2 = new ArrayList<>();
	private ArrayList<Containers> q_yc3 = new ArrayList<>();
	private ArrayList<Containers> q_yc4 = new ArrayList<>();
	private ArrayList<Containers> q_qc_unload = new ArrayList<>();
	*/
	
	public PriorityQueue<Containers> q_yc1 = new PriorityQueue<Containers>();
	public PriorityQueue<Containers> q_yc2 = new PriorityQueue<Containers>();
	public PriorityQueue<Containers> q_yc3 = new PriorityQueue<Containers>();
	public PriorityQueue<Containers> q_yc4 = new PriorityQueue<Containers>();
	public PriorityQueue<Containers> q_qc_unload  = new PriorityQueue<Containers>();
	
	
	public Draft_greedy(){
		//BFS, distance depneds on the distance from the pick up location to agv
		
		
	}
}
