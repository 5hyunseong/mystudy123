package com.ohgiraffers.mybatistojpa.repository;

import com.ohgiraffers.mybatistojpa.menu.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {


    /* 설명. JPQL 작성 시, value만 작성해도 되지만 Native Query 작성시에는 반드시 nativeQuery 속성을 true로 설정해야 한다. */
    @Query(value = "SELECT " +
            "tc.category_code, tc.category_name, tc.ref_category_code " +
            "FROM " +
            "tbl_category as tc " +
            "ORDER BY " +
            "category_code ASC ",
            nativeQuery = true)
    List<Category> findAllCategory();
}
