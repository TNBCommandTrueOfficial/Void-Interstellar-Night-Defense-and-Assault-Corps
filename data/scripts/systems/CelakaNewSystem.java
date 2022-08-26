package systems;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.ids.*;
import com.fs.starfarer.api.impl.campaign.procgen.NebulaEditor;
import com.fs.starfarer.api.impl.campaign.procgen.PlanetConditionGenerator;
import com.fs.starfarer.api.impl.campaign.procgen.StarAge;
import com.fs.starfarer.api.impl.campaign.terrain.AsteroidFieldTerrainPlugin.AsteroidFieldParams;
import com.fs.starfarer.api.impl.campaign.terrain.HyperspaceTerrainPlugin;
import com.fs.starfarer.api.util.Misc;
import org.lazywizard.lazylib.MathUtils;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;

import static utils.UtilTools.addMarketplace;

public class CelakaNewSystem {
    public void generate(SectorAPI sector) {

        StarSystemAPI system = sector.createStarSystem("Celaka");
        system.getLocation().set(-39000, 39000); //top leftish

        system.setBackgroundTextureFilename("graphics/backgrounds/celaka_background.jpg");

        //setup all distances here
        final float asteroids1Dist = 2750f;
        final float celakaDeltaDist = 1850f;
        final float celakaPrimeDist = 3500f;
        final float stable1Dist = 4200f;
        final float celakaPhiDist = 4800f;
        final float asteroidBelt1Dist = 5700f;
        final float celakaBetaDist = 6500f;
        final float stable2Dist = 7100f;
        final float strolluckDist = 8450f;
        final float asteroids2Dist = 8900f;
        final float celakaMajorisDist = 11400f;
        final float celakaGammaDist = 14100f;
        final float stable3Dist = 15200f;
        final float asteroidBelt2Dist = 15700f;
        final float bloodDist = 17200f;
        final float hye_steelDist = 24700f;

        final float jumpInnerDist = 3050f;
        final float jumpOuterDist = 8400f;
        final float jumpFringeDist = 16700f;

        final float majorisRad = 670f;

        // create the star and generate the hyperspace anchor for this system
        PlanetAPI celakaStar = system.initStar("celaka", // unique id for this star
                "star_red_giant", // id in planets.json
                1100f, // radius (in pixels at default zoom)
                450); // corona radius, from star edge
        system.setLightColor(new Color(239, 155, 128)); // light color in entire system, affects all entities

        SectorEntityToken celakaAF1 = system.addTerrain(Terrain.ASTEROID_FIELD,
                new AsteroidFieldParams(
                        200f, // min radius
                        300f, // max radius
                        8, // min asteroid count
                        16, // max asteroid count
                        4f, // min asteroid radius
                        16f, // max asteroid radius
                        "Asteroids Field")); // null for default name
        celakaAF1.setCircularOrbit(celakaStar, 130, asteroids1Dist, 240);

        //add first stable loc
        SectorEntityToken stableLoc1 = system.addCustomEntity("celaka_stableLoc_1", "Stable Location", "stable_location", Factions.NEUTRAL);
        stableLoc1.setCircularOrbit(celakaStar, MathUtils.getRandomNumberInRange(0f, 360f), stable1Dist, 520);

        //asteroid belt1 ring
        system.addAsteroidBelt(celakaStar, 1000, asteroidBelt1Dist, 800, 250, 400, Terrain.ASTEROID_BELT, "Inner Band");
        system.addRingBand(celakaStar, "misc", "rings_asteroids0", 256f, 3, Color.gray, 256f, asteroidBelt1Dist - 200, 250f);
        system.addRingBand(celakaStar, "misc", "rings_asteroids0", 256f, 0, Color.gray, 256f, asteroidBelt1Dist, 350f);
        system.addRingBand(celakaStar, "misc", "rings_asteroids0", 256f, 2, Color.gray, 256f, asteroidBelt1Dist + 200, 400f);

        // Hye-Steel: Useful world way far out, ruins, decivilized
        PlanetAPI hye_steel = system.addPlanet("hye_steel",
                celakaStar,
                "Hye-Steel",
                "frozen",   // starsector-core\data\config\planets.json
                360 * (float) Math.random(),
                190f,
                hye_steelDist,
                3421f);
//        hye_steel.setCustomDescriptionId("redlegion_celaka_hye_steel"); //reference descriptions.csv
        hye_steel.getMarket().addCondition(Conditions.RUINS_WIDESPREAD);
        hye_steel.getMarket().addCondition(Conditions.VERY_COLD);
        hye_steel.getMarket().addCondition(Conditions.DECIVILIZED);
        hye_steel.getMarket().addCondition(Conditions.DARK);
        hye_steel.getMarket().addCondition(Conditions.ORE_ULTRARICH);
        hye_steel.getMarket().addCondition(Conditions.RARE_ORE_MODERATE);

        // Celaka Gamma: Dead World
        PlanetAPI celakaGamma = system.addPlanet("celaka_gamma",
                celakaStar,
                "Celaka Gamma",
                "barren-bombarded",
                360f * (float) Math.random(),
                320f,
                celakaGammaDist,
                1421f);
//        celakaGamma.setCustomDescriptionId("redlegion_celaka_celakaGamma"); //reference descriptions.csv
        PlanetConditionGenerator.generateConditionsForPlanet(celakaGamma, StarAge.AVERAGE);

        // Celaka Prime: Terran homeworld
        PlanetAPI celakaPrime = system.addPlanet("celaka_prime",
                celakaStar,
                "Celaka Prime",
                "terran",
                360f * (float) Math.random(),
                220f,
                celakaPrimeDist,
                320f);

//        celakaPrime.setCustomDescriptionId("redlegion_celaka_celakaprime"); //reference descriptions.csv

        MarketAPI celakaPrime_market = addMarketplace("hegemony", celakaPrime, null,
                "Celaka Prime",
                6,
                Arrays.asList(
                        Conditions.POPULATION_6,
                        Conditions.ORE_RICH,
                        Conditions.RARE_ORE_ABUNDANT,
                        Conditions.FARMLAND_BOUNTIFUL,
                        Conditions.HABITABLE,
                        Conditions.ORGANIZED_CRIME,
                        Conditions.TERRAN,
                        Conditions.REGIONAL_CAPITAL,
                        Conditions.STEALTH_MINEFIELDS,
                        Conditions.AI_CORE_ADMIN
                ),
                Arrays.asList(
                        Submarkets.GENERIC_MILITARY,
                        Submarkets.SUBMARKET_OPEN,
                        Submarkets.SUBMARKET_STORAGE,
                        Submarkets.SUBMARKET_BLACK
                ),
                Arrays.asList(
                        Industries.POPULATION,
                        Industries.MEGAPORT,
                        Industries.MINING,
                        Industries.STARFORTRESS,
                        Industries.HEAVYBATTERIES,
                        Industries.HIGHCOMMAND,
                        Industries.WAYSTATION
                ),
                0.18f,
                true,
                true);
        System.out.println(celakaPrime_market);

        celakaPrime_market.addIndustry(Industries.ORBITALWORKS, Collections.singletonList(Items.PRISTINE_NANOFORGE)); //couldn't find another way to add w/ forge!

        // Blood Keep - the Blood Knight Citadel: Far orbital near the fringe point for garrison, place where strike forces report back and regroup
        SectorEntityToken bloodKeep = system.addCustomEntity("celaka_blood_keep", "Blood Keep", "station_hightech2", "hegemony");
        bloodKeep.setCircularOrbitPointingDown(celakaStar, 0, bloodDist, 4000f);
//        bloodKeep.setCustomDescriptionId("redlegion_celaka_bloodkeep");
        MarketAPI bloodKeep_market = addMarketplace("hegemony", bloodKeep, null,
                "Blood Keep",
                4,
                Arrays.asList(
                        Conditions.POPULATION_4,
                        Conditions.NO_ATMOSPHERE,
                        Conditions.OUTPOST,
                        Conditions.AI_CORE_ADMIN
                ),
                Arrays.asList(
                        Submarkets.GENERIC_MILITARY,
                        Submarkets.SUBMARKET_STORAGE,
                        Submarkets.SUBMARKET_BLACK
                ),
                Arrays.asList(
                        Industries.POPULATION,
                        Industries.SPACEPORT,
                        Industries.BATTLESTATION_HIGH,
                        Industries.HEAVYBATTERIES,
                        Industries.MILITARYBASE,
                        Industries.ORBITALWORKS,
                        Industries.WAYSTATION
                ),
                0.18f,
                false,
                false);

        bloodKeep_market.getIndustry(Industries.MILITARYBASE).setAICoreId(Commodities.BETA_CORE);

        // Manual jump point
        JumpPointAPI jumpPoint3 = Global.getFactory().createJumpPoint(
                "fringe_jump",
                "Fringe System Jump");

        jumpPoint3.setCircularOrbit(system.getEntityById("Celaka"), 2, jumpFringeDist, 4000f);
        jumpPoint3.setStandardWormholeToHyperspaceVisual();

        system.addEntity(jumpPoint3);

        // Helper logic jump points
        system.autogenerateHyperspaceJumpPoints(true, false);

        HyperspaceTerrainPlugin plugin = (HyperspaceTerrainPlugin) Misc.getHyperspaceTerrain().getPlugin();
        NebulaEditor editor = new NebulaEditor(plugin);
        float minRadius = plugin.getTileSize() * 2f;

        float radius = system.getMaxRadiusInHyperspace();
        editor.clearArc(system.getLocation().x, system.getLocation().y, 0, radius + minRadius, 0, 360f);
        editor.clearArc(system.getLocation().x, system.getLocation().y, 0, radius + minRadius, 0, 360f, 0.25f);

    }
}
