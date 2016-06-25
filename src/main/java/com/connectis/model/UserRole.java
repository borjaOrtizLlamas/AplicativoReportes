/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.connectis.model;

/**
 *
 * @author borja
 */
public class UserRole{

	private Integer userRoleId;
	private String usuario;
	private String role;

    public Integer getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Integer userRoleId) {
        this.userRoleId = userRoleId;
    }


	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
        
        
}
