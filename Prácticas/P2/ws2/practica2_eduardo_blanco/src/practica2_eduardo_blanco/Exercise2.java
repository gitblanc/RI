package practica2_eduardo_blanco;

import java.sql.*;
import java.util.Scanner;

public class Exercise2 {

	private static String url = "jdbc:oracle:thin:@156.35.94.98:1521:desa19";
	private static String username = "UO285176";
	private static String passwd = "";

	public static void main(String[] args) {
		// Pide la contraseña
		Scanner teclado = new Scanner(System.in);
		System.out.println("Introduce tu contraseña: ");
		passwd = teclado.nextLine().trim();
		while (true) {
			try {
				Connection c = DriverManager.getConnection(url, username, passwd);
				Statement st = c.createStatement();
				String query = "SELECT * FROM Ttrips WHERE price BETWEEN 250 AND 350";
				ResultSet rs = st.executeQuery(query);
				while (rs.next()) {
					System.out.println("Destination: " + rs.getString(1) + ", Price: " + rs.getInt(2));
				}
				System.out.println("------------------------------");
				rs.close();
				st.close();
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

}
