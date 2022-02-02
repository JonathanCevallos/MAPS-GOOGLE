package com.example.maps_google;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data // EATE GETTERS-SETTERS AND CONSTRUCTORS
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Model {
    String nombre;
    String direccion;
    String decano;
    String contacto;
    String logo;
    Double latitud;
    Double longitud;
}