package rs.ac.uns.ftn.siit.isa_mrs.dto;

import lombok.Data;

import java.util.Collection;

@Data
public class PageDto<T> {
    Collection<T> content;
    Integer pages;
    Integer currentPage;
    Integer pageSize;
}