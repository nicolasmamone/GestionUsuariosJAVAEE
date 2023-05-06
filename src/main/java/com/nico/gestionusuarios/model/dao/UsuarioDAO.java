package com.nico.gestionusuarios.model.dao;

import com.nico.gestionusuarios.model.entities.Usuario;
import com.nico.gestionusuarios.utils.ConnectionManager;
import static java.lang.Integer.parseInt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UsuarioDAO {
    
    //Metodo que nos trae la lista de usuarios en la BD
    public ArrayList<Usuario> getListadoUsuarios() {
        ArrayList<Usuario> listaUsuarios = new ArrayList<>();
        try {

            ConnectionManager connectionManager = new ConnectionManager(); // generamos un objeto "ConnectionManager"
            Connection con = connectionManager.getConnec(); // generamos obj "Connection" y mediante el metodo getConnec generamos la conexion

            Statement stm;
            ResultSet rs;
            String sql;

            sql = "SELECT * \n"
                    + "FROM usuarios\n";

            stm = con.createStatement();
            rs = stm.executeQuery(sql);

            while (rs.next()) {
                Usuario user1 = new Usuario();
                user1.setNombre(rs.getString("nombre"));
                user1.setApellido(rs.getString("apellido"));
                user1.setId(rs.getInt("usuario_id")); //rs.getInt()
                System.out.println("usuario" + user1.getNombre());
                listaUsuarios.add(user1);
            }
            stm.close();
            rs.close();
            con.close();
        } catch (SQLException ex) {
            System.out.println("Error al obtener el listado de Usuarios");
        }
        return listaUsuarios;
    }
    //Método que nos trae un usuario de la BD, le pasamos por parámetro el "Id" del usuario.
    public Usuario getUsuario(int IdUsuario) {
        Usuario usuario = new Usuario();
        try {

            ConnectionManager connectionManager = new ConnectionManager(); // generamos un objeto "ConnectionManager"
            Connection con = connectionManager.getConnec(); // generamos obj "Connection" y mediante el metodo getConnec generamos la conexion

            Statement stm;
            ResultSet rs;
            String sql;

            sql = " SELECT * \n"
                    + " FROM usuarios \n"
                    + " WHERE usuario_id = " + IdUsuario;

            stm = con.createStatement();
            rs = stm.executeQuery(sql);

            rs.next();

            usuario.setNombre(rs.getString("nombre"));
            System.out.println("usuario" + usuario.getNombre());
            usuario.setApellido(rs.getString("apellido"));
            System.out.println("usuario" + usuario.getApellido());
            usuario.setId(rs.getInt("usuario_id"));
            System.out.println("usuario" + usuario.getId());

            stm.close();
            rs.close();
            con.close();
        } catch (SQLException ex) {
            System.out.println("Error al obtener el Usuario");
        }
        return usuario;
    }

    public void getAgregarUsuario(Usuario usuario) {

    }

    public void getModificarUsuario(Usuario usuario) {

    }
    // Método que guarda o actualiza un usuario de la BD.
    //Le pasamos por param un obj Usuario.
    public void getGuardarUsuario(Usuario usuario) {
        try {

            ConnectionManager connectionManager = new ConnectionManager(); // generamos un objeto ConnectionManager
            Connection con = connectionManager.getConnec(); // generamos obj Connection y mediante el metodo getConnec generamos la conexion

            PreparedStatement stm;// (PreparedStament) para que los valores del string sql los tome por medio de '?'
            String sql;

            // " INSERT INTO usuarios(nombre, apellido) VALUES('" 
            //      + usuario.getNombre() + "','"
            //    + usuario.getApellido() + "');";
            
            if(usuario.getId() == 0){ // Si el ID del usuario es 0 entonces inserta    
                sql = " INSERT INTO usuarios(nombre, apellido) VALUES(?,?)";
            }else{ // Sino, Actualiza
                sql= " UPDATE usuarios SET nombre=?, apellido=? WHERE usuario_id=?";
            }

            stm = con.prepareStatement(sql);
            stm.setString(1, usuario.getNombre());
            stm.setString(2, usuario.getApellido());
            
            if(usuario.getId() != 0){ //si actualiza le agregamos el id a la consulta del usuario a editar
                stm.setInt(3, usuario.getId());
            }
            stm.executeUpdate();   // en INSERT se usa executeUpdate            

            stm.close();
            con.close();

        } catch (SQLException ex) {
            System.out.println("Error al GUARDAR el USUARIO");
        }
    }
    // Método para eliminar un Usuario de la BD, por param le pasamos el id del usuario a eliminar
    public void getEliminarUsuario(int IdUsuario) {

        try {

            ConnectionManager connectionManager = new ConnectionManager(); // generamos un objeto ConnectionManager
            Connection con = connectionManager.getConnec(); // generamos obj Connection y mediante el metodo getConnec generamos la conexion

            PreparedStatement stm;
            String sql;

            sql = " DELETE \n"
                    + " FROM usuarios \n"
                    + " WHERE usuario_id = ?";

            stm = con.prepareStatement(sql);
            stm.setInt(1, IdUsuario);
            stm.executeUpdate();

            stm.close();
            con.close();

        } catch (SQLException ex) {
            System.out.println("Error al eliminar el Usuario");
        }

    }
    // Método para buscar usuarios de la BD, le pasamos por param un String con el termino a buscar
    // Retorna los usuarios con coincidencias
    public ArrayList<Usuario> buscarUsuarios(String terminoBuscado){
        ArrayList<Usuario> listaUsuarios = new ArrayList<>();
        
        try{
            ConnectionManager connectionManager = new ConnectionManager();
            Connection con = connectionManager.getConnec();
            
            Statement stm;
            ResultSet rs;
            String sql;
            
            // busque el terminoBuscado aunq tenga o no algo anterior o posterior
            sql = " SELECT * FROM usuarios WHERE nombre LIKE '%" + terminoBuscado + "%' " +
                  " OR apellido LIKE '%" + terminoBuscado + "%'";
            
            stm=con.createStatement();
            rs = stm.executeQuery(sql);
            
            while(rs.next()){
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("usuario_id"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                
                listaUsuarios.add(usuario);
            }
            stm.close();
            rs.close();
            con.close();
            
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    
        return listaUsuarios;
    }

}

