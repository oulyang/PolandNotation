package PolandNotation;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
/**
 * 1�� ��ʼ������ջ�������ջs1 �洢�м�ֵ�����ջs2
 * 2�� ��������ɨ����׺���ʽ
 * 3�� ����������������ѹ��s2
 * 4�� �������������s1ջ���Ƚ����ȼ���С��
 * 			1.��s1Ϊ�ջ�ջ�������Ϊ�����ţ�ֱ��ѹջs1
 * 			2.�����ȼ���ջ���ߣ�ֱ��ѹջs1
 * 			3.��s1ջ�������������ѹ��s2�У��ٴ�ת�� 4)1 ��s1���µ�ջ��������Ƚ�
 * 5����������
 * 		1.���������ţ�ֱ��ѹջs1
 * 		2.���������ţ������ε���s1ջ���������ѹջs2��ֱ�����������ţ���ʱ���ö�����ȥ��
 * 6���ظ�2��~5��ֱ��ɨ�赽���Ҷ�
 * 7�� ��s1��ʣ����������ε�����ѹ��s2
 * 8�� ���ε���s2��Ԫ�ز���������������Ϊ��Ӧ��׺���ʽ
 * @author yyds
 *
 */
public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		//�沨�����ʽ(��׺���ʽ)
		//��������ɨ�裬�������ֽ�����ѹջ������������򵯳�ջ���ʹζ�Ԫ������������м��㣬����
		//�����ջ���ظ��˹���ֱ��ɨ�赽���Ҷˣ���������ļ�Ϊ���ʽ���
		//��3+4��*5-6-->  3 4 + 5 * 6 -
		String suffixExpression="3 4 + 5 * 6 - ";
		//1.�� suffixExpression ����ArrayList
		//2.��ArrayList���뷽��������ArrayList���ջ��ɼ���
		
		List<String> rpnList=getListString(suffixExpression);
		//System.out.println(rpnList);
		
		int ret=calculate(rpnList);
		System.out.println(ret);
		*/
		
		
		//��׺���ʽ��1+((2+3)*4)-5--> 1 2 3 + 4 * + 5 -
		//�����ʽ����List
		//ת�����沨�����ʽ
		String expression="1+((2+3)*4)-5";
		List<String> ls=toInfixExpressionList(expression);
		//System.out.println(ls);
		List<String> resultList=parseSuffixExpression(ls);
		//System.out.println(resultList);
		
		System.out.println(calculate(resultList));
		
	}

	//���沨�����ʽ��ArrayList
	public static List<String> getListString(String suffixExpression){
		//�ָ�suffixExpression
		String[] split =suffixExpression.split(" ");
		List<String> list=new ArrayList<>();
		
		for(String s:split) {
			list.add(s);
		}
		return list;
	}
	
	/*1.��3 ��4ѹջ
	 * 2.����+����4 3���������7ѹջ
	 * 3.��5ѹջ
	 * 4.����*����5 7��ջ�����35ѹջ
	 * 5.��6ѹջ
	 * 6.����������6 35��ջ���ôζ�35-ջ��6�ó�29ѹջ
	 */
	public static int calculate(List<String> ls) {
		//����ջ
		Stack<String> stack=new Stack<>();
		//����ls
		for(String s:ls) {
			//ʹ��������ʽ��ȡ��
			if(s.matches("\\d+")) {
				//ƥ����Ƕ�λ��
				//��ջ
				stack.push(s);
			}else {
				//�����
				//��������������
				int num2=Integer.parseInt(stack.pop());
				int num1=Integer.parseInt(stack.pop());
				int ret=0;
				if(s.equals("+")) {
					ret=num1+num2;
				}else if(s.equals("-")) {
					//�ζ�-ջ��
					ret=num1-num2;
				}else if(s.equals("*")) {
					ret=num1*num2;
				}else if(s.equals("/")){
					ret=num1/num2;
				}else {
					throw new RuntimeException("���������");
				}
				//��retѹջ
				stack.push(""+ret);
			}
		}
		//���Ľ����ջ��
		return Integer.parseInt(stack.pop());
	}
	
	
	
	//����׺���ʽת��List
	public static List<String> toInfixExpressionList(String s){
		//����List
		List<String> list=new ArrayList<>();
		int i=0;//���ڱ�����׺���ʽ�ַ���
		String str;//�����ַ���ƴ��
		char c;//���ڴ洢������ÿһ���ַ�
		
		do {
			//���c�������֣�����Ҫ����list
			if((c=s.charAt(i))<48||((c=s.charAt(i))>57)) {
				list.add(""+c);
				i++;
			}else {
				str="";
				while(i<s.length()&&(c=s.charAt(i))>=48&&(c=s.charAt(i))<=57) {
					str+=c;//ƴ��
					i++;
				}
				list.add(str);
			}
		}while(i<s.length());
		return list;
	}
	
	public static List<String> parseSuffixExpression(List<String> ls){
		Stack<String> charStack=new Stack<>();
		//�洢�м�ֵ���ջ��ת������û��pop�������Һ�����Ҫ�������
		//ʹ��List
		List<String> resList=new ArrayList<>();
		
		//����ls
		for(String s:ls) {
			if(s.matches("\\d+")) {
				//��һ����
				resList.add(s);
			}else if(s.equals("(")) {
				charStack.push(s);
			}else if(s.equals(")")) {
				//���������ţ������ε���s1ջ���������ѹջs2��ֱ�����������ţ���ʱ���ö�����ȥ��
				while(!charStack.peek().equals("(")) {
					resList.add(charStack.pop());
				}
				charStack.pop();//!!!��������s1������һ��С����
			}else {
				//s���ȼ�<=ջ��,��s1ջ�����������������s2�У��ٴ�ת�� 4)1 ��s1���µ�ջ��������Ƚ�
				//ȱ��һ���Ƚ����ȼ�����
				while(charStack.size()!= 0 && Operation.getValue(charStack.peek())>=Operation.getValue(s)) {
					resList.add(charStack.pop());
				}
				//��sѹջ
				charStack.push(s);
			}
		}
		
		//��s1��ʣ����������ε���������s2
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
			System.out.println("�����ڸ������");
			break;
		}
		return ret;
	}
}
