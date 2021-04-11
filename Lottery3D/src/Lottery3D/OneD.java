package Lottery3D;

//1D：对百位、十位或个位中某一特定位置上的号码进行投注
class OneD extends Lottery3D {
	int stancount = 1;
	
	//不同输入有着不同的输入语句
	void print_sen() {
		System.out.print("请输入确定位置的一个数字，其他位输入*，例如，如果确定个位数为2，请输入**2\n");
	}
	 int getstancount() {
		return stancount;
	}
	//判断输入是否合法
	//1D只允许有一个位置存在数字，其他位置为星号
	boolean isAvailable(String s) {
		//长度检查
		if(s.length()!=length) {return false;}
		boolean flag = true;
		int count = 0;
		char a = ' ';
		int j = 0;
		for(int i = 0;i<length;i++) {
			a = s.charAt(i);
			//是数字
			if(a>=48&&a<=57) {
				count++;
				location[j] = i;
				j++;
			}else {
				//若输入了别的符号，也算非法
				if(a!=42) {
					flag = false;
					return flag;
				}
			}
		}
		if(count!=getstancount()) {
			flag = false ;
		}else {
			flag = true;
		}
		return flag;
	}
	
	
	//判断是否获奖
	boolean judwin() {
		boolean flag = true;
		if(this.winNumber[location[0]]!=this.userNumber[location[0]]) {
			flag = false;
		}
		return flag;
	}
	//获取奖金
	int getWins() {
		return 10;
	}

}


//2D:买2个号码，数字正确，位置相同
class TowD extends OneD{
	int stancount = 2;
	//不同输入
	void print_sen() {
		System.out.print("请输入确定位置的两个数字，其他位输入*，例如，如果确定十位数和个位数分别为1和2，请输入*12\n");
	}
	//获取成员变量
	int getstancount() {
		return stancount;
	}
	//判断是否获奖
	boolean judwin() {
		boolean flag = true;
		for(int i = 0;i<this.stancount;i++) {
			if(this.winNumber[location[i]]!=this.userNumber[location[i]]) {
				flag = false;
			}
		}
		return flag;
	}
	//获取奖金
	int getWins() {
		return 104;
	}

}

