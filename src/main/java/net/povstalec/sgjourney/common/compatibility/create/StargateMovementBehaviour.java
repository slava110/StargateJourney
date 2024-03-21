package net.povstalec.sgjourney.common.compatibility.create;

import com.simibubi.create.AllMovementBehaviours;
import com.simibubi.create.content.contraptions.behaviour.MovementBehaviour;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import net.minecraft.world.level.block.state.BlockState;
import net.povstalec.sgjourney.common.blocks.stargate.AbstractStargateBaseBlock;
import org.jetbrains.annotations.Nullable;

public class StargateMovementBehaviour implements MovementBehaviour {

    @Override
    public void startMoving(MovementContext ctx) {

    }

    @Override
    public void stopMoving(MovementContext ctx) {

    }

    @Override
    public void tick(MovementContext ctx) {
        //TODO it resests the stargate?
        /*if(!ctx.blockEntityData.contains("AddToNetwork") || ctx.blockEntityData.getBoolean("AddToNetwork")) {
            ctx.blockEntityData.putBoolean("AddToNetwork", false);
        }*/
    }

    @Override
    public void writeExtraData(MovementContext ctx) {

    }

    @Override
    public boolean renderAsNormalBlockEntity() {
        return true;
    }


    public static class Provider implements AllMovementBehaviours.BehaviourProvider {

        @Nullable
        @Override
        public MovementBehaviour getBehaviour(BlockState state) {
            if(state.getBlock() instanceof AbstractStargateBaseBlock)
                return new StargateMovementBehaviour();
            return null;
        }
    }
}
