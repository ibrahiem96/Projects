import java.util.*;
import java.io.*;

public class LongInt {
	private int[] numbers; // store the integer
	private int size; // size of the interger

	public LongInt(int number) {

		if (number == 0) { // check if the number is 0
			size = 1; // if it is add oe to the size
			numbers = new int[1];
			numbers[0] = 0; // store 0 in the array
			return;
		} else {
			int count = 0; // to count number of digits
			int numberTocount = number; // count digits in this number
			int i = number;
			for (i = 0; number > 0; i++) // peform till the least significant
											// digit
			{
				count++; // increment the counter
				number = number / 10; // and check how big the number is by
										// stripping it's least significant
										// number
			}

			size = count; // set the size of the number to the count
			numbers = new int[size]; // Allocate space for the numbers

			// strip the number and store the number to the numbers array
			int j;
			for (j = 0; j < size; j++) { // go through the number
				numbers[j] = numberTocount % 10; // and store each number into
													// the numbers array
				numberTocount = numberTocount / 10; // move on to the next
													// number by stripping the
													// number each time by it's
													// significant number
			}
		}
	}

	// Make a constructor that cannot have leading 0, unless it is a 0
	public LongInt(String s) {
		size = s.length(); // get the length of the string
		numbers = new int[size]; // allocate size for the integer
		int i;
		for (i = 0; i < size; i++) { // go through the the end of the number
			int setnumber = s.charAt(size - 1 - i) - '0'; // get the number in
															// form of string
			numbers[i] = setnumber; // store the number in the array
		}
	}

	// constructor that takes an array of integer
	public LongInt(int[] num) {
		// check if the array id full of 0's
		int i;
		int count = 0; // count the number of '0'
		size = num.length; // get the size of the array
		for (i = 0; i < num.length; i++) { // got through the array
			if (num[i] == 0) // check if the number is 0
				count++; // if it is then increment
		}
		if (count == size) { // if the count is equal to the size i.e. all the
								// numbers are 0
			size = 1; // set the size to 1
			numbers = new int[1];
			numbers[0] = 0; // set the number to 0
		} else {
			while (num[size - 1] == 0)
				// strip if the leading 0 and decrement the size
				size--; // decrement the size to adjust the number

			numbers = new int[size]; // allocate the memory for the array
			int z = 0;
			for (z = 0; z < size; z++) { // go through the number again
				// numbers = new int[1];
				numbers[z] = num[z]; // copy the rest of the digits as it is
			}
		}
	}

	public LongInt add(int i) {
		return this.add(new LongInt(i));
	}

	public LongInt add(LongInt numberAdded) {

		int newsize = 0;
		if (numberAdded.size > size) {
			newsize = numberAdded.size + 1;
		} else {
			newsize = size + 1;
		}

		int[] tempNumbers = new int[newsize];

		int result = 0;
		int carryOver = 0;
		int y;
		for (y = 0; y < newsize - 1; y++) {
			int temp = 0;
			if (y < size) {
				temp += numbers[y];
			}

			if (y < numberAdded.size) {
				temp += numberAdded.numbers[y];
			}

			temp += carryOver;

			tempNumbers[y] = temp % 10;
			carryOver = temp / 10;

		}

		if (carryOver == 1)
			tempNumbers[newsize - 1] = 1;

		else
			tempNumbers[newsize - 1] = 0;

		return new LongInt(tempNumbers);

	}

	public LongInt sub(int i) {
		return this.sub(new LongInt(i));
	}

	public LongInt sub(LongInt other) {
		int[] tempdigits = new int[size];

		int result = 0, carry = 0;

		int i = 0;
		while (i < size) {
			int temp = numbers[i];
			if (i < other.size)
				temp -= other.numbers[i];

			temp -= carry;
			carry = 0;

			if (temp < 0) {
				temp = temp + 10;
				carry = 1;
			}

			tempdigits[i] = temp;
			i++;
		}

		return new LongInt(tempdigits);
	}

	public LongInt mup(int i) {
		return this.mup(new LongInt(i));
	}

	public LongInt mup(LongInt other) {
		int[] ans = new int[size + other.size];

		for (int i = 0; i < other.size; i++) {
			int carry = 0;
			for (int j = 0; j < size; j++) {

				ans[j + i] += carry + (numbers[j] * other.numbers[i]);
				carry = ans[j + i] / 10;
				ans[j + i] = ans[j + i] % 10;
			}
			ans[i + size] += carry;
		}

		return new LongInt(ans);

	}

