package rs.ac.uns.ftn.siit.isa_mrs.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.siit.isa_mrs.controller.RequestController;
import rs.ac.uns.ftn.siit.isa_mrs.model.Report;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.RequestStatus;
import rs.ac.uns.ftn.siit.isa_mrs.model.enumeration.UserType;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

public interface ReportRepo extends JpaRepository<Report, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="0")})
    Page<Report> findAllByStatusAndShowedUp(RequestStatus status, boolean showedUp, Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="0")})
    Page<Report> findAllByStatusAndShowedUpAndAuthorUserType(RequestStatus status, boolean showedUp, UserType userType, Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="0")})
    Page<Report> findAllByStatusAndShowedUpAndAuthorUserTypeOrAuthorUserTypeOrAuthorUserType(RequestStatus status, boolean showedUp,  UserType userType1, UserType userType2, UserType userType3, Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="0")})
    @Query(value = "SELECT report FROM Report report WHERE report.id = :id")
    Report getReport(@Param("id") long id);
}
