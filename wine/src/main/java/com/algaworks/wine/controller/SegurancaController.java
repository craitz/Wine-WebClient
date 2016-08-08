package com.algaworks.wine.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SegurancaController {
	
	@RequestMapping("/login")
	public String login(@AuthenticationPrincipal User user) {
		return (user != null) ? "redirect:/vinhos" : "Login";
	}
}
