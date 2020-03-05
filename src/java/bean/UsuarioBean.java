/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import java.sql.*;

import com.mysql.jdbc.Statement;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;


/**
 *
 * @author sistemas
 */
@ManagedBean
@RequestScoped
public class UsuarioBean {
    
    String usuario;
    String password;
    String estado;
    String prueba = "aja";
    private String respuesta;
    FacesMessage mensaje = null;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getRespuesta(){
        return respuesta;
    }
    /**
     * Creates a new instance of UsuarioBean
     */
    public UsuarioBean() {
    }
    
    
    public void login(){
        if(validarIngreso()==false){         
            try {
                respuesta = String.format("usuario %s logueado!!", usuario);
                FacesContext.getCurrentInstance().getExternalContext().redirect("menu_principal.xhtml");
                        
            } catch (IOException ex) {
                Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            respuesta = String.format("usuario %s no encontrado", usuario);
            mensaje = new FacesMessage(FacesMessage.SEVERITY_WARN, "Error de usuario", "Credenciales invalidas");
        }
    }
    
    
    private boolean validarIngreso() {
		String SSQL = "SELECT * FROM `USUARIOS` WHERE `USUARIO` ='"+usuario+"' AND `PASS` ='"+password+"' ;";

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/entidadfinanciera", "root", "pass");
			System.out.println("  CONEXION EXITOSA: ");
			
			Statement st = (Statement) conexion.createStatement();
                        ResultSet rs = st.executeQuery(SSQL);
			if(rs.next())
                            {
				return false;
                            }

		} catch (Exception e) {
			// TODO Auto-generated catch block
			respuesta = String.format("PROBLEMAS DE CONEXION A LA BASE DE DATOS");
		}
		return true;

	}
    
    public void crearCliente() throws IOException{
        FacesContext.getCurrentInstance().getExternalContext().redirect("crear_usuario.xhtml");
    }
    
    public void comsutarCliente() throws IOException{
        FacesContext.getCurrentInstance().getExternalContext().redirect("consultar_usuario.xhtml");
    }
    
    public void editarCliente() throws IOException{
        FacesContext.getCurrentInstance().getExternalContext().redirect("editar_usuario.xhtml");
    }
}

