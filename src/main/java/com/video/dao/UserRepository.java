package com.video.dao;


import com.video.domain.Record;
import com.video.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {

    /*
     * 用户界面
     */

    List<User> findAllByUserTellOrUserEmailOrUserName(String userName, String userEmail, String userTell);

    List<User> findAllByUserNameAndUserEmailAndUserTell(String userName, String userEmail, String userTell);

    User findAllByUserRechargeOrderNumber(String userRechargeOrderNumber);

    User findAllByUserRechargeVipOrderNumber(String userRechargeVipOrderNumber);
}
