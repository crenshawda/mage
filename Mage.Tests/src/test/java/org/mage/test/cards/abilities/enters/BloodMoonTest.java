/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mage.test.cards.abilities.enters;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class BloodMoonTest extends CardTestPlayerBase {

    // 614.12. Some replacement effects modify how a permanent enters the battlefield. (See rules 614.1cd.)
    // Such effects may come from the permanent itself if they affect only that permanent (as opposed to a
    // general subset of permanents that includes it). They may also come from other sources. To determine
    // which replacement effects apply and how they apply, check the characteristics of the permanent as it
    // would exist on the battlefield, taking into account replacement effects that have already modified how
    // it enters the battlefield (see rule 616.1), continuous effects generated by the resolution of spells
    // or abilities that changed the permanents characteristics on the stack (see rule 400.7a), and continuous
    // effects from the permanents own static abilities, but ignoring continuous effects from any other source
    // that would affect it.

    // Grassland has to enter the battlefield tapped, because
    // the Blood Moon does not prevent ETB Replacement Effects
    @Test
    public void testBloodMoonDoesNotPreventETBReplacementEffects() {
        // Blood Moon   2R
        // Enchantment
        // Nonbasic lands are Mountains
        addCard(Zone.BATTLEFIELD, playerA, "Blood Moon");

        /**
         * Grasslands 
         * Land
         * Grasslands enters the battlefield tapped.
         * {T}, Sacrifice Grasslands: Search your library for a Forest or Plains card and put it onto the battlefield. Then shuffle your library.
         */
        addCard(Zone.HAND, playerA, "Grasslands");

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grasslands");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        // Check that the land is tapped
        Permanent grassland = getPermanent("Grasslands", playerA.getId());
        Assert.assertEquals("Grasslands is not tapped but is has to be from ETB replacement effect", true, grassland.isTapped());

    }

    @Test
    public void testBloodMoonDoesNotPreventETBReplacementButPreventsTriggeredEffects() {
        // Blood Moon   2R
        // Enchantment
        // Nonbasic lands are Mountains
        addCard(Zone.BATTLEFIELD, playerA, "Blood Moon");

        /**
         * Kabira Crossroads
         * Land
         * Kabira Crossroads enters the battlefield tapped.
         * When Kabira Crossroads enters the battlefield, you gain 2 life.
         * {W}: Add to your mana pool.
         *
         */
        addCard(Zone.HAND, playerA, "Kabira Crossroads");

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kabira Crossroads");
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // Check that the land is tapped
        Permanent grassland = getPermanent("Kabira Crossroads", playerA.getId());
        Assert.assertEquals("Kabira Crossroads is not tapped but is has to be from ETB replacement effect", true, grassland.isTapped());

        assertLife(playerA, 20); // Trigger may not trigger because of Blood Moon so the 2 life were not added
        assertLife(playerB, 20);


    }
}
