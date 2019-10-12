package application;

import database.DatabaseHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class MainFormController {
	@FXML
    private Button addCameraBtn, queryBtn, initBtn;
	@FXML
	private TextField txtURL, txtName;
	
	DatabaseHandler db = new DatabaseHandler("VideoServer.db");
	
	@FXML
	void onClickedAddCamera() {
		if(db.isOpened()) {
			db.insert(txtName.getText(), 1, txtURL.getText());
			db.selectAll();
		}else {
			System.out.println("Connection is not opened");
		}
	}
	
	@FXML
	void onClickedInitDB() {
		if(!db.isOpened()){
			db.init();
		}else {
			System.out.println("Connection alredy opened");
		}
	}
	@FXML
	void onClickedQueryBtn() {
		if(db.isOpened()) {
			db.selectAll();
		}else {
			System.out.println("Connection is not opened");
		}
	}
	
	@FXML
	void onClickedStartRecorder() {
		if(db.isOpened()) {
			db.startAllRecorders();
		}else {
			System.out.println("Connection is not opened");
		}
	}
}
