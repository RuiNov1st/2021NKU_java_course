package Lottery3D;

//和数
class sum extends Lottery3D {
	//不同和数的奖金数量
	int []bouns = {1040,345,172,104,69,49,37,29,23,19,16,15,15,14};
	int sum1 = 0;
	int index = 0;
	int rec = 0;
	
	//不同输入有着不同的输入语句
	void print_sen() {
		System.out.print("请输入三个数之和\n");
	}
	//输入检查
	boolean isAvailable(String s) {
		boolean flag = true;
		//数字的非法性检查
		flag = this.NumLegal(s);
		
		if(flag) {
		//转化数值
		rec = Integer.parseInt(s);
		//数值不在0~27之间
		if(rec<0||rec>27) {
			flag =  false;
		}
	}
		return flag;
}
	//判断是否中奖
	boolean judwin() {
		boolean flag = true;
		//计算获奖的数
		for(int i = 0;i<length;i++) {
			sum1 = sum1+this.winNumber[i];
		}
		if(rec!=sum1) {
			flag = false;
		}
		return flag;
	}
	//获取奖金
	int getWins() {
		if(sum1>13) {
			index = 27-sum1;
		}else {
			index = sum1;
		}
		return bouns[index];
	}

}

//猜大小
class LargeSmall extends Lottery3D{
	int sum1 = 0;
		//不同输入有着不同的输入语句
		void print_sen() {
			System.out.print("请输入“Large”或“Small”\n");
		}
		//判断输入是否合法
		boolean isAvailable(String s) {
			choice[0] = "Large";
			choice[1] = "Small";
			return this.CharaLegal(s);
		}
		//判断是否中奖
		boolean judwin() {
			boolean flag = false;
			//计算获奖的数
			for(int i = 0;i<length;i++) {
				sum1 = sum1+this.winNumber[i];
			}
			if(sum1>=19&&label_high==1) {
				return true;
			}else {
				if(sum1<19&&label_high==0) {
					return true;
				}
			}
			return flag;
			
		}
		//取得奖金
		int getWins() {
			return 6;
		}
		
}

