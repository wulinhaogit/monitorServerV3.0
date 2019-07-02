package com.kedong.common.vo;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public class AJAXJson {
    private boolean success=true;//是否成功
    private String msg="操作成功";//提示信息
    private  Object obj=null;//其他信息
    private Map<String,Object> attrbutes;//替他参数

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public Map<String, Object> getAttrbutes() {
        return attrbutes;
    }

    public void setAttrbutes(Map<String, Object> attrbutes) {
        this.attrbutes = attrbutes;
    }

    public String getJsonStr(){
        JSONObject obj=new JSONObject();
        obj.put("sucess",this.isSuccess());
        obj.put("msg",this.getMsg());
        obj.put("obj",this.obj);
        obj.put("attrbutes",this.attrbutes);
        return obj.toJSONString();
    }
}
