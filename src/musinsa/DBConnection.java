package musinsa;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DBConnection {
	private Connection connection = null;
	private Statement statement = null;
	private ResultSet rs = null;

	// connection
	public void connect() {
		// 1.properties
		Properties properties = new Properties();
		// properties 
		FileInputStream fis;

		try {
			fis = new FileInputStream("C:/java_test/musinsa/src/musinsa/db.properties");
			properties.load(fis);
		} catch (FileNotFoundException e) {
			System.out.println("FileInputStream error " + e.getStackTrace());

		} catch (IOException e) {
			System.out.println("properties error " + e.getStackTrace());
		}

		// 드라이버 로드
		try {
			Class.forName(properties.getProperty("driver"));
			connection = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("userid"),
					properties.getProperty("password"));
		} catch (ClassNotFoundException e) {
			System.out.println("class.forname error " + e.getStackTrace());

		} catch (SQLException e) {
			System.out.println("connection error " + e.getStackTrace());
		}

	}

	// insert
	public int insert(Musinsa musinsa) {
		PreparedStatement ps = null;
		int insertReturnValue = -1;
//		String insertQuery = "insert into musinsa Values (?,?,?,?,?,?,?,?,?,?)";
		String insertQuery = "call procedure_insert_musinsa( ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			ps = connection.prepareStatement(insertQuery);
			ps.setString(1, musinsa.getNo());
			ps.setString(2, musinsa.getName());
			ps.setInt(3, musinsa.getAge());
			ps.setString(4, musinsa.getPhone());
			ps.setInt(5, musinsa.getClothing());
			ps.setInt(6, musinsa.getAcc());
			ps.setInt(7, musinsa.getSport());
			ps.setInt(8, musinsa.getPet());
//			ps.setInt(9, musinsa.getTotal());
//			ps.setString(10, musinsa.getGrade());
			insertReturnValue = ps.executeUpdate();
		} catch (Exception e) {
			System.out.println("Excoption error " + e.getMessage());

		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				System.out.println("PreparedStatement close error " + e.getStackTrace());
			}
		}
		return insertReturnValue;
	}

	// Output
	public List<Musinsa> select() {
		List<Musinsa> list = new ArrayList<Musinsa>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String selectQuery = "select * from musinsa";
		try {
			ps = connection.prepareStatement(selectQuery);
			rs = ps.executeQuery(selectQuery);
			if (!(rs == null || rs.isBeforeFirst())) {
				return list;
			}

			while (rs.next()) {
				String no = rs.getString("no");
				String name = rs.getString("name");
				int age = rs.getInt("age");
				String phone = rs.getString("phone");
				int clothing = rs.getInt("clothing");
				int acc = rs.getInt("acc");
				int sport = rs.getInt("sport");
				int pet = rs.getInt("pet");
				int total = rs.getInt("total");
				String grade = rs.getString("grade");
				String save = rs.getString("save");
				String dc = rs.getString("dc");

				list.add(new Musinsa(no, name, age, phone, clothing, acc, sport, pet, total, grade, save, dc));
			}
		} catch (SQLException e) {
			System.out.println("select error " + e.getMessage());

		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				System.out.println("preparedStatement close error" + e.getStackTrace());
			}
		}
		return list;
	}

	// search
	public List<Musinsa> selectSearch(String data, int type) {
		List<Musinsa> list = new ArrayList<Musinsa>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String selectSearchQuery = "select * from musinsa where ";

		try {
			switch (type) {
			case 1:
				selectSearchQuery += "no like ? ";
				break;
			case 2:
				selectSearchQuery += "name like ? ";
				break;
			default:
				System.out.println("잘못 입력하였습니다.");
				return list;
			}

			ps = connection.prepareStatement(selectSearchQuery);

			String namepattern = "%" + data + "%";
			ps.setString(1, namepattern);
			rs = ps.executeQuery();

			// 결과값이 없을떄 체크
			if (!(rs == null || rs.isBeforeFirst())) {
				return list;
			}
			// rs.next() : 현재 커서에 있는 레코드 위치로 간다
			while (rs.next()) {
				String no = rs.getString("no");
				String name = rs.getString("name");
				int age = rs.getInt("age");
				String phone = rs.getString("phone");
				int clothing = rs.getInt("clothing");
				int acc = rs.getInt("acc");
				int sport = rs.getInt("sport");
				int pet = rs.getInt("pet");
				int total = rs.getInt("total");
				String grade = rs.getString("grade");
				String save = rs.getString("save");
				String dc = rs.getString("dc");

				list.add(new Musinsa(no, name, age, phone, clothing, acc, sport, pet, total, grade, save, dc));
			}
		} catch (Exception e) {
			System.out.println("Exception error " + e.getMessage());

		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				System.out.println("preparedStatement close error" + e.getStackTrace());
			}
		}
		return list;
	}

	// Update
	public int update(Musinsa ms) {
		PreparedStatement ps = null;
		String insertQuery = "call procedure_update_musinsa( ?, ?, ?, ?, ?, ?, ?, ?)";
//		String insertQuery = "update musinsa set clothing = ?, acc = ?, sport = ?, pet = ?,"
//				+ "total = ?, grade = ? where no = ?";
		int updateReturnValue = -1;

		try {
			ps = connection.prepareStatement(insertQuery);
			ps.setString(1, ms.getNo());
			ps.setString(2, ms.getName());
			ps.setInt(3, ms.getAge());
			ps.setString(4, ms.getPhone());
			ps.setInt(5, ms.getClothing());
			ps.setInt(6, ms.getAcc());
			ps.setInt(7, ms.getSport());
			ps.setInt(8, ms.getPet());
			updateReturnValue = ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQLException error " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Exception error " + e.getStackTrace());
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				System.out.println("preparedStatement close error" + e.getStackTrace());
			}
		}
		return updateReturnValue;
	}

	// delete
	public int delete(String no) {
		PreparedStatement ps = null;
		int deleteReturnValue = -1;
		String insertQuery = "delete from musinsa where no = ?";
		try {
			ps = connection.prepareStatement(insertQuery);
			ps.setString(1, no);

			deleteReturnValue = ps.executeUpdate();
		} catch (Exception e) {
			System.out.println("Exception error " + e.getMessage());
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				System.out.println("preparedStatement close error" + e.getStackTrace());
			}
		}
		return deleteReturnValue;
	}

	// order By
	public List<Musinsa> selectOrderBy(int type) {
		List<Musinsa> list = new ArrayList<Musinsa>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String selectOrderByQuery = "select * from musinsa order by ";
		try {
			switch (type) {
			case 1:
				selectOrderByQuery += "no asc ";
				break;
			case 2:
				selectOrderByQuery += "name asc ";
				break;
			case 3:
				selectOrderByQuery += "total desc ";
				break;
			default:
				System.out.println("정렬타입 오류");
				return list;
			}
			ps = connection.prepareStatement(selectOrderByQuery);

			rs = ps.executeQuery();
			// 결과값이 없을떄 체크
			if (!(rs == null || rs.isBeforeFirst())) {
				return list;
			}
			
			while (rs.next()) {
				String no = rs.getString("no");
				String name = rs.getString("name");
				int age = rs.getInt("age");
				String phone = rs.getString("phone");
				int clothing = rs.getInt("clothing");
				int acc = rs.getInt("acc");
				int sport = rs.getInt("sport");
				int pet = rs.getInt("pet");
				int total = rs.getInt("total");
				String grade = rs.getString("grade");
				String save = rs.getString("save");
				String dc = rs.getString("dc");

				list.add(new Musinsa(no, name, age, phone, clothing, acc, sport, pet, total, grade, save, dc));
			}
		} catch (Exception e) {
			System.out.println("select sort error " + e.getMessage());
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				System.out.println("preparedStatement close error" + e.getStackTrace());
			}
		}
		return list;

	}

	//Max min 검색
	public List<Musinsa> selectMaxMin(int type) {
		List<Musinsa> list = new ArrayList<Musinsa>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String selectMaxMinQuery = "select * from musinsa where total = ";
		try {
			switch (type) {
			case 1:
				selectMaxMinQuery += "(select max(total) from musinsa)";
				break;
			case 2:
				selectMaxMinQuery += "(select min(total) from musinsa)";
				break;
			default:
				System.out.println("Max Min 타입 오류");
				return list;
			}
			ps = connection.prepareStatement(selectMaxMinQuery);

			rs = ps.executeQuery();
			// 결과값이 없을떄 체크
			if (!(rs == null || rs.isBeforeFirst())) {
				return list;
			}
			
			while (rs.next()) {
				String no = rs.getString("no");
				String name = rs.getString("name");
				int age = rs.getInt("age");
				String phone = rs.getString("phone");
				int clothing = rs.getInt("clothing");
				int acc = rs.getInt("acc");
				int sport = rs.getInt("sport");
				int pet = rs.getInt("pet");
				int total = rs.getInt("total");
				String grade = rs.getString("grade");
				String save = rs.getString("save");
				String dc = rs.getString("dc");

				list.add(new Musinsa(no, name, age, phone, clothing, acc, sport, pet, total, grade, save, dc));
			}
		} catch (Exception e) {
			System.out.println("select Max Min error " + e.getMessage());
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				System.out.println("preparedStatement close error" + e.getStackTrace());
			}
		}
		return list;

	}
	
	// close
	public void close() {
		try {
			if (statement != null) {
				statement.close();
			}
			if (rs != null) {
				rs.close();
			}
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			System.out.println("connection error " + e.getStackTrace());
		}

	}

	

}
