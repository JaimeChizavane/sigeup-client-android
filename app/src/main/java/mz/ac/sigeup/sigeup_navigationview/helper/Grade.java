package mz.ac.sigeup.sigeup_navigationview.helper;

/**
 * Created by jaimechizavane on 1/11/18.
 */

public class Grade {

    String Subject;
    String Test;

    public Grade(String subject, String test) {
        Subject = subject;
        Test = test;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getTest() {
        return Test;
    }

    public void setTest(String test) {
        Test = test;
    }
}
