/*  Assignment: Project 3 
 *  Author: Ibrahiem Mohammad
 *  Net-ID: imoham3
 *  Date: 10/3/2015

    Version 0.0
 */


#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int Debug = 5;

typedef enum {FALSE = 0, TRUE, NO = 0, YES} boolean;

typedef enum {ERROR = 0, OPERATOR, VALUE, EOLN, QUIT, HELP} tokenType;

typedef struct tokenStruct
{
 tokenType type;
 char      op;
 int       val;
} token;


typedef struct llist_stack
{
  int top;
  struct llist_stack *next;

} llstack;


void init(llstack* head){
  head = NULL;
}

int isEmpty(llstack* head){

  if (head == NULL){
    return 1;
  }
  else {
    return 0;
  }

}

void push(llstack* head, int val){
  
  llstack *temp = (struct llist_stack*)malloc(sizeof(struct llist_stack));


  if (isEmpty(temp)==1){
    exit(0);
  }

  temp->next = head;
  temp->top = val;
  if (Debug == 1){
    printf("Debugging Info: \n pushed value: %d", temp->top);
  head = temp;

}

void pop(llstack* head){

  llstack *temp = head;

  if (isEmpty(temp)==1){ 
    exit(0);
  }

  if (Debug == 1){
    printf("Debugging Info: \n popped value: %d", head->top);
  }
  temp = temp->next;
  free(head);
  head = temp;

}

int top (llstack* head){

  return head->top;

}

//operation to evaluate postfix expression
int eval(int v1, char op, int v2){

  if (op=='+'){
    if (Debug == 1){
    printf("Debugging Info: \n evaluated expression: %d", v2+v1);
    }
    return v1+v2;
  }
  else if (op=='-'){
    if (Debug == 1){
    printf("Debugging Info: \n evaluated expression: %d", v2-v1);
    }
    return v2-v1;
  }
  else if (op=='*'){
    if (Debug == 1){
    printf("Debugging Info: \n evaluated expression: %d", v2*v1);
    }
    return v1*v2;
  }
  else if (op=='/'){
    if (Debug == 1){
    printf("Debugging Info: \n evaluated expression: %d", v2/v1);
    }
    return v2/v1;
  }

  else{
    printf("\n Operator is not valid.");
    return -999;
  }
}

int popAndEval(llstack *val_stack, llstack *op_stack){

  if (isEmpty(val_stack)==1){
    printf("\nStack Empty");
    return -999;
  }

  char op = top(op_stack);
  pop(op_stack);

  int v2 = top(val_stack);
  pop(val_stack);

  int v1 = top(val_stack);
  pop(val_stack);

  int v3 = eval(v1, op, v2);
}

token getInputToken (FILE *in);

void processExpression (token inputToken, FILE *in)
{
 /**********************************************/

 printf("\nworks");

 llstack *val_stack;
 llstack *op_stack;

 init(val_stack);
 init(op_stack);

 /* Loop until the expression reaches its End */
 while (inputToken.type != EOLN)
   {
    /* The expression contains an OPERATOR */
    if (inputToken.type == OPERATOR){
       /* make this a debugMode statement */
       printf ("OP:%c, " ,inputToken.op);

      if (inputToken.op=='('){
        push(op_stack, inputToken.op);
      }
      if (inputToken.op=='+' || inputToken.op=='-'){
        while (isEmpty(op_stack)==0 && (op_stack->top=='+' || op_stack->top=='-' || op_stack->top=='*' || op_stack->top=='/')){
          popAndEval(op_stack, val_stack);
        }
        push(op_stack, inputToken.op);
      }
      if (inputToken.op=='*' || inputToken.op=='/'){
        while (isEmpty(op_stack)==0 && (op_stack->top=='*' || op_stack->top=='/')){
          popAndEval(op_stack, val_stack);
        }
        push(op_stack, inputToken.op);
      }
      if (inputToken.op==')'){
        while (isEmpty(op_stack)==0 && op_stack->top!='('){
          popAndEval(op_stack, val_stack);
        }
        if (isEmpty(op_stack)==1){
          printf("\nOperator stack empty");
        }
        else{
          pop(op_stack);
        }
      }
    }

    /* The expression contain a VALUE */
    else if (inputToken.type == VALUE)
      {
       /* make this a debugMode statement */
       printf ("Val: %d, ", inputToken.val); 

       push(val_stack, inputToken.val);
        
      }
   
    /* get next token from input */
    inputToken = getInputToken (in);
   } 

 /* The expression has reached its end */

 while (isEmpty(op_stack)==0){
  popAndEval(op_stack, val_stack);
 }

 printf("\n top val at stack: %d", val_stack->top);

 if (val_stack!=NULL){
  printf("\nNot empty");
 }

 printf ("\n");
}


/**************************************************************/
/*                                                            */
/*  The Code below this point should NOT need to be modified  */
/*  for this program.   If you feel you must modify the code  */
/*  below this point, you are probably trying to solve a      */
/*  more difficult problem that you are being asked to solve  */
/*                                                            */
/**************************************************************/

token getInputToken (FILE *in)
{
 token retToken;
 retToken.type = QUIT;

 int ch;
 ch = getc(in);
 if (ch == EOF)
   return retToken;
 while (('\n' != ch) && isspace (ch))
   {
    ch = getc(in);
    if (ch == EOF)
      return retToken;
   }
 
 /* check for a q for quit */
 if ('q' == ch)
   {
    retToken.type = QUIT;
    return retToken;
   }

 /* check for a ? for quit */
 if ('?' == ch)
   {
    retToken.type = HELP;
    return retToken;
   }

 /* check for the newline */
 if ('\n' == ch)
   {
    retToken.type = EOLN;
    return retToken;
   }

 /* check for an operator: + - * / ( ) */
 if ( ('+' == ch) || ('-' == ch) || ('*' == ch) ||
      ('/' == ch) || ('(' == ch) || (')' == ch) )
   {
    retToken.type = OPERATOR;
    retToken.op = ch;
    return retToken;
   }
   
 /* check for a number */
 if (isdigit(ch))
   {
    int value = ch - '0';
    ch = getc(in);
    while (isdigit(ch))
      {
       value = value * 10 + ch - '0';
       ch = getc(in);
      }
    ungetc (ch, in);  /* put the last read character back in input stream */
    retToken.type = VALUE;
    retToken.val = value;
    return retToken;
   }
      
 /* else token is invalid */
 retToken.type = ERROR;
 return retToken;
}

/* Clear input until next End of Line Character - \n */
void clearToEoln(FILE *in)
{
 int ch;
 
 do {
     ch = getc(in);
    }
 while ((ch != '\n') && (ch != EOF));
}

void printCommands()
{
 printf ("The commands for this program are:\n\n");
 printf ("q - to quit the program\n");
 printf ("? - to list the accepted commands\n");
 printf ("or any infix mathematical expression using operators of (), *, /, +, -\n");
}

int main (int argc, char **argv)
{
 if (argc > 1){
   if (strcmp(argv[1], "-d")==0){
     Debug = 1;
  }
 }
 char *input;
 token inputToken;

 printf ("Starting Expression Evaluation Program\n\n");
 printf ("Enter Expression: ");

 inputToken = getInputToken (stdin);
 while (inputToken.type != QUIT)
   {
    /* check first Token on Line of input */
    if(inputToken.type == HELP)
      {
       printCommands();
       clearToEoln(stdin);
      }
    else if(inputToken.type == ERROR)
      {
       printf ("Invalid Input - For a list of valid commands, type ?\n");
       clearToEoln(stdin);
      }
    else if(inputToken.type == EOLN)
      {
       printf ("Blank Line - Do Nothing\n");
       /* blank line - do nothing */
      }
    else 
      {
       processExpression(inputToken, stdin);
      } 

    printf ("\nEnter Expression: ");
    inputToken = getInputToken (stdin);
   }

 printf ("Quitting Program\n");
 return 1;
}
