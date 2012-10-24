package org.boooks.web.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.validation.Valid;

import org.boooks.db.entity.SexEnum;
import org.boooks.db.entity.UserEntity;
import org.boooks.exception.BusinessException;
import org.boooks.service.IUserService;
import org.boooks.web.form.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("user")
public class UserController {

	@Autowired
    private IUserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	@RequestMapping(value="/registration", method = RequestMethod.GET)
    public String registration(Model model) {
		UserForm userForm = new UserForm();
		model.addAttribute("userForm", userForm);
        return "user/registration";
    }
	
	@RequestMapping(value="/registration", method = RequestMethod.POST)
    public String registration(@Valid UserForm userForm, BindingResult result, Model model) {
		
		if ( result.hasErrors() ) {
			model.addAttribute("userForm", userForm);	
			return "user/registration"; 
		}
		try {
			
			if ( ! userForm.getPassword().equals(userForm.getConfirmPassword()) ) {
				throw new BusinessException("confirmPassword", "password.notequals", "Les mots de passe ne sont pas identiques");
			}
			
			Date birthDate = null;
			try {
				GregorianCalendar gc =new GregorianCalendar();
				gc.setLenient(false);
				gc.set(userForm.getYear(),userForm.getMonth(),userForm.getDay());        
				birthDate = gc.getTime();
			} catch ( Exception e ) {
				throw new BusinessException("year", "birthdate.invalide", "La date de naissance n'est pas valide");
			}
			
			UserEntity user = new UserEntity();
			user.setActive(false);
			 
			user.setBirthDate(birthDate);
			user.setEmail(userForm.getEmail());
			user.setFirstname(userForm.getFirstname());
			user.setLastname(userForm.getLastname());
			SexEnum sex = SexEnum.values()[userForm.getSex()];
			user.setSex(sex);
			
			// TODO : Remplacer new ShaPasswordEncoder par une reference @autowired spring
			ShaPasswordEncoder shaPasswordEncoder = new ShaPasswordEncoder(256);
			user.setPassword(shaPasswordEncoder.encodePassword(userForm.getPassword(), null));
			
		
			userService.registerUser(user);
			
		} catch (BusinessException e) {
			result.rejectValue(e.getObjectName(), e.getErrorCode(), e.getMessage());
			model.addAttribute("userForm", userForm);	
			return "user/registration"; 
		}
		
		
		return "redirect:/user/registered.htm";
	}
	
	@RequestMapping(value="/registered", method = RequestMethod.GET)
    public String registered() {
		return "user/registered";
	}
	
	@RequestMapping(value="/activation", method = RequestMethod.GET)
    public String activate(@RequestParam("email") String email, @RequestParam("tempkey") String tempkey) {
		
		try {
			userService.activate(email, tempkey);
		} catch (Exception e) {
			return "user/notactivated";
		}
		
		return "user/activated";
	}
	
}