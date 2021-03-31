/*
 * App
 * v1.0
 * @author yusupova.alla@gmail.com
 */

package ru.gnkoshelev.kontur.intern;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
        return isFileExist(args[0]);
    }

    private static boolean isFileExist(String pathToFile) {
        if (Paths.get(pathToFile).toFile().exists() ||
                ((pathToFile.startsWith("/") || pathToFile.startsWith("\\")) && Paths.get(pathToFile.substring(1)).toFile().exists())) {
            return true;
        }
        System.out.println("\r\nFile Not Found: " + pathToFile + "\r\n" +
                "\tEnter an absolute path to the file.\r\n" +
                "\tOr enter a relative path without the '/' at the beginning.\r\n");
        return false;
    }
}
