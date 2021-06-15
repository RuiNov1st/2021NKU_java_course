package database;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

//利用形状类进行画图
class Shape implements Serializable{
    int x1,x2,y1,y2;//坐标
    String name;//类型
    String s;//文字
    Color c;//记录颜色
    boolean isfill;//记录是否填充
    Color bgc;//记录背景颜色
    public Shape(int x1,int y1,int x2,int y2,String name,Color c,boolean isfill,Color bgc) {
    	this.x1 = x1;
    	this.x2 = x2;
    	this.y1 = y1;
    	this.y2 = y2;
    	this.name = name;
    	this.c = c;
    	this.isfill = isfill;
    	this.bgc = bgc;
    }
    public Shape(int x1,int y1,String name,String s,Color c,Color bgc) {
    	this.x1 = x1;
    	this.y1 = y1;
    	this.name = name;
    	this.s = s;
    	this.c = c;
    	this.bgc = bgc;
    }
    //在形状类中进行重绘操作
    public void repaint(Graphics g) {
    	g.setColor(c);
    	Font font = new Font("华文行楷",Font.BOLD + Font.ITALIC,15);
		g.setFont(font);
    	switch(name) {
    	//直线
    	case "StraightLine":
    		g.drawLine(x1, y1, x2, y2);
    		break;
    	//曲线
    	case "RandomLine":
    		g.drawLine(x1, y1, x2, y2);
    		break;
    	//矩形
    	case "Rectangle":
    		if(isfill) {
    			g.fillRect(Math.min(x1,x2), Math.min(y1, y2), Math.abs(x1-x2), Math.abs(y1-y2));
    		}else {
    		g.drawRect(Math.min(x1,x2), Math.min(y1, y2), Math.abs(x1-x2), Math.abs(y1-y2));
    		}
    		break;
    	//圆形
    	case "Circle":
    		if(isfill) {
    			g.fillOval(x1, y1, x2, y2);
    		}else {
    		g.drawOval(x1, y1, x2, y2);
    		}
    		break;
    	//多边形
    	case "Polygon":
    		g.drawLine(x1, y1, x2, y2);
    		break;
    	//文本
    	case "Text":
    		g.drawString(s,x1,y1);
    		break;
    	}
    	
    }
}

class ButtonListener implements ActionListener,MouseListener,MouseMotionListener {
	String s = "";
	boolean flag = false;
	private int index=0;
	//private Shape ShArr[];
	private ArrayList<Shape>ShArr;
	//public ArrayList<Shape>ShArr;
	private JPanel jp;
	private java.awt.Graphics gr;
	private String commend="";
	boolean flag2 = false;
	int x0=0,y0=0,x1=0,y1=0,x2=0,y2=0,x3=0,x4=0,y3=0,y4=0,start_x=0,start_y=0;
	public void set_jp(JPanel jp) {
		this.jp=jp;
		}
	public void set_gr(java.awt.Graphics G) {
		this.gr = G;
	}
	public void set_ShArr(ArrayList<Shape>ShArr) {
		this.ShArr = ShArr;
	}
	public int get_len() {
		return index;
	}
	
