public class Node {
	private float[] values;
	private int group;
	public static final int NO_GROUP = Integer.MAX_VALUE;
	

	public Node(float[] values){
		this.values =values;
		this.group = NO_GROUP ;
	}
	
	public  Node(float[] values, int group){
		this.values = values;
		this.group = group;
		
	}
	public boolean hasGroup(){
		if(this.group == NO_GROUP){
			return false;
		}
		else{
			 return true;
		}
		
	}
	public boolean equals(Node other){
	
		for( int i=0; i< this.values.length; i++){
			if( this.values[i] != other.values[i]){
				return false;
			}
			if( this.group != other.group){
				return false;
			}	
		}
		return true;
	}
	
	public float[] getValues(){
		return this.values;
	}
	public int getGroup(){
		return this.group;
	}
	
	
	

}
