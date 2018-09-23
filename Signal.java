
public class Signal {
	public static final int MAX=1000;
	private double[] x=new double[MAX];
	private int num;
	private double T=1;
	
	public Signal(){}
	
	public Signal(double[] x){
		this.num=x.length;
		for(int i=0;i<MAX&&i<this.num;i++){
			this.x[i]=x[i];
		}
	}
	
	public void setX(double[] x){
		this.num=x.length;
		for(int i=0;i<MAX&&i<this.num;i++){
			this.x[i]=x[i];
		}
	}
	
	public void setXi(double x,int i){
		this.x[i]=x;
	}
	
	public double[] getX(){
		double[] temp=new double[num];
		for(int i=0;i<MAX&&i<this.num;i++){
			temp[i]=x[i];
		}
		return temp;
	}
	
	public double getXi(int i){
		return this.x[i];
	}

	public static double imp(double d) {
		
		return (d==0)?1:0;
	}

	public static double stp(double d) {
		
		return (d>=0)?1:0;
	}
	
	public double getT(){
		return T;
	}
	
	public void setT(double T){
		this.T=T;
	}
}
