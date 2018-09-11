package com.cameco.services;

import by.vistar.comeco.interfaces.DaoService;
import by.vistar.comeco.interfaces.ServiceSetup;
import com.cameco.dao.DaoUser;
import com.cameco.db.DbConstants;
import com.cameco.dao.dto.DtoUser;
import com.cameco.db.InitTextStatement;
import com.cameco.entity.User;

import java.sql.Connection;
import java.sql.SQLException;

public class ServiceUser extends ServiceTablesInitDrop implements DaoService<Long,User>, ServiceSetup<User> {
    private Connection connection;
    private DaoUser daoUser;
    private DtoUser dtoUser;

    public ServiceUser(Connection connection) {
        super(connection);
        this.connection = connection;
        this.daoUser = DaoUser.getInstance();
        this.dtoUser = DtoUser.getInstance();
        try {
            this.daoUser.initPrepareStatement(connection);
            this.dtoUser.initPrepareStatement(connection);
        } catch (SQLException e) {
            System.out.println("Error initPrepareStatement");
            e.printStackTrace();
        }
    }

    private Boolean isLoginExist(User user){
        if (user!=null){
            try {
                return dtoUser.isLogin(user.getLogin());
            } catch (SQLException e) {
                System.out.println("Error Login SQL.");
                e.printStackTrace();
            }
        }
            return null;
    }

    private String getPrefix(String login) {
        try {
            return Prefix.getPrefix(login,connection);
        } catch (SQLException e) {
            System.out.println("Error PREFIX SQL.");
            e.printStackTrace();
        }
        return null;
    }

    public User add(User user) {
        if (user != null) {
            modificationLength(user);
            if (isLoginExist(user)){
                System.out.println("Such login exists.");
                return user= null;
        }else {
                user.setPrefix(getPrefix(user.getLogin()));
                startTransaction();
                try {
                    daoUser.add(user);
                } catch (SQLException e) {
                    System.out.println("Error add user in DB.");
                    e.printStackTrace();
                }
                commit();
            }
        }
        return user;
    }

    public void dell(Long id) {
        if (id != null) {
            startTransaction();
            try {
                daoUser.dell(id);
            } catch (SQLException e) {
                System.out.println("Error dell user from DB");
                e.printStackTrace();
            }
            commit();
        } else {
            System.out.println("Error id == null");
        }
    }

    public User edit(User user) {
        if (user != null){
            modificationLength(user);
            startTransaction();
            try {
                user = daoUser.edit(user);
            } catch (SQLException e) {
                System.out.println("Error edit user in DB");
                e.printStackTrace();
            }
            commit();
        }
        return user;
    }

    public User get(Long id){
        User user = null;
        if(id!=null){
            startTransaction();
            try {
                user = daoUser.get(id);
            } catch (SQLException e) {
                System.out.println("Error get user from DB");
                e.printStackTrace();
            }
            commit();
        }else {
            System.out.println("Error id == null");
        }
        return user;
    }

    public void closeConnectionAndPrepareStatement(){
        try {
            connection.close();
            daoUser.closePrepareStatement();
        } catch (SQLException e) {
            System.out.println("Error close connection end prepareStatement");
            e.printStackTrace();
        }
    }

    public User getUserFromLoginAndPassword(String login, String password){
        User user = null;
        if (login!=null && password!=null) {
            startTransaction();
            try {
                user = dtoUser.getUserFromLoginAndPassword(login, password);
            } catch (SQLException e) {
                System.out.println("Error getUserFromLoginAndPassword from DB");
                e.printStackTrace();
            }
            commit();
        }else {
            System.out.println("Error User or Login NULL.");
        }
        return user;
    }

    @Override
    public User modificationLength(User user) {
        if (user != null) {
            if(user.getLogin()==null){
                user.setLogin("");
            }
            if (user.getLogin().trim().length() > DbConstants.MAX_LENGTH_LOGIN) {
                user.setLogin(user.getLogin().trim().substring(0, DbConstants.MAX_LENGTH_LOGIN));
            } else {
                user.setLogin(user.getLogin().trim());
            }
            if (user.getPassword().trim().length() > DbConstants.MAX_LENGTH_PASSWORD) {
                user.setPassword(user.getPassword().trim().substring(0, DbConstants.MAX_LENGTH_PASSWORD));
            } else {
                user.setPassword(user.getPassword().trim());
            }
            if (user.getE_mail().trim().length() > DbConstants.MAX_LENGTH_EMAIL) {
                user.setE_mail(user.getE_mail().trim().substring(0, DbConstants.MAX_LENGTH_EMAIL));
            } else {
                user.setE_mail(user.getE_mail().trim());
            }
            if (user.getName()==null){
                user.setName("");
            }
            if (user.getName().trim().length() > DbConstants.MAX_LENGTH_NAME) {
                user.setName(user.getName().trim().substring(0, DbConstants.MAX_LENGTH_NAME));
            } else {
                user.setName(user.getName().trim());
            }
            if (user.getFullName()==null){
                user.setFullName("");
            }
            if (user.getFullName().trim().length() > DbConstants.MAX_LENGTH_FULL_NAME) {
                user.setFullName(user.getFullName().trim().substring(0, DbConstants.MAX_LENGTH_FULL_NAME));
            } else {
                user.setFullName(user.getFullName().trim());
            }
        }
        return user;
    }
}
