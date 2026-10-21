package clases;

import java.util.List;
import persistencia.exceptions.NonexistentEntityException;
import persistencia.ControladoraPersistencia;
import clases.ReglaNegocioException;
import clases.SeguridadUtil;

public class Controladora {

    private final ControladoraPersistencia controlPersis = new ControladoraPersistencia();

    // ==================== USUARIOS ====================
    /**
     * Crea un nuevo usuario aplicando validaciones de seguridad:
     * <ul>
     *   <li>Objeto no nulo.</li>
     *   <li>Formato de email y teléfono válido.</li>
     *   <li>Contraseña se almacena como hash seguro (BCrypt).</li>
     *   <li>Sanitiza nombre y apellido para prevenir XSS.</li>
     *   <li>Verifica unicidad de email y teléfono a través de la capa de persistencia.</li>
     * </ul>
     */
    public void crearUsuario(Usuario usu) throws ReglaNegocioException {
        // 1. Validación de nulidad
        if (usu == null) {
            throw new ReglaNegocioException("El objeto Usuario no puede ser nulo.");
        }
        // 2. Validación de email y teléfono
        if (!SeguridadUtil.validateEmail(usu.getCorreo())) {
            throw new ReglaNegocioException("Formato de correo electrónico inválido.");
        }
        if (!SeguridadUtil.validatePhone(usu.getTelefono())) {
            throw new ReglaNegocioException("Formato de teléfono inválido.");
        }
        // 3. Hash de contraseña (BCrypt) si se provee
        if (usu.getPassword() != null && !usu.getPassword().isEmpty()) {
            usu.setPassword(SeguridadUtil.hashPassword(usu.getPassword()));
        }
        // 4. Sanitizar campos de texto para prevenir XSS
        usu.setNombre(SeguridadUtil.sanitize(usu.getNombre()));
        usu.setApellido(SeguridadUtil.sanitize(usu.getApellido()));
        // 5. Delegar a la capa de persistencia
        controlPersis.crearUsuario(usu);
    }
        // Validaciones básicas de seguridad
        if (usu == null) {
            throw new ReglaNegocioException("El objeto Usuario no puede ser nulo.");
        }
        if (!SecurityUtils.validateEmail(usu.getCorreo())) {
            throw new ReglaNegocioException("Formato de correo electrónico inválido.");
        }
        if (!SecurityUtils.validatePhone(usu.getTelefono())) {
            throw new ReglaNegocioException("Formato de teléfono inválido.");
        }
        // Hash de la contraseña antes de persistir
        if (usu.getPassword() != null && !usu.getPassword().isEmpty()) {
            usu.setPassword(SecurityUtils.hashPassword(usu.getPassword()));
        }
        // Sanitizar campos de texto para prevenir XSS
        usu.setNombre(SecurityUtils.sanitize(usu.getNombre()));
        usu.setApellido(SecurityUtils.sanitize(usu.getApellido()));

    }

    }

    public List<Usuario> traerUsuarios() {
        return controlPersis.traerUsuarios();
    }

    public void borrarUsuario(int idEliminar) {
        controlPersis.borrarUsuario(idEliminar);
    }

    public Usuario traerUsuario(int idEditar) {
        return controlPersis.traerUsuario(idEditar);
    }

    public void editarUsuario(Usuario usu) {
        controlPersis.editarUsuario(usu);
    }

    // ==================== PRODUCTOS ====================
    public void crearProducto(Producto prod) {
        controlPersis.crearProducto(prod);
    }

    public List<Producto> traerProductos() {
        return controlPersis.traerProductos();
    }

    public void borrarProducto(int idEliminar) {
        controlPersis.borrarProducto(idEliminar);
    }

    public Producto traerProducto(int idEditar) {
        return controlPersis.traerProducto(idEditar);
    }

    public void editarProducto(Producto prod) {
        controlPersis.editarProducto(prod);
    }

    // ==================== CATEGORIAS ====================
    public List<Categoria> traerCategorias() {
        return controlPersis.traerCategorias();
    }

    public Categoria traerCategoria(int id) {
        return controlPersis.traerCategoria(id);
    }

    public void crearCategoria(String nombre, String descripcion) throws ReglaNegocioException {
        if (controlPersis.existeCategoriaConNombre(nombre, 0)) {
            throw new ReglaNegocioException("Ya existe una categoría con ese nombre.");
        }
        controlPersis.crearCategoria(new Categoria(nombre, descripcion));
    }

    public void editarCategoria(int id, String nombre, String descripcion)
            throws ReglaNegocioException, NonexistentEntityException {
        if (controlPersis.existeCategoriaConNombre(nombre, id)) {
            throw new ReglaNegocioException("Ya existe otra categoría con ese nombre.");
        }
        Categoria cat = new Categoria(nombre, descripcion);
        cat.setIdcategoria(id);
        controlPersis.editarCategoria(cat);
    }

    public void borrarCategoria(int id) throws ReglaNegocioException, NonexistentEntityException {
        controlPersis.borrarCategoria(id);
    }

    // ==================== PEDIDOS ====================
    public void crearPedido(Pedido ped) {
        controlPersis.crearPedido(ped);
    }

    public List<Pedido> traerPedidos() {
        return controlPersis.traerPedidos();
    }

    public void borrarPedido(int idEliminar) {
        controlPersis.borrarPedido(idEliminar);
    }

    public Pedido traerPedido(int idEditar) {
        return controlPersis.traerPedido(idEditar);
    }

    public void editarPedido(Pedido ped) {
        controlPersis.editarPedido(ped);
    }

    // ==================== DETALLE PEDIDOS ====================
    public void crearDetallePedido(DetallePedido det) {
        controlPersis.crearDetallePedido(det);
    }

    public List<DetallePedido> traerDetallePedidos() {
        return controlPersis.traerDetallePedidos();
    }

    public void borrarDetallePedido(int idEliminar) {
        controlPersis.borrarDetallePedido(idEliminar);
    }

    public DetallePedido traerDetallePedido(int idEditar) {
        return controlPersis.traerDetallePedido(idEditar);
    }

    public void editarDetallePedido(DetallePedido det) {
        controlPersis.editarDetallePedido(det);
    }

    // ==================== PAGOS ====================
    public void crearPago(Pago pago) {
        controlPersis.crearPago(pago);
    }

    public List<Pago> traerPagos() {
        return controlPersis.traerPagos();
    }

    public void borrarPago(int idEliminar) {
        controlPersis.borrarPago(idEliminar);
    }

    public Pago traerPago(int idEditar) {
        return controlPersis.traerPago(idEditar);
    }

    public void editarPago(Pago pago) {
        controlPersis.editarPago(pago);
    }
    
    public boolean existeCorreoEnOtroUsuario(String correo, int idUsuarioActual) {
        return controlPersis.existeCorreoEnOtroUsuario(correo, idUsuarioActual);
    }

    public boolean existeTelefonoEnOtroUsuario(String telefono, int idUsuarioActual) {
        return controlPersis.existeTelefonoEnOtroUsuario(telefono, idUsuarioActual);
    }  
    
}
