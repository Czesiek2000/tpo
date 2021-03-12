package Zadanie1;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.Files;


public class Futil {

    public static void processDir(String dirName, String resultFileName) {
        Path directoryPath = Paths.get(dirName);
        Path resultPath = Paths.get(resultFileName);
        String result = dirName + "/" + resultFileName;
        try {
            FileChannel outputChannel = FileChannel.open(resultPath, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
            Files.walkFileTree(directoryPath, new Visitor(directoryPath, resultPath, outputChannel));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Zostal utworzony plik wynikowy " + result);

    }
}