package rs.ac.uns.ftn.siit.isa_mrs.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.AdminDtos.ReportDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PageDto;
import rs.ac.uns.ftn.siit.isa_mrs.service.ReportService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/Reports")
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/getReports")
    public ResponseEntity<PageDto<ReportDto>> getReports(@RequestParam int page, @RequestParam int pageSize) {
        return reportService.getReports(page, pageSize);
    }

    @GetMapping("/getReportsFilter")
    public ResponseEntity<PageDto<ReportDto>> getReportsFilter(@RequestParam int page, @RequestParam int pageSize, @RequestParam String userType) {
        return reportService.getReportsFilter(page, pageSize, userType);
    }

    @PutMapping("/manageReport")
    public ResponseEntity<ReportDto> changeReportStatus(@RequestParam Long id, @RequestParam boolean accepted, @RequestParam String response) {
        return reportService.changeReportStatus(id, accepted, response);
    }
}
