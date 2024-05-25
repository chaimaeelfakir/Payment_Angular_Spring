package ma.enset.springangular_students.entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity @AllArgsConstructor @NoArgsConstructor @Builder @ToString @Getter @Setter
public class Payment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private double amount;
    private PaymentType type;
    private PaymentStatus status;
    private String file;
    @ManyToOne
    private Student student; //un payment appartient à un étudiant
}