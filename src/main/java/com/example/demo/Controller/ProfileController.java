package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Dto.ProfileDTO;
import com.example.demo.Model.Profile;
import com.example.demo.repo.ProfileRepo;

@RestController
public class ProfileController {

	@Autowired
	ProfileRepo repo;
	
	@PostMapping("/postProfile")
	public String postprofile(@RequestBody Profile profile){
		
		Profile prf = new Profile();
		prf.setUserName(profile.getUserName());
		prf.setEducation(profile.getEducation());
		prf.setBranch(profile.getBranch());
		prf.setCollege(profile.getCollege());
		prf.setSkills(profile.getSkills());
		prf.setExperience(profile.getExperience());
		prf.setProjects(profile.getProjects());
		prf.setJobRole(profile.getJobRole());
		prf.setLocation(profile.getLocation());
		prf.setExpSalary(prf.getExpSalary());
		prf.setPreference(profile.getPreference());
		
		repo.save(prf);
		return "Your profile is successfully added";
	}
	
	@GetMapping("/showProfile")
	public List<Profile> showProfile(){
		return repo.findAll();
	} 
	
	@PutMapping("/updateProfile/{id}")
	public String updateProfile(
	        @PathVariable Long id,
	        @RequestBody ProfileDTO dto) {

	    Profile pp = repo.findById(id).orElseThrow();

	    pp.setUserName(dto.getUserName());
	    pp.setEducation(dto.getEducation());
	    pp.setBranch(dto.getBranch());
	    pp.setCollege(dto.getCollege());
	    pp.setSkills(dto.getSkills());
	    pp.setExperience(dto.getExperience());
	    pp.setProjects(dto.getProjects());
	    pp.setJobRole(dto.getJobRole());
	    pp.setLocation(dto.getLocation());
	    pp.setExpSalary(dto.getExpSalary());
	    pp.setPreference(dto.getPreference());
	    	
	    repo.save(pp);
	    
	    return "updated";
	}
	
	@DeleteMapping("/deleteprofile/{id}")
	public String deleteProfile(@PathVariable Long id) {
		if(repo.findById(id) != null) {
			repo.deleteById(id);
			return "Deleted successfully ";
		}
		else {
			return "This profile is not present";
		}
	}
	
} 
