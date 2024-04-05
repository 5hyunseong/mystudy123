package com.ohgiraffers.mybatistojpa.menu.service;

import com.ohgiraffers.mybatistojpa.menu.dto.CategoryDTO;
import com.ohgiraffers.mybatistojpa.menu.dto.MenuDTO;
import com.ohgiraffers.mybatistojpa.menu.entity.Category;
import com.ohgiraffers.mybatistojpa.menu.entity.Menu;
import com.ohgiraffers.mybatistojpa.repository.CategoryRepository;
import com.ohgiraffers.mybatistojpa.repository.MenuRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuService {

    private final CategoryRepository categoryRepository;
    private final MenuRepository menuRepository;
    private final ModelMapper modelMapper;


    @Autowired
    public MenuService(MenuRepository menuRepository, ModelMapper modelMapper, CategoryRepository categoryRepository) {
        this.menuRepository = menuRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }


    public List<MenuDTO> findMenuList() {

        List<Menu> menuList = menuRepository.findAll(Sort.by("menuCode").descending());

        return menuList.stream().map(menu -> modelMapper.map(menu, MenuDTO.class)).collect(Collectors.toList());


    }

    public List<CategoryDTO> findAllCategory() {

        /* 설명. findAll() 메소드를 사용해 전체 카테고리를 조회하는 것이 당연히 가능하다.
         *  하지만, 이 예제에서는 SpringDateJPA에서 JPQL이나 Native Query를 사용할 때 어떻게 설정하고 사용해야 하는지를 중점으로 본다.
         *  따라서 CategoryRepository에 Native Query를 사용해 직접 메소드를 정의한다.
         *  */
        List<Category> categoryList = categoryRepository.findAllCategory();
        System.out.println("호출!!");
        System.out.println("categoryList = " + categoryList);
        return categoryList.stream().map(category -> modelMapper.map(category, CategoryDTO.class)).collect(Collectors.toList());


    }


    public MenuDTO findMenuCode(int code) {
        Menu menu = menuRepository.findById(code)
                .orElseThrow(IllegalArgumentException::new);

        return modelMapper.map(menu, MenuDTO.class);
    }


    @Transactional
    public void registMenu(MenuDTO menuDTO) {
        System.out.println("서비스 호출");
        System.out.println("menu = " + menuDTO);
        Menu menuEntity = modelMapper.map(menuDTO, Menu.class);
        System.out.println("menuEntity = " + menuEntity);
        menuRepository.save(menuEntity);
    }

    @Transactional
    public void modifyMenu(MenuDTO modifyMenu) {

        Menu menuToModify = menuRepository.findById(modifyMenu.getMenuCode())
                .orElseThrow(IllegalArgumentException::new);

        menuToModify.setMenuName(modifyMenu.getMenuName());
        menuToModify.setMenuPrice(modifyMenu.getMenuPrice());
        menuToModify.setCategoryCode(modifyMenu.getCategoryCode());
        menuToModify.setOrderableStatus(modifyMenu.getOrderableStatus());
    }


//    public void deleteMenu(Integer code) {
//        menuRepository.deleteById(code);
//    }
}




