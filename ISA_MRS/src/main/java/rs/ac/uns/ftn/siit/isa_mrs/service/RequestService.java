package rs.ac.uns.ftn.siit.isa_mrs.service;

import org.springframework.data.domain.Page;
import rs.ac.uns.ftn.siit.isa_mrs.model.Request;

import java.util.List;

public interface RequestService {
    List<Request> findAllRequests();
    List<Request> findSortedRequestsByTime(String field);
    Page<Request> findRequestsWithPagination(int offset, int pageSize);
    Page<Request> findRequestsWithPaginationSortedByField(int offset, int pageSize, String field);
}
