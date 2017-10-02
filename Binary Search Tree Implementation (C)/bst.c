#include "bst.h"
#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include <limits.h>


/*NAME: Ibrahiem Mohammad
  NETID: imoham3
  DATE: 11/16/15
  */ 
//fix size--if he asks for size of a node, then what?
struct bst_node {
    int val;
    struct bst_node *left;
    struct bst_node *right;
    int leftCtr;
    int size;
};

//this below is referencing the above struct
typedef struct bst_node NODE;

//this is the tree struct
struct bst {
    NODE *root;
    int min;
    int max;
    //int size;
};

BST_PTR bst_create(){
  BST_PTR t = malloc(sizeof(struct bst));
  t->root = NULL;
  t->min = INT_MAX;
  t->max = INT_MIN;
  return t;
}


static void free_r(NODE *r){
    if(r==NULL) return;
    free_r(r->left);
    free_r(r->right);
    free(r);
}
void bst_free(BST_PTR t){
    free_r(t->root);
    free(t);
}

static NODE * insert(NODE *r, int x){
    NODE *leaf;
    if(r == NULL){
      leaf = malloc(sizeof(NODE));
      leaf->left = NULL;
      leaf->right = NULL;
      leaf->val = x;
      leaf->size = 1;
      return leaf;
    }

    if(r->val == x)
        return r;
    if(x < r->val){
        r->size++;
        r->left = insert(r->left, x);
        return r;
    }
    else {
        r->size++;
        r->right = insert(r->right, x);
        return r;
    }
}

//how about an iterative version?
static NODE *insert_i(BST_PTR t, int x){
    /*pointer to traverse tree*/
    NODE *tracker = t->root;
    NODE *currNode = t->root;

    NODE *leaf;
    leaf = malloc(sizeof(NODE));
    leaf->left = NULL;
    leaf->right = NULL;
    leaf->val = x;
    leaf->size = 1;
 
    //traverse till appropriate node
    while(tracker)
    {
        currNode = tracker;
 
        if( leaf->val < tracker->val )
        {
            /*we are branching to left subtree
               increment node count*/
            tracker->leftCtr++;
            tracker->size++;
            /*left subtree*/
            tracker = tracker->left;
        }
        else
        {
            /*right subtree*/
            tracker->size++;
            tracker = tracker->right;
        }

    }
 
    /*if the tree is empty, make it as root node*/
    if( !t->root )
    {
        t->root = leaf;
    }
    else if( leaf->val < currNode->val )
    {
        /*insert on left side*/
        currNode->left = leaf;

    }
    else
    {
        /*insert on right side*/
        //t->max = t->root->val;
        currNode->right = leaf;
    }
 
    return t->root;

}


void bst_insert(BST_PTR t, int x){
    /*NODE *leaf;
    leaf = malloc(sizeof(NODE));
    leaf->left = NULL;
    leaf->right = NULL;
    leaf->val = x;
    leaf->size = 1;
     
    t->root = insert_i(t->root, leaf, leaf->size);*/
    //t->size++;

    if (x < t->min){
      t->min = x;
    }
    if (x > t->max){
      t->max = x;
    }

    t->root = insert_i(t, x);
}

int bst_contains(BST_PTR t, int x){
    NODE *p = t->root;

    while(p != NULL){

        if(p->val == x)
            return 1;
        if(x < p->val){
            p = p->left;
        }
        else
            p = p->right;
    }
    return 0;  
}

static int min_h(NODE *r){
  if(r==NULL)
    return -1; // should never happen!
  while(r->left != NULL)
      r = r->left;
  return r->val;
}

static int max_h(NODE *r){
  if(r==NULL)
    return -1; // should never happen!
  while(r->right != NULL)
      r = r->right;
  return r->val;
}

static NODE *remove_r(NODE *r, int x, int *success){
NODE   *tmp;
int sanity;

  if(r==NULL){
    *success = 0;
    return NULL;
  }
  if(r->val == x){
    *success = 1;

    if(r->left == NULL){
        tmp = r->right;
        free(r);
        return tmp;
    }
    if(r->right == NULL){
        tmp = r->left;
        free(r);
        return tmp;
    }
    // if we get here, r has two children
    r->val = min_h(r->right);
    r->size--;
    r->right = remove_r(r->right, r->val, &sanity);
    if(!sanity)
        printf("ERROR:  remove() failed to delete promoted value?\n");
    return r;
  }
  if(x < r->val){
    r->size--;
    r->left = remove_r(r->left, x, success);
  }
  else {
    r->size--;
    r->right = remove_r(r->right, x, success);
  }
  return r;

}


int bst_remove(BST_PTR t, int x){

    if (!bst_contains(t, x)) return 0;

    int success;
    int c_min = t->min;
    int c_max = t->max;
    t->root = remove_r(t->root, x, &success);
    t->min = bst_get_nearest(t, c_min);
    t->max = bst_get_nearest(t, c_max);
    //t->size--;
    return success;
    //TODO: add bookkeeping for max and min
}

static int size(NODE *r){

    return r->size;

    /*OLD SIZE CODE
    if(r==NULL) return 0;
    return size(r->left) + size(r->right) + 1;
*/
}

