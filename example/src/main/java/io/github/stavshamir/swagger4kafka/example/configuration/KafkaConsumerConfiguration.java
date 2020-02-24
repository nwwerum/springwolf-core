package io.github.stavshamir.swagger4kafka.example.configuration;

import com.google.common.collect.ImmutableMap;
import io.github.stavshamir.swagger4kafka.example.dtos.ExamplePayloadDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfiguration {

    private final String BOOTSTRAP_SERVERS;

    public KafkaConsumerConfiguration(@Value("${kafka.bootstrap.servers}") String bootstrapServers) {
        this.BOOTSTRAP_SERVERS = bootstrapServers;
    }

    @Bean
    public Map<String, Object> consumerConfiguration() {
        return ImmutableMap.<String, Object>builder()
                .put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS)
                .put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class)
                .put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class)
                .put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
                .put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true)
                .put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100")
                .put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000")
                .put(ConsumerConfig.GROUP_ID_CONFIG, "example-group-id")
                .build();
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ExamplePayloadDto> exampleKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ExamplePayloadDto> containerFactory = new ConcurrentKafkaListenerContainerFactory<>();

        DefaultKafkaConsumerFactory<String, ExamplePayloadDto> consumerFactory =
                new DefaultKafkaConsumerFactory<>(consumerConfiguration(), new StringDeserializer(), new JsonDeserializer<>(ExamplePayloadDto.class));
        containerFactory.setConsumerFactory(consumerFactory);

        return containerFactory;
    }

}
