#include<stdio.h>
#include<stdlib.h>

/* Assignment: Project 1
   Name: Ibrahiem Mohammad
   Net ID: imoham3
   Date Due: 9/6/15

   Version 0.0
*/


/*Function 1
  Copies values from one array to another
 */
void arrayCopy (int fromArray[], int toArray[], int size){
	
	int i, j;

	for (i = 0; i < size; i++){		//clone array
		
		toArray[i] = fromArray[i];
	}
	
	for (j = 0; j < size; j++){		//print cloned array
		
		printf("\nCloned Array: %d", toArray[j]); 
	}
	
	printf("\n");
}

/*Function 2
  Sorts an array in ascending order
*/
void sort (int arr[], int size){

	int i, j, temp, k;
	
	for (i = 0; i < (size - 1); i++){
		
		for (j = 0; j < (size - i - 1); j++){
			
			if (arr[j] > arr[j+1]){
				
				temp = arr[j];
				arr[j] = arr[j+1];
				arr[j+1] = temp;
			}
		}
	}

	for (k = 0; k < size; k++){
		printf("\nSorted Array: %d", arr[k]); 
	}
	
	printf("\n");
}

/*Function 3
  Performs a linear search on an unsorted array
*/
int linear_search (int arr[], int size, int target, int *numComparisons){

	int i;
	for (i = 0; i < size; i++){
		if (arr[i] == target){
			printf("\nTarget found at position: %d", i);
			printf("\nNumber of comparisons: %d", i+1);
			*numComparisons = i + 1;
			return i, *numComparisons;			
		}
	
	}
	
	printf("\nTarget not found");	
	return -1;
}

/*Function 4
  Performs a binary search on a sorted array
*/
int binary_search (int arr[], int size, int target, int *numComparisons){
	
	int divider = size - 1;
	int i = 0;
	int middle = (i+divider)/2;
	*numComparisons = 0;

	while (i <= divider){
		middle = (i+divider)/2;
		if (target==arr[middle]){
			printf("\nTarget found at pos: %d", middle);
			*numComparisons++;
			printf("\nNumber of Comparisons: %i", *numComparisons);
			return middle, *numComparisons;
		}
		else if (target > arr[middle]){
			i = middle + 1;
			*numComparisons++;
		}
		else if (target < arr[middle]){
			divider = middle - 1;
			*numComparisons++;
		}
	}
	
	printf("\nTarget not found");
	return -1;
}


int main(){
	
	int *fromArray;
	int size = 10;
	fromArray = (int *)malloc (size * sizeof(int));
	
	int val = 0;
	int pos = 0;	

	printf("Enter the values to be stored in the array:");
	//read in value and store it in val
	scanf("%d", &val);

	while (val != -999){	
		if (pos >= size){
			int *temp;
			//create temp array to which we allocate double the memory 
			temp = (int *)malloc(size * 2 * sizeof(int));
			int i;
			//filling in elements of original array into temp array
			for (i=0; i<size; i++){
				temp[i] = fromArray[i];
			}
			free(fromArray);
			//assigning memory of the new doubled array to the original array
			fromArray = temp;
			size = size*2;
		}
			
		fromArray[pos] = val;
		
		pos++;

		scanf("%d", &val);
	}	
	
	size = pos;
	int toArray[size];
	int numComparisons;

	int linear_target;
	int binary_target;

	arrayCopy (fromArray, toArray, size);

	sort (fromArray, size);

	printf("\nEnter the number you wish to search in the unsorted array:");	
	scanf("%d", &linear_target); 

	linear_search (toArray, size, linear_target, &numComparisons);

	printf("\nEnter the number you wish to search in the sorted array:");	
	scanf("%d", &binary_target); 
	
	binary_search (fromArray, size, binary_target, &numComparisons);


}
