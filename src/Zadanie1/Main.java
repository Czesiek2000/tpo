/**
 *
 *  @author Czech Michał S20613
 *
 */

package Zadanie1;

public class Main {
    public static void main(String[] args) {
//        String dirName = System.getProperty("user.home")+"/TPO1dir";
        String dirName = "data/TPO1dir";
        String resultFileName = "data/TPO1res.txt";
        Futil.processDir(dirName, resultFileName);
    }
}
