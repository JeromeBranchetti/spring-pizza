package org.generation.italy.controller;

import javax.validation.Valid;

import org.generation.italy.model.Pizza;
import org.generation.italy.service.IngredientiService;
import org.generation.italy.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pizza")
public class PizzaController {
	
	@Autowired
	private PizzaService service;
	
	@Autowired
	private IngredientiService ingredientiService;
	
	@GetMapping("/dettagli/{id}")
	public String detail(@PathVariable("id") Integer id,  Model model) {
		model.addAttribute("pizza", service.getById(id));
		return "/pizza/dettagli";
	}
		
	@GetMapping
	public String list(Model model) {
		model.addAttribute("listpizza", service.findAllSortedByRecent());
		return "/pizza/listpizza";
	}
	
	@GetMapping("/create")
	public String create(Model model) {
		model.addAttribute("edit", false);
		model.addAttribute("pizza", new Pizza());
		model.addAttribute("listaIngredienti", ingredientiService.findAllSortByIngredienti());
		return "/pizza/edit";
	}
	
	@PostMapping("/create")
	public String doCreate(@Valid @ModelAttribute("pizza") Pizza formPizza, 
			BindingResult bindingResult, Model model) {
		// se ci sono errori torni alla form
		if(bindingResult.hasErrors()) {
			model.addAttribute("edit", false);
			model.addAttribute("listaIngredienti", ingredientiService.findAllSortByIngredienti());
			return "/pizza/edit";
		}
		// se non ci sono errori salva i dati e torna alla lista
		service.create(formPizza);
		return "redirect:/pizza";
	}
	
	@GetMapping("/modifica/{id}")
	public String modifica(@PathVariable Integer id, Model model) {
		model.addAttribute("edit", true);
		model.addAttribute("pizza", service.getById(id));
		model.addAttribute("pizzeList", service.findAllSortedByRecent());
		model.addAttribute("listaIngredienti", ingredientiService.findAllSortByIngredienti());
		return "/pizza/edit";
	}
	
	@PostMapping("/modifica/{id}")
	public String doUpdate(@Valid @ModelAttribute("pizza") Pizza formPizza, 
			BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("edit", true);
			model.addAttribute("pizzeList", service.findAllSortedByRecent());
			model.addAttribute("listaIngredienti", ingredientiService.findAllSortByIngredienti());
			return "/pizza/edit";
		}
		service.update(formPizza);
		return "redirect:/pizza";
	}
	
	@GetMapping("/delete/{id}")
	public String doDelete(Model model, @PathVariable("id") Integer id) {
		service.deleteById(id);
		return "redirect:/pizza";
	}
}
