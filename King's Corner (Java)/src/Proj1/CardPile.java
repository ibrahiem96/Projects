package Proj1;
import Proj1.LLStack.node;

/*
 * 
 * This class initializes a card pile as a linked list and contains the appropriate functions to:
 * - default constructor for card pile
 * - print out the cards contained in the card pile linked list
 */
public class CardPile /*extends LLStack*/{
	
	
	int pNum;
	LLStack cp;
	int size;
	
	//constructor
	public CardPile(int num){
		this.pNum = num;
		this.size = 0;
		this.cp = new LLStack();
	}
	
	//print out cards which are contained in the pile
	void print_cp() throws InterruptedException{
		node temp = cp.head;
		System.out.print(pNum + ": ");
		
		if(temp == null) System.out.print(" ");
		
		while(temp != null){
			System.out.print(temp.c.getRankAndSuit() + "  ");
			temp = temp.next;
		}
		System.out.println();
	}
	
}