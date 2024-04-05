package com.ohgiraffers.mybatistojpa.menu.dto;

public class MenuDTO {

    private int menuCode;

    private String menuName;

    private int menuPrice;

    private int categoryCode;

    private char orderableStatus;

    private String menuPictureName;

    private String menuPictureExtension;

    private String img;

    public MenuDTO() {
    }

    public MenuDTO(int menuCode, String menuName, int menuPrice, int categoryCode, char orderableStatus, String menuPictureName, String menuPictureExtension, String img) {
        this.menuCode = menuCode;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.categoryCode = categoryCode;
        this.orderableStatus = orderableStatus;
        this.menuPictureName = menuPictureName;
        this.menuPictureExtension = menuPictureExtension;
        this.img = img;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "MenuDTO{" +
                "menuCode=" + menuCode +
                ", menuName='" + menuName + '\'' +
                ", menuPrice=" + menuPrice +
                ", categoryCode=" + categoryCode +
                ", orderableStatus=" + orderableStatus +
                ", menuPictureName='" + menuPictureName + '\'' +
                ", menuPictureExtension='" + menuPictureExtension + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
