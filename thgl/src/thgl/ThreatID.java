package thgl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ThreatID {
	
	static ArrayList<String[]> threat_list = new ArrayList<String[]>();
	static ArrayList<String[]> cut_data = new ArrayList<String[]>();
	static String t[];
	static String cutting[];
	static String[] adversary = new String[2]; 
	
	public static void main(String[] args) throws IOException {
		
		String line;
		FileReader fr = new FileReader("D:\\thgl\\original_db_T.txt");
		BufferedReader br=new BufferedReader(fr);
	    while ((line=br.readLine()) != null) {
	        cutting = line.split(" ");
	        cut_data.add(cutting);
	    }
	    br.close();
	    fr.close();
	    
	    System.out.println("Original dataset T");
	    for(int i=0; i<cut_data.size();i++){
	    	t = cut_data.get(i);
	    	for(int j=0; j<t.length; j++){
	    		System.out.print(" "+ (cut_data.get(i)[j]));
	    	}
	    	System.out.println(" ");
	    }
	    System.out.println(" ");
	    
	    adversary[0]="a";
	    adversary[1]="b";
	    
	    for(int i=0; i<cut_data.size();i++){ //each t
	    	t = cut_data.get(i);
	    	System.out.println();
	    	System.out.println("第"+i+"筆");
	    	for(String adver:adversary){ //each adversary
	    		System.out.print("對手"+adver);
	    		String[ ] arr = new String[5]; 
	    		arr=projection(adver,i);
	    		if(arr[0]!=null){ //投影不為空
	    			//print
	    			System.out.println("投影:");
	    			for(String str:arr){
	    				if(str!=null){
	    					System.out.print(str+" ");
	    				}
	    			}
	    			System.out.println();
	    			
//	    			for(){ //naST++
//	    				
//	    			}
	    			
	    		}
	    	}
	    }
		
	} //end main
	
	public static String[] projection(String a,int t){
		String[ ] arr = new String[5]; 
		String x[];
		x = cut_data.get(t);
		int c=0;
    	for(int j=1; j<x.length; j++){
    		if(!((x[j]).substring(0, 1)).equals(a)){
    			arr[c]=x[j];
    			c+=1;
    		}
    	}
        return arr;
    }
	
}
