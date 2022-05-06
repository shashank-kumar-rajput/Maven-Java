


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


public class ReadCSVThread extends Thread
{
	private static final String src_dir="src/main/resources";
	private static ArrayList<String> list_of_file = new ArrayList<String>();
	
	static public ArrayList<String> getAllFiles()
	{
		 try 
		 {
	            File folder = new File(src_dir);
	            for (File file : folder.listFiles()) 
	            {
	                String name = file.getName();
	                if (!list_of_file.contains(name)) 
	                {
	                	list_of_file.add(name);
	                }
	            }
	     } 
		 catch (Exception e) 
		 {
	            e.printStackTrace();
	     }
	        return list_of_file;
	}
	
	static ArrayList<String> csv_data=new ArrayList<String>();
	static ArrayList<String> arr;
	static public ArrayList<String> readCSV()
	{
		if (list_of_file.size() > 0)
		{
			//create BufferedReader to read csv file
			for (int i = 0; i < list_of_file.size(); i++ )
			{
				try {					
					BufferedReader br = new BufferedReader(new FileReader(src_dir+"/"+list_of_file.get(i)));
					
					String str_line ="";
					br.readLine();
					while((str_line = br.readLine()) != null) {
						
						if (!csv_data.contains(str_line)) 
		                {
							StringTokenizer token = new StringTokenizer(str_line, "|");
			                arr = new ArrayList(str_line.length());
			                while (token.hasMoreTokens()) {
			                    arr.add(token.nextToken());
			                }
			                Object[] objArr = arr.toArray();
			                
			                
			                // convert Object array to String array
			                String[] str = Arrays.copyOf(objArr, objArr.length,String[].class);
			                
			                CSVData row=new CSVData();
			                row.setTshirtId(str[0]);
			                row.setColor(str[1]);
			                row.setName(str[1]);
			                row.setColor(str[2]);
			                row.setGender(str[3]);
			                row.setSize(str[4]);
			                row.setPrice(str[5]);
			                row.setRating(str[6]);
			                
			                
			                SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			                
			                
			          	    Session session = sessionFactory.openSession();
			          	    Transaction tnx=session.beginTransaction();
			          	  
			          	    session.save(row);
			          	    tnx.commit();
			          	
			          	
			                
			                
			                
							csv_data.add(str_line);
		                }
						}
				}
				catch(Exception e)
				{
					System.out.println(e.getMessage());
					System.out.println("Error occurs");
				}		
			}	
		}
		return csv_data;
	}
	
	public static ArrayList<String> getCSVData()
	{
		return csv_data;
	}
	
	@Override
	public void run()
	{
		while(true)
		{
			getAllFiles();
			readCSV();
			try
			{
				Thread.sleep(10000);//sleep for 10 second
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}	
}