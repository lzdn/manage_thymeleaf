package com.lzdn.manage.domain.core;

import java.io.Serializable;
import java.util.List;

public class MenuGroup implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 6705879215890217700L;

	private Integer groupId;

	private String groupName;

	private String description;

	private Integer orderBy;

	private Integer version;

	private String icon;

	private List<Menu> menus;

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName == null ? null : groupName.trim();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
	}

	public Integer getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(Integer orderBy) {
		this.orderBy = orderBy;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon == null ? null : icon.trim();
	}

}