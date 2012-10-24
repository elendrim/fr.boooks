package org.boooks.service.impl;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.boooks.db.entity.TempKey;
import org.boooks.db.entity.UserEntity;
import org.boooks.service.IMailRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

@Component
public class MailRegistrationService implements IMailRegistrationService {

	@Value("${mail.activation.url}")
	private String activationUrl;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private VelocityEngine velocityEngine;

	@Override
	public void register(final UserEntity user, final TempKey tempkey) {

		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				message.setTo(user.getEmail());
				message.setFrom("no-reply@boooks.fr"); // could be
				message.setSubject("Activation de compte www.boooks.fr");
															// parameterized...
				Object[] arguments = {user.getEmail(), tempkey.getTempKeyPK().getTempkey()};
				String url = MessageFormat.format(activationUrl, arguments);
				
				Map<String, Object> model = new HashMap<String, Object>();
				model.put("user", user);
				model.put("url", url);
				String text = VelocityEngineUtils.mergeTemplateIntoString(
						velocityEngine, "mail/registration-confirmation.vm",
						model);
				message.setText(text, true);
			}
		};
		
		mailSender.send(preparator);
	}
	
}