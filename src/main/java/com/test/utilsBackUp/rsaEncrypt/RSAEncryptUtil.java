package com.test.utilsBackUp.rsaEncrypt;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;


public class RSAEncryptUtil {

    static  String publicKeyString = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCLSytDwFEOR8zZT96EGpsgC6Nxvx4Lm55dI8umjjP21rUZUdn9ALdAEyNXxF6bA5NGJAwPNE95PqZjRQfFyfof/JwucojipzQZldRG+dXV/qdyqiGVp8CTRBZGo1Meaij51kcWXUcmLtbGgU0/ga5ELdEQKVU/DSFY805ZYBbshQIDAQAB";

    /**
     * 根据publicKey获取PublicKey对象
     * @param key
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(key);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(spec);

        return publicKey;
    }

    /**
     * 依据文档说明,需要在加密前对信息进行Base64编码
     * @param publicKeyString
     * @param content
     * @return
     * @throws Exception
     */
    public static String encrypt(String publicKeyString,String content) throws Exception{
        BASE64Encoder encoder = new BASE64Encoder();
        String encodeContent = encoder.encode(content.getBytes());
        Cipher cipher = Cipher.getInstance("RSA");
        PublicKey publicKey = getPublicKey(publicKeyString);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] enBytes = cipher.doFinal(encodeContent.getBytes());
        return Hex.encodeHexString(enBytes);
    }

    public static void main(String[] args) {
        try {
            System.out.println(RSAEncryptUtil.encrypt(RSAEncryptUtil.publicKeyString, "abcdefg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}