package helloworld;
import java.math.BigDecimal;
import java.util.*;

//异常类
//表达式错误
class error_expression extends Exception{
	
}
//变量未定义
class variable_undefined extends Exception{
	
}
//变量未赋初值
class variable_unassigned extends Exception{
	
}

//异常处理
class error {
	String[] variable;//变量
	HashMap<String,ArrayList<String>>var;//设置HashMap键值对记录变量即其是否已赋值
	String input;
	String output;

	error(){
		var = new HashMap<String,ArrayList<String>>();
		
	}
	//将符号转换为真正的值（字符串的形式）
	void deal_realvalue(stack_java sj) throws variable_unassigned, variable_undefined, error_expression {
		boolean flag = true;
		//判断是否为数字
		int length = sj.input_deal.length;
		int last = 0;//记录上一个字符串类型
		int left = 0;//是否出现左括号
		int right = 0;//是否出现右括号与之匹配。
		int ex1 = 0;//记录异常类型
		int ex2 = 0;
		int ex3 = 0;
		mylabel:
		for(int i = 0;i<length;i++) {
			String s = sj.input_deal[i];
			//判断是数字还是符号
			//顺便检验表达式的正确性
			for (char c : s.toCharArray ()) {
				 if (Character.isDigit(c)) {
					 continue mylabel;
				 }
			}
			int prior = sj.jud_prior(s);
			//变量
			if(prior==-1) {
				//查找是否有该变量
				int size = var.size();
				mylabel2:
				for(int j = 0;j<size;j++) {	
					//查找是否有该变量
					if(var.containsKey(s)) {
						//若有该变量，查看其是否赋值
						if(var.get(s).get(1).equals("false")){
							//没有赋值
							ex3++;
							//break mylabel;
						}else {
							//将变量符号变为值的字符串
							String value = var.get(s).get(2);
							if(var.get(s).get(0).equals("float")) {
								if(!value.contains(".")) {
								 value+=".00";//手动加小数点
								}
							}
							sj.input_deal[i] = value;
							last = prior;//记录上一个
							//System.out.print(sj.input_deal[i]);
							break mylabel2;
						}
					}else {
						//没有该变量
						ex2++;
						
						//break mylabel;
					}
				}
				
			}else {
				if(prior==3) {
					if(left!=0) {
						//上一个左括号没匹配
						ex1++;
						break mylabel;
					}else {
						left = 1;
					}
				}else {
					if(prior==4) {
						//没有左括号与之匹配
						if(left==0||right!=0) {
							ex1++;
							break mylabel;
							}else {
								//一对消掉
								left = 0;
								right = 0;
							}
						}
				}
				//两个符号相连
				if(last!=-1&&prior!=-1) {
					//左括号之前除了右括号之外都合法
					if(prior==3&&last!=4) {
				}else {
					//右括号之后除了左括号都合法
						if(last==4&&prior!=3) {
							
						}else {
							ex1++;
							break mylabel;
						}
					}
				}
				
			}
			last = prior;
		}
		if(left==1||right==1) {
			ex1++;
		}
		//判定异常
		if(ex1!=0) {
			throw new error_expression();
		}
		if(ex2!=0) {
			throw new variable_undefined();
		}
		if(ex3!=0) {
			throw new variable_unassigned();
		}
	}
	
	//和初级表达式求值进行接口整合
	void express_value(String input) throws variable_unassigned, variable_undefined {
		boolean flag = true;
		stack_java sj = new stack_java(input);
		//处理表达式输入，存入input_deal中
		//处理的过程中对表达式进行判断
		try {
		sj.input_split_error();
		}catch(Exception e) {
			System.out.print("wrong - error expression");
			flag = false;
		}
		try {
			deal_realvalue(sj);
		}catch(error_expression e) {
			System.out.print("wrong - error expression");
			flag = false;
		}catch(variable_undefined e) {
			System.out.print("wrong - variable undefined");
			flag = false;
		}catch(variable_unassigned e) {
			System.out.print("wrong - variable unassigned");
			flag = false;
		}
		//继续运算
		if(flag==true) {
			output = sj.calculation();
			System.out.print(output);
		}
		//return flag;
	}
	//删除某个位置的字符
	public static String removeCharAt(String s, int pos) {
		return s.substring(0, pos) + s.substring(pos + 1);
	}

