
package persistencia;

import clases.Usuario;
import clases.Producto;
import clases.Pedido;
import clases.DetallePedido;
import clases.Pago;
import clases.Categoria;
import clases.ReglaNegocioException;
import persistencia.exceptions.NonexistentEntityException;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class ControladoraPersistencia {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("Web-Avance");

    UsuarioJpaController usuJpa = new UsuarioJpaController(emf);
    ProductoJpaController prodJpa = new ProductoJpaController(emf);
    PedidoJpaController pedJpa = new PedidoJpaController(emf);
    DetallePedidoJpaController detJpa = new DetallePedidoJpaController(emf);
    PagoJpaController pagoJpa = new PagoJpaController(emf);
    CategoriaJpaController catJpa = new CategoriaJpaController(emf);

    // ==================== MÉTODOS PARA USUARIO ====================
    public void crearUsuario(Usuario usu) {
        usuJpa.create(usu);
    }

    public List<Usuario> traerUsuarios() {
        return usuJpa.findUsuarioEntities();
    }

    public void borrarUsuario(int id) {
        try {
            usuJpa.destroy(id);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Usuario traerUsuario(int id) {
        return usuJpa.findUsuario(id);
    }

    public void editarUsuario(Usuario usu) {
        try {
            usuJpa.edit(usu);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // ==================== MÉTODOS PARA PRODUCTO ====================
    public void crearProducto(Producto prod) {
        prodJpa.create(prod);
    }

    public List<Producto> traerProductos() {
        return prodJpa.findProductoEntities();
    }

    public void borrarProducto(int id) {
        try {
            prodJpa.destroy(id);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Producto traerProducto(int id) {
        return prodJpa.findProducto(id);
    }

    public void editarProducto(Producto prod) {
        try {
            prodJpa.edit(prod);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // ==================== MÉTODOS PARA PEDIDO ====================
    public void crearPedido(Pedido ped) {
        pedJpa.create(ped);
    }

    public List<Pedido> traerPedidos() {
        return pedJpa.findPedidoEntities();
    }

    public void borrarPedido(int id) {
        try {
            pedJpa.destroy(id);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Pedido traerPedido(int id) {
        return pedJpa.findPedido(id);
    }

    public void editarPedido(Pedido ped) {
        try {
            pedJpa.edit(ped);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // ==================== MÉTODOS PARA DETALLE PEDIDO ====================
    public void crearDetallePedido(DetallePedido det) {
        detJpa.create(det);
    }

    public List<DetallePedido> traerDetallePedidos() {
        return detJpa.findDetallePedidoEntities();
    }

    public void borrarDetallePedido(int id) {
        try {
            detJpa.destroy(id);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public DetallePedido traerDetallePedido(int id) {
        return detJpa.findDetallePedido(id);
    }

    public void editarDetallePedido(DetallePedido det) {
        try {
            detJpa.edit(det);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // ==================== MÉTODOS PARA PAGO ====================
    public void crearPago(Pago pago) {
        pagoJpa.create(pago);
    }

    public List<Pago> traerPagos() {
        return pagoJpa.findPagoEntities();
    }

    public void borrarPago(int id) {
        try {
            pagoJpa.destroy(id);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Pago traerPago(int id) {
        return pagoJpa.findPago(id);
    }

    public void editarPago(Pago pago) {
        try {
            pagoJpa.edit(pago);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
    // ==================== MÉTODOS PARA CATEGORIA ====================
    // Estos métodos propagan las excepciones para que la capa superior decida
    // qué mensaje (seguro) mostrar al usuario.
    public void crearCategoria(Categoria cat) {
        catJpa.create(cat);
    }

    public List<Categoria> traerCategorias() {
        return catJpa.findCategoriaEntities();
    }

    public Categoria traerCategoria(int id) {
        return catJpa.findCategoria(id);
    }

    public void editarCategoria(Categoria cat) throws NonexistentEntityException {
        catJpa.edit(cat);
    }

    public void borrarCategoria(int id) throws NonexistentEntityException, ReglaNegocioException {
        catJpa.destroy(id);
    }

    public boolean existeCategoriaConNombre(String nombre, int idExcluir) {
        return catJpa.existeNombre(nombre, idExcluir);
    }

    // ==================== VALIDACIONES USUARIO ====================
    public boolean existeCorreoEnOtroUsuario(String correo, int idUsuarioActual) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT COUNT(u) FROM Usuario u WHERE u.correo = :correo AND u.idusuario != :id";
            Query query = em.createQuery(jpql);
            query.setParameter("correo", correo);
            query.setParameter("id", idUsuarioActual);
            Long count = (Long) query.getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }

    public boolean existeTelefonoEnOtroUsuario(String telefono, int idUsuarioActual) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT COUNT(u) FROM Usuario u WHERE u.telefono = :telefono AND u.idusuario != :id";
            Query query = em.createQuery(jpql);
            query.setParameter("telefono", telefono);
            query.setParameter("id", idUsuarioActual);
            Long count = (Long) query.getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }
       
       
}



