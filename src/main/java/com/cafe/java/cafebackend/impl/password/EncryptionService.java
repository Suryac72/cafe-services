package com.cafe.java.cafebackend.impl.password;

import com.itextpdf.text.pdf.codec.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;



@Service
public class EncryptionService {

    @Autowired
    private AlgorithmParameterSpec makeIv;

    @Autowired
    private Key makeKey;

    public  String encrypt(String src) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, makeKey, makeIv);
            return Base64.encodeBytes(cipher.doFinal(src.getBytes()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public  String decrypt(String src) {
        String decrypted = "";
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, makeKey, makeIv);
            decrypted = new String(cipher.doFinal(Base64.decode(src)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return decrypted;
    }

}
