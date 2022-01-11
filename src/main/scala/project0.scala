import java.sql.DriverManager
import java.sql.Connection
import java.sql.SQLException;
import java.util.Scanner
import java.io.File
import java.io.PrintWriter
 
 object project0 {
       
       
      
       
       val driver = "com.mysql.cj.jdbc.Driver"
       val url = "jdbc:mysql://localhost:3306/MoviesAvailable"
       val username = "sqluser"
       val password = "password" 
       var connection:Connection = null
       val log = new PrintWriter(new File("Flicks.log"))
       Class.forName(driver)
       connection = DriverManager.getConnection(url, username, password)
       val statement = connection.createStatement()


  def add (connection:Connection) : Unit ={

    //try {
      var scan = new Scanner(System.in)
        
      println("ENTER TITLE OF MOVIE ")
      var movieName = scan.nextLine();
   
      println("ENTER GENRE OF MOVIE ")
      var genre  = scan.nextLine();
                
      println("ENTER THE YEAR THE MOVIE WAS RELEASED ")
      var releaseYear = scan.nextInt();
      scan.nextLine()
        
      println("WHO IS THE LEADING ROLE OF THE MOVIE")
      var leadRole = scan.nextLine();

        
      var query = "INSERT INTO MoviesInDataBase (MovieName, Genre, ReleaseYear, LeadRole) VALUES ('"+movieName+"','"+genre+ "' ,"+releaseYear+" , '"+leadRole+"');"
      println(query)
      
      var reslt1 = statement.executeUpdate(query);
      
      
      
     ///val resultSet = statement.executeQuery("SELECT * FROM MoviesInDataBase")
     //while (resultSet.next() ) {
        //print(resultSet.getString(1))
        //if (movieName==resultSet){
          //throw new BadUserInputExceptions
        //}
      //}
    
      
      log.write("Executing 'INSERT INTO MoviesInDataBase (MovieName, Genre, ReleaseYear, LeadRole) VALUES ('"+movieName+"','"+genre+ "' ,"+releaseYear+" , '"+leadRole+"');")

      //}catch{
        //case bui: BadUserInputExceptions => println("THIS MOVIE IS ALREADY IN YOUR LIBRARY")
    //}

      
    
    }

    
    
    
    def view (connection:Connection): Unit = {
     
      val resultSet = statement.executeQuery("SELECT * FROM MoviesInDatabase")
      log.write("Executing 'SELECT * FROM MoviesInDatabase';")
      while (resultSet.next() ) {

      print(resultSet.getString(1) + " " + resultSet.getString(2) + " " + resultSet.getString(3) + " " + resultSet.getString(4))
      println()

      }
     }

     
     def delete (connection:Connection) : Unit = {
     var scan = new Scanner(System.in)
     println("  WARNING YOU ARE ABOUT TO DELETE A MOVIE FROM YOUR LIBRARY")
     println("_______________________________________________________________")
     println("WHAT MOVIE WOULD YOU LIKE TO DELETE FROM YOUR LIBRARY?")
     
    
     var moviedeleted = scan.nextLine();

     val deleted = statement.executeUpdate("DELETE FROM MoviesInDatabase WHERE MovieName = ('"+moviedeleted+"');")
     log.write("Executing 'DELETE FROM MoviesInDatabase WHERE MovieName = ('"+moviedeleted+"');")
    

     
  }
     
     def update(connection:Connection) : Unit = {
    
    var scan = new Scanner(System.in)
     println("******WARNING******")
     println()
     println("UPDATING MOVIE WILL REPLACE THE OLD VERSION OF THE MOVIE!")
     println("WHAT IS THE MOVIE YOU WOULD LIKE TO UPDATE?")
     var update = scan.nextLine();

     println("WHAT IS THE NEW RELEASE YEAR OF THE MOVIE?")
     var year = scan.nextLine();

     println("WHO IS THE NEW LEAD ACTOR?")
     var newLead = scan.nextLine();
     
     val update1 = statement.executeUpdate("UPDATE MoviesInDatabase SET ReleaseYear =  ('"+year+"') , LeadRole = ('"+newLead+"')  WHERE MovieName = ('"+update+"');")
     log.write("Executing 'UPDATE MoviesInDatabase SET ReleaseYear =  ('"+year+"') , LeadRole = ('"+newLead+"')  WHERE MovieName = ('"+update+"');")
    
    
 
    
    
    
    
    
    
    
    }


     

   def main(args: Array[String]):Unit = {
    
     val driver = "com.mysql.cj.jdbc.Driver"
     val url = "jdbc:mysql://localhost:3306/MoviesAvailable"
     val username = "sqluser"
     val password = "password" 
     
     var connection:Connection = null
     
     

     var scan = new Scanner(System.in)  
     println(" WELCOME TO YOUR MOVIE LIBRARY")
     println("---------------------------------")
     println("WOULD YOU LIKE TO ADD, UPDATE, VIEW, OR DELETE A MOVIE FROM YOUR LIBRARY?")
     var decision = scan.nextLine().toUpperCase;


     if (decision == "ADD"){
      add(connection:Connection);
     } 

     if (decision == "VIEW"){
      view(connection:Connection);
     }

     if (decision == "DELETE"){
      delete(connection:Connection);
     }

     if (decision == "UPDATE"){
      update(connection:Connection);
     }



       
       
       
       
       
       
       
       
       
       
       try {
      
       Class.forName(driver)
       connection = DriverManager.getConnection(url, username, password)
       val statement = connection.createStatement()
       
       
       //while ( resultSet.next() ) {
        //print(resultSet.getString(1) + " " + resultSet.getString(2) + " " + resultSet.getString(3) + " " + resultSet.getString(4))
       // println()
       //}
     } catch {
       case e: Exception => e.printStackTrace
     }
     connection.close()
     log.close()
   }


 }