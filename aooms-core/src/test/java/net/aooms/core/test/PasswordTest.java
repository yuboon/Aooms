package net.aooms.core.test;

import net.aooms.core.util.PasswordHash;

/**
 * 类描述
 * Created by 风象南(cheereebo) on 2018/9/30
 */
public class PasswordTest {

    public static void main(String[] args) {
        try {
            // Print out 10 hashes
            for (int i = 0; i < 10; i++)
                System.out.println(PasswordHash.createHash("p\r\nassw0Rd!"));
            // Test password validation
            boolean failure = false;
            System.out.println("Running tests...");
            for (int i = 0; i < 100; i++) {
                String password = "" + i;
                String hash = PasswordHash.createHash(password);
                String secondHash = PasswordHash.createHash(password);
                if (hash.equals(secondHash)) {
                    System.out.println("FAILURE: TWO HASHES ARE EQUAL!");
                    failure = true;
                }
                String wrongPassword = "" + (i + 1);
                if (PasswordHash.validatePassword(wrongPassword, hash)) {
                    System.out.println("FAILURE: WRONG PASSWORD ACCEPTED!");
                    failure = true;
                }
                if (!PasswordHash.validatePassword(password, hash)) {
                    System.out.println("FAILURE: GOOD PASSWORD NOT ACCEPTED!");
                    failure = true;
                }
            }
            if (failure)
                System.out.println("TESTS FAILED!");
            else
                System.out.println("TESTS PASSED!");
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex);
        }
    }

}

