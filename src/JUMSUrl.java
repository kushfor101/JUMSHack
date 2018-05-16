import java.net.*;
import java.util.*;
import javax.swing.*;
import java.awt.Desktop;

public class JUMSUrl {
	
	public String url;
	public int ind1,ind2;
	
	public void openWebsite() {
		System.out.println("Found ");
		System.out.println(url);
		
		if(Desktop.isDesktopSupported()){
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
		else{
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("xdg-open " + url);
            } 
            catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
		
	}
	
	public int acceptChoice()throws Exception {
		
		int fl=0;
		Scanner sc = new Scanner(System.in);
		System.out.println();
		System.out.println("Choose Department : ");
		System.out.println("1. CSE");
		System.out.println("2. ETCE");
		System.out.println("3. ELECTRICAL");
		System.out.println("4. MECHANICAL");
		System.out.println("5. CIVIL");
		System.out.println("6. IT");
		System.out.println("7. Exit");
		ind1=sc.nextInt();
		if(ind1==7)
			return 1;
		System.out.println("Choose Year : ");
		System.out.println("1. FIRST");
		System.out.println("2. SECOND");
		System.out.println("3. THIRD");
		System.out.println("4. FOURTH");
		ind2=sc.nextInt();
		if(ind1==7)
			fl=1;
		return fl;
		
	}
	
	public void setUrl() {
		int fl=0;
		url = "http://juadmission.jdvu.ac.in/jums_exam/student_odd/result/view_print_result_be.jsp?exam_roll=";
		if(ind1==1)
			url+="CSE";
		else if(ind1==2)
			url+="ETC";
		else if(ind1==3)
			url+="ELE";
		else if(ind1==4)
			url+="MEC";
		else if(ind1==5)
			url+="CIV";
		else if(ind1==6)
			url+="ITE";
		else 
			fl=1;
		url+="18";
		if(ind2==1)
			url+="1";
		else if(ind2==2)
			url+="3";
		else if(ind2==3)
			url+="5";
		else if(ind2==4)
			url+="7";
		url+="001";
	}
	
	public void getGradeCard()throws Exception {
		
		Scanner sc=new Scanner(System.in);
		String name;
		System.out.println("Enter name to search in CAPS");
		name=sc.nextLine();
		
		int fl=0;
		int i=1;
		while(i<=8)
		{	
			fl=0;
			URL u = new URL(url);
			Scanner s;
			
			s=new Scanner(u.openStream());
			while(s.hasNext())
			{
				String ss=s.nextLine();
				if(ss.indexOf(name)!=-1)
				{
					fl=1;
				}
			}	
			i++;
			if(fl==1) {
				openWebsite();
			}
			url=url.substring(0,url.length()-1)+String.valueOf(i);
		}
		
		while(i<=80)
		{	
			fl=0;
			URL u = new URL(url);
			Scanner s;
			
			s=new Scanner(u.openStream());
			while(s.hasNext())
			{
				String ss=s.nextLine();
				if(ss.indexOf(name)!=-1)
				{
					fl=1;
				}
			}	
			if(fl==1) {
				openWebsite();
			}
			i++;
			url=url.substring(0,url.length()-2)+String.valueOf(i);
		}

	}
	
	public void getTopper()throws Exception {
		
		ArrayList<String> arr= new ArrayList<String>();
		
		double max = 1.0;
		int i=1;
		while(i<=8)
		{	
			if(i%5==0)
				System.out.println("....Checking");
			URL u = new URL(url);
			Scanner s;
			
			s=new Scanner(u.openStream());
			int lineno=0;
			
			while(s.hasNext())
			{
				String ss=s.nextLine();
				lineno++;
				if(lineno==415)
				{
					String nw = "";
					int i1 = ss.indexOf(";");
					int i2 = ss.indexOf("<",i1);
					nw = ss.substring(i1+2,i2);
					if(nw.trim().length()>0) {
						double sgpa = Double.parseDouble(nw);
						if(sgpa==max)
							arr.add(url);
						else if(sgpa>max)
						{
							max=sgpa;
							arr.clear();
							arr.add(url);
						} 
							
					}
					break;
				}
			}	
			i++;
			url=url.substring(0,url.length()-1)+String.valueOf(i);
		}
		
		while(i<=80)
		{	

			if(i%5==0)
				System.out.println("....Checking");
			URL u = new URL(url);
			Scanner s;
			
			s=new Scanner(u.openStream());
			int lineno=0;
			while(s.hasNext())
			{
				lineno++;
				String ss=s.nextLine();
				if(lineno==415)
				{
					String nw = "";
					int i1 = ss.indexOf(";");
					int i2 = ss.indexOf("<",i1);
					nw = ss.substring(i1+2,i2);
					if(nw.trim().length()>0) {
						double sgpa = Double.parseDouble(nw);
						if(sgpa==max)
							arr.add(url);
						else if(sgpa>max)
						{
							max=sgpa;
							arr.clear();
							arr.add(url);
						}  
					}
					break;
				}
			}	
			i++;
			url=url.substring(0,url.length()-2)+String.valueOf(i);
		}
		
		for(int j=0;j<arr.size();j++) {
			url = arr.get(j);
			openWebsite();
		}

	}
	
	public static void main(String args[])throws Exception{
		
		JUMSUrl ju = new JUMSUrl();
		
		Scanner sc=new Scanner(System.in);
		int fl=0;
		while(fl==0) {
			fl=ju.acceptChoice();
			if(fl==1)
				break;
			ju.setUrl();
			
			System.out.println("1. Search for a grade card");
			System.out.println("2. Get to know the topper/(s)");
			int inp = sc.nextInt();
			if(inp==2)
				ju.getTopper();
			else
				ju.getGradeCard();
		}
	}
}
