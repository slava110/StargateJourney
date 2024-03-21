package net.povstalec.sgjourney.mixin.create;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.trains.track.TrackBlock;
import com.simibubi.create.foundation.utility.BlockFace;
import com.simibubi.create.foundation.utility.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.povstalec.sgjourney.StargateJourney;
import net.povstalec.sgjourney.common.block_entities.stargate.AbstractStargateEntity;
import net.povstalec.sgjourney.common.blocks.stargate.AbstractStargateBaseBlock;
import net.povstalec.sgjourney.common.data.StargateNetwork;
import net.povstalec.sgjourney.common.stargate.Connection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = TrackBlock.class, remap = false)
abstract class MixinTrackBlock {

    @ModifyExpressionValue(
            method = "connectToPortal",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/simibubi/create/content/trains/track/AllPortalTracks;isSupportedPortal(Lnet/minecraft/world/level/block/state/BlockState;)Z"
            )
    )
    public boolean connectSupport(
            boolean original,
            @Local(argsOnly = true) ServerLevel level,
            @Local(ordinal = 2) BlockPos portalPos
    ) {
        return original || level.getBlockState(portalPos.below()).getBlock() instanceof AbstractStargateBaseBlock;
    }

    @ModifyExpressionValue(
            method = "connectToPortal",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/simibubi/create/content/trains/track/AllPortalTracks;getOtherSide(Lnet/minecraft/server/level/ServerLevel;Lcom/simibubi/create/foundation/utility/BlockFace;)Lcom/simibubi/create/foundation/utility/Pair;"
            )
    )
    public Pair<ServerLevel, BlockFace> connectOtherSide(
            Pair<ServerLevel, BlockFace> original,
            @Local(argsOnly = true) ServerLevel level,
            @Local(ordinal = 2) BlockPos portalPos,
            @Local Direction d
    ) {
        if(original == null) {
            BlockEntity be = level.getBlockEntity(portalPos.below());
            if(be instanceof AbstractStargateEntity sg && sg.isConnected()) {
                AbstractStargateEntity dialed = null;

                //TODO check if same dimension? Unless Create checks
                for(Connection con : StargateNetwork.get(level).getConnections().values()) {
                    if(con.getDialingStargate() == be) {
                        dialed = con.getDialedStargate();
                        StargateJourney.LOGGER.info("Dialed!");
                        break;
                    } else if(con.getDialedStargate() == be) {
                        dialed = con.getDialingStargate();
                        StargateJourney.LOGGER.info("Dialing!");
                        break;
                    }
                }

                // TODO fuck what if vertical stargate I need to do something
                // It's just placing track above the gate so it's kinda weird xD
                if(dialed != null)
                    return Pair.of((ServerLevel) dialed.getLevel(), new BlockFace(dialed.getBlockPos().above().relative(dialed.getDirection()), dialed.getDirection().getOpposite()));
            }
        }
        return null;
    }


}
