package UI;

import static Utility.Settings.MARGIN_PERCENTAGE;
import static Utility.Settings.SCENE_WIDTH;

import DukesOfTheRealm.Castle;
import Enums.BuildingEnum;
import Enums.SoldierEnum;
import Interface.IBuilding;
import Interface.IProduction;
import Interface.IUI;
import Interface.IUpdate;
import Utility.BuildingPack;
import Utility.Input;
import Utility.SoldierPack;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * G�re l'interface utilisateur pour la production d'unit� et l'am�lioration des b�timents
 */
public final class UIProductionUnitPreview extends Parent implements IUpdate, IUI
{

	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/

	/**
	 * Bouton pour retirer le dernier �l�ment de la queue de production.
	 */
	private final Button removeLastProduction;

	/**
	 * Bouton pour retirer tout les �l�ments de la queue de production.
	 */
	private final Button removeAllProduction;

	/**
	 * Background d�limitant l'interface de production.
	 */
	private final Rectangle background;

	/**
	 * Background de la barre de chargement pour les productions.
	 */
	private final Rectangle backgroundTime;

	/**
	 * Barre qui grandi en fonction du ratio entre le temps restant de production et le temps total
	 *
	 * @see DukesOfTheRealm.Caserne#ratio
	 */
	private final Rectangle fillTime;

	/**
	 * R�f�rence sur le ch�teau courant.
	 *
	 * @see UIProductionUnitPreview#switchCastle(Castle, boolean)
	 */
	private Castle currentCastle;

	/**
	 * Object Input pour r�cup�rer les touches clavier saisies par l'utilisateur.
	 */
	private Input input;

	/**
	 * Textes des co�ts des unit�s.
	 */
	private final SoldierPack<Text> textCostSoldier;
	
	/**
	 * Textes des co�ts des b�timents.
	 */
	private final BuildingPack<Text> textCostBuilding;

	/**
	 * Boutons de production des unit�s.
	 */
	private final SoldierPack<Button> productSoldier;
	
	/**
	 * Boutons de production des b�timents.
	 */
	private final BuildingPack<Button> upgradeBuilding;

	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

	/**
	 * Constructeur par d�faut de UIProductionUnitPreview.
	 */
	public UIProductionUnitPreview()
	{
		this.productSoldier = new SoldierPack<>(null, null, null, null, null, null, null);
		this.textCostSoldier = new SoldierPack<>(null, null, null, null, null, null, null);
		this.textCostBuilding = new BuildingPack<>(null, null, null, null, null);
		this.upgradeBuilding = new BuildingPack<>(null, null, null, null, null);

		for (SoldierEnum s : SoldierEnum.values())
		{
			this.productSoldier.replace(s, new Button());
			this.textCostSoldier.replace(s, new Text());
		}

		for (BuildingEnum b : BuildingEnum.values())
		{
			this.upgradeBuilding.replace(b, new Button());
			this.textCostBuilding.replace(b, new Text());
		}

		this.removeAllProduction = new Button();
		this.removeLastProduction = new Button();

		this.background = new Rectangle(280, 490);

		this.backgroundTime = new Rectangle(240, 40);
		this.fillTime = new Rectangle(0, 40); // entre +00 et +210
	}

	/*************************************************/
	/********************* START *********************/
	/*************************************************/

	@Override
	public void start()
	{
		addAllNodes();
		relocateAllNodes();
		setAllButtons();
		setBackground();
		setBar();
		setAllVisible(false);
		setAllTexts();

		for (SoldierEnum s : SoldierEnum.values())
		{
			setVisible(this.textCostSoldier.get(s), false);
		}

		for (BuildingEnum b : BuildingEnum.values())
		{
			setVisible(this.textCostBuilding.get(b), false);
		}
	}

	/*************************************************/
	/******************** UPDATE *********************/
	/*************************************************/

	@Override
	public void update(final long now, final boolean pause)
	{
		setFill(this.currentCastle.getCaserne().getRatio());

		for (SoldierEnum s : SoldierEnum.values())
		{
			if (this.textCostSoldier.get(s).isVisible())
			{
				if (this.currentCastle.getMiller().getVillagerFree() < s.villager)
				{
					this.textCostSoldier.get(s).setText("Miller");
				}
				else if (this.input.isShift())
				{
					this.textCostSoldier.get(s).setText(s.cost * 10 + "");
				}
				else
				{
					this.textCostSoldier.get(s).setText(s.cost + "");
				}
			}
		}

		for (BuildingEnum b : BuildingEnum.values())
		{
			if (this.textCostBuilding.get(b).isVisible())
			{
				final int level = this.currentCastle.getBuilding(b).getLevel() + this.currentCastle.getCaserne().getBuildingPack().get(b);
				if (level >= b.maxLevel)
				{
					this.textCostBuilding.get(b).setText("MAX");
					return;
				}

				int price = 0;
				if (this.input.isShift())
				{
					for (int i = 0; i < 10; i++)
					{
						if (level + i < b.maxLevel)
						{
							price += ((IProduction) this.currentCastle.getBuilding(b)).getProductionCost(level + i);
						}
					}

					this.textCostBuilding.get(b).setText(price + "");
				}
				else
				{
					price = ((IProduction) this.currentCastle.getBuilding(b)).getProductionCost(level);
					this.textCostBuilding.get(b).setText(price + "");
				}
			}
		}
	}

	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/

