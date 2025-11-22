package com.furblur;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Walk {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private LocalDate date;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private Double distanceKm;
	private Double averagePace;
	private String durationFormatted;
	private String energyLevel;
	private String happiness;
	private String behaviour;
	
	@OneToMany(mappedBy = "walk", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<Coordinate> coordinates;
	
	public Double getDistanceKm() {
		return distanceKm;
	}
	public void setDistanceKm(Double distanceKm) {
		this.distanceKm = distanceKm;
	}
	
	public Double getAveragePace() {
		return averagePace;
	}
	public void setAveragePace(Double averagePace) {
		this.averagePace = averagePace;
	}
	
	public String getDurationFormatted() {
		return durationFormatted;
	}
	public void setDurationFormatted(String durationFormatted) {
		this.durationFormatted = durationFormatted;
	}
	
	public String getEnergyLevel() {
		return energyLevel;
	}
	public void setEnergyLevel(String energyLevel) {
		this.energyLevel = energyLevel;
	}
	
	public String getHappiness() {
		return happiness;
	}
	public void setHappiness(String happiness) {
		this.happiness = happiness;
	}
	
	public String getBehaviour() {
		return behaviour;
	}
	public void setBehaviour(String behaviour) {
		this.behaviour = behaviour;
	}
	
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	public LocalDateTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}
	
	public LocalDateTime getEndTime() {
		return endTime;
	}
	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}
	
	public Long getId() { 
		return id; 
	}
	public void setId(Long id) { 
		this.id = id; 
	}
	
	public List<Coordinate> getCoordinates() { 
		return coordinates; 
	}
	public void setCoordinates(List<Coordinate> coordinates) {
		this.coordinates = coordinates;
		if (coordinates != null) {
			coordinates.forEach(c -> c.setWalk(this));
		}
	}

}
