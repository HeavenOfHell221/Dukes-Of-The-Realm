package Duke;

import static Utility.Settings.FLORIN_PER_SECOND;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;

import DukesOfTheRealm.Castle;
import DukesOfTheRealm.Main;
import Interface.IUpdate;
import UI.UIManager;
import Utility.Settings;
import Utility.Time;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Repr�sente les diff�rents acteur du jeu.
 * <p>
 * C'est autour des acteurs que le jeu s'articule. Chacun des acteurs contient une liste des
 * ch�teaux qui lui appartient.
 * </p>
 */
public abstract class Actor implements Serializable, IUpdate
{
	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/

	/**
	 * Le nom de cet acteur.
	 */
	protected String name = "";

	/**
	 * La liste des ch�teaux appartenant � cet acteur.
	 */
	protected ArrayList<Castle> castles;

	/**
	 * La couleur utilis� pour r�pr�senter cet acteur (ch�teau, unit�s).
	 */
	protected transient Color color;

	/**
	 * R�f�rence au pane du principal du jeu.
	 */
	protected transient Pane pane;

	/**
	 * Liste des ch�teaux en attente d'ajout dans la liste castles.
	 * <p>
	 * Lorsque cet acteur gagne un ch�teau, il est ajout� � cette liste dans la m�me boucle de jeu (m�me
	 * image). Avant le prochain Update de cet acteur, il ajoute tous les ch�teaux de cette liste aux
	 * siens puis vide cette liste.
	 * </p>
	 *
	 * @see Actor#castles
	 * @see Actor#addOrRemoveCastleList()
	 */
	public ArrayDeque<Castle> castlesWaitForAdding;

	/**
	 * Liste des ch�teaux en attente de suppression dans la liste castles.
	 * <p>
	 * Lorsque cet acteur perd un ch�teau, il est ajout� � cette liste et serra supprim� de la liste
	 * castles avant le prochain Update.
	 * </p>
	 *
	 * @see Actor#castles
	 * @see Actor#addOrRemoveCastleList()
	 */
	public ArrayDeque<Castle> castlesWaitForDelete;

	/**
	 * Boolean servant � savoir si cet acteur est encore vivant. Un acteur n'ayant plus de ch�teau est
	 * consid�r� comme mort.
	 */
	public boolean isDead = false;

	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

	/**
	 * Constructeur par d�faut d'Actor.
	 */
	Actor()
	{
		this.castles = new ArrayList<>();
		this.castlesWaitForAdding = new ArrayDeque<>();
		this.castlesWaitForDelete = new ArrayDeque<>();
	}

	/*************************************************/
	/********************* START *********************/
	/*************************************************/

	/**
	 * Initialise les composents transient de cet acteur.
	 *
	 * @param color La couleur de cet acteur.
	 * @param pane  Le pane principal du jeu.
	 */
	public void startTransient(final Color color, final Pane pane)
	{
		this.color = color;
		if (!Main.isNewGame)
		{
			this.castles.forEach(castle -> castle.setColor(color));
		}
	}

	/*************************************************/
	/******************** UPDATE *********************/
	/*************************************************/

	@Override
	public void update(final long now, final boolean pause)
	{
		addOrRemoveCastleList();

		if (this.isDead)
		{
			return;
		}

		Iterator<Castle> it = this.castles.iterator();
		while (it.hasNext())
		{
			Castle castle = it.next();
			updateFlorin(castle);
			castle.updateProduction();
			castle.updateOst(now, pause);
		}
	}

	/**
	 * Ajoute les nouveaux ch�teaux s'il y en a et supprime les ch�teaux conquis par d'autres acteurs.
	 *
	 * @see Actor#update(long, boolean)
	 * @see Actor#castlesWaitForAdding
	 * @see Actor#castlesWaitForDelete
	 */
	protected void addOrRemoveCastleList()
	{
		// Ajout
		if (this.castlesWaitForAdding.size() > 0)
		{
			this.castles.addAll(this.castlesWaitForAdding);
			this.castlesWaitForAdding.forEach(c -> addEvent(c));
			this.castlesWaitForAdding.clear();
		}

		// Suppression
		if (this.castlesWaitForDelete.size() > 0)
		{
			this.castles.removeAll(this.castlesWaitForDelete);
			this.castlesWaitForDelete.clear();
		}

		// Mort ?
		if (this.castles.size() <= 0)
		{
			this.isDead = true;
		}
	}

	/**
	 * Met � jour les Florin d'un ch�teau en particulier � chaque nouvelle image.
	 * <p>
	 * A chaque image on ajoute une certaine quantit� de Florin pour avoir la quantit� souhait� sur 1
	 * seconde.
	 * </p>
	 *
	 * @param castle Le ch�teau auquel on ajoute des Florin.
	 * @see          DukesOfTheRealm.Castle#addFlorin(double)
	 */
	protected void updateFlorin(final Castle castle)
	{
		castle.addFlorin(Settings.FLORIN_PER_SECOND * castle.getLevel() * Time.deltaTime);
	}

	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/

	/**
	 * Ev�nement qui modifie le UI pour qu'il affiche le ch�teau sur lequel on vient de cliquer.
	 * <p>
	 * Retrouve le ch�teau en question et modifie le UI en fonction.
	 * </p>
	 *
	 * @param event L'�v�nement cr�e lors d'un clique sur un ch�teau.
	 * @see         Actor#addEvent(Castle)
	 * @see         Actor#switchCastle(Castle)
	 */
	protected void castleHandle(final MouseEvent event)
	{
		if (event.getButton() == MouseButton.PRIMARY)
		{
			getCastles().stream().filter(castle -> castle.getShape() == (Rectangle) event.getSource()).limit(1)
					.forEach(castle -> switchCastle(castle));
		}
	}

	/**
	 *
	 * @param  castle
	 * @return
	 */
	public String florinIncome(final Castle castle)
	{
		return String.format("%.1f", (float) (FLORIN_PER_SECOND * castle.getLevel())) + " Florin/s";
	}

	/**
	 *
	 * @param castle
	 */
	public void addFirstCastle(final Castle castle)
	{
		this.castles.add(castle);
		addEvent(castle);
	}

	/**
	 *
	 * @param castle
	 */
	protected void addEvent(final Castle castle)
	{
		castle.getShape().setOnMousePressed(event -> castleHandle(event));
	}

	/**
	 *
	 */
	public void addEventAllCastles()
	{
		this.castles.forEach(castle -> addEvent(castle));
	}

	/**
	 *
	 * @param castle
	 * @see          UI.UIManager#switchCastle(Castle)
	 */
	protected void switchCastle(final Castle castle)
	{
		UIManager.getInstance().switchCastle(castle);
	}

	/**
	 * Permet de savoir si un acteur est un joueur ou non.
	 *
	 * @return Retourne false pour tout les acteurs sauf ceux �tant de la classe Player.
	 * @see    Player#isPlayer()
	 */
	public boolean isPlayer()
	{
		return false;
	}

	/*************************************************/
	/*************** GETTERS / SETTERS ***************/
	/*************************************************/

	/**
	 * @return the color
	 */
	public final Color getColor()
	{
		return this.color;
	}

	/**
	 * @param color the color to set
	 */
	public final void setColor(final Color color)
	{
		this.color = color;
	}

	/**
	 * @param name the name to set
	 */
	public final void setName(final String name)
	{
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public final String getName()
	{
		return this.name;
	}

	/**
	 * @return the castles
	 */
	public final ArrayList<Castle> getCastles()
	{
		return this.castles;
	}
}
