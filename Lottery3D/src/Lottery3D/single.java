package Lottery3D;

//单选
//对三个号码以唯一的排列方式进行投注
//购买号码与开奖号码完全相同，且位置相同
class Single extends Lottery3D{
	
	int error = 0;//记录错误数
	
	//不同输入有着不同的输入语句
	void print_sen() {
		System.out.print("请输入000~999之间的整数\n");
	}
	
	//判断是否获奖
	//既保持了Lottery3D的一致性，也可以使通选拓展
	boolean Judwin() {
		boolean flag = true;
		for(int i = 0;i<length;i++) {
			if(this.winNumber[i]!=this.userNumber[i]) {
				flag = false;
				error++;
			}
		}
		return flag;
	}
	boolean judwin() {
		return Judwin();
	}
	boolean judwin(Integer[]winNumber,Integer[]userInput) {
		this.winNumber = winNumber;
		this.userNumber = userInput;
		return Judwin();
	}
	//获取奖金：
	int getWins() {
		return 1040;
	}
	
}
//通选：
//两次中奖机会，先中通选1，若不中可再中通选2
class General extends Single{
	int label = 0;//记录是通选1还是通选2，默认什么都不是，1-为通选1，2为通选2
	
	boolean judwin() {
		boolean flag_pro = true;
		flag_pro = Judwin();
		if(flag_pro) {
			label = 1;
		}else {
			if(error==1) {
				label = 2;
			}
		}
		if(label==0) {
			flag_pro =  false;
		}else {
			flag_pro = true;
		}
		return flag_pro;
	}
	//获取奖金
	int getWins() {
		if(label ==1) {
			return 470;
		}else {
			return 21;
		}
	}

}

