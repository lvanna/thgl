package thgl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ThreatID {
	
	static ArrayList<String[]> threat_list = new ArrayList<String[]>();
	static ArrayList<String[]> cut_data = new ArrayList<String[]>();
	static ArrayList<String[]> naST = new ArrayList<String[]>();
	static String t[];
	static String cutting[];
	static String[] adversary = new String[2]; 
	static String[][] projection_arr = new String[3][];
	static String[ ] lambda_arr = new String[5];
	
	public static void main(String[] args) throws IOException {
	      
		String line;
		FileReader fr = new FileReader("D:\\thgl\\thgl-master\\original_db_T.txt");
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
	    		projection_arr=projection(adver,i);
	    		lambda_arr=lambda(adver,i);
	    		if(projection_arr[0]!=null){ //投影不為空
	    			//naST++
	    			for(String lambda:lambda_arr){  //each lambda
	    				if(lambda!=null){
	    					String[ ] nast_arr = new String[5]; 
	    					if (naST.isEmpty()){		
	    						nast_arr[0]=lambda;
	    						nast_arr[1]=projection_arr[1][0];
	    						int st_count=0;
	    						int nast_count=0;
	    						for(int f=0; f<cut_data.size();f++){ //each t
	    							int s =0;
	    							int a=0;
	    							int n=0;
	    							for(int j=1; j<cut_data.get(f).length; j++){   //each location
	    								int n_count=0;
	    								if((cut_data.get(f)[j].substring(0, 1)).equals(adver)){
    										a+=1;
    									}
	    								for(String p:projection_arr[0]){
	    									if(cut_data.get(f)[j].equals(p)){
	    										s+=1;
	    									}else if(cut_data.get(f)[j].equals(lambda) && n_count==0){
	    										n+=1;
	    										n_count=1;
	    									}
	    								}
	    							}
	    							if((s-1)==Integer.valueOf(projection_arr[2][0]) && s==a){  //如果這一筆t滿足投影
	    								st_count+=1;				//st+1
	    								if(n!=0){								   		//如果這一筆t滿足投影，且包含lambda
	    									nast_count+=1;				  //nast+1
	    								}
	    							}
	    						}
	    						nast_arr[2]=Integer.toString(nast_count);
	    						nast_arr[3]=Integer.toString(st_count);	
	    						naST.add(nast_arr);					 //add naST
    					    }else{ //naST is not empty
    					    	int c=0;
    					    	for (int k=0; k<naST.size();k++){
    	    					    if(naST.get(k)[0].equals(lambda) && naST.get(k)[1].equals(projection_arr[1][0])){//update naST
    	    					    	naST.get(k)[2]=Integer.toString(Integer.valueOf(naST.get(k)[2])+1);
    	    					    	c=1;
    	    					    }
    	    					}
    					    	if(c==0){//add naST
    					    		nast_arr[0]=lambda;
    	    						nast_arr[1]=projection_arr[1][0];
    	    						int st_count=0;
    	    						int nast_count=0;
    	    						for(int f=0; f<cut_data.size();f++){ //each t
    	    							int s =0;
    	    							int a=0;
    	    							int n=0;
    	    							for(int j=1; j<cut_data.get(f).length; j++){   //each location
    	    								int n_count=0;
    	    								if((cut_data.get(f)[j].substring(0, 1)).equals(adver)){
        										a+=1;
        									}
    	    								for(String p:projection_arr[0]){
    	    									if(cut_data.get(f)[j].equals(p)){
    	    										s+=1;
    	    									}else if(cut_data.get(f)[j].equals(lambda) && n_count==0){
    	    										n+=1;
    	    										n_count=1;
    	    									}
    	    								}
    	    							}
    	    							if((s-1)==Integer.valueOf(projection_arr[2][0]) && s==a){  //如果這一筆t滿足投影
    	    								st_count+=1;				//st+1
    	    								if(n!=0){								   		//如果這一筆t滿足投影，且包含lambda
    	    									nast_count+=1;				  //nast+1
    	    								}
    	    							}
    	    						}
    	    						nast_arr[2]=Integer.toString(nast_count);
    	    						nast_arr[3]=Integer.toString(st_count);	
    	    						naST.add(nast_arr);
    					    	}
    					    }
	    				}
	    			}
	    		}
	    	}
	    }
	    
	    for(int i=0; i<naST.size(); i++){
	    	System.out.println();
	    	for(int j=0; j<naST.get(i).length; j++){
	    		System.out.print(" "+ (naST.get(i)[j]));
	    	}
	    }
		
	} //end main
	
	public static String[] lambda(String a,int t){
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
	
	public static String[][] projection(String a,int t){
		String arr[][]; 
		arr=new  String[3][];
		arr[0]=new String[5];
		arr[1]=new String[1];
		arr[2]=new String[1];
		String x[];
		x = cut_data.get(t);
		int c=0;
    	for(int j=1; j<x.length; j++){
    		if(((x[j]).substring(0, 1)).equals(a)){
    			arr[0][c]=x[j];
    			if(arr[1][0]==null){
    				arr[1][0]=x[j];
    			}else{
    				arr[1][0]=arr[1][0]+x[j];
    			}
    			c+=1;
    		}
    	}
    	arr[2][0]= Integer.toString(c-1);
        return arr;
    }
	
}
