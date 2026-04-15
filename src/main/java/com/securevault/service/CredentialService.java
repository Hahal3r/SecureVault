package com.securevault.service;

import com.securevault.db.DBUtil;
import com.securevault.model.Credential;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CredentialService {

    public List<Credential> getAllCredentials() {
        List<Credential> list = new ArrayList<>();
        String sql = "SELECT * FROM credentials ORDER BY id DESC";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Credential credential = new Credential(
                        rs.getInt("id"),
                        rs.getString("website"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("category"),
                        rs.getString("notes")
                );
                list.add(credential);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Credential> searchCredentials(String keyword) {
        List<Credential> list = new ArrayList<>();
        String sql = "SELECT * FROM credentials WHERE website LIKE ? OR username LIKE ? ORDER BY id DESC";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String pattern = "%" + keyword + "%";
            ps.setString(1, pattern);
            ps.setString(2, pattern);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Credential credential = new Credential(
                            rs.getInt("id"),
                            rs.getString("website"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("category"),
                            rs.getString("notes")
                    );
                    list.add(credential);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean addCredential(Credential credential) {
        String sql = "INSERT INTO credentials (website, username, password, category, notes) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, credential.getWebsite());
            ps.setString(2, credential.getUsername());
            ps.setString(3, credential.getPassword());
            ps.setString(4, credential.getCategory());
            ps.setString(5, credential.getNotes());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateCredential(Credential credential) {
        String sql = "UPDATE credentials SET website=?, username=?, password=?, category=?, notes=? WHERE id=?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, credential.getWebsite());
            ps.setString(2, credential.getUsername());
            ps.setString(3, credential.getPassword());
            ps.setString(4, credential.getCategory());
            ps.setString(5, credential.getNotes());
            ps.setInt(6, credential.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteCredential(int id) {
        String sql = "DELETE FROM credentials WHERE id=?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
