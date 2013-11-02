package net.petterhj.android.ruterdashclock;

import net.petterhj.java.ruter.*;

import com.google.android.apps.dashclock.api.DashClockExtension;
import com.google.android.apps.dashclock.api.ExtensionData;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;

public class RuterExtension extends DashClockExtension {
    public static final String PREF_USERNAME = "pref_username";
    public static final String PREF_PASSWORD = "pref_password";

    @Override
    protected void onUpdateData(int reason) {
        // Variables 
        String ticket_status = "";
        String ticket_status_expanded = "";
        String ticket_status_body = "";
        
        //= getString(R.string.status_active);
        //String  = ticket_status + " " + getString(R.string.ticket);
        //String ticket_status_body = "30-dagersbillett Student, sone 1 til 1\nUtløper 03/08/13, kl. 17:32\n" + username;
        
        // Get preference values
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String username = sp.getString(PREF_USERNAME, "");
        String password = sp.getString(PREF_PASSWORD, "");
        
        // Get Ruter data
        RuterClient ruter = new RuterClient();
        
        if(ruter.logIn(username, password)) {
            // Get ticket data
            Parser data = new Parser(ruter.getData());
            
            ticket_status = "";
            ticket_status_expanded = "";
            ticket_status_body = "";
            
            for (Ticket ticket : data.getTickets()) {
                ticket_status = ticket.getStatus();
                ticket_status_expanded = ticket.getStatus();
                ticket_status_body = ticket_status_body + ticket.getType() + "\n" + getString(R.string.expires) + " " + ticket.getExpires()[0] + " - " + ticket.getExpires()[1] + "\n";
                
                /*
                System.out.println("\t" + ticket.getNumber());
                System.out.println("\t - Bruker: " + ticket.getHolder());
                System.out.println("\t - Kjøpt: " + ticket.getBought());
                System.out.println("\t - Utløper: " + ticket.getExpires()[0] + " - " + ticket.getExpires()[1]);
                System.out.println("\t - Fornyes: " + ticket.getRenewal());
                */
            }
        }
        else {
            // Could not log in
            ticket_status = getString(R.string.error);
            ticket_status_expanded = getString(R.string.error);
            ticket_status_body = getString(R.string.error_login);
        }
        
        
        // Publish the extension data update.
        publishUpdate(new ExtensionData()
                .visible(true)
                .icon(R.drawable.ic_extension_ruter)
                .status(ticket_status)
                .expandedTitle(ticket_status_expanded)
                .expandedBody(ticket_status_body)
                .clickIntent(new Intent(Intent.ACTION_VIEW, Uri.parse("https://ruter.no/minside/"))));
    }
}