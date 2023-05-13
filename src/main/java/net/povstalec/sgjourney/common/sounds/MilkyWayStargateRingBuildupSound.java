package net.povstalec.sgjourney.common.sounds;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;
import net.povstalec.sgjourney.common.block_entities.stargate.MilkyWayStargateEntity;
import net.povstalec.sgjourney.common.init.SoundInit;

public class MilkyWayStargateRingBuildupSound extends AbstractTickableSoundInstance
{
	private static final float VOLUME_MIN = 0.0F;
	private static final float VOLUME_MAX = 0.5F;
	
	MilkyWayStargateEntity stargate;
	
	public MilkyWayStargateRingBuildupSound(MilkyWayStargateEntity stargate)
	{
		super(SoundInit.MILKY_WAY_RING_SPIN_START.get(), SoundSource.BLOCKS, SoundInstance.createUnseededRandom());
		this.stargate = stargate;
        this.volume = VOLUME_MIN;
	}
	
	@Override
	public boolean isLooping()
	{
		return false;
	}

	@Override
	public void tick()
	{
		if(stargate.isRotating())
			fadeIn();
		else
			fadeOut();
	}
	
	@Override
	public boolean canStartSilent()
	{
		return true;
	}
	
	private void fadeIn()
	{
		if(this.volume < VOLUME_MAX)
			this.volume += 0.1F;
	}
	
	private void fadeOut()
	{
		if(this.volume > VOLUME_MIN)
			this.volume -= 0.1F;
	}
	
	public void stopSound()
	{
		this.stop();
	}
	
}
