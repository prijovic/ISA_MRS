package rs.ac.uns.ftn.siit.isa_mrs.service;

import org.springframework.http.ResponseEntity;
import rs.ac.uns.ftn.siit.isa_mrs.dto.BackToFrontDto.AdminDtos.ReportDto;
import rs.ac.uns.ftn.siit.isa_mrs.dto.PageDto;

public interface ReportService {
    ResponseEntity<PageDto<ReportDto>> getReports(int page, int pageSize);
    ResponseEntity<PageDto<ReportDto>> getReportsFilter(int page, int pageSize, String userType);
    ResponseEntity<ReportDto> changeReportStatus(Long id, boolean accepted, String response);
}
