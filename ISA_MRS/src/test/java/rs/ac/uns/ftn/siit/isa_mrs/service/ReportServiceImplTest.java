package rs.ac.uns.ftn.siit.isa_mrs.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.AdminDtos.ReportDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.AdminDtos.ReviewDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.RentalProfileDtos.ReviewDtos.ReviewUserDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PageDto;
import rs.ac.uns.ftn.siit.isa_mrs.model.*;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RequestStatus;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.ReviewType;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.UserType;
import rs.ac.uns.ftn.siit.isa_mrs.repository.ReportRepo;
import rs.ac.uns.ftn.siit.isa_mrs.repository.ReservationRepo;
import rs.ac.uns.ftn.siit.isa_mrs.repository.ReviewRepo;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class ReportServiceImplTest {
    @Mock
    private ReportRepo reportRepo;
    @Mock
    private ModelMapper modelMapper = new ModelMapper();
    @Mock
    private ReservationRepo reservationRepo;
    @InjectMocks
    private ReportServiceImpl reportService;

    @Test
    void getReports() {
        Pageable pageable1 = PageRequest.of(0, 1).withSort(Sort.by("timeStamp"));
        Pageable pageable2 = PageRequest.of(1, 1).withSort(Sort.by("timeStamp"));
        List<Report> reports = new ArrayList<>();
        List<ReportDto> dtos = new ArrayList<>();
        Reservation reservation = new Reservation();
        Client client = new Client();
        client.setName("Marko");
        client.setSurname("Markovic");
        client.setUserType(UserType.Client);
        RentalObjectOwner owner = new RentalObjectOwner();
        owner.setName("Petar");
        owner.setSurname("Petrovic");
        owner.setUserType(UserType.Instructor);
        RentalObject rentalObject = new RentalObject();
        rentalObject.setName("Rental");
        rentalObject.setRentalObjectOwner(owner);
        reservation.setRentalObject(rentalObject);
        reservation.setClient(client);

        Report report1 = new Report();
        report1.setAuthor(client);
        report1.setReservation(reservation);
        ReportDto dto1 = new ReportDto();
        ReviewUserDto authorDto1 = new ReviewUserDto();
        authorDto1.setName(client.getName());
        authorDto1.setSurname(client.getSurname());
        dto1.setAuthor(authorDto1);

        Report report2 = new Report();
        report2.setAuthor(owner);
        report2.setReservation(reservation);
        ReportDto dto2 = new ReportDto();
        ReviewUserDto authorDto2 = new ReviewUserDto();
        authorDto2.setName(owner.getName());
        authorDto2.setSurname(owner.getSurname());
        dto2.setAuthor(authorDto2);

        reports.add(report1);
        reports.add(report2);

        dtos.add(dto1);
        dtos.add(dto2);

        Page<Report> page1 = new PageImpl<>(reports.subList(0,1), pageable1, 1);
        Page<Report> page2 = new PageImpl<>(reports.subList(1,2), pageable2, 1);

        when(reportRepo.findAllByStatusAndShowedUp(RequestStatus.Pending, true, pageable1)).thenReturn(page1);
        when(reportRepo.findAllByStatusAndShowedUp(RequestStatus.Pending, true, pageable2)).thenReturn(page2);

        when(modelMapper.map(reports.get(0), ReportDto.class)).thenReturn(dtos.get(0));
        when(modelMapper.map(reports.get(1), ReportDto.class)).thenReturn(dtos.get(1));

        when(reservationRepo.findByReportsIsContaining(reports.get(0))).thenReturn(reservation);
        when(reservationRepo.findByReportsIsContaining(reports.get(1))).thenReturn(reservation);

        ResponseEntity<PageDto<ReportDto>> result1 = reportService.getReports(0, 1);

        assertEquals(result1.getStatusCode(), HttpStatus.OK);
        Assertions.assertNotNull(result1.getBody());
        assertEquals(result1.getBody().getContent().size(), 1);
        assertEquals(result1.getBody().getContent().iterator().next().getSubjectName(), "Petar Petrovic");

        ResponseEntity<PageDto<ReportDto>> result2 = reportService.getReports(1, 1);

        assertEquals(result2.getStatusCode(), HttpStatus.OK);
        Assertions.assertNotNull(result2.getBody());
        assertEquals(result2.getBody().getContent().size(), 1);
        assertEquals(result2.getBody().getContent().iterator().next().getSubjectName(), "Marko Markovic");
    }
}