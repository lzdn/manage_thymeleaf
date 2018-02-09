package com.lzdn.manage.domain.core;

public class RoleRightRelation {
    private Integer id;

    private Integer roleId;

    private Integer rightId;

    private String rightType;

    private Integer version;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getRightId() {
        return rightId;
    }

    public void setRightId(Integer rightId) {
        this.rightId = rightId;
    }

    public String getRightType() {
        return rightType;
    }

    public void setRightType(String rightType) {
        this.rightType = rightType == null ? null : rightType.trim();
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}