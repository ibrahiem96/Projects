package Proj1;

/*
 * 
 * Situationally Modified Linked List Stack class to serve as a data structure that holds the cards
 * 
 * - Both piles and decks can utilize this DS
 * 
 */
public class LLStack
	{
		node head;
		
		public class node
		{
			node next;
			Card c;
		
			public node(Card c){
				this.c = c;
				next = null;
			}
		}
		
		public LLStack(){
			head = null;
		}
		
		boolean isEmpty()
		{
			if(head == null)return true;
			return false;
		}
		
		void push(Card c)
		{
			node card = new node(c);
			card.next = head;
			head = card;
		}
		
		void pop()
		{
			if(head != null) head = head.next;
		}

		Card getTop()
		{
			if(!isEmpty()) return head.c;
			return null;
		}
		
		int getTopNum()
		{
			if(!isEmpty())return head.c.getCardNum();
			return 0;
		}
		
		//searches through deck linked list
		//returns true if card exists
		//else, false
		boolean doesExist(String rs){
			node card = head;
			
			while(card != null){
				if(rs.equals(card.c.getRankAndSuit()))return true;
				card = card.next;
			}
			return false;
		}
		
		//searches through deck linked list by card rank and suit
		//returns specific card
		Card getCardFromLList_byRS(String rs){
			node deck_ptr = head;
			
			while(deck_ptr != null){
				if(rs.equals(deck_ptr.c.getRankAndSuit())) return deck_ptr.c;
				deck_ptr = deck_ptr.next;
			}
			return null;
		}
		
		//searches through deck linked list
		//returns true if cardNum exists
		//else, false
		boolean doesCardNumExist(int num){
			
			node deck_ptr = head;
			
			while(deck_ptr != null){
				if(num == deck_ptr.c.getCardNum())return true;
				deck_ptr = deck_ptr.next;
			}
			return false;
		}
		
		//searches through deck linked list by card number
		//returns specific card
		Card getCardFromLList_byCardNum(int num){
			node deck_ptr = head;
			
			while(deck_ptr != null){
				if(num == deck_ptr.c.getCardNum()) return deck_ptr.c;
				deck_ptr = deck_ptr.next;
			}
			return null;
		}
		
		//need to understand what this does
		void remove_empty_node(Card c){
			node deck_ptr = head; node deck_ptr2 = null;
			
			if(!isEmpty()){
				
				if(deck_ptr.c == c)
					head = head.next;
				
				while(deck_ptr.next != null){
				
					if(deck_ptr.next.c == c){
						deck_ptr2 = deck_ptr.next.next;
						deck_ptr.next = deck_ptr2;	
						return;
					}
				
				deck_ptr = deck_ptr.next;
				
				}
			}
		}
		
		
	}
	