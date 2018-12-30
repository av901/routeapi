package com.routeapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.routeapi.model.Edge;

@Repository
public interface EdgeRepository extends CrudRepository<Edge, Long>{

}
