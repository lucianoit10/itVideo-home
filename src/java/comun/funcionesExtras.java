package comun;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class funcionesExtras {
    
  
  /**
   * Metodo utizado para sacar los enter de mas en una pagina.
   * @param txt
   * @return String
   */
  public static String sacarEnter (String txt){
      String dato = txt;
      dato =dato.replace("<br>", "\n");
      dato =dato.replace("\"", "*");
      dato =dato.replace("<Br>", "\n");
      dato =dato.replace("<bR>", "\n");
      dato =dato.replace("<BR>", "\n");
      String aux = "";      
      for (int i = 0; i < dato.length(); i++) {
          if (dato.charAt(i)=='\n'||dato.charAt(i)=='\r'){
              aux+="\\n";
          } else{
              aux+=dato.charAt(i);              
          }
      }
      return aux;
  }

  
  public static String getMD5(String input) {
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      byte[] messageDigest = md.digest(input.getBytes());
      BigInteger number = new BigInteger(1, messageDigest);
      String hashtext = number.toString(16);
      while (hashtext.length() < 32) {
          hashtext = "0" + hashtext;
      }
      return hashtext;
    }
    catch (NoSuchAlgorithmException e) {
        throw new RuntimeException(e);
    }
  }
  
}