/*OLD SIZE FUNCTIONS
  int bst_size(BST_PTR t){

    return size(t->root);
}
*/

/*new size function created using a counter in the tree struct*/
int bst_size(BST_PTR t){
  return t->root->size;
}

static int height(NODE *r){
    int l_h, r_h;

    if(r==NULL) return -1;
    l_h = height(r->left);
    r_h = height(r->right);
    return 1 + (l_h > r_h ? l_h : r_h);

}
int bst_height(BST_PTR t){
    return height(t->root);

}

//gets min value in O(1)
int n_min(BST_PTR t) {
  return t->min;
}

//gets max value in O(1)
int n_max(BST_PTR t){
  return t->max;
}

int bst_min(BST_PTR t){
    return n_min(t);
}

int bst_max(BST_PTR t){
    return n_max(t);
}

static void indent(int m){
    int i;
    for(i=0; i<m; i++)
        printf("-");
}

static void inorder(NODE *r){
  if(r==NULL) return;
  inorder(r->left);
  printf("[%d]\n", r->val);
  inorder(r->right);

}
void bst_inorder(BST_PTR t){

  printf("========BEGIN INORDER============\n");
  inorder(t->root);
  printf("=========END INORDER============\n");


}

static void preorder(NODE *r, int margin){
  if(r==NULL) {
    indent(margin);
    printf("NULL \n");
  } else {
    indent(margin);
    printf("%d\n", r->val);
    preorder(r->left, margin+3);
    preorder(r->right, margin+3);
  }
}

void bst_preorder(BST_PTR t){

  printf("========BEGIN PREORDER============\n");
  preorder(t->root, 0);
  printf("=========END PREORDER============\n");

}

/* 
 * Complete the (recursive) helper function for the post-order traversal.
 * Remember: the indentation needs to be proportional to the height of the node!
 */
static void postorder(NODE *r, int margin){
    /* FILL IN FUNCTION */
}

// indentation is proportional to depth of node being printed
//   depth is #hops from root.
void bst_postorder(BST_PTR t){

  printf("========BEGIN POSTORDER============\n");
  postorder(t->root, 0);
  printf("=========END POSTORDER============\n");

}

/* 
 * Write the (recursive) helper function from_arr, used by
 * bst_from_sorted_arr(...). The function must return a sub-tree that is
 * perfectly balanced, given a sorted array of elements a.
 */
static NODE * from_arr(int *a, int n){
int m;
NODE *root;

    if(n <= 0) return NULL;
    m = n/2;
    root = malloc(sizeof(NODE));
    root->val = a[m];
    root->left = from_arr(a, m);
    root->right = from_arr(&(a[m+1]), n-(m+1));
    return root;

}

BST_PTR bst_from_sorted_arr(int *a, int n){

  BST_PTR t = bst_create();

  t->root = from_arr(a, n);

  return t;
}

void print_keys_in_range(NODE *root, int min, int max){

   /* base case */
   if ( root == NULL ){
      return;
    }
 
   if ( min < root->val )
     print_keys_in_range(root->left, min, max);
 
   if ( min <= root->val && max >= root->val )
     printf("%d ", root->val );
 
   if ( max > root->val )
     print_keys_in_range(root->right, min, max);
}


void add_keys_in_range(NODE *root, int min, int max, int *arr, int *index){

   /* base case */
   if ( root == NULL ){
      return;
    }
 
   /* Since the desired o/p is sorted, recurse for left subtree first
      If root->val is greater than min, then only we can get o/p keys
      in left subtree */
   if ( min < root->val )
     add_keys_in_range(root->left, min, max, arr, index);
 
   /* if root's val lies in range, then prints root's val */
   if ( min <= root->val && max >= root->val ){
     arr[*index] = root->val;
     (*index)++;
   }
  /* If root->val is smaller than max, then only we can get o/p keys
      in right subtree */
   if ( max > root->val )
     add_keys_in_range(root->right, min, max, arr, index);
}

int *bst_to_array(BST_PTR t){
  
  //create array to hold the values of BST
  int n = bst_size(t);
  int *arr = (int*)malloc(sizeof(int)*n);
  int i = 0;
  int index = 0;
  //sort BST in-order
  bst_inorder(t);

  //TODO: traverse BST and add values to array
  //how:? get min and max of the tree and set them as range. then assign the values to the array.     
  //get min and max
  int min = bst_min(t);
  int max = bst_max(t);
  
  if (t->root == NULL){
    return arr;
  }

  add_keys_in_range(t->root, min, max, arr, &i);
  //add_keys_to_array(t->root, arr, &index);

  for (i = 0; i < n; i++){
    printf("\n %d", arr[i]);
  }

  return arr;

}

int bst_get_ith(BST_PTR t, int i){
  int ith_elem = -1;
  //NODE *root = t->root;

  if (t->root){
    NODE *tracker = t->root;

    while (tracker){
      if ((tracker->leftCtr + 1) == i){
        ith_elem = tracker->val;
        break;
      }
      else if (i > tracker->leftCtr){
        i = i - (tracker->leftCtr + 1);
        tracker = tracker->right;
      }
      else {
        tracker = tracker->left;
      }
    }
  }

  return ith_elem;

}

