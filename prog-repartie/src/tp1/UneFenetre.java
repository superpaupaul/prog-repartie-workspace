package tp1;

import java.awt.Container;

import javax.swing.JFrame;

public class UneFenetre extends JFrame{
	public UnMobile sonMobile;
	private final int LARG = 400, HAUT = 250;
	
	UneFenetre()
	{
		super("le Mobile");
		Container leConteneur = getContentPane();
		sonMobile = new UnMobile(LARG,HAUT);
		leConteneur.add(sonMobile);
		Thread laTache = new Thread(sonMobile); // sonMobile = Runnable
		laTache.start();
		setSize(LARG,HAUT);
		setVisible(true);
	}
}
