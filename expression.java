package helloworld;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Collections;
import java.math.*;
import java.text.DecimalFormat;   

//使用栈进行表达式求值
class stack_java_last{
	
	Stack number_int;//存整数
	Stack number_flo;//存小数
	Stack<String> chara_stack;//存运算符号
	String input;//存储运算表达式
	String[] input_deal;//存储处理好的运算表达式
	Stack<String> numberstorage;//存储数字的处理结果
	Queue<String> charastorage;//存储符号的处理结果
	
	//构造函数
	stack_java_last(String input){
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


/*
class expression {
	public static void main(String []arg) {
		String input = "";
		Scanner sc = new Scanner(System.in);
		input = sc.nextLine();
		input = input.replace(" ", "");
		sc.close();
		stack_java sj = new stack_java(input);
		sj.input_split();
		System.out.print(sj.calculation());
		}
	}
	*/