int bst_get_nearest_h(NODE *root, int x, int val_holder, int difference){

  //NODE *t_root = t->root;

  int temp_difference = abs(x-root->val);

  if (temp_difference < difference){
    difference = temp_difference;
    val_holder = root->val;
  }

  if (difference == 0) return root->val;

  if (x < root->val && root->left != NULL) return bst_get_nearest_h(root->left, x, difference, val_holder);

  else if (x > root->val && root->right != NULL) return bst_get_nearest_h(root->right, x, difference, val_holder);

  else return val_holder;

  return 0;
}

int bst_get_nearest(BST_PTR t, int x){
  
  /*base case*/
  if (t->root == NULL) return 0;

  /*if x happens to be equal to current root val*/
  if (x == t->root->val) return x;

  int difference = abs(t->root->val - x);

  int val_holder = t->root->val;

  if (x < t->root->val) return bst_get_nearest_h(t->root->left, x, difference, val_holder);

  if (x > t->root->val) return bst_get_nearest_h(t->root->right, x, difference, val_holder);

  return 0;
}


int bst_num_geq_h(NODE *root, int x, int ctr){

  int ctr_n = ctr;

  if (x == root->val){
    if (root->right != NULL) return (ctr + size(root->right));
    else return ctr;
  }

  if (x < root->val){
    if (root->right != NULL)
      ctr_n = ctr + size(root->right) + 1;
    else ctr_n++;
    if (root->left == NULL) return ctr_n;
    else return bst_num_geq_h(root->left, x, ctr_n);
  }

  if (x > root->val){
    if (root->right == NULL) return ctr_n;
    else return bst_num_geq_h(root->right, x, ctr_n);
  }

  return 0;
}

int bst_num_geq(BST_PTR t, int x){

  int ctr = 0;

  if (x < t->root->val){
    if (t->root->right != NULL){
      ctr = size(t->root->right) + 1;
    }
    else ctr++;
    if (t->root->left == NULL) return ctr;
    else return bst_num_geq_h(t->root->left, x, ctr);
  }
  if (x > t->root->val){
    if (t->root->right == NULL) return ctr;
    else return bst_num_geq_h(t->root->right, x, ctr);
  }

}

int bst_num_leq_h(NODE *root, int x, int ctr){

  int ctr_n = ctr;

  if (x == root->val){
    if (root->left != NULL) return (ctr + size(root->left));
    else return ctr;
  }

  if (x > root->val){
    if (root->left != NULL)
      ctr_n = ctr + size(root->left) + 1;
    else ctr_n++;
    if (root->right == NULL) return ctr_n;
    else return bst_num_geq_h(root->right, x, ctr_n);
  }

  if (x < root->val){
    if (root->left == NULL) return ctr_n;
    else return bst_num_geq_h(root->left, x, ctr_n);
  }

  return 0;
}

int bst_num_leq(BST_PTR t, int x){

  int ctr = 0;

  if (x > t->root->val){
    if (t->root->left != NULL){
      ctr = size(t->root->left) + 1;
    }
    else ctr++;
    if (t->root->right == NULL) return ctr;
    else return bst_num_geq_h(t->root->right, x, ctr);
  }
  if (x < t->root->val){
    if (t->root->left == NULL) return ctr;
    else return bst_num_geq_h(t->root->left, x, ctr);
  }

}

int main(){
    
    /* PART 1 */

    printf("\nsanity check 1");

    int i = 0;
    int a[] = {8, 2, 6, 9, 11, 3, 7, 12, 18, 4, 13};

    BST_PTR t = bst_create();

    printf("\nsanity check 2");

    for(i=0; i<11; i++){
        bst_insert(t, a[i]);
    }

    assert(bst_size(t) == 11);
    //int min = bst_min(t);
    //int max = bst_max(t); 

    printf("\n new min in O(1): %d", n_min(t));
    printf("\n new max in O(1): %d", n_max(t));


    /* PART 2 */
    
    bst_inorder(t);

    //bst_preorder(t);

    //bst_postorder(t);

    //bst_free(t);
    
    /* PART 3 (extra) 
    
    int sorted_a[] = {2, 3, 6, 7, 8, 9, 11};
    
    t = bst_from_sorted_arr(sorted_a, 7);
    
    /*
    bst_inorder(t);

    bst_preorder(t);

    bst_postorder(t);

    bst_free(t);
    */

    bst_to_array(t);
 
    printf("\n check if removal bookkeeping is working: %d", size(t->root->right));


    bst_remove(t, 5);
    printf("\n check if removal bookkeeping is working: %d", size(t->root->right));

    printf("\n root size in O(1): %d", size(t->root));

    printf("\n test for ith_elem: %d", bst_get_ith(t, 7));

    printf("\n test for nearest elem: %d", bst_get_nearest(t, 10));

    printf("\n test for all less than elem %d: %d", 9, bst_num_leq(t, 9));
    printf("\n test for all greather than elem %d: %d", 9, bst_num_geq(t, 9));    


    return 0;

}