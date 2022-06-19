package rs.ac.uns.ftn.siit.isa_mrs.service;

import freemarker.template.TemplateException;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.AdminDtos.ReviewDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.Report;
import rs.ac.uns.ftn.siit.isa_mrs.model.Request;
import rs.ac.uns.ftn.siit.isa_mrs.model.Review;
import rs.ac.uns.ftn.siit.isa_mrs.model.User;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Map;

public interface EmailSenderService {
    void sendRequestHandledEmail(Request request, Map<String, Object> model) throws MessagingException, IOException, TemplateException;
    void sendActivationEmail(User user) throws MessagingException, IOException, TemplateException;
    void sendActivationNotificationEmail(User user, String password) throws MessagingException, IOException, TemplateException;
    void sendReviewStatusNotificationEmail(Review review, String commentedObject) throws MessagingException, IOException, TemplateException;
    void sendPenaltyResponseNotificationEmailOwner(Report report, String commentedObject) throws MessagingException, IOException, TemplateException;
    void sendReportResponseNotificationEmailClient(Report report, String response) throws MessagingException, IOException, TemplateException;
    void sendReportResponseNotificationEmailOwner(Report report, String response) throws MessagingException, IOException, TemplateException;
    void sendPenaltyResponseNotificationEmailClient(Report report) throws MessagingException, IOException, TemplateException;
}
