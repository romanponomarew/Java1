package DataBase;

//public class DataBase {
//}


import java.sql.*;
import java.io.*;
import java.util.Calendar;
import java.util.Date;


class DML{
    public Connection con;
    public DML(){
        try{
            //con=DriverManager.getConnection("jdbc:Mysql://localhost:5432/MyDB","postgres","1234");
            con = DriverManager.getConnection ("jdbc:mysql://localhost:3306/mydb", "root", "password");
            //("jdbc:MySQL://localhost/sakila», userName, "password"");
            Statement statement = con.createStatement();
            System.out.println("Good");
        }
        catch(Exception e){}
    }

    public void insert(int Id, String name, int distance, String startDate, String endDate){
        try{PreparedStatement query=con.prepareStatement("Insert into table1(Id,name,distance,startDate,endDate) values (?,?,?,?,?)");
            query.setInt(1, Id);
            query.setString(2, name);
            query.setInt(3,distance);
            query.setString(4, startDate);
            query.setString(5,endDate);

            query.executeUpdate();
        }
        catch(Exception ex){
            System.out.println(ex);
        }
    }
    public void update(int id,String parametr)
    {
        try{
            PreparedStatement updateQuery=null;
            if ("endDate".equals(parametr))
            {
                updateQuery=con.prepareStatement("Update table1 set endDate=? where id="+id);

            }
            if ("startDate".equals(parametr))
            {updateQuery=con.prepareStatement("Update table1 set startDate=? where id="+id);}
            if ("distance".equals(parametr))
            {updateQuery=con.prepareStatement("Update table1 set distance=? where id="+id);}

            updateQuery.executeUpdate();}
        catch (Exception e){
            System.out.println(e);
        }

    }}
class DQLandDML extends DML{
    public DQLandDML(){
        super();
    }
    public void show(){
        try{PreparedStatement query=con.prepareStatement("Select * from table1 order by 1");
            ResultSet result=query.executeQuery();

            while(result.next()){
                System.out.println(result.getInt(1)+" "+result.getString(2)+" "+result.getString(3)+" "+result.getString(4)+" "+result.getString(5));
                //result.next();
            }
            result.close();}
        catch(Exception ex){
            System.out.println(ex);
        }
    }
    public void show (int Id){
        try{PreparedStatement query=con.prepareStatement("Select * from table1 where Id='"+Id+"' order by 1");
            ResultSet result=query.executeQuery();

            while(result.next()){
                System.out.println(result.getInt(1)+" "+result.getString(2)+" "+result.getString(3)+" "+result.getString(4)+" "+result.getString(5));

            }
            result.close();}
        catch(Exception ex){
            System.out.println(ex);
        }
    }
}
public class DataBase {

    public static String getString() throws Exception{
        BufferedReader br=new BufferedReader(new InputStreamReader (System.in));
        return br.readLine();
    }
    public static short getShort() throws Exception{
        return  Short.parseShort(getString());
    }
    public static int getInt() throws Exception{
        return  Integer.parseInt(getString());
    }

    public static void main(String[] args) throws Exception// throws SQLException
    {
        // TODO code application logic here
        DQLandDML db=new DQLandDML();


        for (;;){
            try{System.out.println("Выберите действие:"+"\n"+
                    "1.ShowAll"+"\n"+"2.insert"+"\n"+"3.Update"+
                    "\n"+"4.Show"+"\n"+"5.Exit");
                short check=getShort();
                if(check==1)
                    db.show();
                if(check==2){
                    System.out.print("Введите id: ");
                    int id=getInt();

                    System.out.print("Введите название: ");
                    String name1=getString();

                    System.out.print("Введите дистанцию: ");
                    int distance=getInt();
                    System.out.print("Введите дату начала: ");
                    String startDate=getString();
                    System.out.print("Введите дату конца: ");
                    String endDate=getString();

                    db.insert(id,name1, distance, startDate, endDate);
                }
                if (check==3){
                    System.out.print("Введите id проекта: ");
                    int id=getInt();
                    System.out.print("Выберите параметр: ");
                    String parametr=getString();
                    System.out.print("Измените: ");
                    db.update(id,parametr);
                }
                if (check==4){
                    System.out.print("Введите id проекта: ");
                    db.show(getInt());
                }
                if (check==5){
                    System.exit(0);
                }
            }
            catch (Exception e){
                System.out.println(e);
            }

        }

    }
}
