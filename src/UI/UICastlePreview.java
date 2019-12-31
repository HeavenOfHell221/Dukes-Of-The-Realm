package UI;

import DukesOfTheRealm.Castle;
import Interface.IUI;
import Interface.IUpdate;
import Utility.Settings;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * G�re l'interface utilisateur des donn�es des ch�teaux.
 * <p>
 * Affiche les donn�es du dernier ch�teau selectionn�. <br>
 * Dans le cas o� selectionner un ch�teau lance la cr�ation d'une ost, les donn�es du ch�teau reste
 * sur le ch�teau qui lance l'ost.
 * </p>
 */
public final class UICastlePreview extends Parent implements IUI, IUpdate
{

	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/

	/**
	 * Icone du Knight.
	 */
	private final ImageView imageKnight;

	/**
	 * Icone du Piker.
	 */
	private final ImageView imagePiker;

	/**
	 * Icone du Onager.
	 */
	private final ImageView imageOnager;

	/**
	 * Icone des Florin?
	 */
	private final ImageView imageFlorin;

	/**
	 * Texte affichant le niveau du ch�teau selectionn�.
	 */
	private final Text level;

	/**
	 * Texte affichant le nom de l'acteur � qui appartient le ch�teau selectionn�.
	 */
	private final Text owner;

	/**
	 * Texte affichant le nombre de Florin par seconde.
	 */
	private final Text florinIncome;

	/**
	 * Texte affichant le nombre de Knight.
	 */
	private final Text nbKnight;

	/**
	 * Texte affichant le nombre de Piker.
	 */
	private final Text nbPiker;

	/**
	 * Texte afffichant le nombre de Onager.
	 */
	private final Text nbOnager;

	/**
	 * Texte affichant le nombre de Florin.
	 */
	private final Text nbFlorin;

	/**
	 * Background qui d�limite l'interface utilisateur de ce module.
	 */
	private final Rectangle background;

	/**
	 * R�f�rence sur le ch�teau courant.
	 * 
	 * @see UICastlePreview#updateTexts()
	 * @see UICastlePreview#switchCastle(Castle, boolean)
	 */
	private Castle currentCastle;

	/**
	 * R�f�rence sur l'ancien ch�teau courant.
	 * 
	 * @see UICastlePreview#updateTexts()
	 * @see UICastlePreview#switchCastle(Castle, boolean)
	 */
	private Castle lastCastle;

	/**
	 * Boolean sp�cifiant si l'interface d'attaque est visible ou non.
	 * 
	 * @see UICastlePreview#updateTexts()
	 * @see UICastlePreview#switchCastle(Castle, boolean)
	 */
	private boolean attackVisible = false;

	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

	/**
	 * Constructeur par d�faut de UICastlePreview.
	 */
	public UICastlePreview()
	{
		this.imageKnight = newImageView("/images/mounted-knight-white.png", 64, 64);
		this.imagePiker = newImageView("/images/spartan-white.png", 64, 64);
		this.imageOnager = newImageView("/images/catapult-white.png", 64, 64);
		this.imageFlorin = newImageView("/images/coins.png", 64, 64);
		this.level = new Text();
		this.owner = new Text();
		this.nbFlorin = new Text();
		this.nbKnight = new Text();
		this.nbOnager = new Text();
		this.nbPiker = new Text();
		this.florinIncome = new Text();
		this.background = new Rectangle(240, 440);

		this.lastCastle = null;
	}

	/*************************************************/
	/********************* START *********************/
	/*************************************************/

	@Override
	public void start()
	{
		addAllNodes();
		relocateAllNodes();
		setAllTexts();
		setBackground();
	}

	/*************************************************/
	/******************** UPDATE *********************/
	/*************************************************/

	@Override
	public void update(final long now, final boolean pause)
	{
		if (this.currentCastle != null)
		{
			updateTexts();
		}
	}

	/**
	 * Met � jour les textes des donn�es du ch�teau courant.
	 * <p>
	 * Si le UI d'attaque est activ� on affiche les donn�es du ch�teau qui envoi l'ost (soit le
	 * lastCastle).
	 * </p>
	 */
	private void updateTexts()
	{
		if (this.attackVisible)
		{
			this.florinIncome.setText(this.lastCastle.getActor().florinIncome(this.lastCastle));
			this.owner.setText(this.lastCastle.getActor().getName());
			this.level.setText("Level: " + this.lastCastle.getLevel());
		}
		else
		{
			this.florinIncome.setText(this.currentCastle.getActor().florinIncome(this.currentCastle));
			this.owner.setText(this.currentCastle.getActor().getName());
			this.level.setText("Level: " + this.currentCastle.getLevel());
		}

		this.nbFlorin.setText((int) this.currentCastle.getTotalFlorin() + "");
		this.nbPiker.setText("" + this.currentCastle.getNbPikers());
		this.nbKnight.setText("" + this.currentCastle.getNbKnights());
		this.nbOnager.setText("" + this.currentCastle.getNbOnagers());
	}

	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/

