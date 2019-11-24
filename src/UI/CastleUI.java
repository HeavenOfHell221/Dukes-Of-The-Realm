package UI;

import DukesOfTheRealm.Castle;
import DukesOfTheRealm.Kingdom;
import Utility.Settings;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class CastleUI {

	private Castle castle;
	private Text t;
	private Pane playfieldLayer;
	
	private static CastleUI instance = new CastleUI();
	
	private CastleUI()
	{	
		this.playfieldLayer = playfieldLayer;
		this.t = new Text();
		this.t.setFont(new Font(20));
		this.t.setWrappingWidth(500);
		this.t.setTextAlignment(TextAlignment.CENTER);
		this.t.relocate(Settings.SCENE_WIDTH * (Settings.MARGIN_PERCENTAGE + 0.05), 150);
	}
	
	public void Update()
	{
		if(this.castle != null)
		{
			if(this.castle.GetActor() == Kingdom.GetPlayer())
				this.t.setText(
					"Name : " + castle.GetActor().GetName() + "\n" + 
					"Level : " + this.castle.GetLevel() + "\n" +
					Settings.FLORIN_PER_SECOND*this.castle.GetLevel() + " Florin(s) / s" + "\n" +
					"Florin : " + (int)this.castle.GetTotalFlorin() + "\n\n" +
					"Piker : " + this.castle.GetReserveOfSoldiers().getNbPikers() + "\n" +
					"Knights : " + this.castle.GetReserveOfSoldiers().getNbKnights() + "\n" +
					"Onager : " + this.castle.GetReserveOfSoldiers().getNbOnagers() + "\n");
			else
				this.t.setText(
						"Name : " + castle.GetActor().GetName() + "\n" + 
						"Florin : " + (int)this.castle.GetTotalFlorin() +  " | level : " + this.castle.GetLevel());
		}
			
	}
	
	public void SwitchCastle(Castle castle)
	{
		this.castle = castle;
	}
	
	public static CastleUI GetInstance()
	{
		return instance;
	}
	
	public void SetCastle(Castle castle)
	{
		this.castle = castle;
	}
	
	public Castle GetCastle()
	{
		return this.castle;
	}
	
	public void SetPlayfieldLayer(Pane playfieldLayer)
	{
		this.playfieldLayer = playfieldLayer;
		this.playfieldLayer.getChildren().add(t);
	}
}
