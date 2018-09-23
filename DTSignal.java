
public class DTSignal extends Signal{
	
	public DTSignal(){}
	
	public DTSignal(Input in){
		double[] temp=new double[MAX];
		
		for(int i=0;i<MAX;i++){
			temp[i]=in.cal(i);
		}
		setX(temp);
	}
}
