package com.kedong;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;
import java.util.stream.IntStream;

import jdk.nashorn.api.scripting.JSObject;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;


public class TEST {
	
	public static void main(String[] args) throws ParseException {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
	    Map<String,Object> m1= new HashMap<String,Object>();
	    Map<String,Object> m2= new HashMap<String,Object>();
	    Map<String,Object> m3= new HashMap<String,Object>();
	    Map<String,Object> m4= new HashMap<String,Object>();
	    Map<String,Object> m5= new HashMap<String,Object>();
	    m1.put("name","a");
	    m1.put("LEVE","1");
	    m2.put("name","b");
	    m2.put("LEVE","2");
	    m3.put("name","c");
	    m3.put("LEVE","1");
	    m4.put("name","d");
	    m4.put("LEVE","2");
	    m5.put("name","e");
	    m5.put("LEVE","2");
	    list.add(m1);
	    list.add(m2);
	    list.add(m3);
	    list.add(m4);
	    list.add(m5);
	 
		
		   Collections.sort(list,new Comparator<Map<String, Object>>(){

				@Override
				public int compare(Map<String, Object> o1, Map<String, Object> o2) {
					int l1=Integer.valueOf(o1.get("LEVE")+"");
					int l2=Integer.valueOf(o2.get("LEVE")+"");
					if (l1>l2) {
						return 1;
						
					}
					return -1;
				}
	        	 
	         });
		   
		 JSONObject o = new JSONObject();
		 String jsonString = o.toJSONString(list);
		 System.out.println(jsonString);
				 
	}
	
	
	

}
