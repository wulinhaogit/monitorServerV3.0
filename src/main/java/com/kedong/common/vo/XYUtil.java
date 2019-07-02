package com.kedong.common.vo;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.mchange.lang.StringUtils;


public class XYUtil {



  public static Map<String, LinkedList> getXYMap(List<Map<String,Object>> list,String xName,String yName){
      LinkedList<Object> X=new LinkedList<>();
      LinkedList<Object> Y=new LinkedList<>();
      Map<String, LinkedList> map=new HashMap<>();
            for (Map<String,Object> m:list) {
                Y.add(m.get(yName));
                if(!StringUtils.nonEmptyString(xName)){
                	X.add("-");
                }else {
                	X.add(m.get(xName));
				}
        }
            map.put("X",X);
            map.put("Y",Y);
            return map;
  }
  public static LinkedList<String> getProcName(List<Map<String,Object>> list){
      LinkedList<String> procName=new LinkedList<>();
            for (Map<String,Object> m:list) {
                procName.add((String)m.get("PROC_NAME"));
        }
            return procName;
  }
  public static LinkedList<String> getNodeName(List<Map<String,Object>> list){
	  LinkedList<String> nodeName=new LinkedList<>();
	  for (Map<String,Object> m:list) {
		  nodeName.add(m.get("NODEID")+"");
	  }
	  return nodeName;
  }


    public static Map<String,Object> getTreeXYMap(List<Map<String,Object>> list,LinkedList<String> nList,LinkedList<String> sList,String xName,String yName){
         String param1=nList.get(0) ;
         String param2=nList.get(1) ;
         String param3=nList.get(2);
         String nodeId1=sList.get(0);
         String nodeId2=sList.get(1);
         String nodeId3=sList.get(2);
         
         
        Map<String,Object> wMap= new HashMap<>();
        Map<String, LinkedList> wm1=new HashMap<>();
        Map<String, LinkedList> wm2=new HashMap<>();
        Map<String, LinkedList> wm3=new HashMap<>();
        LinkedList<Object> WX1=new LinkedList<>();
        LinkedList<Object> WY1=new LinkedList<>();
        LinkedList<Object> WX2=new LinkedList<>();
        LinkedList<Object> WY2=new LinkedList<>();
        LinkedList<Object> WX3=new LinkedList<>();
        LinkedList<Object> WY3=new LinkedList<>();
        Map<String,Object> ws;
        String name;
        String nodeId;

        for (int i=0;i<list.size();i++){
            ws= list.get(i);
            name=(String)ws.get("PROC_NAME");
            nodeId=ws.get("NODEID")+"";
            if (name.equals(param1)&&nodeId.equals(nodeId1)){
                WX1.add(ws.get(xName));
                WY1.add(ws.get(yName));
            }else if (name.equals(param2)&&nodeId.equals(nodeId2)){
                WX2.add(ws.get(xName));
                WY2.add(ws.get(yName));
            }else if (name.equals(param3)&&nodeId.equals(nodeId3)){
                WX3.add(ws.get(xName));
                WY3.add(ws.get(yName));
            }
        }
        wm1.put("X",WX1);
        wm1.put("Y",WY1);
        wMap.put(param1+"-"+nodeId1,wm1);
        wm2.put("X",WX2);
        wm2.put("Y",WY2);
        wMap.put(param2+"-"+nodeId2,wm2);
        wm3.put("X",WX3);
        wm3.put("Y",WY3);
        wMap.put(param3+"-"+nodeId3,wm3);

      return wMap;
    }
    public static Map<String,Object> getTreeXYMap(List<Map<String,Object>> list,List<Map<String, Object>> listName,String xName,String yName){
        String param1=listName.get(0).get("USER")+"" ;
        String param2=listName.get(1).get("USER")+"" ;
        String param3=listName.get(2).get("USER")+"";
     
        
        
       Map<String,Object> wMap= new HashMap<>();
       Map<String, LinkedList> wm1=new HashMap<>();
       Map<String, LinkedList> wm2=new HashMap<>();
       Map<String, LinkedList> wm3=new HashMap<>();
       Map<String, LinkedList> wm4=new HashMap<>();
       LinkedList<Object> WX1=new LinkedList<>();
       LinkedList<Object> WY1=new LinkedList<>();
       LinkedList<Object> WX2=new LinkedList<>();
       LinkedList<Object> WY2=new LinkedList<>();
       LinkedList<Object> WX3=new LinkedList<>();
       LinkedList<Object> WY3=new LinkedList<>();
       LinkedList<Object> WX4=new LinkedList<>();
       LinkedList<Object> WY4=new LinkedList<>();
       Map<String,Object> ws;
       String name;
       String nodeId;

       for (int i=0;i<list.size();i++){
           ws= list.get(i);
           name=(String)ws.get("USER");
           if (name.equals(param1)){
               WX1.add(ws.get(xName));
               WY1.add(ws.get(yName));
           }else if (name.equals(param2)){
               WX2.add(ws.get(xName));
               WY2.add(ws.get(yName));
           }else if (name.equals(param3)){
               WX3.add(ws.get(xName));
               WY3.add(ws.get(yName));
           }else {
        	   WX4.add(ws.get(xName));
               WY4.add(ws.get(yName));
           }
       }
       wm1.put("X",WX1);
       wm1.put("Y",WY1);
       wMap.put(param1,wm1);
       wm2.put("X",WX2);
       wm2.put("Y",WY2);
       wMap.put(param2,wm2);
       wm3.put("X",WX3);
       wm3.put("Y",WY3);
       wMap.put(param3,wm3);
       wm4.put("X",WX4);
       wm4.put("Y",WY4);
       wMap.put("OTHER",wm4);

     return wMap;
   }

