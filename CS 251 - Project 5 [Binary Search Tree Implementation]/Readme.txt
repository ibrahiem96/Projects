Name: Ibrahiem Mohammad

-----------------------------------------------

Describe what augmentations to the bst data structures you made to complete the 
project -- i.e., what typedefs did you change and why?

I added min and man to the tree struct. I did this to ensure that I could achieve min and max run at O(1).

I also added a leftctr (left subtree counter) to the node struct. I did this because I needed it to enable my get_ith_smallest_element function to run in O(h). 


I also added a size element. I did this to ensure that I could achieve size to run at O(1). 



-----------------------------------------------
Which functions did you need to modify as a result of the augmentations from the previous
question?  

The functions I modified are listed below:

extern int bst_min(BST_PTR t);

extern int bst_max(BST_PTR t);

extern int bst_size(BST_PTR t);

extern int bst_remove(BST_PTR t, int x);

extern void bst_insert(BST_PTR t, int x);

extern BST_PTR bst_create();

I also used an iterative version of insert, in which I kept track of all my bookkeeping for min, max, and size.




-----------------------------------------------
For each function from the previous question, how did you ensure that the (assymptotic) runtime 
remained the same?


extern int bst_min(BST_PTR t);
The runtime was O(1) because I was simply returning an element from the struct.

extern int bst_max(BST_PTR t);
The runtime was O(1) because I was simply returning an element from the struct.

extern int bst_size(BST_PTR t);
Runtime was O(1) because I simply accessed element from struct.

extern int bst_remove(BST_PTR t, int x);
Simply decremented size and updated min and max at the appropriate places--this did not change the runtime (did not any recursion or extra iteration).

extern void bst_insert(BST_PTR t, int x);
Simply incremented size and updated min and max at the appropriate places--this did not change the runtime (did not any recursion or extra iteration).

extern BST_PTR bst_create();
Just added two assignments to initialize min and max elements of tree struct.

I also used an iterative version of insert, in which I kept track of all my bookkeeping for min, max, and size.

Simply incremented size and updated min and max at the appropriate places--this did not change the runtime (did not any recursion or extra iteration). For the iterative version of insert, I used a while loop instead of recursion (which ensured that it still remained at O(h)). And the additional modification made to the size and min and max were simple assignments that did not alter the runtime.

-----------------------------------------------
For each of the assigned functions, tell us how far you got using the choices 0-5 and
answer the given questions.  


0:  didn't get started
1:  started, but far from working
2:  have implementation but only works for simple cases
3:  finished and I think it works most of the time but not sure the runtime req is met. 
4:  finished and I think it works most of the time and I'm sure my design leads to 
       the correct runtime (even if I still have a few bugs).
5:  finished and confident on correctness and runtime requirements


bst_to_array level of completion:  5 


-----------------------------------------------
bst_get_ith level of completion:  5  

    How did you ensure O(h) runtime?

    ANSWER: I first set the ith smallest element to -1. Then if the tree root existed I initalized a tracking node (used for traversal throughout the current root [which happens to be the main root]). Then, I set an infinite loop which checked if the given number was equal to one more than the size of the left subtree (left because, the smallest elements will only exist on the left subtree). If this condition was met, then I set the ith smallest element to the current value in the root, applied a break on the while loop, and then returned the ith smallest element. 

    Else, if the given element was greater than the size of the left subtree, I set the given element equal to the difference between the given element and the size of the left subtree plus one. Then traversed the right sub tree.

    Else, I traversed the left subtree.

    Since I used only one while loop in this function, and traversed the right subtree and left subtree based on the element given, this function is O(h).

-----------------------------------------------
bst_get_nearest level of completion:  5  

    How did you ensure O(h) runtime?

    ANSWER: For this function I made sure that if the given value was equal to the current root value then I would return the given value.

    Else I set the difference between the current root value and the given value.

    Then I traversed the left subtree if x was smaller than the current root value. Then if x is greater than the current root val I traverse the right subtree.

    Because I checked first if the right subtree was greater than the given element, I knew whether to traverse right or left (this cut out half the tree, since if the right is greater, I know all the elements in the right will give me a greater difference). 

    Thus, I shortened my traversal distance and time, and went down the tree instead of checking each and every single node. Therefore I was able to get the runtime to equal O(h).
  
-----------------------------------------------
bst_num_geq level of completion:  5  

    How did you ensure O(h) runtime?

    ANSWER: Again, checked if given value is greater than right subtree. If it was, then I know automatically that I must traverse the left subtree. This cuts the traversal amount and time by half. I do this instead of checking each and every single node. This ensures that my runtime is O(h) and not O(n).

-----------------------------------------------
bst_num_leq level of completion:  5

    How did you ensure O(h) runtime?

    ANSWER: Again, checked if given value is greater than right subtree. If it was, then I know automatically that I must traverse the left subtree. This cuts the traversal amount and time by half. I do this instead of checking each and every single node. This ensures that my runtime is O(h) and not O(n).

-----------------------------------------------
EXTRA CREDIT FUNCTIONS:

bst_min level of completion:  5

    How did you ensure O(1) runtime?

    ANSWER: I inserted a min element into my node structure which I set equal to the highest possible integer. Then, every time I inserted a new node (with a new value), I checked to see if the new value was smaller than the current min. If it was, then I set that new value to the min. In the remove function, I set a temp min. After the removal was done, I set the new min to the nearest element to the temp min.

    If I wanted the minimum I just returned the min element from the node structure which took O(1).

-----------------------------------------------
bst_max level of completion:  5

    How did you ensure O(1) runtime?

    ANSWER: I inserted a max element into my node structure which I set equal to the lowest possible integer. Then, every time I inserted a new node (with a new value), I checked to see if the new value was bigger than the current max. If it was, then I set that new value to the max. In the remove function, I set a temp max. After the removal was done, I set the new max to the nearest element to the temp max.

    If I wanted the maximum I just returned the max element from the node structure which took O(1).

----------------------------------------------

Write a few sentences describing how you tested your solutions.  Are there
any tests that in retrospect you wish you'd done?

In order to test my functions, I used a main function within bst itself. In it I used a series of print statements to see if the functions were doing what they were supposed to do. I also used VisualStudio debugger to inside the nodes and see if they held the proper values.

To test if my runtimes were correct I drew out the binary trees and did calculations by hand.