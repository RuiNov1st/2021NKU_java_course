package Lottery3D;

//包选
//内部类进行多态性继承
public class Package extends Lottery3D {
	int label_pro = 0;//记录是哪种获奖方式，默认什么都不是
	//包选3全中：1
	//包选3组中：2
	//包选6全中：3
	//包选6组中: 4
	
	void print_sen() {
		System.out.print("请输入0~999之间的整数\n");
	}
	
	//一个类继承Single类
	//三位全部相同
	class PackSing extends Single{
		
	}
	//一个类继承group类
	//可以不顾顺序
	class PackGroup extends group{
		
	}
	//判断是否获奖
	boolean judwin() {
		int count = 1;//每个数都不相同
		//处理个数
		this.numbercount();
		//确定个数
		for(int i = 0;i<length;i++) {
			if(this.countwin[this.winNumber[i]]>=2) {
				count = 2;//存在两个数相同
			}
		}
		boolean flag1 = true;
		//先判断是否全中
		PackSing ps = new PackSing();
		flag1 = ps.judwin(this.winNumber,this.userNumber);
		if(flag1) {
			if(count==2) {
				label_pro = 1;
			}else {
				label_pro =3;
		    }
		}else {
			//再判断是否组中
			PackGroup pg = new PackGroup();
			flag1 = pg.judwin(this.winNumber,this.userNumber);
			if(flag1) {
				if(count==2) {
					label_pro = 2;
				}else {
					label_pro = 4;
				}
		}
		}
		return flag1;
	}
	//获取奖金
	int getWins() {
		int bouns = 0;
		switch(label_pro) {
		case 1:bouns = 693;break;
		case 2:bouns = 173;break;
		case 3:bouns = 606;break;
		case 4:bouns = 86;break;
		}
		return bouns;
	}
}
