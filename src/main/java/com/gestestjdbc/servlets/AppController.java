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
        String consulta = "SELECT nombre, curso FROM alumnos ORDER BY id";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(consulta);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                alumnos.add(new Alumno(
                        resultSet.getString("nombre"),
                        resultSet.getString("curso")));
            }
        } catch (SQLException e) {
            getServletContext().log("No se pudieron consultar los alumnos en MySQL.", e);
            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "No se pudo consultar la base de datos. Revisa la conexión y la tabla alumnos.");
            return;
        }

        StringBuilder listaAlumnos = new StringBuilder();

        for (Alumno alumno : alumnos) {
            listaAlumnos.append("<li><strong>")
                    .append(escapeHtml(alumno.getNombre()))
                    .append("</strong> — Curso: ")
                    .append(escapeHtml(alumno.getCurso()))
                    .append("</li>\n");
        }

        StringBuilder html = new StringBuilder("<!doctype html>\n" +
                                               "<html lang=\"es\">\n" +
                                               "<head><meta charset=\"UTF-8\"><title>Lista de alumnos</title></head>\n" +
                                               "<body>\n" +
                                               "<h1>Lista de alumnos</h1>\n" +
                                               "<ul>\n");
        html.append(listaAlumnos)
                .append("</ul><p><a href=\"index.html\">Volver</a></p></body></html>");
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

        private Alumno(String nombre, String curso) {
            this.nombre = nombre;
            this.curso = curso;
        }

        private String getNombre() { return nombre; }
        private String getCurso() { return curso; }
    }

}
