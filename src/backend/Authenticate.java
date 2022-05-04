package backend;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Authenticate {

    public static Admin LoginAuthenticate(String username, String passwordHash) {
        DatabaseConnection conn = new DatabaseConnection();
        Admin result = conn.CheckLogin(username, passwordHash);
        return result;

    }

}
