import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;


public class MainFrame extends JFrame{
	public static final int WIDTH=500;
	public static final int HEIGHT=300;
	private JTextField inputField=new JTextField();
	private JTextField systemField=new JTextField();
	private JTextField tsField=new JTextField();
	private SetInputFrame in=new SetInputFrame(this);
	private SetSystemFrame sys=new SetSystemFrame(this); 
	//system
	public static final int MAX=100;
	private double[] Dx=new double[0];
	private int[] Rx=new int[0];
	private double[] Dy=new double[0];
	private int[] Ry=new int[0];
	//input
	private double[] A=new double[0];
	private double[] W=new double[0];
	private double[] phi=new double[0];
	//output type
	private JCheckBox orgOut=new JCheckBox("Original Signal");
	private JCheckBox afrOut=new JCheckBox("Signal After Processing");
	private JCheckBox freqAOut=new JCheckBox("Amptitude Freq-response");
	private JCheckBox freqPOut=new JCheckBox("Phase Freq-response");
	
	private class SetInputListener implements ActionListener{

		private MainFrame main;
		
		public SetInputListener(MainFrame main){
			this.main=main;
		}
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO 自動產生的方法 Stub
			in.setVisible(true);
		}
	}
	
	private class SetSystemListener implements ActionListener{

		//private MainFrame main;
		
		//public SetSystemListener(MainFrame main){
		//	this.main=main;
		//}
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO 自動產生的方法 Stub
			sys.setVisible(true);
		}
	}
	
	private class ClearListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO 自動產生的方法 Stub
			setInput(new LinkedList<Sin>());
			setSystem(new double[0],new double[0]);
			inputField.setText("");
			systemField.setText("");
			tsField.setText("0.0");
			orgOut.setSelected(false);
			afrOut.setSelected(false);
			freqAOut.setSelected(false);
			freqPOut.setSelected(false);
		}
		
	}
	
	private class SubmitListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO 自動產生的方法 Stub
			try{
			if(Double.parseDouble(tsField.getText())==0){tsField.setText("Error:Ts!=0");return;}
			}catch(NumberFormatException e2){
				tsField.setText("Error: Reenter Number.");return;
			}
			
			Input in=new Input(A,W,phi);
			CTSignal sig=new CTSignal(Double.parseDouble(tsField.getText()),in);
			Process pro=new Process(Dx,Rx,Dy,Ry);
			if(orgOut.isSelected()){
				DrawFrame org=new DrawFrame(sig,60,Process.CTSYSTEM,"Original Signal");
				org.setVisible(true);
			}
			if(afrOut.isSelected()){
				DrawFrame out=new DrawFrame(pro.convolution(sig),60,Process.CTSYSTEM,"Signal After Processing");
				out.setVisible(true);
			}
			if(freqAOut.isSelected()){
				DrawFreqFrame freqA=new DrawFreqFrame(pro,60,0,"Amptitude Frequency Response");
				freqA.setVisible(true);
			}
			if(freqPOut.isSelected()){
				DrawFreqFrame freqP=new DrawFreqFrame(pro,60,1,"Phase Frequency Response");
				freqP.setVisible(true);
			}
		}
		
	}
	
	public MainFrame(){
		super("System Analysis");
		
		setSize(WIDTH,HEIGHT);
		setFont(new Font("Times New Roman", Font.PLAIN, 20));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLayout(new BorderLayout(5,5));
		//set
		JPanel setPanel=new JPanel(new GridLayout(3,1));
			//input
		JPanel inputPanel=new JPanel(new BorderLayout());
		
		JLabel setInputLab=new JLabel("Input :x(t)=");
		inputPanel.add(setInputLab,BorderLayout.WEST);
		
		inputField.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		inputField.setEditable(false);
		
		inputPanel.add(new JScrollPane(inputField,JScrollPane.VERTICAL_SCROLLBAR_NEVER,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS),BorderLayout.CENTER);
		
		JButton setInputBtn=new JButton("set");
		setInputBtn.addActionListener(new SetInputListener(this));
		inputPanel.add(setInputBtn,BorderLayout.EAST);
		
		setPanel.add(inputPanel);
			//system
		JPanel systemPanel=new JPanel(new BorderLayout());
		
		JLabel setSystemLab=new JLabel("System :y[n]=");
		systemPanel.add(setSystemLab,BorderLayout.WEST);
		
		systemField.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		systemField.setEditable(false);
		
		systemPanel.add(new JScrollPane(systemField,JScrollPane.VERTICAL_SCROLLBAR_NEVER,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS),BorderLayout.CENTER);
		
		JButton setSystemBtn=new JButton("set");
		setSystemBtn.addActionListener(new SetSystemListener());
		systemPanel.add(setSystemBtn,BorderLayout.EAST);
		
		setPanel.add(systemPanel);
			//other
		JPanel otherPanel=new JPanel(new BorderLayout());
		
		JLabel setTsLab=new JLabel("Ts : ");
		otherPanel.add(setTsLab,BorderLayout.WEST);
		
		
		tsField.setSize(100, 20);
		otherPanel.add(tsField,BorderLayout.CENTER);
		
		JLabel secLab=new JLabel("sec");
		otherPanel.add(secLab,BorderLayout.EAST);
		
		setPanel.add(otherPanel);
		//end set
		
		//bottom
		JPanel bottomPanel=new JPanel();
		bottomPanel.setLayout(new GridLayout(3,2));
		
		bottomPanel.add(orgOut);
		bottomPanel.add(afrOut);
		bottomPanel.add(freqAOut);
		bottomPanel.add(freqPOut);
		
		JButton clearBtn=new JButton("Clear");
		clearBtn.addActionListener(new ClearListener());
		bottomPanel.add(clearBtn);
		
		JButton submitBtn=new JButton("Submit");
		submitBtn.addActionListener(new SubmitListener());
		bottomPanel.add(submitBtn);
		//end bottom
		
		add(setPanel,BorderLayout.CENTER);
		add(bottomPanel,BorderLayout.SOUTH);
	}
	
	public JTextField getInput(){
		return inputField;
	}
	
	public void setInput(LinkedList<Sin> sin){
		int size=sin.size();
		A=new double[size];
		W=new double[size];
		phi=new double[size];
		
		for(int i=0;i<size;i++){
			A[i]=sin.get(i).A;
			W[i]=sin.get(i).W;
			phi[i]=sin.get(i).phi;
		}
	}
	
	public JTextField getSystem(){
		return systemField;
	}
	
	public void setSystem(double[] Cx,double[] Cy){
		int jx,jy;
		jx=jy=0;
		for(int i=0;i<Cx.length;i++){
			if(Cx[i]!=0){jx++;}
			if(Cy[i]!=0){jy++;}
		}
		Dx=new double[jx];
		Rx=new int[jx];
		Dy=new double[jy];
		Ry=new int[jy];
		jx=jy=0;
		for(int i=0;i<Cx.length;i++){
			if(Cx[i]!=0){Dx[jx]=Cx[i];Rx[jx]=i;jx++;}
			if(Cy[i]!=0){Dy[jy]=Cy[i];Ry[jy]=i;jy++;}
		}
	}
}
