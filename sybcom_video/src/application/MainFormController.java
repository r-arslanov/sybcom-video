package application;

import utils.Utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.VideoWriter;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MainFormController {
	@FXML
    private Button startBtn;
	@FXML
	private ImageView cappture_view;
	
	private ScheduledExecutorService timer;
	private Boolean isActive = false;
	
	// the OpenCV object that realizes the video capture
	private VideoCapture capture = new VideoCapture(0);
//	private VideoCapture capture = new VideoCapture("rtsp://192.168.43.147/test.mp4&t=unicast&p=rtsp&ve=H264&w=1280&h=720&ae=PCMU&sr=8000");
	private Mat pic = new Mat();
	
	@FXML
	void onClickedStartButton() {
		if(capture.isOpened()) {
			System.out.println("Clicked");
//			VideoWriter writer = new VideoWriter("C:\\OpenCV\\test.avi", VideoWriter.fourcc('X', 'V', 'I', 'D'), 30, new Size(640,480), true);  
			VideoWriter writer = new VideoWriter("C:\\OpenCV\\test.avi", VideoWriter.fourcc('X', 'V', 'I', 'D'), 30, new Size(640, 480), true);
	//		writer.open("C:\\OpenCV\\test2.avi", VideoWriter.fourcc('X', 'V', 'I', 'D'), 30, new Size(640, 480), true);
			Runnable grabber = new Runnable() {
				@Override
				public void run() {
					while(isActive) {
						capture.read(pic);
						writer.write(pic);
						Image img = Utils.mat2Image(pic);
						Platform.runLater(()->{
							cappture_view.setImage(img);
						});
						pic.release();
						try {
							Thread.sleep(33);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}};

			if(!this.isActive) {
//				this.timer = Executors.newSingleThreadScheduledExecutor();
//				this.timer.scheduleAtFixedRate(grabber, 0, 33, TimeUnit.MILLISECONDS);
				grabber.run();
				System.out.println("rn");
				this.isActive = true;
				this.startBtn.setText("Stop");
			}else{
//					this.timer.shutdown();
//					this.timer.awaitTermination(33, TimeUnit.MILLISECONDS);
//					this.startBtn.setText("Start");
				this.isActive = false;
				writer.release();
			}
		}
	}
	
}
