package utils;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.VideoWriter;


public class Recorder extends Thread{
	private VideoCapture capture = new VideoCapture(0);
	//private VideoCapture capture = new VideoCapture("rtsp://192.168.43.147/test.mp4&t=unicast&p=rtsp&ve=H264&w=1280&h=720&ae=PCMU&sr=8000");
	
	private Mat pic = new Mat();
	private VideoWriter writer;
	private Boolean inter = false;
	
	private String fileName, path;
	
	public Recorder(String path, String fileName){
		this.fileName = fileName;
		this.path = path;
	}
	
	@Override
	public void run() {
		Mat temp = new Mat();
		capture.read(temp);
		Size rSize = temp.size();
		writer = new VideoWriter(path + fileName, VideoWriter.fourcc('X', 'V', 'I', 'D'), 30, rSize, true);
		while(!inter) {
			inter = Thread.currentThread().isInterrupted();
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

	public void call() {
		inter = !inter;
	}
}
