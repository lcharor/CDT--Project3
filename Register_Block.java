
public class Register_Block {
int readyBit;
int Value_Tag;
public Register_Block(int readyBit, int valueTag) {
	super();
	this.readyBit = readyBit;
	Value_Tag = valueTag;
}
public int getReadyBit() {
	return readyBit;
}
public void setReadyBit(int readyBit) {
	this.readyBit = readyBit;
}
public int getValue_Tag() {
	return Value_Tag;
}
public void setValue_Tag(int valueTag) {
	Value_Tag = valueTag;
}
}
