package util;

import org.apache.commons.codec.digest.DigestUtils;

public final class HashUtils {

    public static String CriptografaSenha(String senha){
        String md5Hex = DigestUtils
                .md5Hex(senha).toLowerCase();
        return md5Hex;
    }
}
