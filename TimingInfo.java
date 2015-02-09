
public class TimingInfo {
int instr_No;
int IF_in;
int IF_out;
int ID_in;
int ID_out;
int IS_in;
int IS_out;
int EX_in;
int EX_out;
int WB_in;
int WB_out;
public TimingInfo() {
	super();
	// TODO Auto-generated constructor stub
}
public TimingInfo(int instrNo, int iFIn, int iFOut, int iDIn, int iDOut,
		int iSIn, int iSOut, int eXIn, int eXOut, int wBIn, int wBOut) {
	super();
	instr_No = instrNo;
	IF_in = iFIn;
	IF_out = iFOut;
	ID_in = iDIn;
	ID_out = iDOut;
	IS_in = iSIn;
	IS_out = iSOut;
	EX_in = eXIn;
	EX_out = eXOut;
	WB_in = wBIn;
	WB_out = wBOut;
}
public int getInstr_No() {
	return instr_No;
}
public void setInstr_No(int instrNo) {
	instr_No = instrNo;
}
public int getIF_in() {
	return IF_in;
}
public void setIF_in(int iFIn) {
	IF_in = iFIn;
}
public int getIF_out() {
	return IF_out;
}
public void setIF_out(int iFOut) {
	IF_out = iFOut;
}
public int getID_in() {
	return ID_in;
}
public void setID_in(int iDIn) {
	ID_in = iDIn;
}
public int getID_out() {
	return ID_out;
}
public void setID_out(int iDOut) {
	ID_out = iDOut;
}
public int getIS_in() {
	return IS_in;
}
public void setIS_in(int iSIn) {
	IS_in = iSIn;
}
public int getIS_out() {
	return IS_out;
}
public void setIS_out(int iSOut) {
	IS_out = iSOut;
}
public int getEX_in() {
	return EX_in;
}
public void setEX_in(int eXIn) {
	EX_in = eXIn;
}
public int getEX_out() {
	return EX_out;
}
public void setEX_out(int eXOut) {
	EX_out = eXOut;
}
public int getWB_in() {
	return WB_in;
}
public void setWB_in(int wBIn) {
	WB_in = wBIn;
}
public int getWB_out() {
	return WB_out;
}
public void setWB_out(int wBOut) {
	WB_out = wBOut;
}

}
