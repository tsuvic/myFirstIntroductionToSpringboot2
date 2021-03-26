package com.example.demo.app.inquiry;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Inquiry;
import com.example.demo.service.InquiryService;

@Controller
@RequestMapping("/inquiry")
public class InquiryController {

	private final InquiryService inquiryService;
	
	@Autowired
	public InquiryController(InquiryService inquiryService) {
		this.inquiryService = inquiryService;
	}
	
	@GetMapping
	public String index(Model model) {
		List<Inquiry> list = inquiryService.getAll();
		model.addAttribute("inquiryList",list);
		model.addAttribute("title","問い合わせ一覧");
		
		return"inquiry/index";
	}
	
	@GetMapping("/form")
	public String form(InquiryForm inquiryForm, Model model) {
		model.addAttribute("title", "問い合わせフォーム");
		return "inquiry/form";
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
			model.addAttribute("title", "問い合わせフォーム");
			return "inquiry/form";
		}
		//insert into the database
		//controllerではformの内容をentityに詰め直す
		//その後、サービスがdaoのメソッドを使って、entityの中身をDBに登録する
		
		Inquiry inquiry = new Inquiry();
		inquiry.setName(inquiryForm.getName());
		inquiry.setEmail(inquiryForm.getEmail());
		inquiry.setContents(inquiryForm.getContents());
		inquiry.setCreated(LocalDateTime.now());
		
		inquiryService.save(inquiry);

		redirectAttributes.addFlashAttribute("complete", "Registerd!");
		return "redirect:/inquiry/form";
	}
	
}
