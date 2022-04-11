package rs.ac.uns.ftn.siit.isa_mrs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.siit.isa_mrs.model.Request;
import rs.ac.uns.ftn.siit.isa_mrs.repository.RequestRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RequestServiceImpl implements RequestService {
    private final RequestRepo requestRepo;

    @Override
    public List<Request> findAllRequests() {
        return requestRepo.findAll();
    }

    @Override
    public List<Request> findSortedRequestsByTime(String field) {
        return requestRepo.findAll(Sort.by(Sort.Direction.DESC, field));
    }

    @Override
    public Page<Request> findRequestsWithPagination(int offset, int pageSize) {
        return requestRepo.findAll(PageRequest.of(offset, pageSize));
    }

    @Override
    public Page<Request> findRequestsWithPaginationSortedByField(int offset, int pageSize, String field) {
        return requestRepo.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
    }
}
