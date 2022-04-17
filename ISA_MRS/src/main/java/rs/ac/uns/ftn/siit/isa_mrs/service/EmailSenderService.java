package rs.ac.uns.ftn.siit.isa_mrs.service;

import freemarker.template.TemplateException;
import rs.ac.uns.ftn.siit.isa_mrs.model.Request;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Map;

public interface EmailSenderService {
    void sendRequestHandledEmail(Request request, Map<String, Object> model) throws MessagingException, IOException, TemplateException;
}
