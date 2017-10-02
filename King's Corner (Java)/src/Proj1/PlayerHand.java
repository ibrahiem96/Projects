package Proj1;


/*
 * 
 * This class is used primarily for the user's algorithm--to play the card game. It also contains the appropriate functions to:
 * - constructor for user
 * - getters and setters
 * - push function to draw cards to user's hand
 * - push functions to draw cards to pile
 * - function to merge the designated piles
 * - check if certain card pile can take cards
 * - print out the cards in user's hand
 * - check if two given piles can be merged
 * - get largest card available in player deck (for piling)
 * - calculate score
 * 
 */
public class PlayerHand extends LLStack{
	
	static boolean gameOver = false;
	static boolean roundOver = false;
	
	private String user_name;
	private int numPoints;
	LLStack deck_playerHand;
	
	//default constructor
	public PlayerHand(String un){
		this.user_name = un;
		deck_playerHand = new LLStack();
		numPoints = 0;
	}
	
	public String getUserName(){
		return this.user_name;
	}
	
	public int getScore(){
		return this.numPoints;
	}
	
	//draws from deck to user hand
	public void draw_to_hand(LLStack deck){
		deck_playerHand.push(deck.getTop());	
		deck.pop();
	}
	
	//draws from deck to designated pile
	public void draw_to_pile(String rs, CardPile cp_){
			
		if(deck_playerHand.doesExist(rs) == false) {
			System.out.println("ERROR: Card does not exist!"); 
			return;
		}
		
		Card c = deck_playerHand.getCardFromLList_byRS(rs);		
		
		if(isPileable(c, cp_) == false) {
			System.out.println("Cannot Add to Specified Pile");
			return;
		}
		
		if(cp_.cp.head == null && 
		   cp_.pNum > 4 && 
		   cp_.pNum < 9 && 
		   c.getCardNum() != 13) System.out.println("ERROR: Can only place K on piles 5-8");
		
		else
		{
		cp_.size++;
		cp_.cp.push(c);	
		deck_playerHand.remove_empty_node(c);		
		}
	}
	
	
	public void draw_to_pile_i(Card c, CardPile cp_) {
		if(isPileable(c, cp_) == false) {
			System.out.println("Card Rank/Suit Not Valid!");
			return;
		}
		else {
			cp_.size++;
			cp_.cp.push(c);
			deck_playerHand.remove_empty_node(c);
		}
	}
	
	
	
	//checks if given pile is able to take the given card
	//yes if true
	//no if false
	 boolean isPileable(Card c, CardPile cp_){
		
		if(cp_.cp.head == null) {
			if(cp_.pNum > 0 && cp_.pNum < 5 && c.getCardNum() == 13) return false;
			if(cp_.pNum > 4 && cp_.pNum < 9 && c.getCardNum() != 13) return false;
			return true;
		}
		
		if(c.getColor() == cp_.cp.head.c.getColor() || c.getCardNum() != cp_.cp.getTopNum() - 1) return false;
		return true;
	}
	
	//function to merge the two designated piles
	 void merge_piles(CardPile one, CardPile two){
		
		if(one.cp.head == null && two.cp.head == null){
			System.out.println("ERROR: Cannot merge empty piles");
			return;
		}
		
		if(one.pNum < 1 || one.pNum > 4) System.out.println("ERROR: Cannot move this pile onto another pile");
		
		node pile_ptr = one.cp.head;
		
		if(pile_ptr.next == null){
			
			if(pile_ptr.c.getCardNum() > two.cp.getTopNum() || 
			   pile_ptr.c.getCardNum() == Integer.MAX_VALUE || 
			   pile_ptr.c.getCardNum() != two.cp.getTopNum() - 1 || 
			   two.cp.head.c.getColor() == pile_ptr.c.getColor()){

				System.out.println("ERROR: Cannot move pile " + one.pNum + "onto " + two.pNum + " !");
				return;
			}
			
			two.cp.push(pile_ptr.c);
			one.cp.remove_empty_node(pile_ptr.c);
			one.size = 0;
			return;
		}
		
		
		while(one.cp.isEmpty() == false){
			pile_ptr = one.cp.head;
			
			if(pile_ptr.next == null){
				two.cp.push(pile_ptr.c);
				one.cp.remove_empty_node(pile_ptr.c);
			}
			
			while(pile_ptr.next != null){
				pile_ptr = pile_ptr.next;
			}
			
			if(pile_ptr.c.getCardNum() >= two.cp.getTopNum() ||
			   pile_ptr.c.getCardNum() == 0 || 
			   pile_ptr.c.getCardNum() != two.cp.getTopNum() - 1 || 
			   two.cp.head.c.getColor() == pile_ptr.c.getColor())
			{
				System.out.println("ERROR : Cannot move " + one.pNum + " onto " + two.pNum + " !");
				return;
			}
				two.cp.push(pile_ptr.c);
				one.cp.remove_empty_node(pile_ptr.c);
				one.size--;
		}
		
	}
	
	 //print cards in hand
	void print_cards_hand() throws InterruptedException {
		node deck_ptr = deck_playerHand.head;
 
		System.out.print(user_name +"'s Deck: ");
		while(deck_ptr != null) {	
			System.out.print(deck_ptr.c.getRankAndSuit() + "  ");
			deck_ptr = deck_ptr.next;
		}
		System.out.println();
	}
	
	//checks if designated piles are mergeable
	boolean doesMerge(CardPile one, CardPile two){
		node pile_ptr = one.cp.head;
		
		if(pile_ptr == null) return false;
		
		while(pile_ptr.next != null){
			pile_ptr = pile_ptr.next;
		}
		
		if(pile_ptr.c.getCardNum() != two.cp.getTopNum() - 1 ||
		   pile_ptr.c.getColor() == two.cp.head.c.getColor() || 
		   pile_ptr.c.getCardNum() == Integer.MAX_VALUE) 
			return false;
		
			return true;
	}
	
	//returns largest card in hand
	Card getLargestCard(){
		node deck_ptr = deck_playerHand.head;
		Card curr_largest = deck_playerHand.head.c;
		while(deck_ptr != null) {
			
			if(deck_ptr.c.getCardNum() > curr_largest.getCardNum()) curr_largest = deck_ptr.c;	
			deck_ptr = deck_ptr.next;
		}
		return curr_largest;
	}
	
	//calculates score
	void scoreCalc(){
		node deck_ptr = deck_playerHand.head;
		
		
		while(deck_ptr != null){
		
			if(deck_ptr.c.getCardNum() == 13) numPoints = numPoints + 10;
			else numPoints++;
			
			deck_ptr = deck_ptr.next;
		}
		
		if(numPoints > 25) {
			System.out.println(user_name + " loses");
			gameOver = true;
			return;
		}
		
		else return;
		
	}	
}