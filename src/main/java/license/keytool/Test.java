package license.keytool;
import java.io.FileInputStream;  
import java.io.FileNotFoundException;  
import java.io.IOException;  
import java.io.InputStream;  
import java.security.InvalidKeyException;  
import java.security.KeyStore;  
import java.security.KeyStoreException;  
import java.security.MessageDigest;  
import java.security.NoSuchAlgorithmException;  
import java.security.PrivateKey;  
import java.security.PublicKey;  
import java.security.SignatureException;  
import java.security.UnrecoverableKeyException;  
import java.security.cert.Certificate;  
import java.security.cert.CertificateException;  
import java.security.cert.CertificateFactory;  
  
import javax.crypto.BadPaddingException;  
import javax.crypto.Cipher;  
import javax.crypto.IllegalBlockSizeException;  
import javax.crypto.NoSuchPaddingException;  



/** 
 * 证书的签密方案<br/> 
 * 1.公钥加密私钥解密<br/> 
 * 2.私钥签名公钥验签<br/> 
 *  
 * @author yuezh2
 * 
 */  
public class Test {  
    /** 密钥库的位置  */  
    private static String keystorePath = "d:\\bls.keystore";  
    /** 证书路径 */  
    private static String certPath = "d:\\server.crt";  
    /** 密钥库的口令 */  
    private static String keystorePass = "thrift";   
    /** 密钥对的口令 */  
    private static String certPass = "thrift";  
    /** 密钥对的别名 */  
    private static String alias = "localhost";  
      
    /** 需要加密的内容 */  
    private static String source = "公钥加密私钥解密";  
    /** 需要签名的内容 */  
    private static String source2 = "私钥签名公钥验签";  
      
    /** 
     * 测试 
     * @param args 
     * @throws NoSuchAlgorithmException 
     * @throws KeyStoreException 
     * @throws CertificateException 
     * @throws IOException 
     * @throws InvalidKeyException 
     * @throws NoSuchPaddingException 
     * @throws IllegalBlockSizeException 
     * @throws BadPaddingException 
     * @throws SignatureException 
     */  
    public static void main(String[] args) throws NoSuchAlgorithmException, KeyStoreException,   
            CertificateException, IOException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException,   
            BadPaddingException, SignatureException {  
          
        KeyStore keyStore = loadKeyStore(keystorePath, keystorePass); // 密钥库  
        PrivateKey privateKey = getPrivateKey(keyStore, alias, certPass); // 私钥  
        PublicKey publicKey_server = getPublicKey(keyStore.getCertificate(alias)); // 公钥：服务端的公钥是通过KeyStore获得  
        PublicKey publicKey_client = getPublicKey(loadCertificate()); // 公钥：客户端的公钥是通过证书获得  
          
        System.out.println("==========|客户端向服务器请求：客户端使用公钥加密|===========");  
        System.out.println("原始数据为：" + source);  
        byte[] encrypted = asymmertricEncrypt("RSA", source.getBytes("UTF-8"),publicKey_client);  
        System.out.println("加密算法（私钥）：" + privateKey.getAlgorithm());  
        System.out.println("私钥：" + privateKey.getEncoded());  
        System.out.println("加密后的数据（十六进制）：" + byte2Hex(encrypted));  
        System.out.println("==========|客户端向服务器请求：服务端使用私钥解密|===========");  
        byte[] decrypted = asymmetricDecrypt("RSA", encrypted, privateKey);  
        System.out.println("解密算法（公钥）：" + publicKey_server.getAlgorithm());  
        System.out.println("公钥：" + publicKey_server.getEncoded());  
        System.out.println("解密后数据（字节）：" + byte2Hex(decrypted));  
        System.out.println("解密后的数据（明文）：" + new String(decrypted, "UTF-8"));  
          
        System.out.println("\n");  
          
        System.out.println("==========|服务器向客户端发送数据：服务端手动签名|===========");  
        System.out.println("签名plain：" + source2);  
        byte[] digestText = digest(source2);  
        System.out.println("信息摘要：" + byte2Hex(digestText));  
        byte[] sign1 = asymmetricEncrypt("RSA", digestText, privateKey);  
        System.out.println("数字签名：" + byte2Hex(sign1));  
        System.out.println("==========|服务器向客户端发送数据：客户端手动验签|===========");  
        byte[] digest1 = asymmetricDecrypt("RSA", sign1, publicKey_client);  
        System.out.println("解密签名得到的摘要：" + byte2Hex(digest1));  
        System.out.println("手动验签结果：" + byte2Hex(digest1).equals(byte2Hex(digestText)));  
    }     
      
