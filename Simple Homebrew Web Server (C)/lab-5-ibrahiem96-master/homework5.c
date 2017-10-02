#include <fnmatch.h>
#include <fcntl.h>
#include <errno.h>
#include <netinet/in.h>
#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/select.h>
#include <sys/socket.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <sys/uio.h>
#include <unistd.h>

#include <dirent.h>
#include <stdbool.h>
#include <pthread.h>

#define BACKLOG (10)
//#define pathLIM 4096

char * cwd;
void serve_request(int);

char * request_str = "HTTP/1.0 200 OK\r\n"
        "Content-type: text/html; charset=UTF-8\r\n\r\n";


char * index_hdr = "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 3.2 Final//EN\"><html>"
        "<title>Directory listing for %s</title>"
"<body>"
"<h2>Directory listing for %s</h2><hr><ul>";

// snprintf(output_buffer,4096,index_hdr,filename,filename);


char * index_body = "<li><a href=\"%s\">%s</a>";

char * index_ftr = "</ul><hr></body></html>";

// bool comparer (int n1, int n2, char * o){

//    if (o == "gl"){
//      if (n1 > n2) return true;
//      else return false
//    }
  
//    else if (o == "lt"){
//       if (n1 < n2) return true;
//       return false;
//    }
  
//    else if (o == "geq"){
//      if (n1 >= n2) return true;
//      else return false;
//    }
  
//    else if (o == "leq"){
//      if (n1 <= n2) return true;
//      else return false;
//    } 
  
//    else if (o == "eq"){
//      if (n1 == n2) return true;
//      else return false;
//    }
  
//    else {
//      printf("Program should not reach this line\n");
//      return false;
//    }
  
// }

/*check if directory exists
 *referenced from http://stackoverflow.com/questions/12510874/how-can-i-check-if-a-directory-exists
 */
int isDir(char * dir){
    printf("got into isDir\n");


 struct stat buffer; stat(dir, &buffer);
  if (stat(dir, &buffer) != 0) {
   
    printf("shit is not a dir m8\n");

    return 0;
    
  }
     printf("CONGRATS shit is a dir m8\n");
  return S_ISDIR(buffer.st_mode);
}

bool isFile (char *filename){
  struct stat buffer; stat(filename, &buffer);
  if (S_ISREG(buffer.st_mode)) {
    return true;
  }
  else return false;
  
}

bool isDir2 (char *filename){
  struct stat buffer; stat(filename, &buffer);
  if (S_ISDIR(buffer.st_mode)) return true;
  else return false;
}


// char * copyAndCat(char * f_ret, char * f_copy){
//   strcpy(f_ret, f_copy);
//   strcat(f_ret, f_copy);
//   return f_ret;
// }

// const char * storePath (const char* curr_path, const char* arg1, const char* arg2, const char* arg3){
//   snprintf(curr_path, 4096, "%s%s%s", arg1, arg2, arg3);
//   return curr_path;
// }

// const char * newPath (int lim){
//   return char path[lim];
// }

/* char* parseRequest(char* request)
 * Args: HTTP request of the form "GET /path/to/resource HTTP/1.X" 
 *
 * Return: the resource requested "/path/to/resource"
 *         0 if the request is not a valid HTTP request 
 * 
 * Does not modify the given request string. 
 * The returned resource should be free'd by the caller function. 
 */
char* parseRequest(char* request) {
  //assume file paths are no more than 256 bytes + 1 for null. 
  char *buffer = malloc(sizeof(char)*257);
  memset(buffer, 0, 257);
  
  if(fnmatch("GET * HTTP/1.*",  request, 0)) return 0; 

  sscanf(request, "GET %s HTTP/1.", buffer);
  return buffer; 
}


char * get_filetype(char *filename)
{
 
  char * filetype = malloc(4096*sizeof(char));
  
 if (strstr(filename, ".html"))
 strcpy(filetype, "text/html");
 else if (strstr(filename, ".gif"))
 strcpy(filetype, "image/gif");
 else if (strstr(filename, ".jpg"))
 strcpy(filetype, "image/jpeg");
 else if (strstr(filename, ".png"))
    strcpy(filetype, "image/png");
 else if (strstr(filename, ".ico"))
   strcpy(filetype, "image/x-icon");
 else if (strstr(filename, ".pdf"))
   strcpy(filetype, "application/pdf");
 else
 strcpy(filetype, "text/plain");
  
  return filetype;
}

char * ccat_list(char * dl){
  char * pl = malloc(1013*sizeof(char));
  
  pl[0] = '.';
  strcpy(&pl[1], dl);
  strcat(pl, "/");
  
  if (isDir2(pl)){
     strcat(dl, "/");
  }
  free(pl);
  return dl;
}



