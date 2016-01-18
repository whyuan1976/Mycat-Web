package org.mycat.web.ctrl;


import org.mycat.web.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/*
 * 登陆用控制器
 * 
 * 
 */
@Controller
public class LoginController {
	@Autowired
	CommonService commonService;
	
	
	
	@RequestMapping(value="/register",method=RequestMethod.GET)
	public String register(){
		return "register";
	}
			
	
	
	
	//2015111722005101380013830631 
	//31
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(Model model,@RequestParam("user") String username,@RequestParam("password")  String password) {

		
	    return "main";
	}
}
