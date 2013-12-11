package net.petterhj.android.ruterdashclock;

import net.petterhj.java.ruter.*;

import com.google.android.apps.dashclock.api.DashClockExtension;
import com.google.android.apps.dashclock.api.ExtensionData;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RuterExtension extends DashClockExtension {
    public static final String PREF_USERNAME = "pref_username";
    public static final String PREF_PASSWORD = "pref_password";
    public static final String PREF_CARD = "pref_card";
    
    String ticket_status = "";
    String ticket_status_expanded = "";
    String ticket_status_body = "";
    
    boolean ticketValid = false; 

    @Override
    protected void onUpdateData(int reason) {
        // Get preference values
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String username = sp.getString(PREF_USERNAME, "");
        String password = sp.getString(PREF_PASSWORD, "");
        String card = sp.getString(PREF_CARD, "");
        
        // Update
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Date date = new Date();
        String lastUpdate = dateFormat.format(date);
        
        // Check if configured
        if (username == "" || password == "") {
            ticket_status = getString(R.string.error_config);
            ticket_status_expanded = getString(R.string.error_config);
            ticket_status_body = getString(R.string.error_config_username_password_missing);
            
        } 
        else {
            // Check if card selected in preferences
            if (card == "") {
                ticket_status = getString(R.string.error_config);
                ticket_status_expanded = getString(R.string.error_config);
                ticket_status_body = getString(R.string.error_config_card_missing);
                
            } 
            else {
                // Don't check until after next expiration                 
                if(ticketValid == false) {
                    // Get Ruter data
                    RuterClient ruter = new RuterClient();
                    
                    if(ruter.logIn(username, password)) {
                        // Get ticket data
                        Parser data = new Parser(ruter.getData());
                        
                        Ticket validTicket = null;
                        
                        for (Ticket ticket : data.getTickets()) {
                            // Get selected card
                            if (ticket.getNumber().equals(card))
                                validTicket = ticket;
                        }
                        
                        // Card
                        if(validTicket != null) {
                            ticketValid = validTicket.isValid();
                            ticket_status = validTicket.getStatus();
                            ticket_status_expanded = validTicket.getStatus();
                            ticket_status_body = ticket_status_body + validTicket.getType() + "\n" + 
                                                 getString(R.string.expires) + " " + validTicket.getExpires()[0] + " - " + validTicket.getExpires()[1];
                        }
                        else {
                            ticket_status = getString(R.string.error_unknown_card);
                            ticket_status_expanded = getString(R.string.error_unknown_card);
                            ticket_status_body = getString(R.string.error_config_card_missing);
                        }
                    } 
                    else {
                        // Could not log in
                        ticket_status = getString(R.string.error);
                        ticket_status_expanded = getString(R.string.error);
                        ticket_status_body = getString(R.string.error_login);
                    }
                }
            }
        }
        
        // Publish the extension data update.
        publishUpdate(new ExtensionData()
                .visible(true)
                .icon(R.drawable.ic_extension_ruter)
                .status(ticket_status)
                .expandedTitle(ticket_status_expanded)
                .expandedBody(ticket_status_body + "\nOppdatert: " + lastUpdate)
                .clickIntent(new Intent(Intent.ACTION_VIEW, Uri.parse("https://ruter.no/minside/"))));
    }
}
