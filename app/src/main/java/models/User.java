package models;

public class User {

    String id;
    String nombre;
    String email;
    String telefono;

    public User(String id, String nombre, String email, String telefono) {
        this.id = id;
        this.id = nombre;
        this.id = email;
        this.id = telefono;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
