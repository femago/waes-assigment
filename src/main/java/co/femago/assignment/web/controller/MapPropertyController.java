package co.femago.assignment.web.controller;

import co.femago.assignment.config.MapPropertyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MapPropertyController {

	@Autowired
	private MapPropertyConfig mapPropertyConfig;

	@GetMapping(path = "/prop")
	public ResponseEntity<String> cacheableMethod(){
		return ResponseEntity.ok().body(mapPropertyConfig.getEurekaClients().toString());
	}
}
