/*
 * Uses argon2 hashing with password4j. 
 */
package Utilities;

import com.password4j.Hash;
import com.password4j.Password;
/**
 * The password utility class
 * @author Rylan Cook
 */
public class PasswordUtil {
    /**
     * Takes in a plain text password and returns a password4j hashed version. This hash can be checked against password4j to see if it is the same.
     * @param password The password to hash
     * @return The password4j storage hash to put in the db
     */
    public static String hashPassword(String password) {
        Hash hash = Password.hash((CharSequence)password).addRandomSalt(42).withArgon2();//Wraps string as charsequence
	return hash.getResult(); //Needs to be the whole string or else it doesn't work
    }
    /**
     * Checks if a enter password is the same as a hash from, the database
     * @param password The plain text password
     * @param hashed The hash in the database
     * @return A Boolean if the passwords match
     */
    public static Boolean checkPasswordAgainstHashed(String password,String hashed) {
        return Password.check((CharSequence)password, hashed).withArgon2(); //String is wrapped as a charsequence to comply
    }
}
