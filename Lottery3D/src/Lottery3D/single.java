package Lottery3D;

//��ѡ
//������������Ψһ�����з�ʽ����Ͷע
//��������뿪��������ȫ��ͬ����λ����ͬ
class Single extends Lottery3D{
	
	int error = 0;//��¼������
	
	//��ͬ�������Ų�ͬ���������
	void print_sen() {
		System.out.print("������000~999֮�������\n");
	}
	
	//�ж��Ƿ��
	//�ȱ�����Lottery3D��һ���ԣ�Ҳ����ʹͨѡ��չ
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
	//��ȡ����
	int getWins() {
		return 1040;
	}
	
}
//ͨѡ��
//�����н����ᣬ����ͨѡ1�������п�����ͨѡ2
class General extends Single{
	int label = 0;//��¼��ͨѡ1����ͨѡ2��Ĭ��ʲô�����ǣ�1-Ϊͨѡ1��2Ϊͨѡ2
	
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
	//��ȡ����
	int getWins() {
		if(label ==1) {
			return 470;
		}else {
			return 21;
		}
	}

}

