
package clases;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idusuario;

    private String nombre;
    
    @Column(name = "apaterno")
    private String apaterno;

    @Column(name = "amaterno")
    private String amaterno;

    @Column(name = "sexo")
    private String sexo;

    @Column(name = "fechaN")
    private Date fechaN;

    @Column(name = "tipoDocumento")
    private String tipoDocumento;

    @Column(name = "nDocumento")
    private String nDocumento;

    @Column(name = "nacionalidad")
    private String nacionalidad;

    @Column(name = "correo")
    private String correo;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "departamento")
    private String departamento;

    @Column(name = "provincia")
    private String provincia;

    @Column(name = "distrito")
    private String distrito;

    @Column(name = "contraseña")
    private String contraseña;

    @Column(name = "rol")
    private String rol;


    // Constructor vacío
    public Usuario() {}

    // Constructor con todos los campos
    public Usuario(int idusuario, String nombre, String apaterno,String amaterno,String sexo, Date fechaN, String tipoDocumento, String nDocumento, String nacionalidad, String correo, String telefono, String direccion, String departamento, String provincia, String distrito,String contraseña, String rol) {
        this.idusuario = idusuario;
        this.nombre = nombre;
        this.apaterno = apaterno;
        this.amaterno = amaterno;
        this.sexo = sexo;
        this.fechaN = fechaN;
        this.tipoDocumento = tipoDocumento;
        this.nDocumento = nDocumento;
        this.nacionalidad = nacionalidad;
        this.correo = correo;
        this.telefono = telefono;
        this.direccion = direccion;
        this.departamento = departamento;
        this.provincia = provincia;
        this.distrito = distrito;
        this.contraseña=contraseña;
        this.rol = rol;
    }

    // Getters y Setters
    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApaterno() {
        return apaterno;
    }

    public void setApaterno(String apaterno) {
        this.apaterno = apaterno;
    }

    public String getAmaterno() {
        return amaterno;
    }

    public void setAmaterno(String amaterno) {
        this.amaterno = amaterno;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Date getFechaN() {
        return fechaN;
    }

    public void setFechaN(Date fechaN) {
        this.fechaN = fechaN;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getnDocumento() {
        return nDocumento;
    }

    public void setnDocumento(String nDocumento) {
        this.nDocumento = nDocumento;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
    
    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }      
}