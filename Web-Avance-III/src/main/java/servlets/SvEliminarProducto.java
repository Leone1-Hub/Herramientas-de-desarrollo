
package servlets;
import clases.Controladora;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/SvEliminarProducto")
public class SvEliminarProducto extends HttpServlet {

    Controladora control = new Controladora();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("idproducto");

        try {
            int id = Integer.parseInt(idStr);

            control.borrarProducto(id); 

            // Actualizas la lista y rediriges
            request.getSession().setAttribute("listaProductos", control.traerProductos());

            response.sendRedirect("SvProductos");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

    // No necesitas doPost aquí, solo GET
}