	public void openpic() {
		//弹出选择对话框，选择需要读入的文件
		JFileChooser chooser = new JFileChooser();
		chooser.showOpenDialog(null);
		File file =chooser.getSelectedFile();
		//如果为选中文件
		if(file==null){
			JOptionPane.showMessageDialog(null, "没有选择文件");
		}
		else {
			try {
			ShArr.clear();
			//选中了相应的文件，则柑橘选中的文件创建对象输入流
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			//将读出来的对象转换成父类对象的容器进行接收
			ArrayList<Shape> l =(ArrayList<Shape>)ois.readObject();
			//遍历容器里面的具体对象，将取出来的对象保存到容器里面
			for (int i = 0; i <l.size(); i++) {
				Shape shape=(Shape)l.get(i);
				ShArr.add(shape);
			}
			ois.close();
			jp.paint(gr);
		}
		catch (Exception e1) {
		e1.printStackTrace();
	}
		}
}
	
	
	public void savepic() {
		//选择要保存的位置以及文件名字和信息
		JFileChooser chooser = new JFileChooser();
		chooser.showSaveDialog(null);
		File file =chooser.getSelectedFile();
				
		if(file==null){
			JOptionPane.showMessageDialog(null, "没有选择文件");
		}else {
			try {
			//根据要保存的文件创建对象输出流
			FileOutputStream fis = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fis);
			//将容器里面所绘制的图形利用对象流全部写入选中的文件中
			oos.writeObject(ShArr);
			JOptionPane.showMessageDialog(null, "保存成功！");
			oos.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}

	public void actionPerformed(ActionEvent e) 
	{
		// TODO Auto-generated method stub
		x1 = 200; y1 = 200;
		if (e.getActionCommand()=="") {
			JButton j = (JButton)e.getSource();
			gr.setColor(j.getBackground());
		}
		else {
			commend = e.getActionCommand();
			if("Clear".equals(commend)) {
				index = 0;
				ShArr.clear();
				jp.repaint();
				x4 = 0;
				y4 = 0;
			}else {
				if("Save".equals(commend)) {
					savepic();
				}else {
					if("Open".equals(commend)) {
						openpic();
					}else {
						if("yes".equals(commend)){
							flag2 = true;
						}else {
							if("no".equals(commend)) {
								flag2 = false;
							}
						}
					}
				}
			}
		}
	}
	public void mouseDragged(MouseEvent e) {
		//System.out.println("Drag");
		if("RandomLine".equals(commend)) {
			x1 = x0; y1 = y0; x0 = e.getX(); y0 = e.getY();
			gr.drawLine(x1,y1,x0,y0);
			//ShArr[index++] = new Shape(x1,y1,x0,y0,commend);
			ShArr.add(new Shape(x1,y1,x0,y0,commend,gr.getColor(),flag2,jp.getBackground()));
		}
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if("Polygon".equals(commend)) {
			
			if(x4==0&&y4==0){
				x4 = e.getX(); y4 = e.getY();
				start_x = x4; start_y = y4;
				//gr.drawLine(x3, y3, x4, y4);
			}
			else {
				x3 = x4; y3 = y4; x4 = e.getX(); y4 = e.getY();
				gr.drawLine(x3, y3, x4, y4);
				//ShArr[index++] = new Shape(x3,y3,x4,y4,commend);
				ShArr.add(new Shape(x3,y3,x4,y4,commend,gr.getColor(),flag2,jp.getBackground()));
			}
			if(e.getClickCount()==2){
			x4 =0; y4=0;
			gr.drawLine(start_x, start_y, e.getX(), e.getY());
			//ShArr[index++] = new Shape(start_x,start_y,e.getX(),e.getY(),commend);
			ShArr.add(new Shape(start_x,start_y,e.getX(),e.getY(),commend,gr.getColor(),flag2,jp.getBackground()));
			}	
		}else {
			if("Text".equals(commend)) {
				//获取坐标
				x4 = e.getX();
				y4 = e.getY();
				//jp.setLayout(null);
				JTextField jt = new JTextField();
				jp.add(jt);
				jt.setBounds(x4,y4,100,20);
				//阻塞式监听
				jt.addKeyListener(new KeyAdapter() {
					@Override
					public void keyTyped(KeyEvent ek) {
						if((char)ek.getKeyChar()==KeyEvent.VK_ENTER) {
							s = jt.getText();//获取文本
							Font font = new Font("华文行楷",Font.BOLD + Font.ITALIC,15);
							gr.setFont(font);
							gr.drawString(s,e.getX(),e.getY());
							//jp.remove(jt);//删去
							jt.setVisible(false);
							//ShArr[index++] = new Shape(x4,y4,commend,s);
							ShArr.add(new Shape(e.getX(),e.getY(),commend,s,gr.getColor(),jp.getBackground()));
							//jp.revalidate();
							//jp.repaint();
							//gr.drawString("hahaha", x4, y4);
							//System.out.print("Stirng set");
						}
					}
				});
			}
		}
		
	}

	@Override // 按下
	public void mousePressed(MouseEvent e) {
	//	System.out.println("按下");
		
		if("RandomLine".equals(commend)){
			x0 = e.getX();
			y0 = e.getY();
		}
		// TODO Auto-generated method stub
		x1 = e.getX();
		y1 = e.getY();
		
	}
	@Override // 松开
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		x2 = e.getX();
		y2 = e.getY();
	//	System.out.println("松开");
		if("StraightLine".equals(commend)){
			gr.drawLine(x1, y1, x2, y2);
			ShArr.add(new Shape(x1, y1, x2, y2,commend,gr.getColor(),flag2,jp.getBackground()));
			//System.out.print(ShArr.size());
			//ShArr[index++] = new Shape(x1,y1,x2,y2,commend);
		}
		else if("Rectangle".equals(commend)) {
			if(flag2) {
				gr.fillRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1-x2), Math.abs(y1-y2));
			}else {
			gr.drawRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1-x2), Math.abs(y1-y2));
			}
			ShArr.add(new Shape(x1,y1,x2,y2,commend,gr.getColor(),flag2,jp.getBackground()));
			//System.out.print(ShArr.size());
			//ShArr[index++] = new Shape(x1,y1,x2,y2,commend);
		}
		else if("Circle".equals(commend)) {
			if(flag2) {
				gr.fillOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1-x2), Math.abs(y1-y2));
			}else {
			gr.drawOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1-x2), Math.abs(y1-y2));
			}
			ShArr.add(new Shape(x1,y1,x2,y2,commend,gr.getColor(),flag2,jp.getBackground()));
			//System.out.print(ShArr.size());
		}
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	public void mouseMoved(MouseEvent e) {
		
	}
}

