package com.routeapi.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.routeapi.model.Route;

@Repository
public interface RouteRepository extends CrudRepository<Route, Long> {
	
	Route findById(long routeID);
	
	List<Route> findAll();
	
}
