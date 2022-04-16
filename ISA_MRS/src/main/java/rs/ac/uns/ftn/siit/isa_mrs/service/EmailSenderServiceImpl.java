package rs.ac.uns.ftn.siit.isa_mrs.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import rs.ac.uns.ftn.siit.isa_mrs.model.Request;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService{
    private final JavaMailSender mailSender;
    private final Configuration configuration;

    @Override
    public void sendRequestHandledEmail(Request request, Map<String, Object> model) throws MessagingException, IOException, TemplateException {
        final String email = request.getUser().getEmail();
        final String subject = "Your " + request.getType().toString().toLowerCase() + " request has been " + request.getStatus().toString().toLowerCase() + ".";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED);
        Template template = configuration.getTemplate("request-handled-email.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        messageHelper.setTo(email);
        messageHelper.setText(html, true);
        messageHelper.setSubject(subject);
        mailSender.send(message);
    }
}
