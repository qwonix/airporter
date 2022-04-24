package ru.qwonix.suai.airporter.views.ticket;

public class Ticket {

    private final int id;
    private String seatId;
    private int price;

    public Ticket(int id, String seatId, int price) {
        this.id = id;
        this.seatId = seatId;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getSeatId() {
        return seatId;
    }

    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


}