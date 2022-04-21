package tp3;

import java.awt.*;

import javax.swing.*;

public class UneFenetre extends JFrame{
	public int nbMobiles = 8;
	public UnMobile[] mobiles;
	public JButton[] boutonsOnOff;
	public Thread[] taches;
	public boolean[] isTacheRunning;
	private final int LARG = 800, HAUT = 250;
	private final int NBLIG = nbMobiles, NBCOL = 2;

	
	UneFenetre()
	{
		super("le Mobile");

		Container leConteneur = getContentPane();
		leConteneur.setLayout(new GridLayout(NBLIG,NBCOL));

		this.boutonsOnOff = new JButton[nbMobiles];
		this.mobiles = new UnMobile[nbMobiles];
		this.taches = new Thread[nbMobiles];
		this.isTacheRunning = new boolean[nbMobiles];

		for(int i = 0; i < nbMobiles; i++)
		{
			final Integer innerI = new Integer(i);
			this.boutonsOnOff[i] = new JButton("Suspend / Resume " + i);
			this.boutonsOnOff[i].addActionListener(e -> btnOnOffPressed(innerI));
			mobiles[i] = new UnMobile(LARG, HAUT/nbMobiles);
			taches[i] = new Thread(mobiles[i]); 
			taches[i].start();
			isTacheRunning[i] = true;
			leConteneur.add(mobiles[i]); // Mobile = Runnable
			leConteneur.add(boutonsOnOff[i]);
		}

		setSize(LARG,HAUT);
	}

	private void btnOnOffPressed(int idBouton)
	{
		if(isTacheRunning[idBouton])
				{
					taches[idBouton].suspend();
					isTacheRunning[idBouton] = false;
				}
				else
				{
					taches[idBouton].resume();
					isTacheRunning[idBouton] = true;
				}
	}
}
