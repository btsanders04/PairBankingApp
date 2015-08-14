import java.text.NumberFormat;
import java.util.*;

public class Member {
	private String id;
	private String name;
	private int NoOfAccounts=0;

	public Member(String user,String name) {
		this.id = user;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public int getNoOfAccounts() {
		return NoOfAccounts;
	}

	public void setNoOfAccounts(int noOfAccounts) {
		NoOfAccounts = noOfAccounts;
	}
	
}

