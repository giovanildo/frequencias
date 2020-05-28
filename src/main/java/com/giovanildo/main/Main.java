package com.giovanildo.main;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Main {
	public static void main(String[] args) {
		Calendar aData = new GregorianCalendar();
		
		System.out.println(aData.getTimeZone());
		
		Date date = new Date();
		
		aData.setTime(date);
		System.out.println(aData.getTimeZone());
		
		
		
	}


}