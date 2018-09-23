import java.util.Arrays;

//DT:y[n]=Dx[]*x[n-Rx[]](+Dy*y[n-Ry[]])//up order&& Ry>=1
//CT:Y=Cx[0]*X+Cx[1]*AX+Cy[0]*AY
public class Process {
	public static final int MAX=100;
	public static final int DTSYSTEM=0;
	public static final int CTSYSTEM=1;
	private int type;
	private double[] Dx=new double[MAX];
	private int[] Rx=new int[MAX];
	private double[] Dy=new double[MAX];
	private int[] Ry=new int[MAX];
	private double[] Cx=new double[MAX];
	private double[] Cy=new double[MAX];
	private int szDx;
	private int szDy;
	private double[] y=new double[Signal.MAX];
	
	public Process(){}
	
	public Process(double[] Dx,int[] Rx){
		type=DTSYSTEM;
		szDx=Dx.length;
		szDy=0;
		for(int i=0;i<szDx;i++){
			this.Dx[i]=Dx[i];
			this.Rx[i]=Rx[i];
		}
	}
	
	public Process(double[] Dx,int[] Rx,double[] Dy,int[] Ry){
		type=DTSYSTEM;
		szDx=Dx.length;
		szDy=Dy.length;
		for(int i=0;i<szDx;i++){
			this.Dx[i]=Dx[i];
			this.Rx[i]=Rx[i];
		}
		for(int i=0;i<szDy;i++){
			if(Ry[i]<1)System.exit(1);
			this.Dy[i]=Dy[i];
			this.Ry[i]=Ry[i];
		}
	}
	
	public Signal convolution(Signal sig){
		
		Signal temp;
		if(sig.getT()==1)
			temp=new DTSignal();
		else{
			temp=new CTSignal();
			temp.setT(sig.getT());
		}
		
		double[] zero=new double[Signal.MAX];
		Arrays.fill(zero, 0);
		temp.setX(zero);
		Arrays.fill(y, 0);
		
		for(int i=0;i<Signal.MAX;i++){
			for(int j=0;j<szDx;j++){
				if(i+Rx[j]<Signal.MAX){
					temp.setXi(temp.getXi(i+Rx[j])+sig.getXi(i)*Dx[j], i+Rx[j]);
					y[i+Rx[j]]+=sig.getXi(i)*Dx[j];
				}
			}
			for(int j=0;j<szDy;j++){
				if(i+Ry[j]<Signal.MAX){
					temp.setXi(temp.getXi(i+Ry[j])+y[i]*Dy[j], i+Ry[j]);
					y[i+Ry[j]]+=y[i]*Dy[j];
				}
			}
		}
		return temp;
	}
	
	public double freqA(double W){
		Input in=new Input(1,W,0);
		
		CTSignal sig=new CTSignal(1,in);
		Signal sigA=convolution(sig);
		
		double temp=0;
		for(int i=100;i<1000;i++){
			if(sigA.getXi(i)>temp){
				temp=sigA.getXi(i);
			}
		}
		if(temp==0)return 1;
		return temp;
	}
	
	public double freqP(double W){
		Input in=new Input(1,W,0);
		
		CTSignal sig=new CTSignal(1,in);
		Signal sigA=convolution(sig);
		
		double temp1,temp2;
		double diff1=9999,diff2=9999;
		temp1=temp2=0;
		double temp;
		for(int i=100;i<Signal.MAX-1;i++){
			//System.out.println("sig("+sig.getXi(i)+")");
			if(sig.getXi(i)>=0&&sig.getXi(i+1)<=0&&diff1>sig.getXi(i)-0){
				temp1=sig.getXi(i)/(sig.getXi(i)-sig.getXi(i+1))+i;
				diff1=sig.getXi(i)-0;
				
			}
		}
		for(int i=120;i<Signal.MAX-1;i++){
			//System.out.println("sigA("+sigA.getXi(i)+")");
			if(sigA.getXi(i)>=0&&sigA.getXi(i+1)<=0&&diff2>sigA.getXi(i)-0){
				temp2=sigA.getXi(i)/(sigA.getXi(i)-sigA.getXi(i+1))+i;
				diff2=sigA.getXi(i)-0;
				
			}
		}
		
		temp=(W*(temp2-temp1))%6.28;
		
		if(temp>3.14)temp-=6.28;
		if(temp<-3.14)temp+=6.28;
		return temp*(-1);
	}
}
