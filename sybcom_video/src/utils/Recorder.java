package utils;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.VideoWriter;


public class Recorder extends Thread{
	
	private VideoCapture capture;
	
	private Mat pic = new Mat();
	private VideoWriter writer;
	
	private String fileName, path;
	
	public Recorder(String path, String camera, String url){
		this.fileName = "1_" + camera;
		this.path = path;
		System.out.println(url);
		if(url == "0") {
			capture = new VideoCapture(0);
		}else {
			capture = new VideoCapture(url);
		}
	}
	
	@Override
	public void run() {
		Mat temp = new Mat();
		capture.read(temp);
		Size rSize = temp.size();
		writer = new VideoWriter(path + fileName, VideoWriter.fourcc('X', 'V', 'I', 'D'), 30, rSize, true);
		while(capture.isOpened()) {
			capture.read(pic);
			writer.write(pic);
			pic.release();
			try {
				Thread.sleep(33);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				this.interrupt();
				e.printStackTrace();
			}
		}
		writer.release();
		capture.release();
	}
}
