package com.nico.gestionusuarios.ajax;

import java.util.ArrayList;

// Clase que nos permite generar objetos que representen una respuesta Json
public class RespuestaJson {
    String status;
    String message;
    ArrayList data;

    public RespuestaJson(String status, String message, ArrayList data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
    
    

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList getData() {
        return data;
    }

    public void setData(ArrayList data) {
        this.data = data;
    }
    
    
}
