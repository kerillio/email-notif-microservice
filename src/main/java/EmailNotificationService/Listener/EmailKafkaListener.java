package EmailNotificationService.Listener;

import EmailNotificationService.DTO.EmailMessage;
import EmailNotificationService.Service.MailSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class EmailKafkaListener {

    private static final Logger log = LoggerFactory.getLogger(EmailKafkaListener.class);
    private final MailSenderService mailSenderService;

    public EmailKafkaListener(MailSenderService mailSenderService) {
        this.mailSenderService = mailSenderService;
    }

    @KafkaListener(topics = "email-topic", groupId = "email-sender-group")
    public void onMessage(EmailMessage dto) {
        log.info("Получено сообщение для " + dto.to());
        mailSenderService.sendMail(dto);
    }

}
