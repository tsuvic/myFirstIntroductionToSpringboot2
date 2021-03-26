package com.example.demo.app.inquiry;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/inquiry")
public class InquiryController {

	@GetMapping
	public String index(Model model) {
		//hands-on
		return "inquiry/index";
	}
	
	@GetMapping("/form")
	public String form(InquiryForm inquiryForm,
			Model model,
			@ModelAttribute("compelete") String complete) {
		model.addAttribute("title", "Inquiry Form");
		return "inquriy/form";
	}	
	
	@PostMapping("/form")
	public String formGoBack(InquiryForm inquiryForm, Model model) {
		model.addAttribute("title", "問い合わせフォーム");
		return "inquiry/form";
	}
	
	@PostMapping("/confirm")
	public String confirm(@Validated InquiryForm inquiryForm,
			BindingResult result, 
			Model model){
		if(result.hasErrors()) {
		model.addAttribute("title", "問い合わせフォーム");
		return "inquiry/form";
		}
		
		model.addAttribute("title", "確認ページ");
		return "inquiry/confirm";
	}
	
	@PostMapping("/complete")
	public String complete(@Validated InquiryForm inquiryForm,
			BindingResult result,
			Model model,
			RedirectAttributes redirectAttributes){
		if(result.hasErrors()) {
			model.addAttribute("title", "Inquiry Form");
			return "inquiry/form";
		}
		//insert into the database
		//controllerではformの内容をentityに詰め直す
		//その後、サービスがdaoのメソッドを使って、entityの中身をDBに登録する
		
		
		redirectAttributes.addFlashAttribute("complete", "Registerd!");
		return "redirect:/inquiry/form";
	}
	
}
