/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package firstjdbc;
import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;


class DML{
public Connection con;
public DML(){
try{
con=DriverManager.getConnection("jdbc:postgresql://localhost:5432/mydb","postgres","password");
        //Statement statement = con.createStatement();
System.out.println("Good");        
}
catch(Exception e){}
}

public void insert(String name,String startDate,int distance,String endDate){
try{PreparedStatement query=con.prepareStatement("Insert into projects (name,startDate,distance,endDate) values (?,?,?,?)");
    java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);//Перевод строки в дату

java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());  //Перевод даты в формат SQL-date
query.setString(1, name); //запросы 
      query.setDate(2,sqlDate);
      query.setInt(3,distance);
      java.util.Date utilDate2 = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);

java.sql.Date sqlDate2 = new java.sql.Date(utilDate2.getTime()); 
      query.setDate(4,sqlDate2);
      query.executeUpdate();
           }
catch(Exception ex){
System.out.println(ex);
}
}
public void update(int id,String column,String date) 
{
  try{  
PreparedStatement updateQuery=null;
if ("startDate".equals(column))
{
updateQuery=con.prepareStatement("Update projects set startDate=? where id="+id);
java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
updateQuery.setDate(1,sqlDate);
}

if ("endDate".equals(column))
{updateQuery=con.prepareStatement("Update projects set endDate=? where id="+id);
java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
updateQuery.setDate(1,sqlDate);
}

updateQuery.executeUpdate();}
  catch (Exception e){
  System.out.println(e);
  }

}
public void update (int id,int distance){
try{
PreparedStatement updateQuery=con.prepareStatement("Update projects set distance=? where id="+id);
updateQuery.setInt(1,distance);
updateQuery.executeUpdate();
}
catch(Exception e){
System.out.println(e);
}
}
public void update (int id,String name){
try{
PreparedStatement updateQuery=con.prepareStatement("Update projects set name=? where id="+id);
updateQuery.setString(1,name);
updateQuery.executeUpdate();
}
catch(Exception e){
System.out.println(e);
}
}
}
class DQL extends DML{//Наследую класс DML
public DQL(){
super();//
}
public void show(){
try{PreparedStatement query=con.prepareStatement("Select * from projects order by 1");
            ResultSet result=query.executeQuery();
            
            while(result.next()){//Идем по курсору(результат запроса) построчно
            System.out.println(result.getInt(1)+" "+result.getString(2)+" "+result.getDate(3)+" "+result.getInt(4)+" "+result.getDate(5));
            //result.next();
            }
            result.close();}
catch(Exception ex){
System.out.println(ex);
}
}
public void show (int id){
try{PreparedStatement query=con.prepareStatement("Select * from projects where id="+id+" order by 1");
            ResultSet result=query.executeQuery();
            
            while(result.next()){
            System.out.println(result.getInt(1)+" "+result.getString(2)+" "+result.getDate(3)+" "+result.getInt(4)+" "+result.getDate(5));
            
           }
            result.close();}
catch(Exception ex){
System.out.println(ex);
}
}
}
public class FirstJDBC {
public static String getString() throws Exception{
    BufferedReader br=new BufferedReader(new InputStreamReader (System.in));
    return br.readLine();
    }
    public static short getShort() throws Exception{
    return  Short.parseShort(getString());
    }
    public static int getInt() throws Exception{
    return  Integer.parseInt(getString());//конвертирую строку в целое цисло
    }
    /**
     * @param args
     * @param arg
     * @throws java.lang.Exception
     * @throws java.lang.Exceptions the command line arguments
     */
    public static void main(String[] args) throws Exception// throws SQLException 
    {
        // TODO code application logic here
        DQL db=new DQL();//Объект базы данных
        
        
        for (;;){
            try{System.out.println("Выберите действие:"+"\n"+
                    "1.Показ таблицы"+"\n"+"2.Вставка в таблицу"+"\n"+"3.Изменение данных по ключу"+
                    "\n"+"4.Поиск по ключу"+"\n"+"5.Выход");
           short check=getShort();
           if(check==1)
               db.show();
           if(check==2){
           System.out.print("Введите название проекта: ");    
           String name=getString(); 
           System.out.print("Введите дату начала: ");
           String startDate=getString();
           System.out.print("Введите длительность проекта: ");
           int val2=getInt();
           System.out.print("Введите дату завершения: ");
           String endDate=getString();
           db.insert(name,startDate,val2,endDate);
           }
           if (check==3){
               System.out.print("Введите id проекта: ");
               int id=getInt();
               System.out.print("Выберите колонку: ");
               String column=getString();
               System.out.print("Измените параметр: ");
               if ("startDate".equals(column) | "endDate".equals(column)){
                db.update(id,column,getString());
               }
               if ("distance".equals(column))
                   db.update(id, getInt());
               if ("name".equals(column))
                   db.update(id, getString());
               //System.out.print("Измените параметр: ");
          // db.update(id,subject,);
           }
           if (check==4){
           System.out.print("Введите id: ");
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
        
    
    

