package coursework;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.SpringLayout;

public class StateDrawer extends JFrame {
	private JRadioButton	rdbtnDepthFirst, rdbtnBreadthFirst, rdbtnA, rdbtnIterativeDeepening;
	private JButton btnGo;
	private PathFinder pathFinder = new PathFinder();
	private int n;

	public static void main(String[] args) {
		StateDrawer gui = new StateDrawer();
		gui.go(5);
	}

	private void go(int n) {
		this.n = n;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(n * 60, n * 60 + 33));
		Timer t = new Timer();
		t.schedule(new UpdateGraphics(this), 0, 17);
		pack();
		setVisible(true);
	}
	
	public Dimension getSize(){
		return new Dimension(n * 20, n * 20 +33);
	}
	
	private class UpdateGraphics extends TimerTask{
		private JFrame mainFrame;
		
		public UpdateGraphics(JFrame mainFrame){
			this.mainFrame = mainFrame;
		}
		
		public void run() {
			mainFrame.repaint();
		}
	}
	
	public StateDrawer() {
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);

		JPanel animationPanel = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, animationPanel, 0, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, animationPanel, 0, SpringLayout.WEST, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, animationPanel, 0, SpringLayout.EAST, getContentPane());
		getContentPane().add(animationPanel);
		animationPanel.setLayout(new GridLayout(1, 0, 0, 0));

		JPanel algorithmChooserPanel = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, algorithmChooserPanel, -33, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, algorithmChooserPanel, 0, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, algorithmChooserPanel, 0, SpringLayout.EAST, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, animationPanel, 0, SpringLayout.NORTH, algorithmChooserPanel);
		springLayout.putConstraint(SpringLayout.WEST, algorithmChooserPanel, 0, SpringLayout.WEST, getContentPane());
		getContentPane().add(algorithmChooserPanel);

		ButtonGroup algorithmGroup = new ButtonGroup();
		rdbtnDepthFirst = new JRadioButton("Depth First");
		algorithmChooserPanel.add(rdbtnDepthFirst);
		algorithmGroup.add(rdbtnDepthFirst);

		rdbtnBreadthFirst = new JRadioButton("Breadth First");
		algorithmChooserPanel.add(rdbtnBreadthFirst);
		algorithmGroup.add(rdbtnBreadthFirst);

		rdbtnA = new JRadioButton("A*");
		algorithmChooserPanel.add(rdbtnA);
		algorithmGroup.add(rdbtnA);

		rdbtnIterativeDeepening = new JRadioButton("Iterative Deepening");
		algorithmChooserPanel.add(rdbtnIterativeDeepening);
		algorithmGroup.add(rdbtnIterativeDeepening);

		btnGo = new JButton("GO");
		btnGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnGo.setText("WAIT");
				setButtons(false);
			}
		});
		algorithmChooserPanel.add(btnGo);
	}

	public void setButtons(boolean active){
		rdbtnDepthFirst.setEnabled(active);
		rdbtnBreadthFirst.setEnabled(active);
		rdbtnA.setEnabled(active);
		rdbtnIterativeDeepening.setEnabled(active);
		btnGo.setEnabled(active);
	}
}
