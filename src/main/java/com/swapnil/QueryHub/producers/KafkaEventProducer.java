package com.swapnil.QueryHub.producers;

import com.swapnil.QueryHub.configuration.KafkaConfig;
import com.swapnil.QueryHub.events.ViewCountEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishViewCount(ViewCountEvent viewCountEvent){
        kafkaTemplate.send(KafkaConfig.TOPIC_NAME, viewCountEvent.getTargetId(), viewCountEvent)
                .whenComplete((result, err)->{
                    if(err!=null)
                        System.out.println("Error in publishing view count event: " + err.getMessage());
                });
    }

}
