#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/*Assignment: Project 2
  Name: Ibrahiem Mohammad
  net-ID: imoham3
  Due Date: 9/20/15

  Version 0.0
*/

typedef struct stack_struct
{
	char* darr;
	int size; //size of array
	int top; //specifies top of stack
} stack;

void stk_init(stack* st){

	st->size = 2;		//allocate space for 2 elements
	st->darr = (char*) malloc (sizeof(char) * st->size);
	st->top = -1;		//set top of stack value
}

int stk_check_if_empty(stack* st){

	if (st->top == -1){
		return 1;
	}
	
	return 0;

}

void stk_grow(stack* st){

	printf("\nDEBUG: in grow(): size: %d, top = %d", st->size, st->top);		
	char* temp = (char*) malloc (sizeof(char) * (st->size + 2));
	
	int i;
	for (i = 0; i < st->size; i++){
		temp[i] = st->darr[i];
	}

	free(st->darr);
	st->darr = temp;
	st->size = st->size + 2;

}

void stk_push(stack* st, char val){
	
	if (st->top+1 >= st->size){
		stk_grow(st);
	}
	st->darr[st->top+1] = val;
	st->top = st->top + 1;
}

void stk_pop(stack* st){

	if (stk_check_if_empty(st)==1){ 
		printf("\nStack is empty");
		abort();
	}
	
	st->darr[st->top] = st->darr[st->top--];
	st->top = st->top--;
	
}

char stk_get_top(stack* st){
	
	return (st->darr[st->top]);
}

void stk_print(stack* st){

	printf("\n--------TOP OF STACK--------");

	int i;
	for (i = st->top; i >= 0; i--){
		printf("\n%c", st->darr[i]);
	}
	
	printf("\n--------BOTTOM OF STACK--------"); 
}

void stk_reset(stack* st){

	free(st->darr);
	
	st->darr = NULL;
	st->size = 0;
	st->top = -1;
}




int main(int argc, char** argv){


	stack st1; //instance of stack
	
	stk_init(&st1); //initialize stack
	
	printf("\nTop of stack value: %d", st1.top);

	printf("\nThe Command Line Arguments are: ");
	int x;
	for (x = 0; x < argc; x++){
		if(strcmp(argv[x], "-d")==0){
			debug = 1;
			printf"\nFlag -d entered:");
		}
	}

	printf("\nEnter a series of symbols from the following list, less than 300 characters long: { [ ( < > ) ] }. In order to be balanced, each opening symbol must have a closing symbol (order matters). This program will tell you whether or not the symbols you entered are balanced.\nEnter q or Q to exit.\n");
	
	char symb_array[300];

	fgets(symb_array, 300, stdin);
  	
	int i;	
	for (i = 0; i < sizeof(symb_array); i++){
		if (symb_array[i]=='q'||symb_array[i]=='Q') exit(1);

		if (symb_array[i]=='{'||symb_array[i]=='['||symb_array[i]=='('||symb_array[i]=='<'){
			stk_push(&st1, symb_array[i]);
		}
		else if (symb_array[i]=='}'){
			if(stk_check_if_empty(&st1)==1){
				printf("\nString is unbalanced");
				printf("\n%s", symb_array);
				return 0;
			}
			else if (st1.darr[st1.top]==symb_array[i]){
				stk_pop(&st1);
				if (symb_array[i+1]=='\0'){
					printf("\n%s", symb_array);
				}
			}
		}
		else if (symb_array[i]==']'){
			if(stk_check_if_empty(&st1)==1){
				printf("\nString is unbalanced");
				printf("\n%s", symb_array);
				return 0;
			}
			else if (st1.darr[st1.top]==symb_array[i]){
				stk_pop(&st1);
				if (symb_array[i+1]=='\0'){
					printf("\n%s", symb_array);
				}
			
			}

		}

		else if (symb_array[i]==')'){
			if(stk_check_if_empty(&st1)==1){
				printf("\nString is unbalanced");
				printf("\n%s", symb_array);
				return 0;
			}
			else if (st1.darr[st1.top]==symb_array[i]){
				stk_pop(&st1);
				if (symb_array[i+1]=='\0'){
					printf("\n%s", symb_array);
				}
			}	
			
		}

		else if (symb_array[i]=='>'){
			if(stk_check_if_empty(&st1)==1){
				printf("\nString is unbalanced");
				printf("\n%s", symb_array);
				return 0;
			}
			else if (st1.darr[st1.top]==symb_array[i]){
				stk_pop(&st1);
				if (symb_array[i+1]=='\0'){
					printf("\n%s", symb_array);
				}
			}
		}	
	}

	stk_print(&st1);

}
