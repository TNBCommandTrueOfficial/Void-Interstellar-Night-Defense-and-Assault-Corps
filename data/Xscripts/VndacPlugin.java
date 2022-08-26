import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import exerelin.campaign.SectorManager;
import tnb.vndac.world.VndacGen;

public class VndacPlugin extends BaseModPlugin {
    @Override
    public void onApplicationLoad() throws Exception {
        super.onApplicationLoad();

        // Test that the .jar is loaded and working, using the most obnoxious way possible.
//        throw new RuntimeException("Template mod loaded! Remove this crash in TemplateModPlugin.");
    }

    // STANDALONE
//    private static void initMyMod() {
//        new VndacGen().generate(Global.getSector());
//    }

    @Override
    public void onNewGame() {
        super.onNewGame();
        Global.getLogger(this.getClass()).info("Hooray My mod plugin in a jar is loaded!");

        // STANDALONE
//        initMyMod();

        // NEXERELIN
        // The code below requires that Nexerelin is added as a library (not a dependency, it's only needed to compile the mod).
//        boolean isNexerelinEnabled = Global.getSettings().getModManager().isModEnabled("nexerelin");
//
//        if (!isNexerelinEnabled || SectorManager.getManager().isCorvusMode()) {
//                    new tnb.vndac.world.VndacGen().generate(Global.getSector());
//             Add code that creates a new star system (will only run if Nexerelin's Random (corvus) mode is disabled).
//        }
    }
}
