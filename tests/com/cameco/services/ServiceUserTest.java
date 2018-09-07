package com.cameco.services;


import com.cameco.entity.PackageSoft;
import com.cameco.entity.User;
import org.junit.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class ServiceUserTest {

    Connection connection;

    @Before
    public void initTest() {
        connection = ServiceMainTest.getConnection();
    }

    @Test
    public void add() {
        new ServiceTablesInitDrop(connection).initTable("");
        PackageSoft packageSoft = new PackageSoft();
        packageSoft.setKey(8);
        packageSoft.setName(" Store     ");
        packageSoft.setInfo(" Пакет включает в себя управление складом. Существует только количественный учет. ");
        new ServicePackageSoft(connection).add(packageSoft);

        User user = new User();
        user.setLogin("Login33");
        user.setPassword("Пароль");
        user.setDateActivation(new Date(100, 0, 21));
        user.setDateReg(new Date(110, 2, 3));
        user.setRole(22);
        user.setE_mail("info@101.by");
        user.setName("Виталий ");
        user.setFullName("Wasilus");
        user.setPackage_id(packageSoft.getId());
        new ServiceUser(connection).add(user);

        User userTest = new ServiceUser(connection).get(user.getId());
        assertEquals(user.getLogin(), userTest.getLogin());
        assertEquals(user.getPassword(),userTest.getPassword());
        assertEquals(user.getDateActivation(), userTest.getDateActivation());
        assertEquals(user.getDateReg(), userTest.getDateReg());
        assertEquals(user.getRole(), userTest.getRole());
        assertEquals(user.getId(), userTest.getId());
        assertEquals(user.getPrefix(), userTest.getPrefix());
        assertEquals(user.getE_mail(), userTest.getE_mail());
        assertEquals(user.getName(), userTest.getName());
        assertEquals(user.getFullName(), userTest.getFullName());
        assertEquals(user.getPackage_id(), userTest.getPackage_id());

        assertNull(new ServiceUser(connection).add(user));

        new ServiceTablesInitDrop(connection).dropTable("");
    }

    @Test
    public void edit() {
        new ServiceTablesInitDrop(connection).initTable("");

        PackageSoft packageSoft = new PackageSoft();
        packageSoft.setKey(8);
        packageSoft.setName(" Store     ");
        packageSoft.setInfo(" Пакет включает в себя управление складом. Существует только количественный учет. ");
        new ServicePackageSoft(connection).add(packageSoft);

        User user = new User();
        user.setLogin("Login");
        user.setPassword("Пароль");
        user.setDateActivation(new Date(100, 0, 21));
        user.setDateReg(new Date(110, 2, 3));
        user.setRole(22);
        user.setE_mail("info@101.by");
        user.setName("Виталий ");
        user.setFullName("Wasilus");
        user.setPackage_id(packageSoft.getId());
        new ServiceUser(connection).add(user);

        User userTest = new User();
        userTest.setPassword("Пароль Test");
        userTest.setDateActivation(new Date(110, 2, 3));
        userTest.setRole(22);
        userTest.setE_mail("info@101.by Test");
        userTest.setName("Виталий Test ");
        userTest.setFullName("Wasilus Test");
        userTest.setId(user.getId());
        userTest.setPackage_id(user.getPackage_id());
        user = new ServiceUser(connection).edit(userTest);

        assertEquals(user.getLogin(), userTest.getLogin());
        assertEquals(user.getPassword(),userTest.getPassword());
        assertEquals(user.getDateActivation(), userTest.getDateActivation());
        assertEquals(user.getDateReg(), userTest.getDateReg());
        assertEquals(user.getRole(), userTest.getRole());
        assertEquals(user.getId(), userTest.getId());
        assertEquals(user.getPrefix(), userTest.getPrefix());
        assertEquals(user.getE_mail(), userTest.getE_mail());
        assertEquals(user.getName(), userTest.getName());
        assertEquals(user.getFullName(), userTest.getFullName());
        assertEquals(user.getPackage_id(), userTest.getPackage_id());

        new ServiceTablesInitDrop(connection).dropTable("");
    }

    @Test
    public void get() {
        add();
    }

    @Test
    public void dell() {
        new ServiceTablesInitDrop(connection).initTable("");

        initTest();
        PackageSoft packageSoft = new PackageSoft();
        packageSoft.setKey(8);
        packageSoft.setName(" Store     ");
        packageSoft.setInfo(" Пакет включает в себя управление складом. Существует только количественный учет. ");
        new ServicePackageSoft(connection).add(packageSoft);

        User user = new User();
        user.setLogin("Login");
        user.setPassword("Пароль");
        user.setDateActivation(new Date(100, 0, 21));
        user.setDateReg(new Date(110, 2, 3));
        user.setRole(22);
        user.setE_mail("info@101.by");
        user.setName("Виталий ");
        user.setFullName("Wasilus");
        user.setPackage_id(packageSoft.getId());
        new ServiceUser(connection).add(user);
        new ServiceUser(connection).dell(user.getId());
        assertNull(new ServiceUser(connection).get(user.getId()));
        new ServiceTablesInitDrop(connection).dropTable("");
    }


    public void closeTests() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}