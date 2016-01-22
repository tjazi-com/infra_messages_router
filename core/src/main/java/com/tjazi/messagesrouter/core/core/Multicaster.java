package com.tjazi.messagesrouter.core.core;

import com.tjazi.messagesrouter.messages.ChatMessage;

import java.util.List;

/**
 * Created by Krzysztof Wasiak on 22/01/2016.
 */

public interface Multicaster {

    void multicastMessage(ChatMessage message, List<String> clusterNames);
}
