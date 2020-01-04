package UI;

import static Utility.Settings.KNIGHT_COST;
import static Utility.Settings.CASTLE_COST;
import static Utility.Settings.MARGIN_PERCENTAGE;
import static Utility.Settings.ONAGER_COST;
import static Utility.Settings.PIKER_COST;
import static Utility.Settings.SCENE_WIDTH;

import DukesOfTheRealm.Castle;
import Enums.BuildingEnum;
import Enums.SoldierEnum;
import Interface.IProduction;
import Interface.IUI;
import Interface.IUpdate;
import Soldiers.Knight;
import Soldiers.Onager;
import Soldiers.Piker;
import Utility.BuildingPack;
import Utility.Input;
import Utility.SoldierPack;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
 * Gère l'interface utilisateur pour la production d'unité et l'amélioration des bâtiments
 */
public final class UIProductionUnitPreview extends Parent implements IUpdate, IUI
{

	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/

	/**
	 * Bouton pour retirer le dernier élément de la queue de production.
	 */
	private final Button removeLastProduction;

	/**
	 * Bouton pour retirer tout les éléments de la queue de production.
	 */
	private final Button removeAllProduction;

	/**
	 * Background délimitant l'interface de production.
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
	 * Référence sur le château courant.
	 *
	 * @see UIProductionUnitPreview#switchCastle(Castle, boolean)
	 */
	private Castle currentCastle;

	/**
	 * Object Input pour récupérer les touches clavier saisies par l'utilisateur.
	 */
	private Input input;
	
	
	private SoldierPack<Text> textCostSoldier;
	private BuildingPack<Text> textCostBuilding;
	
	private SoldierPack<Button> productSoldier;
	private BuildingPack<Button> upgradeBuilding;

	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

