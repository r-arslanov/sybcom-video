package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import utils.Recorder;

public class DatabaseHandler{
	
	private String database;
	private Connection con;
	private Statement stat;
	private Boolean opened = false;
	public DatabaseHandler(String fileDB) {
		this.database = fileDB;
	}
// Инициализация соединения с БД
	public void init() {
		try {
			con = DriverManager.getConnection("jdbc:sqlite:" + database);
			if(stat == null) {
				stat = con.createStatement();
				stat.executeUpdate("CREATE TABLE IF NOT EXISTS cameras ("
						+" id 			INTEGER PRIMARY KEY AUTOINCREMENT,"
						+" name 		TEXT NOT NULL,"
						+" type			INTEGER DEFAULT 1,"
						+ "url			TEXT NOT NULL);");
				
				opened = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
// Запись значений в БД
	public void insert(String name, Integer type, String url) {
		String query = "INSERT INTO cameras(name, url, type) VALUES(\"" + name + "\",\""+url+"\"," + type + ");";
		if(stat != null) {
			try {
				stat.executeUpdate(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void selectAll() {
		ResultSet rs;
		try {
			rs = stat.executeQuery("SELECT * FROM cameras;");
			System.out.println("--------------------------------------------------");
			while(rs.next()) {
				Integer id = rs.getInt("id");
				String  name = rs.getString("name");
				Integer type = rs.getInt("type");
				String url = rs.getString("url");
				
				System.out.format("| %d | %s | %d | %s |%n", id, name, type, url);
			}
			System.out.println("--------------------------------------------------");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void startAllRecorders() {
		try {
			ResultSet rs = stat.executeQuery("SELECT * FROM cameras;");
			while(rs.next()) {
				Recorder oRecorder = new Recorder("C:\\OpenCV\\", rs.getString("name") + ".avi", rs.getString("url"));
				Thread tRecorder = new Thread(oRecorder, rs.getString("name"));
				tRecorder.setDaemon(true);
				tRecorder.start();
				
				System.out.format("Recorder started: %s | %s %n", rs.getString("name"), rs.getString("url") );
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Boolean isOpened() {		
		return opened;
	}
}
