package com.platform.servlet;

import com.platform.dao.MaterialDAO;
import com.platform.model.Material;
import com.platform.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@WebServlet("/upload")
@MultipartConfig // Essential for file uploads
public class UploadServlet extends HttpServlet {

    // --- THIS IS THE DANGEROUS PART ---
    // This path is relative to your TOMCAT server, not your project.
    // We will save files to a folder named "uploads" *next to* your project folder.
    // On Render, this path is temporary and will be erased.
    private static final String UPLOAD_DIR = "uploads";
    // --- END DANGEROUS PART ---

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
        request.getRequestDispatcher("jsp/upload.jsp").forward(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if user is logged in
        if (request.getSession(false) == null || request.getSession(false).getAttribute("user") == null) {
            response.sendRedirect("jsp/login.jsp");
            return;
        }
        
        User user = (User) request.getSession().getAttribute("user");

        // Get form fields
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        Part filePart = request.getPart("file");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // Sanitize filename

        // Get the absolute path of the web application
        String applicationPath = request.getServletContext().getRealPath("");
        // Construct the path to the upload directory
        String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;
        
        // Create the upload directory if it doesn't exist
        File uploadDir = new File(uploadFilePath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // Create the full path for the file
        String savePath = uploadFilePath + File.separator + fileName;
        File fileToSave = new File(savePath);

        try {
            // Save the file to the server
            filePart.write(savePath);

            // Save metadata to the database
            Material material = new Material();
            material.setTitle(title);
            material.setDescription(description);
            material.setFileName(fileName);
            material.setFilePath(savePath); // Store the *absolute* path
            material.setUploaderId(user.getUserId());

            if (materialDAO.addMaterial(material)) {
                // Success
                response.sendRedirect("home");
            } else {
                // DB Error
                request.setAttribute("error", "Database error: Could not save file metadata.");
                request.getRequestDispatcher("jsp/upload.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "File upload failed: " + e.getMessage());
            request.getRequestDispatcher("jsp/upload.jsp").forward(request, response);
        }
    }
}