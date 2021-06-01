package br.com.tijo.api.mail.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.tijo.api.mail.dto.ContactEmailRequestDTO;
import br.com.tijo.api.mail.dto.ContactEmpRequestDTO;
import br.com.tijo.api.mail.dto.EmailRequestDTO;
import br.com.tijo.api.mail.service.EmailService;

@RestController
@RequestMapping("/email")
public class EmailController {

	private final EmailService emailService;
	
	public EmailController(EmailService emailService) {
		this.emailService = emailService;
	}
	
	@PostMapping("/reset")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public String sendEmailToResetPassword(@RequestBody EmailRequestDTO dtoRequest) {
		return emailService
				.sendEmailToResetPassword(dtoRequest);
	}

	@PostMapping("/invite")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public String sendEmailInvite(@RequestBody EmailRequestDTO dtoRequest) {
		return emailService
				.sendEmailConvite(dtoRequest);
	}

	@PostMapping("/contact")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public String sendEmailInvite(@RequestBody ContactEmailRequestDTO dtoRequest) {
		emailService
				.sendContactEmail(dtoRequest);
		return "{}";
	}

	@PostMapping("/emp")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public String sendEmailEmp(@RequestBody ContactEmpRequestDTO dtoRequest) {
		emailService
				.sendContactEmpEmail(dtoRequest);
		return "{}";
	}

}
