<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
    <h1>README - Práctica M07 - Desarrollo en Entorno Servidor</h1>
    <p>Esta práctica consiste en una <strong>aplicación web</strong> que permite gestionar los alumnos del centro educativo. Entre sus funcionalidades destacan:</p>
    <ul>
        <li>Consulta de alumnos registrados.</li>
        <li>Registro de nuevos alumnos (requiere un usuario validado).</li>
        <li>Generación de informes en distintos formatos: PDF, Excel, Word y HTML.</li>
        <li>Visualización de información de la sesión y del usuario validado.</li>
    </ul>
    <h2>Tecnologías</h2>
    <ul>
        <li><strong>Eclipse</strong>: Entorno de desarrollo.</li>
        <li><strong>Tomcat 10.1.28</strong>: Servidor de aplicaciones.</li>
        <li><strong>Java Servlets</strong>: Controlador principal.</li>
        <li><strong>HTML, JSP y JSTL</strong>: Capas de presentación.</li>
        <li><strong>Java</strong>: Lógica de negocio separada en clases.</li>
        <li><strong>JPA (Java Persistence API)</strong>: Tecnología para la gestión de la base de datos y la persistencia de los datos.</li>
    </ul>
    <h2>Instalación y Uso</h2>
    <ol>
        <li>Clonar el repositorio.</li>
        <li>Importar el proyecto en Eclipse.</li>
        <li>Maven --> Update Project...</li>
        <li>Configurar Tomcat 10.1.28 como servidor.</li>
        <li>Ejecutar la aplicación y acceder a través del navegador.</li>
    </ol>
    <h2>Configuración</h2>
    <ul>
        <li>Cambiar BBDD: En `Conexion.java`, se modifican las variables: `dbURL`, `user` y `pass`.</li>
        <li>Cambiar o añadir usuarios: En `Login.java`, en la línea 63 dentro del método `validarUsuario`, se añade el usuario o se modifica el existente.</li>
        <li>Para modificar la base de datos que se usa en el informe, se deben modificar las líneas 11, 12 y 13 del archivo `persistence.xml`:</li>
        <pre>
            &lt;property name="jakarta.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/dbalumnos"/&gt;
            &lt;property name="jakarta.persistence.jdbc.user" value="usuario"/&gt;
            &lt;property name="jakarta.persistence.jdbc.password" value="usuario"/&gt;
        </pre>
    </ul>
    <h2>Autor</h2>
    <p>Fernando DG</p>
    <h2>Licencia</h2>
    <p>Este proyecto está bajo la licencia <a href="https://creativecommons.org/licenses/by-nc-sa/3.0/es/">Reconocimiento-NoComercial-CompartirIgual 3.0 España de Creative Commons</a>.</p>
</body>
</html>
