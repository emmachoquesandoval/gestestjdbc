package com.gestestjdbc.servlets;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Comparator;


@WebServlet("/controlador")
public class AppController extends HttpServlet {
    private final Map<Integer, Alumno> alumnos = new HashMap<>();

    @Override
    public void init() throws ServletException {
        super.init();
        alumnos.put(1, new Alumno(1, "Java", "Lucía García"));
        alumnos.put(2, new Alumno(2, "Python", "Mateo López"));
        alumnos.put(3, new Alumno(3, "PHP", "Sofía Martín"));
        alumnos.put(4, new Alumno(4, "Java", "Hugo Sánchez"));
        alumnos.put(5, new Alumno(5, "Python", "Valeria Ruiz"));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        StringBuilder listaAlumnos = new StringBuilder();

        for (Alumno alumno : alumnos.values().stream()
                .sorted(Comparator.comparingInt(Alumno::getId)).toList()) {
            request.setAttribute("userName", alumno.getNombre());
            request.setAttribute("userCourse", alumno.getCurso());

            String clientNom = request.getAttribute("userName").toString();
            String clientCurs = request.getAttribute("userCourse").toString();

            String HOME = ("<li>\n" +
                           "    <strong>%s</strong> — Curso: %s\n" +
                           "</li>\n").formatted(clientNom, clientCurs);

            listaAlumnos.append(HOME);
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

    private static class Alumno {
        private final int id;
        private final String curso;
        private final String nombre;

        private Alumno(int id, String curso, String nombre) {
            this.id = id;
            this.curso = curso;
            this.nombre = nombre;
        }

        private int getId() { return id; }
        private String getCurso() { return curso; }
        private String getNombre() { return nombre; }
    }

}
