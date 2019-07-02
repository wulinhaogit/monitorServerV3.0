package com.kedong.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/web")
public class WebSocketController {

    //页面请求
    @RequestMapping("/socket")
    public String socketKind2() {
        return "/socket";
    }


}
