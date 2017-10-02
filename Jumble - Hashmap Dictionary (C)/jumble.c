/*Name: Ibrahiem Mohammad
  NETID: imoham3
*/


#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <ctype.h>

#include "hmap.h"


typedef struct node{
  char *word; 
  char *key; 
  struct node *next; 
}NODE;  


NODE *init(char* user_word, char* user_key)
{
  NODE* new_node = malloc(sizeof(NODE));

  new_node->word = user_word; 
  new_node->key = user_key; 
  new_node->next = NULL;
  return new_node;
}


void print( NODE* new_node)
{
  while(new_node != NULL)
  {
    printf("%s\n", new_node->word); 
    new_node = new_node->next; 
  }
}

void make_lower(char string[]){
  
  int i;
  for (i = 0; i < strlen(string); i++){
    tolower(string[i]);
  }
}  

void sort(char string[]){
   
  int i;
  int length = strlen(string);
  int count = 0;

  char *sorted = (char*)malloc(length+1);
  char *temp = string;
  char letter;

  for ( letter = 'a' ; letter <= 'z' ; letter++ ){
      for ( i = 0 ; i < length ; i++ ) {
         if ( *temp == letter ){
            *(sorted+count) = *temp;
            count++;
         }
         
         temp++;
      }
      
      temp = string;
   }
  
 *(sorted+count) = '\0';

 strcpy(string, sorted);
 free(sorted);
}



int main( int argc, char* argv[]){
  char user_word[128];

  HMAP_PTR map = hmap_create(0,1);

  if(argc == 2){

    printf("\nOPENING DICT FROM FILE\n");
    
    FILE *file;
    file = fopen(argv[1], "r");
    
    if(file == 0){
      printf("\nerror in reading filename\n");
      return 0;
     }
     else { 

      while(fgets(user_word,128, file) != NULL){

        char *sorted_word;
      	char *user_key;
         
        int n = strlen(user_word);
      	
        sorted_word = malloc(sizeof(char)*n);
      	
        strcpy(sorted_word, user_word);  
        make_lower(user_word);
        sort(user_word);
       
        user_key = malloc(sizeof(char)*n);
      	
        strcpy(user_key, user_word);  
        printf("Sorted String: %s\n", sorted_word);
        printf("Key: %s\n", user_key);  
        
        NODE *new = init(sorted_word, user_key);         
        
        if(!(hmap_contains(map, new->key))){ 
        
          hmap_set(map, new->key, new);
        }          
        else{
          
          NODE* temp = hmap_get(map, new->key);
          NODE* head = temp; 
          
          while(temp->next != NULL){
            temp = temp->next;  
          } 
          
          temp->next = new; 
          hmap_set(map, new->key, head);
        }
       }
     }
   
     printf("\nCOMMAND sorted_word INPUT\n");
     
     char string[100];
     
     while(1){
      printf("Enter word to unscrambled OR . to end program: ");
      scanf("%s", string);
      
      if(strcmp(string, ".") != 0){
        make_lower(string);
	      sort(string);
      
	    
        printf("sorted:%s\n", string); 
              
        if(hmap_contains(map, string) == 1){

            NODE *new_node = hmap_get(map, string);
            print(new_node);
        }


        else{
          printf("\nNO MATCHES FOUND\n"); 
        }
      
     }

     else{

      printf("\nPROGRAM TERMINATED\n");
      hmap_display(map);
      hmap_print_stats(map); 
      return 0;
     }
	    
   }
  
    return 0; 
 
 }
}
