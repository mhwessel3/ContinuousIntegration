package ci;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Random;

public class BuildAndTest {

    public static String folderName = new String();

    /**
     * Helper function that uses BufferedReader to read the output of a process
     */
    public String readProcessOutput(Process process) throws IOException {
        StringBuilder ret = new StringBuilder();
        String line = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        while((line = reader.readLine()) != null) {
            ret.append(line + "\n");
        }
        return ret.toString();
    }

    /**
     * Clones the repository to a folder so we can test or build it.
     * @author Anton Hedkrans
     */
    public String cloneBranch() { //HttpServletResponse response
        String URL = "https://github.com/starkelove/LAB2group17.git";

        Random rand = new Random();
        String folderNumber = Integer.toString(rand.nextInt(1000000));
        folderName = "pushFolder" + folderNumber.toString();

        File branchDirectories = new File("../" + folderName);
        branchDirectories.mkdir();
        try {
            Process process = Runtime.getRuntime().exec("git " + "clone " + URL + " " + branchDirectories);
            if(process.waitFor() == 0) {
                return "Folder successfully cloned.";
            } else {
                return "Folder clone failed.";
            }
        } catch(Exception e) {
            return "Folder clone failed.";
        }
    }
    /**
     * Run build gradle using exec function
     * @author Anton Hedkrans
     */
    public String buildBranch() { //HttpServletResponse response
        try {
            Runtime.getRuntime().exec("cd .. && cd " + folderName);
            Process build_process = Runtime.getRuntime().exec("gradle build -x test");
            if(build_process.waitFor() == 0) {
                return readProcessOutput(build_process);
            } else {
                return "Build Failed";
            }
        } catch(Exception e) {
            return "Build Failed";
        }
    }

    public String testBranch() { //HttpServletResponse response
        try {
            Runtime.getRuntime().exec("cd .. && cd " + folderName);
            Process test_process = Runtime.getRuntime().exec("./gradlew test");

            if(test_process.waitFor() == 0) {
                return readProcessOutput(test_process);
            } else {
                return "Build Failed";
            }
        } catch(Exception e) {
            return "Build Failed";
        }
    }

}
