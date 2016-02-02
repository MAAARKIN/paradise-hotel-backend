package br.com.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.backend.model.Email;

@CrossOrigin
@RestController
@RequestMapping(value="/contacts")
public class ContactController {
	
	@Autowired private JavaMailSender mailer;

	/**
	 * Responsável pelo envio de email através do SimpleMailMessage  
	 * @param email
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<String> sendMessage(@RequestBody Email email) {
		System.out.println(email.getMessage());
		SimpleMailMessage content = new SimpleMailMessage();
		
		content.setTo(email.getTo());
		content.setSubject(email.getMessage());
		this.mailer.send(content);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<String> Hello() {
		System.out.println("Hello");
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
