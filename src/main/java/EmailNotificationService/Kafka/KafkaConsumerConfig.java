package EmailNotificationService.Kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.internals.Topic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
public class KafkaConsumerConfig {

    //Основной топик, до 3-х копий
    @Bean
    public NewTopic emailTopic() {
        return TopicBuilder.name("email-topic")
                .partitions(3)
                .replicas(1)
                .build();
    }

    //Запасной топик для неотправок
    public NewTopic emailDltTopic() {
        return TopicBuilder.name("email-topic-dlt")
                .partitions(1)
                .replicas(1)
                .build();
    }

    //Обрабатываем ошибки: до 3 ошибок, таймаут 2 секунды
    @Bean
    public DefaultErrorHandler errorHandler(KafkaTemplate <Object, Object> template) {
        DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(template);
        return new DefaultErrorHandler(recoverer, new FixedBackOff(2000L, 3L));
    }


}
