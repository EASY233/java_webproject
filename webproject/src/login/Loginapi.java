package login;

import Data.field.GetField;

import java.sql.SQLException;

public interface Loginapi {
    public GetField login(String username,String password) throws SQLException;
}
