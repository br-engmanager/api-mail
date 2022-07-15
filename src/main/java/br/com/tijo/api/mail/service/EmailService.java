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

import br.com.tijo.api.mail.dto.ContactEmailRequestDTO;
import br.com.tijo.api.mail.dto.ContactEmpRequestDTO;
import br.com.tijo.api.mail.dto.EmailRequestDTO;

@Service
public class EmailService {
	
	private final String emailContato = "contato@tijo.com.br";
	
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
	        helper.setSubject("[Compras A2] - Redefini\u00E7\u00E3o de Senha");
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

	public String sendEmailConvite(EmailRequestDTO emailRequestDTO){
		try {
	        MimeMessage message = mailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);
	        
	        Context context = new Context();
	
	        String url = "https://www.tijo.com.br?sub=cadastro";
	        
	        String body = templateEngine.process("ConviteMailTemplate.html", context);
	        
	        body = body.replace("{url}", url);
	        
	        helper.setFrom(new InternetAddress("contato@tijo.com.br"));
	        helper.setTo(emailRequestDTO.getEmail());
	        helper.setSubject("[TIJO] - Convite");
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

	
	public void sendContactEmail(ContactEmailRequestDTO emailRequestDTO) {
		sendEmailToContact(emailRequestDTO);
		sendContactEmailConfirm(emailRequestDTO.getEmail());
	}
	
	public void sendContactEmpEmail(ContactEmpRequestDTO emailRequestDTO) {
		sendDataEmailFromEmp(emailRequestDTO);
		sendContactEmailConfirm(emailRequestDTO.getEmail());
	}

	private String sendEmailToContact(ContactEmailRequestDTO emailRequestDTO){
		try {
	        MimeMessage message = mailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);

	        StringBuilder sb = new StringBuilder();
	        sb.append("Nome: ");
	        sb.append(emailRequestDTO.getName());
	        sb.append("<br/><br/>Email: ");
	        sb.append(emailRequestDTO.getEmail());
	        sb.append("<br/><br/>Mensagem: ");
	        sb.append(emailRequestDTO.getMessage());

	        
	        helper.setFrom(new InternetAddress("contato@tijo.com.br"));
	        helper.setTo(emailContato);
	        helper.setSubject("[TIJO] - Contato");
	        helper.setText(sb.toString(), true);

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

	private String sendDataEmailFromEmp(ContactEmpRequestDTO emailRequestDTO){
		try {
	        MimeMessage message = mailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);

	        StringBuilder sb = new StringBuilder();
	        sb.append("Empresa: ");
	        sb.append(emailRequestDTO.getEmpresa());
	        sb.append("<br/><br/>Nome: ");
	        sb.append(emailRequestDTO.getName());
	        sb.append("<br/><br/>Email: ");
	        sb.append(emailRequestDTO.getEmail());
	        sb.append("<br/><br/>Telefone: ");
	        sb.append(emailRequestDTO.getTelefone());

	        
	        helper.setFrom(new InternetAddress("contato@tijo.com.br"));
	        helper.setTo(emailContato);
	        helper.setSubject("[TIJO] - NOVO PROFISSIONAL");
	        helper.setText(sb.toString(), true);

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

	
	public String sendContactEmailConfirm(String email){
		try {
	        MimeMessage message = mailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);
	        
	        Context context = new Context();
	
	        String body = templateEngine.process("ContactTemplate.html", context);
	        
	        helper.setFrom(new InternetAddress("contato@tijo.com.br"));
	        helper.setTo(email);
	        helper.setSubject("[TIJO] - Mensagem recebida");
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
