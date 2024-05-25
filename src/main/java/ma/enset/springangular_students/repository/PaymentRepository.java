package ma.enset.springangular_students.repository;


import ma.enset.springangular_students.entities.Payment;
import ma.enset.springangular_students.entities.PaymentStatus;
import ma.enset.springangular_students.entities.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByStudentCode(String code);
    List<Payment> findByStatus(PaymentStatus status);
    List<Payment> findByType(PaymentType type);
}
