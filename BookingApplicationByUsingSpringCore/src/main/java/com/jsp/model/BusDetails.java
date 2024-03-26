package com.jsp.model;

import java.time.LocalTime;

public class BusDetails {
	
	private int busnumber;
	private String from;
	private String to;
	private LocalTime time;
	private double price;
	
	public BusDetails() {
		
	}

	public BusDetails(int busnumber, String from, String to, LocalTime time, double price) {
		super();
		this.busnumber = busnumber;
		this.from = from;
		this.to = to;
		this.time = time;
		this.price = price;
	}

	public int getBusnumber() {
		return busnumber;
	}

	public void setBusnumber(int busnumber) {
		this.busnumber = busnumber;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "BusDetails [busnumber=" + busnumber + ", from=" + from + ", to=" + to + ", time=" + time + ", price="
				+ price + "]";
	}
	
	
}
