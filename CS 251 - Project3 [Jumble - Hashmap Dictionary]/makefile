hmap.o: hmap.c hmap.h
	gcc -c hmap.c

jumble: jumble.c hmap.o
	gcc -o jumble jumble.c hmap.c -I.

clean:
	rm -rf *.o test test2
