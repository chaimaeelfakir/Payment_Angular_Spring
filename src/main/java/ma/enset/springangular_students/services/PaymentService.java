package ma.enset.springangular_students.services;

import ma.enset.springangular_students.entities.Payment;
import ma.enset.springangular_students.entities.PaymentStatus;
import ma.enset.springangular_students.entities.PaymentType;
import ma.enset.springangular_students.entities.Student;
import ma.enset.springangular_students.repository.PaymentRepository;
import ma.enset.springangular_students.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

@Service
@Transactional
public class PaymentService {
    private StudentRepository studentRepository;
    private PaymentRepository paymentRepository;

    public PaymentService(StudentRepository studentRepository, PaymentRepository paymentRepository) {
        this.studentRepository = studentRepository;
        this.paymentRepository = paymentRepository;
    }

    //Lorsqu'il y a beaucoup de traitement c mieux le faire ici et pas directement en PaymentRestController
    public Payment savePayment( MultipartFile file,
                                LocalDate date,
                                double amount,
                                PaymentType type,
                                String studentCode ) throws IOException {
        // Envoyer le fichier vers un dossier
        Path folderPath = Paths.get(System.getProperty("user.home"),"enset-doc","payments");
        if(!Files.exists(folderPath)){
            Files.createDirectories(folderPath);
        }
        String fileName = UUID.randomUUID().toString();
        Path filePath = Paths.get(System.getProperty("user.home"),"enset-doc","payments",fileName+".pdf");
        Files.copy(file.getInputStream(),filePath);
        Student student = studentRepository.findByCode(studentCode);
        if (student != null) {
            Payment payment = Payment.builder()
                    .date(date)
                    .type(type)
                    .student(student)
                    .amount(amount)
                    .status(PaymentStatus.CREATED)
                    .file(filePath.toUri().toString())
                    .build();
            return paymentRepository.save(payment);
        } else {
            throw new IllegalArgumentException("Student with code " + studentCode + " not found");
        }
    }

    public Payment updatePaymentStatus( PaymentStatus status, Long id){
        Payment payment = paymentRepository.findById(id).orElse(null); // Gestion de l'absence de paiement
        if(payment != null) {
            payment.setStatus(status);
            return paymentRepository.save(payment);
        }
        return null;
    }


    public byte[] getPaymentFile(Long paymentId) throws IOException {
        Payment payment = paymentRepository.findById(paymentId).get();
        return Files.readAllBytes(Path.of(URI.create(payment.getFile())));
    }


}
