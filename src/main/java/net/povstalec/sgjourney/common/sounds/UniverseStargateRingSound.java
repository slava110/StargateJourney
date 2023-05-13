package net.povstalec.sgjourney.common.sounds;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;
import net.povstalec.sgjourney.common.init.SoundInit;

public class UniverseStargateRingSound extends AbstractTickableSoundInstance
{
	private static final float VOLUME_MAX = 1.0F;
	public UniverseStargateRingSound()
	{
		super(SoundInit.UNIVERSE_RING_SPIN.get(), SoundSource.BLOCKS, SoundInstance.createUnseededRandom());
		this.volume = VOLUME_MAX;
	}
	
	@Override
	public boolean isLooping()
	{
		return false;
	}

	@Override
	public void tick()
	{
		this.volume = VOLUME_MAX;
	}
	
	public void stopSound()
	{
		this.stop();
	}
}
