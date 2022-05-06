


import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, ParseException, InterruptedException  {
        ReadCSVThread thread=new ReadCSVThread();
        thread.start();
 	   
        Thread.sleep(3000);

        Scanner sc=new Scanner(System.in);
        String color,gender,size;
        String choiceCode;
        ArrayList<String> CSV_data=ReadCSVThread.getCSVData();     


        System.out.print("Enter Color for Tshirt  : ");
        color=sc.nextLine();
        color=color.toUpperCase();
        System.out.print("Please enter the Gender  : ");
        gender=sc.nextLine();
        gender=gender.toUpperCase();
        System.out.print("Please enter Size   : ");
        size=sc.nextLine();
        size=size.toUpperCase();
        System.out.print("Enter Output Preference :   1. Price \t 2. Rating  \t 3. Both: ");
        choiceCode=sc.nextLine();
        choiceCode=choiceCode.toUpperCase();
        CSV_data=ReadCSVThread.getCSVData();
        DataController fc=new DataController();

        fc.searchData(CSV_data,color,gender,size);
    
      fc.upsizeView(choiceCode);
    }
}

class DataController {
    ArrayList<Model> TshirtList=new ArrayList<Model>();
    ArrayList<String> arr;
    DataView view=new DataView();

    public void searchData(ArrayList<String> CSV_data, String color, String gender, String size) throws FileNotFoundException
    {
    	SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
   	    Session session = sessionFactory.openSession();
   	    Transaction tnx=session.beginTransaction();

   	    Query q=session.createQuery("from CSVData ");
   	    List<CSVData> lst=q.list();
   	    List<CSVData> res=new ArrayList<CSVData>();
   	    for(CSVData l:lst)
   	    {
   	    	
   	    	
   	    	if (l.color.equals(color) && l.gender.equals(gender) &&  (l.size.equals(size)) ) 
   	    	{
                Model model = new Model(l.TshirtId, l.name, l.color, l.gender, l.size, l.price,l.rating);
                TshirtList.add(model);	
//  	    
   	    	}
   	    }
   	    tnx.commit();
    }

    public void upsizeView(String choiceCode)
    {
        if(choiceCode.equals("PRICE"))
        {
        	TshirtList.sort( Comparator.comparing(Model::getprice));
        }
        else if(choiceCode.equals("RATING"))
        {
        	TshirtList.sort( Comparator.comparing(Model::getrating));
        }
        else if(choiceCode.equals("BOTH"))
        {
            Collections.sort(TshirtList, Comparator.comparing(Model::getprice)
                    .thenComparing(Model::getrating));
          
        }
        else {
        	System.out.println("Wrong Input");
        	return;
        }
        view.viewTshirt(TshirtList);

    }
}

class Model {

    private String TshirtId;
    private String name;
   
	private String color;
    private String gender;
    private String size;
    private String price;
    private String rating;
    

    public Model(){}

    public Model(String TshirtId,String name, String color, String gender, String size, String price, String rating) {
        this.TshirtId = TshirtId;
        this.name=name;
        this.color = color;
        this.gender = gender;
        this.size = size;
        this.price = price;
        this.rating = rating;
    }
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    public String getTshirtId()
    {
        return TshirtId;
    }
    public void setTshirtId(String TshirtId)
    {
        this.TshirtId=TshirtId;
    }

    public String getcolor()
    {
        return color;
    }
    public void setcolor(String color)
    {
        this.color=color;
    }

    public String getgender()
    {
        return gender;
    }
    public void setgender(String gender)
    {
        this.gender=gender;
    }
    public String getsize()
    {
        return size;
    }
    public void setsize(String size)
    {
        this.size=size;
    }

    public String getprice()
    {
        return price;
    }
    public void setprice(String price)
    {
        this.price=price;
    }

    public String getrating()
    {
        return rating;
    }
    public void setrating(String rating)
    {
        this.rating=rating;
    }

}


class DataView {

    public void viewTshirt(ArrayList<Model> TshirtList)
    {
        System.out.println("\n \t\t\t\t\t                          ***** Tshirt Information *****        ");
        System.out.println("|      ID        |                     Name                      |     Color     |     Gender    |  Size  |    price    | rating |");
        for(Model f:TshirtList)
        {
            System.out.print("| "+f.getTshirtId());
            System.out.print("\t |\t"+f.getName());
            System.out.print("\t |\t"+f.getcolor());
            System.out.print("\t |\t"+f.getgender());
            System.out.print("\t |\t"+f.getsize());
            System.out.print(" | "+f.getprice());
            System.out.println("\t|  "+f.getrating()+"\t |");
        }
        if(TshirtList.isEmpty())
        {
            System.out.println("Tshirt Not Available.");
        }
    }
}
