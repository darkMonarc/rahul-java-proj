package com.platform.servlet;

import com.platform.dao.MaterialDAO;
import com.platform.model.Material;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    private MaterialDAO materialDAO;

    public void init() {
        materialDAO = new MaterialDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if user is logged in
        if (request.getSession(false) == null || request.getSession(false).getAttribute("user") == null) {
            response.sendRedirect("jsp/login.jsp");
            return;
        }

        // Fetch all materials
        List<Material> materials = materialDAO.getAllMaterials();
        request.setAttribute("materials", materials);
        
        // Forward to the home JSP
        request.getRequestDispatcher("jsp/home.jsp").forward(request, response);
    }
}