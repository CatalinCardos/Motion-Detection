import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;


import javax.swing.*;


public class MotionDetection {

    private JPanel panelSlider;

    public static void main(String args[]) throws InterruptedException {
        //load library
        nu.pattern.OpenCV.loadLocally();
        //System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        //OpenCV.loadShared();

        JFrame jframe = new JFrame("Title");
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel vidPanel = new JLabel();
        jframe.setContentPane(vidPanel);
        jframe.setSize(750,650);
        jframe.setVisible(true);

        ImageProcess imageProcess = new ImageProcess();
        Mat firstFrame = new Mat();

        VideoCapture camera = new VideoCapture();
        camera.open(0); //open camera
        //set the video size to 512x288
        camera.set(3, 512);
        camera.set(4, 288);

/*        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        firstFrame = imageProcess.getFirstFrame(camera,vidPanel);
        imageProcess.readCamera(camera,vidPanel,firstFrame);


    }


}