package com.tamtran.myreceipt.controller;

import com.tamtran.myreceipt.business.services.UserService;
import com.tamtran.myreceipt.common.model.UserRegisterRequest;
import com.tamtran.myreceipt.data.domain.UserInfoDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;
import java.util.Set;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	private Set<String> roles;

	//Spring Security see this :
	@RequestMapping(value = "/home",  method = {RequestMethod.GET, RequestMethod.HEAD})
	public ModelAndView login(
			@RequestParam(value = "error", required = false) String error) {

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid username and password!");
		}
		model.setViewName("home");
		return model;
	}
    @RequestMapping(value = "/accessdenied", method = {RequestMethod.GET, RequestMethod.HEAD})
    public String loginerror(Map<String, Object> map) {
        map.put("error", "true");
        return "error";
    }

    @RequestMapping(value = "/logout", method = {RequestMethod.GET, RequestMethod.HEAD})
    public String logout(Map map) {
        return "logout";
    }
    
    public Set<String> getRoles() {
    	roles = AuthorityUtils
                .authorityListToSet(SecurityContextHolder.getContext()
                        .getAuthentication().getAuthorities());
    	return roles;
    }

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerUser(@ModelAttribute("userRegisterRequest") @Valid UserRegisterRequest userRegisterRequest, HttpServletRequest request) {
		System.out.println(request);
		System.out.println(userRegisterRequest.toString());
		//TODO: server side validation
		userService.addUser(userRegisterRequest);

		return null;
	}

	@RequestMapping(value = "/register", method = {RequestMethod.GET, RequestMethod.HEAD})
	public String register(Model m) {
		m.addAttribute("userInfo", new UserInfoDO());
		return "register";
	}
}
