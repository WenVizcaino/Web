
package Controlador;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

/**
 * Servlet encargado de enviar correos electrónicos a través de Gmail usando Jakarta Mail.
 */
@WebServlet("/EnviarCorreo")
public class EnviarCorreo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor del Servlet (opcional en este caso)
	 */
	public EnviarCorreo() {
		super();
	}

	/**
	 * Método GET (no utilizado en este servlet)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// No implementado porque solo se usa POST
	}

	/**
	 * Método POST: Procesa el envío del correo electrónico.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Obtener los datos del formulario enviados desde EnviarCorreo.jsp
		String destinatario = request.getParameter("destinatario"); // Correo del receptor
		String asunto = request.getParameter("asunto");             // Asunto del correo
		String mensajeTexto = request.getParameter("mensaje");      // Cuerpo del mensaje

		// Correo del remitente (tu cuenta Gmail) y contraseña de aplicación
		String remitente = "wendyvizcaino73@gmail.com";
		String clave = "ifmb ddea icbt cqev"; // IMPORTANTE: Es la contraseña de aplicación, no la contraseña personal

		// Configuración para conexión SMTP con Gmail
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true"); // Habilita autenticación
		props.put("mail.smtp.starttls.enable", "true"); // Activa TLS
		props.put("mail.smtp.host", "smtp.gmail.com"); // Servidor SMTP de Gmail
		props.put("mail.smtp.port", "587"); // Puerto SMTP con TLS

		// Crear una sesión autenticada con las credenciales del remitente
		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(remitente, clave);
			}
		});

		try {
			// Crear el mensaje MIME
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(remitente)); // Dirección del remitente
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario)); // Receptor(es)
			message.setSubject(asunto); // Asunto
			message.setText(mensajeTexto); // Cuerpo del mensaje en texto plano

			// Enviar el mensaje
			Transport.send(message);

			// Mostrar respuesta de éxito en el navegador
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<html><body style='font-family:sans-serif; text-align:center; padding-top:40px;'>");
			out.println("<h2 style='color:green;'>¡Correo enviado exitosamente a " + destinatario + "!</h2>");
			out.println("<a href='EnviarCorreo.jsp'>← Volver</a>");
			out.println("</body></html>");

		} catch (MessagingException e) {
			// En caso de error al enviar el correo, se muestra el mensaje de excepción
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<html><body style='font-family:sans-serif; text-align:center; padding-top:40px;'>");
			out.println("<h2 style='color:red;'>Error al enviar correo:</h2>");
			out.println("<p>" + e.getMessage() + "</p>");
			out.println("<a href='EnviarCorreo.jsp'>← Volver</a>");
			out.println("</body></html>");
		}
	}
}
