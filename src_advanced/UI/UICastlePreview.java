package UI;

import DukesOfTheRealm.Castle;
import Enums.BuildingEnum;
import Enums.SoldierEnum;
import Interface.IUI;
import Interface.IUpdate;
import Utility.BuildingPack;
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
 * Gère l'interface utilisateur des données des châteaux.
 * <p>
 * Affiche les données du dernier château selectionné. <br>
 * Dans le cas où selectionner un château lance la création d'une ost, les données du château reste
 * sur le château qui lance l'ost.
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
	 * Icone de selection du château.
	 */
	private final ImageView imageCircle;

	/**
	 * Texte affichant le nom de l'acteur à qui appartient le château selectionné.
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
	 * Background qui délimite l'interface utilisateur de ce module.
	 */
	private final Rectangle background;

	/**
	 * Référence sur le château courant.
	 *
	 * @see UICastlePreview#updateTexts()
	 * @see UICastlePreview#switchCastle(Castle, boolean, boolean)
	 */
	private Castle currentCastle;

	/**
	 * Référence sur l'ancien château courant.
	 *
	 * @see UICastlePreview#updateTexts()
	 * @see UICastlePreview#switchCastle(Castle, boolean, boolean)
	 */
	private Castle lastCastle;

	/**
	 * Boolean spécifiant si l'interface d'attaque est visible ou non.
	 *
	 * @see UICastlePreview#updateTexts()
	 * @see UICastlePreview#switchCastle(Castle, boolean, boolean)
	 */
	private boolean attackVisible = false;

	/**
	 * Textes du nombre d'unité du château.
	 */
	private final SoldierPack<Text> textSoldier;

	/**
	 * Icones des unités.
	 */
	private final SoldierPack<ImageView> imageSoldier;
	
	/**
	 * Textes des niveaux des bâtiments 
	 */
	private final BuildingPack<Text> textBuildingLevel;

	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

	/**
	 * Constructeur par défaut de UICastlePreview.
	 */
	public UICastlePreview()
	{
		this.textSoldier = new SoldierPack<>();
		this.imageSoldier = new SoldierPack<>();
		this.textBuildingLevel = new BuildingPack<>();

		int size = 48;
		this.imageSoldier.replace(SoldierEnum.Piker, newImageView("/images/PikerPreview_48.png", size, size));
		this.imageSoldier.replace(SoldierEnum.Knight, newImageView("/images/KnightPreview_48.png", size, size));
		this.imageSoldier.replace(SoldierEnum.Onager, newImageView("/images/OnagerPreview_48.png", size, size));
		this.imageSoldier.replace(SoldierEnum.Archer, newImageView("/images/ArcherPreview_48.png", size, size));
		this.imageSoldier.replace(SoldierEnum.Berserker, newImageView("/images/BerserkerPreview_48.png", size, size));
		this.imageSoldier.replace(SoldierEnum.Spy, newImageView("/images/SpyPreview_48.png", size, size));

		this.imageFlorin = newImageView("/images/FlorinPreview_64.png", size, size);
		this.imageCircle = newImageView("/images/CircleCurrentCastle_96.png", 128, 128);
		
		for(BuildingEnum b : BuildingEnum.values())
		{
			this.textBuildingLevel.replace(b, new Text());
		}

		this.owner = new Text();
		this.nbFlorin = new Text();

		for (SoldierEnum s : SoldierEnum.values())
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
	 * Met à jour les textes des données du château courant.
	 * <p>
	 * Si le UI d'attaque est activé on affiche les données du château qui envoi l'ost (soit le
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

		for (SoldierEnum s : SoldierEnum.values())
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

		for (Text t : this.textSoldier.values())
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
		relocate(this.florinIncome, Settings.SCENE_WIDTH * margin, offset + i * 1 - 15);
		relocate(this.level, Settings.SCENE_WIDTH * margin, offset + i * 2 - 30);
		offset -= 20;
		relocate(this.imageFlorin, Settings.SCENE_WIDTH * margin, offset + i * 2 + 20);

		int multiplier = 3;
		for (SoldierEnum s : SoldierEnum.values())
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

		for (Text t : this.textSoldier.values())
		{
			addNode(t);
		}

		for (ImageView i : this.imageSoldier.values())
		{
			addNode(i);
		}
	}

	/**
	 * Initialise un texte donné en paramètre en lui donnant une taille de police, un alignement et une
	 * couleur.
	 *
	 * @param text Le texte à initialiser.
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

		for (Text t : this.textSoldier.values())
		{
			setVisible(t, visible);
		}

		for (ImageView i : this.imageSoldier.values())
		{
			setVisible(i, visible);
		}
	}

	/**
	 * Change le château courant si le boolean castleSwitch est à true et modifie l'ancien château
	 * courant.
	 *
	 * @param castle        Le potentiel nouveau château courant.
	 * @param castleSwitch  Spécifie si on doit changer le château courant.
	 * @param attackVisible Spécifie si le UI d'attaque est affiché ou non.
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
