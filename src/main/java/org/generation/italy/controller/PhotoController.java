package org.generation.italy.controller;

import java.io.IOException;
import java.security.Principal;

import javax.validation.Valid;

import org.generation.italy.model.Photo;
import org.generation.italy.model.PhotoForm;
import org.generation.italy.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/gallery")
public class PhotoController {

	@Autowired
	private PhotoService service;
	
	@GetMapping
	public String index(Model model) {
		model.addAttribute("photo", new PhotoForm());
		model.addAttribute("list", service.findAll());
		return "index";
	}
	
	@PostMapping("/create")
	public String create(@Valid @ModelAttribute("photo") PhotoForm photo, 
			BindingResult bindingResult, 
			RedirectAttributes redirectAttributes,
			Model model, Principal principal) {
		// se ci sono errori ritorno la form con gli errori
		if(photo.getContent() == null || photo.getContent().isEmpty()) {
			bindingResult.addError(new ObjectError("content", "The Photo File is mandatory"));
		}
		if(bindingResult.hasErrors()) {
			model.addAttribute("list", service.findAll());
			return "index";
		}
		// se non ci sono errori salvo i dati e redirect alla lista
		try {
			service.create(photo, principal.getName());
			redirectAttributes.addFlashAttribute("successMessage", "Photo added!");
		} catch (IOException e) {
			redirectAttributes.addFlashAttribute("errorMessage", "Unable to save the photo");
			e.printStackTrace();
		}
		return "redirect:/gallery";
		
	}
	
	@RequestMapping(value = "/{id}/photo", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> getPhotoContent(@PathVariable Integer id){
		Photo photo = service.getById(id);
		byte[] photoContent = photo.getContent();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
		return new ResponseEntity<byte[]>(photoContent, headers, HttpStatus.OK);
	}
}
