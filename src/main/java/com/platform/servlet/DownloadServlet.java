package com.platform.servlet;

import com.platform.dao.MaterialDAO;
import com.platform.model.Material;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/download")
public class DownloadServlet extends HttpServlet {
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

        int materialId = Integer.parseInt(request.getParameter("id"));
        Material material = materialDAO.getMaterialById(materialId);

        if (material == null) {
            response.getWriter().println("File not found.");
            return;
        }

        // Get the file from the path stored in the DB
        File downloadFile = new File(material.getFilePath());
        if (!downloadFile.exists()) {
            response.getWriter().println("File not found on server. It may have been deleted.");
            return;
        }

        // Set up the response
        ServletContext context = getServletContext();
        String mimeType = context.getMimeType(material.getFilePath());
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }

        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());

        // Set the "Content-Disposition" header to force download
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", material.getFileName());
        response.setHeader(headerKey, headerValue);

        // Read the file and write it to the response output stream
        try (FileInputStream inStream = new FileInputStream(downloadFile);
             OutputStream outStream = response.getOutputStream()) {

            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
        }
    }
}