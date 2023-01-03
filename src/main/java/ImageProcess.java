import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ImageProcess {

    public Mat getFirstFrame (VideoCapture camera, JLabel vidPanel) throws InterruptedException {
        Mat frame = new Mat();
        Mat firstFrame = new Mat();
        camera.read(frame);
        //System.out.println("Stau");
        TimeUnit.SECONDS.sleep(2);
        //System.out.println("Nu stau");
        camera.read(frame);

        ImageIcon image = new ImageIcon(TransformMatToBufferedImage.matToBufferedImage(frame));
        vidPanel.setIcon(image);
        vidPanel.repaint();
        //convert to grayscale and set the first frame
        Imgproc.cvtColor(frame, firstFrame, Imgproc.COLOR_BGR2GRAY);
        Imgproc.GaussianBlur(firstFrame, firstFrame, new Size(21, 21), 0);

        return firstFrame;
    }

    public void readCamera (VideoCapture camera, JLabel vidPanel, Mat firstFrame){
        Mat gray = new Mat();
        Mat frameDelta = new Mat();
        Mat thresh = new Mat();
        Mat frame = new Mat();

        SoundPlayerUsingClip soundPlayerUsingClip = new SoundPlayerUsingClip();
        List<MatOfPoint> cnts = new ArrayList<MatOfPoint>();

        while(camera.read(frame)) {
            ImageIcon image = new ImageIcon();

            image = new ImageIcon(TransformMatToBufferedImage.matToBufferedImage(frame));
            vidPanel.setIcon(image);
            vidPanel.repaint();

            //convert to grayscale
            Imgproc.cvtColor(frame, gray, Imgproc.COLOR_BGR2GRAY);
            Imgproc.GaussianBlur(gray, gray, new Size(21, 21), 0);

            //compute difference between first frame and current frame
            Core.absdiff(firstFrame, gray, frameDelta);
            Imgproc.threshold(frameDelta, thresh, 25, 255, Imgproc.THRESH_BINARY);

            Imgproc.dilate(thresh, thresh, new Mat(), new Point(-1, -1), 2);
            Imgproc.findContours(thresh, cnts, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

            for(int i=0; i < cnts.size(); i++) {
                if(Imgproc.contourArea(cnts.get(i)) < 200) {
                    continue;
                }

                soundPlayerUsingClip.playSound2();
                cnts.clear();
                Imgproc.cvtColor(frame, firstFrame, Imgproc.COLOR_BGR2GRAY);
                Imgproc.GaussianBlur(firstFrame, firstFrame, new Size(21, 21), 0);
                //System.exit(0);
            }
        }
    }
}
