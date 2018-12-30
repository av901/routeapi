package com.routeapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.routeapi.model.Node;

@Repository
public interface NodeRepository extends CrudRepository<Node, Long>{

}
