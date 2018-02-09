package com.lzdn.manage.web;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lzdn.manage.utils.web.RequestMessage;
import com.lzdn.manage.utils.web.ResponseMessage;

@Controller
public class SocketController {
	
	@RequestMapping("chat")
	public String chat() {
		return "im/ws";
	}

	@MessageMapping("/welcome")
	@SendTo("/topic/getResponse")
	public ResponseMessage say(RequestMessage message) {
		System.out.println(message.getName());
		return new ResponseMessage("welcome," + message.getName() + " !");
	}
}
