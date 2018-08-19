package br.com.gastronomia.util;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         --
 */

public class SimpleKeyGenerator implements KeyGenerator {

    // ======================================
    // =          Business methods          =
    // ======================================

    public static Key generateKey() {
        String keyString = "simplekey";
        Key key = new SecretKeySpec(keyString.getBytes(), 0, keyString.getBytes().length, "DES");
        return key;
    }
}