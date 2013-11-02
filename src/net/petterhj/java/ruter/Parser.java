package net.petterhj.java.ruter;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Parser {
    Document doc;
    Element customer_data;
    Elements tickets_data;

    public Parser(String data){
        // Parse
        doc = Jsoup.parse(data);
        customer_data = doc.select("div.loggedin").first();
        tickets_data = doc.select("li.travelCard");
    }

    // Return customer
    public Customer getCustomer() {
        Customer customer = new Customer();

        customer.setName(customer_data.select("a").eq(0).text());
        customer.setNumber(customer_data.select("span").eq(1).text().split(": ")[1]);

        return customer;
    }

    // Return array of tickets
    public ArrayList<Ticket> getTickets() {
        // Tickets
        ArrayList<Ticket> tickets = new ArrayList<Ticket>();

        for (Element ticket_data : tickets_data) {
            // Ticket
            Ticket ticket = new Ticket();
            
            ticket.setStatus(ticket_data.select("h3").first().text());
            ticket.setType(ticket_data.select("div.meta").first().text());
            ticket.setNumber(ticket_data.select("span.number").first().text());
            ticket.setHolder(ticket_data.select("span.text").first().text());
            ticket.setBought(ticket_data.select("div.property").eq(0).text().split(" ")[1]);
            ticket.setExpires(ticket_data.select("div.property").eq(1).text().split(" ")[1], ticket_data.select("div.property").eq(1).text().split(" ")[3]);
            ticket.setRenewal(ticket_data.select("div.property").eq(2).text().split(" ")[2]);

            tickets.add(ticket);
        }

        return tickets;
    }
}
