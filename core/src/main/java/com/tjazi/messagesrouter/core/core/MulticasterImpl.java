package com.tjazi.messagesrouter.core.core;

import com.tjazi.messagesrouter.messages.ChatMessage;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Krzysztof Wasiak on 22/01/2016.
 */

@Service
public class MulticasterImpl implements Multicaster {


    @Override
    public void multicastMessage(ChatMessage message, List<String> clusterNames) {

    }
}