void serve_request(int client_fd){
  int read_fd;
  int bytes_read;                 //addition
  int file_offset = 0;
  char client_buf[4096];
  char send_buf[4096];            //addition
  char filename[4096];            //addition
  char * requested_file;
  memset(client_buf,0,4096);
  memset(filename,0,4096);        //addition
  while(1){

    file_offset += recv(client_fd,&client_buf[file_offset],4096,0);
    if(strstr(client_buf,"\r\n\r\n"))
      break;
  }
  
  //OUT OF LOOP
  requested_file = parseRequest(client_buf);
  
  
  /*------------------------------------------------------------*/
 // send(client_fd,request_str,strlen(request_str),0);
  // take requested_file, add a . to beginning, open that file
  filename[0] = '.';
  strncpy(&filename[1],requested_file,4095);
           printf("accepted into serve\n");

  //check if dir contains index.html
  if (isDir2(filename)){
         printf("accepted into dir\n");

     char * filename2 = malloc(4096*sizeof(char));
     strcpy(filename2, filename);
     strcat(filename2, "index.html");
  
      if (isFile(filename2)){
        char * new_req = malloc(4096*sizeof(char));
        strcpy(new_req, "HTTP/1.0 200 OK\r\n"
        "Content-type: ");
        strcat(new_req, get_filetype(filename2));
        strcat(new_req, "; charset=UTF-8\r\n\r\n");
        
        send(client_fd, new_req, strlen(new_req), 0);

        read_fd = open(filename2,0,0);
        while(1){
          bytes_read = read(read_fd,send_buf,4096);
          if(bytes_read == 0)
            break;
          
          send(client_fd,send_buf,bytes_read,0);
        }

        printf("sent content for index.html\n");
        free(new_req);
        return;
      }

      else {
      
        //server directory_listing
        
        char * req_l = malloc(4096*sizeof(char));
        strcpy(req_l, "HTTP/1.0 200 OK\r\n"
        "Content-type: ");
        strcat(req_l, get_filetype(filename));
        strcat(req_l, "; charset=UTF-8\r\n\r\n");
        //CONTENT SEND
        send(client_fd, req_l, strlen(req_l), 0);

        read_fd = open(filename,0,0);
        while(1){
          bytes_read = read(read_fd,send_buf,4096);
          if(bytes_read == 0)
            break;
          
          send(client_fd,send_buf,bytes_read,0);
        }
        
       char * plh = malloc(4096*sizeof(char));
      
       snprintf(plh, 4096, index_hdr, requested_file, requested_file);
        //HEADER SEND
       send(client_fd, plh, strlen(plh), 0);

       DIR * path = opendir(filename);

       if (path != NULL){
        //char * directory_listing = malloc(128000*sizeof(char));

      // directory_listing[0] = '\0';

       struct dirent * underlying_file = NULL;

       while ((underlying_file = readdir(path)) != NULL){
         
         char * dl = malloc(1013*sizeof(char));
         
         strcpy(dl, requested_file);
         strcpy(dl, underlying_file->d_name);
         
         strcat(dl, underlying_file->d_name);
         strcat(dl, "\n");
         
         char * nv = malloc(1013*sizeof(char));
         snprintf(nv, 1013, index_body, ccat_list(dl), underlying_file->d_name);
          //DIRECTORY SEND
         send(client_fd, nv, strlen(nv), 0);
         
         free(dl); free (nv);
        
         

       }
        
         //FOOTER SEND
       send(client_fd, index_ftr, strlen(index_ftr), 0);
         
       closedir(path);
      }
        
        
        
      }
    
     }
    //send file
    if(isFile(filename)){
      char * req_f = malloc(4096*sizeof(char));
      strcpy(req_f, "HTTP/1.0 200 OK\r\n"
        "Content-type: ");
      strcat(req_f, get_filetype(filename));
      strcat(req_f, "; charset=UTF-8\r\n\r\n");
      
      send(client_fd, req_f, strlen(req_f), 0);

        read_fd = open(filename,0,0);
        while(1){
          bytes_read = read(read_fd,send_buf,4096);
          if(bytes_read == 0)
            break;
          
          send(client_fd,send_buf,bytes_read,0);
        }
        free(req_f);
        return;
      
    }
      
    else {
        char * req_404 = malloc(4096*sizeof(char));
      
        strcpy(req_404, "HTTP/1.0 404 BAD\r\n"
        "Content-type: ");
        strcat(req_404, ".html");
        strcat(req_404, "; charset=UTF-8\r\n\r\n");
      
        char * n_cwd = malloc(4096*sizeof(char));
      
        n_cwd[0] = '.';
      
        strcpy(&n_cwd[1], cwd);
      
        send(client_fd, req_404, strlen(req_404), 0);
        
        read_fd = open(n_cwd,0,0);
        while(1){
          bytes_read = read(read_fd,send_buf,4096);
          if(bytes_read == 0)
            break;
          
          send(client_fd,send_buf,bytes_read,0);
        }

        free(req_404);
        free(n_cwd);
        return;
        
    }
       
  
  
  /*-----------------------------------------------------------*/
  
//     read_fd = open(filename,0,0);
//   while(1){
//     bytes_read = read(read_fd,send_buf,4096);
//     if(bytes_read == 0)
//       break;

//     send(client_fd,send_buf,bytes_read,0);
    
//   const char * pwd = newPath(256);
//   const char * rd = newPath(256);
//   const char * p = newPath(4096);
  
//   if (isDir(storePath(p, 4096, pwd, rd, requested_file))){
//     const char * ip = newPath(4096);
//     storePath(ip, 4096, p, index, "");
//     read_fd = open(ip, 0, 0);
    
//     if (read_fd < 0){
      
//     }
  
  close(read_fd);
  close(client_fd);
  return;
}


