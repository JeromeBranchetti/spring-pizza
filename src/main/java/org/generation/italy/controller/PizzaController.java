package org.generation.italy.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/pizza")
public class PizzaController {
	
	@Autowired
	private PizzaService service;
	
	@Autowired
	private IngredientiService ingredientiService;
	

	
	@GetMapping
	public String list(Model model) {
		model.addAttribute("listpizza", service.findAllSortedByRecent());
		return "/pizza/listpizza";
	}
	
	@GetMapping("/create")
	public String create(Model model) {
		model.addAttribute("pizza", new Pizza());
		return "/pizza/edit";
	}
	
	@PostMapping("/create")
	public String doCreate(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model) {
		// se ci sono errori torni alla form
		if(bindingResult.hasErrors()) {
			model.addAttribute("edit", false);
			model.addAttribute("ingredientiList", ingredientiService.findAllSortByIngrediente());
			return "/pizza/edit";
		}
		// se non ci sono errori salva i dati e torna alla lista
		service.create(formPizza);
		return "redirect:/pizza";
	}
}
