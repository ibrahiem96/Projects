import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import javax.swing.JOptionPane;

public class KeyGenerator extends RSA_GUI {

	//private data members
	private String publicKey = null, privateKey = null;
	private LongInt pFromUser, qFromUser, pFromFile, qFromFile;
	private LongInt n, phi, dTemp, e, d;
	
	private String[] bigA = new String[20];
	private String prime1;
	private String prime2;

	/**
	 * default constructor
	 * @throws IOException 
	 */
	public KeyGenerator() throws IOException{
		assignPrime();		

		defaultFileNames();
		genKeys();
		makeKeyFiles();

	}
	
	public KeyGenerator(String msg) throws IOException{
		
		System.out.println(LongInt.encrypt(new LongInt(msg), e, n).toString());
		
		
	}
	
	public KeyGenerator(String msg, int option) throws IOException{
		
		System.out.println(LongInt.encrypt(new LongInt(msg), d, n).toString());
		
	}

	void assignPrime() throws IOException{

		/*if (super.choice == 1)
			userSelectPrimes();
		else if (super.choice == 2)*/
		selectTwoPrimes();


		pFromFile = new LongInt(prime1);
		qFromFile = new LongInt(prime2);
	}

	boolean isValidPrime(LongInt l){
		
		System.out.println("check1");
		
		//test1: checking if larger than 0
		if (l.isLessthanEqual(new LongInt(0))) {
			System.out.println("DEBUG: not a prime -- less than 0");
			return false;
		}
		
		//test2: checking if even
		System.out.println("check2");
		System.out.println(l.mod(new LongInt(2)).toString());
		if (l.mod(new LongInt(2)).isEqual(new LongInt(0))) {
			System.out.println("DEBUG: not a prime -- div by 2");
			return false;
		}
		//test3: checking if less than 8 and non even
		System.out.println("check3");
		if (l.isLessthan(new LongInt(8))) {
			System.out.println("DEBUG: is a prime -- smaller than 8");
			return true;
		}

		for (LongInt d = new LongInt(3);  d.isLessthan(l.div(new LongInt(3))); l.add(2)){
			
			//System.out.println("entered for loop");
			
			if ((l.mod(d)).isEqual(new LongInt(0))) {
				System.out.println("DEBUG: is not a prime -- by factor");
				return false;
			}
		}

		System.out.println("DEBUG: is a prime");
		return true;

	}

	/**
	 * Don't need this unless we are using the user given input
	 */
	
	/*public LongInt findPrime(LongInt l){

		//make odd if long int is even
		if(l.mod(new LongInt(2)) == new LongInt(0)) 
			l = l.add(1);
		//loop until prime number is found
		while(!isValidPrime(l)){
			l = l.add(2);
		}
		return l;
	}*/

	private void defaultFileNames(){

		if (publicKey == null) {
			publicKey = "pubkey.xml";
			privateKey = "privkey.xml";		
		}

		System.out.println("DEBUG: Using default names");
	}


	private void genKeys(){

		n = pFromFile.mup(qFromFile);

		System.out.println("n: "+n);
		
		System.out.println("DEBUG: n obtained");

		LongInt pTemp = pFromFile.sub(1);
		LongInt qTemp = qFromFile.sub(1);

		phi = pTemp.mup(qTemp);

		LongInt new_e;
		
		for (new_e = n.sub(1); new_e.isGreater(new LongInt(0)); new_e = new_e.sub(1))
            if (gcdTest(new_e, phi).isEqual(new LongInt(1)))
            {
                e = new_e;
                break;
            }

		System.out.println("e done");
		
		System.out.println("e: " + e.toString());

		int k = 1;
		
		this.d = e.modInverse(phi);
		
		System.out.println("DEBUG: generated keys succesfully");

//start at 1 with k
//1+k(phi) mod e | if that equals 0 then found and divide by e
//
		
	}
	
	

	public LongInt gcdTest(LongInt a, LongInt b) {
        LongInt p = a;
        
        while (!b.isEqual(new LongInt(0))) {
            LongInt t = b;
            b = p.mod(b);
            p = t;
        }
        return p;
    }
	
	private void makeKeyFiles() throws IOException{

		FileWriter pubKey = new FileWriter(publicKey);
		pubKey.write("<rsakey>\r\n" +
				"\t<evalue>"+e.toString()+"</evalue>\r\n" +
				"\t<nvalue>"+n.toString()+"</nvalue>\r\n" +
				"</rsakey>\r\n");
		pubKey.close();

		FileWriter privKey = new FileWriter(privateKey);
		privKey.write("<rsakey>\r\n" +
				"\t<dvalue>"+d.toString()+"</evalue>\r\n" +
				"\t<nvalue>"+n.toString()+"</nvalue>\r\n" +
				"</rsakey>\r\n");
		privKey.close();

	}

	public void readFile (File f) throws IOException {
		FileInputStream fs = new FileInputStream(f);

		BufferedReader br = new BufferedReader(new InputStreamReader(fs));

		String line = null;
		int index = 0;
		while ((line = br.readLine()) != null){
			bigA[index] = line;
			index++;
		}
		br.close();
	}

	public void selectTwoPrimes() throws IOException{

		readFile(new File("primes.txt"));

		int r[] = new Random().ints(0, bigA.length).distinct().limit(2).toArray();
		this.prime1 = bigA[r[0]];
		this.prime2 = bigA[r[1]];

		System.out.println("DEBUG: primes are " + this.prime1 + " " + this.prime2);
	}
	
	LongInt get_d(){
		return this.d;
	}
	
	LongInt get_n(){
		return this.n;
	}

	/*public void userSelectPrimes(){
		//TODO: add user input info
	}*/






}
