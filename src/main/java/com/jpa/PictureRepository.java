package com.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureRepository extends JpaRepository<Picture,Long> {
    //Picture findById(Long id)
}
