package com.nico.gestionusuarios.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    
    public Connection getConnec(){
        
        // Datos necesarios para generar la url
        String user = "root";
        String password = "";
        String bd = "usuarios_db";
        String host = "localhost";
        String port = "3306";
        String timezone = "UTC";
        
        String url;
        url = "jdbc:mysql://" + host + ":" + port + "/" + bd + "?user=" + user + "&password=" + password + "&serverTimezone=" + timezone;
        
        Connection con;
        con = null;// inicializamos el objeto connection


        try {

            Class.forName("com.mysql.cj.jdbc.Driver");// Cargamos el driver
            System.out.println("EL DRIVER SE CARGO CORRECTAMENTE");

            con = DriverManager.getConnection(url); // Conectamos con la BD
            System.out.println("CONECTADO SATISFACTORIAMENTE");

        } catch (ClassNotFoundException ex) {
            System.out.println("ERROR AL CARGAR EL DRIVER DE MYSQL");;
        } catch (SQLException ex) {
            System.out.println("ERROR AL CONECTARSE CON LA BASE DE DATOS ");;
            System.out.println(ex.getMessage());
        }
        
        return con;
    } 
}
