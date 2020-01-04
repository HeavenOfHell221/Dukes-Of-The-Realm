package UI;

import DukesOfTheRealm.Castle;
import DukesOfTheRealm.Main;
import Enums.SoldierEnum;
import Interface.IUI;
import Interface.IUpdate;
import Utility.Settings;
import Utility.SoldierPack;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * G�re l'interface utilisateur des attaques.
 */
public final class UIAttackPreview extends Parent implements IUpdate, IUI
{
	/**
	 * Background qui d�limite l'interface utilisateur de ce module.
	 */
	private transient Rectangle background;

	/**
	 * Bouton permettant de lancer une attaque ou d'envoyer des renforts.
	 */
	private final Button buttonAttack;

	/**
	 * Nombre actuelle d'unit� dans l'ost en cours de cr�ation.
	 */
	private SoldierPack<Integer> soldierPack;

	/**
	 * R�f�rence sur le ch�teau que va recevoir l'ost.
	 *
	 * @see UIAttackPreview#switchCastle(Castle, boolean)
	 */
	private Castle currentCastle;

	/**
	 * R�f�rence sur le ch�teau qui va envoyer l'ost.
	 *
	 * @see UIAttackPreview#switchCastle(Castle, boolean)
	 */
	private Castle lastCastle;

	/**
	 * Boutons permettant d'augmenter le nombre d'une unit� dans l'ost.
	 * <p>
	 * Cliquer : Augmente de 1 � chaque clique. <br>
	 * Molette souris vers le haut : Augmente de 1 � chaque crant.
	 * </p>
	 */
	private SoldierPack<Button> upSoldier;
	
	/**
	 * Boutons permettant de diminuer le nombre d'une unit� dans l'ost.
	 * <p>
	 * Cliquer : diminue de 1 � chaque clique. <br>
	 * Molette souris vers le bas : diminue de 1 � chaque crant.
	 * </p>
	 */
	private SoldierPack<Button> downSoldier;
	
	/**
	 * Textes qui affiche le nombre actuelle des unit�s dans l'ost en cours de cr�ation.
	 */
	private SoldierPack<Text> textSoldier;
	
	/**
	 * Icones repr�sentant les unit�s sur l'interface.
	 */
	private SoldierPack<ImageView> imageSoldier;

	/*************************************************/
	/***************** CONSTRUCTEURS *****************/
	/*************************************************/

	/**
	 * Constructeur par d�faut de UIAttackPreview.
	 */
	public UIAttackPreview()
	{
		this.soldierPack = new SoldierPack<Integer>();
		this.upSoldier = new SoldierPack<Button>();
		this.downSoldier = new SoldierPack<Button>();
		this.imageSoldier = new SoldierPack<ImageView>();
		this.textSoldier = new SoldierPack<Text>();
		
		this.imageSoldier.replace(SoldierEnum.Piker, newImageView("/images/PikerPreview_64.png", 64, 64));
		this.imageSoldier.replace(SoldierEnum.Knight, newImageView("/images/KnightPreview_64.png", 64, 64));
		this.imageSoldier.replace(SoldierEnum.Onager, newImageView("/images/OnagerPreview_64.png", 64, 64));
		this.imageSoldier.replace(SoldierEnum.Archer, newImageView("/images/ArcherPreview_64.png", 64, 64));
		this.imageSoldier.replace(SoldierEnum.Berserker, newImageView("/images/BerserkerPreview_64.png", 64, 64));
		this.imageSoldier.replace(SoldierEnum.Spy, newImageView("/images/SpyPreview_64.png", 64, 64));

		this.background = new Rectangle(340, 530);
		
		this.buttonAttack = new Button();
		
		for(SoldierEnum s : SoldierEnum.values())
		{
			this.upSoldier.replace(s, new Button());
			this.downSoldier.replace(s, new Button());
			this.textSoldier.replace(s, new Text());
		}

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
		setAllButtons();
		setAllTexts();
		setBackground();
		setAllVisible(false);
	}

	/*************************************************/
	/******************** UPDATE *********************/
	/*************************************************/

	@Override
	public void update(final long now, final boolean pause)
	{
		for(SoldierEnum s : SoldierEnum.values())
		{
			this.textSoldier.get(s).setText(this.soldierPack.get(s).toString());
		}
	}

	/*************************************************/
	/******************* METHODES ********************/
	/*************************************************/

	/**
	 * M�thode qui va initialiser tout les objets Text de ce module.
	 *
	 * @see UIAttackPreview#setText(Text, int)
	 */
	private void setAllTexts()
	{
		for(SoldierEnum s : SoldierEnum.values())
		{
			setText(this.textSoldier.get(s), 38);
		}
	}

	/**
	 * Initalise un objet Text avec des param�tres pr�d�finis.
	 *
	 * @param text Lobjet Text qu'on initialise
	 * @param font La taille de la police.
	 */
	private void setText(final Text text, final int font)
	{
		text.setFont(new Font(font));
		text.setWrappingWidth(64);
		text.setTextAlignment(TextAlignment.CENTER);
		text.setFill(Color.WHITE);
		text.setText("0");
	}

