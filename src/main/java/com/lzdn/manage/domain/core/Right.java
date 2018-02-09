package com.lzdn.manage.domain.core;

import java.io.Serializable;

public class Right implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -2096216115816474454L;

	private Integer rightId;

    private String rightUrl;

    private String rightName;

    private String description;

    private Integer version;

    private Integer menuId;
    
    /*增加关联*/
    private Menu menu;
    /*增加关联*/

    public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

    public Integer getRightId() {
        return rightId;
    }

    public void setRightId(Integer rightId) {
        this.rightId = rightId;
    }

    public String getRightUrl() {
        return rightUrl;
    }

    public void setRightUrl(String rightUrl) {
        this.rightUrl = rightUrl == null ? null : rightUrl.trim();
    }

    public String getRightName() {
        return rightName;
    }

    public void setRightName(String rightName) {
        this.rightName = rightName == null ? null : rightName.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }
}