package Lottery3D;

//组选
class group extends Lottery3D {
	
	//组选3和组选6的标志
	int label = 1;//0-为组选3（有两个数字相同），1-为组选6（三个数字都不同）
	void print_sen() {
		System.out.print("请输入0~999之间的整数\n");
	}
	//判断是否中奖
	boolean judwin() {
		//需要个数时进行处理
		numbercount();
		boolean flag = true;
		for(int i = 0;i<length;i++) {
			//若存在个数不相同，则为False
			if(!(countwin[this.winNumber[i]]==countinput[this.winNumber[i]])) {
				flag = false;
				break;
			}else {
				if(countwin[this.winNumber[i]]>=2) {
					label = 0;
				}
			}
		}
		return flag;
	}
	boolean judwin(Integer[]winNumber,Integer[]userInput) {
		this.winNumber = winNumber;
		this.userNumber = userInput;
		return judwin();
	}
	//奖金
	int getWins() {
		if(label == 1) {
			return 173;
		}else {
			return 346;
		}
	}
}