    /** 
     * 直接从文件加载证书 
     * @return java.security.cert.Certificate 
     * @throws FileNotFoundException 
     * @throws CertificateException 
     */  
    public static Certificate loadCertificate() throws FileNotFoundException, CertificateException {  
        // 证书格式为x509  
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");  
        // 读取证书文件的输入流  
        InputStream in = new FileInputStream(certPath);  
        Certificate certificate = certificateFactory.generateCertificate(in);  
        return certificate;  
    }  
      
    /** 
     * 加载密钥库 
     *  
     * @param keystorePath is java.lang.String keystorePath 密钥库路径 
     * @param keystorePass is java.lang.String keystorePass 密钥库密码 
     * @return java.security.KeyStore 
     *  
     * @throws KeyStoreException 
     * @throws NoSuchAlgorithmException 
     * @throws CertificateException 
     * @throws IOException 
     *  
     * @author 张爽 
     * @date 2016/08/07 
     */  
    public static KeyStore loadKeyStore(String keystorePath, String keystorePass) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {  
        // 提供密钥库类型  
        KeyStore keyStore = KeyStore.getInstance("JKS");  
        // 读取keystore文件的输入流  
        InputStream in = new FileInputStream(keystorePath);  
        keyStore.load(in, keystorePass.toCharArray());  
        return keyStore;  
    }  
      
    /** 
     * 获得公钥，可以从证书中获得，也可以从keystore中获得 
     * @param certificate is java.security.cert.Certificate 证书 
     * @return java.security.PublicKey 
     *  
     * @author 张爽 
     * @date 2016/08/07 
     */  
    public static PublicKey getPublicKey(Certificate certificate) {  
        return certificate.getPublicKey();  
    }  
      
