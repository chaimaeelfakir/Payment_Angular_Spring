package ma.enset.springangular_students.web;

import ma.enset.springangular_students.entities.Payment;
import ma.enset.springangular_students.entities.PaymentStatus;
import ma.enset.springangular_students.entities.PaymentType;
import ma.enset.springangular_students.entities.Student;
import ma.enset.springangular_students.repository.PaymentRepository;
import ma.enset.springangular_students.repository.StudentRepository;
import ma.enset.springangular_students.services.PaymentService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin("*")
public class PaymentRestController {
    //Injection des dependances via un constructeur
    private final StudentRepository studentRepository;
    private final PaymentRepository paymentRepository;
    private PaymentService paymentService;

    public PaymentRestController(StudentRepository studentRepository, PaymentRepository paymentRepository, PaymentService paymentService) {
        this.studentRepository = studentRepository;
        this.paymentRepository = paymentRepository;
        this.paymentService = paymentService;
    }



    @GetMapping("/payments")
    public List<Payment> allPayments(){
        return paymentRepository.findAll();
    }

    @GetMapping("/payments/{id}")
    public Payment getPaymentById(@PathVariable Long id) {
        return paymentRepository.findById(id).orElse(null); // Gestion de l'absence de paiement
    }

    @GetMapping("/students")
    public List<Student> allStudents(){
        return studentRepository.findAll();
    }

    @GetMapping("/students/{code}")
    public Student getStudentByCode(@PathVariable String code){
        return studentRepository.findByCode(code);
    }

    @GetMapping("/studentsByProgrammeId")
    public List<Student> getStudentByProgrammeCode(@RequestParam String programmeId){
        return studentRepository.findByProgrammeId(programmeId);
    }

    @GetMapping("/students/{code}/payments")
    public List<Payment> paymentsByStudent(@PathVariable String code){
        return paymentRepository.findByStudentCode(code);
    }

    @GetMapping("/payments/ByStatus")
    public List<Payment> paymentsByStatus(@RequestParam PaymentStatus status){
        return paymentRepository.findByStatus(status);
    }

    @GetMapping("/payments/ByType")
    public List<Payment> paymentsByType(@RequestParam PaymentType type){
        return paymentRepository.findByType(type);
    }

    @PutMapping("/payments/{id}")
    public Payment updatePaymentStatus(@RequestParam PaymentStatus status, @PathVariable Long id){
        return paymentService.updatePaymentStatus(status,id);
    }


    @PostMapping(path = "/payments", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Payment savePayment(@RequestParam("file") MultipartFile file,
                               @RequestParam("date") LocalDate date,
                               @RequestParam("amount") double amount,
                               @RequestParam("type") PaymentType type,
                               @RequestParam("studentCode") String studentCode ) throws IOException {
        return this.paymentService.savePayment(file,date,amount,type,studentCode);
    }


    //consulter un fichier selon paymentID
    @GetMapping(path = "/paymentFile/{paymentId}", produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] getPaymentFile(@PathVariable Long paymentId) throws IOException {
       return paymentService.getPaymentFile(paymentId);
    }
}
