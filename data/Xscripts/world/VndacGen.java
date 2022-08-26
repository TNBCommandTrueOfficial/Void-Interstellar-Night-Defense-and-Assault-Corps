package tnb.vndac.world;

import com.fs.starfarer.api.campaign.SectorAPI;
import tnb.vndac.world.systems.CelakaNewSystem;
import tnb.vndac.world.systems.CelakaSystem;

public class VndacGen {
    public void generate(SectorAPI sector) {
//        new CelakaNewSystem().generate(sector);
        new CelakaSystem().generate(sector);
    }
}
