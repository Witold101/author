package com.cameco.dao.dto;

import com.cameco.db.DbConstants;
import com.cameco.db.InitTextStatement;

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

}
