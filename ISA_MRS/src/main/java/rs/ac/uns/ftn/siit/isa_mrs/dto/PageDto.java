package rs.ac.uns.ftn.siit.isa_mrs.dto;

import lombok.Data;

import java.util.Collection;

@Data
public class PageDto<T> {
    private Collection<T> content;
    private Integer pages;
    private Integer currentPage;
    private Integer pageSize;
}
