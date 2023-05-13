package com.exam.mydictionary.Repositories;

import com.exam.mydictionary.Models.Dict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface DictRepository extends JpaRepository<Dict, Long> {
    // Search
    @Query("SELECT p FROM Dict p WHERE "  + "CONCAT(' ', p.english, ' ', p.russian, ' ', p.id)" + "" + "LIKE %?1%")
    public Page<Dict> findAll(String keyword, Pageable pageable);

}
