package botPackage;

public class Output extends Node{

	public Output() {
		
	}
	
	public Double getOutput() {
		
		if (output > 1) {
			return(1.0);
		}else if(output < -1) {
			return(-1.0);
		}
		
		return(output);
	}
}
