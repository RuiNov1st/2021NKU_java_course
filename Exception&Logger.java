package helloworld;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.System.Logger.Level;
import java.math.BigDecimal;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

//�쳣��
//���ʽ����
class error_expression extends Exception{
	int extype = 0;
	error_expression(){
		
	}
	error_expression(int extype){
		this.extype = extype;
	}
	
	
}
//����δ����
class variable_undefined extends Exception{
	String name = "";
	variable_undefined(String name){
		this.name = name;
	}
	
}
//����δ����ֵ
class variable_unassigned extends Exception{
	String name = "";
	variable_unassigned(String name){
		this.name = name;
	}
	}
//��֧�ֵ��������
class unsupported_operation extends Exception{
	int type1 = 0;
	int type2 = 0;
	unsupported_operation(int prior){
		if(prior==5) {
			type1++;
		}else {
			type2++;
		}
	}
	
}

//�쳣����
class error {
	String[] variable;//����
	HashMap<String,ArrayList<String>>var;//����HashMap��ֵ�Լ�¼���������Ƿ��Ѹ�ֵ
	String input;
	String output;
	//���ô�������
	//1-���ʽ����
	//2-����δ����
	//3-����δ��ֵ
	//4-��֧�ֵ��������
	int error1 = 0;
	int error2 = 0;
	int error3 = 0;
	int error4 = 0;
	Logger mylogger;

