package helloworld;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Collections;
import java.math.*;
import java.text.DecimalFormat;   

//ʹ��ջ���б��ʽ��ֵ
class stack_java_last{
	
	Stack number_int;//������
	Stack number_flo;//��С��
	Stack<String> chara_stack;//���������
	String input;//�洢������ʽ
	String[] input_deal;//�洢����õ�������ʽ
	Stack<String> numberstorage;//�洢���ֵĴ�����
	Queue<String> charastorage;//�洢���ŵĴ�����
	
	//���캯��
	stack_java_last(String input){
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
		tt = temp.split("(?<=[\\+\\-\\*\\/\\(\\)%])|(?=[\\+\\-\\*\\/\\(\\)%])");
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


