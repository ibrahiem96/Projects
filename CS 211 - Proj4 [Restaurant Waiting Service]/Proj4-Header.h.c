/**Name: Ibrahiem Mohammad
   Net-ID: imoham3
   File: Header file for Proj 4
   Date: 10/18/14
*/


#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct r_list {
	
	char *name;
	int size;
	enum status {called = 0, in_restaurant = 1};
	struct r_list *next;

} GROUP;

typedef enum {FALSE = 0, TRUE, NO = 0, YES} boolean;

void cleartoEoln();

int getNextNWSChar();

int getPosInt();

char *getName();

void printCommands();

void doAdd (GROUP *g);

void doCallAhead (GROUP *g);

void doWaiting (GROUP *g);

void doRetrieve (GROUP *g);

void doList (GROUP *g);

void doDisplay (GROUP *g);

/*adds new group to restaurant list*/
void addToList(GROUP *g, int size, char *name);

/*checks if group name is unique*/
bool doesNameExist(GROUP *g, char *name);

/*updates call-in group to in-restaurant, returns FALSE if group
is already waiting*/
bool updateSatus(GROUP *g, char *name);

/*retrieve first in-restaurant group, return name, and pop from list*/
char retrieveAndRemove(GROUP *g);

/*returns num of groups ahead of a given group*/
int countGroupsAhead(GROUP *g, char *name);

/*prints out group sizes ahead of given group*/
void displayGroupSizeAhead(GROUP *g, char *name);

/*display all group information for all groups in list*/
void displayListInformation(GROUP *g);

