package Proj1;

/*
 * 
 * This class serves as the container for the card. The data members are the respective properties that you would find on a card.
 * Functions contained:
 * 
 * - card constructor
 * - getters and setters
 * 
 */
public class Card{
	
	private char color;
	private char suit;
	private String rank_and_suit;
	private int cardNum;
		
		//constructs card based on given rank and suit 
		public Card(int num, char suit){
		
		this.cardNum = num;	
		this.suit = suit;
		
		if(suit == 'S' || suit == 'C') this.color = 'B';
		else this.color = 'R';
		
		switch (cardNum){
			case 1: rank_and_suit = new StringBuilder().append("A").append(suit).toString();
				break;
			case 10: rank_and_suit = new StringBuilder().append("T").append(suit).toString();
				break;
			case 11: rank_and_suit = new StringBuilder().append("J").append(suit).toString();
				break;
			case 12: rank_and_suit = new StringBuilder().append("Q").append(suit).toString();
				break;
			case 13: rank_and_suit = new StringBuilder().append("K").append(suit).toString();
				break;
			default:
				rank_and_suit = new StringBuilder().append(cardNum).append(suit).toString();
				break;
		}
		
	}
		
		int getCardNum()
		{
			return this.cardNum;
		}
		
		char getColor()
		{
			return this.color;
		}
		
		char getSuit()
		{
			return this.suit;
		}
		
		String getRankAndSuit()
		{
			return this.rank_and_suit;
		}

		
/*		char getRank(){
			
			char rank = 'a';
			
			int rand = 1 + (int)(Math.random()*13);
			//System.out.print(rand);
			
			switch(rand){
				case 1: rank = 'A';
					break;
				case 2: rank = '2';
					break;
				case 3: rank = '3';
					break;
				case 4: rank = '4';
					break;
				case 5: rank = '5';
					break;
				case 6: rank = '6';
					break;
				case 7: rank = '7';
					break;
				case 8: rank = '8';
					break;
				case 9: rank = '9';
					break;
				case 10: rank = 'T';
					break;
				case 11: rank = 'J';
					break;
				case 12: rank = 'Q';
					break;
				case 13: rank = 'K';
					break;
			}
			return rank;
		}
		
		char getSuit(){
			
			char suit = 'a';
			int rand = 1 + (int)(Math.random()*4);
			//System.out.print(rand);
			
			switch(rand){
				case 1: suit = 'C';
					break;
				case 2: suit = 'D';
					break;
				case 3: suit = 'H';
					break;
				case 4: suit = 'S';
					break;
			}
			return suit;
		}
		
*/
}