	/**
	 * Initalise tout les textes de ce module.
	 */
	private void setAllTexts()
	{
		for (SoldierEnum s : SoldierEnum.values())
		{
			setText(this.textCostSoldier.get(s), 30);
		}

		for (BuildingEnum b : BuildingEnum.values())
		{
			setText(this.textCostBuilding.get(b), 30);
		}
	}

	/**
	 * Initialise un texte avec des param�tres pr�d�finis.
	 *
	 * @param t Le texte � initialiser.
	 * @param font Le taille du texte.
	 */
	private void setText(final Text t, final int font)
	{
		t.setFont(new Font(font));
		t.setFill(Color.ORANGERED);
		t.setStyle("-fx-font-weight: bold");
		t.setWrappingWidth(100);
		t.setStroke(Color.BLACK);
		t.setStrokeWidth(2);
		t.setTextAlignment(TextAlignment.LEFT);
		t.setMouseTransparent(true);
	}

	@Override
	public void addNode(final Node node)
	{
		getChildren().add(node);
	}

	/**
	 * Retire un noeud pour qu'il ne soit plus visible.
	 *
	 * @param node Le noeud qu'on retire.
	 */
	public void removeNode(final Node node)
	{
		getChildren().remove(node);
	}

	@Override
	public void setAllVisible(final boolean visible)
	{
		setVisible(this.backgroundTime, visible);
		setVisible(this.background, visible);
		setVisible(this.fillTime, visible);
		setVisible(this.removeAllProduction, visible);
		setVisible(this.removeLastProduction, visible);

		for (SoldierEnum s : SoldierEnum.values())
		{
			setVisible(this.productSoldier.get(s), visible);
		}

		for (BuildingEnum b : BuildingEnum.values())
		{
			setVisible(this.upgradeBuilding.get(b), visible);
		}

	}

	/**
	 * Met � l'�chelle la barre de production par rapport au ratio.
	 *
	 * @param fractionFill Le ratio.
	 * @see                UIProductionUnitPreview#fillTime
	 */
	public void setFill(final double fractionFill)
	{
		if (fractionFill >= 1)
		{
			this.fillTime.setWidth(0);
			return;
		}
		if (fractionFill <= 0)
		{
			this.fillTime.setWidth(0);
			return;
		}
		this.fillTime.setWidth(5 + fractionFill * 235);
	}

	/**
	 * Initialise le background de la barre de production avec des param�tres pr�d�finis.
	 *
	 * @see UIProductionUnitPreview#backgroundTime
	 */
	private void setBar()
	{
		this.backgroundTime.setFill(Color.TAN);
		this.backgroundTime.setStrokeWidth(5);
		this.backgroundTime.setStrokeType(StrokeType.OUTSIDE);
		this.backgroundTime.setStroke(Color.BLANCHEDALMOND);
		this.backgroundTime.setArcHeight(20);
		this.backgroundTime.setArcWidth(20);

		this.fillTime.setFill(Color.SEAGREEN);
		this.fillTime.setArcHeight(20);
		this.fillTime.setArcWidth(20);

		final InnerShadow i = new InnerShadow();
		i.setColor(Color.BLACK);
		i.setHeight(10);

		this.fillTime.setEffect(i);
	}

	/**
	 * Initialise le style d'un bouton, sa taille et le cursor lorsque la souris passe dessus.
	 *
	 * @param b   Le bouton � initialiser.
	 * @param url Le chemin pour acc�der � l'image du bouton.
	 */
	private void setStyle(final Button b, final String url)
	{
		b.setStyle("" + "-fx-background-color: transparent;" + "-fx-background-image: url('" + url + "'); "
				+ "-fx-background-size: 64px 64px; " + "-fx-background-repeat: no-repeat; ");
		b.setMinSize(64, 64);
		b.setMaxSize(64, 64);
		b.setCursor(Cursor.HAND);
	}