    public static Map<String,Object> getXYYMap(List<Map<String,Object>> list,String xName,String y1Name,String y2Name){

        LinkedList<Object> X=new LinkedList<>();
        LinkedList<Object> YW=new LinkedList<>();
        LinkedList<Object> YR=new LinkedList<>();
        Map<String, Object> map=new HashMap<>();
        for (Map<String,Object> m:list) {
            YW.add(m.get(y2Name));
            YR.add(m.get(y1Name));
            X.add(m.get(xName));
        }
        map.put("X",X);
        map.put("YW",YW);
        map.put("YR",YR);

        return map;
    }
    public static Map<String, LinkedList> getXXYMap(List<Map<String,Object>> list,String x1Name,String x2Name,String y1Name){
    	
    	LinkedList<Object> X1=new LinkedList<>();
    	LinkedList<Object> X2=new LinkedList<>();
    	LinkedList<Object> Y=new LinkedList<>();
    	Map<String, LinkedList> map=new HashMap<>();
    	for (Map<String,Object> m:list) {
    		Y.add(m.get(y1Name));
    		X1.add(m.get(x1Name));
    		X2.add(m.get(x2Name));
    	}
    	map.put("X1",X1);
    	map.put("X2",X2);
    	map.put("Y",Y);
    	
    	return map;
    }
  /*  public static Map<String,Object> getTreeXYYMap(List<Map<String,Object>> list,LinkedList<String> nlist,String xName,String y1Name,String y2Name){
        String param1=nlist.get(0) ;
        String param2=nlist.get(1) ;
        String param3=nlist.get(2);
        Map<String,Object> wMap= new HashMap<>();
        Map<String, LinkedList> wm1=new HashMap<>();
        Map<String, LinkedList> wm2=new HashMap<>();
        Map<String, LinkedList> wm3=new HashMap<>();
        LinkedList<Object> WX1=new LinkedList<>();
        LinkedList<Object> WYR1=new LinkedList<>();
        LinkedList<Object> WYW1=new LinkedList<>();
        LinkedList<Object> WX2=new LinkedList<>();
        LinkedList<Object> WYR2=new LinkedList<>();
        LinkedList<Object> WYW2=new LinkedList<>();
        LinkedList<Object> WX3=new LinkedList<>();
        LinkedList<Object> WYR3=new LinkedList<>();
        LinkedList<Object> WYW3=new LinkedList<>();
        Map<String,Object> ws;
        String name;
        for (int i=0;i<list.size();i++){
            ws= list.get(i);
            name=(String)ws.get("PROC_NAME");
            if (name.equals(param1)){
                WYW1.add(ws.get(y2Name));
                WYR1.add(ws.get(y1Name));
                WX1.add(ws.get(xName));
            }else if (name.equals(param2)){
                WYW2.add(ws.get(y2Name));
                WYR2.add(ws.get(y1Name));
                WX2.add(ws.get(xName));
            }else if (name.equals(param3)){
                WYW3.add(ws.get(y2Name));
                WYR3.add(ws.get(y1Name));
                WX3.add(ws.get(xName));
            }
        }
        wm1.put("X",WX1);
        wm1.put("YR",WYR1);
        wm1.put("YW",WYW1);
        wMap.put(param1,wm1);
        wm2.put("X",WX2);
        wm2.put("YR",WYR2);
        wm2.put("YW",WYW2);
        wMap.put(param2,wm2);
        wm3.put("X",WX3);
        wm3.put("YR",WYR3);
        wm3.put("YW",WYW3);
        wMap.put(param3,wm3);
        return wMap;
    }*/
  
}
