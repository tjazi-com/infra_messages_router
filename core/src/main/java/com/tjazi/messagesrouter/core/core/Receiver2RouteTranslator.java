package com.tjazi.messagesrouter.core.core;

import java.util.List;

/**
 * Created by Krzysztof Wasiak on 22/01/2016.
 */
public interface Receiver2RouteTranslator {
    List<String> getClusterNamesForReceiver(String messageReceiverId);
}
