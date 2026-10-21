package clases;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "productos")
public class Producto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idproducto;

    private String nombre;
    private String descripcion;
    private String marca;
    private String categoria;
    private double precio;
    private int stock;
    private int cantidad;
    private double subtotal;

    // Constructor vacío
    public Producto() {}
    
    // Constructor para el carrito (sin necesidad de id, marca, stock, etc.)
    public Producto(String nombre, int cantidad, double precio) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.subtotal = cantidad * precio;
    }
    
    // Constructor usado por ProductoDAO.obtenerTodos() (sin marca ni subtotal)
    public Producto(int idproducto, String nombre, String descripcion, String categoria, double precio, int stock, int cantidad, double subtotal) {
        this.idproducto = idproducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    // Constructor completo
    public Producto(int idproducto, String nombre, String descripcion, String marca, String categoria, double precio, int stock, int cantidad,double subtotal ) {
        this.idproducto = idproducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.marca = marca;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
        this.cantidad=cantidad;
        this.subtotal=subtotal;
    }

    // Getters y Setters

    public int getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(int idproducto) {
        this.idproducto = idproducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
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
       
}
