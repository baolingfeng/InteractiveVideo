package cn.zju.blf.video.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ImageProcesser {
	
    public static void main(String[] args) throws IOException {
        String path = "C:/baolingfeng/research/InteractiveVideo/data/example3/log/screen/2016-07-13-13-02-01-478.png";
        File file = new File(path);
        BufferedImage image = ImageIO.read(file);
        
        
        JLabel label = new JLabel(new ImageIcon(image));
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(label);
        f.pack();
        f.setLocation(200,200);
        f.setVisible(true);
    }
	
}
