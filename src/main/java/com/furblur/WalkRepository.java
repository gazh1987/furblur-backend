package com.furblur;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WalkRepository extends JpaRepository<Walk, Long>{

		List<Walk> findAllByOrderByIdDesc();
}
