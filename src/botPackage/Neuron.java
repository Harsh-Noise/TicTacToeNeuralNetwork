package botPackage;

import java.util.ArrayList;

public class Neuron extends Node{	
	//Data for cubic transformation
	Double a = 0.0;
	Double b = 0.0;
	Double c = 0.0;
	Double d = 0.0;
	
	public Neuron(ArrayList<Integer> genes) {
		//Convert integer data to double 
		this.a = toDouble(genes.get(0));
		this.b = toDouble(genes.get(1));
		this.c = toDouble(genes.get(2));
		this.d = toDouble(genes.get(3));
		
		ArrayList<Integer> tempArray = new ArrayList<Integer>();
		for(int x = 4; x < genes.size(); x++) {
			tempArray.add(genes.get(x));
		}
		outputLocations = new ArrayList<Integer>(tempArray);
	}
	
	public Neuron() {
		
	}
	
	public void setData(int a, int b, int c, int d) {
		//Convert integer data to double 
		this.a = toDouble(a);
		this.b = toDouble(b);
		this.c = toDouble(c);
		this.d = toDouble(d);
	}
	
	public void setOutputLocations(ArrayList<Integer> outputList) {
		outputLocations = new ArrayList<Integer>(outputList);
	}
	
	private double toDouble(int a) {
		return(a/10.0);
	}
	
	private Double cubicTransform(Double data) {
		//Preform a cubic transformation on some data using the neuron's genes
		return(a*(Math.pow(data, 3))+b*(Math.pow(data, 2))+c*(data)+d);
	}
	
	public void calculateOutput() {
		//Set the neuron's output
		output = cubicTransform(averageInputs());
	}
	
	public String toString() {
		return("Input data: " + input.toString() + " Genes: [" + a + " " + b + " " + c + " " + d + "] " + " Outputs: " + outputLocations.toString() + " Data: [" + output.toString() + "]");
	} 
	
	public static void main(String[] args) {
		Neuron zap = new Neuron();
		
		zap.setData(5, 2, -8, 1);
		
		zap.sendInput(0.5);
		
		System.out.println(zap.toString());
		
		System.out.println(zap.averageInputs());
		
		zap.calculateOutput();
		
		System.out.println(zap.getOutput());
	}
}
