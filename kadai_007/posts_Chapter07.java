package kadai_007;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class posts_Chapter07 {

	public static void main(String[] args) {

		//Cnnectionオブジェクトの変数conをnullで初期化
		Connection con = null;
		//PreparedStatementオブジェクトの変数statementをnullで初期化
		PreparedStatement statement = null;

		//二次元配列リスト
		String[][] postList = {
				{ "1003", "2023-02-08", "昨日の夜は徹夜でした", "13" },
				{ "1002", "2023-02-08", "お疲れ様です！", "12" },
				{ "1003", "2023-02-09", "今日も頑張ります！", "18" },
				{ "1001", "2023-02-09", "無理は禁物ですよ！", "17" },
				{ "1002", "2023-02-10", "明日から連休ですね！", "20" }
		};

		//DBに接続
		try {
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/Challenge_java",
					"root",
					"19930524");

			//接続成功メッセージ出力
			System.out.println("データベース接続成功：" + con);

			//SQLクエリを準備
			String sql1 = "INSERT INTO posts (users_id, posted_at, post_content, likes) VALUES (?, ?, ?, ?);";
			String sql2 = "SELECT posted_at, post_content, likes FROM posts WHERE users_id = 1002";
			statement = con.prepareStatement(sql1);
			//レコード追加メッセージ
			System.out.println("レコード追加を実行します");
			
			//リストの1行目から順番に読み込む
			int rowCnt = 0;
			for (int i = 0; i < postList.length; i++) {
				//SQLクエリの？部分をlistのデータに置き換え
				statement.setString(1, postList[i][0]);
				statement.setString(2, postList[i][1]);
				statement.setString(3, postList[i][2]);
				statement.setString(4, postList[i][3]);
				
				//SQLクエリを実行（更新系）
				rowCnt = statement.executeUpdate();
			}
			
			//追加したレコード件数を出力
			System.out.println(postList.length + "件のレコードが追加されました");

			//SQLクエリを実行（取得系）
			statement = con.prepareStatement(sql2);
			ResultSet result = statement.executeQuery();
			
			//検索条件を出力
			System.out.println("ユーザーIDが1002のレコードを検索しました");
			
			//SQLクエリの実行結果を抽出
			while (result.next()) {
				String posted_at = result.getString("posted_at");
				String post_content = result.getString("post_content");
				int likes = result.getInt("likes");
				//抽出結果を出力
				System.out.println(result.getRow() + "件目：投稿日時=" + posted_at + "/投稿内容="
						+ post_content + "/いいね数=" + likes);
			}
		} catch (SQLException e) {
			System.out.println("エラー発生：" + e.getMessage());
		} finally {
			//使用したオブジェクトを解放
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException ignore) {

				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ignore) {

				}
			}
		}
	}
}
