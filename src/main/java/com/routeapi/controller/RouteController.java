package com.routeapi.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.routeapi.services.RouteService;

@Controller   
@RequestMapping(path="/route")
public class RouteController {
	
	@Autowired
	private RouteService routeService;
	
	
	
	
}
