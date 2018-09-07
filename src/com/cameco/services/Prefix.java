package com.cameco.services;


import com.cameco.dao.dto.DtoUser;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Класс генерирующий префикс для записи в таблицу
 */
public class Prefix {

    public static String getPrefix(String login, Connection connection) throws SQLException {
        String perfLogin = login;
        DtoUser dtoUser = DtoUser.getInstance();
        dtoUser.initPrepareStatement(connection);
        String prefix;
        int i=1;
        do {
            prefix = DigestUtils.md5Hex(perfLogin);
            perfLogin = perfLogin + i;
            i++;
        }
        while (dtoUser.isPrefix(prefix));
        dtoUser.closePrepareStatement();
        return prefix;
    }
}
