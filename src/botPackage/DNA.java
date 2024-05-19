package botPackage;

import java.util.ArrayList;
import java.util.Random;

public class DNA {
	
	//ArrayList for holding the DNA sequence
	ArrayList<Integer> sequence = new ArrayList<Integer>();
	
	//ArrayList for holding size and number of middle neurons
	//ArrayList<Integer> layerList = new ArrayList<Integer>();
	int[] layerNums;
	
	//Storage for input, output, and layers
	int input, output, layers = 0;

	
	Random rand = new Random();
		
	//Constructor taking the number of sensory organs, outputs, and node layers
	public DNA(int sensory, int output, int[] layerNums) {
		
		//System.out.println(layerNums.length);
		
		if(layerNums.length == 0) {
			//If there are no neurons, only add the connections between input and output
			for(int x = 0; x < (sensory * output); x++) {
				sequence.add(-10 + rand.nextInt(21));
			}
		}else {
			//Else, add connections between input and first layer of neurons
			for(int x = 0; x < (sensory * layerNums[0]); x++) {
				sequence.add(-10 + rand.nextInt(21));
			}
			
			//Add connections between neuron layers
			for(int x = 0; x < layerNums.length - 1; x++) {
				//add connections for each neuron
				for(int y = 0; y < (layerNums[x] * layerNums[x+1]); y++) {
					sequence.add(-10 + rand.nextInt(21));
				}
			}
			
			//add connections between last layer of neurons and output
			for(int x = 0; x < (layerNums[layerNums.length - 1] * output); x++) {
				sequence.add(-10 + rand.nextInt(21));
			}
			
			//add genes to account for the cubic transformation (it doesn't matter in what order you add genes, only that the correct number of them are present)
			for(int x = 0; x < layerNums.length; x++) {
				for(int y = 0; y < layerNums[x]; y++) {
					sequence.add(-10 + rand.nextInt(21));
					sequence.add(-10 + rand.nextInt(21));
					sequence.add(-10 + rand.nextInt(21));
					sequence.add(-10 + rand.nextInt(21));
				}
			}
		}
	}
	
	//DNA constructor where the size and number of layers are determined by the genes
	/*
	public DNA(int sensory, int output) {
		int input = sensory;
		this.output = output;
		layers = rand.nextInt(11);
		
		for(int x = 0; x < layers; x++) {
			layerList.add(rand.nextInt(11));
		}
	}
	*/
	
	//Create a copy of a DNA object
	public DNA(DNA copy) {
		this.sequence = copy.sequence;
	}
	
	//Create a DNA object given a string formatted like the .toString() function
	public DNA(String list) {
		//Get size of the string
		int listLength = list.length();
		
		//Storage for the next number to interpret as an integer
		String nextNumber = "";
		
		for(int x = 1; x < listLength; x++) {
			//System.out.println(nextNumber);
			//System.out.println(list.charAt(x));
			if(list.charAt(x) != ',' && list.charAt(x) != ' ' && list.charAt(x) != ']') {
				//System.out.println("Not a valid number character");
				nextNumber += String.valueOf(list.charAt(x));
			}else if(nextNumber.length() == 0) {
				continue;
			}else {
				//System.out.println("Adding " + nextNumber);
				sequence.add(Integer.parseInt(nextNumber));
				nextNumber = "";
			}
		}
	}
	
	//mutates ratePercent of the genome
	public void mutate(double ratePercent) {
		for(int x = 0; x < sequence.size(); x++) {
			if(rand.nextDouble() <= ratePercent) {
				sequence.set(x, -10 + rand.nextInt(21));
			}
		}
	}
	
	public void sex(DNA partner, double swapRate) {
		for(int x = 0; x < sequence.size(); x++) {
			if(rand.nextDouble() <= swapRate) {
				int storage = sequence.get(x);
				sequence.set(x, partner.sequence.get(x));
				partner.sequence.set(x, storage);
			}
		}
	}
	
	public ArrayList<Integer> sequence(){
		return(sequence);
	}
	
	public static void main(String[] args) {
		//ArrayList<Integer> layers = new ArrayList<Integer>();
		//layers.add(3);
		int[] layers = {3};
		DNA dna = new DNA(2, 2, layers);
		DNA partner = new DNA(2, 2, layers);
		System.out.println(dna.sequence.size());
		System.out.println(dna.sequence());
		System.out.println();
		dna.mutate(0.5);
		System.out.println(dna.sequence());
		System.out.println(partner.sequence());
		System.out.println();
		dna.sex(partner, 0.5);
		System.out.println(dna.sequence());
		System.out.println(partner.sequence());
	}
}
