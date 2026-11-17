package com.gestestjdbc.servlets;
import com.gestestjdbc.database.DatabaseConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@WebServlet("/controlador")
public class AppController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        List<Alumno> alumnos = new ArrayList<>();
        String consulta = request.getParameter("select");
        try (Connection connection = DatabaseConnection.getConnection();
             java.sql.Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(consulta)) {
            while (resultSet.next()) {
                alumnos.add(new Alumno(
                        resultSet.getString("id"),
                        resultSet.getString("curso"),
                        resultSet.getString("nombre")));
            }
        } catch (SQLException e) {
            System.out.println("Error MySQL");
            return;
        }

        StringBuilder html = new StringBuilder("<!doctype html><html lang=\"es\"><meta charset=\"UTF-8\"><body bgcolor=\"lightyellow\"><h2>Usa JDBC para recuperar registros de una tabla</h2><hr>id curso nombre<br>");

        for (Alumno alumno : alumnos) {
            html.append(alumno.getId()).append(" ")
                    .append(escapeHtml(alumno.getCurso())).append(" ")
                    .append(escapeHtml(alumno.getNombre())).append("<br>");
        }

        html.append("<p><a href='index.html'>Volver</a></p></body></html>");
        response.getWriter().write(html.toString());
    }

    private static String escapeHtml(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }

    private static class Alumno {
        private final String nombre;
        private final String curso;
        private final String id;

        private Alumno(String id, String curso, String nombre) {
            this.id = id;
            this.nombre = nombre;
            this.curso = curso;
        }

        private String getId() { return id; }
        private String getNombre() { return nombre; }
        private String getCurso() { return curso; }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String accion = request.getParameter("accion");
        String idTexto = request.getParameter("id");
        String curso = request.getParameter("curso");
        String nombre = request.getParameter("nombre");

        try {
            int id = Integer.parseInt(idTexto);

            String sql = "INSERT INTO alumnos (id, curso, nombre) VALUES (?, ?, ?)";

            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement statement = connection.prepareStatement(sql)) {

                statement.setInt(1, id);
                statement.setString(2, curso);
                statement.setString(3, nombre);

                int filasInsertadas = statement.executeUpdate();

                if (filasInsertadas == 1) {
                    response.getWriter().write(
                            "<!doctype html><html lang='es'><meta charset='UTF-8'>" +
                                    "<h1>Alumno añadido correctamente</h1>" +
                                    "<p><a href='index.html'>Volver</a></p></html>"
                    );
                } else {
                    response.getWriter().write("<h1>No se pudo añadir el alumno</h1>");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error MySQL");
        }
    }

}
