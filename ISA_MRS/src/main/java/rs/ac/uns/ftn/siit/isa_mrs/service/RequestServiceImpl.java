package rs.ac.uns.ftn.siit.isa_mrs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.siit.isa_mrs.model.Request;
import rs.ac.uns.ftn.siit.isa_mrs.repository.RequestRepo;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RequestServiceImpl implements RequestService {

    private final RequestRepo requestRepo;

    @Override
    public Optional<Request> getRequest(Long id){
        log.info("Getting request by id {}.", id);
        return requestRepo.findById(id);
    }

    @Override
    public Request saveRequest(Request request) {
        log.info("Adding new request with id {}.", request.getId());
        return requestRepo.save(request);
    }
}
