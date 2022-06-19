package rs.ac.uns.ftn.siit.isa_mrs.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.siit.isa_mrs.controller.RequestController;
import rs.ac.uns.ftn.siit.isa_mrs.model.Report;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RequestStatus;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.UserType;

public interface ReportRepo extends JpaRepository<Report, Long> {
    Page<Report> findAllByStatusAndShowedUp(RequestStatus status, boolean showedUp, Pageable pageable);
    Page<Report> findAllByStatusAndShowedUpAndAuthorUserType(RequestStatus status, boolean showedUp, UserType userType, Pageable pageable);
    Page<Report> findAllByStatusAndShowedUpAndAuthorUserTypeOrAuthorUserTypeOrAuthorUserType(RequestStatus status, boolean showedUp,  UserType userType1, UserType userType2, UserType userType3, Pageable pageable);

}