	//表达式的处理
	boolean express_split(String input) throws variable_unassigned, variable_undefined, error_expression {
		boolean judge = true;
		//输入的字符串用空格、分号、=号隔开
		
		int length = input.length();
		String temp = input;
		
		//查找是否存在问号，即最后的表达式求值
		if(temp.contains("?")) {
			express_value(input);
			//接口设置
		}
		//不是最后的表达式求值
		else {
			//处理时去掉分号
			if(temp.contains(";")) {
				int index = temp.indexOf(";");
				temp = removeCharAt(temp, index);
			}else {
				//没含有分号，表达式错误
				//直接抛出错误
				throw new error_expression();
				//judge = false;
			}
			//定义一个数组装变量值的定义
			//第一个:表示变量的类型：float,int,默认没有为null
			//第二个:表示变量是否赋值:true,false，默认没有为null
			//第三个:若有赋值，则把值存到第三个
			ArrayList<String> lt = new ArrayList<String>(3);
			//初始化为null
			for(int i = 0;i<3;i++) {
				lt.add("null");
			}
			//判断变量是否已赋值
			boolean flag = false;
			
			//变量定义的同时有赋值
			if(temp.contains("=")) {
				//去掉等号
				int index = temp.indexOf("=");
				//不能直接去掉，否则会将等号和前面的或后面的数字连起来
				temp = temp.replace("=", " ");
				//temp = removeCharAt(temp,index);
				lt.set(1,"true");
				flag = true;//赋值了
			}else {
				lt.set(1, "false");
				flag = false;//没赋值
			}
			String[]tt;
			//按照空格进行分隔
			tt = temp.split("\\s+");
			//处理分隔好的定义或变量表达式
			String varname = "";
			boolean type = false;//该表达式是否涵盖了变量的类型
			
			//判断类型
			if(tt[0].equals("float")) {
				lt.set(0,"float");
				type = true;
			}else {
				//int
				if(tt[0].equals("int")) {
					lt.set(0,"int");
					type = true;
				}else {
					//是变量名
					if(var.containsKey(tt[0])) {
						//之前已经有过了
						//抛出来赋值
						lt = var.remove(tt[0]);
						if(flag==true) {
							lt.set(1, "true");
						}
					}
					
				}
			}
			//存储变量名字和值
			if(type==true) {
				varname = tt[1];
				//赋值了
				if(flag==true) {
					lt.set(2, tt[2]);
				}
			}else {
				varname = tt[0];
				lt.set(2, tt[1]);
			}
			//存入HashMap
			var.put(varname, lt);
		}
		
		
	return true;
}

}
//使用栈进行表达式求值
class stack_java{
	
	Stack number_int;//存整数
	Stack number_flo;//存小数
	Stack<String> chara_stack;//存运算符号
	String input;//存储运算表达式
	String[] input_deal;//存储处理好的运算表达式
	Stack<String> numberstorage;//存储数字的处理结果
	Queue<String> charastorage;//存储符号的处理结果
	
	//构造函数
	stack_java(String input){
		number_int=new Stack();
		number_flo = new Stack();
		chara_stack = new Stack();
		this.input = input;
		numberstorage = new Stack();
		charastorage = new LinkedList();
	}
	
