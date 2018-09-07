package com.cameco.services;

import by.vistar.comeco.interfaces.InitDropTables;
import com.cameco.db.DbConstants;
import com.cameco.db.InitTextStatement;

import java.sql.Connection;
import java.sql.SQLException;

public class ServiceTablesInitDrop implements InitDropTables {

    Connection connection;

    public ServiceTablesInitDrop(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void dropTable(String prefix) {
        try {
            InitTextStatement statement = new InitTextStatement();
            connection.prepareStatement(statement.getMysqlDropUserTable()).execute();
            connection.prepareStatement(statement.getMysqlDropPackageTable()).execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initTable(String prefix) {
        try {
            InitTextStatement statement = new InitTextStatement();
            connection.prepareStatement(statement.getMysqlInitPackageTable()).execute();
            connection.prepareStatement(statement.getMysqlInitUserTable()).execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void startTransaction() {
        try {
            this.connection.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println("Error init setAutocommit = false");
            e.printStackTrace();
        }
    }

    public void commit() {
        try {
            this.connection.commit();
        } catch (SQLException e) {
            System.out.println("Error commit");
            e.printStackTrace();
        }
    }
}
