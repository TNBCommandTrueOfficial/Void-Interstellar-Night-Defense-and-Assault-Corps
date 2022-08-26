package tnb.vndac.world.systems;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.locks.Condition;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.campaign.econ.EconomyAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.impl.campaign.ids.*;
import com.fs.starfarer.api.impl.campaign.procgen.NebulaEditor;
import com.fs.starfarer.api.impl.campaign.procgen.PlanetConditionGenerator;
import com.fs.starfarer.api.impl.campaign.procgen.StarAge;
import com.fs.starfarer.api.impl.campaign.procgen.StarSystemGenerator;
import com.fs.starfarer.api.impl.campaign.terrain.AsteroidFieldTerrainPlugin;
import com.fs.starfarer.api.impl.campaign.terrain.AsteroidFieldTerrainPlugin.AsteroidFieldParams;
import com.fs.starfarer.api.impl.campaign.terrain.HyperspaceTerrainPlugin;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.api.impl.campaign.terrain.MagneticFieldTerrainPlugin.MagneticFieldParams;
import org.lazywizard.lazylib.MathUtils;
import sun.rmi.runtime.Log;
import utils.UtilTools;

import static utils.UtilTools.addMarketplace;

public class CelakaSystem {
    public void generate(SectorAPI sector) {

        StarSystemAPI system = sector.createStarSystem("Celaka");
        // Set the hyperspace location X & Y
        system.getLocation().set(-39000, 39000); //top leftish

        //It's by no means necessary to set a background to your star system.
        //However, let's see how to do it.
        //If you prefer a non-descript star field, simply disable this line.
        system.setBackgroundTextureFilename("graphics/VNC/backgrounds/celaka_background.jpg");

        //setup all distances here
        final float asteroidField1Dist = 2500f;
        final float asteroidBelt1Dist = 2000f;

        //  Star instantiation using the StarSystemAPI
        //  initStar(
        //      id, // Unique id for this star. This is how we retrieve it!
        //      type, // You can refer to com.fs.starfarer.api.impl.campaign.ids.StarTypes or use the
                // actual values found in \Starsector\starsector-core\data\config\planets.json
        //      radius, //The rest are pretty obvious!
        //      hyperspaceLocationX, // Unless specified above
        //      hyperspaceLocationY, // Unless specified above
        //      coronaSize // corona radius, from star edge
        //  );

        // NOTE:    Here we have already set the location up above so here
        //          the hyperspace locations are no longer needed

        PlanetAPI star = system.initStar(
                "celaka",		// id, unique id for this star. This is how we retrieve it!
                StarTypes.RED_GIANT, 	// type, You can refer to com.fs.starfarer.api.impl.campaign.ids.StarTypes or use the actual
                // values found in \Starsector\starsector-core\data\config\planets.json
                1000, 			// radius, //The rest are pretty obvious!
                250); 			// coronaSize); corona radius, from star edge

//        star.setCustomDescriptionId("celaka"); // Custom Description (descriptions.csv)

        ////////////////////////
        // Structure Entities //
        ////////////////////////
        {
            //Buoy
            SectorEntityToken buoy = system.addCustomEntity("celaka_buoy",
                    "Celaka Buoy",
                    "nav_buoy",
                    "neutral");
            buoy.setCircularOrbitPointingDown(star, 40, 7000, 400);

            //Relay
            SectorEntityToken relay = system.addCustomEntity("celaka_relay",
                    "Celaka Relay",
                    "comm_relay",
                    "neutral");
            relay.setCircularOrbitPointingDown(star, 190, 7000, 400);

            //Array
            SectorEntityToken array = system.addCustomEntity("celaka_array",
                    "Celaka Array",
                    "sensor_array",
                    "neutral");
            array.setCircularOrbitPointingDown(star, 280, 7000, 400);

            //Gate
            SectorEntityToken sol_gate = system.addCustomEntity("celaka_gate",
                    "Celaka Gate",
                    "inactive_gate",
                    null);
            sol_gate.setCircularOrbit(star, 120, 7000, 400);
        }

        ///////////////////////////////
        /// Asteroid Fields & Belts ///
        ///////////////////////////////
        {

            SectorEntityToken celakaAF1 = system.addTerrain(Terrain.ASTEROID_FIELD,
                    new AsteroidFieldTerrainPlugin.AsteroidFieldParams(
                            200f, // min radius
                            400f, // max radius
                            9, // min asteroid count
                            20, // max asteroid count
                            4f, // min asteroid radius
                            16f, // max asteroid radius
                            "Asteroids Field")); // null for default name
            celakaAF1.setCircularOrbit(star, 220, asteroidField1Dist, 220);

            //asteroid belt1 ring
            system.addAsteroidBelt(star, 1000, asteroidBelt1Dist, 800, 250, 350, Terrain.ASTEROID_BELT, "Inner Band");
            system.addRingBand(star, "misc", "rings_asteroids0", 256f, 3, Color.red, 256f, asteroidBelt1Dist - 200, 250f);
            system.addRingBand(star, "misc", "rings_asteroids0", 256f, 0, Color.red, 256f, asteroidBelt1Dist, 350f);
            system.addRingBand(star, "misc", "rings_asteroids0", 256f, 2, Color.red, 256f, asteroidBelt1Dist + 200, 400f);

        }
        // Asteroid Belt
        system.addAsteroidBelt(star, 750, 6000, 500, 700, 300, Terrain.ASTEROID_BELT, "Asteroid Belt");
        system.addRingBand(star, "misc", "rings_asteroids0", 256f, 4, Color.white,256f,6000,295f,Terrain.ASTEROID_BELT,"Asteroid Belt1");

        // Asteroid Belt Jump Point
        JumpPointAPI asteroid_belt_jump_point = Global.getFactory().createJumpPoint("celaka_jump1", "Celaka Jump-Point1");
        asteroid_belt_jump_point.setCircularOrbit(system.getEntityById("celaka"), 245, 7000, 200);
        asteroid_belt_jump_point.setStandardWormholeToHyperspaceVisual();
        system.addEntity(asteroid_belt_jump_point);

        // Kuiper Belt
        system.addAsteroidBelt(star, 1000, 20000, 1000, 150, 300, Terrain.ASTEROID_BELT, "Celaka Belt");
        system.addRingBand(star, "misc", "rings_dust0", 256f, 3, Color.white, 256f, 23000, 305f, Terrain.ASTEROID_BELT,"Kuiper Belt1");
        system.addRingBand(star, "misc", "rings_asteroids0", 256f, 3, Color.white,256f,23000,295f,Terrain.ASTEROID_BELT,"Kuiper Belt2");

        //This sets an ambient light color in entire system, affects all entities (planets, stars, etc).
        //It is not required but can be used to make a spooky effect.
        //Other times it makes things look horrible.
        //Let's see how this color 0xCC0080 looks. It's my favorite color!
        //0xCC0080 is a combined color in RGB hexadecimal notation.
        //We need to provide each red, green, and blue value separately so it becomes:
//		system.setLightColor(new Color(0xCC, 0x00, 0x80));


        //Now let's add some random "entities" to the system.
        //The StarSystemGenerator can do this for us and it will add anything you can imagine:
        //accretion disks, more planets, moons, asteroids, etc. You never know!
        //This function returns a number representing the outermost orbit of whatever was just added.
        //This is handy if you want to continue adding stuff to the system in sequential orbits, which we'll do!
        float outermostOrbitDistance = StarSystemGenerator.addOrbitingEntities(
                system,  // Current System from above
                star,  // Current Star from above
                StarAge.AVERAGE, //This setting determines what kind of potential entities are added.
                0, 1, //Min-Max entities to add, here we'll just add 1 entity!
                2500, //Radius to start adding at. Make sure it's greater than your star's actual radius! You can have planets inside a star otherwise (maybe cool???)
                1, //Name offset - next planet will be <system name> <roman numeral of this parameter + 1> if using system-based names.
                false); // whether to use custom or system-name based names

        ////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////// Planet Konta /////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////
        PlanetAPI planetKonta = system.addPlanet(
                "konta",
                star,
                "Konta",
                Planets.PLANET_TERRAN,
                360f * (float) Math.random(),
                229f,
                outermostOrbitDistance + 2000f,
                200f
        );

        planetKonta.setCustomDescriptionId("konta"); // Custom Description (descriptions.csv)

        MarketAPI planetKonta_market = addMarketplace("hegemony", planetKonta, null,
                "Konta",
                7,
                Arrays.asList(
                        Conditions.POPULATION_7,
                        Conditions.RUINS_WIDESPREAD,
                        Conditions.HABITABLE,
                        Conditions.ORE_MODERATE,
                        Conditions.RARE_ORE_MODERATE,
                        Conditions.REGIONAL_CAPITAL,
                        Conditions.FARMLAND_ADEQUATE
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
                        Industries.STARFORTRESS,
                        Industries.HEAVYBATTERIES,
                        Industries.HIGHCOMMAND,
                        Industries.WAYSTATION,
                        Industries.MINING
                ),
                0.15f,
                true,
                true
        );


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
