package net.povstalec.sgjourney.common.compatibility.create;

import com.simibubi.create.content.contraptions.BlockMovementChecks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.povstalec.sgjourney.common.block_entities.stargate.AbstractStargateEntity;
import net.povstalec.sgjourney.common.blocks.stargate.AbstractStargateBaseBlock;
import net.povstalec.sgjourney.common.blocks.stargate.AbstractStargateBlock;

public class StargateMovementChecks implements BlockMovementChecks.AllChecks {

    @Override
    public BlockMovementChecks.CheckResult isBlockAttachedTowards(BlockState state, Level level, BlockPos pos, Direction dir) {
        if(!(state.getBlock() instanceof AbstractStargateBlock))
            return BlockMovementChecks.CheckResult.PASS;

        BlockPos relPos = pos.relative(dir);
        BlockState relState = level.getBlockState(relPos);

        if(!(relState.getBlock() instanceof AbstractStargateBlock))
            return BlockMovementChecks.CheckResult.PASS;

        AbstractStargateEntity sg = ((AbstractStargateBlock) state.getBlock()).getStargate(level, pos, state);
        AbstractStargateEntity relSg = ((AbstractStargateBlock) relState.getBlock()).getStargate(level, relPos, relState);

        if(sg == relSg)
            return BlockMovementChecks.CheckResult.SUCCESS;

        return BlockMovementChecks.CheckResult.FAIL;
    }

    @Override
    public BlockMovementChecks.CheckResult isBrittle(BlockState state) {
            /*if(state.getBlock() instanceof AbstractStargateRingBlock)
                return BlockMovementChecks.CheckResult.SUCCESS;*/
        /*if(state.getBlock() instanceof AbstractStargateBaseBlock)
            return BlockMovementChecks.CheckResult.SUCCESS;*/
        return BlockMovementChecks.CheckResult.PASS;
    }

    @Override
    public BlockMovementChecks.CheckResult isMovementAllowed(BlockState state, Level level, BlockPos pos) {
        if(state.getBlock() instanceof AbstractStargateBlock)
            return BlockMovementChecks.CheckResult.SUCCESS;
        return BlockMovementChecks.CheckResult.PASS;
    }

    @Override
    public BlockMovementChecks.CheckResult isMovementNecessary(BlockState state, Level level, BlockPos pos) {
            /*if(state.getBlock() instanceof AbstractStargateRingBlock)
                return BlockMovementChecks.CheckResult.SUCCESS;*/
        return BlockMovementChecks.CheckResult.PASS;
    }

    @Override
    public BlockMovementChecks.CheckResult isNotSupportive(BlockState state, Direction direction) {
        return BlockMovementChecks.CheckResult.PASS;
    }
}
