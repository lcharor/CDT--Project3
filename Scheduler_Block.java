
public class Scheduler_Block {
int seq_no;
int src1_ready;
int src1;
int src2_ready;
int src2;

public Scheduler_Block() {
	super();
	// TODO Auto-generated constructor stub
}
public Scheduler_Block(int seqNo, int src1Ready, int src1, int src2Ready,
		int src2) {
	super();
	seq_no = seqNo;
	src1_ready = src1Ready;
	this.src1 = src1;
	src2_ready = src2Ready;
	this.src2 = src2;
}
public int getSeq_no() {
	return seq_no;
}
public void setSeq_no(int seqNo) {
	seq_no = seqNo;
}
public int getSrc1_ready() {
	return src1_ready;
}
public void setSrc1_ready(int src1Ready) {
	src1_ready = src1Ready;
}
public int getSrc1() {
	return src1;
}
public void setSrc1(int src1) {
	this.src1 = src1;
}
public int getSrc2_ready() {
	return src2_ready;
}
public void setSrc2_ready(int src2Ready) {
	src2_ready = src2Ready;
}
public int getSrc2() {
	return src2;
}
public void setSrc2(int src2) {
	this.src2 = src2;
}

}
