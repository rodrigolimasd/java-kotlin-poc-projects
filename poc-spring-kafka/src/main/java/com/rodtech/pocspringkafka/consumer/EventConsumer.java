package com.rodtech.pocspringkafka.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EventConsumer {

    private static final Logger log = LoggerFactory.getLogger(EventConsumer.class);

    @KafkaListener(id="event-consumer",
            topicPartitions = @TopicPartition(
                    topic = "Events",
                    partitions = { "0" },
                    partitionOffsets = {
                        @PartitionOffset(partition = "*", initialOffset = "0")}
            )
    )
    public void consume(@Payload String msg, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition){
        log.info("Message consumed from partition {} msg {} ", partition, msg);
    }
}
