package UI;

import static Utility.Settings.KNIGHT_COST;
import static Utility.Settings.LEVEL_UP_COST;
import static Utility.Settings.MARGIN_PERCENTAGE;
import static Utility.Settings.ONAGER_COST;
import static Utility.Settings.PIKER_COST;
import static Utility.Settings.SCENE_WIDTH;

import DukesOfTheRealm.Castle;
import Interface.IProductionUnit;
import Interface.IUI;
import Interface.IUpdate;
import Soldiers.Knight;
import Soldiers.Onager;
import Soldiers.Piker;
import Utility.Input;
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
 * G�re l'interface utilisateur pour la production d'unit� et l'am�lioration des b�timents
 */
public final class UIProductionUnitPreview extends Parent implements IUpdate, IUI
{

	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/

	/**
	 * Bouton pour produire un Piker.
	 */
	private final Button buttonCreatePiker;

	/**
	 * Bouton pour produire un Knight.
	 */
	private final Button buttonCreateKnight;

	/**
	 * Bouton pour produire un Onager
	 */
	private final Button buttonCreateOnager;

	/**
	 * Bouton pour am�liorer le ch�teau
	 */
	private final Button buttonUpgradeCastle;

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
	 * Co�t de production d'un Piker affich� sur le bouton.
	 *
	 * @see UIProductionUnitPreview#buttonCreatePiker
	 */
	private final Text pikerCost;

	/**
	 * Co�t de production d'un Onager affich� sur le bouton.
	 *
	 * @see UIProductionUnitPreview#buttonCreateOnager
	 */
	private final Text onagerCost;

	/**
	 * Co�t de production d'un Knight affich� sur le bouton.
	 *
	 * @see UIProductionUnitPreview#buttonCreateKnight
	 */
	private final Text knightCost;

	/**
	 * Co�t de production de l'am�lioration du ch�teau affich� sur le bouton.
	 *
	 * @see UIProductionUnitPreview#buttonUpgradeCastle
	 */
	private final Text castleCost;

	/**
	 * Object Input pour r�cup�rer les touches clavier saisies par l'utilisateur.
	 */
	private Input input;

	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

	/**
	 * Constructeur par d�faut de UIProductionUnitPreview.
	 */
	public UIProductionUnitPreview()
	{
		this.buttonCreatePiker = new Button();
		this.buttonCreateKnight = new Button();
		this.buttonCreateOnager = new Button();
		this.buttonUpgradeCastle = new Button();
		this.removeAllProduction = new Button();
		this.removeLastProduction = new Button();

		this.background = new Rectangle(280, 450); // 280 / 300

		this.backgroundTime = new Rectangle(240, 40);
		this.fillTime = new Rectangle(0, 40); // entre +00 et +210

		this.castleCost = new Text();
		this.knightCost = new Text();
		this.onagerCost = new Text();
		this.pikerCost = new Text();
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
		this.castleCost.setVisible(false);
		this.knightCost.setVisible(false);
		this.onagerCost.setVisible(false);
		this.pikerCost.setVisible(false);
	}

	/*************************************************/
	/******************** UPDATE *********************/
	/*************************************************/

	@Override
	public void update(final long now, final boolean pause)
	{
		setFill(this.currentCastle.getRatio());
		this.input.update(now, pause);
		if (this.castleCost.isVisible())
		{
			if (this.input.isShift())
			{
				int price = 0;

				for (int i = 0; i < 10; i++)
				{
					price += LEVEL_UP_COST * (this.currentCastle.getLevel() + this.currentCastle.getNbCastleInProduction() + i);
				}

				this.castleCost.setText(price + "");
			}
			else
			{
				this.castleCost
						.setText(LEVEL_UP_COST * (this.currentCastle.getLevel() + this.currentCastle.getNbCastleInProduction()) + "");
			}

		}

		if (this.pikerCost.isVisible())
		{
			if (this.input.isShift())
			{
				this.pikerCost.setText(PIKER_COST * 10 + "");
			}
			else
			{
				this.pikerCost.setText(PIKER_COST + "");
			}
		}

		if (this.onagerCost.isVisible())
		{

			if (this.input.isShift())
			{
				this.onagerCost.setText(ONAGER_COST * 10 + "");
			}
			else
			{
				this.onagerCost.setText(ONAGER_COST + "");
			}
		}

		if (this.knightCost.isVisible())
		{

			if (this.input.isShift())
			{
				this.knightCost.setText(KNIGHT_COST * 10 + "");
			}
			else
			{
				this.knightCost.setText(KNIGHT_COST + "");
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
	 * Initialise un texte avec des param�tres pr�d�finis.
	 *
	 * @param t Le texte � initialiser.
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
	 * Initialise tout les boutons en leur affectant un style, une image , des effets ainsi que des
	 * �v�nements.
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

		this.removeAllProduction.setOnMousePressed(event -> this.currentCastle.resetQueue(true));
		this.removeLastProduction.setOnMousePressed(event -> this.currentCastle.removeLastProduction(true));

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
	 * Change la couleur du shadom du bouton suivant si la production a bien �t� ajout� ou non.
	 *
	 * @param  b Le bouton qui vient d'�tre activ�.
	 * @param  p La production qu'on veut produire.
	 * @return   Retourne true si la production a bien �t� ajout� � la queue de production de la caserne
	 *           du ch�teau courant.
	 */
	private boolean addProduction(final Button b, final IProductionUnit p)
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
		final int offset = 540;

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
