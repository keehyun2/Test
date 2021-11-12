package linx;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class Password {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        String raw = "1111";

        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        byte[] bytes = new byte[16];
        random.nextBytes(bytes);
        String salt = new String(Base64.getEncoder().encode(bytes));
        salt = "5974Se8KOODOAnLiqkWD/Q==";

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt.getBytes());
        md.update(raw.getBytes());
        String hex = String.format("%064x", new BigInteger(1, md.digest()));
        
        System.out.println(salt);
        System.out.println(hex);
    }
}
