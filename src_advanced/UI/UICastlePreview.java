package UI;

import java.util.ArrayList;
import java.util.HashMap;

import Duke.Actor;
import DukesOfTheRealm.Castle;
import DukesOfTheRealm.Market;
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
	private final SoldierPack<Text> textSoldier;

	/**
	 * Icones des unit�s.
	 */
	private final SoldierPack<ImageView> imageSoldier;
	
	/**
	 * Textes des niveaux des b�timents 
	 */
	private final BuildingPack<Text> textBuildingLevel;
	
	private final BuildingPack<ImageView> imageBuilding;
	

	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

	/**
	 * Constructeur par d�faut de UICastlePreview.
	 */
	public UICastlePreview()
	{
		this.textSoldier = new SoldierPack<>();
		this.imageSoldier = new SoldierPack<>();
		this.textBuildingLevel = new BuildingPack<>();
		this.imageBuilding = new BuildingPack<>();
		
		this.imageSoldier.replace(SoldierEnum.Piker, newImageView("/images/PikerPreview_48.png", 48, 48));
		this.imageSoldier.replace(SoldierEnum.Knight, newImageView("/images/KnightPreview_48.png", 48, 48));
		this.imageSoldier.replace(SoldierEnum.Onager, newImageView("/images/OnagerPreview_48.png", 48, 48));
		this.imageSoldier.replace(SoldierEnum.Archer, newImageView("/images/ArcherPreview_48.png", 48, 48));
		this.imageSoldier.replace(SoldierEnum.Berserker, newImageView("/images/BerserkerPreview_48.png", 48, 48));
		this.imageSoldier.replace(SoldierEnum.Spy, newImageView("/images/SpyPreview_48.png", 48, 48));
		this.imageSoldier.replace(SoldierEnum.Conveyors, newImageView("/images/ConvoyeurPreview_48.png", 48, 48));
		
		this.imageBuilding.replace(BuildingEnum.Castle, newImageView("/images/CastlePreview_48.png", 48, 48));
		this.imageBuilding.replace(BuildingEnum.Caserne, newImageView("/images/CasernePreview_48.png", 48, 48));
		this.imageBuilding.replace(BuildingEnum.Miller, newImageView("/images/MillerPreview_48.png", 48, 48));
		this.imageBuilding.replace(BuildingEnum.Wall, newImageView("/images/WallPreview_48.png", 48, 48));
		this.imageBuilding.replace(BuildingEnum.Market, newImageView("/images/MarketPreview_48.png", 48, 48));

		this.imageFlorin = newImageView("/images/FlorinPreview_64.png", 48, 48);
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
		this.background = new Rectangle(320, 460);

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
			updateTexts(now);
		}
	}

	/**
	 * Met � jour les textes des donn�es du ch�teau courant.
	 * <p>
	 * Si le UI d'attaque est activ� on affiche les donn�es du ch�teau qui envoi l'ost (soit le
	 * lastCastle).
	 * </p>
	 * @param now Le now de la m�thode update.
	 * @see UICastlePreview#update(long, boolean)
	 */
	private void updateTexts(long now)
	{
		this.background.setHeight(460);
		setAllVisible(true);
		
		// Si c'est une attaque, on montre le ch�teau qui lance l'attaque
		if(this.attackVisible)
		{
			this.florinIncome.setText(this.lastCastle.getActor().florinIncome(this.lastCastle));
			this.owner.setText(this.lastCastle.getActor().getName());
			this.nbFlorin.setText((int) this.lastCastle.getTotalFlorin() + "");

			for(BuildingEnum b : BuildingEnum.values())
			{
				this.textBuildingLevel.get(b).setText(this.lastCastle.getBuilding(b).getLevel() + "");
			}
			
			for (SoldierEnum s : SoldierEnum.values())
			{
				this.textSoldier.get(s).setText(this.currentCastle.getReserveOfSoldiers().getSoldierPack().get(s).toString());
			}
			return;
		}
		
		this.owner.setText(this.currentCastle.getActor().getName());
		
		// Si c'est un ch�teau du joueur ou un ch�teau espionn�
		if(this.currentCastle.getActor().isPlayer() || this.currentCastle.isSpiedOn())
		{
			this.nbFlorin.setText((int) this.currentCastle.getTotalFlorin() + "");
			
			this.florinIncome.setText(this.currentCastle.getActor().florinIncome(this.currentCastle));
			
			for(BuildingEnum b : BuildingEnum.values())
			{
				this.textBuildingLevel.get(b).setText(this.currentCastle.getBuilding(b).getLevel() + "");
			}
			for (SoldierEnum s : SoldierEnum.values())
			{
				this.textSoldier.get(s).setText(this.currentCastle.getReserveOfSoldiers().getSoldierPack().get(s).toString());
			}
		}
		else
		{
			setAllVisible(false);
			setVisible(this.background, true);
			setVisible(this.owner, true);
			this.background.setHeight(100);
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
		for(BuildingEnum b : BuildingEnum.values())
		{
			setText(this.textBuildingLevel.get(b), 28);
		}
		
		setText(this.owner, 24);
		setText(this.florinIncome, 24);
		//setText(this.textConveyors, 28);

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
		int offset = 30;
		final int i = 52;

		relocate(this.owner, Settings.SCENE_WIDTH * margin, offset + i * 0);
		relocate(this.florinIncome, Settings.SCENE_WIDTH * margin, offset + i * 1 - 15);
		
		int multiplier = 3;
		for(BuildingEnum b : BuildingEnum.values())
		{
			relocate(this.imageBuilding.get(b), Settings.SCENE_WIDTH * margin + 100, offset + i * multiplier - 30);
			relocate(this.textBuildingLevel.get(b), Settings.SCENE_WIDTH * margin + 120, offset + i * multiplier  - 12);
			multiplier++;
		}
		
		multiplier = 3;
		for (SoldierEnum s : SoldierEnum.values())
		{
			if(s != SoldierEnum.Conveyors)
			{
				relocate(this.imageSoldier.get(s), Settings.SCENE_WIDTH * margin - 60, offset + i * multiplier - 30);
				relocate(this.textSoldier.get(s), Settings.SCENE_WIDTH * margin - 40, offset + i * multiplier - 12);
				multiplier++;
			}
		}
		
		multiplier--;
		relocate(this.imageSoldier.get(SoldierEnum.Conveyors),  Settings.SCENE_WIDTH * margin + 100, offset + i * multiplier - 33);
		relocate(this.textSoldier.get(SoldierEnum.Conveyors), Settings.SCENE_WIDTH * margin + 120, offset + i * multiplier  - 12);
		
		offset -= 20;
		relocate(this.imageFlorin, Settings.SCENE_WIDTH * margin + 20, offset + i * 1.4 + 10);
		relocate(this.nbFlorin, Settings.SCENE_WIDTH * margin + 40, offset + i * 1.4 + 35);
		
		relocate(this.background, Settings.SCENE_WIDTH * margin - 77, offset);
	}

	@Override
	public void addAllNodes()
	{
		addNode(this.background);
		addNode(this.imageFlorin);
		addNode(this.owner);
		addNode(this.florinIncome);
		addNode(this.nbFlorin);
		addNode(this.imageCircle);

		for(BuildingEnum b : BuildingEnum.values())
		{
			addNode(this.textBuildingLevel.get(b));
			addNode(this.imageBuilding.get(b));
		}
		
		for(SoldierEnum s : SoldierEnum.values())
		{
			addNode(this.textSoldier.get(s));
			addNode(this.imageSoldier.get(s));
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
		setVisible(this.imageFlorin, visible);
		setVisible(this.owner, visible);
		setVisible(this.florinIncome, visible);
		setVisible(this.nbFlorin, visible);
		
		for(SoldierEnum s : SoldierEnum.values())
		{
			setVisible(this.imageSoldier.get(s), visible);
			setVisible(this.textSoldier.get(s), visible);
		}
		
		for(BuildingEnum b : BuildingEnum.values())
		{
			setVisible(this.textBuildingLevel.get(b), visible);
			setVisible(this.imageBuilding.get(b), visible);
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
