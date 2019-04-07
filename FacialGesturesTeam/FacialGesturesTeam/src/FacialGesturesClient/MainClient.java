package FacialGesturesClient;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.RowId;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.jar.Attributes.Name;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.RowFilter;

public class MainClient extends JFrame {

	private HeartClient heart;
	private SkinClient skin;
	private EyeClient eye;
	private FaceClient face;
	private BCIClient bci;
	private static MainClient instance = null;
	private static BufferedImage predictImage = null;
	private static BufferedImage image = new BufferedImage(1280, 768,
            BufferedImage.TYPE_INT_RGB);
	private static JPanel canvas = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 64, 38, this);
        }
    };
	static HashMap<Integer, Double> faceHeartData = new HashMap<>();

	private static JTabbedPane client = new JTabbedPane();
	static double[] passArray = null;
	public MainClient() {
		passArray = new double[9];
	}

	private void shutdown() {
		heart.getSubscriber().stop();
		heart.getService().shutdown();
		skin.getSubscriber().stop();
		skin.getService().shutdown();
		eye.getSubscriber().stop();
		eye.getService().shutdown();
		face.getSubscriber().stop();
		face.getService().shutdown();
		bci.getSubscriber().stop();
		bci.getService().shutdown();

		try {
			if (!heart.getService().awaitTermination(10, TimeUnit.SECONDS)) {
				heart.getService().shutdownNow();

			}
			if (!skin.getService().awaitTermination(10, TimeUnit.SECONDS)) {
				skin.getService().shutdownNow();

			}
			if (!eye.getService().awaitTermination(10, TimeUnit.SECONDS)) {
				eye.getService().shutdownNow();

			}
			if (!face.getService().awaitTermination(10, TimeUnit.SECONDS)) {
				face.getService().shutdownNow();

			}
			if (!bci.getService().awaitTermination(10, TimeUnit.SECONDS)) {
				bci.getService().shutdownNow();

			}
		} catch (InterruptedException ex) {
			System.out.println("Exception: " + ex);
		}

	}

	// static ArrayList<Double> faceHeartData = new ArrayList<>();
	static boolean fileStarted = false;
	static PrintWriter pw;
	{
		try {
			pw = new PrintWriter(new File("NewData.csv"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void collectData(double data, int index) {

		faceHeartData.put(index, data);

		if (faceHeartData.size() == 6) {
			
			for (int i = 0; i < faceHeartData.size(); i++)
				passArray[i] = faceHeartData.get(i);

			double valuePleasure = (passArray[0] + passArray[1] + passArray[5]) / 3;

			if (passArray[1] > 0.5)
				passArray[6] = (passArray[1] + valuePleasure) / 2;
			else
				passArray[6] = valuePleasure;

			double valueArousal = (passArray[2] + passArray[3] + passArray[4] + passArray[5]) / 4;

			if (passArray[4] > 0.5)
				passArray[7] = (passArray[4] + valueArousal) / 2;
			else
				passArray[7] = valueArousal;

			int SAD = 1;
			int MODERATE = 2;
			int HAPPY = 3;

			double averagePA = (passArray[6] + passArray[7]) / 2;
			if (averagePA >= 0 && averagePA < 0.4) {
				passArray[8] = SAD;
			} else if (averagePA >= 0.4 && averagePA < 0.7) {
				passArray[8] = MODERATE;
			} else {
				passArray[8] = HAPPY;
			}

			// String [] arr = {"happy"};
			StringBuilder sb = new StringBuilder();
			for (double element : passArray) {
				System.out.println(" Element - " + element);
				sb.append(element);
				sb.append(",");
			}
			sb.append("\n");
			pw.write(sb.toString());
			
//			pw.close();
			for (int i = 0; i < 9; i++)
				System.out.print(passArray[i] + ",");
			faceHeartData = new HashMap<>();
			
		}
	}
public static JPanel plot(double d, double e) {
		
	    Graphics g = image.getGraphics();
	    g.setColor(Color.red);
//	    g.
	    g.drawRect(5, 7, 2, 2);
	    g.drawRect(5, 8, 1, 1);
	    g.drawRect(5, 6, 1, 1);
	    g.drawRect(5, 9, 1, 1);
	    g.drawRect(5, 10, 1, 1);
	    g.drawRect(5, 11, 1, 1);
	    g.dispose();
	    MainClient.canvas.repaint();
	    return MainClient.canvas;
	}
	public static void main(String[] args) {
		MainClient primaryClient = MainClient.getInstance();
		primaryClient.heart = new HeartClient(new Subscriber("", -1));
		primaryClient.skin = new SkinClient(new Subscriber("", -1));
		primaryClient.eye = new EyeClient(new Subscriber("", -1));
		primaryClient.face = new FaceClient(new Subscriber("", -1));
		primaryClient.bci = new BCIClient(new Subscriber("", -1));
		primaryClient.setSize(600, 600);
		primaryClient.setVisible(true);
		client.setBounds(0, 0, 600, 600);
		client.addTab("Heart", primaryClient.heart.processPanelHeart("Heart"));
		client.addTab("Skin", primaryClient.skin.processPanelSkin("Skin"));
		client.addTab("BCI", primaryClient.bci.processPanelBCI("BCI"));
		client.addTab("Eye", primaryClient.eye.processPanelEye("Eye"));
		client.addTab("Face", primaryClient.face.processPanelFace("Face"));
		JPanel outerPanel = new JPanel();
	    outerPanel.setBackground(Color.white);
	    outerPanel.setLayout(new GridLayout(1,2));
	    outerPanel.add(client);
	    JPanel innerPanel = new JPanel();
	    innerPanel.setLayout(new GridLayout(2,1));
	    JPanel plot = new JPanel();
	    JPanel predict = new JPanel(); 
	    plot.setBackground(Color.black);
	    outerPanel.setBackground(Color.white);
	    innerPanel.add(plot(2.5,3.6));
	    innerPanel.add(predict);
	    outerPanel.add(innerPanel);
	    primaryClient.add(outerPanel);

		primaryClient.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent e) {
//				MainClient.getInstance().shutdown();
				System.exit(0);
			}
		});

	}

	private static MainClient getInstance() {
		// TODO Auto-generated method stub
		return new MainClient();
	}

}