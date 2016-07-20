/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package macencrypter;

import java.io.IOException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public class MacEncrypter {

	public static void main(String[] args) {
		MacFile mac = new MacFile("addresses.txt");
		
		String unecryptedMacs = "ab:cd:ef:gh:ij:kl" + ","
								+"12:34:56:78:90:98";
		
		byte[] encryptedMac = mac.encrypt(unecryptedMacs);
		
		mac.save(encryptedMac);
		

		try {
			encryptedMac = mac.load();
			byte[] byteUnecrypted = mac.decrypt(encryptedMac);
			System.out.println(new String(byteUnecrypted));
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}