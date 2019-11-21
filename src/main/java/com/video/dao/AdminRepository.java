package com.video.dao;

import com.video.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminRepository extends JpaRepository<Admin,Integer> {

    List<Admin> findByANameOrAEmailOrATell(String aName, String aEmail, String aTell);

}
