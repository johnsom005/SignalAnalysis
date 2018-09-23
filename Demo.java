import java.util.Arrays;
import java.util.LinkedList;

public class Demo {

	public static void main(String[] args) {
		// TODO 自動產生的方法 Stub
		Input in=new Input(new double[]{1,1/3.0,1/5.0,1/7.0,1/9.0,1/11.0,1/13.0},new double[]{2,6,10,14,18,22,26},new double[]{0,0,0,0,0,0,0});
		Input in_sin=new Input(new double[]{1,0.1},new double[]{1,50},new double[]{1.57,1.57});
		
		CTSignal sigC=new CTSignal(0.02,in);
		CTSignal sigC1=new CTSignal(0.2,in_sin);
		
		
		Process pros=new Process(new double[]{1/100,-3/100,9/100,-27/100,81/100,-243/100,729/100},new int[]{1,2,3,4,5,6,7});
		Process low_pass=new Process(new double[]{0.2,0.2,0.2,0.2,0.2},new int[]{0,1,2,3,4});
		Process high_pass=new Process(new double[]{1,-1},new int[]{0,1});
		Process pros3=new Process(new double[]{0.1},new int[]{0},new double[]{0.9},new int[]{1});
		
		DTSignal sigD=new DTSignal(in_sin);
		DTSignal sigD1=new DTSignal();
		
		MainFrame demo=new MainFrame();
		demo.setVisible(true);
		
		//DrawFreqFrame freqP=new DrawFreqFrame(pros3,60,1,"Phase Frequency Response");
		//freqP.setVisible(true);
		
	}

}
