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


    private static final String URL = "jdbc:mysql://localhost:3306/tienda_mascota?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "2556229";

   
    @Override
    

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Connection connection = null;

        try {
        
            Class.forName("com.mysql.cj.jdbc.Driver");
          
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

     
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=usuarios.pdf");

          
            Document document = new Document();
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

           
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, BaseColor.DARK_GRAY);
            Paragraph title = new Paragraph("Lista de Clientes", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" ")); 

       
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100); 
            table.setWidths(new float[] {3f, 2f, 5f, 3f, 4f}); 
            table.setSpacingBefore(10f); 
            table.setSpacingAfter(10f);  
          
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.WHITE);
            BaseColor headerBgColor = new BaseColor(40, 116, 166);
            BaseColor rowColor1 = BaseColor.WHITE;         
            BaseColor rowColor2 = new BaseColor(230, 240, 255);

           
            String[] headers = {"Nombre", "Cédula", "Email", "Teléfono", "Dirección"};
            for (String header : headers) {
                PdfPCell headerCell = new PdfPCell(new Phrase(header, headerFont));
                headerCell.setBackgroundColor(headerBgColor);
                headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                headerCell.setPadding(8);
                table.addCell(headerCell);
            }

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT nombre, cedula, email, telefono, direccion FROM usuario");

            Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 11, BaseColor.DARK_GRAY);
            int rowCount = 0;

          
            while (rs.next()) {
               
                BaseColor bgColor = (rowCount % 2 == 0) ? rowColor1 : rowColor2;

                table.addCell(createCell(rs.getString("nombre"), dataFont, Element.ALIGN_LEFT, bgColor, false));
                table.addCell(createCell(rs.getString("cedula"), dataFont, Element.ALIGN_CENTER, bgColor, true));
                table.addCell(createCell(rs.getString("email"), dataFont, Element.ALIGN_LEFT, bgColor, false));
                table.addCell(createCell(rs.getString("telefono"), dataFont, Element.ALIGN_CENTER, bgColor, true));
                table.addCell(createCell(rs.getString("direccion"), dataFont, Element.ALIGN_LEFT, bgColor, false));

                rowCount++;
            }

            
            document.add(table);

       
            Paragraph footer = new Paragraph("Reporte generado por WenViz",
                    FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 9, BaseColor.GRAY));
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

           
            document.close();
            rs.close();
            stmt.close();
            connection.close();

        } catch (Exception e) {
           
            throw new ServletException(e);
        }
    }

    
    private PdfPCell createCell(String content, Font font, int alignment, BaseColor bgColor, boolean noWrap) {
        PdfPCell cell = new PdfPCell(new Phrase(content, font));

        cell.setHorizontalAlignment(alignment);

        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

        cell.setBackgroundColor(bgColor);
      
        cell.setPadding(8);
        cell.setNoWrap(noWrap);

     
        cell.setBorderColor(new BaseColor(200, 200, 200));

        return cell;
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
