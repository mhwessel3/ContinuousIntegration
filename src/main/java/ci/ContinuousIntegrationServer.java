package ci;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.stream.Collectors;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

/**
 * ContinuousIntegrationServer using GitHub webhook features.
*/
public class ContinuousIntegrationServer extends AbstractHandler {
    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response)
        throws IOException, ServletException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        baseRequest.setHandled(true);
        System.out.println(target);

        /**
         * Only go through if POST
         */
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            String payload_str = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            JsonParser parser = new JsonParser();
            JsonObject obj = parser.parse(payload_str).getAsJsonObject();

            if (obj.get("ref").toString().equalsIgnoreCase("\"refs/heads/assessment\"")) {
                // push made to assessment branch
                JsonObject head_commit = obj.getAsJsonObject("head_commit");
                JsonObject author = head_commit.getAsJsonObject("author");
                String name = author.get("name").toString();
                String email = author.get("email").toString();
                String commit_url = head_commit.get("url").toString();
                String timestamp = head_commit.get("timestamp").toString();
                String added = head_commit.get("added").toString();
                String removed = head_commit.get("removed").toString();
                String modified = head_commit.get("modified").toString();

                // build and test the commit
                String clone_report = "";
                String build_report = "";
                String test_report = "";
                if(request.getHeader("X-Github-Event").equals("push")) {
                    BuildAndTest bat = new BuildAndTest();
                    clone_report = bat.cloneBranch();
                    build_report = bat.buildBranch();
                    test_report = bat.testBranch();
                }
                response.getWriter().println("EVENT: " + request.getHeader("X-Github-Event"));

                // Generate report for email regarding push to assessment
                String report = name + ",\n\n" +
                        "You pushed to the assessment branch at " + timestamp + "\n" +
                        "To reference this commit online, you may go to " + commit_url + "\n\n" +
                        "ALL FILE CHANGES BELOW: \n\n" +
                        "FILES ADDED: " + added + "\n\n" +
                        "FILES REMOVED: " + removed + "\n\n" +
                        "FILES MODIFIED: " + modified + "\n\n" +
                        "CLONE REPORT: " + clone_report + "\n\n" +
                        "BUILD REPORT: " + build_report + "\n\n" +
                        "TEST REPORT: " + test_report + "\n\n" +
                        "- Team 17";
                response.getWriter().println(report);

                try{
                    MailNotification.Send("contintgroup17", "morganlove123", email, "Git Commit Report", report);
                } catch (AddressException e) {
                    e.printStackTrace();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * Begins the CI server upon project start
     * This triggers the functionality of the rest of the program.
     */
    public static void main(String[] args) throws Exception
    {
        Server server = new Server(8097);
        server.setHandler(new ContinuousIntegrationServer());
        server.start();
        server.join();
    }
}
