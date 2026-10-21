
package servlets;
import clases.Controladora;
import clases.Usuario;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "svUsuarios", urlPatterns = {"/svUsuarios"})
public class svUsuarios extends HttpServlet {

    Controladora control = new Controladora();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Usuario> listaUsuarios = new ArrayList<>();
        listaUsuarios = control.traerUsuarios();

        HttpSession mision = request.getSession();
        mision.setAttribute("listaUsuarios", listaUsuarios);

        response.sendRedirect("listarUsuarios.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String numeroDocumento = request.getParameter("numeroDocumento");
            String tipoDocumento = request.getParameter("tipoDocumento");
            String nombre = request.getParameter("nombre");
            String apaterno = request.getParameter("apaterno");
            String amaterno = request.getParameter("amaterno");
            String sexo = request.getParameter("sexo");

            String fechaNacStr = request.getParameter("fechaN");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaNac = sdf.parse(fechaNacStr);

            String nacionalidad = request.getParameter("nacionalidad");
            String telefono = request.getParameter("telefono");
            String correo = request.getParameter("correo");
            String direccion = request.getParameter("direccion");
            String departamento = request.getParameter("departamento");
            String provincia = request.getParameter("provincia");
            String distrito = request.getParameter("distrito");
            String contraseña = request.getParameter("contraseña");
            String rol = request.getParameter("rol");

            Usuario usu = new Usuario();
            usu.setnDocumento(numeroDocumento);
            usu.setTipoDocumento(tipoDocumento);
            usu.setNombre(nombre);
            usu.setApaterno(apaterno);
            usu.setAmaterno(amaterno);
            usu.setSexo(sexo);
            usu.setFechaN(fechaNac);
            usu.setNacionalidad(nacionalidad);
            usu.setTelefono(telefono);
            usu.setCorreo(correo);
            usu.setDireccion(direccion);
            usu.setDepartamento(departamento);
            usu.setProvincia(provincia);
            usu.setDistrito(distrito);
            usu.setContraseña(contraseña);
            usu.setRol(rol);

            control.crearUsuario(usu);

            // Actualiza la lista y redirige
            List<Usuario> listaUsuarios = control.traerUsuarios();
            HttpSession mision = request.getSession();
            mision.setAttribute("listaUsuarios", listaUsuarios);

            response.sendRedirect("listarUsuarios.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp"); 
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet para gestionar usuarios";
    }
}
