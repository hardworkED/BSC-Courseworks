import java.util.Arrays;
import java.util.Scanner;

public class Game {

	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);
		
		String[] pokeType = {"Attacking", "Defending", "Fairy"};
		String[] engColour = {"Red\t", "Blue\t", "Yellow\t"};
		String[] coin = {"head", "tail"};
		Pok¨¦mon[] players = new Pok¨¦mon[12];
		Pok¨¦mon[] player1 = new Pok¨¦mon[6];
		Pok¨¦mon[] player2 = new Pok¨¦mon[6];
		int a1 = 0, d1 = 0, a2 = 0, d2 = 0;
		
		while (a1 < 2 || d1 < 1 || a2 < 2 || d2 < 1) {
			a1 = 0;
			d1 = 0;
			a2 = 0;
			d2 = 0;
			for (int n = 1; n <= 12; n++) {
				Pok¨¦mon p = new Pok¨¦mon(n, pokeType[(int)(Math.random()*3)], 0, 0, (int)(Math.random() * 31 + 50),
						(int)(Math.random() * 31 + 20), engColour[(int)(Math.random() * 3)], 0, 0, "Active\t");
				
				//reduce colourless card existence
				double colourless = (Math.random() * 1);		
				if (colourless > 0.95) {
					p.setEnergyColour("Colourless");
				}
				
				players[n - 1] = p;
				
				//reset no. for player 2
				
				
				if (n > 6) {
					p.setNum(n - 6);
					if (p.getType().equals("Attacking")) {
						a2 += 1;
					}
					
					else if (p.getType().equals("Defending")) {
						d2 += 1;
					}
					
				}
				
				else {
					if (p.getType().equals("Attacking")) {
						a1 += 1;
					}
					
					else if (p.getType().equals("Defending")) {
						d1 += 1;
					}
					
				}
				
			
				if (p.getType().equals("Attacking")) {
					p.setAttackPoint((int)(Math.random() * 4 + 2));
				}
				
				else if (p.getType().equals("Defending")) {
					p.setResistancePoint((int)(Math.random() * 3 + 1));
				}
				
				
				
				//test
				//System.out.println(a1+a2+d1+d2);
				
			}
		}
		
		//split array
		System.arraycopy(players, 0, player1, 0, player1.length);
		System.arraycopy(players, player1.length, player2, 0, player2.length);
		
		
		System.out.print("Key in the name for player 1: ");
		String playerName1 = input.nextLine();
		System.out.print("Key in the name for player 2: ");
		String playerName2 = input.nextLine();
		
		//display cards
		System.out.println("\n" + playerName1 + "'s cards:");
		System.out.println("©°©¤©¤©¤©Ð©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©Ð©¤©¤©¤©¤©¤©¤©¤©Ð©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©Ð©¤©¤©¤©¤©¤©¤©¤©¤©¤©Ð©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©Ð©¤©¤©¤©¤©¤©¤©¤"
				+ "©¤©¤©¤©¤©¤©¤©¤©¤©¤©Ð©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©Ð©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©Ð©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©´");
		System.out.println("©¦No.©¦Type\t©¦Stage\t©¦Experience©¦Hit Point©¦Energy\t©¦Energy Colour\t ©¦"
				+ "Attack Point©¦Resistance Point©¦Status\t\t©¦");
		for (int n = 0; n < 6; n++) {
			System.out.println("©À©¤©¤©¤©à©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©à©¤©¤©¤©¤©¤©¤©¤©à©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©à©¤©¤©¤©¤©¤©¤©¤©¤©¤©à©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©à©¤©¤©¤©¤©¤©¤©¤"
					+ "©¤©¤©¤©¤©¤©¤©¤©¤©¤©à©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©à©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©à©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©È");
			System.out.println(player1[n]);
		}
		System.out.println("©¸©¤©¤©¤©Ø©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©Ø©¤©¤©¤©¤©¤©¤©¤©Ø©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©Ø©¤©¤©¤©¤©¤©¤©¤©¤©¤©Ø©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©Ø©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤"
				+ "©¤©¤©¤©¤©¤©Ø©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©Ø©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©Ø©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¼");
		System.out.println("\n" + playerName2 + "'s cards:");
		System.out.println("©°©¤©¤©¤©Ð©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©Ð©¤©¤©¤©¤©¤©¤©¤©Ð©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©Ð©¤©¤©¤©¤©¤©¤©¤©¤©¤©Ð©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©Ð©¤©¤©¤©¤©¤©¤©¤"
				+ "©¤©¤©¤©¤©¤©¤©¤©¤©¤©Ð©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©Ð©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©Ð©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©´");
		System.out.println("©¦No.©¦Type\t©¦Stage\t©¦Experience©¦Hit Point©¦Energy\t©¦Energy Colour\t ©¦"
				+ "Attack Point©¦Resistance Point©¦Status\t\t©¦");
		for (int n = 0; n < 6; n++) {
			System.out.println("©À©¤©¤©¤©à©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©à©¤©¤©¤©¤©¤©¤©¤©à©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©à©¤©¤©¤©¤©¤©¤©¤©¤©¤©à©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©à©¤©¤©¤©¤©¤©¤©¤"
					+ "©¤©¤©¤©¤©¤©¤©¤©¤©¤©à©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©à©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©à©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©È");
			System.out.println(player2[n]);
		}
		System.out.println("©¸©¤©¤©¤©Ø©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©Ø©¤©¤©¤©¤©¤©¤©¤©Ø©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©Ø©¤©¤©¤©¤©¤©¤©¤©¤©¤©Ø©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©Ø©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤"
				+ "©¤©¤©¤©¤©¤©Ø©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©Ø©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©Ø©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¼");
		
		
	}

}