	//封装具体计算过程的函数
	void cal_adv() {
		String b = numberstorage.pop();
		String a = numberstorage.pop();
		numberstorage.add(this.cal(a, b, chara_stack.pop()));
	}
	
	
	//计算函数
	String cal(String a,String b,String cha) {
		int a_int = 0;
		int b_int = 0;
		float a_flo = 0;
		float b_flo = 0;
		int flag = 0;//转化成什么数：1-浮点数，0-整数
		String result = "";
		
		//利用是否存在小数点
		//只要一方存在小数点，就需要返回float的结果
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
			//补零
			if(sub==1) {
			result+="0";
		}
		}
		return result;
	}
	
	//判断优先级
	int jud_prior(String num) {
		switch(num) {
		case "+":return 1;
		case "-":return 1;
		case "*":return 2;
		case "/":return 2;
		case "%":return 2;
		case "(":return 3;
		case ")":return 4;
		
		default:return -1;
		}
	}
	//删除某个位置的字符
	public static String removeCharAt(String s, int pos) {
	      return s.substring(0, pos) + s.substring(pos + 1);
	   }
	//处理表达式输入
	public void input_split() {
		String temp = this.input;
		int length = temp.length();
		//若存在等号，去掉等号
		if(temp.charAt(length-1)=='=') {
			temp = removeCharAt(temp,length-1);
			//temp.replace("=", "");
		}
		String[]tt;
		
		tt = temp.split("(?<=[\\+\\-\\*\\/\\(\\)%])|(?=[\\+\\-\\*\\/\\(\\)%])");
		for(int i = 0;i<tt.length;i++) {
			//System.out.print(tt[i]);
			//负数开头或者括号后面跟着负号
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
	//异常时的处理表达式输入
	public void input_split_error() throws error_expression {
		boolean flag = true;//判断表达式是否正确
		String temp = this.input;
		temp = temp.replace(" ","");
		int length = temp.length();
		//表达式只有在最后一个为问号，倒数第二个为等号的情况下才正确
		//正确的情况下去掉
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
		//按照运算符进行分割
		String[]tt;
		tt = temp.split("(?<=[\\+\\-\\*\\/\\(\\)%])|(?=[\\+\\-\\*\\/\\(\\)%])");
		input_deal = tt;
		//判断是否抛出异常
		if(flag==false) {
			throw new error_expression();
		}
	}
	
	//运算
	public String calculation() {
		int flag = 0;
		int prior = 0;
		for(String s:input_deal) {
			prior = jud_prior(s);
			//数字
			if(prior==-1) {
				numberstorage.add(s);
			}
			//字符
			else {
				int stack_prior = 0;
				//当符号的栈为空时，直接加入
				if(chara_stack.empty()) {
					chara_stack.add(s);
				}else {
				do {
				//计算运算符栈栈顶元素的优先级
				stack_prior = jud_prior(chara_stack.peek());
				//栈顶元素优先级高于待进栈元素优先级，弹出
				if(stack_prior!=3&&stack_prior>=prior) {
					cal_adv();
				}
			}while(!chara_stack.empty()&&stack_prior!=3&&stack_prior>=prior);
				if(chara_stack.empty()) {
					chara_stack.add(s);
				}else {
					stack_prior = jud_prior(chara_stack.peek());
				if(prior>stack_prior||stack_prior==3) {
					//栈顶元素的优先级小于等于待进栈元素的优先级且不为右括号，进栈
					if(prior!=4) {
						chara_stack.add(s);
					}else {
						//是右括号
						//不进栈，而是开始弹出数字做计算，直到遇到左括号为止
						mylabel2:
						while(jud_prior(chara_stack.peek())!=3) {
							cal_adv();
							continue mylabel2;
						}
						//遇到左括号了，弹出
						chara_stack.pop();
					}
				}
				}
				
			}
			}
		}

	
		
		//字符结束了
		//若字符栈没空，则继续弹出
		//逆序：
		//chara_stack = reverse(chara_stack);
		//numberstorage = reverse(numberstorage);
		while(!this.chara_stack.empty()) {
			cal_adv();
		}
		//返回最后得数：
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
		//补零
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
			try{
				er.express_split(input);
			}catch(error_expression e) {
				System.out.print("wrong - error expression");
			}
		}
		
	}
}

