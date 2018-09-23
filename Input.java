//import java.util.Arrays;

//x(t) expression "A[]*sin(w[]*t+phi[])+E[]*e^(exp[]*t)+L[]*log(log[]*t)+I[]*imp(t+imp[])+S[]*stp(t+stp[])"
public class Input {
	public static final int MAX=100;
	
	
	private double[] A=new double[MAX];
	private double[] w=new double[MAX];
	private double[] phi=new double[MAX];
	private double[] E=new double[MAX];
	private double[] exp=new double[MAX];
	private double[] L=new double[MAX];
	private double[] log=new double[MAX];
	private double[] I=new double[MAX];
	private double[] imp=new double[MAX];
	private double[] S=new double[MAX];
	private double[] stp=new double[MAX];
	
	private int[] magnitude=new int[11];
	
	Input(){
		for(int i=0;i<5;i++){
			magnitude[i]=0;
		}
		//Arrays.fill(w, 2);
	}
	
	Input(double A,double w,double phi){
			magnitude[0]=1;
		for(int i=1;i<5;i++)
			magnitude[i]=0;
		this.A[0]=A;
		this.w[0]=w;
		this.phi[0]=phi;
	}
	
	Input(double[] A,double[] w,double[] phi){
			magnitude[0]=A.length;
		for(int i=1;i<5;i++)
			magnitude[i]=0;
		for(int i=0;i<magnitude[0];i++){
			this.A[i]=A[i];
			this.w[i]=w[i];
			this.phi[i]=phi[i];
		}
	}
	
	public double cal(double t){
		double temp=0;
		
		for(int i=0;i<5;i++){
			for(int j=0;j<magnitude[i];j++){
				switch(i){
				case 0:
					temp+=A[j]*Math.sin(w[j]*t+phi[j]);
					break;
				case 1:
					temp+=E[j]*Math.exp(exp[j]*t);
					break;
				case 2:
					temp+=L[j]*Math.log(log[j]*t);
					break;
				case 3:
					temp+=I[j]*Signal.imp(t+imp[j]);//may cause error
					break;
				case 4:
					temp+=S[j]*Signal.stp(t+stp[j]);
				}
			}
		}
		
		return temp;
	}
}
