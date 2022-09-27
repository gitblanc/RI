import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.DataSources;

/**
 * 
 */

/**
 * @author UO285176
 *
 */
public class Session1 {

    /**
     * @param args
     */
    public static void main(String[] args) {
	// ex1();
	// ex2();
	ex3();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static void ex3() {
	Connection c = null;
	Statement st = null;

	String url = "jdbc:hsqldb:hsql://localhost:9001";
	String username = "sa";
	String passwd = "";
	String query = "select * from tmechanics";

	long t11 = 0;
	long t21 = 0;

	long t12 = 0;
	long t22 = 0;

	DataSource ds_unpooled;
	try {
	    // ya est� inicializado

	    // FORMA SIN POOL
	    t11 = System.currentTimeMillis();
	    for (int i = 0; i < 100; i++) {
		c = DriverManager.getConnection(url, username, passwd);
		st = c.createStatement();
		st.executeQuery(query);
		c.close();
	    }
	    t21 = System.currentTimeMillis();

	    System.out.println("Sin pool: " + (t21 - t11));

	    // FORMA CON POOL
	    t12 = System.currentTimeMillis();
	    // Creaci�n del pool de conexiones
	    ds_unpooled = DataSources.unpooledDataSource(url, username, passwd);
	    DataSource ds_pooled = null;

	    Map overrides = new HashMap();
	    overrides.put("minPoolSize", 3);
	    overrides.put("maxPoolSize", 50);
	    overrides.put("initialPoolSize", 3);
	    ds_pooled = DataSources.pooledDataSource(ds_unpooled, overrides);

	    for (int i = 0; i < 100; i++) {
		c = ds_pooled.getConnection();
		st = c.createStatement();
		st.executeQuery(query);
		c.close();
	    }
	    t22 = System.currentTimeMillis();

	    System.out.println("Con pool: " + (t22 - t12));

	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

    private static void ex2() {
	Connection c = null;
	Statement st = null;

	String url = "jdbc:hsqldb:hsql://localhost:9001";
	String username = "sa";
	String passwd = "";
	String query = "select * from tmechanics";

	long t11 = 0;
	long t21 = 0;

	long t12 = 0;
	long t22 = 0;

	try {
	    // 1
	    t11 = System.currentTimeMillis();
	    for (int i = 0; i < 100; i++) {
		c = DriverManager.getConnection(url, username, passwd);
		st = c.createStatement();
		st.execute(query);
		c.close();
	    }
	    t21 = System.currentTimeMillis();

	    // 2
	    t12 = System.currentTimeMillis();
	    c = DriverManager.getConnection(url, username, passwd);
	    for (int i = 0; i < 100; i++) {
		st = c.createStatement();
		st.execute(query);
	    }
	    c.close();
	    t22 = System.currentTimeMillis();

	    System.out.println("1: " + (t21 - t11));
	    System.out.println("2: " + (t22 - t12));

	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

    private static void ex1() {
	Connection c = null;
	Statement st = null;
	PreparedStatement pst = null;
	ResultSet rsSt = null;
	ResultSet rsPst = null;

	String url = "jdbc:hsqldb:hsql://localhost:9001";
	String username = "sa";
	String passwd = "";
	// tring iteration = "";
	String querySt = "select * from tmechanics";
	String queryPst = "select * from tmechanics";

	long t1st = 0;
	long t2st = 0;

	long t1pst = 0;
	long t2pst = 0;

	try {
	    // STATEMENT
	    t1st = System.currentTimeMillis();
	    c = DriverManager.getConnection(url, username, passwd);
	    st = c.createStatement();
	    for (int i = 0; i < 1000; i++) {
		// iteration = i + "%'";
		rsSt = st.executeQuery(querySt);// statement
	    }
	    st.close();
	    rsSt.close();
	    c.close();
	    t2st = System.currentTimeMillis();

	    System.out.println("Statement: " + (t2st - t1st));

	    // PREPARED STATEMENT
	    t1pst = System.currentTimeMillis();
	    c = DriverManager.getConnection(url, username, passwd);
	    pst = c.prepareStatement(queryPst);
	    for (int i = 0; i < 1000; i++) {
		rsPst = pst.executeQuery();// prepared statement
	    }
	    pst.close();
	    rsPst.close();
	    c.close();
	    t2pst = System.currentTimeMillis();

	    System.out.println("Prepared Statement: " + (t2pst - t1pst));

	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

}
