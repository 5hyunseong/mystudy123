package com.ohgiraffers.mybatistojpa.repository;

import com.ohgiraffers.mybatistojpa.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Integer> {
}
