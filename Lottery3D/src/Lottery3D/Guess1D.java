package Lottery3D;

import java.util.Scanner;

class Guess1D extends Lottery3D {
	//记录是猜中1或2或3
	int label = 0;//默认没猜中
	int standcount = 1;
	
	void print_sen() {
		System.out.print("请输入一个数字");
	}
	int getstancount() {
		return standcount;
	}
	
	//只输入一个数字
	boolean isAvailable(String s) {
		//长度检查
		if(s.length()!=getstancount()) {return false;}
		//判断输入是否为数字
		for(int i = 0;i<getstancount();i++) {
			char a = s.charAt(i);
			if(a<48||a>57) {
			return false;
		}
		}
		return true;
	}
	//判断输赢并确定猜中几
	boolean judwin() {
		boolean flag = true;
		//位置处理
		numbercount();
		label = this.countwin[this.userNumber[0]];
		if(label == 0) {
			flag = false;
		}
		return flag;
	}
	//奖金数量
	int getWins() {
		switch(label) {
		case 1:return 2;
		case 2:return 12;
		default:return 230;
		}
	}
	
	
}

class Guess2D extends Guess1D{
	int standcount = 2;
	int label = 1 ;//判断是两同号还是两不同号：1-默认为两不同号，2-为两同号
	//输入
	void print_sen() {
		System.out.print("请输入两个数字");
	}
	int getstancount() {
		return standcount;
	}
	//判断输赢并确定猜中几
	boolean judwin() {
		boolean flag = true;
		//个数处理
		numbercount();
		for(int i = 0;i<this.standcount;i++) {
			if(this.countinput[this.userNumber[i]]==this.countwin[this.userNumber[i]]) {
				if(this.countinput[this.userNumber[i]]>=2) {
					label = 2;
				}
			}else {
				flag = false;
			}
		}
		
		return flag;
	}
	//获取奖金
	int getWins() {
		if(label==1) {
			return 19;
		}else {
			return 37;
		}
	}
}

//猜三同
class ThreeSame extends Lottery3D{
	int label = 0;//记录是否开出豹子数，默认为0-不开，1-开
	//输出
	void print_sen() {
		System.out.print("请输入“Yes”或者“No”");
	}
	//判断输入是否正确
	boolean isAvailable(String s) {
		choice[0] = "Yes";
		choice[1] = "No";
		return CharaLegal(s);
		
	}
	//判断是否获奖
	boolean judwin() {
		//处理个数
		this.numbercount();
		//开出豹子数了
		if(this.countwin[this.winNumber[0]]==3) {
			if(label_high==1) {
				return true;
			}
		}else {
			//没开出豹子数
			if(label_high==0) {
				return true;
			}
		}
		return false;
	}
	//取得奖金
	int getWins() {
		return 104;
	}
	
}
