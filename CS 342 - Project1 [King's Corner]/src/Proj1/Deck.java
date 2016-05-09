package Proj1;
import java.util.Random;
import Proj1.LLStack;

import Proj1.Card;


/*
 * 
 * Class to initialize the card deck. Contains the appropriate functions to:
 *  - init deck
 *  - shuffle deck
 *  - check deck for duplicates
 */
public class Deck{
	
	int size = 52;
	
	public Deck(LLStack deck, 
			CardPile one, 
			CardPile two, 
			CardPile three, 
			CardPile four,
			CardPile five, 
			CardPile six, 
			CardPile seven, 
			CardPile eight, 
			PlayerHand player, AI computer, int flipCoin)
		{
	
		
		init_deck(deck);
		
		//push first card pile
		one.cp.push(deck.getTop());
		one.size++;
		deck.pop();
		
		//push second card pile
		two.cp.push(deck.getTop());
		two.size++;
		deck.pop();
		
		//push third card pile
		three.cp.push(deck.getTop());
		three.size++;
		deck.pop();
		
		//push fourth card pile
		four.cp.push(deck.getTop());
		four.size++;
		deck.pop();
		
		//"flip" coin to decide who deals
		if(flipCoin % 2 == 0){
			for(int i = 0; i < 7; i++){              
				player.draw_to_hand(deck);
				computer.draw_to_hand(deck);
			}
		}

		else if(flipCoin % 2 != 0){
			computer.draw_to_hand(deck);
			player.draw_to_hand(deck);
		}
		
		}
	
	
	
	//initializes empty deck
	public void init_deck(LLStack deck) {
		
		Card deck_arr[] = new Card[size];
		
		for(int i = 0; i < 13; i++){
			deck_arr[i] = new Card(i+1, 'S');
		}
		
		for(int i = 0; i < 13; i++){
			deck_arr[i+13] = new Card(i+1, 'C');
		}
		
		for(int i = 0; i < 13; i++){
			deck_arr[i+26] = new Card(i+1, 'H');
		}
		
		for(int i = 0; i < 13; i++){
			deck_arr[i+39] = new Card(i+1, 'D');
		}
		
		init_shuffled_deck(deck_arr);
		
		for(int i = 0; i < 52; i++){
			deck.push(deck_arr[i]);
		}
	}
	
	//initialize a new deck that has been shuffled randomly
	public void init_shuffled_deck(Card[] deck_arr){
		Random r = new Random();
		int r1; int r2; Card placeHolder; int deck_capacity = 0;
		
		while(deck_capacity != size){
			r1 = r.nextInt(size);
		    r2 = r.nextInt(size);
		    
		    //use swapping to ensure pure randomness
		    placeHolder = deck_arr[r1];
		    deck_arr[r1] = deck_arr[r2];
		    deck_arr[r2] = placeHolder;
		
			deck_capacity++;
		}
	}
	
	//check if duplicate card exists in deck
	//return true if yes
	//else, no
	public boolean isDuplicate(Card[] deck_arr){
		
		for(int i = 0; i < size; i++){
			for(int j = i + 1; j < size; j++){
				if(deck_arr[i] == deck_arr[j]) return true;
			}
		}
		return false;
	}
	
	//can't test this until i push in the cards
		/*void printCards(LLStack deck){
			
			if (deck.isEmpty()) return;
			
			while (deck.head != deck.head.next){
				System.out.println("Card Number: " + deck.head.c.cardNum + " " + deck.head.c.rank_and_suit);
				deck.head = deck.head.next;
			}
		}*/
		
		/*boolean isDuplicate (String rsc){
			
			if (deck.isEmpty()){
				System.out.println("Deck Empty");
				return true;
			}
			
			while (deck.head != deck.head.next){
				if (deck.head.c.rank_and_suit.equals(rsc)) return true;
				deck.head = deck.head.next;
			}
			return false;
		}*/
}
	
	
	
	