	/**
	 * Initialise tout les boutons en leur affectant un style, une image , des effets ainsi que des
	 * �v�nements.
	 */
	private void setAllButtons()
	{
		setStyle(this.productSoldier.get(SoldierEnum.Piker), "/images/PikerButton_64.png");
		setStyle(this.productSoldier.get(SoldierEnum.Knight), "/images/KnightButton_64.png");
		setStyle(this.productSoldier.get(SoldierEnum.Onager), "/images/OnagerButton_64.png");
		setStyle(this.productSoldier.get(SoldierEnum.Archer), "/images/ArcherButton_64.png");
		setStyle(this.productSoldier.get(SoldierEnum.Berserker), "/images/BerserkerButton_64.png");
		setStyle(this.productSoldier.get(SoldierEnum.Spy), "/images/SpyButton_64.png");

		setStyle(this.upgradeBuilding.get(BuildingEnum.Castle), "/images/CastleButton_64.png");
		setStyle(this.upgradeBuilding.get(BuildingEnum.Caserne), "/images/CaserneButton_64.png");
		setStyle(this.upgradeBuilding.get(BuildingEnum.Wall), "/images/WallButton_64.png");
		setStyle(this.upgradeBuilding.get(BuildingEnum.Market), "/images/MarketButton_64.png");
		setStyle(this.upgradeBuilding.get(BuildingEnum.Miller), "/images/MillerButton_64.png");

		setStyle(this.removeAllProduction, "/images/ResetProductionButton_64.png");
		setStyle(this.removeLastProduction, "/images/CancelLastProduction_64.png");

		this.removeAllProduction.setOnMousePressed(event -> this.currentCastle.getCaserne().resetQueue(true));
		this.removeLastProduction.setOnMousePressed(event -> this.currentCastle.getCaserne().removeLastProduction(true));

		addEventMouse(this.removeAllProduction);
		addEventMouse(this.removeLastProduction);

		for (SoldierEnum s : SoldierEnum.values())
		{
			this.productSoldier.get(s).setOnMousePressed(event ->
			{
				if (this.currentCastle.getMiller().getVillagerFree() >= s.villager)
				{
					if (this.input.isAlt())
					{
						while (this.currentCastle.getMiller().getVillagerFree() >= s.villager
								&& addProduction(this.productSoldier.get(s), s.getObject()))
						{
						}
					}
					else if (this.input.isShift())
					{
						for (int i = 0; i < 10; i++)
						{
							if (this.currentCastle.getMiller().getVillagerFree() >= s.villager)
							{
								addProduction(this.productSoldier.get(s), s.getObject());
							}
						}

					}
					else
					{
						addProduction(this.productSoldier.get(s), s.getObject());
					}
				}
			});

			this.productSoldier.get(s).setOnMouseEntered(event -> this.textCostSoldier.get(s).setVisible(true));
			this.productSoldier.get(s).setOnMouseExited(event -> this.textCostSoldier.get(s).setVisible(false));
			addEventMouse(this.productSoldier.get(s));
		}

		for (BuildingEnum b : BuildingEnum.values())
		{
			this.upgradeBuilding.get(b).setOnMousePressed(event ->
			{
				final int level = this.currentCastle.getBuilding(b).getLevel() + this.currentCastle.getCaserne().getBuildingPack().get(b);
				if (level >= b.maxLevel)
				{
					return;
				}

				IProduction prod = (IProduction) b.getObject();
				((IBuilding) prod).setLevel(level);
				if (this.input.isAlt())
				{
					int i = 0;
					while (level + i < b.maxLevel && addProduction(this.upgradeBuilding.get(b), prod))
					{
						i++;
						((IBuilding) prod).setLevel(level + i);
					}
				}
				else if (this.input.isShift())
				{
					for (int i = 0; i < 10; i++)
					{
						if (level + i < b.maxLevel)
						{
							addProduction(this.upgradeBuilding.get(b), prod);
						}
					}
				}
				else
				{
					addProduction(this.upgradeBuilding.get(b), prod);
				}
			});

			this.upgradeBuilding.get(b).setOnMouseEntered(event -> this.textCostBuilding.get(b).setVisible(true));
			this.upgradeBuilding.get(b).setOnMouseExited(event -> this.textCostBuilding.get(b).setVisible(false));
			addEventMouse(this.upgradeBuilding.get(b));
		}
	}

