This is a rough breakdown of the usage, contents, and conclusions of my neural network project. I'll try to give as many details as needed to fully understand, but I can't promise you'll understand what I'm doing.

First, a disclaimer:
I have never taken a class on neural networks, and only have the most surface level understanding of them. In my pride, I am doing this from scratch, only learning from professionals when I hit road blocks too steep for me to climb on my own. I did make an evolutionary algorithm in python once, which changed an array of integers from random to 9. It was kinda cool.

Usage-
	To change the testing and bot variables, the code will need to be changed and the program recompiled.
	The main in driver.GamePlayer is for training on a single core.
	The main in driver.BotThreadder is for training on multiple cores. 
	The main in driver.BotVsPlayer is designed for you to play against a bot.
	All except for BotThreadder have their editable variables in the main class. BotThreadder has them at the top.
	All training objects have variables for changing the inputs, outputs, and number of internal neurons. In/out shouldn't be changed, unless you add more data to feed to the bot. For internal neurons, the size of the list represents the number of layers, and each int represents how many neurons are in that layer.  
	
Contents-
	botPackage-
		Bot.java - An object defining a single bot. Contains its DNA and rank.
		DNA.java - An object defining the DNA of a bot. Consists of a list of ints ranging from -10 to 10. 
		Node.java - An object defining a node of the neural network.
			Input.java - A sensory node, to take in data and feed it to subsequent nodes.
			Neuron.java - An internal node, taking in data from a node and transforming it, before sending it to another node.
			Output.java - A node which takes in data from a node, and exports it.
		NodeMatrix.java - An object responsible for creating and arranging nodes into a "brain" given a bot's DNA. Contains methods for setting the input data, processing it, and exporting the output.
		MutationTesting.java - An algorithm to determine the best mutation rate to use. Runs a short evolutionary algorithm over many mutation rates and exports the rate with the highest average rank. It turns out that rate is 0.0055% of genes mutated per turn.
	tttPackage-
		GameBoard.java - An object defining the game of tic-tac-toe. Contains a board to store moves, and methods to play moves and detect winners. 
		GameTree.java - An object that recursively creates a tree of all moves in the game tic-tac-toe, and attempts to rank the moves. Used for bot training.
		TreeInvestigator.java - Runs the investigate algorithm in GameTree.java, allowing you to browse the tree and see its data for debuging.
	driver-
		GamePlayer.java - An object for training bots. Runs on a single core. Outputs the DNA of the highest ranked bot of the final iteration.
		BotThreadder.java - Creates a GamePlayer object and splits its work over multiple threads. Outputs the DNA of the highest ranked bot of the final iteration.
		BotVsPlayer.java - Allows the user to play a game of tic-tac-toe vs a bot given its DNA and the size of the bot.
		BotRecorder.java - Data analysis, takes the average DNA of a group and records it as a stripe of pixels on a PNG, red for negative, green for positive. Records the next generation below it.
	Notes.txt - mad ramblings with some more details.
	
Conclusions-
	My goal with this project was to create a functional neural network: a system who's behavior can be varied drastically by the simple modification of a DNA object. I have succeeded in doing that. Have I made a TTT AI powerful enough to crush any human on the planet? No. Tic tac toe was only the medium for my goal to reside in, and not even my first choice. I had originally wanted to use the game Connect Four, however I decided something simpler would be good, especially because conquering the game itself was more of a secondary goal. My room mate told me later that he didn't think TTT was a skill based game, and on reflection that might be somewhat the case. There are some obvious good moves to make, but an opponent placing mostly random moves can have a good chance of shutting you down. I saw this first hand after the creation of BotRecorder. You can see in BotViewer.png the result of one such run. The top is black, because all bots are randomized and their DNA averages to zero. But after a few generations progress, they start to take shape and have a preference. However, very few patterns seem to emerge after this, and every gene of the DNA rapidly changes without ever settling down in one configuration. This seed of doubt was not helped by some struggles in ranking the bots. Bots are ranked against each other, not against an external standard. So as one bot preforms better, another must preform worse, causing the average to not change much. Even as the bots grow more skilled as a group, their equally skilled opponents counter the growth of their ranks on average. It was difficult to tell how much each generation was growing, and could only be estimated. The normalized improvement was an effort to help estimate growth. In the end, I came to the conclusion that it did not take a lot to be an okay player at tic-tac-toe, and called off my overnight training routines. It's a bitter end to my biggest project yet, but it's important to remember I accomplished what I set out to do.
	
