package com.tjazi.messagesrouter.core.core;

import com.tjazi.messagesrouter.core.dao.RoutingTableDAO;
import com.tjazi.messagesrouter.core.dao.model.RoutingTableDAOModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Krzysztof Wasiak on 22/01/2016.
 */

@Service
public class Receiver2RouteTranslatorImpl implements Receiver2RouteTranslator {

    private final static Logger log = LoggerFactory.getLogger(Receiver2RouteTranslatorImpl.class);

    private final static String CLUSTERS_SPLIT_CHARACTER = ";";

    @Autowired
    private RoutingTableDAO routingTableDAO;

    /**
     * Get all cluster names for the given receiver
     * @param messageReceiverId Receiver of the message
     * @return List of clusters, which given receiver is registered to.
     */
    @Override
    public List<String> getClusterNamesForReceiver(String messageReceiverId) {

        if (messageReceiverId == null || messageReceiverId.isEmpty()) {
            throw new IllegalArgumentException("messageReceiver is null or empty");
        }

        List<RoutingTableDAOModel> routingTableRecord = routingTableDAO.findByReceiverId(messageReceiverId);

        ArrayList<String> result = new ArrayList<>();

        if (routingTableRecord == null || routingTableRecord.size() == 0) {
            log.warn("No routing records for given receiver");
            return result;
        }

        return this.string2ClusterNames(result.get(0));
    }

    private List<String> string2ClusterNames(String clusterNamesString) {
        String[] splitClusterNames = clusterNamesString.split(CLUSTERS_SPLIT_CHARACTER);
        return Arrays.asList(splitClusterNames);
    }
}
