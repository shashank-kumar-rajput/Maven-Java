import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc=new Scanner(System.in);
        String color,gender,size;
        String choiceCode;

        System.out.print("Enter Color for Tshirt  : ");
        color=sc.nextLine().toUpperCase();
        System.out.print("Enter Gender  : ");
        gender=sc.nextLine().toUpperCase();
        System.out.print("Enter size   : ");
        size=sc.nextLine().toUpperCase();
        System.out.print("Enter Output Preference :   1. Price \t 2. Rating  \t 3. Both: ");
        choiceCode=sc.nextLine().toUpperCase();

        DataController fc=new DataController();

        fc.searchData("src/main/resources/worksheet_adidas.csv",color,gender,size);
      fc.searchData("src/main/resources/worksheet_adidas.csv",color,gender,size);
       fc.searchData("src/main/resources/worksheet_adidas.csv",color,gender,size);
      fc.upsizeView(choiceCode);
    }
}

class DataController {
    ArrayList<Model> TshirtList=new ArrayList<Model>();
    ArrayList<String> arr;
    DataView view=new DataView();

    public void searchData(String filename, String color, String gender, String size) throws FileNotFoundException
    {
        Scanner sc = new Scanner(new File(filename));
        while(sc.hasNext()) {
            String line = sc.nextLine().toUpperCase().toString();
            if (!line.isEmpty()) {
                StringTokenizer token = new StringTokenizer(line, "|");
                arr = new ArrayList<>(line.length());
                while (token.hasMoreTokens()) {
                    arr.add(token.nextToken());
                }
               
                if (arr.get(2).equals(color) && arr.get(3).equals(gender) && arr.get(4).equals(size)) {
                    Model model = new Model(arr.get(0), arr.get(1), arr.get(2), arr.get(3),arr.get(4), arr.get(5), arr.get(6));
                    TshirtList.add(model);
                }
            }
        }
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
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
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
 class PriceComparator implements Comparator<Model>
{
    public int compare(Model o1, Model o2)
    {
       return o1.getName().compareTo(o2.getName());
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
