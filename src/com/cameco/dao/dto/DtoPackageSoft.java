package com.cameco.dao.dto;

import com.cameco.db.InitTextStatement;
import com.cameco.entity.PackageSoft;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DtoPackageSoft {

    private Map<String, PreparedStatement> mysqlPrepareStatement = null;

    private static volatile DtoPackageSoft INSTANCE = null;

    private DtoPackageSoft() {
    }

    public static DtoPackageSoft getInstance() {
        DtoPackageSoft dtoPackageSoft = INSTANCE;
        if (dtoPackageSoft == null) {
            synchronized (DtoPackageSoft.class) {
                dtoPackageSoft = INSTANCE;
                if (dtoPackageSoft == null) {
                    INSTANCE = dtoPackageSoft = new DtoPackageSoft();
                }
            }
        }
        return dtoPackageSoft;
    }

    public void initPrepareStatement(Connection connection) throws SQLException {
        if (mysqlPrepareStatement != null) {
            mysqlPrepareStatement.clear();
        } else {
            mysqlPrepareStatement = new HashMap<>();
        }
        InitTextStatement statement = new InitTextStatement();
        mysqlPrepareStatement.put("getPackageFoKey", connection.prepareStatement(statement.getMysqlPackageGetForKey()));
    }

    public void closePrepareStatement() throws SQLException {
        for (PreparedStatement ps : mysqlPrepareStatement.values()) {
            ps.close();
        }
    }

    public PackageSoft getPackageFoKey(Integer key) throws SQLException {
        PreparedStatement pst = mysqlPrepareStatement.get("getPackageFoKey");
        pst.setInt(1,key);
        ResultSet rs = pst.executeQuery();
        PackageSoft pac = null;
        if (rs.next()) {
            pac = new PackageSoft();
            pac.setId(rs.getLong("id"));
            pac.setName(rs.getString("name"));
            pac.setInfo(rs.getString("info"));
            pac.setKey(rs.getInt("key"));
        }
        return pac;
    }
}
