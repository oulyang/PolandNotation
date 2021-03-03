package PolandNotation;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
/**
 * 1） 初始化两个栈：运算符栈s1 存储中间值结果的栈s2
 * 2） 从左至右扫描中缀表达式
 * 3） 遇到操作数，将其压入s2
 * 4） 遇到运算符，与s1栈顶比较优先级大小：
 * 			1.若s1为空或栈顶运算符为左括号，直接压栈s1
 * 			2.若优先级比栈顶高，直接压栈s1
 * 			3.将s1栈顶运算符弹出并压入s2中，再次转到 4)1 与s1中新的栈顶运算符比较
 * 5）遇到括号
 * 		1.若是左括号，直接压栈s1
 * 		2.若是右括号，则依次弹出s1栈顶运算符并压栈s2，直到遇到左括号，此时将该对括号去掉
 * 6）重复2）~5）直到扫描到最右端
 * 7） 将s1中剩余运算符依次弹出并压入s2
 * 8） 依次弹出s2的元素并输出，结果的逆序即为对应后缀表达式
 * @author yyds
 *
 */
public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		//逆波兰表达式(后缀表达式)
		//从左往右扫描，遇到数字将数字压栈，遇到运算符则弹出栈顶和次顶元素用运算符进行计算，并将
		//结果入栈，重复此过程直到扫描到最右端，最后计算出的即为表达式结果
		//（3+4）*5-6-->  3 4 + 5 * 6 -
		String suffixExpression="3 4 + 5 * 6 - ";
		//1.将 suffixExpression 放入ArrayList
		//2.将ArrayList传入方法，遍历ArrayList配合栈完成计算
		
		List<String> rpnList=getListString(suffixExpression);
		//System.out.println(rpnList);
		
		int ret=calculate(rpnList);
		System.out.println(ret);
		*/
		
		
		//中缀表达式：1+((2+3)*4)-5--> 1 2 3 + 4 * + 5 -
		//将表达式放入List
		//转换成逆波兰表达式
		String expression="1+((2+3)*4)-5";
		List<String> ls=toInfixExpressionList(expression);
		//System.out.println(ls);
		List<String> resultList=parseSuffixExpression(ls);
		//System.out.println(resultList);
		
		System.out.println(calculate(resultList));
		
	}

	//将逆波兰表达式放ArrayList
	public static List<String> getListString(String suffixExpression){
		//分割suffixExpression
		String[] split =suffixExpression.split(" ");
		List<String> list=new ArrayList<>();
		
		for(String s:split) {
			list.add(s);
		}
		return list;
	}
	
	/*1.将3 和4压栈
	 * 2.遇到+，将4 3弹出计算得7压栈
	 * 3.将5压栈
	 * 4.遇到*，将5 7弹栈计算得35压栈
	 * 5.将6压栈
	 * 6.遇到—，将6 35弹栈，用次顶35-栈顶6得出29压栈
	 */
	public static int calculate(List<String> ls) {
		//创建栈
		Stack<String> stack=new Stack<>();
		//遍历ls
		for(String s:ls) {
			//使用正则表达式来取数
			if(s.matches("\\d+")) {
				//匹配的是多位数
				//入栈
				stack.push(s);
			}else {
				//运算符
				//弹出两个数计算
				int num2=Integer.parseInt(stack.pop());
				int num1=Integer.parseInt(stack.pop());
				int ret=0;
				if(s.equals("+")) {
					ret=num1+num2;
				}else if(s.equals("-")) {
					//次顶-栈顶
					ret=num1-num2;
				}else if(s.equals("*")) {
					ret=num1*num2;
				}else if(s.equals("/")){
					ret=num1/num2;
				}else {
					throw new RuntimeException("运算符错误");
				}
				//把ret压栈
				stack.push(""+ret);
			}
		}
		//最后的结果在栈中
		return Integer.parseInt(stack.pop());
	}
	
	
	
	//将中缀表达式转成List
	public static List<String> toInfixExpressionList(String s){
		//定义List
		List<String> list=new ArrayList<>();
		int i=0;//用于遍历中缀表达式字符串
		String str;//用于字符串拼接
		char c;//用于存储遍历的每一个字符
		
		do {
			//如果c不是数字，就需要加入list
			if((c=s.charAt(i))<48||((c=s.charAt(i))>57)) {
				list.add(""+c);
				i++;
			}else {
				str="";
				while(i<s.length()&&(c=s.charAt(i))>=48&&(c=s.charAt(i))<=57) {
					str+=c;//拼接
					i++;
				}
				list.add(str);
			}
		}while(i<s.length());
		return list;
	}
	
	public static List<String> parseSuffixExpression(List<String> ls){
		Stack<String> charStack=new Stack<>();
		//存储中间值这个栈在转换过程没有pop操作，且后续需要逆序输出
		//使用List
		List<String> resList=new ArrayList<>();
		
		//遍历ls
		for(String s:ls) {
			if(s.matches("\\d+")) {
				//是一个数
				resList.add(s);
			}else if(s.equals("(")) {
				charStack.push(s);
			}else if(s.equals(")")) {
				//若是右括号，则依次弹出s1栈顶运算符并压栈s2，直到遇到左括号，此时将该对括号去掉
				while(!charStack.peek().equals("(")) {
					resList.add(charStack.pop());
				}
				charStack.pop();//!!!将（弹出s1，消除一对小括号
			}else {
				//s优先级<=栈顶,将s1栈顶运算符弹出并加入s2中，再次转到 4)1 与s1中新的栈顶运算符比较
				//缺少一个比较优先级方法
				while(charStack.size()!= 0 && Operation.getValue(charStack.peek())>=Operation.getValue(s)) {
					resList.add(charStack.pop());
				}
				//将s压栈
				charStack.push(s);
			}
		}
		
		//将s1中剩余运算符依次弹出并加入s2
		while(charStack.size()!=0) {
			resList.add(charStack.pop());
		}
		return resList;
	}
}

class Operation{
	private static int Add=1;
	private static int Sub=1;
	private static int Mul=2;
	private static int Div=2;
	
	public static int getValue(String operation) {
		int ret=0;
		switch(operation) {
		case "+":
			ret=Add;
			break;
		case "-":
			ret=Sub;
			break;
		case "*":
			ret=Mul;
			break;
		case "/":
			ret=Div;
			break;
		default:
			System.out.println("不存在该运算符");
			break;
		}
		return ret;
	}
}
