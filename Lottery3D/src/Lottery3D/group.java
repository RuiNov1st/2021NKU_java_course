package Lottery3D;

//��ѡ
class group extends Lottery3D {
	
	//��ѡ3����ѡ6�ı�־
	int label = 1;//0-Ϊ��ѡ3��������������ͬ����1-Ϊ��ѡ6���������ֶ���ͬ��
	void print_sen() {
		System.out.print("������0~999֮�������\n");
	}
	//�ж��Ƿ��н�
	boolean judwin() {
		//��Ҫ����ʱ���д���
		numbercount();
		boolean flag = true;
		for(int i = 0;i<length;i++) {
			//�����ڸ�������ͬ����ΪFalse
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
	//����
	int getWins() {
		if(label == 1) {
			return 173;
		}else {
			return 346;
		}
	}
}