//用弹出式菜单设置背景颜色
class MenuButton extends JToggleButton{
	private JPopupMenu menu;
	public MenuButton(final String label){
		super(label);
		this.setText("▶"+label);
		this.setHorizontalTextPosition(SwingConstants.RIGHT );
		addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(isSelected()){
					setText("◀"+label);
					menu.show(MenuButton.this,MenuButton.this.getWidth(), MenuButton.this.getHeight()-30);
				}else{
					setText("▶"+label);
					menu.setVisible(false);
				}
			}
		});
	}
	public void addMenu(JPopupMenu menu){
		this.menu=menu;
	}
	
}

//颜色按钮，不当作匿名类
class ColorButton implements ActionListener{
	Color c;
	JButton jb;
	JPanel jp;
	JPanel setpanel;
	JPanel drawpanel;
	ColorButton(JPanel jp,JPanel setpanel,JPanel drawpanel){
		this.jp = jp;
		this.setpanel = setpanel;
		this.drawpanel = drawpanel;
	}
	public void actionPerformed(ActionEvent e) 
	{
			jb = (JButton)e.getSource();
			c = jb.getBackground();
			jp.setBackground(c);
			setpanel.setBackground(c);
			drawpanel.setBackground(c);
	}
}

public class drawline extends JPanel{
	int len = 0;
	private ArrayList<Shape>ShArr = new ArrayList<Shape>();
	public static void main(String[] args) {
		drawline p = new drawline();
	}

	JFrame jf = new JFrame();

	ButtonListener bl = new ButtonListener();
	
