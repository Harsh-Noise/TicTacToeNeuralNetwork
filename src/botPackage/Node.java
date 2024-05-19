package botPackage;

import java.util.ArrayList;

public class Node {
	//Input numbers to be averaged
	ArrayList<Double> input = new ArrayList<Double>();
	
	//The genes describing the output locations dedicated to this node
	ArrayList<Integer> outputLocations = new ArrayList<Integer>();
	
	//Storage for the output data
	Double output = 0.0;
	
	
	public Node() {
		
	}
	
	//Adds some input data to the input list
	public void sendInput(Double data) {
		input.add(data);
	}
	
	//returns the average of the input data
	public Double averageInputs() {
		Double sum = 0.0;
		for(int x = 0; x < input.size(); x++) {
			sum += input.get(x);
		}
		if(input.size() > 0) {
			return(sum/input.size()); 
		}else {
			return(0.0);
		}
	}
	
	//Calculates the output by taking the average of the inputs
	public void calculateOutput() {
		output = averageInputs();
	}
	
	//Sets the output data to a specific number
	public void setOutput(Double data) {
		output = data;
	}
	
	//Returns output data
	public Double getOutput() {
		return(output);
	}
	
	//Returns output location data
	public ArrayList<Integer> getConnections() {
		return(outputLocations);
	}
	
	public String toString() {
		return("Input data:" + input.toString() +  " Outputs: " + outputLocations.toString() + " Data: [" + output.toString() + "]");
	}
	
	public static void main(String[] args) {
		Node node = new Node();
		
		System.out.println(node.getOutput());
		
		node.calculateOutput();
		
		System.out.println(node.getOutput());
	}

}
