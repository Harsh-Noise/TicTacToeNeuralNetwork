package botPackage;

import java.util.ArrayList;

public class NodeMatrix {
	
	//Matrix representing the inputs, neurons, and outputs
	ArrayList<ArrayList<Node>> matrix = new ArrayList<ArrayList<Node>>();
	
	public NodeMatrix(Bot bot) {
		
		//pointer for the location in the gene sequence
		int pointer = 0;
		
		//Storage of genes before they're added to each node
		ArrayList<Integer> tempArray = new ArrayList<Integer>();
		
		//If there are no neurons, only add the connections between input and output
		if(bot.getLayers().length == 0) {
			//Add ArrayList for input nodes
			matrix.add(new ArrayList<Node>());
			//Loop for total genes added per input
			for(int x = 0; x < bot.getInput(); x++) {
				//Loop for genes added per output
				for(int y = 0; y < bot.getOutput(); y++) {
					//Add the pointer's gene to the temp array, and move the pointer up one
					tempArray.add(bot.genes.sequence.get(pointer));
					pointer++;
				}
				//Add the list of output locations to the input node, and clear the temp array
				matrix.get(0).add(new Input(tempArray));
				tempArray.clear();
			}
			
			//Add arraylist for outputs
			matrix.add(new ArrayList<Node>());
			//loop for adding the correct number of outputs
			for(int x = 0; x < bot.getOutput(); x++) {
				matrix.get(1).add(new Output());
			}
		}
		else {
			//Add ArrayList for input nodes
			matrix.add(new ArrayList<Node>());
			//Else, add connections between input and first layer of neurons
			for(int x = 0; x < bot.getInput(); x++) {
				//Loop for genes added per output
				for(int y = 0; y < bot.getLayers()[0]; y++) {
					//Add the pointer's gene to the temp array, and move the pointer up one
					tempArray.add(bot.genes.sequence.get(pointer)); 
					pointer++;
				}
				//Add the list of output locations to the input node, and clear the temp array
				matrix.get(0).add(new Input(tempArray));
				tempArray.clear();
			}
			
			//Add neuron-neuron connections
			
			for(int x = 0; x < bot.getLayers().length - 1; x++) {
				//Add ArrayList for layer of neurons
				matrix.add(new ArrayList<Node>());
				for(int y = 0; y < bot.getLayers()[x]; y++) {
					//Add 4 genes for cubic transform
					tempArray.add(bot.genes.sequence.get(pointer));
					tempArray.add(bot.genes.sequence.get(pointer + 1));
					tempArray.add(bot.genes.sequence.get(pointer + 2));
					tempArray.add(bot.genes.sequence.get(pointer + 3));
					pointer += 4;
					for(int z = 0; z < bot.getLayers()[x + 1]; z++) {
						//add gene for each connector between neuron layers
						tempArray.add(bot.genes.sequence.get(pointer));
						pointer++;
					}
					//add neuron to the matrix
					matrix.get(x + 1).add(new Neuron(tempArray));
					tempArray.clear();
				}
			}
			
			//add final layer of neurons connected to output
			matrix.add(new ArrayList<Node>());
			for(int y = 0; y < bot.getLayers()[bot.getLayers().length-1]; y++) {
				//Add 4 genes for cubic transform
				tempArray.add(bot.genes.sequence.get(pointer));
				tempArray.add(bot.genes.sequence.get(pointer + 1));
				tempArray.add(bot.genes.sequence.get(pointer + 2));
				tempArray.add(bot.genes.sequence.get(pointer + 3));
				pointer += 4;
				for(int z = 0; z < bot.getOutput(); z++) {
					//add gene for each connector between neuron layers
					tempArray.add(bot.genes.sequence.get(pointer));
					pointer++;
				}
				//add neuron to the matrix
				matrix.get(bot.getLayers().length).add(new Neuron(tempArray));
				tempArray.clear();
			}
			
			//Add outputs
			matrix.add(new ArrayList<Node>());
			//loop for adding the correct number of outputs
			for(int x = 0; x < bot.getOutput(); x++) {
				matrix.get(bot.getLayers().length + 1).add(new Output());
			}
		}
	}
	
	public void seeMatrix() {
		for(int x = 0; x < matrix.size(); x++) {
			for(int y = 0; y < matrix.get(x).size(); y++){
				System.out.print("[" + matrix.get(x).get(y).toString() + "] ");
			}
			System.out.println(); 
		}
		for(int x = 0; x < matrix.size(); x++) {
			for(int y = 0; y < matrix.get(x).size(); y++){
				System.out.print("@");
			}
			System.out.println(); 
		}
	}
	
	public void setInputs(ArrayList<Double> matrixInputs){
		//Sets the input nodes to be full of values defined by an arraylist
		for(int x = 0; x < matrixInputs.size(); x++) {
			matrix.get(0).get(x).sendInput((double)matrixInputs.get(x));
		}
		//System.out.println(matrix.size());
	}
	
	public void calculate() {
		//for all layers of the matrix
		for(int x = 0; x < matrix.size(); x++) {
			//for all neurons in that layer
			for(int y = 0; y < matrix.get(x).size(); y++) {
				//set the output by calculating the data
				matrix.get(x).get(y).calculateOutput();
			}
			//If it's not the last layer with no output destination
			if(x != (matrix.size() - 1)) {
				//also for all neurons in that layer
				for(int y = 0; y < matrix.get(x).size(); y++) {
					//for all output locations stored in that neuron
					for(int z = 0; z < matrix.get(x).get(y).getConnections().size(); z++) {
						//if it is a boolean true gene
						if(matrix.get(x).get(y).getConnections().get(z) >= 0) {
							//send the output of the neuron to the input of that node in the next layer
							matrix.get(x+1).get(z).sendInput(matrix.get(x).get(y).getOutput());
							//System.out.print("Writing " + y + ": " + matrix.get(x).get(y).getOutput() + " to " + z + ", ");
						}
					}//System.out.println();
				}//System.out.println();
			}
		}
	}
	
	public ArrayList<Double> getOutputs(){
		ArrayList<Double> tempList = new ArrayList<Double>();
		for(int x = 0; x < matrix.get(matrix.size() - 1).size(); x++) {
			tempList.add(matrix.get(matrix.size()-1).get(x).getOutput());
		}
		return(tempList);
	}
	
	public static void main(String[] args) {
		int[] neurons = {8};
		
		Bot bot = new Bot(6, 9, neurons);
		
		System.out.println(bot.genes.sequence.toString());

		NodeMatrix matrix = new NodeMatrix(bot);		
		
		ArrayList<Double> inputs = new ArrayList<Double>();
		inputs.add(1.0);
		inputs.add(1.0);
		inputs.add(1.0);
		inputs.add(1.0);
		inputs.add(1.0);
		inputs.add(1.0);
		
		matrix.setInputs(inputs);
		
		matrix.seeMatrix();
		
		matrix.calculate();
		
		matrix.seeMatrix();
		
		System.out.println(matrix.getOutputs().toString());
	}
}
