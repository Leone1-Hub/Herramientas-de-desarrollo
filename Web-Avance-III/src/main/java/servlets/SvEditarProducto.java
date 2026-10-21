
package servlets;

import clases.Controladora;
import clases.Producto;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/SvEditarProducto")
public class SvEditarProducto extends HttpServlet {

    Controladora control = new Controladora();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("idproducto");
        if (idStr == null || idStr.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta idproducto");
            return;
        }

        try {
            int idProducto = Integer.parseInt(idStr);
            Producto prod = control.traerProducto(idProducto);

            if (prod == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Producto no encontrado");
                return;
            }

            HttpSession session = request.getSession();
            session.setAttribute("productoEditar", prod);

            response.sendRedirect("editarProducto.jsp");

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID inválido");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        String marca = request.getParameter("marca");
        double precio = Double.parseDouble(request.getParameter("precio"));
        int stock = Integer.parseInt(request.getParameter("stock"));

        Producto prod = (Producto) request.getSession().getAttribute("productoEditar");

        prod.setNombre(nombre);
        prod.setDescripcion(descripcion);
        prod.setMarca(marca);
        prod.setPrecio(precio);
        prod.setStock(stock);

        control.editarProducto(prod);

        request.getSession().setAttribute("listaProductos", control.traerProductos());

        response.sendRedirect("SvProductos");
    }
}

