
public class CTSignal extends Signal{
	
	//private double T;
	
	public CTSignal(){}
	
	public CTSignal(double T,Input in){
		double[] temp=new double[MAX];
		setT(T);
		for(int i=0;i<MAX;i++){
			temp[i]=in.cal(i*T);
		}
		setX(temp);
	}
	
}
