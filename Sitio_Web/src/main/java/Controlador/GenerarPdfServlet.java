package Controlador;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@WebServlet("/GenerarPdfServlet")
public class GenerarPdfServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Configuración de conexión a la base de datos MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/tienda_mascota?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "2556229";

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Connection connection = null;

        try {
            // Carga del driver JDBC de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establecimiento de conexión a la base de datos
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            // Configuración de la respuesta HTTP para enviar un archivo PDF
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=usuarios.pdf");

            // Creación del documento PDF
            Document document = new Document();
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            // Estilo y título del documento
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, BaseColor.DARK_GRAY);
            Paragraph title = new Paragraph("Lista de Clientes", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" ")); // Espacio en blanco

            // Configuración de la tabla con 5 columnas
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100); 
            table.setWidths(new float[] {3f, 2f, 5f, 3f, 4f}); 
            table.setSpacingBefore(10f); 
            table.setSpacingAfter(10f);  
            // Fuente y color para encabezados de tabla
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);
            BaseColor headerBgColor = new BaseColor(40, 116, 166);
            BaseColor rowColor1 = BaseColor.WHITE;         
            BaseColor rowColor2 = new BaseColor(230, 240, 255);

            // Encabezados de la tabla
            String[] headers = {"Nombre", "Cédula", "Email", "Teléfono", "Dirección"};
            for (String header : headers) {
                PdfPCell headerCell = new PdfPCell(new Phrase(header, headerFont));
                headerCell.setBackgroundColor(headerBgColor);
                headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                headerCell.setPadding(8);
                table.addCell(headerCell);
            }

            // Consulta a la base de datos para obtener los usuarios
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT nombre, cedula, email, telefono, direccion FROM usuario");

            Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 11, BaseColor.DARK_GRAY);
            int rowCount = 0;

            // Iteración sobre los resultados para poblar la tabla
            while (rs.next()) {
                // Alternar colores de fondo para filas
                BaseColor bgColor = (rowCount % 2 == 0) ? rowColor1 : rowColor2;

                table.addCell(createCell(rs.getString("nombre"), dataFont, Element.ALIGN_LEFT, bgColor, false));
                table.addCell(createCell(rs.getString("cedula"), dataFont, Element.ALIGN_CENTER, bgColor, true));
                table.addCell(createCell(rs.getString("email"), dataFont, Element.ALIGN_LEFT, bgColor, false));
                table.addCell(createCell(rs.getString("telefono"), dataFont, Element.ALIGN_CENTER, bgColor, true));
                table.addCell(createCell(rs.getString("direccion"), dataFont, Element.ALIGN_LEFT, bgColor, false));

                rowCount++;
            }

            // Agregar tabla al documento
            document.add(table);

            // Pie de página con crédito del generador del reporte
            Paragraph footer = new Paragraph("Reporte generado por WenViz",
                    FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 9, BaseColor.GRAY));
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

            // Cierre del documento y liberación de recursos
            document.close();
            rs.close();
            stmt.close();
            connection.close();

        } catch (Exception e) {
            // Manejo de excepciones, se lanza ServletException para que el servidor lo maneje
            throw new ServletException(e);
        }
    }

    /**
     * Crea una celda personalizada para una tabla PDF.
     *
     * 
     */
    private PdfPCell createCell(String content, Font font, int alignment, BaseColor bgColor, boolean noWrap) {
        // Crear una celda con el contenido de texto y fuente especificada
        PdfPCell cell = new PdfPCell(new Phrase(content, font));

        // Alinear el contenido horizontalmente (izquierda, centro, derecha)
        cell.setHorizontalAlignment(alignment);

        // Alinear verticalmente el contenido al centro
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

        // Aplicar color de fondo a la celda
        cell.setBackgroundColor(bgColor);

        // Espaciado interno de la celda
        cell.setPadding(8);

        // Controlar si el texto debe ajustarse o mantenerse en una sola línea
        cell.setNoWrap(noWrap);

        // Color del borde de la celda
        cell.setBorderColor(new BaseColor(200, 200, 200));

        // Devolver la celda ya configurada
        return cell;
    }


    /**
     * Método que maneja peticiones POST simplemente delegando a doGet
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
