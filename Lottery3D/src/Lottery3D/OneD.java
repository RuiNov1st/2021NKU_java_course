package Lottery3D;

//1D���԰�λ��ʮλ���λ��ĳһ�ض�λ���ϵĺ������Ͷע
class OneD extends Lottery3D {
	int stancount = 1;
	
	//��ͬ�������Ų�ͬ���������
	void print_sen() {
		System.out.print("������ȷ��λ�õ�һ�����֣�����λ����*�����磬���ȷ����λ��Ϊ2��������**2\n");
	}
	 int getstancount() {
		return stancount;
	}
	//�ж������Ƿ�Ϸ�
	//1Dֻ������һ��λ�ô������֣�����λ��Ϊ�Ǻ�
	boolean isAvailable(String s) {
		//���ȼ��
		if(s.length()!=length) {return false;}
		boolean flag = true;
		int count = 0;
		char a = ' ';
		int j = 0;
		for(int i = 0;i<length;i++) {
			a = s.charAt(i);
			//������
			if(a>=48&&a<=57) {
				count++;
				location[j] = i;
				j++;
			}else {
				//�������˱�ķ��ţ�Ҳ��Ƿ�
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
	
	
	//�ж��Ƿ��
	boolean judwin() {
		boolean flag = true;
		if(this.winNumber[location[0]]!=this.userNumber[location[0]]) {
			flag = false;
		}
		return flag;
	}
	//��ȡ����
	int getWins() {
		return 10;
	}

}


//2D:��2�����룬������ȷ��λ����ͬ
class TowD extends OneD{
	int stancount = 2;
	//��ͬ����
	void print_sen() {
		System.out.print("������ȷ��λ�õ��������֣�����λ����*�����磬���ȷ��ʮλ���͸�λ���ֱ�Ϊ1��2��������*12\n");
	}
	//��ȡ��Ա����
	int getstancount() {
		return stancount;
	}
	//�ж��Ƿ��
	boolean judwin() {
		boolean flag = true;
		for(int i = 0;i<this.stancount;i++) {
			if(this.winNumber[location[i]]!=this.userNumber[location[i]]) {
				flag = false;
			}
		}
		return flag;
	}
	//��ȡ����
	int getWins() {
		return 104;
	}

}

