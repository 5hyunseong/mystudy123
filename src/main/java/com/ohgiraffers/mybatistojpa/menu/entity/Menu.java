package com.ohgiraffers.mybatistojpa.menu.entity;

import jakarta.persistence.*;

@Entity(name = "menu")
@Table(name = "tbl_menu")
public class Menu {

    @Id
    @Column(name = "menu_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int menuCode;

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "menu_price")
    private int menuPrice;

    @Column(name = "category_code")
    private int categoryCode;

    @Column(name = "orderable_status")
    private char orderableStatus;

    @Column(name = "menu_picture_name")
    private String menuPictureName;

    @Column(name = "menu_picture_extension")
    private String menuPictureExtension;

    public Menu() {
    }

    public Menu(int menuCode, String menuName, int menuPrice, int categoryCode, char orderableStatus, String menuPictureName, String menuPictureExtension) {
        this.menuCode = menuCode;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.categoryCode = categoryCode;
        this.orderableStatus = orderableStatus;
        this.menuPictureName = menuPictureName;
        this.menuPictureExtension = menuPictureExtension;
    }

    public int getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(int menuCode) {
        this.menuCode = menuCode;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public int getMenuPrice() {
        return menuPrice;
    }

    public void setMenuPrice(int menuPrice) {
        this.menuPrice = menuPrice;
    }

    public int getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(int categoryCode) {
        this.categoryCode = categoryCode;
    }

    public char getOrderableStatus() {
        return orderableStatus;
    }

    public void setOrderableStatus(char orderableStatus) {
        this.orderableStatus = orderableStatus;
    }

    public String getMenuPictureName() {
        return menuPictureName;
    }

    public void setMenuPictureName(String menuPictureName) {
        this.menuPictureName = menuPictureName;
    }

    public String getMenuPictureExtension() {
        return menuPictureExtension;
    }

    public void setMenuPictureExtension(String menuPictureExtension) {
        this.menuPictureExtension = menuPictureExtension;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "menuCode=" + menuCode +
                ", menuName='" + menuName + '\'' +
                ", menuPrice=" + menuPrice +
                ", categoryCode=" + categoryCode +
                ", orderableStatus=" + orderableStatus +
                ", menuPictureName='" + menuPictureName + '\'' +
                ", menuPictureExtension='" + menuPictureExtension + '\'' +
                '}';
    }
}
