package com.furblur;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class WalkController {
	
	@Autowired
	private WalkRepository walkRepository;
	
	@PostMapping("/walks")
	public ResponseEntity<Walk> createWalk(@RequestBody Walk walk) {
		if(walk.getCoordinates() != null) {
			walk.getCoordinates().forEach(c -> c.setWalk(walk));
		}
		
		Walk saved = walkRepository.save(walk);
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}
	
	@GetMapping("/walks")
	public List<Walk> getAllWalks() {
		return walkRepository.findAllByOrderByIdDesc();
	}
}
