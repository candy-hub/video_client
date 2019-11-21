package com.video.dao;


import com.video.domain.Type;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TypeRepository extends JpaRepository<Type,Integer> {

    Integer findAllByTypeName(String typeName);

}
