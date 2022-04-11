package rs.ac.uns.ftn.siit.isa_mrs.service;

import rs.ac.uns.ftn.siit.isa_mrs.model.Request;

import java.util.Collection;
import java.util.Optional;

public interface RequestService {
    Optional<Request> getRequest(Long id);
    Request saveRequest(Request request);
}
