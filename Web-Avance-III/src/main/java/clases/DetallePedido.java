
package clases;
import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "detallepedido")
public class DetallePedido implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_detalle;

    private int idpedido;
    private int idproducto;
    private int idpago;
    private int cantidad;
    private double subtotal;
    private double descuento;
    private double igv;
    private double totalpago;

    public DetallePedido() {}

    public DetallePedido(int id_detalle, int idpedido, int idproducto, int idpago, int cantidad, double subtotal, double descuento, double igv, double totalpago) {
        this.id_detalle = id_detalle;
        this.idpedido = idpedido;
        this.idproducto = idproducto;
        this.idpago = idpago;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
        this.descuento = descuento;
        this.igv = igv;
        this.totalpago = totalpago;
    }

    public int getId_detalle() {
        return id_detalle;
    }

    public void setId_detalle(int id_detalle) {
        this.id_detalle = id_detalle;
    }

    public int getIdpedido() {
        return idpedido;
    }

    public void setIdpedido(int idpedido) {
        this.idpedido = idpedido;
    }

    public int getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(int idproducto) {
        this.idproducto = idproducto;
    }

    public int getIdpago() {
        return idpago;
    }

    public void setIdpago(int idpago) {
        this.idpago = idpago;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public double getIgv() {
        return igv;
    }

    public void setIgv(double igv) {
        this.igv = igv;
    }

    public double getTotalpago() {
        return totalpago;
    }

    public void setTotalpago(double totalpago) {
        this.totalpago = totalpago;
    }
}

