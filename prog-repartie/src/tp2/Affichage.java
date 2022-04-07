package tp2;
import java.lang.String;

public class Affichage extends Thread{
	String texte; 

	public Affichage (String txt){texte=txt;}
	
	public void run()
	{
		afficherTexte(texte);
	}

	private synchronized static void afficherTexte(String texte) // on la met en statique car sinon, chacun des threads vont executer leur propre méthode afficherTexte, or ici ils vont se partager la fonction statique
	{
		for (int i=0; i<texte.length(); i++){
		    System.out.print(texte.charAt(i));
		    try {sleep(100);} catch(InterruptedException e){};
		}
	}
	/*
	On ne peut pas mettre synchronized sur run directement car sinon le thread entier serait synchronized ce qui ne fait aucun sens
	On aurait pu aussir synchroniser simplement le bloc de code dans le run mais cela aurait impliqué de créer une classe bidon pour y mettre un objet de référence, ce serait revenu au même
	*/
}