    /** 
     * 通过密钥库，密钥对的别名，密钥对的密码获得密钥对中的私钥 
     *  
     * @param keyStore is java.security.KeyStore keyStore 密钥库 
     * @param alias is java.lang.String alias 密钥对的别名 
     * @param certpass is java.lang.String certpass 密钥对的口令 
     * @return java.security.PrivateKey  
     *  
     * @author 张爽 
     * @date 2016/08/07 
     */  
    public static PrivateKey getPrivateKey(KeyStore keyStore, String alias, String certpass) {  
        try {  
            return (PrivateKey) keyStore.getKey(alias, certpass.toCharArray());  
        } catch (UnrecoverableKeyException | KeyStoreException  
                | NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
      
    /** 
     * 使用公钥 进行 非对称加密数据    
     * @param transformation is java.lang.String 加密算法 RSA/ECB/PCKCS1Padding 
     * @param plainText is byte[] 加密资源的字节码数组 
     * @param key is java.security.PublicKey 证书中的公钥 
     * @return byte[] 
     * @throws NoSuchAlgorithmException 
     * @throws NoSuchPaddingException 
     * @throws InvalidKeyException 
     * @throws IllegalBlockSizeException 
     * @throws BadPaddingException 
     *   
     * @author 张爽 
     * @date 2016/08/07 
     */  
    public static byte[] asymmertricEncrypt(String transformation, byte[] plainText, PublicKey key) throws  
            NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,   
            IllegalBlockSizeException, BadPaddingException {  
        Cipher cipher = Cipher.getInstance(transformation);  
        cipher.init(Cipher.ENCRYPT_MODE, key);  
        cipher.update(plainText);  
        return cipher.doFinal();  
    }  
      
    /** 
     * 使用私钥 进行 非对称加密数据    签名时使用 
     * @param transformation is java.lang.String 加密算法 RSA/ECB/PCKCS1Padding 
     * @param plainText is byte[] 加密资源的字节码数组 
     * @param key java.security.PrivateKey 密钥对的私钥 
     * @return 
     * @throws NoSuchAlgorithmException 
     * @throws NoSuchPaddingException 
     * @throws InvalidKeyException 
     * @throws IllegalBlockSizeException 
     * @throws BadPaddingException 
     *  
     * @author 张爽 
     * @date 2016/08/07 
     */  
    public static byte[] asymmetricEncrypt(String transformation, byte[] plainText, PrivateKey key) throws   
            NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,   
            IllegalBlockSizeException, BadPaddingException {  
        Cipher cipher = Cipher.getInstance(transformation);  
        cipher.init(Cipher.ENCRYPT_MODE, key);  
        cipher.update(plainText);  
        return cipher.doFinal();  
    }  
  
    /** 
     * 使用私钥 进行 非对称解密数据 
     *  
     * @param transformation is java.lang.String 解密算法 
     * @param cipherText is byte[] 需要解密的数据，要字节数组 
     * @param key is java.security.PublicKey 公钥 
     * @return byte[] 
     * @throws NoSuchAlgorithmException 
     * @throws NoSuchPaddingException 
     * @throws InvalidKeyException 
     * @throws IllegalBlockSizeException 
     * @throws BadPaddingException 
     */  
    public static byte[] asymmetricDecrypt(String transformation, byte[] cipherText, PrivateKey key) throws  
            NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,   
            IllegalBlockSizeException, BadPaddingException {  
        Cipher cipher = Cipher.getInstance(transformation);  
        cipher.init(Cipher.DECRYPT_MODE, key);  
        cipher.update(cipherText);  
        return cipher.doFinal();  
    }  
      
    /** 
     * 字节数组转十六进制 
     *   
     * @param b is byte[]  
     * @return java.lang.String 
     *  
     * @author 张爽 
     * @date 2016/08/07 
     */  
    public static String byte2Hex(byte[] b) {  
        StringBuilder sb = new StringBuilder();  
        for (int i = 0; i < b.length; i++) {  
            String hex = Integer.toHexString(0x00ff & b [i]);  
            if (hex.length() < 2) {  
                sb.append(0);  
            }  
            sb.append(hex);  
        }  
        return sb.toString();  
    }  
      
    /** 
     * 十六进制转字节数组 
     *  
     * @param hex is java.lang.String 
     * @return byte[] 
     *  
     * @author 张爽 
     * @date 2016/08/07 
     */  
    public static byte[] hex2Byte(String hex) {  
        byte[] bytes = new byte[hex.length() / 2];  
        for (int i = 0; i * 2 < hex.length(); i++) {  
            bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);  
        }  
        return bytes;  
    }  
      
    /** 
     * 信息摘要 
     * @param source 需要签名的内容 
     * @return 
     * @throws NoSuchAlgorithmException 
     *  
     * @author 张爽 
     * @date 2016/08/07 
     */  
    public static byte[] digest(String source) throws NoSuchAlgorithmException {  
        MessageDigest md = MessageDigest.getInstance("SHA1");  
        md.update(source.getBytes());  
        return md.digest();  
    }  
  
    /** 
     * 签名时使用的非对称解密  
     * @param transformation 
     * @param cipherText 
     * @param key 
     * @return 
     * @throws NoSuchAlgorithmException 
     * @throws NoSuchPaddingException 
     * @throws InvalidKeyException 
     * @throws IllegalBlockSizeException 
     * @throws BadPaddingException 
     *  
     * @author 张爽 
     * @date 2016/08/07 
     */  
    public static byte[] asymmetricDecrypt(String transformation, byte[] cipherText, PublicKey key) throws   
            NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,   
            IllegalBlockSizeException, BadPaddingException {  
        Cipher cipher = Cipher.getInstance(transformation);  
        cipher.init(Cipher.DECRYPT_MODE, key);  
        cipher.update(cipherText);  
        return cipher.doFinal();  
    }  
      
}  