	error(){
		var = new HashMap<String,ArrayList<String>>();
		//������־
		mylogger = Logger.getLogger("cal");
		//���뵽�ļ���
		try {
			FileHandler f = new FileHandler("D:\\java_file\\HelloWorld\\cal8.txt");
			SimpleFormatter sf = new SimpleFormatter();
			f.setFormatter(sf);
			mylogger.addHandler(f);
		} catch (SecurityException e1) {
			e1.printStackTrace();
			// TODO Auto-generated catch block
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	//������ת��Ϊ������ֵ���ַ�������ʽ��
	void deal_realvalue(stack_java sj) throws variable_unassigned, variable_undefined, error_expression, unsupported_operation {
		boolean flag = true;
		//�ж��Ƿ�Ϊ����
		int length = sj.input_deal.length;
		int last = 0;//��¼��һ���ַ�������
		int left = 0;//�Ƿ����������
		int right = 0;//�Ƿ������������֮ƥ�䡣
		String unmatched_name = "";
		String unsigned_name = "";
		
		//��¼�쳣����
		int ex1 = 0;//���ʽ����
		int ex2 = 0;//����������
		int ex3 = 0;//����û�и�ֵ
		int ex4 = 0;//��֧�ֵĲ���
		int ex11 = 0;
		int ex12 = 0;
		
		int prior = 0;
		mylabel:
		for(int i = 0;i<length;i++) {
			String s = sj.input_deal[i];
			//�ж������ֻ��Ƿ���
			//˳�������ʽ����ȷ��
			for (char c : s.toCharArray ()) {
				 if (Character.isDigit(c)) {
					 last = -1;
					 continue mylabel;
				 }
			}
			prior = sj.jud_prior(s);
			//��֧�ֵĲ���
			if(prior == 5||prior==6) {
				ex4++;
				break mylabel;
			}
			//����
			if(prior==-1) {
				//�����Ƿ��иñ���
				int size = var.size();
				mylabel2:
				for(int j = 0;j<size;j++) {	
					//�����Ƿ��иñ���
					if(var.containsKey(s)) {
						//���иñ������鿴���Ƿ�ֵ
						if(var.get(s).get(1).equals("false")){
							//û�и�ֵ
							ex3++;
							unsigned_name = s;
							//break mylabel;
						}else {
							//���������ű�Ϊֵ���ַ���
							String value = var.get(s).get(2);
							if(var.get(s).get(0).equals("float")) {
								if(!value.contains(".")) {
								 value+=".00";//�ֶ���С����
								}
							}
							sj.input_deal[i] = value;
							last = prior;//��¼��һ��
							//System.out.print(sj.input_deal[i]);
							break mylabel2;
						}
					}else {
						//û�иñ���
						unmatched_name = s;
						ex2++;
					}
				}
				
			}else {
				if(prior==3) {
					if(left!=0) {
						//��һ��������ûƥ��
						ex1++;
						ex11++;
						break mylabel;
					}else {
						left = 1;
					}
				}else {
					if(prior==4) {
						//û����������֮ƥ��
						if(left==0||right!=0) {
							ex1++;
							ex11++;
							break mylabel;
							}else {
								//һ������
								left = 0;
								right = 0;
							}
						}
				}
				//������������
				if(last!=-1&&prior!=-1) {
					//������֮ǰ����������֮�ⶼ�Ϸ�
					if(prior==3&&last!=4) {
				}else {
					//������֮����������Ŷ��Ϸ�
						if(last==4&&prior!=3) {
							
						}else {
							ex1++;
							ex12++;
							break mylabel;
						}
					}
				}
				
			}
			last = prior;
		}
		if(left==1||right==1) {
			ex1++;
			ex11++;
		}
		//�ж��쳣
		if(ex1!=0) {
			int temp = 0;
			if(ex11!=0)temp = 1;
			else if(ex12!=0)temp =2;
			throw new error_expression(temp);
		}
		if(ex2!=0) {
			throw new variable_undefined(unmatched_name);
		}
		if(ex3!=0) {
			throw new variable_unassigned(unsigned_name);
		}
		if(ex4!=0) {
			throw new unsupported_operation(prior);
		}
	}
	
	//�ͳ������ʽ��ֵ���нӿ�����
	void express_value(String input) throws error_expression {
		boolean flag = true;
		stack_java sj = new stack_java(input);
		//������ʽ���룬����input_deal��
		//����Ĺ����жԱ��ʽ�����ж�
		//�����Ƿֺ����⣬�������׳�һ����
		sj.input_split_error();
		StringWriter trace = new StringWriter();
		try {
			deal_realvalue(sj);
			
		}catch(error_expression e) {
			e.printStackTrace(new PrintWriter(trace));
			if(e.extype==1) {
			mylogger.severe(trace.toString()+"Wrong - Error Expression: Parentheses cannot matched");
			//mylogger.severe("Wrong - Error Expression: Parentheses cannot matched");
			System.out.print("Wrong - Error Expression: Parentheses cannot matched");
			}else {
				mylogger.severe(trace.toString()+"Wrong - Error Expression: Operators Wrong usage");
				//mylogger.severe("Wrong - Error Expression: Operators Wrong usage");
				System.out.print("Wrong - Error Expression: Operators Wrong usage");
			}
			flag = false;
			
		}catch(variable_undefined e) {
			e.printStackTrace(new PrintWriter(trace));
			mylogger.severe(trace.toString()+"Wrong - Variable "+e.name+" Undefined");
			//mylogger.severe("Wrong - Variable "+e.name+" Undefined");
			System.out.print("Wrong - Variable "+e.name+" Undefined");
			flag = false;
		}catch(variable_unassigned e) {
			e.printStackTrace(new PrintWriter(trace));
			mylogger.severe(trace.toString()+"Wrong - Variable "+e.name+" Unassigned");
			//mylogger.severe("Wrong - Variable "+e.name+" Unassigned");
			System.out.print("Wrong - Variable "+e.name+" Unassigned");
			flag = false;
		}catch(unsupported_operation e) {
			e.printStackTrace(new PrintWriter(trace));
			//mylogger.severe(trace.toString()+"Wrong - Error Expression: Operators Wrong usage");
			System.out.print("Wrong - Unsupported_Operation: ");
			if(e.type1!=0) {
				mylogger.severe(trace.toString()+"Wrong - Unsupported_Operation: Exponential Operation cannot be solved.");
				//mylogger.severe("Wrong - Unsupported_Operation: Exponential Operation cannot be solved.");
				System.out.print("Exponential Operation cannot be solved.");
			}else {
				if(e.type2!=0) {
					mylogger.severe(trace.toString()+"Wrong - Unsupported_Operation: Factorial Operation cannot be solved.");
					//mylogger.severe("Wrong - Unsupported_Operation: Factorial Operation cannot be solved.");
					System.out.print("Factorial Operation cannot be solved.");
				}
				
			}
			
			flag = false;
		}
		//��������
		if(flag==true) {
			output = sj.calculation();
			input = input.substring(0,input.length()-1);
			mylogger.info(input+" "+output);
			System.out.print(output);
		}
		//return flag;
	}
	//ɾ��ĳ��λ�õ��ַ�
	public static String removeCharAt(String s, int pos) {
		return s.substring(0, pos) + s.substring(pos + 1);
	}

	//���ʽ�Ĵ���
	void express_split(String input)  {
		
		int error1 = 0;
		boolean judge = true;
		int out = 0;
		//������ַ����ÿո񡢷ֺš�=�Ÿ���
		
		int length = input.length();
		String temp = input;
		
		//�����Ƿ�����ʺţ������ı��ʽ��ֵ
		if(temp.contains("?")) {
			mylogger.info(input+" (Deal Finished)");
			//�鿴�Ƿ�ֺ������⡣
			try {
				express_value(input);
			}catch(error_expression e){
				error1++;
			}
			out = 1;
			//�ӿ�����
		}
		//�������ı��ʽ��ֵ
		else {
			//����ʱȥ���ֺ�
			if(temp.contains(";")) {
				int index = temp.indexOf(";");
				temp = removeCharAt(temp, index);
			}else {
				//û���зֺţ����ʽ����
				error1++;
				//throw new error_expression();
				//judge = false;
			}
			//����һ������װ����ֵ�Ķ���
			//��һ��:��ʾ���������ͣ�float,int,Ĭ��û��Ϊnull
			//�ڶ���:��ʾ�����Ƿ�ֵ:true,false��Ĭ��û��Ϊnull
			//������:���и�ֵ�����ֵ�浽������
			ArrayList<String> lt = new ArrayList<String>(3);
			//��ʼ��Ϊnull
			for(int i = 0;i<3;i++) {
				lt.add("null");
			}
			//�жϱ����Ƿ��Ѹ�ֵ
			boolean flag = false;
			
			//���������ͬʱ�и�ֵ
			if(temp.contains("=")) {
				//ȥ���Ⱥ�
				int index = temp.indexOf("=");
				//����ֱ��ȥ��������Ὣ�Ⱥź�ǰ��Ļ���������������
				temp = temp.replace("=", " ");
				//temp = removeCharAt(temp,index);
				lt.set(1,"true");
				flag = true;//��ֵ��
			}else {
				lt.set(1, "false");
				flag = false;//û��ֵ
			}
			String[]tt;
			//���տո���зָ�
			tt = temp.split("\\s+");
			//����ָ��õĶ����������ʽ
			String varname = "";
			boolean type = false;//�ñ��ʽ�Ƿ񺭸��˱���������
			
			//�ж�����
			if(tt[0].equals("float")) {
				lt.set(0,"float");
				type = true;
			}else {
				//int
				if(tt[0].equals("int")) {
					lt.set(0,"int");
					type = true;
				}else {
					//�Ǳ�����
					if(var.containsKey(tt[0])) {
						//֮ǰ�Ѿ��й���
						//�׳�����ֵ
						lt = var.remove(tt[0]);
						if(flag==true) {
							lt.set(1, "true");
						}
					}else {
						mylogger.severe("Wrong - Variable "+tt[0]+" Undefined");
						System.out.print("Wrong - Variable "+tt[0]+" Undefined");
					}
					
				}
			}
			//�洢�������ֺ�ֵ
			if(type==true) {
				varname = tt[1];
				//��ֵ��
				if(flag==true) {
					lt.set(2, tt[2]);
				}
			}else {
				varname = tt[0];
				lt.set(2, tt[1]);
			}
			//����HashMap
			var.put(varname, lt);
		}
	if(error1!=0) {
		mylogger.severe(input+"\n"+"Wrong Statement!");
		System.out.print("Wrong Statement!");
	}else {
		if(out==0) {
		mylogger.info(input+" (Deal Finished)");
		}
	}
}

}
//ʹ��ջ���б��ʽ��ֵ
class stack_java{
	
	Stack number_int;//������
	Stack number_flo;//��С��
	Stack<String> chara_stack;//���������
	String input;//�洢������ʽ
	String[] input_deal;//�洢����õ�������ʽ
	Stack<String> numberstorage;//�洢���ֵĴ�����
	Queue<String> charastorage;//�洢���ŵĴ�����
	
	//���캯��
	stack_java(String input){
		number_int=new Stack();
		number_flo = new Stack();
		chara_stack = new Stack();
		this.input = input;
		numberstorage = new Stack();
		charastorage = new LinkedList();
	}
	
	//��װ���������̵ĺ���
	void cal_adv() {
		String b = numberstorage.pop();
		String a = numberstorage.pop();
		numberstorage.add(this.cal(a, b, chara_stack.pop()));
	}
	
	
	//���㺯��
	String cal(String a,String b,String cha) {
		int a_int = 0;
		int b_int = 0;
		float a_flo = 0;
		float b_flo = 0;
		int flag = 0;//ת����ʲô����1-��������0-����
		String result = "";
		
		//�����Ƿ����С����
		//ֻҪһ������С���㣬����Ҫ����float�Ľ��
		if(a.contains(".")||b.contains(".")) {
			a_flo = Float.parseFloat(a);
			b_flo = Float.parseFloat(b);
			flag = 1;
		}else {
			a_int = Integer.parseInt(a);
			b_int = Integer.parseInt(b);
		}
		switch(cha) {
		case "+":
			if(flag==1) {
				result = String.valueOf(a_flo+b_flo);
			}else {
				result = String.valueOf(a_int+b_int);
			}
			break;
		case "-":
			if(flag==1) {
				result = String.valueOf(a_flo-b_flo);
			}else {
				result = String.valueOf(a_int-b_int);
			}
			break;
		case "*":
			if(flag==1) {
				BigDecimal aa = new BigDecimal(a);
				BigDecimal bb = new BigDecimal(b);
				result = String.valueOf(aa.multiply(bb).floatValue());
			}else {
				result = String.valueOf(a_int*b_int);
			}
			break;
		case "/":
			if(flag==1) {
				BigDecimal aa = new BigDecimal(a);
				BigDecimal bb = new BigDecimal(b);
				result = String.valueOf(aa.divide(bb,3,BigDecimal.ROUND_HALF_UP).floatValue());
			}else {
				result = String.valueOf(a_int/b_int);
			}
			break;
		case"%":
			if(flag==1) {
				result = String.valueOf(a_flo%b_flo);
			}else {
				result = String.valueOf(a_int%b_int);
			}
			break;
		}
		if(flag==1) {
			int index = result.indexOf(".");
			int sub = result.length()-1-index;
			if(sub==3) {
				BigDecimal num1 = new BigDecimal(result);
				result = String.valueOf((float)num1.setScale(3,BigDecimal.ROUND_HALF_UP).doubleValue());
			}
			//����
			if(sub==1) {
			result+="0";
		}
		}
		return result;
	}
	
	//�ж����ȼ�
	int jud_prior(String num) {
		switch(num) {
		case "+":return 1;
		case "-":return 1;
		case "*":return 2;
		case "/":return 2;
		case "%":return 2;
		case "(":return 3;
		case ")":return 4;
		//������֧�ֵĲ���
		case "^":;
		case "!":return 6;
		
		default:return -1;
		}
	}
	//ɾ��ĳ��λ�õ��ַ�
	public static String removeCharAt(String s, int pos) {
	      return s.substring(0, pos) + s.substring(pos + 1);
	   }
	
	//������ʽ����
	public void input_split() {
		String temp = this.input;
		int length = temp.length();
		//�����ڵȺţ�ȥ���Ⱥ�
		if(temp.charAt(length-1)=='=') {
			temp = removeCharAt(temp,length-1);
			//temp.replace("=", "");
		}
		String[]tt;
		
		tt = temp.split("(?<=[\\+\\-\\*\\/\\(\\)%])|(?=[\\+\\-\\*\\/\\(\\)%])");
		for(int i = 0;i<tt.length;i++) {
			//System.out.print(tt[i]);
			//������ͷ�������ź�����Ÿ���
			if((tt[i].equals("-")&&i==0)||i>=1&&(tt[i-1].equals("(")&&tt[i].equals("-"))) {
				if(tt[i+1].contains(".")) {
					tt[i+1] = String.valueOf(Float.parseFloat(tt[i+1])*(-1.0) );
				}else {
					tt[i+1] = String.valueOf(Integer.parseInt(tt[i+1])*(-1));
				}
				List<String>list1 = Arrays.asList(tt);
				List<String> arrList = new ArrayList<String>(list1); //
				arrList.remove(i);
				tt = arrList.toArray(new String[arrList.size()]);
			}
			
		}
		input_deal = tt;
			
	}
	//�쳣ʱ�Ĵ�����ʽ����
	public void input_split_error() throws error_expression {
		boolean flag = true;//�жϱ��ʽ�Ƿ���ȷ
		String temp = this.input;
		temp = temp.replace(" ","");
		int length = temp.length();
		
		//���ʽֻ�������һ��Ϊ�ʺţ������ڶ���Ϊ�Ⱥŵ�����²���ȷ
		//��ȷ�������ȥ��
		if(temp.charAt(length-1)=='?') {
			if(temp.charAt(length-2)=='=') {
				temp = removeCharAt(temp,length-1);
				temp = removeCharAt(temp,length-2);
			}else {
				flag = false;
			}
		}else {
			flag = false;
		}
		//������������зָ�
		String[]tt;
		tt = temp.split("(?<=[\\+\\-\\*\\/\\(\\)%!^])|(?=[\\+\\-\\*\\/\\(\\)%!^])");
		input_deal = tt;
		//�ж��Ƿ��׳��쳣
		if(flag==false) {
			throw new error_expression();
		}
	}
	
	//����
	public String calculation() {
		int flag = 0;
		int prior = 0;
		for(String s:input_deal) {
			prior = jud_prior(s);
			//����
			if(prior==-1) {
				numberstorage.add(s);
			}
			//�ַ�
			else {
				int stack_prior = 0;
				//�����ŵ�ջΪ��ʱ��ֱ�Ӽ���
				if(chara_stack.empty()) {
					chara_stack.add(s);
				}else {
				do {
				//���������ջջ��Ԫ�ص����ȼ�
				stack_prior = jud_prior(chara_stack.peek());
				//ջ��Ԫ�����ȼ����ڴ���ջԪ�����ȼ�������
				if(stack_prior!=3&&stack_prior>=prior) {
					cal_adv();
				}
			}while(!chara_stack.empty()&&stack_prior!=3&&stack_prior>=prior);
				if(chara_stack.empty()) {
					chara_stack.add(s);
				}else {
					stack_prior = jud_prior(chara_stack.peek());
				if(prior>stack_prior||stack_prior==3) {
					//ջ��Ԫ�ص����ȼ�С�ڵ��ڴ���ջԪ�ص����ȼ��Ҳ�Ϊ�����ţ���ջ
					if(prior!=4) {
						chara_stack.add(s);
					}else {
						//��������
						//����ջ�����ǿ�ʼ�������������㣬ֱ������������Ϊֹ
						mylabel2:
						while(jud_prior(chara_stack.peek())!=3) {
							cal_adv();
							continue mylabel2;
						}
						//�����������ˣ�����
						chara_stack.pop();
					}
				}
				}
				
			}
			}
		}

	
		
		//�ַ�������
		//���ַ�ջû�գ����������
		//����
		//chara_stack = reverse(chara_stack);
		//numberstorage = reverse(numberstorage);
		while(!this.chara_stack.empty()) {
			cal_adv();
		}
		//������������
		String last = this.numberstorage.pop();
		int index = last.indexOf(".");
		if(index>=0) {
		int sub = last.length()-1-index;
		if(sub==3) {
			BigDecimal num1 = new BigDecimal(last);
			last = String.valueOf((float)num1.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
		}
		index = last.indexOf(".");
		sub = last.length()-1-index;
		//����
		if(sub==1) {
		last+="0";
		}
		}
		return last;
	}
		
	
}

public class express_error{
	public static void main(String []args) throws variable_unassigned, variable_undefined {
		String input = "";
		Scanner sc = new Scanner(System.in);
		error er = new error();
		while(sc.hasNext()) {
			input = sc.nextLine();
			er.express_split(input);
			
		}
		
	}
}

