package servlets;

import clases.ConexionBD;
import clases.Producto;
import clases.Usuario;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@WebServlet("/GenerarFacturaServlet")
public class GenerarFacturaServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Usuario usuario = null;
        Usuario usuarioSesion = (Usuario) session.getAttribute("usuario");

        if (usuarioSesion != null && usuarioSesion.getIdusuario() != 0) {
            try {
                ConexionBD cnn = new ConexionBD();
                Connection con = cnn.Conectar();

                String sql = "SELECT * FROM usuarios WHERE idusuario = ?";
                try (PreparedStatement stmt = con.prepareStatement(sql)) {
                    stmt.setInt(1, usuarioSesion.getIdusuario());
                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {
                        usuario = new Usuario();
                        usuario.setIdusuario(rs.getInt("idusuario"));
                        usuario.setNombre(rs.getString("nombre"));
                        usuario.setApaterno(rs.getString("apaterno"));
                        usuario.setAmaterno(rs.getString("amaterno"));
                        usuario.setnDocumento(rs.getString("nDocumento"));
                        usuario.setCorreo(rs.getString("correo"));
                        usuario.setTelefono(rs.getString("telefono"));
                        usuario.setRol(rs.getString("rol"));
                        usuario.setDireccion(rs.getString("direccion"));
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Obtener parámetros del formulario
        String total = request.getParameter("total");
        String recibido = request.getParameter("recibido");
        String cambio = request.getParameter("cambio");
        String moneda = request.getParameter("moneda");
        String empresa = request.getParameter("empresa");

        // Obtener carrito de productos
        List<Producto> carrito = new ArrayList<>();
        Object objCarrito = session.getAttribute("carrito");

        if (objCarrito instanceof List<?>) {
            List<?> tempList = (List<?>) objCarrito;

            if (tempList.stream().allMatch(p -> p instanceof Producto)) {
                carrito = tempList.stream().map(p -> (Producto) p).collect(Collectors.toList());
            }
        }
        
        // Registrar en BD: pedido, pago, detallepedido
        try {
            ConexionBD cnn = new ConexionBD();
            Connection con = cnn.Conectar();

            // Validar stock disponible antes de registrar el pedido
            boolean stockInsuficiente = false;
            StringBuilder productosAgotados = new StringBuilder();

            String sqlStock = "SELECT stock, nombre FROM productos WHERE idproducto = ?";
            try (PreparedStatement stmtStock = con.prepareStatement(sqlStock)) {
                for (Producto p : carrito) {
                    stmtStock.setInt(1, p.getIdproducto());
                    ResultSet rsStock = stmtStock.executeQuery();

                    if (rsStock.next()) {
                        int stockActual = rsStock.getInt("stock");
                        String nombreProd = rsStock.getString("nombre");
                        if (p.getCantidad() > stockActual) {
                            stockInsuficiente = true;
                            productosAgotados.append("- ").append(nombreProd)
                                             .append(" (Stock: ").append(stockActual)
                                             .append(", Solicitado: ").append(p.getCantidad())
                                             .append(")\n");
                        }
                    }
                }
            }

            if (stockInsuficiente) {
                session.setAttribute("errorStock", "No hay stock suficiente para los siguientes productos:\n" + productosAgotados);
                response.sendRedirect("ProcesoCompras2.jsp"); // O tu página de compra
                return;
            }
            
            // Insertar pedido
            int idPedido = 0;
            String sqlPedido = "INSERT INTO pedido (fechapedido, idusuario) VALUES (NOW(), ?)";
            try (PreparedStatement stmtPedido = con.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS)) {
                stmtPedido.setInt(1, usuario.getIdusuario());
                stmtPedido.executeUpdate();
                ResultSet rsPedido = stmtPedido.getGeneratedKeys();
                if (rsPedido.next()) {
                    idPedido = rsPedido.getInt(1);
                }
            }

            // Insertar pago
            int idPago = 0;
            String sqlPago = "INSERT INTO pago (monto, metodopago, cambio) VALUES (?, ?, ?)";
            try (PreparedStatement stmtPago = con.prepareStatement(sqlPago, Statement.RETURN_GENERATED_KEYS)) {
                stmtPago.setDouble(1, Double.parseDouble(recibido));
                stmtPago.setString(2, moneda);
                stmtPago.setDouble(3, Double.parseDouble(cambio));
                stmtPago.executeUpdate();
                ResultSet rsPago = stmtPago.getGeneratedKeys();
                if (rsPago.next()) {
                    idPago = rsPago.getInt(1);
                }
            }
            
            // Insertar detallepedido
            String sqlDetalle = "INSERT INTO detallepedido (idpedido, idproducto, cantidad, subtotal, igv, totalpago, idpago) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmtDetalle = con.prepareStatement(sqlDetalle)) {
                for (Producto p : carrito) {
                    double subtotal = p.getSubtotal();
                    double igv = subtotal * 0.18;
                    double totalPago = subtotal + igv;

                    stmtDetalle.setInt(1, idPedido);
                    stmtDetalle.setInt(2, p.getIdproducto());
                    stmtDetalle.setInt(3, p.getCantidad());
                    stmtDetalle.setDouble(4, subtotal);
                    stmtDetalle.setDouble(5, igv);
                    stmtDetalle.setDouble(6, totalPago);
                    stmtDetalle.setInt(7, idPago);
                    stmtDetalle.addBatch();
                }
                stmtDetalle.executeBatch();
            }
            
             // Actualizar Stock
            String sqlUpdateStock = "UPDATE productos SET stock = stock - ? WHERE idproducto = ?";
            try (PreparedStatement stmtUpdateStock = con.prepareStatement(sqlUpdateStock)) {
                for (Producto p : carrito) {
                    stmtUpdateStock.setInt(1, p.getCantidad());
                    stmtUpdateStock.setInt(2, p.getIdproducto());
                    stmtUpdateStock.addBatch();
                }
                stmtUpdateStock.executeBatch();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejo de error, tal vez revertir el pedido o notificar al usuario
        }
        

        // Generar PDF
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=Factura.pdf");

        Document document = new Document();

        try {
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            Font titleFont = new Font(Font.BOLD, 18);
            Font textFont = new Font(Font.NORMAL, 12);

            Paragraph title = new Paragraph("DETALLE DE LA VENTA", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            if (usuario != null) {
                String nombreCompleto = Stream.of(usuario.getNombre(), usuario.getApaterno(), usuario.getAmaterno())
                        .filter(Objects::nonNull)
                        .collect(Collectors.joining(" "));
                document.add(new Paragraph("\nCliente: " + nombreCompleto.trim(), textFont));

                String tipoDoc = usuario.getTipoDocumento() != null ? usuario.getTipoDocumento().trim() : "";
                String numeroDoc = usuario.getnDocumento() != null ? usuario.getnDocumento().trim() : "";
                if (!tipoDoc.isEmpty() || !numeroDoc.isEmpty()) {
                    document.add(new Paragraph("Documento: " + tipoDoc + " " + numeroDoc, textFont));
                }

                String direccion = usuario.getDireccion();
                if (direccion != null && !direccion.trim().isEmpty()) {
                    document.add(new Paragraph("Dirección: " + direccion.trim(), textFont));
                }
            }

            document.add(new Paragraph("Empresa de Envío: " + (empresa != null ? empresa : ""), textFont));
            document.add(new Paragraph("\n", textFont));

            document.add(new Paragraph("Resumen de Compra:", textFont));
            if (!carrito.isEmpty()) {
                for (Producto p : carrito) {
                    String linea = "- " + p.getNombre() + " x" + p.getCantidad() + "   S/ " + p.getSubtotal();
                    document.add(new Paragraph(linea, textFont));
                }
            } else {
                document.add(new Paragraph("No se encontraron productos.", textFont));
            }

            document.add(new Paragraph("\n------------------------------", textFont));
            document.add(new Paragraph("Total a Pagar: S/ " + total, textFont));
            document.add(new Paragraph("Recibido: S/ " + recibido, textFont));
            document.add(new Paragraph("Cambio: S/ " + cambio, textFont));
            document.add(new Paragraph("Moneda: " + moneda, textFont));

            Font finalFont = new Font(Font.BOLD, 16);
            Paragraph gracias = new Paragraph("\nGRACIAS POR SU COMPRA!", finalFont);
            gracias.setAlignment(Element.ALIGN_CENTER);
            document.add(gracias);

        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }
}
    