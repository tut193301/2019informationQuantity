package s4.B193301; // Please modify to s4.Bnnnnnn, where nnnnnn is your student ID. 
import java.lang.*;
import s4.specification.*;
//import java.util.Arrays;
//import java.util.HashMap;

/* What is imported from s4.specification
package s4.specification;
public interface InformationEstimatorInterface{
    void setTarget(byte target[]); // set the data for computing the information quantities
    void setSpace(byte space[]); // set data for sample space to computer probability
    double estimation(); // It returns 0.0 when the target is not set or Target's length is zero;
// It returns Double.MAX_VALUE, when the true value is infinite, or space is not set.
// The behavior is undefined, if the true value is finete but larger than Double.MAX_VALUE.
// Note that this happens only when the space is unreasonably large. We will encounter other problem anyway.
// Otherwise, estimation of information quantity, 
}                        
*/

public class InformationEstimator implements InformationEstimatorInterface{
    // Code to tet, *warning: This code condtains intentional problem*
    byte [] myTarget; // data to compute its information quantity
    byte [] mySpace;  // Sample space to compute the probability
    private double [] dble;
    FrequencerInterface myFrequencer;  // Object for counting frequency
    

    byte [] subBytes(byte [] x, int start, int end) {
	// corresponding to substring of String for  byte[] ,
	// It is not implement in class library because internal structure of byte[] requires copy.
	byte [] result = new byte[end - start];
	for(int i = 0; i<end - start; i++) { result[i] = x[start + i]; };
	return result;
    }

    private void inDble(){
	if(this.dble==null){
	    double[] array = new double[this.myTarget.length];
	    for(int i=0;i<this.myTarget.length;i++){
		array[i]=iq(myFrequencer.subByteFrequency(0,i+1));
		for(int j=0;j<i;j++){
		    var Tmp = array[j]+iq(myFrequencer.subByteFrequency(j+1,i+1));
		    if(array[i]>Tmp)array[i]=Tmp;
		}
	    }
	    this.dble = array;
	}
    }

    // IQ: information quantity for a count,  -log2(count/sizeof(space))
    private double iq(int freq) {
	return  - Math.log10((double) freq / (double) mySpace.length)/ Math.log10((double) 2.0);
    }

    public void setTarget(byte [] target) {
	myTarget = target;
	this.dble=null;
	//dble = new double[target.length+1];
       	//Arrays.full(dble, Double.MAX_VALUE);
    }
    public void setSpace(byte []space) { 
	myFrequencer = new Frequencer();
	mySpace = space; myFrequencer.setSpace(space); 
    }

    public double estimation(){
	/*
	HashMap<String,Double> map = new HashMap<>();
	boolean [] partition = new boolean[myTarget.length+1];
	int np;
	np = 1<<(myTarget.length-1);
	// System.out.println("np="+np+" length="+myTarget.length);
	double value = Double.MAX_VALUE; // value = mininimum of each "value1".

	for(int p=0; p<np; p++) { // There are 2^(n-1) kinds of partitions.
	    // binary representation of p forms partition.
	    // for partition {"ab" "cde" "fg"}
	    // a b c d e f g   : myTarget
	    // T F T F F T F T : partition:
	    partition[0] = true; // I know that this is not needed, but..
	    for(int i=0; i<myTarget.length -1;i++) {
		partition[i+1] = (0 !=((1<<i) & p));
	    }
	    partition[myTarget.length] = true;

	    // Compute Information Quantity for the partition, in "value1"
	    // value1 = IQ(#"ab")+IQ(#"cde")+IQ(#"fg") for the above example
            double value1 = (double) 0.0;
	    int end = 0;
	    int start = end;
	    while(start<myTarget.length) {
		//System.out.println("("+start+","+end+")");
		 //System.out.write(myTarget[end]);
		end++;;
		while(partition[end] == false) { 
		     //System.out.write(myTarget[end]);
		    end++;
		}
		//System.out.println("("+start+","+end+")");
		//myFrequencer.setTarget(subBytes(myTarget, start, end));
		String key = new String(subBytes(myTarget, start, end));
		if(!map.containsKey(key)){
		    myFrequencer.setTarget(subBytes(myTarget, start, end));
		    map.put(key, iq(myFrequencer.frequency()));
		}
		value1 = value1 + map.get(key);
		start = end;
	    }
	     //System.out.println(" "+ value1);

	    // Get the minimal value in "value"
	    if(value1 < value) value = value1;
	}
	return value;
	*/
	/*
	if(myTarget == null || myTarget.length <= 0){
	    return 0.0;
	}
	if(mySpace==null || mySpace.length<=0){
	    return Double.MAX_VALUE;
	}

	int s=0; int e=1;
	for(e=1;e<=myTarget.length;e++){
	    double tmp=0;
	    for(int par=1;par<=e;par++){
		if(par==e){
		    myFrequencer.setTarget(subBytes(myTarget,s,e));
		    tmp=iq(myFrequencer.frequency());
		}else{
		    myFrequencer.setTarget(subBytes(myTarget,par,e));
		    tmp=dble[par]+iq(myFrequencer.frequency());
		}
		if(dble[e] > tmp){
		    dble[e]=tmp;
		}
	    }
	}
	return dble[e-1];
	*/
	this.myFrequencer.setTarget(this.myTarget);
	inDble();
	return this.dble[this.myTarget.length - 1];
    }

    public static void main(String[] args) {
	InformationEstimator myObject;
	double value;
	myObject = new InformationEstimator();
	myObject.setSpace("3210321001230123".getBytes());
	myObject.setTarget("0".getBytes());
	value = myObject.estimation();
	System.out.println(">0 "+value);
	myObject.setTarget("01".getBytes());
	value = myObject.estimation();
	System.out.println(">01 "+value);
	myObject.setTarget("0123".getBytes());
	value = myObject.estimation();
	System.out.println(">0123 "+value);
	myObject.setTarget("00".getBytes());
	value = myObject.estimation();
	System.out.println(">00 "+value);
    }
}
				  
				       

	
    
