package Lottery3D;

//������
//��ָ��ȫ������������������еĺ������Ͷע��890��098��901��109���⣩
class Tractor extends Lottery3D{
	int label1 = 0;//��¼�µĿ��Ͳ���
	
	//���
	void print_sen() {
		System.out.print("�����롰Yes�����ߡ�No��");
	}
	//�ж����������Ƿ�Ϸ�
	boolean isAvailable(String s) {
		//���ø������������뷶Χ
		choice[0] = "Yes";
		choice[1] = "No";
		//�����ֵ������Ƿ��ڹ涨��Χ�ڽ��м��
		return this.CharaLegal(s);
	}
	//�ж��Ƿ��
	//�������Ĺ���Ϊ������߽��򶼿��ԡ�
	boolean judwin() {
		//�������
		this.numbercount();
		 int a = this.winNumber[0];
		 int b = this.winNumber[1];
		 int c = this.winNumber[2];
		//�ж�����
		if(a==b-1&&a==c-2&&b==c-1) {
			label1 = 1;
		}
		//�жϽ���
		if(c==b-1&&c==a-2&&a-1==b) {
			label1 = 1;
		}
		if(label_high == label1) {
			return true;
		}else {
			return false;
		}
	}
	
	//��ý���
	int getWins() {
		return 65;
	}

}

//����������ż��
class OddEven extends Lottery3D{
	int label1 = 0;//Ĭ��Ϊż��
	//���
	void print_sen() {
		System.out.print("�����롰Odd�����ߡ�Even��");
	}
	//�ж������Ƿ�Ϸ�
	boolean isAvailable(String s) {
		choice[0] = "Odd";
		choice[1] = "Even";
		return this.CharaLegal(s);
	}
	//�ж��Ƿ��
	boolean judwin() {
		//�������
		this.numbercount();
		int i = 1;
		int j = 0;
		int count1 = 0;//��������
		int count2 = 0;//����ż��
		
		while(j<this.countwin.length-2&&i<this.countwin.length-1) {
			count1+=this.countwin[i];
			count2+=this.countwin[j];
			i+=2;
			j+=2;
		}
		//����
		if(count1==3&&label_high==1) {
			return true;
		}else {
			//ż��
			if(count2==3&&label_high==0) {
				return false;
			}
		}
		return false;
	}
	//ȡ�ý���
	int getWins() {
		return 8;
	}
	
	
}
