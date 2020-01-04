package UI;

import DukesOfTheRealm.Castle;
import Enums.SoldierEnum;
import Interface.IUI;
import Interface.IUpdate;
import Utility.Settings;
import Utility.SoldierPack;
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
	 * Icone des Florin.
	 */
	private final ImageView imageFlorin;

	/**
	 * Icone de selection du ch�teau.
	 */
	private final ImageView imageCircle;

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
	 * @see UICastlePreview#switchCastle(Castle, boolean, boolean)
	 */
	private Castle currentCastle;

	/**
	 * R�f�rence sur l'ancien ch�teau courant.
	 *
	 * @see UICastlePreview#updateTexts()
	 * @see UICastlePreview#switchCastle(Castle, boolean, boolean)
	 */
	private Castle lastCastle;

	/**
	 * Boolean sp�cifiant si l'interface d'attaque est visible ou non.
	 *
	 * @see UICastlePreview#updateTexts()
	 * @see UICastlePreview#switchCastle(Castle, boolean, boolean)
	 */
	private boolean attackVisible = false;
	
	/**
	 * Textes du nombre d'unit� du ch�teau.
	 */
	private SoldierPack<Text> textSoldier;
	
	/**
	 * Icones des unit�s.
	 */
	private SoldierPack<ImageView> imageSoldier;

	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

	/**
	 * Constructeur par d�faut de UICastlePreview.
	 */
	public UICastlePreview()
	{
		this.textSoldier = new SoldierPack<Text>();
		this.imageSoldier = new SoldierPack<ImageView>();
		
		int size = 48;
		this.imageSoldier.replace(SoldierEnum.Piker, newImageView("/images/spartan-white.png", size, size));
		this.imageSoldier.replace(SoldierEnum.Knight, newImageView("/images/mounted-knight-white.png", size, size));
		this.imageSoldier.replace(SoldierEnum.Onager, newImageView("/images/catapult-white.png", size, size));
		this.imageSoldier.replace(SoldierEnum.Archer, newImageView("/images/spartan-white.png", size, size));
		this.imageSoldier.replace(SoldierEnum.Berserker, newImageView("/images/mounted-knight-white.png", size, size));
		this.imageSoldier.replace(SoldierEnum.Spy, newImageView("/images/catapult-white.png", size, size));
		
		this.imageFlorin = newImageView("/images/coins.png", size, size);
		this.imageCircle = newImageView("/images/circle.png", 128, 128);
		
		this.level = new Text();
		this.owner = new Text();
		this.nbFlorin = new Text();
		
		for(SoldierEnum s : SoldierEnum.values())
		{
			this.textSoldier.replace(s, new Text());
		}
		
		this.florinIncome = new Text();
		this.background = new Rectangle(240, 500);

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

		
		for(SoldierEnum s : SoldierEnum.values())
		{
			this.textSoldier.get(s).setText(this.currentCastle.getReserveOfSoldiers().getSoldierPack().get(s).toString());
		}
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
		
		for(Text t : this.textSoldier.values())
		{
			setText(t, 28);
		}

		setText(this.nbFlorin, 28);
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
		int offset = 40;
		final int i = 52;

		relocate(this.owner, Settings.SCENE_WIDTH * margin, offset + i * 0);
		relocate(this.florinIncome, Settings.SCENE_WIDTH * margin, offset + i * 1 -15);
		relocate(this.level, Settings.SCENE_WIDTH * margin, offset + i * 2 - 30);
		offset -= 20;
		relocate(this.imageFlorin, Settings.SCENE_WIDTH * margin, offset + i * 2 + 20);
		
		int multiplier = 3;
		for(SoldierEnum s : SoldierEnum.values())
		{
			relocate(this.imageSoldier.get(s), Settings.SCENE_WIDTH * margin, offset + i * multiplier + 20);
			relocate(this.textSoldier.get(s), Settings.SCENE_WIDTH * margin + 40, offset + i * multiplier + 42);
			multiplier++;
		}
		
		relocate(this.nbFlorin, Settings.SCENE_WIDTH * margin + 40, offset + i * 2 + 50);
		relocate(this.background, Settings.SCENE_WIDTH * margin - 44, offset);
	}

	@Override
	public void addAllNodes()
	{
		addNode(this.background);
		addNode(this.level);
		addNode(this.imageFlorin);
		addNode(this.owner);
		addNode(this.florinIncome);
		addNode(this.nbFlorin);
		addNode(this.imageCircle);
		
		for(Text t : this.textSoldier.values())
		{
			addNode(t);
		}
		
		for(ImageView i : this.imageSoldier.values())
		{
			addNode(i);
		}
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
		setVisible(this.level, visible);
		setVisible(this.imageFlorin, visible);
		setVisible(this.owner, visible);
		setVisible(this.florinIncome, visible);
		setVisible(this.nbFlorin, visible);
		
		for(Text t : this.textSoldier.values())
		{
			setVisible(t, visible);
		}
		
		for(ImageView i : this.imageSoldier.values())
		{
			setVisible(i, visible);
		}
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
			this.imageCircle.relocate(this.currentCastle.getX() - Settings.CASTLE_SIZE / 1.9f,
					this.currentCastle.getY() - Settings.CASTLE_SIZE / 1.9f);
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
