<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="UTF-8">
<title>Gestión de estudiantes</title>
</head>
<body>
<h1>Gestión de estudiantes (Prac7a)</h1>

<%
    Boolean sesionNueva = session.isNew();
    if (!sesionNueva) {
%>
    <!-- Enlace para mostrar la información de la sesión -->
    <a href="Servlet?operacion=info">Información de sesión</a>

    <!-- Enlace para desconectar -->
    <a href="javascript:desconectar()">Desconectar</a>

    <script type="text/javascript">
    function desconectar() {
        var log_out = confirm("Con esta acción se finaliza la sesión actual. ¿Está seguro?");
        if (log_out) {
            window.location = "Servlet?operacion=desconectar";
        }
    }
    </script>
<%
    }
%>

<h2>Consulta de estudiantes</h2>
<!-- Formulario para la consulta de estudiantes -->
<form action="Servlet" method="GET">
    <input type="hidden" name="operacion" value="consulta">
    
    <input type="text" id="sentencia" name="sentencia" placeholder="SELECT * FROM alumnos" required><br><br>
    <button type="submit">Consultar</button><br><br>

    <!-- Opciones para usar JSTL en la consulta -->
    <h3>Opciones</h3>
    <label for="jstl">Técnica JSTL en resultados de consulta:</label><br>
    <input type="radio" id="jstl" name="jstl" value="true" checked>
    <label for="jstl-si">Sí</label>
    <input type="radio" id="jstl" name="jstl" value="false">
    <label for="jstl-no">No</label>
</form>

<br>

<h2>Alta de Alumno</h2>
<!-- Formulario para dar de alta a un alumno -->
<form action="Servlet" method="POST">
    <input type="hidden" name="operacion" value="alta">
    
    <label for="id">ID Alumno:</label><br>
    <input type="number" id="id" name="txtID" placeholder="1" min="1" required><br><br>

    <label for="nombre">Nombre:</label><br>
    <input type="text" id="nombre" name="txtNombre" placeholder="Nombre Apellido" required><br><br>

    <label for="curso">Curso:</label><br>
    <input type="text" id="curso" name="txtCurso" placeholder="DAW" required><br><br>

    <button type="submit">Alta Alumno</button>
</form>

<br>

<h2>Informes</h2>
<!-- Formulario para generar informes -->
<form action="Servlet" method="POST">
	<input type="hidden" name="operacion" value="informe">
<!-- Pasarlo al servlet y que coja el valor para enviarlo al report -->
    <input type="radio" value="application/pdf" name="optInformes" checked>
    <label for="pdf">PDF</label>
    <input type="radio" value="application/vnd.ms-excel" name="optInformes">
    <label for="excel">Excel</label>
    <input type="radio" value="application/msword" name="optInformes">
    <label for="word">Word</label>
    <input type="radio" value="text/html" name="optInformes">
    <label for="html">HTML</label><br><br>
    <input type="submit">
</form>

</body>
</html>
