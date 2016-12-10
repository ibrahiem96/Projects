#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <string.h>
#include <assert.h>
#include <stdbool.h>

struct memory_region{
  size_t * start;
  size_t * end;
};

struct memory_region global_mem;
struct memory_region heap_mem;
struct memory_region stack_mem;

void walk_region_and_mark(void* start, void* end);

//how many ptrs into the heap we have
#define INDEX_SIZE 1000
void* heapindex[INDEX_SIZE];


//grabbing the address and size of the global memory region from proc 
void init_global_range(){
  char file[100];
  char * line=NULL;
  size_t n=0;
  size_t read_bytes=0;
  size_t start, end;

  sprintf(file, "/proc/%d/maps", getpid());
  FILE * mapfile  = fopen(file, "r");
  if (mapfile==NULL){
    perror("opening maps file failed\n");
    exit(-1);
  }

  int counter=0;
  while ((read_bytes = getline(&line, &n, mapfile)) != -1) {
    if (strstr(line, "hw4")!=NULL){
      ++counter;
      if (counter==3){
        sscanf(line, "%lx-%lx", &start, &end);
        global_mem.start=(size_t*)start;
        // with a regular address space, our globals spill over into the heap
        global_mem.end=malloc(256);
        free(global_mem.end);
      }
    }
    else if (read_bytes > 0 && counter==3) {
      if(strstr(line,"heap")==NULL) {
        // with a randomized address space, our globals spill over into an unnamed segment directly following the globals
        sscanf(line, "%lx-%lx", &start, &end);
        printf("found an extra segment, ending at %zx\n",end);						
        global_mem.end=(size_t*)end;
      }
      break;
    }
  }
  fclose(mapfile);
}


//marking related operations

int is_marked(size_t* chunk) {
  return ((*chunk) & 0x2) > 0;
}

void mark(size_t* chunk) {
  (*chunk)|=0x2;
}

void clear_mark(size_t* chunk) {
  (*chunk)&=(~0x2);
}

// chunk related operations

#define chunk_size(c)  ((*((size_t*)c))& ~(size_t)3 ) 
void* next_chunk(void* c) { 
  if(chunk_size(c) == 0) {
    printf("Panic, chunk is of zero size.\n");
  }
  if((c+chunk_size(c)) < sbrk(0))
    return ((void*)c+chunk_size(c));
  else 
    return 0;
}
int in_use(void *c) { 
  return (next_chunk(c) && ((*(size_t*)next_chunk(c)) & 1));
}


// index related operations

#define IND_INTERVAL ((sbrk(0) - (void*)(heap_mem.start - 1)) / INDEX_SIZE)
void build_heap_index() {
  // TODO
}

// the actual collection code
void sweep(size_t *ptr, size_t *end) {
  
  while (ptr < end){
    if (is_marked(ptr)) clear_mark(ptr);
    else if (in_use(ptr)) free(ptr);
    
    ptr = next_chunk(ptr);
  }
  end = sbrk(0);
}



bool check_ptr(size_t * ptr) { if (ptr) return true; else return false; }

//determine if what "looks" like a pointer actually points to a block in the heap
size_t * is_pointer(size_t * ptr) {
  
//   //chunk header and end
//   size_t *header = heap_mem.start;
//   size_t *end    = heap_mem.end;
//   //check if pointer is null or is 0
//   if (ptr == NULL || check_ptr(ptr) == false) return NULL;
//   //check if it within the bounds of the heap
//   if (ptr < header || ptr >= end) return NULL;
  
//   //iterate through each chunk in memory
//   //check if address of curr chunk is less than pointer address
//   //AND if address of next chunk after it is bigger than pointer address
//   while (header < end) {
    
//     size_t *c_chunk = header - 2;
//     size_t *n_chunk = next_chunk(c_chunk) + 2;
//     if (header <= ptr && ptr < n_chunk) return c_chunk;
  
//     header = n_chunk;
//   }
//   return NULL;
  
  
size_t *start = heap_mem.start - 1 ;
size_t *end = heap_mem.end ; 
  
  size_t *pointer ; 

    
   if(start < ptr && (start + chunk_size(start)) > ptr && in_use(start)){
		
		  
  while(start < end ){
    
    if( start  > ptr  ){
       return 0 ;
    }
    if(end < ptr ){
       return 0 ; 
    }
		 
		 
		 
    //if(chunk_size(start) < chunk_size(ptr) && chunk_size(next_chunk(start)) > chunk_size(ptr)){
 	    pointer = start ;//= next_chunk(start) ;
      return pointer ;
      
    }
    start = next_chunk(start); 
  
	}

 return 0 ;
}




void walk_region_and_mark(void* start, void* end) {

  //if our start ptr has already been return  
  if (!start) return;
  if (is_marked(start)) return;
  
  //otherwise mark the first address
  mark(start);
  //size_t *ptr2m = start - 1;
  size_t *ptr2m = start + 2;
//   int size = chunk_size(start) - 1;
  
//   int i;
//   for (i = 0; i < size; i++){
    
//   }
  
  while (ptr2m != end){
    if (is_pointer(ptr2m) == NULL) return;
    mark(ptr2m);
    ptr2m = next_chunk(ptr2m);
  }
  
  
}




// standard initialization 

void init_gc() {
  size_t stack_var;
  init_global_range();
  heap_mem.start=malloc(512);
  //since the heap grows down, the end is found first
  stack_mem.end=(size_t *)&stack_var;
}

void gc() {
  size_t stack_var;
  heap_mem.end=sbrk(0);
  //grows down, so start is a lower address
  stack_mem.start=(size_t *)&stack_var;

  // build the index that makes determining valid ptrs easier
  // implementing this smart function for collecting garbage can get bonus;
  // if you can't figure it out, just comment out this function.
  // walk_region_and_mark and sweep are enough for this project.
  build_heap_index();

  //walk memory regions
  walk_region_and_mark(global_mem.start,global_mem.end);
  walk_region_and_mark(stack_mem.start,stack_mem.end);
  //idk what to change
  sweep(global_mem.start, global_mem.end);
}
