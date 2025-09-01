package co.credit.app.r2dbc.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "loan")
public class LoanEntity {
    @Id
    private Long id;
    private String document;
    private String email;
    private Double amount;
    private Integer term;
    @Column(name = "status_id", nullable = false)
    private Integer statusId;
    @Column(name = "loand_type_id", nullable = false)
    private Integer loanTypeId;
}
