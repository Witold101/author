package com.cameco.dao;

import by.vistar.comeco.interfaces.DAO;
import com.cameco.db.InitTextStatement;
import com.cameco.entity.User;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import static com.cameco.db.DbConstants.*;

public class DaoUser implements DAO<Long, User> {

    private Map<String, PreparedStatement> mysqlPrepareStatement = null;

    private static volatile DaoUser INSTANCE = null;

    private DaoUser() {
    }

    public static DaoUser getInstance() {
        DaoUser daoUser = INSTANCE;
        if (daoUser == null) {
            synchronized (DaoUser.class) {
                daoUser = INSTANCE;
                if (daoUser == null) {
                    INSTANCE = daoUser = new DaoUser();
                }
            }
        }
        return daoUser;
    }

    public void initPrepareStatement(Connection connection) throws SQLException {
        if (mysqlPrepareStatement != null) {
            mysqlPrepareStatement.clear();
        } else {
            mysqlPrepareStatement = new HashMap<>();
        }
        InitTextStatement statement = new InitTextStatement();
        mysqlPrepareStatement.put("add", connection.prepareStatement(statement.getMysqlUserAdd(), Statement.RETURN_GENERATED_KEYS));
        mysqlPrepareStatement.put("dell", connection.prepareStatement(statement.getMysqlUserDell()));
        mysqlPrepareStatement.put("edit", connection.prepareStatement(statement.getMysqlUserEdit()));
        mysqlPrepareStatement.put("get", connection.prepareStatement(statement.getMysqlUserGet()));
    }

    public void closePrepareStatement() throws SQLException {
        for (PreparedStatement ps : mysqlPrepareStatement.values()) {
            ps.close();
        }
    }

    @Override
    public User add(User user) throws SQLException {
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        PreparedStatement pst = mysqlPrepareStatement.get("add");
        pst.setString(1, user.getLogin());
        pst.setString(2, user.getPassword());
        pst.setInt(3, user.getRole());
        pst.setDate(4, user.getDateReg());
        pst.setDate(5, user.getDateActivation());
        pst.setString(6, user.getPrefix());
        pst.setString(7, user.getE_mail());
        pst.setString(8, user.getName());
        pst.setString(9, user.getFullName());
        pst.setLong(10,user.getPackage_id());
        pst.executeUpdate();
        ResultSet rs = pst.getGeneratedKeys();
        if (rs.next()) {
            user.setId(rs.getLong(1));
        }
        rs.close();
        return user;
    }

    @Override
    public void dell(Long id) throws SQLException {
        PreparedStatement pst = mysqlPrepareStatement.get("dell");
        pst.setLong(1, id);
        pst.executeUpdate();
    }

    @Override
    public User edit(User user) throws SQLException {
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        PreparedStatement pst = mysqlPrepareStatement.get("edit");
        pst.setString(1, user.getPassword());
        pst.setInt(2, user.getRole());
        pst.setDate(3, user.getDateActivation());
        pst.setString(4, user.getE_mail());
        pst.setString(5, user.getName());
        pst.setString(6, user.getFullName());
        pst.setLong(7,user.getPackage_id());
        pst.setLong(8,user.getId());
        pst.executeUpdate();
        return user;
    }

    @Override
    public User get(Long id) throws SQLException {
        User user = null;
        PreparedStatement pst = mysqlPrepareStatement.get("get");
        pst.setLong(1, id);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            user = new User();
            user.setId(rs.getLong("id"));
            user.setLogin(rs.getString("login"));
            user.setPassword(rs.getString("password"));
            user.setRole(rs.getInt("role"));
            user.setDateReg(rs.getDate("date_reg"));
            user.setDateActivation(rs.getDate("date_activ"));
            user.setPrefix(rs.getString("prefix"));
            user.setE_mail(rs.getString("e_mail"));
            user.setName(rs.getString("name"));
            user.setFullName(rs.getString("full_name"));
            user.setPackage_id(rs.getLong("package_id"));
        }
        rs.close();
        return user;
    }

}

