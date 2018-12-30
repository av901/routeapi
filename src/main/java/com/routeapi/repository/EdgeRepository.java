package com.routeapi.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.routeapi.model.Edge;

@Repository
public interface EdgeRepository extends CrudRepository<Edge, Long>{
	
	List<Edge> findByNode1IdOrNode2Id(long nodeId1, long nodeId2);
	
	Edge findByNode1IdAndNode2IdOrNode2IdAndNode1Id(long nodeId11, long nodeId12, long nodeId22, long nodeId21);

}
