package rs.ac.uns.ftn.siit.isa_mrs.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "report_response")
public class ReportResponse extends Response {
    @OneToOne
    private Report report;
}
