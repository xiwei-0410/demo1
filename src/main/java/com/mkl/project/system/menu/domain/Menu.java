package com.mkl.project.system.menu.domain;

import com.mkl.framework.domain.BaseEntity;
import java.util.ArrayList;
import java.util.List;

public class Menu extends BaseEntity {
   private static final long serialVersionUID = 1L;
   private Long menuId;
   private String menuName;
   private String parentName;
   private Long parentId;
   private String orderNum;
   private String url;
   private String menuType;
   private Integer visible;
   private Integer type;
   private String perms;
   private String icon;
   private Integer isApplet;
   private Integer isBottomNavigation;
   private String clickIcon;
   private List<Menu> children = new ArrayList();

   public Integer getType() {
      return this.type;
   }

   public void setType(Integer type) {
      this.type = type;
   }

   public Long getMenuId() {
      return this.menuId;
   }

   public void setMenuId(Long menuId) {
      this.menuId = menuId;
   }

   public String getMenuName() {
      return this.menuName;
   }

   public void setMenuName(String menuName) {
      this.menuName = menuName;
   }

   public String getParentName() {
      return this.parentName;
   }

   public void setParentName(String parentName) {
      this.parentName = parentName;
   }

   public Long getParentId() {
      return this.parentId;
   }

   public void setParentId(Long parentId) {
      this.parentId = parentId;
   }

   public String getOrderNum() {
      return this.orderNum;
   }

   public void setOrderNum(String orderNum) {
      this.orderNum = orderNum;
   }

   public String getUrl() {
      return this.url;
   }

   public void setUrl(String url) {
      this.url = url;
   }

   public String getMenuType() {
      return this.menuType;
   }

   public void setMenuType(String menuType) {
      this.menuType = menuType;
   }

   public Integer getVisible() {
      return this.visible;
   }

   public void setVisible(Integer visible) {
      this.visible = visible;
   }

   public String getPerms() {
      return this.perms;
   }

   public void setPerms(String perms) {
      this.perms = perms;
   }

   public String getIcon() {
      return this.icon;
   }

   public void setIcon(String icon) {
      this.icon = icon;
   }

   public List<Menu> getChildren() {
      return this.children;
   }

   public void setChildren(List<Menu> children) {
      this.children = children;
   }

   public Integer getIsApplet() {
      return isApplet;
   }

   public void setIsApplet(Integer isApplet) {
      this.isApplet = isApplet;
   }

   public Integer getIsBottomNavigation() {
      return isBottomNavigation;
   }

   public void setIsBottomNavigation(Integer isBottomNavigation) {
      this.isBottomNavigation = isBottomNavigation;
   }

   public String getClickIcon() {
      return clickIcon;
   }

   public void setClickIcon(String clickIcon) {
      this.clickIcon = clickIcon;
   }

   public String toString() {
      return "Menu [menuId=" + this.menuId + ", menuName=" + this.menuName + ", parentName=" + this.parentName + ", parentId=" + this.parentId + ", orderNum=" + this.orderNum + ", url=" + this.url + ", menuType=" + this.menuType + ", visible=" + this.visible + ", perms=" + this.perms + ", icon=" + this.icon + ", children=" + this.children + " , isApplet=" + this.isApplet+ ",isBottomNavigation=" + this.isBottomNavigation+",clickIcon="+this.clickIcon+"]";
   }
}
