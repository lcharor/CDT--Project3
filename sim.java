import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class sim {
	public static String traceFileName;
	public static ArrayList<ROB_Block> ROB=new ArrayList<ROB_Block>();
	public static int N;
    public static int S;
    public static int instr_in_dispatch_Queue=0;
    public static int instr_in_sch_Queue=0;
    public static int instr_in_exe_Queue=0;
    public static ArrayList<Instruction> Instr_List=new ArrayList<Instruction>();
	//defining 3 Lists
   public static ArrayList<Instruction> prefetch_queue=new ArrayList<Instruction>();
	public static ArrayList<Instruction> Dispatch_queue=new ArrayList<Instruction>();
	public static ArrayList<Scheduler_Block> Scheduling_queue=new ArrayList<Scheduler_Block>();
	public static ArrayList<Execute_Block> Execute_queue=new ArrayList<Execute_Block>();
	
	public static ArrayList<TimingInfo> Instr_Timing=new ArrayList<TimingInfo>();
	
	public static ArrayList<Register_Block> Register_List=new ArrayList<Register_Block>();
	public static int current_cycle;
	
	public static int delay;
	
	public static void main(String[] simulatorInitializer) throws IOException{
		S=Integer.parseInt(simulatorInitializer[0]);
		N=Integer.parseInt(simulatorInitializer[1]);
		delay=0;
		current_cycle=0;
		int sequence_no=0;
		int total_Instructions=0;
		traceFileName=simulatorInitializer[simulatorInitializer.length-1];
		
		
		//making register file
		
		for(int i=0;i<128;i++)
		{
			Register_Block reg_block=new Register_Block(1,20000);
			Register_List.add(reg_block);
			
		}
		//scanning trace file
		BufferedReader bufferedReader=new BufferedReader(new FileReader(traceFileName));
		String currLine=bufferedReader.readLine();
	
		while(currLine!=null)
		{
			//Reading the command line.
			String[] command=currLine.split(" ");
			String PC=command[0];
			int Operation_Type=Integer.parseInt(command[1]);
			int dest_reg=Integer.parseInt(command[2]);
			int src1_reg=Integer.parseInt(command[3]);
			int src2_reg=Integer.parseInt(command[4]);
			
			Instruction instr=new Instruction(sequence_no,PC, Operation_Type, dest_reg, src1_reg, src2_reg);
			Instr_List.add(instr);
		//Adding one Timing Block Per Instruction	
			TimingInfo Timing_Block=new TimingInfo();
			Timing_Block.instr_No=sequence_no;
			Instr_Timing.add(Timing_Block);
			
			sequence_no++;
			currLine=bufferedReader.readLine();								
		}
	
		total_Instructions=Instr_List.size();
	
		instr_in_dispatch_Queue=0;
		instr_in_sch_Queue=0;
		instr_in_exe_Queue=0;
		//Starting the game
		do{
		   
		    Fake_Retire();
			Execute();
			Issue();
			Dispatch();
			Fetch();	
			Prefetch();
			
		}while(Advance_Cycle(current_cycle));
		
for(ROB_Block RB:ROB){
	System.out.print(RB.instr_No+" \t");
	System.out.print("fu{"+RB.operation_type+"}");
	System.out.print("src{"+RB.src1+","+RB.src2+"} dst{"+RB.dest+"}");
	System.out.print("IF{"+(Instr_Timing.get(RB.instr_No).IF_in)+",1}");
	System.out.print("ID{"+(Instr_Timing.get(RB.instr_No).IF_in+1)+","+(Instr_Timing.get(RB.instr_No).IS_in-(Instr_Timing.get(RB.instr_No).IF_in+1))+"}");  
	System.out.print("IS{"+(Instr_Timing.get(RB.instr_No).IS_in)+","+((Instr_Timing.get(RB.instr_No).EX_in)-Instr_Timing.get(RB.instr_No).IS_in)+"}");
	System.out.print("EX{"+(Instr_Timing.get(RB.instr_No).EX_in)+","+(Instr_Timing.get(RB.instr_No).WB_in-Instr_Timing.get(RB.instr_No).EX_in)+"} ");
	System.out.println("WB{"+(Instr_Timing.get(RB.instr_No).WB_in)+",1}");
}	
		
		
		
		
System.out.println("CONFIGURATION");	
System.out.println("superscalar bandwidth (N) ="+N);
System.out.println("dispatch queue size (2*N) ="+2*N);
System.out.println("schedule queue size (S)   = "+S);
System.out.println("RESULTS");
System.out.println("number of instructions = "+total_Instructions);
System.out.println("number of cycles ="+(current_cycle));
float IPC=(((float) total_Instructions)/((float)(current_cycle)));
System.out.println("IPC =\t"+String.format("%1.2f", IPC));








}
	
public static boolean Advance_Cycle(int curr_Cycle){
	
	current_cycle=curr_Cycle+1;
	if(Instr_List.size()!=0 || Dispatch_queue.size()!=0 || Scheduling_queue.size()!=0|| Execute_queue.size()!=0)
	return true;
	else
	return false;
}
public static void Prefetch(){
	int instr_left=Instr_List.size();
	int space_prefetch_Q=N-prefetch_queue.size();
	
	if(space_prefetch_Q>instr_left)
		space_prefetch_Q=instr_left;
	
	for(int i=0;i<space_prefetch_Q;i++){
		Instruction I=Instr_List.remove(0);
		prefetch_queue.add(I);
		Instr_Timing.get(I.Sequence_No).IF_in=current_cycle;
		
	}
	
	
}





public static void Fetch(){
	int instr_left=prefetch_queue.size();
	int space_DQ=N-Dispatch_queue.size();
//	int space_DQ=2*N-Dispatch_queue.size();
	if(space_DQ>N) space_DQ=N;
	if(space_DQ>instr_left)
	space_DQ=instr_left;	
	
	
			//only if instruction List not 0
	for(int i=0;i<space_DQ;i++)
	{
		Instruction I=new Instruction();
		I=prefetch_queue.remove(0);   //removing Instruction from Instruction Queue
		
		
		Instr_Timing.get(I.Sequence_No).ID_in=current_cycle;
		
		Dispatch_queue.add(I);                     //adding entry into Dispatch queue in-order
		ROB.add(new ROB_Block(I.Sequence_No, "IF", I.dest_reg, I.Operation_Type,I.src1_reg,I.src2_reg,0));   //adding entry into ROB 
		instr_in_dispatch_Queue++;
		
		
		
	}

}
public static void Dispatch(){
	
	int instr_left=Dispatch_queue.size();
	int space_SQ=S-Scheduling_queue.size();
	if(space_SQ>N) space_SQ=N;								
	if(space_SQ>instr_left) space_SQ=instr_left;                 //only do if dispatch queue size not 0
	for(int i=0;i<space_SQ;i++){
		Instruction I=new Instruction();
		I=Dispatch_queue.remove(0);                              //remove Instruction In-order from dispatch queue
		Scheduler_Block SB=new Scheduler_Block();                //creating new scheduler block
		SB.seq_no=I.Sequence_No;

//Source Register 1		
		if(I.src1_reg!=-1){                                       //if source register 1 exists 
			if(Register_List.get(I.src1_reg).readyBit==1){
				//if it's ready bit is 1;
				SB.src1_ready=1;
				SB.src1=I.src1_reg;
			}
			else{                                                 //if ready bit is 0;
				SB.src1_ready=0;
	
				SB.src1=Register_List.get(I.src1_reg).Value_Tag;
			}
		}
		else                                               //if source register 1  Does not exist 
		{
			SB.src1=-1;
			SB.src1_ready=1;
		}
//Source Register 2		
		if(I.src2_reg!=-1){                                       //if source register 2 exists 
			if(Register_List.get(I.src2_reg).readyBit==1){         //if it's ready bit is 1;
				SB.src2_ready=1;
				SB.src2=I.src2_reg;
			}
			else{                                                 //if ready bit is 0;
				SB.src2_ready=0;
		
				SB.src2=Register_List.get(I.src2_reg).Value_Tag; 
			}
		}
		else                                               //if source register 2  Does not exist 
		{
			SB.src2=-1;
			SB.src2_ready=1;
		}
	
//Set destination bit not ready and put tag of Instruction(Sequence No) into this	
      if(I.dest_reg!=-1){                               //If destination Register Exists
    	   	  
    	  Register_List.get(I.dest_reg).setReadyBit(0);
    	  Register_List.get(I.dest_reg).setValue_Tag(I.Sequence_No);
    	
    	  
      }
      else{	//nothing goes here 
      }

    for (ROB_Block currentBlock:ROB){                          //ROB entry state Update
        if(currentBlock.instr_No==I.Sequence_No)	
        	currentBlock.State="IS";
        Instr_Timing.get(I.Sequence_No).IS_in=current_cycle;
    }
      
    instr_in_dispatch_Queue--;
    instr_in_sch_Queue++;
    
    //Adding entry to scheduling queue
    Scheduling_queue.add(SB);
	}
	     
}
public static void Issue(){  
	int instr_left=Scheduling_queue.size();
	int space_EQ=N;
	if(space_EQ>instr_left)
		space_EQ=instr_left;
	int i=0;
	int life_cycle=0;
	 //only if scheduling queue size is not 0
	 
for(int instr_issue_count=0;instr_issue_count<space_EQ;){
		
		if(i==Scheduling_queue.size()){
			break;
			}
		Scheduler_Block SB=new Scheduler_Block();
		SB=Scheduling_queue.get(i);        //Starting from first instruction in scheduling queue
	    
	
		if(SB.src1_ready==1 && SB.src2_ready==1){                   //checking if both the operands are ready
		
			Scheduling_queue.remove(i);
			Instr_Timing.get(SB.seq_no).EX_in=current_cycle;
			
			for(ROB_Block currentROB_block:ROB){                      //getting the operation type from ROB
				if(currentROB_block.instr_No==SB.seq_no){
					if(currentROB_block.operation_type==0)
						life_cycle=1;
					if(currentROB_block.operation_type==1)
						life_cycle=2;
					if(currentROB_block.operation_type==2)
						life_cycle=5;
					
					currentROB_block.State="EX";                      //Changing the State to EX in ROB
				}
			  
			}
			Execute_Block EB=new Execute_Block(SB.seq_no,life_cycle);
			Execute_queue.add(EB);
		
			instr_issue_count++;       //change instruction issue count +1
			instr_in_exe_Queue++;      //increment instruction in Execute queue by 1
			instr_in_sch_Queue--;      //decrease instruction in scheduling queue by 1
			
		}
		else{
			i++;
			
			//No need to change instruction issue count;
		}
		
	}
}	

public static void Execute(){
ArrayList<Integer> temp_list=new ArrayList<Integer>();
int tag=0;

for(Execute_Block current_EB:Execute_queue){          //Removing all the instruction which are completing this cycle
	current_EB.life_cycle=current_EB.life_cycle-1;
		if(current_EB.life_cycle==0){
			
			for(ROB_Block currentROB_block:ROB){                      //Updating ROB
				if(currentROB_block.instr_No==current_EB.instr_seg){
					
					currentROB_block.State="WB";    //Changing the State to WB in ROB
					
					Instr_Timing.get(current_EB.instr_seg).WB_in=current_cycle;
					
					tag=currentROB_block.instr_No;
					currentROB_block.completed=1;
					
				}
			}
			
			for(Register_Block currBlock:Register_List){     //checking the Register files
				if(currBlock.Value_Tag==tag && currBlock.readyBit==0){
					currBlock.readyBit=1;                     //writing in register files
				}
			}
			
			
			for(Scheduler_Block cur_block:Scheduling_queue){            //updating scheduling List on the fly
				
				if(cur_block.src1==tag && cur_block.src1_ready==0)
				{
				
					cur_block.src1_ready=1;
				}
				
				if(cur_block.src2==tag && cur_block.src2_ready==0)
				{
					cur_block.src2_ready=1;
				}
			}

			

		//Execute_queue.remove(temp_list.get(k));
			int index=Execute_queue.indexOf(current_EB);
		
			temp_list.add(index);           //A Temp List contains all the index which needs to be removed from EX_Queue
			instr_in_exe_Queue--;                 //decreasing Instructions in Execute queue
		}
		else{
			
		}
	}
	
//Removing blocks from Execute Queue	


for(int k=temp_list.size()-1;k>-1;k--){
	
	int index=temp_list.get(k).intValue();
	
	Execute_queue.remove(index);
}	

temp_list.clear();
}

public static void Fake_Retire(){
/*ArrayList<Integer> temp_list=new ArrayList<Integer>();	

	for(ROB_Block current_ROB_Block:ROB){
		if(current_ROB_Block.State.equalsIgnoreCase("WB") && current_ROB_Block.completed==1){
		//	ROB.remove(current_ROB_Block);                  //CAN NOT REMOVE ROB ENTRY from here
			int index=ROB.indexOf(current_ROB_Block);
			temp_list.add(index); 
		}
		else{
			break;
		}
	}*/
	
//Removing entries from ROB	
/*for(int k=temp_list.size()-1;k>=0;k--){
	int index=temp_list.get(k);
	ROB_Block RB=ROB.remove(index);
	
	//Printing the out coming Instruction
	System.out.print(RB.instr_No+" \t");
	System.out.print("fu{"+RB.operation_type+"}");
	System.out.println("src{"+RB.src1+","+RB.src2+"} dst{"+RB.dest+"} IF{,1} ID{,1}  IS{,} EX{,} WB{"+(current_cycle-1)+",1} ");
	}*/
}
}
