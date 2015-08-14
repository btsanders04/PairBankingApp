import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Simple Java Program to connect Oracle database by using Oracle JDBC thin driver
 * Make sure you have Oracle JDBC thin driver in your classpath before running this program
 * @author
 */
public class BankDBQuery {
	

     //properties for creating connection to Oracle database
	public static Connection conn = null;
	 
    public static void openConnection(){
		String url = "jdbc:oracle:thin:testuser/password@localhost"; 
    	 Properties props = new Properties();
    	 props.setProperty("user", "testdb");
         props.setProperty("password", "password");
         try {
			conn = DriverManager.getConnection(url,props);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
	public void updateDB(String sql)throws SQLException {
	        //URL of Oracle database server
	       

	        //String sql ="select cust_last_name,cust_city,cust_state from demo_customers";

	        //creating PreparedStatement object to execute query
	        PreparedStatement preStatement = conn.prepareStatement(sql);
	    
	        preStatement.setQueryTimeout(10);
	        preStatement.executeUpdate(); 
	       
	      
	    }
	
    public ResultSet getFromDB(String sql)throws SQLException {
       
        PreparedStatement preStatement = conn.prepareStatement(sql);
        ResultSet result = preStatement.executeQuery(); 
        return result;
      
    }
    /**
     * 
     * @param id
     * @return member else return null if member does not exist
     */
    public Member getUser(String id){ 
      	ResultSet result;
      	Member member = null;
		try {
			String sql = "select * from member where member_id = '" + id + "'";
			result = getFromDB(sql);
			
			result.next();
			String user_id = result.getString("member_id");
			String name = result.getString("name");
			int noofAccounts = result.getInt("NoOfAccounts");
			member = new Member(user_id,name);
			member.setNoOfAccounts(noofAccounts);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
    	return member;
    }
        
    public void createUser(Member user){ 
		try {
			String sql = "insert into member values('"+user.getId()+"','"+user.getName()+"',"
						+user.getNoOfAccounts()+")";
			updateDB(sql);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
    }
    
    public Account getAccount(String number){ 
    	Account a = null;
    	ResultSet result;
		try {
			String sql = "select * from account where member_id = '" + number + "'";
			result = getFromDB(sql);
			
			while(result.next()){
				String acctNum = result.getString("AccountNumber");
				int acctType = result.getInt("AccountType");
				double bal = result.getDouble("balance");
				String userId = result.getString("member_id");
				
				a = new Account(acctNum,userId,acctType);
				a.setBalance(bal);
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
    	return a;
    }
    
    public void storeAccount(Account account){
    	
    	try {
			String sql = "insert into account values('"+account.getNumber()+"',"
					+account.getAccType()+","+account.getBalance()+",'" + account.getUser_id() +"')";
 
			updateDB(sql);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
    }
    
    public void storeTransaction(Transaction transaction, Account account){
    	
    	try {
			String sql = "insert into transactions values(seq_transactions.nextval,'"+account.getNumber()+"', '"+
					transaction.getUser_id() +"'," + transaction.getAmount()+",TO_DATE('"+transaction.getDate()+
					"','mm/dd/yyyy'),0,"+transaction.getType()+")";
			updateDB(sql);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
    }
   
    public ArrayList<Transaction> getTransactions(Account account){
    	ResultSet result;
    	ArrayList<Transaction> transactions = new ArrayList<Transaction>();
    	try {
			String sql = "select type,amount,t_date from transactions where member_id = '"+ account.getNumber()+
					"'and status = 0 order by t_date";
			result = getFromDB(sql);
		while(result.next()){
			Transaction t = new Transaction(result.getInt("type"),result.getDouble("Amount"));
			t.setDate(result.getDate("t_date").toString());
			transactions.add(t);
		}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
    	return transactions;
    }
    //must process first
    public void updateTransactionStatus(Account account){
    	
    	try {
			String sql = "update transactions set status = 1 where member_id = '"+ account.getNumber() +"'";
			updateDB(sql);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
    }
    
    public boolean findAccount(String number){
    	ResultSet result;
    	try {
			String sql = "select count(member_id) as count from member_account where member_id = '"+ number +"'";
			result=getFromDB(sql);
			result.next();
			if(result.getInt("count")>0){
				return true;
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
    	return false;
    }
    
    public void updateBalance(Account account){
    	try {
			String sql = "update member_account set balance = " + account.getBalance() + " where member_id = '"+ account.getNumber() +"'";
			updateDB(sql);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
    }
    
    public void deleteAccount(String account){
    	try {
    		String sql = "delete from member_account where member_id = '"+ account +"'";
		//	String sql = "delete from member_account where member_id = '"+ account +"'";
			updateDB(sql);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
    }
    
    public void deleteTranscations(String account){
    	try{
    		String sql = "delete from transactions where member_id = '"+ account +"'";
    		updateDB(sql);
    	} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    
}