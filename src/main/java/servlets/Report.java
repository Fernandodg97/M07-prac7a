package servlets;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logicaNegocio.Alumno;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.*;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.*;
import net.sf.jasperreports.j2ee.servlets.BaseHttpServlet;
import net.sf.jasperreports.web.util.WebHtmlResourceHandler;

@WebServlet(name = "Report", urlPatterns = {"/Report"}, asyncSupported = false)
public class Report extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private JasperReport jasperReport;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        String jrxmlPath = getServletContext().getRealPath("/WEB-INF/informes/alumnos/Alumnos.jrxml");
        File jrxmlFile = new File(jrxmlPath);
        
        if (!jrxmlFile.exists()) {
            throw new ServletException("El archivo .jrxml no se encuentra: " + jrxmlPath);
        }
        
        try {
            jasperReport = JasperCompileManager.compileReport(jrxmlFile.getAbsolutePath());
            System.out.println("Informe compilado correctamente: " + jrxmlPath);
        } catch (JRException e) {
            throw new ServletException("Error al compilar el informe", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        service(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        service(request, response);
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Collection<Alumno> lista = (List<Alumno>) request.getAttribute("lista");
        
        if (lista == null || lista.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No se encontraron datos para generar el informe.");
            return;
        }

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lista);
        
        try {
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), dataSource);
            String tipoInforme = request.getParameter("optInformes");
            response.setContentType(tipoInforme);
            
            switch (tipoInforme) {
                case "application/pdf":
                    exportPDF(jasperPrint, response);
                    break;
                case "application/vnd.ms-excel":
                    exportExcel(jasperPrint, response);
                    break;
                case "application/msword":
                    exportWord(jasperPrint, response);
                    break;
                default:
                    exportHTML(jasperPrint, response);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void exportPDF(JasperPrint jasperPrint, HttpServletResponse response) throws JRException, IOException {
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
        exporter.exportReport();
    }

    private void exportExcel(JasperPrint jasperPrint, HttpServletResponse response) throws JRException, IOException {
        response.setHeader("Content-Disposition", "inline; filename=informe.xls");
        JRXlsExporter exporter = new JRXlsExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
        exporter.exportReport();
    }

    private void exportWord(JasperPrint jasperPrint, HttpServletResponse response) throws JRException, IOException {
        response.setHeader("Content-Disposition", "inline; filename=informe.doc");
        JRDocxExporter exporter = new JRDocxExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
        exporter.exportReport();
    }

    private void exportHTML(JasperPrint jasperPrint, HttpServletResponse response) throws JRException, IOException {
        HtmlExporter exporter = new HtmlExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        SimpleHtmlExporterOutput exporterOutput = new SimpleHtmlExporterOutput(response.getOutputStream());
        exporterOutput.setImageHandler(new WebHtmlResourceHandler("image?image={0}"));
        exporter.setExporterOutput(exporterOutput);
        exporter.exportReport();
    }
}