	JPanel drawpanel = new JPanel();
	JPanel setpanel = new JPanel();
	ColorButton cb = new ColorButton(this,drawpanel,setpanel);
	public drawline() {
		//实现一个窗体
		jf.setTitle("画图板");
		jf.setLocation(450, 100);
		jf.setSize(700, 600);
		//this.setSize(new Dimension(600, 500));
		//当采用布局的时候就需要用setpreferredsize
		this.setPreferredSize(new Dimension(600, 500));
		this.setBackground(new Color(238,229,248));
		drawpanel.setPreferredSize(new Dimension(600,35));
		drawpanel.setBackground(new Color(238,229,248));
		setpanel.setPreferredSize(new Dimension(110,50));
		setpanel.setBackground(new Color(238,229,248));
		//setpanel.setBackground(Color.black);
		//setpanel.setLayout(new BorderLayout());
		//声明两个数组，包含各种指令
		String[] command = { "Clear", "StraightLine", "RandomLine", "Polygon", "Rectangle","Circle","Text","Save","Open"};
		Color[] color = {Color.white,Color.black,new Color(193,203,215),new Color(238,229,248),new Color(234,208,209),new Color(210,118,163),new Color(129,92,148),new Color(186,204,217),new Color(185,222,201) };
		//设置布局为流式布局
		//jf.setLayout(new FlowLayout());
		jf.setLayout(new BorderLayout());
		jf.setResizable(false);

		for (int i = 0; i < command.length-2; i++) {
			JButton jb = new JButton(command[i]);
			jb.addActionListener(bl);
			drawpanel.add(jb);
		}
		jf.add(drawpanel,"North");
		
		//菜单项
		JMenu coloritem = new JMenu("BackGroundColor");
		//coloritem.setPreferredSize(new Dimension(35,10));
		JMenu coloritem2 = new JMenu("PenColor");
		JMenu coloritem3 = new JMenu("IsFill");
		//coloritem3.setLayout(new BorderLayout());
		for (int i = color.length - 1; i >= 0; i--) {
			JButton jb = new JButton();
			jb.setBackground(color[i]);//设置背景颜色
			Dimension dm = new Dimension(20, 20);//设置大小
			jb.setPreferredSize(dm);
			jb.addActionListener(cb);
			coloritem.add(jb);
		}
		JButton yesb = new JButton("yes");
		JButton nob = new JButton("no");
		Dimension dm1 = new Dimension(55, 20);//设置大小
		yesb.setPreferredSize(dm1);
		yesb.addActionListener(bl);
		nob.setPreferredSize(dm1);
		nob.addActionListener(bl);
		coloritem3.add(yesb);
		coloritem3.add(nob);
		//弹出式菜单设计
		JPopupMenu penmenu=new JPopupMenu();
		//penmenu.setPreferredSize(new Dimension(30,20));
		penmenu.add(coloritem);
		penmenu.add(coloritem2);
		penmenu.add(coloritem3);
				        
		//创建按钮
		MenuButton penbutton1=new MenuButton("SetUp");
        
		//把菜单放到按钮上
		penbutton1.addMenu(penmenu);
		penbutton1.setPreferredSize(new Dimension(100,30));
		//把按钮放到控件上
		setpanel.add(penbutton1,"North");
		//保存和打开按钮
		for(int i = command.length-2;i<command.length;i++) {
			JButton jb = new JButton(command[i]);
			jb.addActionListener(bl);
			jb.setPreferredSize(new Dimension(100,25));
			setpanel.add(jb,"North");
		}
		jf.add(setpanel,"West");	
		
		for (int i = color.length - 1; i >= 0; i--) {
			JButton jb = new JButton();
			jb.setBackground(color[i]);//设置背景颜色
			Dimension dm = new Dimension(25, 20);//设置大小
			jb.setPreferredSize(dm);
			jb.addActionListener(bl);
			coloritem2.add(jb);
		}
		
		//将JPanel对象添加进入jf窗体对象中，让他生效
		jf.add(this,"Center");
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);	//设置可见
				
		
		Graphics gf = this.getGraphics();	//获取画笔，我们的this代表当前类的对象，正好是一个JPanel的对象
		this.addMouseListener(bl);		//添加鼠标监听器，用于画图
		this.addMouseMotionListener(bl);	//添加鼠标模式监听器，用于绘画曲线
		
		bl.set_jp(this);	//设置另外一个类的JPanel容器
		bl.set_gr(gf);		//设置另外一个类的画笔
		bl.set_ShArr(ShArr);	//设置另外一个类的数组
	}
	public void paint(Graphics g) {
		super.paint(g);		//调用父类的paint方法，用来画出窗体
		//len = bl.get_len();	//获取数组的长度
		int size = ShArr.size();
		//重绘我们的图案
		if(size!=0) {
			this.setBackground(ShArr.get(0).bgc);
			setpanel.setBackground(ShArr.get(0).bgc);
			drawpanel.setBackground(ShArr.get(0).bgc);
		}
		for(int i=0;i<size;i++) {
			if(ShArr.get(i)!=null) {
				//重写绘画图像，但是我只重绘了图形，忘记加颜色了；
				ShArr.get(i).repaint(g);
			}
			else {
				break;
			}
		}
	}

}







