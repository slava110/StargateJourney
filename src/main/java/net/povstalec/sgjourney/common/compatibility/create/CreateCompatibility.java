package net.povstalec.sgjourney.common.compatibility.create;

import com.simibubi.create.AllMovementBehaviours;
import com.simibubi.create.content.contraptions.BlockMovementChecks;
import com.simibubi.create.content.trains.track.AllPortalTracks;
import net.povstalec.sgjourney.common.init.BlockInit;

public class CreateCompatibility {

    public static void registerStargateBehaviour() {
        BlockMovementChecks.registerAllChecks(new StargateMovementChecks());
        AllMovementBehaviours.registerBehaviourProvider(new StargateMovementBehaviour.Provider());

        // TODO Create stargate trains
    }
}
