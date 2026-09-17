package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.Model.Profile;

public interface ProfileRepo extends JpaRepository<Profile,Long>{

}
