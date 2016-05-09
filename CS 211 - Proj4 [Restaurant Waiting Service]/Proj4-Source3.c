/**Third source file*/

void addToList(GROUP *g, int g_size, char *g_name, enum status){
	
	GROUP *new = (GROUP*)malloc(sizeof(GROUP));

	if(new == NULL){
		printf("\n Unable to allocate memory");
		exit(-1);
	}

	GROUP *temp = g;

	new->size = g_size;
	new->name = g_name;
	new->status = status;
	new->next = NULL;

	if(g == NULL){
		g = new;
		return;
	}

	while(temp->next != NULL){
		temp = temp->next;
	}

	temp->next = new;
	return;
}


bool doesNameExist(GROUP *g, char *g_name){

	bool exists = TRUE;

	if (g == NULL){
		exit(-1);
	}

	while (g->next != NULL){

		if (g->name == g_name){
			return exists; 
		}

		g = g->next;

	}

	if (exists==TRUE){
		exists = FALSE;
		return exists;
	}

}

bool updateStatus(GROUP *g, char *g_name){

	if (g == NULL) exit(-1);

	while (g->next != NULL){

		if (g->name == name){

			if (g->status == 1){
				return FALSE;
			}

			g->status = in_restaurant;
		}

		g = g->next;
	}

	return TRUE;
}


char retrieveAndRemove(GROUP *g, int table_size){

	char *name_holder;

	name_holder = (char*)malloc(sizeof(char));

	while (g->next!=NULL) {

		if (g->status == 1 && g->size <= table_size){
			name_holder = g->name;
		}

		g = g->next;
	}


	return name_holder;

	//add error checks and remove node from list
}


int countGroupsAhead(GROUP *g, char *g_name){

	int counter = 0;

	while (g->next != NULL){

		if (g->name == g_name){
			return counter;
		}

		counter++;
		g = g->next;
	}

	return counter;
	//add error checks
}


void displayGroupSizeAhead(GROUP *g, char *g_name){

	while (g->next != NULL){

		if (g->name == g_name){
			return;
		}

		counter++;
		printf("\n Group Name: %s, Group Size: %d", g->name, g->size);
		g = g->next;
	}
	//add error checks
}

void displayListInformation(GROUP *g){


	int counter = 0;
	while (g->next!=NULL){

		printf("\n Group Name: %s", g->name);
		printf("\n Group Size: %d", g->size);
		printf("\n Group Status (0 = Called Ahead, 1 = In-Restaurant: %d", g->status);
		
		counter++;
		g = g->next;
	}

	printf("\n Number of groups: %d", counter);

//	add error checks
}































	)