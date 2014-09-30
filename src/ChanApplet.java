import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ChanApplet extends Applet implements MouseListener, ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int xstart = 1234567890, xend = 1234567890, ystart = 1234567890,
			yend = 1234567890;
	int xpos = 1234567890;
	int ypos = 1234567890;
	int x1 = 1234567890, y1 = 1234567890, x2 = 1234567890, y2 = 1234567890;
	List<String> points = new ArrayList<String>();

	Color color = Color.RED;
	boolean draw = false;
	boolean clearScreen = false;
	Graphics graphics;
	boolean markPoint = false;
	static int sleepTime = 100;
	TextField num;
	TextField sleepValue;
	boolean showConvexHull = false;
	int h1, h2, h3, h4;
	static List<List<Integer>> linesList= new ArrayList<List<Integer>>();
	
	public void init() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(600, 600));

		final JPanel panel = new JPanel();
		frame.getContentPane().add(panel);

		// Add the MouseListener to your applet
		addMouseListener(this);
		final Button btnContinue = new Button("Generate hull");

		add(btnContinue);

		btnContinue.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					drawConvexHull();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		final Button clear = new Button("Clear");
		add(clear);
		clear.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				clearScreen = true;
				points = new ArrayList<String>();
				showConvexHull = false;
				resetAllFields();
				repaint();
			}

		});

		final Button randompts = new Button("Generate random points");

		add(randompts);
		randompts.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int maxNumber = 10;

				try {
					if (!num.getText().equals("")) {
						maxNumber = Integer.parseInt(num.getText());
					}
				} catch (Exception e) {
					throw e;
				}
				Set<String> set = new LinkedHashSet<String>();
				Random position = new Random();
				String test;
				do {
					test = new String();
					test = "(" + position.nextInt(500) + ","
							+ position.nextInt(500) + ")";
					set.add(test);
				} while (set.size() < maxNumber);

				List<String> list = new ArrayList<String>(set);
				points.clear();
				points.addAll(list);

				drawPoints(points, getGraphics());
			}

		});

		num = new TextField("",7);
		num.setSize(25,10);
		add(num);
		
		Button sleepBtn = new Button("Set Sleep Time");
		add(sleepBtn);
		sleepValue = new TextField("",7);
		add(sleepValue);
		sleepBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.out.println("setting sleep time");
				sleepTime = Integer.parseInt(sleepValue.getText());
			}
		});
	}

	public void paint(Graphics g) {
		if (!showConvexHull) {
			graphics = g;
			if (!clearScreen) {
				if (g == null) {
				}
				g.setColor(color);
				if (markPoint && xpos != 1234567890 && ypos != 1234567890) {
					g.fillArc(xpos, ypos, 10, 10, 0, 360);
					markPoint = false;
				}
				if (draw && x1 != 1234567890 && x2 != 1234567890) {
					if(!isLine(x1,y1,x2,y2)){
					g.drawLine(x1, y1, x2, y2);
					List<Integer> a = new ArrayList<>();
					a.add(x1);
					a.add(y1);
					a.add(x2);
					a.add(y2);
					linesList.add(a);
					}
				}
				if (draw && xstart != 1234567890 && ystart != 1234567890
						&& xend != 1234567890 && yend != 1234567890) {
					if(!isLine(xstart, ystart, xend, yend)){
					g.drawLine(xstart, ystart, xend, yend);
					List<Integer> a = new ArrayList<>();
					a.add(xstart);
					a.add(ystart);
					a.add(xend);
					a.add(yend);
					linesList.add(a);
					}
				}

				g.setColor(Color.RED);
			} else {
				clearScreen = false;
				Dimension d = getSize();
				g.setColor(getBackground());
				g.fillRect(0, 0, d.width, d.height);
			}
		} else {
			g.setColor(Color.BLUE);
			g.drawLine(h1, h2, h3, h4);
			showConvexHull = false;
		}
	}

	private boolean isLine(int x1, int y1, int x2, int y2) {
		List<Integer> a = new ArrayList<>();
		a.add(x1);
		a.add(y1);
		a.add(x2);
		a.add(y2);
		if(linesList.contains(a)){
			return true;
		}
		return false;
	}


	public void mouseClicked(MouseEvent e) {
		markPoint = true;
		xpos = e.getX();
		ypos = e.getY();
		points.add("(" + xpos + "," + ypos + ")");
		color = Color.RED;
		graphics = getGraphics();
		update(graphics);
	}

	// override update
	public void update(Graphics g) {
		paint(g);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	public void drawConvexHull() throws InterruptedException {
		List<String> convexhull = new ArrayList<String>();
		 convexhull = Chans.convexHull(points, true, graphics);
		draw = true;
		color = Color.BLUE;
		convexhull.add(convexhull.get(0));
		for (int k = 0; k < convexhull.size() - 1; k++) {
			showConvexHull = true;
			String h = convexhull.get(k).trim();
			String point[] = h.split(",");
			String j = convexhull.get(k + 1).trim();
			String nextPoint[] = j.split(",");
			h1 = Integer.parseInt(point[0].substring(1, point[0].length()));
			h2 = Integer.parseInt(point[1].substring(0, point[1].length() - 1));
			h3 = Integer.parseInt(nextPoint[0].substring(1,
					nextPoint[0].length()));
			h4 = Integer.parseInt(nextPoint[1].substring(0,
					nextPoint[1].length() - 1));
			update(getGraphics());
		}
		resetAllFields();

	}

	public void resetAllFields() {
		draw = false;
		xstart = 1234567890;
		xend = 1234567890;
		ystart = 1234567890;
		yend = 1234567890;
		xpos = 1234567890;
		ypos = 1234567890;
		x1 = 1234567890;
		y1 = 1234567890;
		x2 = 1234567890;
		y2 = 1234567890;
	}

	public void showSubPoints(List<String> subList, Graphics graphics, Color col)
			throws InterruptedException {
		color = col;
		drawPoints(subList, graphics);
		clearLines(subList, graphics);
		Thread.sleep(sleepTime);
	}

	public void showGrahamScanResult(List<String> grahamScanHull,
			Graphics graphics, Color col) throws InterruptedException {
		draw = true;
		color = col;
		drawHull(grahamScanHull, graphics);
		resetAllFields();
		Thread.sleep(sleepTime);
	}

	public void drawHull(List<String> hull, Graphics g) {
		clearScreen = true;
		repaint();
		drawPoints(points, g);
		for (String s : hull) {
			String h = s.trim();
			String point[] = h.split(",");
			xpos = Integer.parseInt(point[0].substring(1, point[0].length()));
			ypos = Integer
					.parseInt(point[1].substring(0, point[1].length() - 1));
			if (x1 == 1234567890) {
				x1 = xpos;
				y1 = ypos;
				xstart = x1;
				ystart = y1;
			} else {
				x2 = xpos;
				y2 = ypos;
			}
			if (s.equals(hull.get(hull.size() - 1))) {
				xend = xpos;
				yend = ypos;
			}
			update(g);
			if (x2 != 1234567890) {
				x1 = x2;
				y1 = y2;
			}
		}
	}

	private void drawPoints(List<String> points, Graphics g) {
		for (String p : points) {
			String[] point = p.split(",");
			xpos = Integer.parseInt(point[0].substring(1, point[0].length()));
			ypos = Integer
					.parseInt(point[1].substring(0, point[1].length() - 1));
			markPoint = true;
			update(g);
		}
	}

	private void clearLines(List<String> points, Graphics g) {
		color = getBackground();
		// TODO
	}

	public void showExtremePoint(List<String> hull, Graphics graphics,
			Color pink) throws InterruptedException {
		color = pink;
		markPoint = true;
		String[] point = hull.get(0).split(",");
		xpos = Integer.parseInt(point[0].substring(1, point[0].length()));
		ypos = Integer.parseInt(point[1].substring(0, point[1].length() - 1));
		update(graphics);
		Thread.sleep(sleepTime);
	}

	public void showPoint(String p, Graphics graphics2, Color blue)
			throws InterruptedException {
		color = blue;
		markPoint = true;
		String[] point = p.split(",");
		xpos = Integer.parseInt(point[0].substring(1, point[0].length()));
		ypos = Integer.parseInt(point[1].substring(0, point[1].length() - 1));
		update(graphics);
		resetAllFields();
		Thread.sleep(sleepTime);
	}
}