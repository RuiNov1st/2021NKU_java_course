package game;

import java.util.*;
//��Ϸ����
// 1.�����ɫ����
// 2.ʵ�ֽ�ɫ����Game1�̳У�
// 3. �����Ա��������Game1�̳У�
abstract class Game {
	//��ɫ��������
	int actortypenum = 2;
	//��ɫ����
	Actor a1;
	Actor a2;//ϵͳ���
	
	//��Ա����
	//���ý�ɫ���ͣ����������ɫ��ϵͳ��ɫ��
	abstract Actor randomActor(boolean human);
	
	//�����û�����Ľ�ɫ�����ض���ɫ
	abstract Actor setActor(String type);
	
	abstract void play();

}
