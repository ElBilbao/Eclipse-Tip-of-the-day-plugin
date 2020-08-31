package dasoft.introeclipse.tipadder;

import java.util.ArrayList;
import java.util.List;

import dasoft.introeclipse.tipoftheday.extensionsource.ITipSource;

public class EclipseTips implements ITipSource {
	
	List<String> consejosNuevos = new ArrayList<String>();
	public EclipseTips() {
		consejosNuevos.add("Lava tus manos constantemente");
		consejosNuevos.add("Manten una distancia con otras personas al salir de tu hogar");
		consejosNuevos.add("Evita salir de tu hogar");
		consejosNuevos.add("Limpia tu hogar con productos desinfectantes");
	}

	@Override
	public List<String> getTips() {
		return consejosNuevos;
	}
	

}
