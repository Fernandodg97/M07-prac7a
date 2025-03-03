package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import logicaNegocio.Alumno;
import logicaNegocio.Usuario;
import util.SessionManager;

@WebServlet("/Servlet")
public class Servlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String PG_RESPUESTA_DESCONEXION = "desconectado.jsp";
    private static final String PG_RESPUESTA_INICIO = "validLogin.jsp";
    private static final String PG_RESPUESTA_VALIDARSE = "acceso.jsp";
    private static final String PG_RESPUESTA_LOGIN = "Login";
    
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("UPEstudiantes");
    
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManager entityManager = emf.createEntityManager();
        HttpSession sesion = request.getSession(true);
        SessionManager.incrementarContadorSesion(sesion);
        String operacion = request.getParameter("operacion");

        if ("info".equals(operacion)) {
            request.getRequestDispatcher("infosesion.jsp").forward(request, response);
            return;
        }

        if ("desconectar".equals(operacion)) {
            sesion.invalidate();
            response.sendRedirect(PG_RESPUESTA_DESCONEXION);
            return;
        }
        
        if ("consulta".equals(operacion)) {
            List<Alumno> alumnos = entityManager.createQuery("SELECT a FROM Alumno a", Alumno.class).getResultList();
            boolean usarJSTL = "true".equals(request.getParameter("jstl"));
            
            if (usarJSTL) {
                request.setAttribute("listaAlumnos", alumnos);
                request.getRequestDispatcher("consulta.jsp").forward(request, response);
            } else {
                response.setContentType("text/html; charset=UTF-8");
                PrintWriter out = response.getWriter();
                out.println("<html><head><title>Resultados de la Consulta</title></head><body>");
                out.println("<h2>Resultados de la Consulta</h2>");
                out.println("<table border='1'><tr><th>ID</th><th>Nombre</th><th>Curso</th></tr>");
                
                for (Alumno alumno : alumnos) {
                    out.println("<tr><td>" + alumno.getId() + "</td><td>" + alumno.getNombre() + "</td><td>" + alumno.getCurso() + "</td></tr>");
                }
                
                out.println("</table></body></html>");
            }
        }
        
        if ("alta".equals(operacion)) {
            String idParam = request.getParameter("txtID");
            String curso = request.getParameter("txtCurso");
            String nombre = request.getParameter("txtNombre");
            int id = (idParam != null && !idParam.isEmpty()) ? Integer.parseInt(idParam) : -1;

            Usuario usuario = (Usuario) sesion.getAttribute("usuario");
            if (usuario != null) {
                entityManager.getTransaction().begin();
                Alumno alumno = new Alumno();
                alumno.setId(id);
                alumno.setNombre(nombre);
                alumno.setCurso(curso);
                entityManager.persist(alumno);
                entityManager.getTransaction().commit();
                response.sendRedirect(PG_RESPUESTA_INICIO);
            } else {
                sesion.setAttribute("sesAlumno", id);
                sesion.setAttribute("sesCurso", curso);
                sesion.setAttribute("sesNombre", nombre);
                response.sendRedirect(PG_RESPUESTA_VALIDARSE);
                return;
            }
        }
        
        if ("validar".equals(operacion)) {
            response.sendRedirect(PG_RESPUESTA_LOGIN);
        }

        if ("informe".equals(operacion)) {
            List<Alumno> listaAlumnos = entityManager.createQuery("SELECT a FROM Alumno a", Alumno.class).getResultList();
            request.setAttribute("lista", listaAlumnos);
            request.setAttribute("optInformes", request.getParameter("optInformes"));
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/Report");
            dispatcher.forward(request, response);
        }
        entityManager.close();
    }
}
