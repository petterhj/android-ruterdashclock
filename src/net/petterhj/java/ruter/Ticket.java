package net.petterhj.java.ruter;

public class Ticket {
    String number;
    String holder;
    String status;
    String type;
    String bought;
    String[] expires = new String[2];
    String renewal;

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
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
        this.expires[0] = date;
        this.expires[1] = time;
    }
    public String getRenewal() {
        return renewal;
    }
    public void setRenewal(String renewal) {
        this.renewal = renewal;
    }
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public String getHolder() {
        return holder;
    }
    public void setHolder(String holder) {
        this.holder = holder;
    }
}
