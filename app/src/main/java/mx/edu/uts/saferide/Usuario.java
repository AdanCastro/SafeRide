package mx.edu.uts.saferide;

/**
 * Created by MacBookPro on 04/04/2017.
 */

public class Usuario {
    private String Usucorreo;
    private String Usunombre;
    private String Usuapellido;
    private String Usuubicacion;
    private String UsuEscuela;
    private String UsuFoto;

    public Usuario(){}

    public String getUsucorreo() {
        return Usucorreo;
    }

    public void setUsucorreo(String usucorreo) {
        Usucorreo = usucorreo;
    }

    public String getUsunombre() {
        return Usunombre;
    }

    public void setUsunombre(String usunombre) {
        Usunombre = usunombre;
    }

    public String getUsuapellido() {
        return Usuapellido;
    }

    public void setUsuapellido(String usuapellido) {
        Usuapellido = usuapellido;
    }

    public String getUsuUbicacion() {
        return Usuubicacion;
    }

    public void setUsuUbicacion(String usuubicacion) {
        Usuubicacion = usuubicacion;
    }

    public String getUsuFoto() {
        return UsuFoto;
    }

    public void setUsuFoto(String usufoto) {
        UsuFoto = usufoto;
    }

    public String getUsuEscuela() {
        return UsuEscuela;
    }

    public void setUsuEscuela(String usuescuela) {
        UsuEscuela = usuescuela;
    }
}