void * thread (void * arg){
  int sock = (*((int*) arg));
  free(arg);
  pthread_detach(pthread_self());
  
  serve_request(sock);
  
  return NULL;
}

/* Your program should take two arguments:
 * 1) The port number on which to bind and listen for connections, and
 * 2) The directory out of which to serve files.
 */
int main(int argc, char** argv) {
    /* For checking return values. */
    int retval;

    /* Read the port number from the first command line argument. */
    int port = atoi(argv[1]);  
  
    /*test cwd*/  
    
    //char * n_cwd_main = malloc(4096*sizeof(char));
    cwd = malloc(4096*sizeof(4096));
    getcwd(cwd, 4096);
    
    
    if ((chdir(argv[2])) < 0) {
      printf("Error obtaining cwd\n");
      exit(1);
    }
    else printf("changed dir\n");
  
    

    /* Create a socket to which clients will connect. */
    int server_sock = socket(AF_INET6, SOCK_STREAM, 0);
    if(server_sock < 0) {
        perror("Creating socket failed");
        exit(1);
    }

    /* A server socket is bound to a port, which it will listen on for incoming
     * connections.  By default, when a bound socket is closed, the OS waits a
     * couple of minutes before allowing the port to be re-used.  This is
     * inconvenient when you're developing an application, since it means that
     * you have to wait a minute or two after you run to try things again, so
     * we can disable the wait time by setting a socket option called
     * SO_REUSEADDR, which tells the OS that we want to be able to immediately
     * re-bind to that same port. See the socket(7) man page ("man 7 socket")
     * and setsockopt(2) pages for more details about socket options. */
    int reuse_true = 1;
    retval = setsockopt(server_sock, SOL_SOCKET, SO_REUSEADDR, &reuse_true,
                        sizeof(reuse_true));
    if (retval < 0) {
        perror("Setting socket option failed");
        exit(1);
    }

    /* Create an address structure.  This is very similar to what we saw on the
     * client side, only this time, we're not telling the OS where to connect,
     * we're telling it to bind to a particular address and port to receive
     * incoming connections.  Like the client side, we must use htons() to put
     * the port number in network byte order.  When specifying the IP address,
     * we use a special constant, INADDR_ANY, which tells the OS to bind to all
     * of the system's addresses.  If your machine has multiple network
     * interfaces, and you only wanted to accept connections from one of them,
     * you could supply the address of the interface you wanted to use here. */
    
   
    struct sockaddr_in6 addr;   // internet socket address data structure
    addr.sin6_family = AF_INET6;
    addr.sin6_port = htons(port); // byte order is significant
    addr.sin6_addr = in6addr_any; // listen to all interfaces

    
    /* As its name implies, this system call asks the OS to bind the socket to
     * address and port specified above. */
    retval = bind(server_sock, (struct sockaddr*)&addr, sizeof(addr));
    if(retval < 0) {
        perror("Error binding to port");
        exit(1);
    }

    /* Now that we've bound to an address and port, we tell the OS that we're
     * ready to start listening for client connections.  This effectively
     * activates the server socket.  BACKLOG (#defined above) tells the OS how
     * much space to reserve for incoming connections that have not yet been
     * accepted. */
    retval = listen(server_sock, BACKLOG);
    if(retval < 0) {
        perror("Error listening for connections");
        exit(1);
    }

    pthread_t *threadInfo;
  
    while(1) {
        /* Declare a socket for the client connection. */
        int * sock = (int*)malloc(sizeof(int));
        //char buffer[256];
      
        printf("reached and sock malloced\n");

        /* Another address structure.  This time, the system will automatically
         * fill it in, when we accept a connection, to tell us where the
         * connection came from. */
        struct sockaddr_in remote_addr;
        unsigned int socklen = sizeof(remote_addr); 

        /* Accept the first waiting connection from the server socket and
         * populate the address information.  The result (sock) is a socket
         * descriptor for the conversation with the newly connected client.  If
         * there are no pending connections in the back log, this function will
         * block indefinitely while waiting for a client connection to be made.
         * */
        (*sock) = accept(server_sock, (struct sockaddr*) &remote_addr, &socklen);
        
        if(sock < 0) {
            perror("Error accepting connection");
            exit(1);
        }
  
         printf("sock connected\n");
        
        /* At this point, you have a connected socket (named sock) that you can
         * use to send() and recv(). */

        /* ALWAYS check the return value of send().  Also, don't hardcode
         * values.  This is just an example.  Do as I say, not as I do, etc. */
        //serve_request(sock);
        
      pthread_create(&threadInfo, NULL, thread, (void*) sock);
        

        /* Tell the OS to clean up the resources associated with that client
         * connection, now that we're done with it. */
        close(sock);
    }

    close(server_sock);
}
