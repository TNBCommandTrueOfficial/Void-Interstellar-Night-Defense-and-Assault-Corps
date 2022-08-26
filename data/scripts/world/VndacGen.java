package world;

import com.fs.starfarer.api.campaign.SectorAPI;
import systems.CelakaSystem;

public class VndacGen {
    public void generate(SectorAPI sector) {
//        new CelakaNewSystem().generate(sector);
        new CelakaSystem().generate(sector);
    }
}
