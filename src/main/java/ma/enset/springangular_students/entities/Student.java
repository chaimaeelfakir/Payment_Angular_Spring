package ma.enset.springangular_students.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity  @AllArgsConstructor @NoArgsConstructor @Getter @Setter @Builder @ToString
public class Student {
    @Id
    private String id;
    private  String firstName;
    private  String lastName;
    @Column(unique = true)
    private String code; //@Column(unique = true) : code d'étudiant est unique
    private String programmeId;
    private String photo;
}