	/**
	 * Constructeur par défaut de UIProductionUnitPreview.
	 */
	public UIProductionUnitPreview()
	{
		for(SoldierEnum s : SoldierEnum.values())
		{
			this.productSoldier.replace(s, new Button());
			this.textCostSoldier.replace(s, new Text());
		}
		
		for(BuildingEnum b : BuildingEnum.values())
		{
			this.upgradeBuilding.replace(b, new Button());
			this.textCostBuilding.replace(b, new Text());
		}
		
		this.removeAllProduction = new Button();
		this.removeLastProduction = new Button();

		this.background = new Rectangle(280, 450); // 280 / 300

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
		
		for(SoldierEnum s : SoldierEnum.values())
		{
			setVisible(this.textCostSoldier.get(s), false);
		}
		
		for(BuildingEnum b : BuildingEnum.values())
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
		//setFill(this.currentCastle.getRatio());
		this.input.update(now, pause);
		
		for(SoldierEnum s : SoldierEnum.values())
		{
			if(this.textCostSoldier.get(s).isVisible())
			{
				if(this.input.isShift())
				{
					this.textCostSoldier.get(s).setText((s.cost * 10) + "");
				}
				else
				{
					this.textCostSoldier.get(s).setText(s.cost + "");
				}
			}
		}
		
		for(BuildingEnum b : BuildingEnum.values())
		{
			if(this.textCostBuilding.get(b).isVisible())
			{
				if(this.input.isShift())
				{
					int price = 0;

					for (int i = 0; i < 10; i++)
					{
						price += b.cost * (this.currentCastle.getLevel() + this.currentCastle.getCaserne().getBuildingPack().get(b) + i);
					}

					this.textCostBuilding.get(b).setText(price + "");
				}
				else
				{
					this.textCostBuilding.get(b).setText(b.cost * (this.currentCastle.getLevel() + this.currentCastle.getCaserne().getBuildingPack().get(b)) + "");
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
		setText(this.castleCost);
		setText(this.knightCost);
		setText(this.onagerCost);
		setText(this.pikerCost);
	}

	/**
	 * Initialise un texte avec des paramètres prédéfinis.
	 *
	 * @param t Le texte à initialiser.
	 */
	private void setText(final Text t)
	{
		t.setFont(new Font(30));
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
		setVisible(this.buttonCreateKnight, visible);
		setVisible(this.buttonCreateOnager, visible);
		setVisible(this.buttonCreatePiker, visible);
		setVisible(this.buttonUpgradeCastle, visible);
		setVisible(this.removeAllProduction, visible);
		setVisible(this.removeLastProduction, visible);
	}

	/**
	 * Met à l'échelle la barre de production par rapport au ratio.
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
	 * Initialise le background de la barre de production avec des paramètres prédéfinis.
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
	 * Initialise tout les boutons en leur affectant un style, une image , des effets ainsi que des
	 * événements.
	 */
	private void setAllButtons()
	{
		this.buttonCreateKnight
				.setStyle("" + "-fx-background-color: transparent;" + "-fx-background-image: url('/images/mounted-knight2.png'); "
						+ "-fx-background-size: 64px 64px; " + "-fx-background-repeat: no-repeat; ");

		this.buttonCreatePiker.setStyle("" + "-fx-background-color: transparent;" + "-fx-background-image: url('/images/spartan2.png'); "
				+ "-fx-background-size: 64px 64px; " + "-fx-background-repeat: no-repeat; ");

		this.buttonCreateOnager.setStyle("" + "-fx-background-color: transparent;" + "-fx-background-image: url('/images/catapult2.png'); "
				+ "-fx-background-size: 64px 64px; " + "-fx-background-repeat: no-repeat; ");

		this.buttonUpgradeCastle
				.setStyle("" + "-fx-background-color: transparent;" + "-fx-background-image: url('/images/egyptian-temple-b.png'); "
						+ "-fx-background-size: 64px 64px; " + "-fx-background-repeat: no-repeat; ");

		this.removeAllProduction.setStyle("" + "-fx-background-color: transparent;" + "-fx-background-image: url('/images/cancel.png'); "
				+ "-fx-background-size: 64px 64px; " + "-fx-background-repeat: no-repeat; ");

		this.removeLastProduction
				.setStyle("" + "-fx-background-color: transparent;" + "-fx-background-image: url('/images/anticlockwise-rotation.png'); "
						+ "-fx-background-size: 64px 64px; " + "-fx-background-repeat: no-repeat; ");

		this.buttonCreateOnager.setOnMousePressed(event ->
		{
			if (this.input.isAlt())
			{
				while (addProduction(this.buttonCreateOnager, new Onager()))
				{
				}
			}
			else if (this.input.isShift())
			{
				for (int i = 0; i < 10; i++)
				{
					addProduction(this.buttonCreateOnager, new Onager());
				}

			}
			else
			{
				addProduction(this.buttonCreateOnager, new Onager());
			}

		});

		this.buttonCreatePiker.setOnMousePressed(event ->
		{
			if (this.input.isAlt())
			{
				while (addProduction(this.buttonCreatePiker, new Piker()))
				{
				}
			}
			else if (this.input.isShift())
			{
				for (int i = 0; i < 10; i++)
				{
					addProduction(this.buttonCreatePiker, new Piker());
				}
			}
			else
			{
				addProduction(this.buttonCreatePiker, new Piker());
			}

		});

		this.buttonCreateKnight.setOnMousePressed(event ->
		{
			if (this.input.isAlt())
			{
				while (addProduction(this.buttonCreateKnight, new Knight()))
				{
				}
			}
			else if (this.input.isShift())
			{
				for (int i = 0; i < 10; i++)
				{
					addProduction(this.buttonCreateKnight, new Knight());
				}
			}
			else
			{
				addProduction(this.buttonCreateKnight, new Knight());
			}

		});

		this.buttonUpgradeCastle.setOnMousePressed(event ->
		{
			if (this.input.isAlt())
			{
				while (addProduction(this.buttonUpgradeCastle, new Castle(this.currentCastle.getLevel())))
				{
				}
			}
			else if (this.input.isShift())
			{
				for (int i = 0; i < 10; i++)
				{
					addProduction(this.buttonUpgradeCastle, new Castle(this.currentCastle.getLevel()));
				}
			}
			else
			{
				addProduction(this.buttonUpgradeCastle, new Castle(this.currentCastle.getLevel()));
			}
		});

		this.removeAllProduction.setOnMousePressed(event -> this.currentCastle.getCaserne().resetQueue(true));
		this.removeLastProduction.setOnMousePressed(event -> this.currentCastle.getCaserne().removeLastProduction(true));

		this.buttonCreatePiker.setOnMouseEntered(event ->
		{
			this.pikerCost.setVisible(true);
		});

		this.buttonCreateKnight.setOnMouseEntered(event ->
		{
			this.knightCost.setVisible(true);
		});

		this.buttonCreateOnager.setOnMouseEntered(event ->
		{
			this.onagerCost.setVisible(true);
		});

		this.buttonUpgradeCastle.setOnMouseEntered(event ->
		{
			this.castleCost.setVisible(true);
		});

		this.buttonCreateKnight.setOnMouseExited(event -> this.knightCost.setVisible(false));
		this.buttonCreatePiker.setOnMouseExited(event -> this.pikerCost.setVisible(false));
		this.buttonCreateOnager.setOnMouseExited(event -> this.onagerCost.setVisible(false));
		this.buttonUpgradeCastle.setOnMouseExited(event -> this.castleCost.setVisible(false));

		addEventMouse(this.buttonCreateKnight);
		addEventMouse(this.buttonCreateOnager);
		addEventMouse(this.buttonCreatePiker);
		addEventMouse(this.buttonUpgradeCastle);
		addEventMouse(this.removeAllProduction);
		addEventMouse(this.removeLastProduction);
	}

	/**
	 * Change la couleur du shadom du bouton suivant si la production a bien été ajouté ou non.
	 *
	 * @param  b Le bouton qui vient d'être activé.
	 * @param  p La production qu'on veut produire.
	 * @return   Retourne true si la production a bien été ajouté à la queue de production de la caserne
	 *           du château courant.
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
	 * Initalise les paramètres du background.
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
	 * Ajoute des événements sur un bouton.
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
		addNode(this.buttonCreateKnight);
		addNode(this.buttonCreateOnager);
		addNode(this.buttonCreatePiker);
		addNode(this.buttonUpgradeCastle);
		addNode(this.fillTime);
		addNode(this.removeAllProduction);
		addNode(this.removeLastProduction);
		addNode(this.castleCost);
		addNode(this.knightCost);
		addNode(this.onagerCost);
		addNode(this.pikerCost);
	}

	@Override
	public void relocateAllNodes()
	{
		final int i = 90;
		final int offset = 560;

		final float margin = (float) MARGIN_PERCENTAGE + 0.076f;

		relocate(this.buttonCreatePiker, SCENE_WIDTH * margin + i * 0, offset);
		relocate(this.buttonCreateKnight, SCENE_WIDTH * margin + i * 1, offset);
		relocate(this.buttonCreateOnager, SCENE_WIDTH * margin + i * 2, offset);
		relocate(this.buttonUpgradeCastle, SCENE_WIDTH * margin + i * 1, offset + i * 1);
		relocate(this.castleCost, this.buttonUpgradeCastle.getLayoutX(), this.buttonUpgradeCastle.getLayoutY() + 20);
		relocate(this.knightCost, this.buttonCreateKnight.getLayoutX(), this.buttonCreateKnight.getLayoutY() + 20);
		relocate(this.pikerCost, this.buttonCreatePiker.getLayoutX(), this.buttonCreatePiker.getLayoutY() + 20);
		relocate(this.onagerCost, this.buttonCreateOnager.getLayoutX(), this.buttonCreateOnager.getLayoutY() + 20);

		relocate(this.removeAllProduction, SCENE_WIDTH * margin + i * 2 - i * 0.5f, offset + i * 3);
		relocate(this.removeLastProduction, SCENE_WIDTH * margin + i * 1 - i * 0.5f, offset + i * 3);

		relocate(this.fillTime, SCENE_WIDTH * margin + 1, offset + i * 4);
		relocate(this.backgroundTime, SCENE_WIDTH * margin + 1, offset + i * 4);

		relocate(this.background, SCENE_WIDTH * margin - 17, offset - 22);
	}

	/**
	 * Change le château courant et rend visible ou invisible ce module.
	 *
	 * @param castle            Le nouveau château courant.
	 * @param productionVisible Spécifie si les parties graphiques de ce module seront visible ou non.
	 */
	public void switchCastle(final Castle castle, final boolean productionVisible)
	{
		this.currentCastle = castle;
		setAllVisible(productionVisible);
	}

	/*************************************************/
	/*************** GETTERS / SETTERS ***************/
	/*************************************************/

	/**
	 * @param scene the scene to set
	 */
	public void setScene(final Scene scene)
	{
		this.input = new Input(scene);
		this.input.addListeners();
	}
}
