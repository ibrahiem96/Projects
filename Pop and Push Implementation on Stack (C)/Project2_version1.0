#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/*Assignment: Project 2
  Name: Ibrahiem Mohammad
  net-ID: imoham3
  Due Date: 9/20/15
  Version 0.0
*/

/*
  Version: 1.0
  Date: 9/22/18
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
		return 0;
	}
	
	return 1;

}

void stk_grow(stack* st){

// 	printf("\nDEBUG: in grow(): size: %d, top = %d", st->size, st->top);		
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

	if (stk_check_if_empty(st)==0){ 
// 		abort();
	}
	
// 	st->darr[st->top] = st->darr[st->top--];
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

int main (int argc, char** argv)
{
  
  stack st1; //instance of stack
	
	stk_init(&st1); //initialize stack
	
// 	printf("\nTop of stack value: %d", st1.top);

	printf("\nEnter a series of symbols from the following list, less than 300 characters long: { [ ( < > ) ] }. In order to be balanced, each opening symbol must have a closing symbol (order matters). This program will tell you whether or not the symbols you entered are balanced.\nEnter q or Q to exit.\n");
	
 char input[301];

 /* set up an infinite loop */
 while (1)
 {
   stk_print(&st1);
   stk_reset(&st1);
   stk_init(&st1);
   /* get line of input from standard input */
   printf ("\nEnter input to check or q to quit\n");
   fgets(input, 300, stdin);

   /* remove the newline character from the input */
   int i = 0;
   while (input[i] != '\n' && input[i] != '\0')
   {
      i++;
   }
   input[i] = '\0';

   /* check if user enter q or Q to quit program */
   if ( (strcmp (input, "q") == 0) || (strcmp (input, "Q") == 0) )
     break;
 
   
   printf ("%s\n", input);

   /* run the algorithm to determine is input is balanced */
   int curly_ctr = 0;
   int square_ctr= 0;
   int angle_ctr = 0;
   int paran_ctr = 0;
   
   char error_symbol = '0';
   char error_open_symbol = '0';
   char error_open_symbol_second = '0';
   int error_counter = 0;
   
   int input_counter; int input_counter_outer = 0;
   for (input_counter = 0; input_counter < sizeof(input)/sizeof(char); input_counter++){
     
		if (input[input_counter]=='{'||input[input_counter]=='['||input[input_counter]=='('||input[input_counter]=='<'){
      if (input[input_counter] == '{') curly_ctr++;      
      else if (input[input_counter] == '[') square_ctr++;
      else if (input[input_counter] == '(') paran_ctr++;
      else if (input[input_counter] == '<') angle_ctr++;
			stk_push(&st1, input[input_counter]);
//       printf("\nPushed %c onto stack", input[input_counter]);
//       printf("\ncurly counter: %d\nsquare counter: %d\nparantheses counter: %d\nangle counter: %d\n", curly_ctr, square_ctr, paran_ctr, angle_ctr);
		}
    
    else if (input[input_counter]=='}'||input[input_counter]==']'||input[input_counter]==')'||input[input_counter]=='>'){
// 			stk_push(&st1, symb_array[i]);
//       printf("\nClosing symbol: %c", input[input_counter]);
      if (input[input_counter]=='}'&& curly_ctr > 0 && stk_get_top(&st1) == '{'){
        stk_pop(&st1);
        curly_ctr--;
//         printf("\nPopped %c", input[input_counter]);
      }
      else if (input[input_counter]==']'&& square_ctr > 0 && stk_get_top(&st1) == '['){
        stk_pop(&st1);
        square_ctr--;
//         printf("\nPopped %c", input[input_counter]);
      }
      else if (input[input_counter]==')'&& paran_ctr > 0 && stk_get_top(&st1) == '('){
        stk_pop(&st1);
        paran_ctr--;
//         printf("\nPopped %c", input[input_counter]);
      }
      else if (input[input_counter]=='>'&& angle_ctr > 0 && stk_get_top(&st1) == '<'){
        stk_pop(&st1);
        angle_ctr--;
//         printf("\nPopped %c", input[input_counter]);
      }
      else {
        error_symbol = input[input_counter];
        error_counter = input_counter;
        break;
      } 
      
		}
    else if (input[input_counter] == '\0' || input[input_counter] == '\n') {
      break;
    }
    else {
      continue;
    }
     
    input_counter_outer++; 
   }
   
   printf("\ncurly counter: %d\nsquare counter: %d\nparantheses counter: %d\nangle counter: %d\n", curly_ctr, square_ctr, paran_ctr, angle_ctr);
   printf("\ntest top: %d", st1.top);
   
   if (curly_ctr == 0 && square_ctr == 0 && paran_ctr == 0 && angle_ctr == 0) {
     if (st1.top < 0){
       printf("\nStack is empty");
     }
     else {
       printf ("\n%s", input);
       printf("\nString is balanced");  
     }
     
   }
   
   else {
     printf("\nString is unbalanced");
     
//      switch (error_symbol) {
//        case '}':
//          error_open_symbol ='{';
//          break;
//        case ')':
//          error_open_symbol ='(';
//          break;
//        case ']':
//          error_open_symbol ='[';
//          break;
//        case '>':
//          error_open_symbol ='<';
//          break;
//      }
     
     switch (stk_get_top(&st1)) {
       case '{':
         error_open_symbol_second ='}';
         break;
       case '(':
         error_open_symbol_second =')';
         break;
       case '[':
         error_open_symbol_second =']';
         break;
       case '<':
         error_open_symbol_second ='>';
         break;
     }
     
     if (error_symbol == '0'){
       
       printf ("\n%s", input);
       printf ("\n%*c%c %c expected - no closing symbol", input_counter_outer, ' ', '^', error_open_symbol_second);
     
     }
     
     else {

//        printf("\nError occurred at %c", error_symbol);
//        printf("\nError occurred at position %d", error_counter);
       
//        printf("\nError occurred at %c", error_symbol);
       
       printf ("\n%s", input);
       printf ("\n%*c%c %c expected - incorrect closing", error_counter, ' ', '^', error_open_symbol_second);
     
     }
     
   }

  }
   
   printf ("\nGoodbye\n");
   return 0;
  
}


