package com.example.model;

public class MyCustomStatusMessage {

	String messag1;
	String message2;
	public MyCustomStatusMessage() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MyCustomStatusMessage(String messag1, String message2) {
		super();
		this.messag1 = messag1;
		this.message2 = message2;
	}
	public String getMessag1() {
		return messag1;
	}
	public void setMessag1(String messag1) {
		this.messag1 = messag1;
	}
	public String getMessage2() {
		return message2;
	}
	public void setMessage2(String message2) {
		this.message2 = message2;
	}
	@Override
	public String toString() {
		return "MyCustomStatusMessage [messag1=" + messag1 + ", message2=" + message2 + "]";
	}
	
	
}
