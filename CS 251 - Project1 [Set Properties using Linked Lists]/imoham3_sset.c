#include "sset.h"
#include "stdlib.h"
#include "stdio.h"

#define DEBUG

struct sset_struct{
  int val;
  struct sset_struct *next;
};


/**
* Function:  sset_create
* Status:  DONE
* Returns an empty set
*/
SSET *sset_create(){
  return NULL;
}

/**
* Function:  sset_singleton
* Status:  DONE
* Returns the set {x}
*/
SSET *sset_singleton(int x) {
SSET *p;

  p = malloc(sizeof(SSET));
  p->val = x;
  p->next = NULL;
  return p;
}

/**
*   Used for the sset_from_array function
*/
static int int_cmp(const void *a, const void *b) {

  int *pa = (int *)a;
  int *pb = (int *)b;

  return (*pa - *pb);
}

/**
* Function:  from_sorted_array (helper)
*
* Status:  DONE
* Parameters:
*  
*    a:  the base address of an array of integers
*    n:  the length of array a.
*
* Returns:
*    A sorted set populated with the n elements in the
*    given array.
*
* Precondition:  array a[] is assumed to be sorted in
*      ascending order and have no duplicates.
*
* Requirement:  linear time
* Recommendation:  recursion
*/
static SSET *from_sorted_array(int *a, int n) {

	SSET *sorted_list;

	sorted_list =malloc (sizeof(SSET) * n);

	int i;
	for (i = 0; i < (n-1); i++){
		sorted_list[i].next = &sorted_list[i+1];
	}
	for (i = 0; i < n; i++){
		sorted_list[i].val = a[i];
	}
	return sorted_list;
}

/**
* Function:  print_arr (helper)
*
* Description:  helper function for debugging.
*/
static void print_arr(int *a, int n) {
int i;

  printf("[ ");
  for(i=0; i<n; i++) 
     printf("%i ", a[i]);
  printf("]\n");
}

/**
* Fuction:  sset_from_array
*
* Status:  DONE (but subroutine from_sorted_array needs
*   implementation)
*
* Parameters:
*
*   a:  base address of an array of integers
*   n:  array length
*
* Returns the SSET * representation of the elements.
*
* Notes:  given array not necessarily sorted and
*  may have duplicates.
*/
SSET *sset_from_array(int *a, int n) {
int *b, *c;
int i, j, x;
SSET *s;

  b = malloc(n*sizeof(int));
  c = malloc(n*sizeof(int));
  
  for(i=0; i<n; i++)
    b[i] = a[i];
  qsort(b, n, sizeof(int), int_cmp);

  i=0; j=0;

  // copy elements w/o duplicates from b[] to c[] (retaining
  //   sorted order
  while(i < n) {
    x = b[i];
    c[j] = x;
    i++; j++;

    while(i < n && b[i] == x)
	i++;
  }
#ifdef DEBUG
  printf("---start debug---\n");
  printf("sset_from_array:\n");
  printf("       a: ");
  print_arr(a, n);
  printf("       b: ");
  print_arr(b, n);
  printf("       c: ");
  print_arr(c, j);
  printf("---end debug---\n");
#endif

  s = from_sorted_array(c, j);
  free(b);
  free(c);
  return s;
}
  
  
/**
* Function:  sset_free
* Status:   DONE
*
* Description:  de-allocates all memory associated with the
*   parameter set.
*
* Requirements:  linear time
*                recursive
*/
void sset_free(SSET *s) {

	if (s->next){
		sset_free(s->next);
	}
	free(s);
}

/**
* Function:  sset_isempty
* Status:  DONE
* 
* Description:  self-evident
*/
int sset_isempty(SSET *s) {
	return s==NULL;
}

/**
* Function:  sset_size
* Status: DONE
*
* Description:  returns the cardinality of the
*   given set.
*/
int sset_size(SSET *s) {
  if(s == NULL) return 0;
  return 1 + sset_size(s->next);
}

/**
* Function:  sset_display
* Status:  DONE
*
* Description:  prints the contents of the set s in 
*   curly-brace style.
*/
void sset_display(SSET *s) {
  printf("  { ");
  while(s != NULL) {
	printf("%i ", s->val);
	s = s->next;
  }
  printf("}\n  ");
}


/**
* Function  sset_contains
* Status: DONE
*
* Description:  Returns 0 or 1 accordingly
*
* Requirement:  linear time
* Recommendation:  recursion
*/
int sset_contains(SSET *s, int x) {

	int i;

	s = malloc(sizeof(SSET));


	while(s->val != '\0'){
		if(s->val == x){
			i++;	
		}

		s = s->next;
	}

	if(i != 1){
		return 1;
	}
	else {
		return 0;
	}


}

/**
* Function:  sset_clone
* Status:  DONE
*
* Description:  creates and returns a clone of the set s.  
*   Afterwards, there are two distinct sets which happen
*   to have the same contents.
*/
SSET *sset_clone(SSET *s) {
  SSET *p;
  if(s == NULL)
	return NULL;
  p = malloc(sizeof(struct sset_struct));
  p->val = s->val;
  p->next = sset_clone(s->next);
  return p;
}

