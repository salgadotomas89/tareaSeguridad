import java.security.Key;
import java.security.MessageDigest;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
//import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author Tomas Salgado
 */
public class AES {
    
    public static void main(String [] args) throws Exception{
        String entradaTeclado = "";
        String texto="";
        //1. Generar una llave de sesión para encriptar con AES un archivo de texto de largo arbitrario.
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");//creamos un objeto KeyGenerator con el algoritmo AES 
        keyGenerator.init(128);//Inicializamos este generador de claves con tamaño de clave de 128bits.
        Key key = keyGenerator.generateKey();//generamos una clave secreta "key" con el metodo generateKey del objeto  KeyGenerator
        System.out.println("Porfavor ingrese su clave de 16 digitos para encriptar el texto:");
        Scanner entradaEscaner = new Scanner (System.in); //Creación de un objeto Scanner
        entradaTeclado = entradaEscaner.nextLine ();
        while(entradaTeclado.length()!=16){//validamos que la contraseña sea de 16 digitos
            System.out.println("Porfavor ingrese una contraseña de 16 digitos");
            entradaTeclado = entradaEscaner.nextLine ();
        }
        key = new SecretKeySpec(entradaTeclado.getBytes(),0, 16, "AES");
        byte[] bkey=key.getEncoded();
        //pasamos la clave a  string
        String str1 = new String(bkey);
        System.out.println("Contraseña creada correctamente!!\n"+str1);
        System.out.println("Ingrese el texto a encriptar:");
        texto=entradaEscaner.nextLine();
        
        //System.out.println("Texto ingresado correctamente!!\n");
        // Se obtiene un cifrador AES
        Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
        // Se inicializa para encriptacion y se encripta el texto que debemos pasar como bytes
        aes.init(Cipher.ENCRYPT_MODE, key);
        byte[] encriptado = aes.doFinal(texto.getBytes());
        System.out.print("Texto encriptado:");
        for (byte b : encriptado) {
            System.out.print(Integer.toHexString(0xFF & b));
        }
        
        // Se iniciliza el cifrador para desencriptar, con la
         // misma clave y se desencripta. 
         
        aes.init(Cipher.DECRYPT_MODE,key);
        byte[] desencriptado = aes.doFinal(encriptado);
        System.out.println("Texto desencriptado:"+new String(desencriptado));

        
        
        //////////////////////////////////////////////////////////////////////////////////////////
        //generacion del hash del mensaje usando el algoritmo MD5
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(texto.getBytes());//le pasamos nuestro texto al objeto md con el método update(). 
        byte[] digest = md.digest();//el método digest() Nos lo devolverá como un array de bytes
        
        // Se escribe byte a byte en hexadecimal
        for (byte b : digest) {
         System.out.print(Integer.toHexString(0xFF & b));
        }
        System.out.println();

        // ahora lo codificamos en base 64
        //byte[] encoded = Base64.encodeBase64(digest);
        //System.out.println(new String(encoded));
        ////////////////////////////////////////////////////////////////////////////////////////////
        
        
        
    
    }
    
    
}
