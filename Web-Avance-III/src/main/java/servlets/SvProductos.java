
package servlets;
import clases.Controladora;
import clases.Producto;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;

@WebServlet("/SvProductos")
public class SvProductos extends HttpServlet {

    Controladora control = new Controladora();

    // Mostrar lista de productos (GET)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Producto> lista = control.traerProductos();

        // Se usa request en lugar de session
        request.setAttribute("productos", lista);

        // Redireccionamos internamente al JSP que mostrará la lista
        RequestDispatcher dispatcher = request.getRequestDispatcher("listarProductos.jsp");
        dispatcher.forward(request, response);
    }

    // Registrar nuevo producto (POST)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String nombre = request.getParameter("nombre");
            String descripcion = request.getParameter("descripcion");
            String marca = request.getParameter("marca");
            double precio = Double.parseDouble(request.getParameter("precio"));
            int stock = Integer.parseInt(request.getParameter("stock"));

            Producto p = new Producto();
            p.setNombre(nombre);
            p.setDescripcion(descripcion);
            p.setMarca(marca);
            p.setPrecio(precio);
            p.setStock(stock);

            control.crearProducto(p);

            // Luego de guardar, redirigimos a la lista actualizada
            response.sendRedirect("SvProductos");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error al registrar producto: " + e.getMessage());
        }
    }
}