	public boolean isGreater(LongInt other) {
		if (size > other.size) {
			return true;
		} else if (size < other.size) {
			return false;
		} else {
			for (int i = size - 1; i >= 0; i--) {
				if (numbers[i] < other.numbers[i]) {
					return false;
				} else if (numbers[i] > other.numbers[i]) {
					return true;
				}
			}
			return false;
			// this means they r equal
		}

	}

	public boolean isEqual(LongInt other) {
		if (size != other.size) {
			return false;
		} else {
			for (int i = size-1; i >= 0; i--) {
				if (numbers[i] != other.numbers[i]) {
					return false;
				}
			}
			return true;
		}
	}

	public boolean isLessthan(LongInt other) {
		return !this.isGreater(other) && !this.isGreater(other);
	}

	public boolean isLessthanEqual(LongInt other) {
		return !this.isGreater(other);
	}

	public boolean isGreaterthanEqual(LongInt other) {
		return this.isGreater(other) || this.isGreater(other);
	}


	LongInt remainder = null; 
	public LongInt div(LongInt other){
		LongInt count = new LongInt(0);
		LongInt dividend = this;
		
		while(dividend.isGreaterthanEqual(other) || dividend.isEqual(other)){
			dividend = dividend.sub(other);
			count = count.add(new LongInt(1));
		}
		if(!dividend.isGreaterthanEqual(other)){
			remainder = dividend;
		}
		return count;
	}
	
	
	public LongInt pow (LongInt other){
		LongInt result = new LongInt(0);
		LongInt num = this;
		LongInt one = new LongInt(1);
		
		LongInt product = this; 
		LongInt zero = new LongInt(0);
		LongInt n1 = new LongInt(1);
		while(other.isGreater(n1)){
			product = product.mup(num);
			other = other.sub(one);
		}
		return product; 
	}

	public LongInt mod(LongInt other) {
		LongInt count = new LongInt(0);
		LongInt dividend = this;
		
		while(dividend.isGreaterthanEqual(other) || dividend.isEqual(other)){
			dividend = dividend.sub(other);
			count = count.add(new LongInt(1));
		}
		if(!dividend.isGreaterthanEqual(other)){
			remainder = dividend;
		}
		return remainder;
	}

	public LongInt powMod(LongInt other, LongInt base) {
		
		LongInt base1 =  base.mup(10);
		LongInt result = new LongInt(0);
		LongInt num = this;
		LongInt one = new LongInt(1);
		
		LongInt product = this; 
		LongInt zero = new LongInt(0);
		LongInt n1 = new LongInt(1);
		LongInt result1 = new LongInt(0);
		while(other.isGreater(n1)){
			product = product.mup(num).mod(base1);
			other = other.sub(one); //.mod(base); // .mod(base);
		}
		result1 = product;
		return result1; 
	}

	// Returns a string with the digits in the normal order.
	public String toString() {
		String ans = "";

		int length = size;

		if (length > 1) {
			while (numbers[length - 1] == 0 && length > 1)
				length--;
		}
		while (length != 0) {
			ans += numbers[length - 1];
			length--;
		}
		return ans;
	}

	// Returns the equivalent String object to c.
	public static String charToString(char c) {
		char[] temp = new char[1];
		temp[0] = c;
		return new String(temp);
	}

	public static LongInt encrypt(LongInt msg, LongInt eOrd, LongInt pq) {
		return msg.powMod(eOrd, pq);
	}

