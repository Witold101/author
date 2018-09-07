package com.cameco.dao;

import by.vistar.comeco.interfaces.DAO;
import com.cameco.db.InitTextStatement;
import com.cameco.entity.PackageSoft;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import static com.cameco.db.DbConstants.*;

public class DaoPackageSoft implements DAO<Long,PackageSoft> {

    private Map<String, PreparedStatement> mysqlPrepareStatement;

    private static volatile DaoPackageSoft INSTANCE = null;

    private DaoPackageSoft() {
    }

    public static DaoPackageSoft getInstance() {
        DaoPackageSoft daoPackage = INSTANCE;
        if (daoPackage == null) {
            synchronized (DaoPackageSoft.class) {
                daoPackage = INSTANCE;
                if (daoPackage == null) {
                    INSTANCE = daoPackage = new DaoPackageSoft();
                }
            }
        }
        return daoPackage;
    }

    public void initPrepareStatement(Connection connection) throws SQLException {
        if (mysqlPrepareStatement == null) {
            InitTextStatement statement = new InitTextStatement();
            mysqlPrepareStatement = new HashMap<>();
            mysqlPrepareStatement.put("addPackage", connection.prepareStatement(statement.getMysqlPackageAdd(), Statement.RETURN_GENERATED_KEYS));
            mysqlPrepareStatement.put("getPackage", connection.prepareStatement(statement.getMysqlPackageGet()));
            mysqlPrepareStatement.put("editPackage", connection.prepareStatement(statement.getMysqlPackageEdit()));
            mysqlPrepareStatement.put("dellPackage", connection.prepareStatement(statement.getMysqlPackageDell()));
        }
    }

    @Override
    public PackageSoft add(PackageSoft pac) throws SQLException {
        PreparedStatement pst = mysqlPrepareStatement.get("addPackage");
        pst.setInt(1, pac.getKey());
        pst.setString(2, pac.getName());
        pst.setString(3, pac.getInfo());
        pst.executeUpdate();
        ResultSet rs = pst.getGeneratedKeys();
        if (rs.next()) {
            pac.setId(rs.getLong(1));
        }
        rs.close();
        return pac;
    }

    @Override
    public void dell(Long id) throws SQLException {
        PreparedStatement pst = mysqlPrepareStatement.get("dellPackage");
        pst.setLong(1, id);
        pst.executeUpdate();
    }

    @Override
    public PackageSoft edit(PackageSoft pac) throws SQLException {
        PreparedStatement pst = mysqlPrepareStatement.get("editPackage");
        pst.setInt(1, pac.getKey());
        pst.setString(2,pac.getName());
        pst.setString(3, pac.getInfo());
        pst.setLong(4,pac.getId());
        if (pst.executeUpdate() > 0) {
            return pac;
        } else {
            return null;
        }
    }

    @Override
    public PackageSoft get(Long id) throws SQLException {
        PackageSoft pac = null;
        PreparedStatement pst = mysqlPrepareStatement.get("getPackage");
        pst.setLong(1, id);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            pac = new PackageSoft();
            pac.setId(rs.getLong("id"));
            pac.setName(rs.getString("name"));
            pac.setInfo(rs.getString("info"));
            pac.setKey(rs.getInt("key"));
        }
        rs.close();
        return pac;
    }
}
