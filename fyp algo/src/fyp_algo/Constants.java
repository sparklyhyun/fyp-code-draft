package fyp_algo;

import java.util.ArrayList;

public class Constants {
	public static final int AGV_NO = 4;
	public static String[] LOADTYPE = {"loading", "unloading"};  
	public static String[] CRANETYPE = {"YC1", "YC2", "YC3", "YC4", "QC_LOAD", "QC_UNLOAD"};
	public static String TASK_QUEUE = "need to initialize task queue";
	public static String AGV_QUEUE = "need to initialize idle agv queue? - look at the textbook";
	
	public static final int MAX_Y = 2;
	public static final int MAX_X = 4;
	
	public static final int MOVE_UP_COST = 2;
	public static final int MOVE_DOWN_COST = 2;
	public static final int MOVE_SIDE_COST = 1; 
	
	
	public static int[][] AGV_POS = new int[][] {{0,0,0,0},{0,0,0,0,0}};
	//initially 0, when agv put, change to 1

	//print agv pos
	public static void printAgvPos(){
		for(int i = 0; i<2; i++){
			for(int j=0; j<4; j++){
				System.out.print(AGV_POS[i][j]);
			}
		}
	}

}
