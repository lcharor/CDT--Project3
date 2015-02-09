
public class Instruction {
int Sequence_No;
String PC;
int Operation_Type;
int dest_reg;
int src1_reg;
int src2_reg;

public Instruction() {
	super();
	// TODO Auto-generated constructor stub
}

public Instruction(int Sequence_No,String pC, int operationType, int destReg,
		int src1Reg, int src2Reg) {
	
	this.Sequence_No=Sequence_No;
	PC = pC;
	Operation_Type = operationType;
	dest_reg = destReg;
	src1_reg = src1Reg;
	src2_reg = src2Reg;
}

public int getSequence_No() {
	return Sequence_No;
}

public void setSequence_No(int sequenceNo) {
	Sequence_No = sequenceNo;
}

public String getPC() {
	return PC;
}
public void setPC(String pC) {
	PC = pC;
}
public int getOperation_Type() {
	return Operation_Type;
}
public void setOperation_Type(int operationType) {
	Operation_Type = operationType;
}
public int getDest_reg() {
	return dest_reg;
}
public void setDest_reg(int destReg) {
	dest_reg = destReg;
}
public int getSrc1_reg() {
	return src1_reg;
}
public void setSrc1_reg(int src1Reg) {
	src1_reg = src1Reg;
}
public int getSrc2_reg() {
	return src2_reg;
}
public void setSrc2_reg(int src2Reg) {
	src2_reg = src2Reg;
}

}
