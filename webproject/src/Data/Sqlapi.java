package Data;

import Data.field.GetField;

import java.sql.SQLException;
import java.util.List;

public interface Sqlapi {
    public GetField select(String username) throws SQLException;
    public List<GetField> showall() throws SQLException;
}
