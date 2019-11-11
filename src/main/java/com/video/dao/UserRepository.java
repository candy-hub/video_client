package com.video.dao;


import com.video.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {

    List<User> findAllByUEmailOrUNameOrUTell(String userName, String userEmail, String userTell);

    List<User> findAllByUNameAndUEmailAndUTell(String userName, String userEmail, String userTell);
}
