package net.petterhj.java.ruter;

import static com.gistlabs.mechanize.document.html.query.HtmlQueryBuilder.byName;
import com.gistlabs.mechanize.MechanizeAgent;
import com.gistlabs.mechanize.document.Document;
import com.gistlabs.mechanize.document.html.form.Form;

public class RuterClient {
    // Variables
    String data;

    // Log in
    public boolean logIn(String username, String password) {
        if(username != "" && password != "") {
            // Constants
            final String RUTER_LOGIN_URL = "https://ruter.no/minside/logg-inn/";
            final String RUTER_MYPAGE_URL = "https://ruter.no/minside/";
            final String RUTER_LOGIN_FORM_ID = "aspnetForm";
            final String RUTER_LOGIN_FORM_USERNAME_NAME = "ctl00$FormRegion$ContentOuter$ContentInner$ContentMiddleAndRightOuter$uxLogin$uxLogin$UserName"; 
            final String RUTER_LOGIN_FORM_PASSWORD_NAME = "ctl00$FormRegion$ContentOuter$ContentInner$ContentMiddleAndRightOuter$uxLogin$uxLogin$Password"; 
            final String RUTER_LOGIN_FORM_SUBMIT_NAME = "ctl00$FormRegion$ContentOuter$ContentInner$ContentMiddleAndRightOuter$uxLogin$uxLogin$btnLogin"; 
    
            // Mechanize
            MechanizeAgent agent = new MechanizeAgent();
    
            // Log in
            Document page = agent.get(RUTER_LOGIN_URL);
            Form form = page.form(RUTER_LOGIN_FORM_ID);
            form.get(RUTER_LOGIN_FORM_USERNAME_NAME).set(username);
            form.get(RUTER_LOGIN_FORM_PASSWORD_NAME).set(password);
            form.getSubmitButton(byName(RUTER_LOGIN_FORM_SUBMIT_NAME)).submit();
    
            // My page
            Document mypage = agent.get(RUTER_MYPAGE_URL);
    
            data = mypage.asString();
    
            // Return
            //  Not really the best way to check if logged in or not
            if(mypage.getTitle().contains("MinSide")) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    // Return HTML data
    public String getData() {
        return data;
    }
}
