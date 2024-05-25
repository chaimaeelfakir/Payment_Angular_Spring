package ma.enset.springangular_students.dtos;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;
import ma.enset.springangular_students.entities.PaymentStatus;
import ma.enset.springangular_students.entities.PaymentType;
import ma.enset.springangular_students.entities.Student;
import org.hibernate.internal.build.AllowPrintStacktrace;

import java.time.LocalDate;
@NoArgsConstructor @AllArgsConstructor @Getter @Setter @ToString @Builder
public class PaymentDTO {
    private Long id;
    private LocalDate date;
    private double amount;
    private PaymentType type;
    private PaymentStatus status;
}
