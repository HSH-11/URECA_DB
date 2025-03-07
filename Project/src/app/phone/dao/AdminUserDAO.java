package app.phone.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import app.phone.common.DBManager;
import app.phone.dto.AdminUserDTO;

public class AdminUserDAO {

    public AdminUserDTO login(String adminId, String password) {
        String sql = "SELECT * FROM AdminUsers WHERE admin_id = ? AND password = ?";

        try (Connection conn = DBManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, adminId);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new AdminUserDTO(
                    rs.getString("admin_id"),
                    rs.getString("password"),
                    rs.getString("name")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

