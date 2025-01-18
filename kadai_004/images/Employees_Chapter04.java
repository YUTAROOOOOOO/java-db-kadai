package kadai_004;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Employees_Chapter04 {

	public static void main(String[] args) {

		//ConnectionクラスとStatementクラスを定義
		Connection con = null;
		Statement statement = null;

		try {
			//DBを接続
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/challenge_java",
					"root",
					"19930524");
			//接続結果を出力
			System.out.println("データベース接続成功：" + con);

			//SQLクエリを準備
			statement = con.createStatement();
			String sql = """
					CREATE TABLE employees (
					id INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
					name VARCHAR(60) NOT NULL,
					emaill VARCHAR(255) NOT NULL,
					age INT(11),
					address VARCHAR(255)
					);
					""";

			//SQLクエリを実行
			int rowCnt = statement.executeUpdate(sql);
			//employeesテーブルの更新レコード数を出力
			System.out.println("社員テーブルを作成しました：更新レコード数＝" + rowCnt);
		} catch (SQLException e) {
			//エラーメッセージを出力
			System.out.println("エラー発生" + e.getMessage());
		} finally {
			//使用したオブジェクトを解放
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException ignone) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ignone) {
				}
			}
		}
	}
}