int temp_main(int argc, char** argv){


	stack st1; //instance of stack
	
	stk_init(&st1); //initialize stack
	
	printf("\nTop of stack value: %d", st1.top);

	printf("\nEnter a series of symbols from the following list, less than 300 characters long: { [ ( < > ) ] }. In order to be balanced, each opening symbol must have a closing symbol (order matters). This program will tell you whether or not the symbols you entered are balanced.\nEnter q or Q to exit.\n");
	
	char symb_array[301];

	fgets(symb_array, 300, stdin);
  
//   int j = 0;
//   while (symb_array[j] != '\n' && symb_array[j] != '\0')
//   {
//     j++;
//   }
//   symb_array[j] = '\0';
  	
	int i;	
	for (i = 0; i < sizeof(symb_array); i++){
		if (symb_array[i]=='q'||symb_array[i]=='Q') exit(1);

		if (symb_array[i]=='{'||symb_array[i]=='['||symb_array[i]=='('||symb_array[i]=='<'){
			stk_push(&st1, symb_array[i]);
      printf("\nPushed %c onto stack", symb_array[i]);
		}
    
    else if (symb_array[i]=='}'||symb_array[i]==']'||symb_array[i]==')'||symb_array[i]=='>'){
// 			stk_push(&st1, symb_array[i]);
      printf("\nClosing symbol: %c", symb_array[i]);
		}
    
    else {
      break;
    }
// 		else if (symb_array[i]=='}'){
// 			if(stk_check_if_empty(&st1)==1){
// 				printf("\nString is unbalanced");
// 				printf("\n%s", symb_array);
// 				return 0;
// 			}
// 			else if (st1.darr[st1.top]==symb_array[i]){
// 				stk_pop(&st1);
// 				if (symb_array[i+1]=='\0'){
// 					printf("\n%s", symb_array);
// 				}
// 			}
// 		}
// 		else if (symb_array[i]==']'){
// 			if(stk_check_if_empty(&st1)==1){
// 				printf("\nString is unbalanced");
// 				printf("\n%s", symb_array);
// 				return 0;
// 			}
// 			else if (st1.darr[st1.top]==symb_array[i]){
// 				stk_pop(&st1);
// 				if (symb_array[i+1]=='\0'){
// 					printf("\n%s", symb_array);
// 				}
			
// 			}

// 		}

// 		else if (symb_array[i]==')'){
// 			if(stk_check_if_empty(&st1)==1){
// 				printf("\nString is unbalanced");
// 				printf("\n%s", symb_array);
// 				return 0;
// 			}
// 			else if (st1.darr[st1.top]==symb_array[i]){
// 				stk_pop(&st1);
// 				if (symb_array[i+1]=='\0'){
// 					printf("\n%s", symb_array);
// 				}
// 			}	
			
// 		}

// 		else if (symb_array[i]=='>'){
// 			if(stk_check_if_empty(&st1)==1){
// 				printf("\nString is unbalanced");
// 				printf("\n%s", symb_array);
// 				return 0;
// 			}
// 			else if (st1.darr[st1.top]==symb_array[i]){
// 				stk_pop(&st1);
// 				if (symb_array[i+1]=='\0'){
// 					printf("\n%s", symb_array);
// 				}
// 			}
// 		}	
	}

	stk_print(&st1);

}

