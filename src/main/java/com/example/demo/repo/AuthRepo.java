package com.example.demo.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.Model.Auth;

public interface AuthRepo extends JpaRepository<Auth,Long>{


	Optional<Auth> findByEmail(String email);

	boolean existsByEmail(String email);

	
}
