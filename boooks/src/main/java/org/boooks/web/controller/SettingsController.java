package org.boooks.web.controller;

import java.security.Principal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.validation.Valid;

import org.boooks.db.entity.SexEnum;
import org.boooks.db.entity.UserEntity;
import org.boooks.exception.BusinessException;
import org.boooks.service.IUserService;
import org.boooks.web.form.UserAccountForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("settings")
public class SettingsController {

	@Autowired
    private IUserService userService;
	
	@RequestMapping(value="/account", method = RequestMethod.GET)
    public String account(Model model, Principal principal) {
		
		UserEntity userEntity = userService.findUserByEmail(principal.getName());
		UserAccountForm userForm = new UserAccountForm();
		
		userForm.setFirstname(userEntity.getFirstname());
		userForm.setLastname(userEntity.getLastname());
		userForm.setEmail(userEntity.getEmail());
		
		Date birthdate = userEntity.getBirthDate();
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(birthdate);
		
		userForm.setYear(gc.get(Calendar.YEAR));
		userForm.setMonth(gc.get(Calendar.MONTH));
		userForm.setDay(gc.get(Calendar.DAY_OF_MONTH));
		
		SexEnum sexEnum = userEntity.getSex();
		userForm.setSex(sexEnum.ordinal());
		
		model.addAttribute("userForm", userForm);
        return "settings/account";
    }
	
	@RequestMapping(value="/account", method = RequestMethod.POST)
    public String account(@Valid UserAccountForm userForm, BindingResult result, Model model, Principal principal) {
		
		if ( result.hasErrors() ) {
			model.addAttribute("userForm", userForm);	
			return "settings/account"; 
		}
		
		UserEntity userEntity = userService.findUserByEmail(principal.getName());
		try {
			Date birthDate = null;
			try {
				GregorianCalendar gc =new GregorianCalendar();
				gc.setLenient(false);
				gc.set(userForm.getYear(),userForm.getMonth(),userForm.getDay());        
				birthDate = gc.getTime();
			} catch ( Exception e ) {
				throw new BusinessException("year", "birthdate.invalide", "La date de naissance n'est pas valide");
			}
			
			userEntity.setBirthDate(birthDate);
			userEntity.setFirstname(userForm.getFirstname());
			userEntity.setLastname(userForm.getLastname());
			SexEnum sex = SexEnum.values()[userForm.getSex()];
			userEntity.setSex(sex);
			
			userEntity = userService.saveUser(userEntity);
			
			// update formulaire
			userForm.setFirstname(userEntity.getFirstname());
			userForm.setLastname(userEntity.getLastname());
			userForm.setEmail(userEntity.getEmail());
			
			Date birthdate = userEntity.getBirthDate();
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTime(birthdate);
			
			userForm.setYear(gc.get(Calendar.YEAR));
			userForm.setMonth(gc.get(Calendar.MONTH));
			userForm.setDay(gc.get(Calendar.DAY_OF_MONTH));
			
			SexEnum sexEnum = userEntity.getSex();
			userForm.setSex(sexEnum.ordinal());
			
			model.addAttribute("userForm", userForm);
			model.addAttribute("messageSuccess", "Modifications sauvegardées");	
		} catch (BusinessException e) {
			result.rejectValue(e.getObjectName(), e.getErrorCode(), e.getMessage());
			model.addAttribute("userForm", userForm);	
			return "settings/account"; 
		}
		
		
		return "settings/account";
		
		
		
	}
	
}