package com.routeapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.routeapi.repository.EdgeRepository;
import com.routeapi.repository.NodeRepository;
import com.routeapi.repository.RouteRepository;

@Service
public class RepositoryHolder {
	
	@Autowired
	public RouteRepository routeRepository;
	
	@Autowired
	public EdgeRepository edgeRepository;
	
	@Autowired
	public NodeRepository nodeRepository;

}
