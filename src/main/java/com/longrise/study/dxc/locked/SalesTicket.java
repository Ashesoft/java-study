package com.longrise.study.dxc.locked;

public class SalesTicket {
    public static void main(String[] args) {
        // TicketLockNo ticket = new TicketLockNo();
        TicketLockYes ticket = new TicketLockYes();
        new Thread(ticket, "1号窗口").start();
        new Thread(ticket, "2号窗口").start();
        new Thread(ticket, "3号窗口").start();
    }
}