/*
 * App
 * v1.0
 * @author yusupova.alla@gmail.com
 */

package ru.gnkoshelev.kontur.intern;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.nio.file.Paths;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        if (checkArgs(args)) {
            SpringApplication.run(App.class, args);
        }
    }

    private static boolean checkArgs(String[] args) {
        if (args.length != 1) {
            System.out.println("\r\nThere must be one argument (path to file) to start the application.\r\n" +
                    "\t(example: java -jar universal-converter-1.0.0.jar path/to/file.csv)\r\n");
            return false;
        }
        File file = Paths.get(args[0]).toFile();
        if (!file.exists()) {
            System.out.println("\r\nFile Not Found: " + file.getAbsolutePath() + "\r\n" +
                    "Enter an absolute path to the file or a relative path without the '/' or '\\' at the beginning.\r\n");
            return false;
        }
        return true;
    }
}
