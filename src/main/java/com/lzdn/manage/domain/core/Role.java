package com.lzdn.manage.domain.core;

import java.io.Serializable;
import java.util.List;

public class Role implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -527124373404124656L;

	private Integer roleId;

    private String roleName;

    private String description;

    private Integer version;
    

    /*增加关联*/
    private List<Right> rights;
    /*增加关联*/

    public List<Right> getRights() {
		return rights;
	}

	public void setRights(List<Right> rights) {
		this.rights = rights;
	}

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
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
}