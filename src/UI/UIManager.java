package UI;

import DukesOfTheRealm.Castle;
import DukesOfTheRealm.Main;
import Interface.IUI;
import Interface.IUpdate;
import Utility.Settings;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;

/**
 * G�re les modules d'iinterface utilisateur du jeu.
 * @see UIAttackPreview
 * @see UICastlePreview
 * @see UIProductionUnitPreview
 */
public class UIManager extends Parent implements IUI, IUpdate
{
	/*************************************************/
	/******************* ATTRIBUTS *******************/
	/*************************************************/

	/**
	 * Instance de la classe UIManager utilis� comme singleton.
	 */
	private static UIManager instance = new UIManager();

	/**
	 * R�f�rence � un oobjet UIAttackPreview qui va s'occuper de l'interface utilisateur des attaques.
	 * @See UIAttackPreview
	 */
	private UIAttackPreview attackPreview;

	/**
	 * R�f�rence � un oobjet UIProductionUnitPreview qui va s'occuper de l'interface utilisateur de la production.
	 * @see UIProductionUnitPreview
	 */
	private UIProductionUnitPreview productionUnitPreview;

	/**
	 * R�f�rence � un objet UICastlePreview qui va s'occuper de l'interface utilisateur des donn�es afficher des ch�teaux.
	 * @see UICastlePreview
	 */
	private UICastlePreview castlePreview;

	/**
	 * R�f�rence au pane principale du jeu.
	 * @see DukesOfTheRealm.Main#playfieldLayer
	 */
	private Pane playfieldLayer;

	/**
	 * Background principal qui dissocie la partie "jeu" de la partie "interface utilisateur"
	 */
	private Rectangle background;

	/**
	 * R�f�rence sur le dernier ch�teau s�lectionn�.
	 * @see UIManager#switchCastle(Castle)
	 * @see DukesOfTheRealm.Castle
	 */
	private Castle currentCastle;

	/**
	 * R�f�rence sur l'avant dernier ch�teau s�lectionn�.
	 * @see UIManager#switchCastle(Castle)
	 * @see DukesOfTheRealm.Castle
	 */
	private Castle lastCastle;

	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

	/**
	 * Constructeur par d�faut de UIManager.
	 */
	private UIManager()
	{

	}

	/*************************************************/
	/********************* START *********************/
	/*************************************************/

	@Override
	public void start()
	{
		addAllNodes();
		relocateAllNodes();
		setBackground();

		getAttackPreview().start();
		getCastlePreview().start();
		getProductionUnitPreview().start();
	}

	/**
	 * M�thode appel� avant la m�thode start pour cr�er les r�f�rences des diff�rents objets qui g�re l'interface, le background et set le pane.
	 * @param pane Le pane principal du jeu.
	 * @see UIManager#start()
	 * @see DukesOfTheRealm.Main#playfieldLayer
	 */
	public void awake(final Pane pane)
	{
		this.playfieldLayer = pane;
		this.background = new Rectangle(Settings.SCENE_WIDTH * (1 - Settings.MARGIN_PERCENTAGE), Settings.SCENE_HEIGHT * 2);
		this.attackPreview = new UIAttackPreview();
		this.castlePreview = new UICastlePreview();
		this.productionUnitPreview = new UIProductionUnitPreview();
		start();
	}

	/*************************************************/
	/******************** UPDATE *********************/
	/*************************************************/

	@Override
	public void update(final long now, final boolean pause)
	{
		if (this.currentCastle != null && this.currentCastle.getActor() != null)
		{
			this.productionUnitPreview.update(now, pause);
			this.attackPreview.update(now, pause);
			this.castlePreview.update(now, pause);
		}
	}

	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/

	@Override
	public void relocate(final Node node, final double x, final double y)
	{
		node.relocate(x, y);
	}

	@Override
	public void addNode(final Node node)
	{
		this.playfieldLayer.getChildren().add(node);
		this.background.toBack();
	}

	/**
	 * Initialise les param�tres du background tel que sa couleur et ses effets visuels.
	 * @see UIManager#background
	 */
	private void setBackground()
	{
		final Stop[] stops = new Stop[]
		{
				new Stop(0, Color.web("#753F0B")), new Stop(1, Color.web("#4F2E0F"))
		};
		final LinearGradient lg1 = new LinearGradient(0, 0, 1, 0, true, CycleMethod.REFLECT, stops);
		this.background.setFill(lg1);

		final DropShadow a = new DropShadow();
		a.setOffsetX(-2);
		a.setOffsetY(0);
		a.setSpread(0.1);
		a.setRadius(5);
		a.setColor(Color.BLACK);
		this.background.setEffect(a);
	}

	@Override
	public void addAllNodes()
	{
		addNode(this.attackPreview);
		addNode(this.castlePreview);
		addNode(this.productionUnitPreview);
		addNode(this.background);
	}

	@Override
	public void relocateAllNodes()
	{
		relocate(this.background, Settings.SCENE_WIDTH * (Settings.MARGIN_PERCENTAGE + 0.0375), 0);
	}

	/**
	 * Change le ch�teau courant et rend visible ou invisible les modules UI en fonction de l'acteur du nouveau ch�teau courant.
	 * @param castle Le nouveau ch�teau courant.
	 */
	public void switchCastle(final Castle castle)
	{
		this.lastCastle = this.currentCastle;
		this.currentCastle = castle;

		boolean attackVisible = false;
		boolean productionVisible = false;
		boolean castleSwitch = false;

		if (this.lastCastle == null)
		{
			productionVisible = true;
		}
		else if (!this.currentCastle.getActor().isPlayer() && this.lastCastle.getActor().isPlayer())
		{
			attackVisible = true;
		}
		else if (this.currentCastle.getActor().isPlayer() && this.lastCastle.getActor().isPlayer())
		{
			if (this.currentCastle == this.lastCastle)
			{
				productionVisible = true;
			}
			else
			{
				attackVisible = true;
			}
		}
		else if (this.currentCastle.getActor().isPlayer() && !this.lastCastle.getActor().isPlayer())
		{
			productionVisible = true;
		}

		Main.pause = attackVisible;
		castleSwitch = !attackVisible;

		this.attackPreview.switchCastle(castle, attackVisible);
		this.productionUnitPreview.switchCastle(castle, productionVisible);
		this.castlePreview.switchCastle(castle, castleSwitch, productionVisible, attackVisible);
	}

	@Override
	public void setAllVisible(final boolean visible)
	{
		setVisible(this.background, visible);
	}

	@Override
	public void setVisible(final Node node, final boolean visible)
	{
		node.setVisible(visible);
	}

	/*************************************************/
	/*************** GETTERS / SETTERS ***************/
	/*************************************************/

	/**
	 * @return the instance
	 */
	public static final UIManager getInstance()
	{
		return instance;
	}

	/**
	 * @param playfieldLayer the playfieldLayer to set
	 */
	public final void setPlayfieldLayer(final Pane playfieldLayer)
	{
		instance.playfieldLayer = playfieldLayer;
	}

	/**
	 * @return the attackPreview
	 */
	public static final UIAttackPreview getAttackPreview()
	{
		return instance.attackPreview;
	}

	/**
	 * @return the productionUnitPreview
	 */
	public static final UIProductionUnitPreview getProductionUnitPreview()
	{
		return instance.productionUnitPreview;
	}

	/**
	 * @return the castlePreview
	 */
	public static final UICastlePreview getCastlePreview()
	{
		return instance.castlePreview;
	}
}
