package net.aooms.core.test;

import cn.hutool.core.io.watch.Watcher;
import net.aooms.core.util.PasswordHash;
import org.springframework.util.StopWatch;

/**
 * 类描述
 * Created by 风象南(yuboon) on 2018/9/30
 */
public class PasswordTest {

    public static void main(String[] args) {
        try {

            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            for(int i = 0 ;i < 1000; i++){
                System.out.println(PasswordHash.createHash("p\r\nassw0Rd!"));
            }
            stopWatch.stop();
            System.err.println("cost:" + stopWatch.getTotalTimeMillis());

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