/**
* Function:  sset_union
* Status:  DONE
*
* Description:
*   
*   self evident (?)
*/
SSET *sset_union(SSET *a, SSET *b) {
SSET *p;

  if(a == NULL) 
	return sset_clone(b);
  if(b == NULL) 
	return sset_clone(a);
  p = malloc(sizeof(SSET));
  if(a->val < b->val){
	p->val = a->val;
	p->next = sset_union(a->next, b);
  }
  else if(a->val > b->val){
	p->val = b->val;
	p->next = sset_union(a, b->next);
  }
  else {
	p->val = a->val;
	p->next = sset_union(a->next, b->next);
  }
  return p;
}

/**
* Function:  sset_intersection
* Status:  TODO
*
* Description:  returns a new sorted set which is the 
*   intersection of the given sets a and b.
*
* Requirements:  linear time
*                recursive
*/
SSET *sset_intersection(SSET *a, SSET *b) {

  SSET *c;

  if(a == NULL) 
    return NULL;
  if(b == NULL) 
    return NULL;
    
  c = malloc(sizeof(SSET));
  if(a->val < b->val){
    c = sset_intersection(a->next, b); 
  }
  else if(a->val > b->val){
    c = sset_intersection(a, b->next);
  }
  else {
    c->val = a->val;
    c->next = sset_intersection(a->next, b->next);
  }
  return c;
}


/**
* Function:  sset_diff
* Status:  TODO
* 
* Description:  constructs and returns the difference 
*    of set a with set b.
*    Recall that  A - B = {x in A s.t. x NOT-IN B}
*
*    Example:  {3, 7, 11, 14} - {3, 5, 11, 20} = {7, 14}
*
* Requirements:  linear time
*                recursive
*
*/
SSET *sset_diff(SSET *a, SSET *b){
  SSET *c;

  if(a == NULL) 
    return NULL;
  if(b == NULL) 
    return sset_clone(a);

  c = malloc(sizeof(SSET));
  if(a->val < b->val){
    c->val = a->val;
    c->next = sset_diff(a->next, b);
  }
  else if(a->val > b->val){
    c = sset_diff(a, b->next);
  }
  else {
    c = sset_diff(a->next, b->next);
  }
  return c;
}

/**
* Function  sset_toarray
* Status:  TODO
* Description:  allocates and returns an array containing the 
*   elements of the given set.
*
* Requirement:  linear time
*/
int *sset_toarray(SSET *a) {
  
	int *array;
	int i=0;
	while(a->val != '\0'){
		array[i] = a->val;
		a = a->next;
		i++;
	}
  
  	return array;
}

/**
* Function:  sset_cmp
* Status:  TODO
*
* Compares sets a and b lexicographically (essentially like alphabetical).
* Sets a and b are given via void pointers instead of SSET pointers so the
* function can be utilized by qsort
*
*    If "a < b", a negative number is returned.
*    if "a > b", a positive number is returned.
*    if a and b are identical, zero is returned.
*
*    Lex rules:
*       The empty set precedes all other sets
*       For non-empty A={a0, ...}, B={b0, ...}, compare a0 and b0
*
*           - if a0 != b0 set with smallest first elem "wins"
*           - otherwise, compare A-{a0} with B-{b0}
*            
*     Example:
*              A = {2, 3, 5, 8, 10, 11}
*              B = {2, 3, 5, 7, 1000, 2000, 30000}
*
*             we have ties on frst 3 elements; then 7<8 so B < A.
*
*     Example:
*              A = {2, 3, 5}
*              B = {2, 3, 5, 7, 1000 }
*
*             Again, ties on first three elements; then we are comparing {} with
*             {7, 1000} so A < B
*
*  Note:  you will use this function to complete the sset_sort_sets
*    function (which will use qsort).
*          
* Requirements:  linear time
*                recursive
*/
int sset_cmp(const void *a, const void *b){

	SSET* p = (SSET*)a;
	SSET* q = (SSET*)b;

	if (p->val == q->val){
		if(p->next != NULL && q->next != NULL){
			return sset_cmp(p->next, q->next);
		}
		else{
			return -1;
		}
	}
	else{
		return (p->val) - (q->val);
	}
}


/**
* Function:  sset_sort_sets
* Status:  TODO
* Description:  Takes an array of sets (array element type:  
*   SSET *) and sorts  the sets according to the lexicographic 
*   rules of the  sset_cmp function.
*
* This should be a very short function!!  It really just act as 
*   a "wrapper" function which calls the qsort library routine.
*
* Hint:  you need to get sset_cmp working first
*/
void sset_sort_sets(SSET * sets[], int n) {
	qsort(sets, n, sizeof(SSET), sset_cmp);
}
  
int main() {

  SSET *s;
  SSET *s2;

  int a[] = {2, 8, 1, 3, 1, 8, 0, 4};
  int b[] = {1, 7, 4, 8, 3, 2, 0, 1};
  s = sset_from_array(a, 8);
  s2 = sset_from_array(b, 6);
 
  SSET* p;
  p = sset_intersection(s, s2);
  sset_display(p);

  SSET* q;
  q = sset_diff(s, s2);
  sset_display(q);

  int result;
  result = sset_cmp(s, s2);

  printf("\n%d", result);

  SSET* array_sets[] = {s, s2};

  sset_sort_sets(array_sets, 2); 

}
