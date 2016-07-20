/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package macencrypter;


import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class MacFile {
    private String filename;
    public ArrayList<String> MacAddresses;

    byte[] key = {115, 35, -69, -112, 54, 114, 122, 74, 113, -2, 64, -37, -100, -112, 120, 107};
    private Cipher desCipher;
    private SecretKey myDesKey;
	
    public MacFile(String filename) {
        MacAddresses = new ArrayList<String>();
        this.filename = filename;

        myDesKey = new SecretKeySpec(key, 0, key.length, "AES");
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());  
        try
        {
            try {
                desCipher = Cipher.getInstance("AES/ECB/PKCS5Padding", "BC");
            } catch (NoSuchProviderException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException e)
        {
            e.printStackTrace();
        }
    }

    public byte[] load() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            String everything = sb.toString();

            String[] parts = everything.split(",");
            byte[] result = new byte[parts.length];

            for(int x = 0; x < parts.length; x++) {
                result[x] = Byte.parseByte(parts[x]);
            }

            return result;
        } finally {
            br.close();
        }
    }

    public void save(byte[] savebytes) {
        try{ 
            PrintWriter writer = new PrintWriter(filename, "UTF-8");

            String saveString = "";

            for(int x = 0; x < savebytes.length - 1; x++) {
                    saveString += savebytes[x] + ",";
            }

            writer.println(saveString + savebytes[savebytes.length - 1]);

            writer.close(); 
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
    }

    public byte[] encrypt(String input)
    {
        byte[] textEncrypted = null;
        try
        {
            desCipher.init(Cipher.ENCRYPT_MODE, myDesKey);
            textEncrypted = desCipher.doFinal(input.getBytes());
        }
        catch(InvalidKeyException | IllegalBlockSizeException | BadPaddingException e)
        {
            e.printStackTrace();
        }
        return textEncrypted;
    }

    public byte[] decrypt(byte[] textEncrypted) throws IllegalBlockSizeException, BadPaddingException
    {
        try
        {
            desCipher.init(Cipher.DECRYPT_MODE, myDesKey);
        }
        catch (InvalidKeyException e)
        {
            e.printStackTrace();
        }
        return desCipher.doFinal(textEncrypted);
    }
}
