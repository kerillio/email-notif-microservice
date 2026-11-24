package EmailNotificationService.Service;

import EmailNotificationService.DTO.EmailMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService {

    private static final Logger log = LoggerFactory.getLogger(MailSenderService.class);
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username:noreply@study-project.local}")
    private String fromAddress;

    public MailSenderService (JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMail (EmailMessage dto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromAddress);
        message.setTo("mkirilltlt@gmail.com"); //тут я понимаю, что надо ставить dto.to(), но ставлю личный адрес для проверки работоспособности
        message.setSubject(dto.subject());
        message.setText(dto.to() + " : " + dto.body());

        mailSender.send(message);
        log.info("Сообщение для " + dto.to() + " успешно отправлено");

    }

}
