package com.furblur;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WalkController {
	
	@Autowired
	private WalkRepository walkRepository;
	
	@PostMapping("/walks")
	public Walk createWalk(@RequestBody Walk walk) {
		if(walk.getCoordinates() != null) {
			walk.getCoordinates().forEach(c -> c.setWalk(walk));
		}
		return walkRepository.save(walk);
	}
	
	@GetMapping("/walks")
	public List<Walk> getAllWalks() {
		return walkRepository.findAllByOrderByIdDesc();
	}
}
