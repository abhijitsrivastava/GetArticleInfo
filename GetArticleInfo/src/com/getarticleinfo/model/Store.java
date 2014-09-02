package com.getarticleinfo.model;

public class Store {

	private String name;
	private String number;
	private String wwsEndpoint;
	private String active;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getWwsEndpoint() {
		return wwsEndpoint;
	}

	public void setWwsEndpoint(String wwsEndpoint) {
		this.wwsEndpoint = wwsEndpoint;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "Store [name=" + name + ", number=" + number + ", wwsEndpoint="
				+ wwsEndpoint + ", active=" + active + "]";
	}
	

}
