package com.nico.gestionusuarios.servlets;

import com.google.gson.Gson;
import com.nico.gestionusuarios.ajax.RespuestaJson;
import com.nico.gestionusuarios.model.dao.UsuarioDAO;
import com.nico.gestionusuarios.model.entities.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "UsuarioServlet", urlPatterns = {"/usuarios"})
public class UsuarioServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json"); //la Respuesta va a ser un JSON
        response.setCharacterEncoding("UTF-8"); // en utf-8

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        ArrayList listaUsuarios;
        //Si le mando un param "Id" me va a devolver una lista con un solo usuario
        //Sino me devuelve el arraylist cn todos los usuarios
        //De las dos formas tiene q ser array porq es lo q va a esperar el obj RespuestaJson
        if (request.getParameter("id") != null) {
            // trae un solo usuario
            Usuario usuario = usuarioDAO.getUsuario(Integer.parseInt(request.getParameter("id")));
            listaUsuarios = new ArrayList();
            listaUsuarios.add(usuario);
        } else {
            if(request.getParameter("search") != null){
                //realiza una busqueda
                listaUsuarios = usuarioDAO.buscarUsuarios(request.getParameter("search"));
            }else{
                //trae todos los usuarios
                listaUsuarios = usuarioDAO.getListadoUsuarios();
            }
        }

        Gson gson = new Gson();
        //gson.toJson(listaUsuarios);

        RespuestaJson res = new RespuestaJson("ok", "", listaUsuarios);// Le damos formato de Json al objeto

        PrintWriter out = response.getWriter();
        out.print(gson.toJson(res));
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        System.out.println(request.getParameter("nombre"));
        System.out.println(request.getParameter("apellido"));
        
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = new Usuario();
        
        usuario.setId(0);
        usuario.setNombre(request.getParameter("nombre"));
        usuario.setApellido(request.getParameter("apellido"));
        
        usuarioDAO.getGuardarUsuario(usuario);
        RespuestaJson res = new RespuestaJson("ok", "El cliente se ha agregado correctamente", new ArrayList());
        
        Gson gson = new Gson();
        out.print(gson.toJson(res));
        out.flush();
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            Usuario usuario = new Usuario();
            
            usuario.setId(Integer.parseInt(request.getParameter("id")));
            usuario.setNombre(request.getParameter("nombre"));
            usuario.setApellido(request.getParameter("apellido"));
            
            usuarioDAO.getGuardarUsuario(usuario);
            
            RespuestaJson res = new RespuestaJson("ok","El usuario ha sido modificado correctamente ", new ArrayList());
            Gson gson = new Gson();
            out.print(gson.toJson(res));
            out.flush();
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioDAO.getEliminarUsuario(Integer.parseInt(request.getParameter("id")));
        
        Gson gson = new Gson();
        RespuestaJson res = new RespuestaJson("ok", "El usuario se borro Correctamente", new ArrayList());
        PrintWriter out = response.getWriter();
        out.print(gson.toJson(res));
        out.flush();
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
