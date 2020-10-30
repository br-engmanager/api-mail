package br.com.tijo.api.mail.service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import br.com.tijo.api.mail.dto.EmailRequestDTO;

@Service
public class EmailService {
	
	private final JavaMailSender mailSender;
	private final TemplateEngine templateEngine;
	
	public EmailService (JavaMailSender mailSender, TemplateEngine templateEngine) {
		this.mailSender = mailSender;
		this.templateEngine = templateEngine;
	}

	public String sendEmailToResetPassword(EmailRequestDTO emailRequestDTO){
		try {
	        MimeMessage message = mailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);
	        
	        Context context = new Context();
	
	        context.setVariable("name", emailRequestDTO.getUserName());
	        context.setVariable("psw", emailRequestDTO.getTempPsw());
	        
	        String body = templateEngine.process("ResetMailTemplate", context);
	        
	        helper.setFrom(new InternetAddress("contato@tijo.com.br"));
	        helper.setTo(emailRequestDTO.getEmail());
	        helper.setSubject("[TIJO] - Redefini\u00E7\u00E3o de Senha");
	        helper.setText(body, true);

            mailSender.send(message);
            
            return "Email enviado com sucesso!";
		}catch(MessagingException messaginExecption) {
			messaginExecption.printStackTrace();
            return messaginExecption.getLocalizedMessage();
        }catch(MailException emailException) { 
        	emailException.printStackTrace();
            return emailException.getLocalizedMessage();
	    }catch (Exception genericException) {
	    	genericException.printStackTrace();
            return "Erro ao enviar email.";
        }
	}
}
