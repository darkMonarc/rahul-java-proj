package com.platform.dao;

import com.platform.model.Material;
import com.platform.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterialDAO {

    // Add a new material record to the DB
    public boolean addMaterial(Material material) {
        String sql = "INSERT INTO materials (title, description, file_name, file_path, uploader_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, material.getTitle());
            stmt.setString(2, material.getDescription());
            stmt.setString(3, material.getFileName());
            stmt.setString(4, material.getFilePath());
            stmt.setInt(5, material.getUploaderId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get all materials
    public List<Material> getAllMaterials() {
        List<Material> materials = new ArrayList<>();
        // Join with users table to get the uploader's name
        String sql = "SELECT m.*, u.username FROM materials m JOIN users u ON m.uploader_id = u.user_id ORDER BY m.upload_date DESC";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Material material = new Material();
                material.setMaterialId(rs.getInt("material_id"));
                material.setTitle(rs.getString("title"));
                material.setDescription(rs.getString("description"));
                material.setFileName(rs.getString("file_name"));
                material.setFilePath(rs.getString("file_path"));
                material.setUploadDate(rs.getTimestamp("upload_date"));
                material.setUploaderId(rs.getInt("uploader_id"));
                material.setUploaderUsername(rs.getString("username")); // Get from join
                materials.add(material);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return materials;
    }

    // Get a single material by its ID (for downloading)
    public Material getMaterialById(int materialId) {
        String sql = "SELECT * FROM materials WHERE material_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, materialId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Material material = new Material();
                material.setMaterialId(rs.getInt("material_id"));
                material.setTitle(rs.getString("title"));
                material.setDescription(rs.getString("description"));
                material.setFileName(rs.getString("file_name"));
                material.setFilePath(rs.getString("file_path"));
                material.setUploadDate(rs.getTimestamp("upload_date"));
                material.setUploaderId(rs.getInt("uploader_id"));
                return material;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}