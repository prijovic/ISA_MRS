package rs.ac.uns.ftn.siit.isa_mrs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.AdminDtos.ReportDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.AdminDtos.ReviewDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PageDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.*;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RequestStatus;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.ReviewType;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.UserType;
import rs.ac.uns.ftn.siit.isa_mrs.repository.ReportRepo;
import rs.ac.uns.ftn.siit.isa_mrs.repository.ReservationRepo;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportServiceImpl implements ReportService{
    private final ReportRepo reportRepo;
    private final ModelMapper modelMapper;
    private final ReservationRepo reservationRepo;
    private final EmailSenderService emailSenderService;

    @Override
    public ResponseEntity<PageDto<ReportDto>> getReports(int page, int pageSize) {
        try {
            Pageable pageable = PageRequest.of(page, pageSize).withSort(Sort.by("timeStamp"));
            Page<Report> reportPage = reportRepo.findAllByStatusAndShowedUp(RequestStatus.Pending, true, pageable);
            return new ResponseEntity<>(reportPageToDto(reportPage), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<PageDto<ReportDto>> getReportsFilter(int page, int pageSize, String userType) {
        try {
            Pageable pageable = PageRequest.of(page, pageSize).withSort(Sort.by("timeStamp"));
            UserType type = UserType.Admin;
            try {
                type = UserType.valueOf(userType);
            } catch (Exception ignored) {}
            Page<Report> reportPage;
            if (type.equals(UserType.Client)) {
                reportPage = reportRepo.findAllByStatusAndShowedUpAndAuthorUserType(RequestStatus.Pending, true, type, pageable);
            } else {
                reportPage = reportRepo.findAllByStatusAndShowedUpAndAuthorUserTypeOrAuthorUserTypeOrAuthorUserType(RequestStatus.Pending, true, UserType.Instructor, UserType.BoatOwner, UserType.VacationRentalOwner, pageable);
            }
            return new ResponseEntity<>(reportPageToDto(reportPage), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<ReportDto> changeReportStatus(Long id, boolean accepted, String response) {
        try {
            Report report = reportRepo.getById(id);
            Reservation reservation = reservationRepo.findByReportsIsContaining(report);
            Client client = reservation.getClient();
            if (accepted) {
                report.setStatus(RequestStatus.Accepted);
                if (report.getAuthor().getUserType().equals(UserType.Client)) {
                    emailSenderService.sendReportResponseNotificationEmailClient(report, response);
                    emailSenderService.sendReportResponseNotificationEmailOwner(report, response);
                } else {
                    emailSenderService.sendPenaltyResponseNotificationEmailClient(report);
                    emailSenderService.sendPenaltyResponseNotificationEmailOwner(report, client.getName() + " " + client.getSurname());
                }
            } else {
                report.setStatus(RequestStatus.Rejected);
                emailSenderService.sendPenaltyResponseNotificationEmailClient(report);
                emailSenderService.sendPenaltyResponseNotificationEmailOwner(report, client.getName() + " " + client.getSurname());
            }
            reportRepo.save(report);
            return new ResponseEntity<>(modelMapper.map(report, ReportDto.class), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private PageDto<ReportDto> reportPageToDto(Page<Report> reportPage) {
        PageDto<ReportDto> result = new PageDto<>();
        Collection<ReportDto> reportDtos = new ArrayList<>();
        reportPage.getContent().forEach(report -> {
            ReportDto reportDto = modelMapper.map(report, ReportDto.class);
            Reservation reservation = reservationRepo.findByReportsIsContaining(report);
            if (report.getAuthor().getUserType().equals(UserType.Client)) {
                RentalObjectOwner owner = reservation.getRentalObject().getRentalObjectOwner();
                reportDto.setSubjectName(owner.getName() + " " + owner.getSurname());
            } else {
                Client client = reservation.getClient();
                reportDto.setSubjectName(client.getName() + " " + client.getSurname());
            }
            reportDtos.add(reportDto);
        });
        result.setContent(reportDtos);
        result.setPages(reportPage.getTotalPages());
        result.setCurrentPage(reportPage.getNumber());
        result.setPageSize(reportPage.getSize());
        return result;
    }
}
