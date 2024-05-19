 package botPackage;

import java.util.ArrayList;

public class MutationTesting {
	
	public static ArrayList<Bot> sortRank(ArrayList<Bot> list){
		ArrayList<Bot> tempList = new ArrayList<Bot>();
		while(list.size() > 0) {
			int pointer = 0;
			for(int x = 0; x < list.size(); x++) {
				if(list.get(pointer).getRank() < list.get(x).getRank()) {
					pointer = x;
				}
			}
			tempList.add(new Bot(list.get(pointer)));
			list.remove(pointer);
		}
		return(tempList);
	}
	
	public static void main(String[] args) {
		double rankAvg = 0.0;
		double bestRate = 1.0;
		double bestRank = 0.0;
		
		for(double rate = 1; rate > 0.0; rate -= 0.001) {
			System.out.println("Testing " + rate);
			int botNumber = 100;
			Double mutRate = rate;
			int[] layers = {8, 8};
			int time = 100;
			
			ArrayList<Bot> bots = new ArrayList<Bot>();
			
			//System.out.println("Creating...");
			for(int x = 0; x < botNumber; x++) {
				bots.add(new Bot(10, 9, layers));
				//System.out.println(bots.get(x).genes.sequence);
			}
			
			//System.out.println("Starting timer...");
			for(int x = 0; x < time; x++) {
				ArrayList<Bot> tempList = new ArrayList<Bot>();
				
				//System.out.println("Ranking...");
				rankAvg = 0.0;
				int botLength = bots.get(0).genes.sequence.size();
				for(int y = 0; y < botNumber; y++) {
					//System.out.println(bots.get(y).genes.sequence);
					bots.get(y).setRank(0);
					for(int z = 0; z < botLength; z++) {
						if(bots.get(y).genes.sequence.get(z) == 0) {
							bots.get(y).setRank(bots.get(y).getRank() + 1);
						}
					}
					rankAvg += bots.get(y).getRank();
					//System.out.println(bots.get(y).getRank());
				}
				
				//System.out.println("Pruning...");
				rankAvg /= botNumber;
				//System.out.println("Average = " + rankAvg);
				for(int y = 0; y < botNumber; y++) {
					if(bots.get(y).rank >= rankAvg) {
						//System.out.println(bots.get(y).genes.sequence);
						tempList.add(bots.get(y));
					}
				}
				bots = new ArrayList<Bot>(sortRank(bots));
				
				//System.out.println("Refilling...");
				for(int y = 0; y < botNumber; y++) {
					if(tempList.size() < botNumber) {
						//System.out.println(bots.get(y).genes.sequence);
						tempList.add(new Bot(tempList.get(y)));
					}
				}
				
				//System.out.println("Mutating...");
				for(int y = 0; y < botNumber; y++) {
					//System.out.println("Next gene...");
					//System.out.println(bots.get(y).genes.sequence);
					tempList.get(y).genes.mutate(mutRate);
					//System.out.println(bots.get(y).genes.sequence);
				}
				
				//System.out.println("Resetting...");
				bots = new ArrayList<Bot>(tempList);
			}
			
			if(rankAvg > bestRank) {
				bestRank = rankAvg;
				bestRate = rate;
			}
		}
		System.out.println(bestRate);
		System.out.println(bestRank);
	}
}
