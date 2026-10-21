
package clases;
import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "pago")
public class Pago implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idpago;

    private int idpedido;
    private double monto;
    private double cambio;
    private String metodopago;

    public Pago() {}

    public Pago(int idpago, int idpedido, double monto, double cambio, String metodopago) {
        this.idpago = idpago;
        this.idpedido = idpedido;
        this.monto = monto;
        this.cambio = cambio;
        this.metodopago = metodopago;
    }

    public int getIdpago() {
        return idpago;
    }

    public void setIdpago(int idpago) {
        this.idpago = idpago;
    }

    public int getIdpedido() {
        return idpedido;
    }

    public void setIdpedido(int idpedido) {
        this.idpedido = idpedido;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public double getCambio() {
        return cambio;
    }

    public void setCambio(double cambio) {
        this.cambio = cambio;
    }

    public String getMetodopago() {
        return metodopago;
    }

    public void setMetodopago(String metodopago) {
        this.metodopago = metodopago;
    }  
}

