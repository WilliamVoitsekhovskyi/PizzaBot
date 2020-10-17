//import org.springframework.jdbc.core.RowMapper;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//public class MenuItemRowMapper implements RowMapper<com.example.springboot.MenuItem> {
//    @Override
//    public com.example.springboot.MenuItem mapRow(ResultSet rs, int rowNum) throws SQLException {
//        com.example.springboot.MenuItem menuItem = new com.example.springboot.MenuItem();
//
//        menuItem.setName(rs.getString("name"));
//        menuItem.setPrice(rs.getInt("price"));
//        menuItem.setType(rs.getString("type"));
//        return menuItem;
//    }
//}