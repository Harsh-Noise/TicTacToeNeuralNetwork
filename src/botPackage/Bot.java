package botPackage;

public class Bot {
	
	//The DNA object for this bot
	DNA genes;
	
	//The rank of this bot based on its performance
	double rank = 0.0;
	
	//Input and output describing the number of inputs and outputs of the network, layerNums describing the number and size of internal neuron layers
	int input, output = 0;
	int[] layerNums;
	
	public Bot(int input, int output, int[] layerNums) {
		this.input = input;
		this.output = output;
		this.layerNums = layerNums;
		genes = new DNA(input, output, layerNums);
		//System.out.println("Bot set up");
	}
	
	//Making a bot with custom DNA
	public Bot(int input, int output, int[] layerNums, String inputGenes) {
		this.input = input;
		this.output = output;
		this.layerNums = layerNums;
		genes = new DNA(inputGenes);
	}
	
	public Bot(Bot copy) {
		this.input = copy.input;
		this.output = copy.output;
		this.layerNums = copy.layerNums;
		this.genes = new DNA(copy.genes);
	}
	
	public DNA genes() {
		return(genes);
	}
	
	public double getRank() {
		return(rank);
	}
	
	public int getInput() {
		return(input);
	}
	
	public int getOutput() {
		return(output);
	}
	
	public int[] getLayers() {
		return(layerNums);
	}
	
	public void setRank(double rank) {
		this.rank = rank;
	}
	
	public String toString() {
		return("[" + getRank() + ", " + genes.sequence.toString() + "]");
	}
	
	public static void main(String[] args) {
		int[] layers = {33};
		
		Bot one = new Bot(2, 3, layers);
		Bot two = new Bot(2, 3, layers);
		
		System.out.println(one.toString());
		System.out.println(two.toString());
		System.out.println();
		
		one.genes.sex(two.genes, 0.5);

		System.out.println(one.toString());
		System.out.println(two.toString());
	}
}
