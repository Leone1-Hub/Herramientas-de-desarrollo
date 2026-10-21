
package clases;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "pedido")
public class Pedido implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idpedido;

    private int idusuario;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPedido;

    private String estado;

    public Pedido() {}

    public Pedido(int idpedido, int idusuario, Date fechaPedido, String estado) {
        this.idpedido = idpedido;
        this.idusuario = idusuario;
        this.fechaPedido = fechaPedido;
        this.estado = estado;
    }

    public int getIdpedido() {
        return idpedido;
    }

    public void setIdpedido(int idpedido) {
        this.idpedido = idpedido;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public Date getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(Date fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
