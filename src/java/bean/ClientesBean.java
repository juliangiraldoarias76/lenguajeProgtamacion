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
import java.awt.HeadlessException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author sistemas
 */
@ManagedBean
@RequestScoped
public class ClientesBean {
    String cedula;
    String nombres;
    String apellidos;
    String direccion;
    String correo;
    int resultado = 0;
    private String respuesta;
    
    

    public String getRespuesta(){
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
    
    

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    public void crearCliente(){
        if(validarCorreo()==false) {
                FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "El correo no contiene el formato indicado", null);
                FacesContext.getCurrentInstance().addMessage(null, facesMsg);
                respuesta = String.format("PROBLEMAS DE CONEXION A LA BASE DE DATOS");
    	}

    	try {
    		Class.forName("com.mysql.jdbc.Driver");
    		Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/entidadfinanciera", "root", "pass");
			System.out.println("  CONEXION EXITOSA: ");
			String SSQL = "INSERT INTO CLIENTES (ID_CLIENTE, CEDULA, NOMBRES, APELLIDOS, DIRECCION, CORREO, ESTADO) VALUES (default, ?, ?, ?, ?, ?, 'ACTIVO');";
			PreparedStatement psql = conexion.prepareStatement(SSQL);
			//psql.setString(1, "default");
			psql.setString(1, cedula);
                        psql.setString(2, nombres);
			psql.setString(3, apellidos);
			psql.setString(4, direccion);
                        psql.setString(5, correo);
			resultado = psql.executeUpdate();

			psql.close();
                        respuesta = String.format("Creacion Exitosa de Cliente");
                        
		} catch (HeadlessException | ClassNotFoundException | SQLException e) {
			respuesta = String.format("PROBLEMAS DE CONEXION A LA BASE DE DATOS");
		}

        }
    
       
    
        public ClientesBean() {
//             FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Entidad Financiera", null);
//             FacesContext.getCurrentInstance().addMessage(null, facesMsg);
        }
        
        
        public boolean validarCorreo() {
            String emailPattern = "^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@" +
              "[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$";
          Pattern pattern = Pattern.compile(emailPattern);
            String email = correo;
          if (email != null) {
              Matcher matcher = pattern.matcher(email);
              if (matcher.matches()) {
                 System.out.println("VÃ¡lido");
                 return true;
              }   
           }
          return false;
      }
    }


