package application;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import utils.Recorder;

public class MainFormController {
	@FXML
    private Button startBtn;
	@FXML
	private ImageView cappture_view;
	
	private Boolean isActive = false;
	
	@FXML
	void onClickedStartButton() {
		Recorder mRecorder = new Recorder("C:\\OpenCV\\", "video_1.avi");
		Thread tRecorder = new Thread(mRecorder);
//		writer.open("C:\\OpenCV\\test2.avi", VideoWriter.fourcc('X', 'V', 'I', 'D'), 30, new Size(640, 480), true);
		if(!this.isActive) {
			this.isActive = true;
			tRecorder.setDaemon(true);
			tRecorder.start();
			this.startBtn.setText("Stop");
		}else{
			this.startBtn.setText("Start");
			this.isActive = false;
		}
	}
	
}
