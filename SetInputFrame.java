import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class SetInputFrame extends JFrame{
	public static final int WIDTH=500;
	public static final int HEIGHT=250;
	public static final int MAX=Signal.MAX;
	private JTextField infoField=new JTextField("A*sin(W*t+phi)+...");
	private JTextField AField;
	private JTextField WField;
	private JTextField phiField;
	private JLabel emptyLabel=new JLabel();
	private JButton updateBtn,deleteBtn;
	private LinkedList<Sin> sin=new LinkedList<Sin>();
	private MainFrame mainui;
	public static String info;
	
	
	private class UpdateListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO 自動產生的方法 Stub
			
			JButton btn=(JButton)e.getSource();
			try{
				if(Double.parseDouble(phiField.getText())>3.14||Double.parseDouble(phiField.getText())<-3.14){emptyLabel.setText("-3.14<phi<3.14");return;}
			}catch(NumberFormatException e2){
				emptyLabel.setText("Error: Reenter Number.");
			}
			if(btn==updateBtn){
				
				try{
					if(Double.parseDouble(AField.getText())==0||Double.parseDouble(WField.getText())==0){emptyLabel.setText("A!=0&&W!=0");return;}
					int index=sin.indexOf(new Sin(Double.parseDouble(AField.getText()),Double.parseDouble(WField.getText()),Double.parseDouble(phiField.getText())));
					if(index==-1)sin.addLast(new Sin(Double.parseDouble(AField.getText()),Double.parseDouble(WField.getText()),Double.parseDouble(phiField.getText())));
					else sin.set(index, new Sin(Double.parseDouble(AField.getText()),Double.parseDouble(WField.getText()),Double.parseDouble(phiField.getText())));
				}catch(NumberFormatException e2){
					emptyLabel.setText("Error: Reenter Number.");
				}
				
			}else if(btn==deleteBtn){
				try{
					if(Double.parseDouble(WField.getText())==0){emptyLabel.setText("W!=0");return;}
					int index=sin.indexOf(new Sin(Double.parseDouble(AField.getText()),Double.parseDouble(WField.getText()),Double.parseDouble(phiField.getText())));
					if(index==-1){emptyLabel.setText("Error:sin NOT exist.");return;}
					else sin.remove(index);
				}catch(NumberFormatException e2){
					emptyLabel.setText("Error: Reenter Number.");
				}
				
				AField.setText("0.0");
			}
			showInfo();
		}
		
	}
	
	private class SetListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO 自動產生的方法 Stub
			mainui.getInput().setText(infoField.getText());
			mainui.setInput(sin);
			setVisible(false);
			
		}
		
	}
	
	private class ClearListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO 自動產生的方法 Stub
			for(int i=0;i<MAX;i++){
				sin.clear();
			}
			AField.setText("0.0");
			WField.setText("0.0");
			phiField.setText("0.0");
			showInfo();
		}
		
	}
	
	public SetInputFrame(){
		this(new MainFrame());
	}
	
	public SetInputFrame(MainFrame mainui){
		super("Set DT System Input");
		setSize(WIDTH,HEIGHT);
		setFont(new Font("Times New Roman", Font.PLAIN, 20));
		
		setLocation(400,200);
		
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
		
		JLabel infoLabel=new JLabel("x(t)=");
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
		
		JLabel get1Label=new JLabel("A");
		setPanel.add(get1Label);
		JLabel get2Label=new JLabel("W");
		setPanel.add(get2Label);
		JLabel get3Label=new JLabel("phi");
		setPanel.add(get3Label);
		
		
		
		AField=new JTextField();
		AField.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		AField.setText("0.0");
		setPanel.add(AField);
		
		WField=new JTextField();
		WField.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		WField.setText("0.0");
		setPanel.add(WField);
		
		phiField=new JTextField();
		phiField.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		phiField.setText("0.0");
		setPanel.add(phiField);
		
		
		emptyLabel.setForeground(Color.RED);
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
	
	
	private void showInfo(){
		info="";
		for(int i=0;i<sin.size();i++){
			if(i!=0)info+=" + ";
			info+=sin.get(i).A+" *sin( "+sin.get(i).W+" *t";
			if(sin.get(i).phi!=0)info+="+ "+sin.get(i).phi+" ";
			info+=")";
		}
		infoField.setText(info);
	}
}
