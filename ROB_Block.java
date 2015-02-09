
public class ROB_Block {
int instr_No;
String State;
int dest;
int operation_type;
int src1;
int src2;
int completed;
public ROB_Block(int instrNo, String state, int dest, int operationType,int src1,int src2,int completed) {
	super();
	instr_No = instrNo;
	State = state;
	this.dest = dest;
	operation_type = operationType;
	this.src1=src1;
	this.src2=src2;
	this.completed=completed;
}
public int getCompleted() {
	return completed;
}
public void setCompleted(int completed) {
	this.completed = completed;
}
public int getSrc1() {
	return src1;
}
public void setSrc1(int src1) {
	this.src1 = src1;
}
public int getSrc2() {
	return src2;
}
public void setSrc2(int src2) {
	this.src2 = src2;
}
public int getInstr_No() {
	return instr_No;
}
public void setInstr_No(int instrNo) {
	instr_No = instrNo;
}
public String getState() {
	return State;
}
public void setState(String state) {
	State = state;
}
public int getDest() {
	return dest;
}
public void setDest(int dest) {
	this.dest = dest;
}
public int getOperation_type() {
	return operation_type;
}
public void setOperation_type(int operationType) {
	operation_type = operationType;
}

}
