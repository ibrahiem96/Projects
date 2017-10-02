package Proj1;

/*
 * 
 * This class is used primarily for the AI's algorithm--to play the card game. It also contains the appropriate functions to:
 * - constructor for AI
 * - getters and setters
 * - push function to draw cards to AI's hand
 * - push functions to draw cards to pile
 * - function to merge the designated piles
 * - check if certain card pile can take cards
 * - print out the cards in AI's hand
 * - check if two given piles can be merged
 * - get largest card available in player deck (for piling)
 * - calculate score
 * 
 * - A lot of the functions above are extended from the player class. This use of inheritance makes it easier
 * and reduces the amount of repetition in the code.
 * 
 */
public class AI extends PlayerHand{

	private int pileSize = 8;
	
	public AI(String rs) {
		super(rs);
	}
	
	//AI algorithm
	public void AI_game(LLStack deck, 
									CardPile one, 
									CardPile two, 
									CardPile three, 
									CardPile four,
									CardPile five, 
									CardPile six, 
									CardPile seven, 
									CardPile eight){
								
		node AI_deck_ptr = deck_playerHand.head;
		
		CardPile [] pile = new CardPile[pileSize];
	
		pile[0] = one;
		pile[1] = two;
		pile[2] = three;
		pile[3] = four;
		pile[4] = five;
		pile[5] = six;
		pile[6] = seven;
		pile[7] = eight;
		
		while(AI_deck_ptr != null){
			
			if(deck_playerHand.doesCardNumExist(13) == true){
				Card c = deck_playerHand.getCardFromLList_byCardNum(13);
			
				for(int i = 4; i < 8; i++){
					
					if(pile[i].cp.head == null){
						pile[i].cp.push(c);
						deck_playerHand.remove_empty_node(c);
						i = 8;
					}
				}
			}
			AI_deck_ptr = AI_deck_ptr.next;
		}
			
		for(int index1 = 0; index1 < 4; index1++){
			node deck_ptr1 = pile[index1].cp.head;
			CardPile cardpile_ptr = pile[index1];
			
			if(AI_deck_ptr == null)	break;
				
			while(deck_ptr1.next != null){
				deck_ptr1 = deck_ptr1.next;
			}

			if(deck_ptr1.c.getCardNum() == 13){
			
				for(int k = 5; k < 8; k++){
					
					if(pile[k].cp.head == null) merge_piles(cardpile_ptr, pile[k]);
				}
			}
		}
			
			
		for(int index2 = 0; index2 < 1; index2++){
			
			for(int index3 = 0; index3 < 8; index3++){
				
				for(int index4 = 0; index4 < 8; index4++){
					
					if(doesMerge(pile[index3], pile[index4]) == true) merge_piles(pile[index3], pile[index4]);
				}
			}
		
			
			node deck_ptr2 = deck_playerHand.head;
	
			while(deck_ptr2 != null){
				
				for(int index5 = 0; index5 < 8; index5++){
					
					if(isPileable(deck_ptr2.c, pile[index5]) == true){
						
						draw_to_pile_i(deck_ptr2.c, pile[index5]);
						index2 = 0;
						break;
					}
				}
				deck_ptr2 = deck_ptr2.next;
			}
			
			if(index2 == 0) break;
				
			for(int d = 0; d < 4; d++){
				
				if(pile[d].cp.isEmpty()){
					draw_to_pile_i(getLargestCard(), pile[d]);
					index2 = 0;
					break;
				}
			}
		}
		
		if(deck_playerHand.head != null) draw_to_hand(deck);
	
		System.out.println("END TURN: AI");
		return;
	}
	
		//checks if piles are mergeable
	//returns true if yes
	//no if no
	 boolean doesMerge(CardPile one, CardPile two){
		node pile_ptr = one.cp.head;
		
		if(pile_ptr == null) return false;
		
		while(pile_ptr.next != null){
			pile_ptr = pile_ptr.next;
		}
		
		if(pile_ptr.c.getCardNum() != two.cp.getTopNum() - 1 || pile_ptr.c.getColor() == two.cp.head.c.getColor()) return false;
		return true;
		
	}
	
	
}
	
