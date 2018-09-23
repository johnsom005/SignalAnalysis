import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class SetSystemFrame extends JFrame{
	public static final int WIDTH=500;
	public static final int HEIGHT=250;
	public static final int MAX=Signal.MAX;
	public static final int USED=20;//DrawFrame.WIDTH/2/DrawFrame.XLENGTH+1;
	public static final int UNITSTEP=1;
	public static final int UNITSAMPLE=0;
	private double[] Cy=new double[USED];
	private double[] Cx=new double[USED];
	private JTextField infoField=new JTextField("info will show here");
	private JTextField cofField;
	private JComboBox xyComboBox,RComboBox;
	private JButton updateBtn,deleteBtn;
	private JRadioButton unitSample,unitStep;
	private MainFrame mainui;
	public static String info;
	
	
	private class ConditionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO 自動產生的方法 Stub
			//System.out.println(xyComboBox.getSelectedItem()+" ,"+RComboBox.getSelectedItem());
			if(xyComboBox.getSelectedItem()=="y"){
				cofField.setText(String.valueOf(Cy[RComboBox.getSelectedIndex()]));
			}else if(xyComboBox.getSelectedItem()=="x"){
				cofField.setText(String.valueOf(Cx[RComboBox.getSelectedIndex()]));
			}else{
				cofField.setText("Error:31039 conditionListener");
			}
		}
		
	}
	
	private class UpdateListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO 自動產生的方法 Stub
			
			JButton btn=(JButton)e.getSource();
			if(btn==updateBtn){
				try{
					if(xyComboBox.getSelectedItem()=="y"){
						if(RComboBox.getSelectedIndex()==0){
							cofField.setText("Error:infinite loop or format");return;
						}
						Cy[RComboBox.getSelectedIndex()]=Double.parseDouble(cofField.getText());
					}else if(xyComboBox.getSelectedItem()=="x"){
						Cx[RComboBox.getSelectedIndex()]=Double.parseDouble(cofField.getText());
					}else{
						cofField.setText("Error:48055 updateListener");
					}
				}catch(NumberFormatException e2){
					cofField.setText("Error: Reenter Number.");
				}
			}else if(btn==deleteBtn){
				if(xyComboBox.getSelectedItem()=="y"){
					Cy[RComboBox.getSelectedIndex()]=0.0;
				}else if(xyComboBox.getSelectedItem()=="x"){
					Cx[RComboBox.getSelectedIndex()]=0.0;
				}else{
					cofField.setText("Error:48055 updateListener");
				}
				cofField.setText("0.0");
			}
			showInfo();
		}
		
	}
	
	private class SetListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO 自動產生的方法 Stub
			mainui.getSystem().setText(infoField.getText());
			mainui.setSystem(Cx,Cy);
			setVisible(false);
		}
		
	}
	
	private class ClearListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO 自動產生的方法 Stub
			for(int i=0;i<USED;i++){
				Cy[i]=Cx[i]=0.0;
			}
			cofField.setText("0.0");
			showInfo();
		}
		
	}
	
	public SetSystemFrame(){
		this(new MainFrame());
	}
	
	public SetSystemFrame(MainFrame mainui){
		super("Set DT System Difference Equation");
		setSize(WIDTH,HEIGHT);
		setLocation(400,100);
		setFont(new Font("Times New Roman", Font.PLAIN, 20));
		
		this.mainui=mainui;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout(5,5));
		/*bottom panel*/
		JPanel bottomPanel=new JPanel();
		bottomPanel.setLayout(new GridLayout(1,2));
		
		JButton clearBtn=new JButton("Clear");
		clearBtn.addActionListener(new ClearListener());
		bottomPanel.add(clearBtn);
		
		JButton setBtn=new JButton("Set");
		setBtn.addActionListener(new SetListener());
		bottomPanel.add(setBtn);
		
		add(bottomPanel,BorderLayout.SOUTH);
		/*info panel*/
		JPanel infoPanel=new JPanel();
		infoPanel.setLayout(new BorderLayout());
		
		JLabel infoLabel=new JLabel("y[n]=");
		infoLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		infoPanel.add(infoLabel,BorderLayout.WEST);
		
		//JPanel textPanel=new JPanel();
		//textPanel.setLayout(new FlowLayout());
		
		infoField.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		infoField.setEditable(false);
		//textPanel.add(infoField);
		
		infoPanel.add(new JScrollPane(infoField,JScrollPane.VERTICAL_SCROLLBAR_NEVER,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS),BorderLayout.CENTER);
		
		add(infoPanel,BorderLayout.NORTH);
		/*set panel*/
		JPanel setPanel=new JPanel();
		setPanel.setLayout(new GridLayout(3,3));
		
		JLabel get1Label=new JLabel("I/O");
		setPanel.add(get1Label);
		JLabel get2Label=new JLabel("delay");
		setPanel.add(get2Label);
		JLabel get3Label=new JLabel("coefficient");
		setPanel.add(get3Label);
		
		String[] io={"x","y"};
		
		xyComboBox = new JComboBox(io);
		xyComboBox.addActionListener(new ConditionListener());
		xyComboBox.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		setPanel.add(xyComboBox);
		
		String[] delay=new String[USED];
		for(int i=0;i<USED;i++){
			delay[i]=String.valueOf(i);
		}
		
		RComboBox = new JComboBox(delay);
		RComboBox.addActionListener(new ConditionListener());
		RComboBox.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		setPanel.add(RComboBox);
		
		cofField=new JTextField();
		cofField.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		cofField.setText("0.0");
		setPanel.add(cofField);
		
		JLabel emptyLabel=new JLabel();
		setPanel.add(emptyLabel);
		
		add(setPanel,BorderLayout.CENTER);
		/*update panel*/
		JPanel updatePanel=new JPanel();
		updatePanel.setLayout(new BorderLayout());
		
		updateBtn=new JButton("Update");
		updateBtn.addActionListener(new UpdateListener());
		updatePanel.add(updateBtn,BorderLayout.CENTER);
		
		deleteBtn=new JButton("Delete");
		deleteBtn.addActionListener(new UpdateListener());
		updatePanel.add(deleteBtn,BorderLayout.SOUTH);
		
		add(updatePanel,BorderLayout.EAST);
	}
	
	public double[] getCy(){
		double[] temp=new double[MAX];
		for(int i=0;i<MAX;i++){
			temp[i]=Cy[i];
		}
		return temp;
	}
	
	public double[] getCx(){
		double[] temp=new double[MAX];
		for(int i=0;i<MAX;i++){
			temp[i]=Cx[i];
		}
		return temp;
	}
	
	private void showInfo(){
		info="";
		boolean flag=false;
		for(int i=0;i<USED;i++){
			if(Cy[i]!=0.0){
				if(flag&&Cy[i]>0)info+="+";
				if(!flag)flag=true;
				info+=String.valueOf(Cy[i])+"*y[n-"+i+"]";
			}
		}
		for(int i=0;i<USED;i++){
			if(Cx[i]!=0.0){
				if(flag&&Cx[i]>0)info+="+";
				if(!flag)flag=true;
				info+=String.valueOf(Cx[i])+"*x[n-"+i+"]";
			}
		}
		infoField.setText(info);
	}
}
