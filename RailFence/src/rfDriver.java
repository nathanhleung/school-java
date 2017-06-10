/**
@author Bobby Steinberg
@date 3/17/2014

This is a driver for a rail-fence cipher.  It is your job to write the 
object that enables this driver to work correctly.
*/
import java.util.Scanner;

public class rfDriver 
{
	public static void main(String[] args)
	{	
		RFcipher.tests();
		
		Scanner sc = new Scanner( System.in);
		System.out.print("Enter a sentence: ");
		String str = sc.nextLine();
		
		//Instantiate the object
		RFcipher cipher = new RFcipher();
		
		////[ Encryption ]/////////////////////	
		String cipherText = cipher.encrypt(str);
		System.out.println("CipherText: \t\t" + cipherText);
		
		
		
		////[ Decryption ]/////////////////////
		String recoveredPlainText = cipher.decrypt( cipherText );
		System.out.println("Recovered Plain Text: \t" + recoveredPlainText);
		
		System.out.println("\n========[ Alternate Constructor and ToString ]==============\n");
		
		System.out.print("Enter a sentence: ");
		String secondString = sc.nextLine();
		
		RFcipher cipher2 = new RFcipher(secondString);
		
		System.out.println(cipher2);  //toString method called
	}
}
