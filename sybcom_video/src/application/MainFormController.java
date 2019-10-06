package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainFormController {
	@FXML
    private Button startBtn;
	
	@FXML
	void onClickedStartButton() {
		System.out.println("Button Clicked");
	}
	
}
