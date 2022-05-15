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
import rs.ac.uns.ftn.siit.isa_mrs.model.User;
import rs.ac.uns.ftn.siit.isa_mrs.security.JwtGenerator;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService{
    private final JavaMailSender mailSender;
    private final Configuration configuration;
    private final JwtGenerator jwtGenerator;

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

    @Override
    public void sendActivationEmail(User user) throws MessagingException, IOException, TemplateException {
        final String subject = "Account Verification";
        Map<String, Object> model = new HashMap<>();
        List<String> authority = new ArrayList<>();
        authority.add(user.getUserType().name());
        model.put("link", "http://localhost:3000/" + jwtGenerator.generateJwt(user.getEmail(), "", authority, JwtGenerator.TokenPeriod.OneDay) + "/" + jwtGenerator.generateJwt(user.getEmail(), "", authority, JwtGenerator.TokenPeriod.TwoDays));
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED);
        Template template = configuration.getTemplate("successful-registration-email.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        messageHelper.setTo(user.getEmail());
        messageHelper.setText(html, true);
        messageHelper.setSubject(subject);
        mailSender.send(message);
    }
}
