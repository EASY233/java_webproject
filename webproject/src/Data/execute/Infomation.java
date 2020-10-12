package Data.execute;

import Data.Config;
import Data.Sqlapi;
import Data.field.GetField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Infomation implements Sqlapi{
    @Override
    public GetField select(String username) throws SQLException {
        GetField field = null;
        try(Connection conn = Config.getConnection().getConnection()){
           String sql = "select * from users where user = ?";
           try(PreparedStatement ps = conn.prepareStatement(sql)){
               ps.setObject(1,username);
               try(ResultSet result = ps.executeQuery()){
                   while(result.next()){
                       field = new GetField(result.getString("user"),result.getString("password"),result.getString("avatar"));
                   }
               }
           }
        }
        return field;
    }
    @Override
    public List<GetField> showall() throws SQLException{
        List<GetField> users = new ArrayList<>();
        try(Connection conn = Config.getConnection().getConnection()){
            String sql  = "select * from users";
            try(PreparedStatement ps = conn.prepareStatement(sql)){
                try(ResultSet result = ps.executeQuery()){
                    while(result.next()){
                        users.add(new GetField(result.getString("user"), result.getString("password"),result.getString("avatar")));
                    }
                }
            }
        }
        return users;
    }
}
