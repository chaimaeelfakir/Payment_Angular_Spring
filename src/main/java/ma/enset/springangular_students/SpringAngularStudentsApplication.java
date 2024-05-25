package ma.enset.springangular_students;

import ma.enset.springangular_students.entities.Payment;
import ma.enset.springangular_students.entities.PaymentStatus;
import ma.enset.springangular_students.entities.PaymentType;
import ma.enset.springangular_students.entities.Student;
import ma.enset.springangular_students.repository.PaymentRepository;
import ma.enset.springangular_students.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

@SpringBootApplication
public class SpringAngularStudentsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAngularStudentsApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository, PaymentRepository paymentRepository){
        return args -> {
            studentRepository.save(Student.builder().id(UUID.randomUUID().toString()).
                    firstName("Mohammed").code("1234").lastName("Ait Jeddi").programmeId("GLSID")
                    .build());

            studentRepository.save(Student.builder().id(UUID.randomUUID().toString()).
                    firstName("Imane").code("2937").lastName("Zouba").programmeId("BDCC")
                    .build());

            studentRepository.save(Student.builder().id(UUID.randomUUID().toString()).
                    firstName("Assia").code("4905").lastName("Ait Jeddi").programmeId("SDIA")
                    .build());

            studentRepository.save(Student.builder().id(UUID.randomUUID().toString()).
                    firstName("Najat").code("3234").lastName("Hrimo").programmeId("MRMI")
                    .build());

            PaymentType[] paymentTypes = PaymentType.values();
            Random random = new Random();
            studentRepository.findAll().forEach(st->{
                for(int i = 0; i<10; i++) {
                    int index = random.nextInt(paymentTypes.length);
                    Payment payment = Payment.builder()
                            .amount(1000+(int) (Math.random()*20000))
                            .type(paymentTypes[index])
                            .status(PaymentStatus.CREATED)
                            .date(LocalDate.now())
                            .student(st)
                            .build();
                    paymentRepository.save(payment);
                }
            });
        };
    }
}
