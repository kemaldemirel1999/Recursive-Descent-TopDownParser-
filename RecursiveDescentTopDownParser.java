import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class RecursiveDescentTopDownParser 
{
	static String sentences;
	static String willPrintedString = "";
	static String s1,so1;
	static String e1,e2,eo1;
	static String l1,l2,lo1;
	static String so1_else,s1_else;
	static String e1_else,e2_else,eo1_else;
	static String l1_else,l2_else,lo1_else;
	static boolean isThereElse = false;
	public static String parseFile(String in)
	{
		Scanner inputStream = null;
		String temp = "";
		try
		{
			inputStream = new Scanner(new FileInputStream(in));
			while(inputStream.hasNextLine())
			{
				temp += inputStream.nextLine();
				//temp += '\n';
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		inputStream.close();
		return temp;
	}
	public static boolean isItStart(String s)
	{
		willPrintedString += "<start>\n";
		if(isItIfStatement(s))
		{
			if(!isThereElse)
			{
				String temp = "if "+l1 + " " + lo1 + " " + l2 + " ";
				willPrintedString += "<if_statement>\n";
				willPrintedString += "if <logic_expr> <statement>\n";
				willPrintedString += "if <var> "+ lo1 + " <var> <statement>\n";
				willPrintedString += "if " + l1 + " " + lo1 + " <var> <statement>\n";
				willPrintedString += temp + "<statement>\n";
				willPrintedString += temp + "{<var> := <expr>}\n";
				willPrintedString += temp + "{"+s1 + " := " + "<expr>"+"}\n";
				willPrintedString += temp + "{"+s1 + " := " + "<var> "+eo1+" <var>"+ "}\n";
				willPrintedString += temp + "{"+s1 + " := " + e1 +" "+eo1+" <var>"+"}\n";
				willPrintedString += temp + "{"+s1 + " := " + e1 +" "+eo1+" "+e2 +"}\n";
			}
			else
			{
				String temp = "if "+l1 + " " + lo1 + " " + l2 + " ";
				willPrintedString += "<if_statement>\n";
				willPrintedString += "if <logic_expr> <statement> else <statement>\n";
				willPrintedString += "if <var> "+ lo1 + " <var> <statement> else <statement>\n";
				willPrintedString += "if " + l1 + " " + lo1 + " <var> <statement> else <statement>\n";
				willPrintedString += temp + "<statement> else <statement>\n";
				willPrintedString += temp + "{<var> := <expr>} else <statement>\n";
				willPrintedString += temp + "{"+s1 + " := " + "<expr>"+"} else <statement>\n";
				willPrintedString += temp + "{"+s1 + " := " + "<var> "+eo1+" <var>"+ "} else <statement>\n";
				willPrintedString += temp + "{"+s1 + " := " + e1 +" "+eo1+" <var>"+"} else <statement>\n";
				willPrintedString += temp + "{"+s1 + " := " + e1 +" "+eo1+" "+e2 +"} else <statement>\n";
				willPrintedString += temp + "{"+s1 + " := " + e1 +" "+eo1+" "+e2 +"} else {<var> := <expr>}\n";
				willPrintedString += temp + "{"+s1 + " := " + e1 +" "+eo1+" "+e2 +"} else {"+s1_else + " := <expr>}\n";
				willPrintedString += temp + "{"+s1 + " := " + e1 +" "+eo1+" "+e2 +"} else {"+s1_else + " := " + "<var> "+eo1_else+" <var>"+ "}\n";
				willPrintedString += temp + "{"+s1 + " := " + e1 +" "+eo1+" "+e2 +"} else {"+s1_else + " := "+ e1_else +" "+eo1_else+" <var>"+"}\n";
				willPrintedString += temp + "{"+s1 + " := " + e1 +" "+eo1+" "+e2 +"} else {"+s1_else + " := " + e1_else +" "+eo1_else+" "+e2_else +"}\n";
				
			}
			return true;
		}
		willPrintedString = "<start>\n";
		if(isItStatement(s,false))
		{
			willPrintedString += "<statement>\n";
			willPrintedString += "<var> := <expr>\n";
			willPrintedString += s1 + " := " + "<expr>\n";
			willPrintedString += s1 + " := " + "<var> "+eo1+" <var>\n";
			willPrintedString += s1 + " := " + e1 +" "+eo1+" <var>\n";
			willPrintedString += s1 + " := " + e1 +" "+eo1+" "+e2 +"\n";
			return true;
		}
		return false;
	}
	public static boolean isItIfStatement(String s)
	{
		s = s.replace(" ", "");
		String if_s = s.substring(0,2);
		s = s.substring(2);
		String logic_expr = "";
		String else_s = "";
		String statement = "";
		boolean change = false;
		if(if_s.equals("if") && s.contains("else"))	
		{
			isThereElse = true;
			if(s.contains("{") && s.contains("}") )
			{
				int i;
				for( i=0; i<=s.indexOf("}"); i++)
				{
					if(s.charAt(i) == '{' || s.charAt(i) == '}' ) 
					{
						change = true;
					}
					if(!change)	logic_expr += s.charAt(i);
					else		statement += s.charAt(i);
				}
				if(isItLogicExpr(logic_expr,false) && isItStatement(statement,false))
				{	
					else_s = s.substring(i,i+4);
					s = s.substring(i+4);
					if(else_s.equals("else") && isItStatement(s,true))
					{
						return true;
					}
				}
				else	return false;
			}	
		}
		else if(if_s.equals("if"))
		{
			if(s.contains("{") && s.contains("}") )
			{
				for(int i=0; i<s.length(); i++)
				{
					if(s.charAt(i) == '{' || s.charAt(i) == '}' ) 
					{
						change = true;
					}
					if(!change)	logic_expr += s.charAt(i);
					else		statement += s.charAt(i);
				}
			}		
			if(isItLogicExpr(logic_expr,false) && isItStatement(statement,false))
			{
				return true;
			}
		}
		return false;
	}
	public static boolean isItLogicExpr(String s,boolean isItElse)
	{
		String var1 = "";
		String var2 = "";
		String operation = "";
		s = s.replace(" ", "");
		boolean changeVar = false;
		for(int i=0; i<s.length(); i++)
		{
			if(!( (s.charAt(i) <='z' && s.charAt(i) >= 'a' ) || (s.charAt(i) <='Z' && s.charAt(i) >= 'A') ))
			{
				operation += s.charAt(i);
				changeVar = true;
				continue;
			}
			else if ( ! changeVar)	var1 += s.charAt(i);
			else	var2 += s.charAt(i);
		}
		if(isItVar(var1) && isItVar(var2) && isItLogicOp(operation))
		{
			if(!isItElse)
			{
				l1 = var1;
				l2 = var2;
				lo1 = operation;
			}
			else
			{
				l1_else = var1;
				l2_else = var2;
				lo1_else = operation;
			}
			return true;
		}
		return false;
	}
	public static boolean isItExpr(String s,boolean isItElse)
	{
		String var1 = "";
		String var2 = "";
		String operation = "";
		s = s.replace(" ", "");
		boolean changeVar = false;
		for(int i=0; i<s.length(); i++)
		{
			if(! ( (s.charAt(i) <='z' && s.charAt(i) >= 'a' ) || (s.charAt(i) <='Z' && s.charAt(i) >= 'A') ) )
			{
				operation += s.charAt(i);
				changeVar = true;
				continue;
			}
			else if ( ! changeVar)	var1 += s.charAt(i);
			else	var2 += s.charAt(i);
		}
		if(isItVar(var1) && isItVar(var2) && isItOp(operation))
		{
			if(!isItElse)
			{
				e1 = var1;
				e2 = var2;
				eo1 = operation;
			}
			else
			{
				e1_else = var1;
				e2_else = var2;
				eo1_else = operation;
			}
			return true;
		}
		return false;
	}
	public static boolean isItStatement(String s,boolean isItElse)
	{
		String var1 = "";
		String expr = "";
		String operation = "";
		s = s.replace(" ", "");
		boolean changeVar = false;
		if(s.charAt(0) != '{' || s.charAt(s.length()-1) != '}')
		{
			return false;
		}
		for(int i=1; i<s.length()-1; i++)
		{
			if(!( (s.charAt(i) <='z' && s.charAt(i) >= 'a' ) || (s.charAt(i) <='Z' && s.charAt(i) >= 'A')))
			{
				operation += s.charAt(i);
				changeVar = true;
				continue;
			}
			else if ( ! changeVar)	var1 += s.charAt(i);
			else
			{
				expr = s.substring(i,s.length()-1);
				break;
			}
		}
		if(isItVar(var1) && isItExpr(expr,isItElse) && isItSignOp(operation))
		{
			if(!isItElse)
			{
				s1 = var1;
				so1 = operation;
			}
			else
			{
				s1_else = var1;
				so1_else = operation;
			}
			return true;
		}
		return false;
	}
	public static boolean isItLogicOp(String s)
	{
		s = s.trim();
		if(s.equals("<") || s.equals(">") || s.equals("=") || s.equals(">=") || s.equals("<="))	return true;
		return false;
	}
	public static boolean isItSignOp(String s)
	{
		s = s.replace(" ", "");
		if(s.equals(":="))	return true;
		return false;
	}
	
	public static boolean isItOp(String s)
	{
		s = s.replace(" ", "");
		if(s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/") || s.equals("%"))	return true;
		return false;
	}
	public static boolean isItVar(String s) 
	{
		int counter = 0;
		for(int i=0; i<s.length(); i++)
		{
			if( ! ((s.charAt(i) <='z' && s.charAt(i) >= 'a' ) || (s.charAt(i) <='Z' && s.charAt(i) >= 'A')))
				return false;
			counter++;
		}
		if(counter > 1)	return false;
		else			return true;
	}
	public static void save(String out)
	{
		PrintWriter outputStream = null;
		try
		{
			outputStream = new PrintWriter(out);
			if(isItStart(sentences))
			{
				outputStream.println("Parsed correctly\nParse Tree:");
				outputStream.println(willPrintedString);
			}
			else
			{
				outputStream.println("Parsed incorrectly");
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		outputStream.close();
	}
	public static void main(String[] args) 
	{
		 sentences = parseFile(args[0]);
		 save(args[1]);
	}

}
