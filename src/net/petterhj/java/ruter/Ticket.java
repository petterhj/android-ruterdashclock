package net.petterhj.java.ruter;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Ticket {
    String number;
    String holder;
    String status;
    String type;
    String bought;
    String[] expires = new String[2];
    String renewal;
    
    public boolean isValid() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Date date = new Date();
        System.out.println(dateFormat.format(date));
        
        try {
            Date expiration = dateFormat.parse(this.getExpires()[0] + " " + this.getExpires()[1]);
        
            if(date.after(expiration)){
                return false;
            }
            else {
                return true;
            }
        }
        catch (Exception e) {
            return false;
        }
        
    }
    
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status.trim();
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type.trim();
    }
    public String getBought() {
        return bought;
    }
    public void setBought(String bought) {
        this.bought = bought;
    }
    public String[] getExpires() {
        return expires;
    }
    public void setExpires(String date, String time) {
        this.expires[0] = date.trim();
        this.expires[1] = time.trim();
    }
    public String getRenewal() {
        return renewal;
    }
    public void setRenewal(String renewal) {
        this.renewal = renewal.trim();
    }
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number.trim();
    }
    public String getHolder() {
        return holder;
    }
    public void setHolder(String holder) {
        this.holder = holder.trim();
    }
}