	/**
	 * Remet � 0 le nombre de chaque unit� qui est affich�.
	 */
	private void resetOst()
	{
		for(SoldierEnum s : SoldierEnum.values())
		{
			this.soldierPack.replace(s, 0);
		}
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
	 * Initialise et place les �v�nements de la souris sur tout les boutons du module.
	 */
	private void setAllButtons()
	{

		setStyle(this.buttonAttack, "images/Attack_64.png");

		for(SoldierEnum s : SoldierEnum.values())
		{
			setStyle(this.downSoldier.get(s), "images/RemoveSoldierOst_64.png");
			setStyle(this.upSoldier.get(s), "images/AddSoldierOst_64.png");
			
			this.upSoldier.get(s).setOnMousePressed(event ->
			{
				if(this.soldierPack.get(s) < this.lastCastle.getReserveOfSoldiers().getSoldierPack().get(s))
				{
					this.soldierPack.replace(s, this.soldierPack.get(s) + 1);
				}
			});
			
			this.upSoldier.get(s).setOnScroll(event ->
			{
				if(event.getDeltaY() > 0 && this.soldierPack.get(s) < this.lastCastle.getReserveOfSoldiers().getSoldierPack().get(s))
				{
					this.soldierPack.replace(s, this.soldierPack.get(s) + 1);
				}
			});
			
			this.downSoldier.get(s).setOnMousePressed(event ->
			{
				this.soldierPack.replace(s, this.soldierPack.get(s) > 0 ?  (this.soldierPack.get(s) - 1) : 0);
			});
			
			this.downSoldier.get(s).setOnScroll(event ->
			{
				if(event.getDeltaY() < 0 && this.soldierPack.get(s) > 0)
				{
					this.soldierPack.replace(s, this.soldierPack.get(s) - 1);
				}
			});
			
		}
		
		this.buttonAttack.setOnMousePressed(e ->
		{
			this.lastCastle.createOst(this.currentCastle, this.soldierPack);
			reset();
		});
	}

	/**
	 * R�initialise enti�rement le module et le rend invisible.
	 */
	private void reset()
	{
		resetOst();
		setAllVisible(false);
		Main.pause = false;
	}

	/**
	 * Initialise le background (couleur, effets, apparence graphique..)
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

	@Override
	public void addNode(final Node node)
	{
		getChildren().add(node);
	}

	@Override
	public void addAllNodes()
	{
		addNode(this.background);
		addNode(this.buttonAttack);
		
		for(SoldierEnum s : SoldierEnum.values())
		{
			addNode(this.upSoldier.get(s));
			addNode(this.downSoldier.get(s));
			addNode(this.imageSoldier.get(s));
			addNode(this.textSoldier.get(s));
		}
	}

	@Override
	public void relocateAllNodes()
	{
		final int i = 69;
		final int offset = 530;
		final float margin = (float) Settings.MARGIN_PERCENTAGE + 0.053f;
		int multiplier = 0;
		
		for(SoldierEnum s : SoldierEnum.values())
		{
			relocate(this.imageSoldier.get(s), Settings.SCENE_WIDTH * margin + 20, 20 + offset + i * multiplier);
			relocate(this.upSoldier.get(s), Settings.SCENE_WIDTH * margin + 25 + i, 20 + offset + i * multiplier);
			relocate(this.downSoldier.get(s), Settings.SCENE_WIDTH * margin + 25 + i * 3, 20 + offset + i * multiplier);
			relocate(this.textSoldier.get(s), Settings.SCENE_WIDTH * margin + 25 + i * 2, 45 + offset + i * multiplier);
			multiplier++;
		}

		relocate(this.buttonAttack, Settings.SCENE_WIDTH * margin + 30 + i * 1.5, offset + 440);
		relocate(this.background, Settings.SCENE_WIDTH * margin, offset);
	}

	@Override
	public void setAllVisible(final boolean visible)
	{
		setVisible(this.background, visible);
		setVisible(this.buttonAttack, visible);
		
		for(SoldierEnum s : SoldierEnum.values())
		{
			setVisible(this.upSoldier.get(s), visible);
			setVisible(this.downSoldier.get(s), visible);
			setVisible(this.imageSoldier.get(s), visible);
			setVisible(this.textSoldier.get(s), visible);
		}
		
		resetOst();
	}

	/**
	 * Change le ch�teau courant et fixe la visibilit� de ce module en fonction du boolean
	 * attackVisible.
	 *
	 * @param castle        Le nouveau ch�teau courant.
	 * @param attackVisible Sp�cifie si ce module est visible ou non.
	 * @see                 UIManager#switchCastle(Castle)
	 */
	public void switchCastle(final Castle castle, final boolean attackVisible)
	{
		this.lastCastle = this.currentCastle;
		this.currentCastle = castle;

		setAllVisible(attackVisible);
	}

	/*************************************************/
	/*************** GETTERS / SETTERS ***************/
	/*************************************************/
}
