package com.snowboardcrud.web;

import javax.jdo.PersistenceManager;

import com.snowboardcrud.domain.Snowboard;
import com.snowboardcrud.repository.PMF;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/snowboard")
public class SnowboardController {

	@RequestMapping(value = "/processCreateSnowboard", method = RequestMethod.POST)
	public String processCreateSnowboard(@ModelAttribute("snowboard")
							Snowboard snowboard, BindingResult result) {
		
		System.out.println("Brand:" + snowboard.getBrand() + " " +
					"Model:" + snowboard.getModel());

        PersistenceManager pm = PMF.get().getPersistenceManager();
        try {
            pm.makePersistent(snowboard);
        } catch (Exception e){
        	System.out.println(e);
        } finally {
            pm.close();
        }
		
		return "redirect:createSnowboard";
	}
	
	@RequestMapping(value = "/deleteSnowboard/{id}")
	public String processDeleteSnowboard(@PathVariable ("id") Long id) {
		
		System.out.println("Snowboard with id:" + id + " " +
					"--- Ready For Deletion");

        PersistenceManager pm = PMF.get().getPersistenceManager();
        try {
        	Snowboard snowboard = pm.getObjectById(Snowboard.class, id);
        	pm.deletePersistent(snowboard);
            pm.makePersistent(snowboard);
        } catch (Exception e){
        	System.out.println(e);
        } finally {
            pm.close();
        }
	
		return "redirect:/snowboard/createSnowboard";
	}
	
	@RequestMapping(value = "/ajaxUpdate", method = RequestMethod.POST)
	public String ajaxUpdate(){
		System.out.println("testing ajax.....");
		return null;
	}
	
	@RequestMapping("/createSnowboard")
	public ModelAndView showSnowboards() {
		return new ModelAndView("snowboard/createSnowboard", "command", new Snowboard());
	}
}
