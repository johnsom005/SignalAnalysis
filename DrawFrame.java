import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class DrawFrame extends JFrame{
	public static final int WIDTH=800;
	public static final int HEIGHT=600;
	public static final int XCENTER=WIDTH/5;
	public static final int YCENTER=HEIGHT/2+25;
	private int XLENGTH=15;
	private int YLENGTH=20;
	public static final int DOTSIZE=4;
	private double[] Y=new double[Signal.MAX];
	private double T=1;
	private int type=0;
	private JLabel valueN,valueY;
	
	
	private class MouseListener extends MouseAdapter{
		public void mouseMoved(MouseEvent e){
			int n=(int)Math.ceil((double)(e.getX()-XCENTER-XLENGTH/2)/XLENGTH);
			valueN.setText("n="+String.valueOf(n));
			if(n>=0)
				valueY.setText("y="+String.valueOf(Y[n]));
			else
				valueY.setText("y=0.0");
		}
	}
	

	public DrawFrame(Signal sig,int limit,int type){
		this(sig,limit,type,"");
	}
	
	public DrawFrame(Signal sig,int limit,int type,String title){
		super("paint : "+title);
		YLENGTH=limit;
		this.T=sig.getT();
		if(type==1)
			XLENGTH=(int)Math.ceil(50*T);
		this.type=type;
		setBounds(200,200,WIDTH,HEIGHT+25);
		setLayout(null);
		setResizable(false);
		for(int i=0;i<Signal.MAX;i++){
			Y[i]=sig.getX()[i];
		}
		addMouseMotionListener(new MouseListener());
		//getContentPane().setBackground(Color.BLACK);
		
		valueN=new JLabel("NULL");
		
		valueN.setFont(new Font("Arial",Font.PLAIN,16));
		valueN.setBounds(((XCENTER-85)<50)?(XCENTER-85):50, HEIGHT-80-25-5, 80, 18);
		
		valueY=new JLabel("NULL");
		valueY.setFont(new Font("Arial",Font.PLAIN,16));
		valueY.setBounds(((XCENTER-85)<50)?(XCENTER-85):50, HEIGHT-80+40/2-25-5,80,18);
		
		
		add(valueN);
		add(valueY);
	}
	
	public void paint(Graphics g){
		
		g.setColor(new Color(200,200,255));
		g.fillRect(0, 0, WIDTH, HEIGHT+25);
		super.paint(g);
		g.setColor(Color.GRAY);
		g.drawLine(0, 25+HEIGHT/2, WIDTH, 25+HEIGHT/2);
		g.drawLine(XCENTER, 0, XCENTER, HEIGHT+25);
		//int xCenter=WIDTH/2;
		for(int i=(-1)*(int)(XCENTER/XLENGTH*T);i<=(WIDTH-XCENTER)/XLENGTH*T;i++){
			
			HLabel(g,XCENTER+(int)(i/T*XLENGTH),25+HEIGHT/2,i);
		}
		//int yCenter=HEIGHT/2+25;
		for(int i=(-1)*HEIGHT/YLENGTH/2;i<=HEIGHT/YLENGTH/2;i++){
			if(i==0)continue;
			VLabel(g,XCENTER,YCENTER+i*YLENGTH,i*(-1));
		}
		
		for(int i=(-1)*XCENTER/XLENGTH;i<0;i++){
			if(type==1)
				g.drawLine(0, YCENTER, XCENTER, YCENTER);
			else
				dot(g,i,0);
		}
		for(int i=0;i<=(WIDTH-XCENTER)/XLENGTH;i++){
			
			if(type==1){
				g.setColor(Color.BLUE);
				g.drawLine(XCENTER+i*XLENGTH, YCENTER-(int)(Y[i]*YLENGTH),XCENTER+(i+1)*XLENGTH, YCENTER-(int)(Y[i+1]*YLENGTH));//Continue
			}else
				dot(g,i,Y[i]);//Discrete
		}
		
		
	}
	
	public void HLabel(Graphics g,int x,int y,int label){
		g.setColor(Color.GRAY);
		g.drawLine(x, y-3, x, y+3);
		g.setColor(Color.BLUE);
		g.setFont(new Font("Arial",Font.PLAIN,10));
		g.drawString(String.valueOf(label), x-2, y+10);
	}
	
	public void VLabel(Graphics g,int x,int y,int label){
		g.setColor(Color.GRAY);
		g.drawLine(x-3, y, x+3, y);
		g.setColor(Color.BLUE);
		g.setFont(new Font("Arial",Font.PLAIN,10));
		g.drawString(String.valueOf(label), x-12, y+2);
	}
	
	public void dot(Graphics g,int x,double y){
		g.setColor(Color.BLUE);
		g.drawLine(XCENTER+x*XLENGTH, YCENTER, XCENTER+x*XLENGTH, YCENTER-(int)(y*YLENGTH));
		g.setColor(Color.WHITE);
		g.fillOval(XCENTER+x*XLENGTH-DOTSIZE/2, YCENTER-(int)(y*YLENGTH)-DOTSIZE/2, DOTSIZE, DOTSIZE);
		g.setColor(Color.BLUE);
		g.drawOval(XCENTER+x*XLENGTH-DOTSIZE/2, YCENTER-(int)(y*YLENGTH)-DOTSIZE/2, DOTSIZE, DOTSIZE);
	}
}
