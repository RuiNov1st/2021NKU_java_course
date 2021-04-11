package Lottery3D;

//拖拉机
//是指对全部以升序或降序连续排列的号码进行投注（890、098、901、109除外）
class Tractor extends Lottery3D{
	int label1 = 0;//记录猜的开和不开
	
	//输出
	void print_sen() {
		System.out.print("请输入“Yes”或者“No”");
	}
	//判断文字输入是否合法
	boolean isAvailable(String s) {
		//设置给定的文字输入范围
		choice[0] = "Yes";
		choice[1] = "No";
		//对文字的输入是否在规定范围内进行检查
		return this.CharaLegal(s);
	}
	//判断是否获奖
	//拖拉机的规则为升序或者降序都可以。
	boolean judwin() {
		//处理个数
		this.numbercount();
		 int a = this.winNumber[0];
		 int b = this.winNumber[1];
		 int c = this.winNumber[2];
		//判断升序
		if(a==b-1&&a==c-2&&b==c-1) {
			label1 = 1;
		}
		//判断降序
		if(c==b-1&&c==a-2&&a-1==b) {
			label1 = 1;
		}
		if(label_high == label1) {
			return true;
		}else {
			return false;
		}
	}
	
	//获得奖金
	int getWins() {
		return 65;
	}

}

//猜奇数还是偶数
class OddEven extends Lottery3D{
	int label1 = 0;//默认为偶数
	//输出
	void print_sen() {
		System.out.print("请输入“Odd”或者“Even”");
	}
	//判断输入是否合法
	boolean isAvailable(String s) {
		choice[0] = "Odd";
		choice[1] = "Even";
		return this.CharaLegal(s);
	}
	//判断是否获奖
	boolean judwin() {
		//处理个数
		this.numbercount();
		int i = 1;
		int j = 0;
		int count1 = 0;//计算奇数
		int count2 = 0;//计算偶数
		
		while(j<this.countwin.length-2&&i<this.countwin.length-1) {
			count1+=this.countwin[i];
			count2+=this.countwin[j];
			i+=2;
			j+=2;
		}
		//奇数
		if(count1==3&&label_high==1) {
			return true;
		}else {
			//偶数
			if(count2==3&&label_high==0) {
				return false;
			}
		}
		return false;
	}
	//取得奖金
	int getWins() {
		return 8;
	}
	
	
}
