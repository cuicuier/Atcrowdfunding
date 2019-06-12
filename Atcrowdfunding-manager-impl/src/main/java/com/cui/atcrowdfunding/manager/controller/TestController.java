package com.cui.atcrowdfunding.manager.controller;

import com.cui.atcrowdfunding.manager.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
public class TestController {

	@Autowired
	private TestService testService;

	@RequestMapping("/test")
	public String test() {
		testService.insert();
		return "success";
	}

}
