package Lottery3D;

//����
class sum extends Lottery3D {
	//��ͬ�����Ľ�������
	int []bouns = {1040,345,172,104,69,49,37,29,23,19,16,15,15,14};
	int sum1 = 0;
	int index = 0;
	int rec = 0;
	
	//��ͬ�������Ų�ͬ���������
	void print_sen() {
		System.out.print("������������֮��\n");
	}
	//������
	boolean isAvailable(String s) {
		boolean flag = true;
		//���ֵķǷ��Լ��
		flag = this.NumLegal(s);
		
		if(flag) {
		//ת����ֵ
		rec = Integer.parseInt(s);
		//��ֵ����0~27֮��
		if(rec<0||rec>27) {
			flag =  false;
		}
	}
		return flag;
}
	//�ж��Ƿ��н�
	boolean judwin() {
		boolean flag = true;
		//����񽱵���
		for(int i = 0;i<length;i++) {
			sum1 = sum1+this.winNumber[i];
		}
		if(rec!=sum1) {
			flag = false;
		}
		return flag;
	}
	//��ȡ����
	int getWins() {
		if(sum1>13) {
			index = 27-sum1;
		}else {
			index = sum1;
		}
		return bouns[index];
	}

}

//�´�С
class LargeSmall extends Lottery3D{
	int sum1 = 0;
		//��ͬ�������Ų�ͬ���������
		void print_sen() {
			System.out.print("�����롰Large����Small��\n");
		}
		//�ж������Ƿ�Ϸ�
		boolean isAvailable(String s) {
			choice[0] = "Large";
			choice[1] = "Small";
			return this.CharaLegal(s);
		}
		//�ж��Ƿ��н�
		boolean judwin() {
			boolean flag = false;
			//����񽱵���
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
		//ȡ�ý���
		int getWins() {
			return 6;
		}
		
}

