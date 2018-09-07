package com.cameco.db;

import static com.cameco.db.DbConstants.*;

/**
 * Класс инициализирующий PrepareStatement согласно загруженного префикса.
 */

public class InitTextStatement {

    private final String TABLE_NAME = "users";
    private final String TABLE_PACKAGE = "package";

    private String usersTable;
    private String packageTable;

    private String mysqlInitUserTable;
    private String mysqlInitPackageTable;

    private String mysqlDropUserTable;
    private String mysqlDropPackageTable;

    private String mysqlPackageAdd;
    private String mysqlPackageDell;
    private String mysqlPackageEdit;
    private String mysqlPackageGet;

    private String mysqlUserAdd;
    private String mysqlUserDell;
    private String mysqlUserEdit;
    private String mysqlUserGet;
    private String mysqlUserIsLogin;
    private String mysqlUserIsPrefix;


    public InitTextStatement() {
        this.usersTable = TABLE_NAME;
        this.packageTable = TABLE_PACKAGE;
    }

    public String getUsersTable() {
        return usersTable;
    }

    public String getPackageTable() {
        return packageTable;
    }

    public String getMysqlPackageAdd() {
        this.mysqlPackageAdd = "INSERT INTO " + getPackageTable() + "(`key`, `name`, `info`) VALUE (?,?,?);";
        return mysqlPackageAdd;
    }

    public String getMysqlPackageDell() {
        this.mysqlPackageDell = "DELETE FROM " + getPackageTable() + " where id=?;";
        return mysqlPackageDell;
    }

    public String getMysqlPackageEdit() {
        this.mysqlPackageEdit = "UPDATE " + getPackageTable() + " SET `key`=?, name=?, info=? WHERE id=?;";
        return mysqlPackageEdit;
    }

    public String getMysqlPackageGet() {
        this.mysqlPackageGet = "SELECT * FROM " + getPackageTable() + " WHERE id=?;";
        return mysqlPackageGet;
    }

    public String getMysqlUserAdd() {
        this.mysqlUserAdd = "INSERT INTO " + getUsersTable() + "(`login`, `password`, `role`, `date_reg`, `date_activ`," +
                " `prefix`, `e_mail`, `name`, `full_name`, `package_id`) VALUE (?,?,?,?,?,?,?,?,?,?);";
        return mysqlUserAdd;
    }

    public String getMysqlUserDell() {
        this.mysqlUserDell = "DELETE FROM " + getUsersTable() + " where id=?;";
        return mysqlUserDell;
    }

    public String getMysqlUserEdit() {
        this.mysqlUserEdit = "UPDATE " + getUsersTable() + " SET " +
                "password=?, role=?, date_activ=?, e_mail=?, name=?, full_name=?, package_id=?  WHERE id=?;";
        return mysqlUserEdit;
    }

    public String getMysqlUserGet() {
        this.mysqlUserGet = "SELECT * FROM " + getUsersTable() + " WHERE id=?;";
        return mysqlUserGet;
    }

    public String getMysqlUserIsLogin() {
        this.mysqlUserIsLogin = "SELECT 1 from " + getUsersTable() + " where login=? limit 1;";
        return mysqlUserIsLogin;
    }

    public String getMysqlUserIsPrefix() {
        this.mysqlUserIsPrefix = "SELECT 1 from " + getUsersTable() + " where prefix=? limit 1;";
        return mysqlUserIsPrefix;
    }


    public String getMysqlInitUserTable() {
        this.mysqlInitUserTable = "CREATE TABLE IF NOT EXISTS `" + getUsersTable() + "` (`id` INT(11) NOT NULL AUTO_INCREMENT," +
                "            `login` VARCHAR(" + MAX_LENGTH_LOGIN + ") NOT NULL," +
                "            `password` VARCHAR(" + MAX_LENGTH_PASSWORD + ") NOT NULL," +
                "            `role` INT(11) NOT NULL," +
                "            `date_reg` DATE NOT NULL," +
                "            `date_activ` VARCHAR(45) NULL DEFAULT NULL," +
                "            `prefix` VARCHAR(" + MAX_LENGTH_PREFIX + ") NOT NULL," +
                "            `e_mail` VARCHAR(" + MAX_LENGTH_EMAIL + ") NOT NULL," +
                "            `name` VARCHAR(" + MAX_LENGTH_NAME + ") NOT NULL," +
                "            `full_name` VARCHAR(" + MAX_LENGTH_FULL_NAME + ") NULL," +
                "            `package_id` INT(11) NOT NULL," +
                "             PRIMARY KEY (`id`), INDEX `fk_package_idx` (`package_id` ASC)," +
                "             CONSTRAINT `fk_package` FOREIGN KEY (`package_id`)" +
                "             REFERENCES `" + getPackageTable() + "` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION)" +
                "             ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;";
        return mysqlInitUserTable;
    }

    public String getMysqlInitPackageTable() {
        this.mysqlInitPackageTable ="CREATE TABLE IF NOT EXISTS `" + getPackageTable() + "` (" +
                " `id` INT(11) NOT NULL AUTO_INCREMENT, `key` INT(11) NOT NULL,`name` VARCHAR(" + MAX_LENGTH_NAME + ") NOT NULL," +
                " `info` VARCHAR(" + MAX_LENGTH_INFO + ") NULL DEFAULT NULL, PRIMARY KEY (`id`))" +
                " ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;";
        return mysqlInitPackageTable;
    }

    public String getMysqlDropUserTable() {
        this.mysqlDropUserTable = "DROP TABLE `" + getUsersTable() + "`;";
        return mysqlDropUserTable;
    }

    public String getMysqlDropPackageTable() {
        this.mysqlDropPackageTable = "DROP TABLE `" + getPackageTable() + "`;";
        return mysqlDropPackageTable;
    }
}
