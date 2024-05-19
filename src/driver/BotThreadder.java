package driver;

import java.util.ArrayList;

import botPackage.Bot;

public class BotThreadder implements Runnable {
	//Define properties of the bot
	static int inputs = 11;
	static int outputs = 9;
	static int[] layers = {8};
	
	//Define properties of the experiment
	static int creatureNumber = 96; 
	static double mutationRate = 0.0055;
	static double swapRate = 0.5;
	static int timer = 100;
	static int threadNumber = 16;
	
	static GamePlayer gamePlayer = new GamePlayer(inputs, outputs, layers, creatureNumber, mutationRate, swapRate, timer);

	//calc values
	static int startPoint = 0;
	static int endPoint = 0;
	static volatile int threadsDone = 0;
	
	public void run(){
		try {
			//System.out.println("Starting thread with: " + startPoint + ", " + endPoint);
			gamePlayer.botsCompete(startPoint, endPoint);
			threadsDone++;
		}
		catch (Exception e) {
			// Throwing an exception
			System.out.println("Exception is caught " + e);
		}
    }
	
	public static void botFullRun() {
		System.out.println("Filling");
		
		//Fill the bot list
		gamePlayer.botsFill();
		
		BotRecorder recorder = new BotRecorder(gamePlayer.botList.get(0).genes().sequence().size(), timer);
		
		System.out.println("Filt");
		
		double max = 0;
		double NRank = 0.0;
		
		//Repeat for the length of the timer
		for(int x = 0; x < timer; x++) {
			threadsDone = 0;
			
			for(int y = 0; y < creatureNumber; y += (creatureNumber/threadNumber)) {
				//System.out.println(y + ", " + (y + (creatureNumber/threadNumber)));
				startPoint = y;
				endPoint = y + (creatureNumber/threadNumber);
				
				Thread object = new Thread(new BotThreadder());
	            object.start();
	            /*
	            System.out.println("Invoking join");
	            t2.join();
	            System.out.println("Returned from join");
	            */
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			while(true) {
				if(threadsDone != threadNumber) {
					//System.out.println(threadsDone);
					
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else {
					break;
				}
			}
			
			NRank = gamePlayer.getNormalizedRank();
			if(NRank > max) {
				max = NRank;
				System.out.print('*');
			}
			
			System.out.println("Cycle " + x + ", normalized improvement = " + NRank);
			
			//sort list
			gamePlayer.botsSort();
			//System.out.println(gamePlayer.botList.toString());

			//print out preview
			ArrayList<Integer> list = new ArrayList<Integer>();
			for(int z = 0; z < gamePlayer.botList.get(0).genes().sequence().size(); z++) {
				list.add(0);
			}
			for(int y = 0; y < creatureNumber; y++) {
				for(int z = 0; z < gamePlayer.botList.get(0).genes().sequence().size(); z++) {
					list.set(z, list.get(z) + gamePlayer.botList.get(y).genes().sequence().get(z));
				}
			}
			for(int z = 0; z < gamePlayer.botList.get(0).genes().sequence().size(); z++) {
				list.set(z, list.get(z) / creatureNumber);
			}
			recorder.record(list);
			System.out.println("Best bot with a score of " + gamePlayer.botList.get(0).getRank() + " = " + gamePlayer.botList.get(0).genes().sequence().toString());
			
			//prune list
			gamePlayer.botsPrune();
			
			//refill from best
			gamePlayer.botsRefill();
			
			//scramble the list to get random pairs
			gamePlayer.botsScramble();
			
			//kinky time :3
			gamePlayer.botsSex();
			
			//mutate genes
			gamePlayer.botsMutate();
			
		}
	}
	
	public static void main(String[] args) {
		if(creatureNumber%threadNumber != 0) {
			//Code gets angry when the work can't be divided evenly amongst threads.
			System.out.println("Error: creatureNumber must be a multiple of threadNumber");
			System.out.println("A close valid number would be " + Math.round((double)creatureNumber/threadNumber)*threadNumber);
		}else {
			botFullRun();
			Bot bestBot = gamePlayer.getBestBot();
			System.out.println("Best bot with a score of " + bestBot.getRank() + " = " + bestBot.genes().sequence().toString());
		}
	}
}