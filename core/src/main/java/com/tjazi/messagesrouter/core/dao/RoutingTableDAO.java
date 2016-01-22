package com.tjazi.messagesrouter.core.dao;

import com.tjazi.messagesrouter.core.dao.model.RoutingTableDAOModel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Krzysztof Wasiak on 22/01/2016.
 */
public interface RoutingTableDAO extends CrudRepository<RoutingTableDAOModel, Long> {

    List<RoutingTableDAOModel> findByReceiverId(String receiverId);
}
