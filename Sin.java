
public class Sin {
	public double A; 
	public double W; 
	public double phi;
	
	public Sin(double A,double W,double phi){
		this.A=A;
		this.W=W;
		this.phi=phi;
	}
	
	public boolean equals(Object other){
		return ((this.W==((Sin)other).W)&&(this.phi==((Sin)other).phi));
	}
	
	public String toString(){
		return "A="+A+",W="+W+",phi="+phi;
	}
}
