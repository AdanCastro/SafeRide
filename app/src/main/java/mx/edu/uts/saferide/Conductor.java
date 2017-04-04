package mx.edu.uts.saferide;

/**
 * Created by MacBookPro on 04/04/2017.
 */

public class Conductor {
    private String correo;
    private String nombre;
    private String apellido;
    private String ubicacion;
    private String fotoc;
    private String pasajeros;
    private String auto;

    public Conductor(){}


    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getFotoC() {
        return fotoc;
    }
    public void setFotoC(String fotoc) {
        this.fotoc = fotoc;
    }
    public String getPasajeros() {
        return pasajeros;
    }
    public void setPasajeros(String pasajeros) {
        this.pasajeros = pasajeros;
    }
    public String getAuto() {
        return auto;
    }
    public void setAuto(String auto) {
        this.auto = auto;
    }
}
