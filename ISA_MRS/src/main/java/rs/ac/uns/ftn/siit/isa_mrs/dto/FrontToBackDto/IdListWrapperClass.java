package rs.ac.uns.ftn.siit.isa_mrs.dto.FrontToBackDto;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class IdListWrapperClass {
    private Collection<Long> list = new ArrayList<>();
}