	/**
	 * Change la couleur du shadom du bouton suivant si la production a bien �t� ajout� ou non.
	 *
	 * @param  b Le bouton qui vient d'�tre activ�.
	 * @param  p La production qu'on veut produire.
	 * @return   Retourne true si la production a bien �t� ajout� � la queue de production de la caserne
	 *           du ch�teau courant.
	 */
	private boolean addProduction(final Button b, final IProduction p)
	{
		if (this.currentCastle.addProduction(p))
		{
			b.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.LIGHTGREEN, 30, 0.33, 0, 0));
			return true;
		}
		else
		{
			b.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.ORANGERED, 30, 0.33, 0, 0));
		}
		return false;
	}

	/**
	 * Initalise les param�tres du background.
	 */
	private void setBackground()
	{
		final Stop[] stops = new Stop[]
		{
				new Stop(0, Color.WHITE), new Stop(1, Color.WHITE)
		};
		final LinearGradient lg2 = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);
		this.background.setStroke(lg2);

		this.background.setStrokeWidth(3);
		this.background.setArcHeight(60);
		this.background.setArcWidth(60);
	}

	/**
	 * Ajoute des �v�nements sur un bouton.
	 *
	 * @param b Le bouton.
	 */
	private void addEventMouse(final Button b)
	{
		b.setMinSize(64, 64);
		b.setMaxSize(64, 64);
		b.setCursor(Cursor.HAND);
		b.addEventHandler(MouseEvent.MOUSE_ENTERED, event ->
		{
			setButtonShadow(b);
		});

		b.addEventHandler(MouseEvent.MOUSE_EXITED, event ->
		{
			b.setEffect(null);
		});

		b.setOnMouseClicked(event ->
		{
			setButtonShadow(b);
		});
	}

	/**
	 * Ajoute un bloom sur un bouton ainsi que de l'ombre.
	 *
	 * @param b Le bouton.
	 */
	private void setButtonShadow(final Button b)
	{
		final Bloom bloom = new Bloom();
		bloom.setThreshold(1);
		bloom.setInput(new DropShadow(BlurType.GAUSSIAN, Color.BISQUE, 20, 0.15, 0, 0));
		b.setEffect(bloom);
	}

	@Override
	public void addAllNodes()
	{
		addNode(this.background);
		addNode(this.backgroundTime);
		addNode(this.fillTime);
		addNode(this.removeAllProduction);
		addNode(this.removeLastProduction);

		for (SoldierEnum s : SoldierEnum.values())
		{
			if (s != SoldierEnum.Conveyors)
			{
				addNode(this.productSoldier.get(s));
				addNode(this.textCostSoldier.get(s));
			}
		}

		for (BuildingEnum b : BuildingEnum.values())
		{
			addNode(this.upgradeBuilding.get(b));
			addNode(this.textCostBuilding.get(b));
		}
	}

	@Override
	public void relocateAllNodes()
	{
		final int width = 90;
		final int height = 80;
		final int offset = 560;

		final float margin = (float) MARGIN_PERCENTAGE + 0.08f;

		int multiplier = 0;
		int offset2 = 0;
		for (SoldierEnum s : SoldierEnum.values())
		{
			relocate(this.productSoldier.get(s), SCENE_WIDTH * margin + width * multiplier, offset + offset2);
			relocate(this.textCostSoldier.get(s), this.productSoldier.get(s).getLayoutX(), this.productSoldier.get(s).getLayoutY() + 20);
			multiplier++;

			if (multiplier == 3)
			{
				multiplier = 0;
				offset2 += height;
			}
		}
		multiplier = 0;
		offset2 += 10;
		for (BuildingEnum b : BuildingEnum.values())
		{
			relocate(this.upgradeBuilding.get(b), SCENE_WIDTH * margin + width * multiplier, offset + offset2);
			relocate(this.textCostBuilding.get(b), this.upgradeBuilding.get(b).getLayoutX(), this.upgradeBuilding.get(b).getLayoutY() + 30);
			multiplier++;
			if (multiplier == 3)
			{
				multiplier = 0;
				offset2 += height;
			}
		}

		relocate(this.removeAllProduction, SCENE_WIDTH * margin + width * 2 - width * 0.5f, offset + height * 4.95);
		relocate(this.removeLastProduction, SCENE_WIDTH * margin + width * 1 - width * 0.5f, offset + height * 4.95);

		relocate(this.fillTime, SCENE_WIDTH * margin + 1, offset + height * 4.3);
		relocate(this.backgroundTime, SCENE_WIDTH * margin + 1, offset + height * 4.3);

		relocate(this.background, SCENE_WIDTH * margin - 17, offset - 22);
	}

	/**
	 * Change le ch�teau courant et rend visible ou invisible ce module.
	 *
	 * @param castle            Le nouveau ch�teau courant.
	 * @param productionVisible Sp�cifie si les parties graphiques de ce module seront visible ou non.
	 */
	public void switchCastle(final Castle castle, final boolean productionVisible)
	{
		this.currentCastle = castle;
		setAllVisible(productionVisible);
	}

	/**
	 * @param input the input to set
	 */
	public void setInput(final Input input)
	{
		this.input = input;
	}
}
