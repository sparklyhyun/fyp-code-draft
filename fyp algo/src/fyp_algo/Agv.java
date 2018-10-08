package fyp_algo;
import java.util.ArrayList;
//import java.util.LinkedList; 
import java.util.concurrent.TimeUnit;
import java.util.Random;

import fyp_algo.Constants.*;

public class Agv {
	private int agv_num;	//index of agv
	private int agv_X;
	private int agv_Y;
	private int dest_x;	//store drop off point/ pick off point of the container it is carrying 
	private int dest_y; 
	
	private String controlPt = "null"; 
	private boolean idle = true; 
	
	private ArrayList<Containers> task_list = new ArrayList<>();	//list of jobs 
	private int total_cost; //total travel distance to complete all tasks in the task_list 
	
	public Agv(int agv_num){
		this.agv_num = agv_num;
		initPos();
		setCtrlPt(this.agv_Y, this.agv_X);
	}
	
	private void initPos(){
		//generate initial pos (random among all the control points) 
		Random rand = new Random();
		int min = 0;
		int randomPosX = rand.nextInt(Constants.MAX_X);
		int randomPosY = rand.nextInt(Constants.MAX_Y);
		if(Constants.AGV_POS[randomPosY][randomPosX] == 0){
			Constants.AGV_POS[randomPosY][randomPosX] = 1;
			this.agv_Y = randomPosY;
			this.agv_X = randomPosX;
			return;
		}else{
			initPos();	//calculate random again
		}
	}
	
	
	public void setAgvx(int x){
		this.agv_X = x;
	}
	
	public void setAgvy(int y){
		this.agv_Y = y;
	}
	
	public void setIdle(boolean idle){
		this.idle = idle;
	}
	
	public void setCtrlPt(String ctrl){
		this.controlPt = ctrl;
	}
	
	public void setDesty(int y){
		this.dest_y = y;
	}
	
	public void setDestx(int x){
		this.dest_x = x;
	}
	
	public int getAgvNum(){
		return this.agv_num;
	}
	
	public int getAgvx(){
		return this.agv_X;
	}
	public int getAgvy(){
		return this.agv_Y;
	}
	
	public boolean getIdle(){
		return idle;
	}
	
	public String getCtrlPt(){
		return controlPt;
	}
	
	public int getDestx(){
		return dest_x;
	}
	
	public int getDesty(){
		return dest_y;
	}
	
	public ArrayList<Containers> getTaskList(){
		return task_list; 
	}
	
	public int getTotalCost(){
		return total_cost;
	}
	
	public void updateTotalCost(int x){
		total_cost += x; 
	}
	
	public void setCtrlPt(int y, int x){
		//CRANETYPE = {"YC1", "YC2", "YC3", "YC4", "QC_LOAD", "QC_UNLOAD"}
		if(y == 0){
			if(x == 0){
				this.controlPt = Constants.CRANETYPE[5]; //qc_unload
			}else if(x == 2){
				this.controlPt = Constants.CRANETYPE[4]; //qc_load
			}
		}else{
			switch(x){
			case 0: this.controlPt = Constants.CRANETYPE[0]; break;
			case 1: this.controlPt = Constants.CRANETYPE[1]; break;
			case 2: this.controlPt = Constants.CRANETYPE[2]; break;
			case 3: this.controlPt = Constants.CRANETYPE[3]; break;
			default: break; 
			}
		}
	}
	
	public void agvMove(){	
		//calculate agv travel dist
		int travelx = dest_x - agv_X;
		int travely = dest_y - agv_Y;
		if(travelx == 0 && travely == 0){
			return;
		}
		if(travely < 0){
			Constants.AGV_POS[agv_Y][agv_X] = 0;
			agv_Y--;
			//wait for 2 units 
			try {
				TimeUnit.MILLISECONDS.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Constants.AGV_POS[agv_Y][agv_X] = 1;
			agvMove();
		}else if(travely > 0 && agv_X > 0){
			Constants.AGV_POS[agv_Y][agv_X] = 0;
			agv_X--;	//can only move in clockwise direction
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//wait for 1 units 
			Constants.AGV_POS[agv_Y][agv_X] = 1;
			agvMove();
		}else if(travely > 0 && agv_X == 0){
			Constants.AGV_POS[agv_Y][agv_X] = 0;
			agv_Y++;
			//wait for 2 units 
			try {
				TimeUnit.MILLISECONDS.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Constants.AGV_POS[agv_Y][agv_X] = 1;
			agvMove();
		}else if(travely == 0 && travelx > 0){
			Constants.AGV_POS[agv_Y][agv_X] = 0;
			agv_X++;
			//wait for 1 units 
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Constants.AGV_POS[agv_Y][agv_X] = 1;
			agvMove();
		}
		Constants.printAgvPos();
		//if y = 0, only can x--
		//if y = 1, can x++, y++(but cost is 2) 
		//if y = 0 && x = 0, can y-- 
	}

	

}
