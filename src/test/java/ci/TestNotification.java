package ci;
import static org.junit.Assert.*;
import org.junit.Test;
import java.util.Properties;
import javax.mail.*;
import java.util.Random;

public class TestNotification {
    //Code found at: http://www.technicalkeeda.com/java-tutorials/how-to-access-gmail-inbox-using-java-imap

    //Generates a random number, sends it to it self and checks the latest mail to make sure that the number
    //is there.
    @Test
    public void mailSentTrue() throws MessagingException {
        MailNotification cis = new MailNotification();
        Random rand = new Random();
        int r = rand.nextInt();
        cis.Send("contintgroup17", "morganlove123", "contintgroup17@gmail.com", Integer.toString(r), "");
        Properties props = new Properties();

        try {
            Session session = Session.getDefaultInstance(props, null);

            Store store = session.getStore("imaps");
            store.connect("smtp.gmail.com", "contintgroup17@gmail.com", "morganlove123");

            Folder inbox = store.getFolder("inbox");
            inbox.open(Folder.READ_ONLY);
            int messageCount = inbox.getMessageCount();

            System.out.println("Total Messages:- " + messageCount);

            Message[] messages = inbox.getMessages();
            System.out.println("------------------------------");

            System.out.println("Mail Subject:- " + messages[messageCount - 1].getSubject());

            inbox.close(true);
            store.close();
            assertTrue(r == Integer.parseInt(messages[messageCount - 1].getSubject()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Generates a random number and sends it to another mail
    //checks to see that the random number is not in the inbox of itself.
    @Test
    public void mailSentFalse() throws MessagingException {
        MailNotification cis = new MailNotification();
        Random rand = new Random();
        int r = rand.nextInt();
        cis.Send("contintgroup17", "morganlove123", "virre.carre@gmail.com", Integer.toString(r), "");
        Properties props = new Properties();

        try {
            Session session = Session.getDefaultInstance(props, null);

            Store store = session.getStore("imaps");
            store.connect("smtp.gmail.com", "contintgroup17@gmail.com", "morganlove123");

            Folder inbox = store.getFolder("inbox");
            inbox.open(Folder.READ_ONLY);
            int messageCount = inbox.getMessageCount();

            System.out.println("Total Messages:- " + messageCount);

            Message[] messages = inbox.getMessages();
            System.out.println("------------------------------");

            System.out.println("Mail Subject:- " + messages[messageCount - 1].getSubject());

            inbox.close(true);
            store.close();
            assertFalse(r == Integer.parseInt(messages[messageCount - 1].getSubject()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
