/*Source File Two*/


void doAdd (GROUP *g)
{
 /* get group size from input */
 int size = getPosInt();
 if (size < 1)
   {
    printf ("Error: Add command requires an integer value of at least 1\n");
    printf ("Add command is of form: a <size> <name>\n");
    printf ("  where: <size> is the size of the group making the reservation\n");
    printf ("         <name> is the name of the group making the reservation\n");
    return;
   }

 /* get group name from input */
 char *name = getName();
 if (NULL == name)
   {
    printf ("Error: Add command requires a name to be given\n");
    printf ("Add command is of form: a <size> <name>\n");
    printf ("  where: <size> is the size of the group making the reservation\n");
    printf ("         <name> is the name of the group making the reservation\n");
    return;
   }

 printf ("Adding group \"%s\" of size %d\n", name, size);


 enum status = in_restaurant;

 if (doesNameExist(g, name)==TRUE){
  printf("\n Error: Group name already exists");
  return;
 }

 else {
  addToList(g, size, name, status); 
 }


}


void doCallAhead (GROUP *g)
{
 /* get group size from input */
 int size = getPosInt();
 if (size < 1)
   {
    printf ("Error: Call-ahead command requires an integer value of at least 1\n");
    printf ("Call-ahead command is of form: c <size> <name>\n");
    printf ("  where: <size> is the size of the group making the reservation\n");
    printf ("         <name> is the name of the group making the reservation\n");
    return;
   }

 /* get group name from input */
 char *name = getName();
 if (NULL == name)
   {
    printf ("Error: Call-ahead command requires a name to be given\n");
    printf ("Call-ahead command is of form: c <size> <name>\n");
    printf ("  where: <size> is the size of the group making the reservation\n");
    printf ("         <name> is the name of the group making the reservation\n");
    return;
   }

 printf ("Call-ahead group \"%s\" of size %d\n", name, size);

 enum status = called;

 if (doesNameExist(g, name)==TRUE){
  printf("\n Error: Group name already exists");
  return;
 }

 else {
  addToList(g, size, name, status); 
 }

void doWaiting (GROUP *g)
{
 /* get group name from input */
 char *name = getName();
 if (NULL == name)
   {
    printf ("Error: Waiting command requires a name to be given\n");
    printf ("Waiting command is of form: w <name>\n");
    printf ("  where: <name> is the name of the group that is now waiting\n");
    return;
   }

 printf ("Waiting group \"%s\" is now in the restaurant\n", name);


 if (doesNameExist(g, name)==FALSE){
  printf("\n Error: Group name does not exist");
  return;
}

  else if (updateStatus(g, name)==FALSE){
    printf("\n Error: Group status is not Call-in");
    return;
  }

  else {
    updateStatus(g, name);
  }



void doRetrieve (GROUP *g)
{
 /* get table size from input */
 int size = getPosInt();
 if (size < 1)
   {
    printf ("Error: Retrieve command requires an integer value of at least 1\n");
    printf ("Retrieve command is of form: r <size>\n");
    printf ("  where: <size> is the size of the group making the reservation\n");
    return;
   }
 clearToEoln();
 printf ("Retrieve (and remove) the first group that can fit at a tabel of size %d\n", size);

 retrieveAndRemove(g, size)

}

void doList (GROUP *g)
{
 /* get group name from input */
 char *name = getName();
 if (NULL == name)
   {
    printf ("Error: List command requires a name to be given\n");
    printf ("List command is of form: l <name>\n");
    printf ("  where: <name> is the name of the group to inquire about\n");
    return;
   }

   if (doesNameExist(g, name)==FALSE){
    printf("\n Error: Group name does not exist");
    return;
   }

   else {
    printf ("Group \"%s\" is behind the following groups\n", name);

    printf("\n Number of groups ahead: %d", countGroupsAhead(g, name));

    displayGroupSizeAhead(g, name);
   
   }

 }
 
void doDisplay (GROUP *g)
{
 clearToEoln();
 printf ("Display information about all groups\n");

  displayListInformation(g); 

}