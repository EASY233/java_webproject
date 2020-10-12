package login;

import Data.Sqlapi;
import Data.execute.Infomation;
import Data.field.GetField;

import java.sql.SQLException;

public class Login implements  Loginapi{
     @Override
     public GetField login(String username,String password) throws SQLException{
          Sqlapi sql  = new Infomation();
          GetField result = sql.select(username);
          if(result!=null){
               if(result.getPassword().equals(password)){
                    return result;
               }
          }
          else{
               return null;
          }
          return null;
     }
}

