import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;
import org.postgis.*;
import org.postgresql.PGConnection;

@SuppressWarnings("unused")
public class TestGis {
//这段代码--利用JDBC连接postgresql数据库、实施查询操作。
	public static void main(String[] args) {
		java.sql.Connection conn;
		
		try {//conn的实际类型是存在的（实现PGConnection、connection），是厂商自己注册到jvm里的，可通过conn.getClass()获取
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://localhost:5432/test";
			conn = DriverManager.getConnection(url, "postgres", "lilei");			
			((org.postgresql.PGConnection)conn).addDataType("geometry", Class.forName("org.postgis.PGgeometry"));
			((org.postgresql.PGConnection)conn).addDataType("box3d", Class.forName("org.postgis.PGbox3d"));
			Statement statement = conn.createStatement();
			/*Method[] method = conn.getClass().getMethods();
			for(Method m:method) {
				System.out.println(m.toString());
			}*/			
			ResultSet r = statement.executeQuery("SELECT park_geom,park_id FROM parks");
			while (r.next()) {
				PGgeometry geom = (PGgeometry)r.getObject(1);
				int id = r.getInt(2);
				System.out.println("Row" + id + ":");
				System.out.println(geom.toString());
				
			}
			statement.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		

	}

}
