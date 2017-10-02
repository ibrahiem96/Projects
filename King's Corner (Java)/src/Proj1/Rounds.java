package Proj1;
	

import java.util.Scanner;
import Proj1.Deck;

/*
 * Ibrahiem Mohammad
 * 2/4/16
 * CS 342
 * 
 * This is the main class which runs the entire game. It initializes a deck and starts the main game command.
 * 
 * This class contains few errors:
 * - the game does not end (I believe my loops somewhere may not be correct)
 * - the merging is not completely correct
 * - the deck sometimes deals all 7 cards, and sometimes doesn't
 * 
 */

public class Rounds {

	//public static boolean gameOver = false;
	//public static boolean RoundOver = false;
	
	public static void main(String args[]) throws InterruptedException{
	
	LLStack deck = new LLStack();
	
	int flipCoin = 1 + (int)(Math.random()*2);
	//System.out.println(flipCoin);
	
	//get user name
	Scanner sc = new Scanner (System.in);
	System.out.println("Enter user name: ");
	
	String u = sc.next();
	
	PlayerHand player = new PlayerHand(u);
	AI computer = new AI("AI");
	
	//initialize 8 card piles
	CardPile one = new CardPile(1);
	CardPile two = new CardPile(2);
	CardPile three = new CardPile(3);
	CardPile four = new CardPile(4);
	CardPile five = new CardPile(5);
	CardPile six = new CardPile(6);
	CardPile seven = new CardPile(7);
	CardPile eight = new CardPile(8);
	
	//init deck
	Deck d = new Deck(deck, one, two, three, four, five, six, seven, eight, player, computer, flipCoin);

	//main game command output/input starts here
	System.out.println("Welcome to the Card Game King's Corner.");
	System.out.println("To Start the game enter 'S'.");
	System.out.println("For more instructions on how to play or more information about the game enter 'H'.");
	System.out.println("To find out more information about the game and developer enter 'A'.");
	System.out.println("To exit, enter 'Q'.");
	
		
	Scanner sc2 = new Scanner(System.in);
	String options;
	
	 while(sc2.hasNext())
		{
		
		options = sc2.next();
		
		if(!options.equals("Q") && 
		   !options.equals("H") && 
		   !options.equals("A") && 
		   !options.equals("D") && 
		   !options.equals("L") && 
		   !options.equals("M") &&
		   !options.equals("S"))
		{
			System.out.println("Please Enter a Valid Command");
			continue;
		}
		
		
		if (options.equals("S")){
			
			//System.out.println("To quit back to the main menu at any time, enter Q");
			
			
			print_game_board(player, one, two, three, four, five, six, seven, eight);
			
			if (PlayerHand.gameOver){
				Scanner sc4 = new Scanner (System.in);
				String yOrN = sc4.next(); 
				System.out.println("Play Again? Y/N");
				
				if (yOrN.equals("Y")) PlayerHand.gameOver = false;
				else if ( yOrN.equals("Q")) break;
				else break;
			}
			while (!PlayerHand.gameOver){

			Scanner sc3 = new Scanner (System.in);
			String subOptions;
			while (sc3.hasNext()){
				subOptions = sc3.next();
				
					if(subOptions.equals("D")){
					
						//add while loop here??
		    	
					if(deck.isEmpty()){
			    		computer.scoreCalc();
			    		player.scoreCalc();
			    		System.out.println("Round Over");
			    		flipCoin++;
			    		d = new Deck(deck, one, two, three, four, five, six, seven, eight, player, computer, flipCoin);
					}
		    	
					player.draw_to_hand(deck);
					computer.AI_game(deck, one, two, three, four, five, six, seven, eight);
					print_game_board(player, one, two, three, four, five, six, seven, eight);
					
					if(player.deck_playerHand.isEmpty()){
			    		System.out.println(u + " wins this round :)");
			    		computer.scoreCalc();
			    		flipCoin++;
			    		//RoundOver = true;
			    		//continue;
			    		Deck another = new Deck(deck, one, two, three, four, five, six, seven, eight, player, computer, flipCoin);
					}
		    	
					else if(computer.deck_playerHand.isEmpty()){
			    		System.out.println("AI wins this round :(");
			    		player.scoreCalc();
			    		flipCoin++;
			    		//RoundOver = true;
			    		//continue;
			    		Deck another = new Deck(deck, one, two, three, four, five, six, seven, eight, player, computer, flipCoin);
			    	}
				}
		    	
				if(subOptions.equals("L")){
					
					String rs1; int p1;
					
					if ( sc3.hasNext()) rs1 = sc3.next();
					
					else {
						System.out.println ("Invalid Entry");
						continue;
					}
		    	
					if ( sc3.hasNextInt()) p1 = sc3.nextInt();
			   	   
					else {
			   	     	System.out.println ("Invalid Entry");
			   	     	continue;
			   	   	}
		    	
					CardPile pHolder = getCardPile(p1, one, two, three, four, five, six, seven, eight);
					
					player.draw_to_pile(rs1, pHolder);
					
					print_game_board(player, one, two, three, four, five, six, seven, eight);
					
					if(player.deck_playerHand.isEmpty()){
						System.out.println(u + "wins this round :)");
						flipCoin++;
			    		//RoundOver = true;
			    		//continue;
						Deck another = new Deck(deck, one, two, three, four, five, six, seven, eight, player, computer, flipCoin);
					}
		    	
					else if(computer.deck_playerHand.isEmpty()) {
			    		System.out.println("AI wins this round :(");
			    		flipCoin++;
			    		//RoundOver = true;
			    		//continue;
			    		Deck another = new Deck(deck, one, two, three, four, five, six, seven, eight, player, computer, flipCoin);
					}
				}
		    
				if(subOptions.equals("M")){
					int p; int p2;
		    	
					if ( sc3.hasNextInt()) p = sc3.nextInt();
					
					else {
			   	     System.out.println ("Invalid Entry");
			   	     continue;
			   	   }
		    	
		    	if (sc3.hasNextInt()) p2 = sc3.nextInt();
			    
		    	else {
			   	     System.out.println ("Invalid Entry");
			   	     continue;
		    	}
		    	
		    	CardPile cardpile1 = getCardPile(p, one, two, three, four, five, six, seven, eight);
		    	
		    	CardPile cardpile2 = getCardPile(p2, one, two, three, four, five, six, seven, eight);
		    	
		    	player.merge_piles(cardpile1, cardpile2);
		    	
		    	print_game_board(player, one, two, three, four, five, six, seven, eight);
			}
		    
		    if(subOptions.equals("Q")) break;
		    
		    if(player.deck_playerHand.isEmpty()){
		    	System.out.println(u + " wins the game! :D");
		    	flipCoin++;
		    	PlayerHand.gameOver = true;
		    	//continue;
		    	Deck another = new Deck(deck, one, two, three, four, five, six, seven, eight, player, computer, flipCoin);
		    }
		    		
	    	else if(computer.deck_playerHand.isEmpty()){
	    		System.out.println("Computer wins the game :'(");
	    		flipCoin++;
	    		PlayerHand.gameOver = true;
	    		//continue;
	    		Deck another = new Deck(deck, one, two, three, four, five, six, seven, eight, player, computer, flipCoin);
	    	}
		    	
		}
				
		}

				
	}
			
		
	else if(options.equals("Q")) break;
	    
	else if(options.equals("H")){
	   	System.out.println("You have selected the Help option.");
		System.out.println("To draw a card from the pile, simply enter 'D'.");
		System.out.println("To lay a card on a specific pile, enter 'L <Card> <Pile>' "
				+ "where the card is signified by rank then suit: 8C, AC, 7H."
				+ " And the card piles range from 1-8.");
		System.out.println("To move a card from one pile to another, enter 'M <Original Pile> <New Pile>'.");
	    	
	}
	    
	if(options.equals("A")){
	   	System.out.println("This program was developed by Ibrahiem Mohammad as part of a project "
			+ "in CS 342 at UIC on 1/31/16.");
		System.out.println("To find out more about the game King's Corner, "
			+ "visit this link: http://www.pagat.com/domino/kingscorners.html");
	}
	    
	}
	        			
   	}

	/*
	 * Prints out card game output
	 */
	static void print_game_board(PlayerHand user, 
		CardPile one, 
		CardPile two, 
		CardPile three, 
		CardPile four,
		CardPile five, 
		CardPile six, 
		CardPile seven, 
		CardPile eight) throws InterruptedException {
	
		user.print_cards_hand();
	
		one.print_cp();
	    two.print_cp();
	    three.print_cp();
	    four.print_cp();
	    five.print_cp();
	    six.print_cp();
	    seven.print_cp();
	    eight.print_cp();
	    
	    System.out.println("YOUR TURN");
	}

	//returns a card pile for the program to use in the game
	static CardPile getCardPile(int cp, 
			CardPile one, 
			CardPile two, 
			CardPile three, 
			CardPile four,
			CardPile five, 
			CardPile six, 
			CardPile seven, 
			CardPile eight){
		
		if(cp < 1 || cp > 8) System.out.println("Please enter a cardpile between 1 and 8"); 
		
		if(cp == 1) return one;
		
		else if(cp == 2)	return two;
		else if(cp == 3) return three;		
		else if(cp == 4) return four;		
		else if(cp == 5)	return five;		
		else if(cp == 6)	return six;
		else if(cp == 7) return seven;		
		else return eight;
	}


}