	/**
	 * Initialise tout les textes de ce module.
	 * 
	 * @see UICastlePreview#setText(Text, int)
	 */
	private void setAllTexts()
	{
		setText(this.level, 24);
		setText(this.owner, 24);
		setText(this.florinIncome, 24);
		setText(this.nbKnight, 30);
		setText(this.nbOnager, 30);
		setText(this.nbPiker, 30);
		setText(this.nbFlorin, 30);
	}

	@Override
	public void addNode(final Node node)
	{
		getChildren().add(node);
	}

	/**
	 * Initialise les attributs du background.
	 * 
	 * @see UICastlePreview#background
	 */
	private void setBackground()
	{
		this.background.setStroke(Color.WHITE);
		this.background.setStrokeWidth(3);
		this.background.setArcHeight(60);
		this.background.setArcWidth(60);
	}

	@Override
	public void relocateAllNodes()
	{
		final float margin = (float) Settings.MARGIN_PERCENTAGE + 0.1f;
		int offset = 50;
		final int i = 69;

		relocate(this.owner, Settings.SCENE_WIDTH * margin, offset + i * 0);
		relocate(this.florinIncome, Settings.SCENE_WIDTH * margin, offset + i * 1 - 30);
		relocate(this.level, Settings.SCENE_WIDTH * margin, offset + i * 2 - 60);
		offset -= 20;
		relocate(this.imageFlorin, Settings.SCENE_WIDTH * margin, offset + i * 2);
		relocate(this.imagePiker, Settings.SCENE_WIDTH * margin, offset + i * 3);
		relocate(this.imageKnight, Settings.SCENE_WIDTH * margin, offset + i * 4);
		relocate(this.imageOnager, Settings.SCENE_WIDTH * margin, offset + i * 5);

		relocate(this.nbFlorin, Settings.SCENE_WIDTH * margin + 40, offset + i * 2 + 30);
		relocate(this.nbPiker, Settings.SCENE_WIDTH * margin + 40, offset + i * 3 + 30);
		relocate(this.nbKnight, Settings.SCENE_WIDTH * margin + 40, offset + i * 4 + 30);
		relocate(this.nbOnager, Settings.SCENE_WIDTH * margin + 40, offset + i * 5 + 30);

		relocate(this.background, Settings.SCENE_WIDTH * margin - 44, offset);
	}

	@Override
	public void addAllNodes()
	{
		addNode(this.background);
		addNode(this.imageKnight);
		addNode(this.level);
		addNode(this.imageFlorin);
		addNode(this.imageOnager);
		addNode(this.imagePiker);
		addNode(this.owner);
		addNode(this.florinIncome);
		addNode(this.nbFlorin);
		addNode(this.nbKnight);
		addNode(this.nbOnager);
		addNode(this.nbPiker);
	}

	/**
	 * Initialise un texte donn� en param�tre en lui donnant une taille de police, un alignement et une
	 * couleur.
	 * 
	 * @param text Le texte � initialiser.
	 * @param font La taille de police.
	 */
	private void setText(final Text text, final int font)
	{
		text.setFont(new Font(font));
		text.setWrappingWidth(155);
		text.setTextAlignment(TextAlignment.CENTER);
		text.setFill(Color.WHITE);
	}

	@Override
	public void setAllVisible(final boolean visible)
	{
		setVisible(this.background, visible);
		setVisible(this.imageKnight, visible);
		setVisible(this.level, visible);
		setVisible(this.imageFlorin, visible);
		setVisible(this.imageOnager, visible);
		setVisible(this.imagePiker, visible);
		setVisible(this.owner, visible);
		setVisible(this.florinIncome, visible);
		setVisible(this.nbFlorin, visible);
		setVisible(this.nbKnight, visible);
		setVisible(this.nbOnager, visible);
		setVisible(this.nbPiker, visible);
	}

	/**
	 * Change le ch�teau courant si le boolean castleSwitch est � true et modifie l'ancien ch�teau
	 * courant.
	 * 
	 * @param castle        Le potentiel nouveau ch�teau courant.
	 * @param castleSwitch  Sp�cifie si on doit changer le ch�teau courant.
	 * @param attackVisible Sp�cifie si le UI d'attaque est affich� ou non.
	 */
	public void switchCastle(final Castle castle, final boolean castleSwitch, final boolean attackVisible)
	{
		this.lastCastle = this.currentCastle;
		this.attackVisible = attackVisible;

		if (castleSwitch)
		{
			this.currentCastle = castle;
		}
	}

	/*************************************************/
	/*************** GETTERS / SETTERS ***************/
	/*************************************************/
	/**
	 * @return the currentCastle
	 */
	public Castle getCurrentCastle()
	{
		return this.currentCastle;
	}

	/**
	 * @return the lastCastle
	 */
	public Castle getLastCastle()
	{
		return this.lastCastle;
	}

	/**
	 * @param currentCastle the currentCastle to set
	 */
	public void setCurrentCastle(final Castle currentCastle)
	{
		this.currentCastle = currentCastle;
	}

	/**
	 * @param lastCastle the lastCastle to set
	 */
	public void setLastCastle(final Castle lastCastle)
	{
		this.lastCastle = lastCastle;
	}
}