	public static LongInt[] breakStr(String x, int chunk){
		int numArrays = x.length()/chunk;
		LongInt [] ret = new LongInt[numArrays+1];
		
		for (int i=0; i<numArrays+1; i++){
			ret[i] = new LongInt("0000000000000000");
		}
		
		int stIndex=0;
		for (int j=0; j<numArrays+1; j++){
			for (int i=15; i>0; i-=2){
				if(stIndex == x.length()) break;
				char t = x.charAt(stIndex);
				int temp3 = (int) t;
				//System.out.println(temp3);
				if(temp3 == 0 ){
					ret[j].numbers[i] = 0;
				}
				else if(temp3 == 11){
					ret[j].numbers[i] = 1;
				}
				else if(temp3 == 9){
					ret[j].numbers[i] = 3;
				}
				else if(temp3 == 10){
					ret[j].numbers[i] = 4;
				}
				else if(temp3 == 13){
					ret[j].numbers[i] = 5;
				}
				else{
				int temp = (int) t-27;
				String temp2 ="";
				temp2 +=temp;	
				
				//System.out.println(temp);
				
				ret[j].numbers[i-1] = temp2.charAt(0)-'0';
				if(temp2.length()==1){
					ret[j].numbers[i] = 0;
					ret[j].numbers[i-1] = temp2.charAt(0)-'0';
				}
				else{
					ret[j].numbers[i] = temp2.charAt(1)-'0';
				}
				stIndex++;
			}
			}
			for (int g =0; g < ret[j].size; g++){
				System.out.print(ret[j].numbers[g]);				
			}
			System.out.println();
		}	
		return ret;
	}
	
	LongInt modInverse (LongInt m){
	    LongInt a = this;
		a = a.mod(m);
	    for (LongInt x = new LongInt(1); x.isLessthan(m); x = x.add(1))
	       if ((a.mup(x)).mod(m).equals(1))
	          return x;
	
	return new LongInt(0);
	}

	
	public static LongInt[] ToChar(String x, int chunk){
		int numArrays = x.length()/chunk;
		LongInt [] ret = new LongInt[numArrays+1];
		
		for (int i=0; i<numArrays+1; i++){
			ret[i] = new LongInt("0000000000000000");
		}
		
		int stIndex=0;
		for (int j=0; j<numArrays+1; j++){
			for (int i=15; i>0; i-=2){
				if(stIndex == x.length()) break;
				char t = x.charAt(stIndex);
				//System.out.println(temp3);
				if(t== '\n' ){
					ret[j].numbers[i] = 10;
				}
				else if(t== '\t'){
					ret[j].numbers[i] = 9;
				}
				else if(t == '\r'){
					ret[j].numbers[i] = 13;
				}
				else if(t == '\0'){
					ret[j].numbers[i] = 0;
				}
				else if(t == (char)11){
					ret[j].numbers[i] = 11;
				}
				else{
				int temp = (int) t-27;
				String temp2 ="";
				temp2 +=temp;	
				
				//System.out.println(temp);
				
				ret[j].numbers[i-1] = temp2.charAt(0)-'0';
				if(temp2.length()==1){
					ret[j].numbers[i] = 0;
					ret[j].numbers[i-1] = temp2.charAt(0)-'0';
				}
				else{
					ret[j].numbers[i] = temp2.charAt(1)-'0';
				}
				stIndex++;
			}
			}
			for (int g =0; g < ret[j].size; g++){
				System.out.print(ret[j].numbers[g]);				
			}
			System.out.println();
		}	
		return ret;
	}
	
	
	
	public static void main(String[] args) throws IOException {
		int numError = 0;

		int i;
		int j;

		LongInt str1 = new LongInt("2547");
		int i1 = 512;
		LongInt str2 = new LongInt("4489");
		int j1 = 12;
		
		String s = "Meet me outside SCE at 10pm.\n";
		
		System.out.println(str1);
		System.out.println(str2);

		LongInt add = str1.add(str2);
		System.out.println(str1 + " + " + str2 + " = " + add);

		LongInt sub = str1.sub(str2);
		System.out.println(str1 + " - " + str2 + " = " + sub);

		LongInt mup = str1.mup(str2);
		System.out.println(str1 + " * " + str2 + " = " + mup);

		LongInt div = str1.div(str2);
		System.out.println(str1 + " / " + str2 + " = " + div);

		LongInt mod = str1.mod(str2);
		System.out.println(str1 + " % " + str2 + " = " + mod);

		LongInt pow = str1.pow(str2);
		System.out.println(str1 + " ^ " + str2 + " = " + pow);

		LongInt powMod = str1.powMod(str2, new LongInt(1000000));
		System.out.println(str1 + " powMod " + str2 + " = " + powMod);
		
		System.out.println();
		LongInt[] brk = breakStr(s, 8 );
		System.out.println();
		
		//createUnBlockedFile(test.txt);

		System.out.println("Noice!! You can Smoke now!!");

	}

}
