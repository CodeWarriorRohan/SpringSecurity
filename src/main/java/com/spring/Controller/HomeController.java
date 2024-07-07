package com.spring.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.spring.entity.User;
import com.spring.repository.UserRepo;
import com.spring.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepo userRepo;

	@ModelAttribute
	public void commonUser(Principal p, Model m) {
		if (p != null) {
			String email = p.getName();
			User user = userRepo.findByEmail(email);
			m.addAttribute("user", user);
		}

	}

	@GetMapping("/")
	public String index() {

		return "index";

	}

	@GetMapping("/signin")
	public String signin() {

		return "login";

	}

	@GetMapping("/register")
	public String register() {

		return "register";

	}

//	@GetMapping("/user/profile")
//	public String profile(Principal p, Model m) {
//		String email = p.getName();
//		User user = userRepo.findByEmail(email);
//		m.addAttribute("user", user);
//		return "profile";
//	}

//	@GetMapping("/user/home")
//	public String home() {
//		
//		return "home";
//		
//	}

	@PostMapping("/saveUser")
	public String saveUser(@ModelAttribute User user, HttpSession session, HttpServletRequest request) {

//		System.out.println(user);

		String url = request.getRequestURI().toString();

		// System.out.println(url);
		url = url.replace(request.getServletPath(), "");
		// System.out.println(url);

		
		  User u = userService.saveUser(user, url);
		  
		  if(u != null) { session.setAttribute("msg", "Register successfully!"); //
		  System.out.println("save success!"); }else { session.setAttribute("msg",
		  "Something wrong on server!"); // System.out.println("error!"); }
		  }
		 
		return "redirect:/register";
	}

	@GetMapping("/verify")
	public String verifyAccount(@Param("code") String code, Model m) {
		
		boolean f = userService.verifyAccount(code);
		
		if(f) {
			m.addAttribute("msg", "Your account is verified successfully");
		}else {
			m.addAttribute("msg", "Already verified or incorrect code");
		}
		return "message";
	}
}

