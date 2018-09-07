package com.cameco.services;

import com.cameco.entity.PackageSoft;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class ServicePackageSoftTest {

    static Connection connection;


    @AfterClass
    public static void connectionClose(){
        //new ServiceTablesInitDrop(connection).dropTable(prefix);
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @BeforeClass
    public static void initTest() {
        connection = ServiceMainTest.getConnection();
        new ServiceTablesInitDrop(connection).initTable("");
    }

    @Test
    public void add() {
        PackageSoft packageSoft = new PackageSoft();
        packageSoft.setKey(8);
        packageSoft.setName(" Store     ");
        packageSoft.setInfo(" Пакет включает в себя управление складом. Существует только количественный учет. ");
        new ServicePackageSoft(connection).add(packageSoft);
        PackageSoft packageSoftTest = new ServicePackageSoft(connection).get(packageSoft.getId());
        assertEquals(packageSoft.getInfo(),packageSoftTest.getInfo());
        assertEquals(packageSoft.getName(),packageSoftTest.getName());
        assertEquals(packageSoft.getKey(),packageSoftTest.getKey());
        assertEquals(packageSoft.getId(),packageSoftTest.getId());
    }

    @Test
    public void dell() {
        PackageSoft packageSoft = new PackageSoft();
        packageSoft.setKey(8);
        packageSoft.setName(" Store     ");
        packageSoft.setInfo(" Пакет включает в себя управление складом. Существует только количественный учет. ");
        new ServicePackageSoft(connection).add(packageSoft);
        new ServicePackageSoft(connection).dell(packageSoft.getId());
        assertNull(new ServicePackageSoft(connection).get(packageSoft.getId()));
    }

    @Test
    public void edit() {
        PackageSoft packageSoft = new PackageSoft();
        packageSoft.setKey(8);
        packageSoft.setName(" Store     ");
        packageSoft.setInfo(" Пакет включает в себя управление складом. Существует только количественный учет. ");
        new ServicePackageSoft(connection).add(packageSoft);
        packageSoft.setKey(90);
        packageSoft.setName(" Store  Test   ");
        packageSoft.setInfo(" Пакет включает в себя управление складом. Существует только количественный учет.  Test ");
        PackageSoft packageSoftTest = new ServicePackageSoft(connection).edit(packageSoft);
        assertEquals(packageSoft.getInfo(),packageSoftTest.getInfo());
        assertEquals(packageSoft.getName(),packageSoftTest.getName());
        assertEquals(packageSoft.getKey(),packageSoftTest.getKey());
        assertEquals(packageSoft.getId(),packageSoftTest.getId());
    }

    @Test
    public void get() {
        add();
    }
}