package com.lzdn.manage.domain.core;

import java.io.Serializable;

public class Menu implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7355945557130911763L;

	private Integer menuId;

    private Integer groupId;

    private String menuCode;

    private String menuName;

    private String icon;

    private String url;

    private String description;

    private Integer status;

    private Integer orderBy;
    
    private MenuGroup menuGroup;

    public MenuGroup getMenuGroup() {
		return menuGroup;
	}

	public void setMenuGroup(MenuGroup menuGroup) {
		this.menuGroup = menuGroup;
	}

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode == null ? null : menuCode.trim();
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }
}