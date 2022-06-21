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
import rs.ac.uns.ftn.siit.isa_mrs.model.*;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RentalObjectType;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RequestStatus;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.UserType;
import rs.ac.uns.ftn.siit.isa_mrs.security.JwtGenerator;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
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
        model.put("link", "http://localhost:3000/token" + jwtGenerator.generateJwt(user.getEmail(), "", authority, JwtGenerator.TokenPeriod.OneDay) + "/refresh" + jwtGenerator.generateJwt(user.getEmail(), "", authority, JwtGenerator.TokenPeriod.TwoDays));
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED);
        Template template = configuration.getTemplate("successful-registration-email.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        messageHelper.setTo(user.getEmail());
        messageHelper.setText(html, true);
        messageHelper.setSubject(subject);
        mailSender.send(message);
    }

    @Override
    public void sendActivationNotificationEmail(User user, String password) throws MessagingException, IOException, TemplateException {
        final String subject = "Account Creation";
        Map<String, Object> model = new HashMap<>();
        model.put("password", password);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED);
        Template template = configuration.getTemplate("added-user-email.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        messageHelper.setTo(user.getEmail());
        messageHelper.setText(html, true);
        messageHelper.setSubject(subject);
        mailSender.send(message);
    }

    @Override
    public void sendReviewStatusNotificationEmail(Review review, String commentedObject) throws MessagingException, IOException, TemplateException {
        final String email = review.getReservation().getClient().getEmail();
        final String subject = "Accepted Review";
        Map<String, Object> model = new HashMap<>();
        model.put("date", review.getTimeStamp().format(DateTimeFormatter.ofPattern("dd.MM.yyyy. hh:mm")));
        model.put("commented", commentedObject);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED);
        Template template = configuration.getTemplate("review-accepted-email.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        messageHelper.setTo(email);
        messageHelper.setText(html, true);
        messageHelper.setSubject(subject);
        mailSender.send(message);
    }

    @Override
    public void sendPenaltyResponseNotificationEmailOwner(Report report, String commentedObject) throws MessagingException, IOException, TemplateException {
        final String email = report.getReservation().getRentalObject().getRentalObjectOwner().getEmail();
        final String subject = "Report Response";
        Map<String, Object> model = new HashMap<>();
        model.put("status", report.getStatus().equals(RequestStatus.Accepted) ? "Accepted":"Rejected");
        model.put("statusSmall", report.getStatus().equals(RequestStatus.Accepted) ? "accepted":"rejected");
        model.put("get", report.getStatus().equals(RequestStatus.Accepted) ? "got":"did not get");
        model.put("date", report.getTimeStamp().format(DateTimeFormatter.ofPattern("dd.MM.yyyy. hh:mm")));
        model.put("commented", commentedObject);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED);
        Template template = configuration.getTemplate("owner-penaulty-response-email.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        messageHelper.setTo(email);
        messageHelper.setText(html, true);
        messageHelper.setSubject(subject);
        mailSender.send(message);
    }

    @Override
    public void sendReportResponseNotificationEmailClient(Report report, String response) throws MessagingException, IOException, TemplateException {
        final String email = report.getReservation().getClient().getEmail();
        final String subject = "Report Response";
        Map<String, Object> model = new HashMap<>();
        model.put("response", response);
        model.put("date", report.getTimeStamp().format(DateTimeFormatter.ofPattern("dd.MM.yyyy. hh:mm")));
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED);
        Template template = configuration.getTemplate("client-report-response-email.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        messageHelper.setTo(email);
        messageHelper.setText(html, true);
        messageHelper.setSubject(subject);
        mailSender.send(message);
    }

    @Override
    public void sendReportResponseNotificationEmailOwner(Report report, String response) throws MessagingException, IOException, TemplateException {
        final String email = report.getReservation().getRentalObject().getRentalObjectOwner().getEmail();
        final String subject = "Report Response";
        Map<String, Object> model = new HashMap<>();
        model.put("response", response);
        model.put("report", report.getComment());
        model.put("date", report.getTimeStamp().format(DateTimeFormatter.ofPattern("dd.MM.yyyy. hh:mm")));
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED);
        Template template = configuration.getTemplate("owner-report-notification-email.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        messageHelper.setTo(email);
        messageHelper.setText(html, true);
        messageHelper.setSubject(subject);
        mailSender.send(message);
    }

    @Override
    public void sendPenaltyResponseNotificationEmailClient(Report report) throws MessagingException, IOException, TemplateException {
        final String email = report.getReservation().getClient().getEmail();
        final String subject = "Report Response";
        Map<String, Object> model = new HashMap<>();
        model.put("status", report.getStatus().equals(RequestStatus.Accepted) ? "Accepted":"Rejected");
        model.put("give", report.getStatus().equals(RequestStatus.Accepted) ? "to give":"not to give");
        model.put("date", report.getTimeStamp().format(DateTimeFormatter.ofPattern("dd.MM.yyyy. hh:mm")));
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED);
        Template template = configuration.getTemplate("client-penaulty-response-email.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        messageHelper.setTo(email);
        messageHelper.setText(html, true);
        messageHelper.setSubject(subject);
        mailSender.send(message);
    }

    @Override
    public void sendSuccessfulReservationEmail(Reservation reservation) throws MessagingException, IOException, TemplateException {
        final String email = reservation.getClient().getEmail();
        final String subject = "Successful Reservation";
        Map<String, Object> model = new HashMap<>();
        model.put("rental", reservation.getRentalObject().getName());
        model.put("appointment", getSuccessfulReservationAppointment(reservation));
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED);
        Template template = configuration.getTemplate("client-successful-reservation-email.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        messageHelper.setTo(email);
        messageHelper.setText(html, true);
        messageHelper.setSubject(subject);
        mailSender.send(message);
    }

    private String getSuccessfulReservationAppointment(Reservation reservation) {
        String initDate = reservation.getInitDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy."));
        String initTime = reservation.getInitDate().format(DateTimeFormatter.ofPattern("HH:mm"));
        String termDate = reservation.getTermDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy."));
        String termTime = reservation.getTermDate().format(DateTimeFormatter.ofPattern("HH:mm"));
        if(reservation.getRentalObject().getRentalObjectType() == RentalObjectType.Adventure)
            return initDate + " from " + initTime + " to " + termTime;
        if(initDate.equals(termDate))
            return initDate;
        return initDate + '-' + termDate;
    }

}
