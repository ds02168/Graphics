package myPicture;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
//������� ����

public class MidPoint extends JFrame{
	private MyPanel panel = new MyPanel();//�г� ��ü ����
	private JLabel la = new JLabel();//���̺� ��ü ����
	public int i;
	private double dxy;//����
	private double m;//�ȼ��� x�� �ּڰ�
	private int x1,y1,x2,y2;//�������� x0,y0,x1,y1�� ��ǥ��
	private int mx,my,mxy;//�ȼ��� x1,y0,y1�� ��ǥ��
	private int dx,dy,d;//�ȼ��� x������,y�� ������, ����
	private int incrE,incrNE;//���� E�� NE�ȼ� ���ý�
	private String rubber;//������� ������ ���� ���� ��Ʈ��
	public MidPoint() {
		setTitle("MidPoint Algorithm");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(panel);
		Container c = getContentPane();
		c.setBackground(Color.WHITE);
		setLayout(null);
		
		la.setSize(1000,100);
		la.setLocation(100,500);
		
		c.add(la);
		c.addMouseListener(panel);
		c.addMouseMotionListener(panel);
		setSize(1200,600);
		setVisible(true);
	} //�ȼ��� �׸� ��ȭ���� ����
	
	class MyPanel extends JPanel implements MouseListener, MouseMotionListener{
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.BLACK);
			g.drawLine(1, 1, 1100, 1);
			g.drawLine(1, 1, 1, 500);
			g.drawLine(1100, 0, 1100, 500);
			for(i=1;i<11;i++)
			{
				g.drawLine(i*100, 0, i*100, 500);
				g.setColor(Color.RED);
				g.drawLine((i*100)-10, 50, (i*100)+10, 50);
				g.drawLine((i*100)-10, 150, (i*100)+10, 150);
				g.drawLine((i*100)-10, 250, (i*100)+10, 250);
				g.drawLine((i*100)-10, 350, (i*100)+10, 350);
				g.drawLine((i*100)-10, 450, (i*100)+10, 450);
				g.setColor(Color.BLACK);
			}
			for(i=1;i<6;i++)
			{
				g.drawLine(0, i*100, 1100, i*100);	
			} // �¿� 100�ȼ��� ���� 100�ȼ��� �������� �׸��� ���ڸ� �׸�
			  //���� 100�ȼ�=������ ����� 1�ȼ�
			
			dxy = ((double)(500-y2)-(double)(500-y1))/((double)x2-(double)x1);//���� 
			m=Math.ceil((double)x1/100)*100;//������ ����ͻ� x0��ǥ
			mx=(int)Math.floor((double)x2/100)*100;//������ ����ͻ� x1��ǥ
			my=(int)(Math.round((dxy*(m-(double)x1)+(double)y1)/100)*100);//������ ����ͻ� y0��ǥ
			mxy=(int)(Math.round((dxy*(mx-(double)x2)+(double)y2)/100)*100);//������ ����ͻ� y1��ǥ
			//������ ����ͻ� ��ġ�� ����
			
			if("RELEASE".equals(rubber)) //��ư�� ����
				g.setColor(Color.RED); //������
			else //�巡�� �� �Ͻ�
				g.setColor(Color.BLUE);//�Ķ���

			g.drawLine((int)m, my, mx, mxy); //�������� ���� �׸�
			
			//�巡�����϶��� ��ư�� �������� ���� �޸� �����ν� ������� ���ü� ����
			
			
			//ȭ�鿡 (x0,y0), (x1,y1)��ǥ�� ���λ����� ������
			la.setText("(x1, y1) : (" + (int)m + ", " + (500-my) +
					")  (x2, y2) : (" + mx + ", " + (500-mxy) + ")   " + "�糡���� �ʷϻ� ��, �������: �巡�׽� �Ķ���");

			//MidPoint �˰���
			dx=mx-(int)m;
			dy=(500-mxy)-(500-my);
			d=dy*2-dx;
			incrE=dy*2;
			incrNE=(dy-dx)*2;
			
			//���� �� ������ ������ ���� �ʷϻ����� ����
			g.setColor(Color.GREEN);
			if((m!=0)&&(mx!=0)) {
			g.fillOval((int)m-20, my-20 , 40, 40);
			g.fillOval((int)mx-20, mxy-20 , 40, 40);
			}
			
			if("RELEASE".equals(rubber)) //��ư�� ����
				g.setColor(Color.RED); //������
			else //�巡�� �� �Ͻ�
				g.setColor(Color.BLUE);//�Ķ���
			//�巡�����϶��� ��ư�� �������� ���� �޸� �����ν� ������� ���ü� ����
			
			//MidPoint �˰���
			for(i=(int)m+100;i<mx;i+=100) { //���� 100�ȼ� = ������ 1�ȼ�
				if(d<=0) {
					d += incrE;
				} else {
					d += incrNE;
					my-=100;//�ڹ� JPanel�� ��ܺ��� �ϴܱ��� 0���� �����̹Ƿ� �ݴ�� -100���Ͽ� ���� �ö�
				}
				
				g.fillOval(i-20, my-20, 40, 40);
			}
			
		}
		
		public void mousePressed(MouseEvent e) { //���콺�� ��������
			x1=e.getX(); 
			y1=e.getY(); //x0,y0�� ��ǥ���� ����
		}
		public void mouseReleased(MouseEvent e) { //���콺�� ������
			x2=e.getX();
			y2=e.getY();//x1,y1�� ��ǥ���� ����
			rubber = "RELEASE"; //RELEASE�� ���¸� �ٲ�
			panel.repaint();//������� �ֽ�ȭ
		}
		public void mouseDragged(MouseEvent e) {//�巡�� ���϶� (�������)
			x2=e.getX();
			y2=e.getY();//x1,y1�� ��ǥ���� ����
			rubber = "DRAG";//DRAG�� ���¸� �ٲ�
			panel.repaint();//������� �ֽ�ȭ
		}
		public void mouseClicked(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mouseMoved(MouseEvent e) {}
		//�������̽� �������̵�
		
		
	}
	
	
	
	public static void main(String[] args) {
		new MidPoint(); //MidPoint ��ü ����
	}

}
