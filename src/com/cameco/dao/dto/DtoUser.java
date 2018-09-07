package com.cameco.dao.dto;

import com.cameco.db.DbConstants;
import com.cameco.db.InitTextStatement;
import com.cameco.entity.User;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DtoUser {
    private Map<String, PreparedStatement> mysqlPrepareStatement = null;

    private static volatile DtoUser INSTANCE = null;

    private DtoUser() {
    }

    public static DtoUser getInstance() {
        DtoUser dtoUser = INSTANCE;
        if (dtoUser == null) {
            synchronized (DtoUser.class) {
                dtoUser = INSTANCE;
                if (dtoUser == null) {
                    INSTANCE = dtoUser = new DtoUser();
                }
            }
        }
        return dtoUser;
    }

    public void initPrepareStatement(Connection connection) throws SQLException {
        if (mysqlPrepareStatement != null) {
            mysqlPrepareStatement.clear();
        } else {
            mysqlPrepareStatement = new HashMap<>();
        }
        InitTextStatement statement = new InitTextStatement();
        mysqlPrepareStatement.put("isLogin", connection.prepareStatement(statement.getMysqlUserIsLogin()));
        mysqlPrepareStatement.put("isPrefix", connection.prepareStatement(statement.getMysqlUserIsPrefix()));
        mysqlPrepareStatement.put("getUserFromLoginAndPassword",
                connection.prepareStatement(statement.getMysqlUserGetForLoginAndPassword()));
    }

    public void closePrepareStatement() throws SQLException {
        for (PreparedStatement ps : mysqlPrepareStatement.values()) {
            ps.close();
        }
    }

    public boolean isLogin(String login) throws SQLException {
        PreparedStatement pst = mysqlPrepareStatement.get("isLogin");
        pst.setString(1,login);
        ResultSet rs = pst.executeQuery();
        return rs.next();
    }

    public boolean isPrefix(String prefix) throws SQLException {
        PreparedStatement pst = mysqlPrepareStatement.get("isPrefix");
        pst.setString(1,prefix);
        ResultSet rs = pst.executeQuery();
        return rs.next();
    }

    public User getUserFromLoginAndPassword(String login, String password) throws SQLException {
        PreparedStatement pst = mysqlPrepareStatement.get("getUserFromLoginAndPassword");
        pst.setString(1,login);
        pst.setString(2, DigestUtils.md5Hex(password));
        ResultSet rs = pst.executeQuery();
        User user = null;
        if (rs.next()) {
            user = new User();
            user.setId(rs.getLong("id"));
            user.setRole(rs.getInt("role"));
            user.setDateReg(rs.getDate("date_reg"));
            user.setDateActivation(rs.getDate("date_activ"));
            user.setPrefix(rs.getString("prefix"));
            user.setE_mail(rs.getString("e_mail"));
            user.setName(rs.getString("name"));
            user.setFullName(rs.getString("full_name"));
            user.setPackage_id(rs.getLong("package_id"));
        }
        return user;
    }

}
