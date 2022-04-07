package tp2;
import java.lang.String;

public class Affichage extends Thread{
	String texte; 
	semaphoreBinaire sem;

	public Affichage (String txt, semaphoreBinaire sem)
	{
		texte=txt;
		this.sem=sem;
	}
	
	public void run()
	{
		afficherTexte(texte,sem);
	}

	private static void afficherTexte(String texte, semaphoreBinaire sem) // on la met en statique car sinon, chacun des threads vont executer leur propre méthode afficherTexte, or ici ils vont se partager la fonction statique
	{
		sem.syncWait();
		System.out.println("J'entre en section critique");
		for (int i=0; i<texte.length(); i++){
		    System.out.print(texte.charAt(i));
		    try {sleep(100);} catch(InterruptedException e){};
		}
		sem.syncSignal();
		
		System.out.println("\nJe sors de section critique");
	}
	/*
	Exercice 1:
	On ne peut pas mettre synchronized sur run directement car sinon le thread entier serait synchronized ce qui ne fait aucun sens
	On aurait pu aussir synchroniser simplement le bloc de code dans le run mais cela aurait impliqué de créer une classe bidon pour y mettre un objet de référence, ce serait revenu au même
	ici ressource critique = l'envoi du texte dans la sortie standard out
	section critique = toute la boucle for

	*/
}
