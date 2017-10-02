// Shell
// CS361 UIC
// Ibrahiem Mohammad
// FALL 2016

#include <setjmp.h>
#include <errno.h>
#include <error.h>
#include <getopt.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <signal.h>
#include <unistd.h>
#include <sys/wait.h>
#include<assert.h> 
// #include <readline/readline.h>
// #include <readline/history.h>

#define CMDLIM 1000
#define ARGV 20
#define ARGC 100 

//global redirection flag
int redirection_flag;

sigjmp_buf ctrlc_buf;

pid_t fork_invoke(){
	pid_t pid = fork();
	//system err call for fork
	if (pid < 0) { printf("ERR: Cannot fork process\n"); exit(-1); }
	
	return pid;
}

char parser (char *buf, char **char_ptr_arr){

	int index_ctr = 0;
	
	char *parse_end = strtok(buf," \n");
	
	while(parse_end != NULL) {
		char_ptr_arr[index_ctr] = parse_end; 	
		parse_end = strtok(NULL," \n");
		
		index_ctr++;		

	}
	char_ptr_arr[index_ctr] = NULL ;

}

// void signal_handler(int sig){
// 	if (sig == SIGINT){ 
// 		printf("SIGINT handled\n");
// 		siglongjmp(ctrlc_buf, 1);
// 	}
// 	else if (sig == SIGTSTP){
// 		printf("SIGTSTP handled\n");
// 	}
// }

void sigint_handler(int sig){
	printf("\nSIGINT handled\n\nCS361> ");
	fflush(stdout);
}

void sigtstp_handler(int sig){
	printf("\nSIGTSTP handled\n\nCS361> ");
	fflush(stdout);
}

/*char parser (char str[], char *ptr){
	
	int i = 0;
	char args[LIM];
	ptr = strtok (str, " ");
	while (ptr != NULL){
		args[i] = ptr;
		ptr = strtok(NULL, " ");
		i++;
	}

	return args;

}	*/
	
//return char ctr for whatever user inputs
int char_ctr (char *char_input){
	int ctr = 0; char *temp;
	
	temp = strtok(char_input, " ");
	
	while(temp != NULL){
		temp = (strtok(NULL, " ")); ctr++;
	 }
	return ctr;
	}


int main(){
	
// 	char *ptr;
// 	char *cmdprmpt = "361> ";
	
	//catch signals
  signal(SIGINT, sigint_handler);
  signal(SIGTSTP, sigtstp_handler);
	
	//print err if signal not caught
	if (signal(SIGINT, sigint_handler) == SIG_ERR) printf("\nSIGINT not caught");
	if (signal(SIGINT, sigtstp_handler) == SIG_ERR) printf("\nSIGTSTP not caught");

	int f_index;
	char cmdline[CMDLIM];
	char **char_ptr_arr = malloc(ARGV*sizeof(char*));
	pid_t pid;
	
	
// 	while (sigsetjmp(ctrlcjmp(ctrlc_buf, 1) != 0);

	printf("Welcome to the 361 Shell. Enter 'exit' to quit.\n");	
	while (1){

		printf("CS361> ");
		//scanf("%s", &cmdline);
		//printf("%s\n", cmdline);	
		
		fgets (cmdline, CMDLIM, stdin);
		
		if (strcmp(cmdline, "exit\n") == 0){
			printf("exiting shell...\n");
			exit(1);
		}
		
		//copy cmdline for evalution
// 		char buf[CMDLIM];
// 		strcpy(buf, cmdline);
		parser(cmdline, char_ptr_arr);
 		int cmd_len = (strlen(cmdline))-1;
		//char **char_ptr_arr = (char**)malloc(sizeof(char*)*(char_ctr(buf)+1));
		
		
		//parsing
		
		pid = fork();
		wait(NULL);
		
		if (pid < 0) {printf("fork err"); exit(0);}

		if (pid == 0){
			printf("PID: %d\nCS361> ", getpid());
			
				for (f_index = 1; f_index < cmd_len; f_index++){
					
					if (strcmp(char_ptr_arr[f_index], ">") == 0) {freopen(char_ptr_arr[f_index+1], "w", stdout); break;}
					else if (strcmp(char_ptr_arr[f_index], "<") == 0) {freopen(char_ptr_arr[f_index+1], "r", stdout); break;}
					else if (strcmp(char_ptr_arr[f_index], ">>") == 0) {freopen(char_ptr_arr[f_index+1], "a", stdout); break;}
					
				}
			
				execvp(char_ptr_arr[0], char_ptr_arr);
		}
	
	}
		
		
		
		
//		parser(cmdline, ptr);
		
}
		
	
	 
