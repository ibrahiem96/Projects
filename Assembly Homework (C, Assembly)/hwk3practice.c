#include <stdio.h>
int main()
{
 long x = 0x00000001;
 long result = 2;
 long mask;
 for (mask = 0; mask != 22 ; mask = mask+1) {
   result |= mask << 3;
 }
 printf("result %lx\n",result);
 }
