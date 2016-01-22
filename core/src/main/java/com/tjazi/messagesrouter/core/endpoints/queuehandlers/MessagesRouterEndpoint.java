package com.tjazi.messagesrouter.core.endpoints.queuehandlers;

import com.tjazi.messagesrouter.core.core.Multicaster;
import com.tjazi.messagesrouter.core.core.Receiver2RouteTranslator;
import com.tjazi.messagesrouter.messages.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Krzysztof Wasiak on 22/01/2016.
 */

@Service
public class MessagesRouterEndpoint {

    private final static Logger log = LoggerFactory.getLogger(MessagesRouterEndpoint.class);

    @Autowired
    private Receiver2RouteTranslator receiver2RouteTranslator;

    @Autowired
    private Multicaster multicaster;

    /**
     * This method will translate receiver into cluster name (based on the routing table),
     * then will multicast message to all queues on for those clusters.
     *
     * E.g.: receiver_group={xxxxx-xxxx-xxxx}, members of that group are registered in clusters 2 and 4
     *       so message will be broadcasted to RabbitMQ exchanges: ws_exch_clstr2 and ws_exch_clstr4
     *
     * @param message
     */
    public void routeMessage(ChatMessage message) {

        log.debug("Routing message, correlation ID: " + message.getCorrelationId());

        // check route destinations for this message
        String messageReceiverId = message.getReceiverId();
        String correlationId = message.getCorrelationId();

        if (messageReceiverId == null || messageReceiverId.isEmpty()) {
            log.error("Message with correlation ID: {} doesn't have receiver field set. Ignoring...", correlationId);
            return;
        }

        List<String> clusterNames = receiver2RouteTranslator.getClusterNamesForReceiver(messageReceiverId);

        if (clusterNames.size() == 0) {
            log.error("Message with correlation ID: {}, receiver ID: {} doesn't have any record in the routing table. Ignoring...",
                    correlationId, messageReceiverId);
            return;
        }

        log.debug("Attempting to multicast message with correlation ID: {} to clusters: {}",
                correlationId, Arrays.toString(clusterNames.toArray()));
        
        multicaster.multicastMessage(message, clusterNames);
    }
}
