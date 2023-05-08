package net.povstalec.sgjourney.common.items;

import java.util.List;
import java.util.function.Consumer;

import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.povstalec.sgjourney.common.misc.AncientTech;

public class PersonalShieldItem extends ArmorItem implements AncientTech
{
	public PersonalShieldItem(ArmorMaterial material, EquipmentSlot slot, Properties properties)
	{
		super(material, slot, properties);
	}
	
	@Override
    public void onArmorTick(ItemStack stack, Level level, Player player)
    {
		if(canUseAncientTech(player))//TODO Add config
		{
			AABB vectorShield = player.getBoundingBox().inflate(10.0);
			
			List<Projectile> projectiles = level.getEntitiesOfClass(Projectile.class, vectorShield);
			doVectorShield(player, projectiles);
			
			List<FallingBlockEntity> fallingBlocks = level.getEntitiesOfClass(FallingBlockEntity.class, vectorShield);
			doVectorShield(player, fallingBlocks);
		}
    }
	
	@Override
	public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken)
	{
		return 0;
	}
	
	private void doVectorShield(Entity esper, List<? extends Entity> entities)
	{
		entities.stream().forEach(entity ->
		{
			if(!entity.equals(esper))
			{
				Vec3 vector = entity.getDeltaMovement();
				if(isVectorHarmful(esper, entity, vector))
				{
					vector = vector.reverse();
					entity.setDeltaMovement(vector);
					if(esper instanceof ServerPlayer player)
						player.connection.send(new ClientboundSetEntityMotionPacket(entity));
				}
			}
		});
	}
	
	private boolean isVectorHarmful(Entity esper, Entity attacker, Vec3 vector)
	{
		if(esper != null && attacker != null)
		{
			Vec3 esperPos = esper.getBoundingBox().getCenter();
			Vec3 attackerPos = attacker.getBoundingBox().getCenter();
			
			return willCollide(esperPos, attackerPos, vector);
		}
		
		return false;
	}
	
	private boolean willCollide(Vec3 esperPos, Vec3 attackerPos, Vec3 vector)
	{
		if(isVectorPointingTowardsEsper(esperPos, attackerPos, vector))
		{
			Vec3 distanceVector = attackerPos.vectorTo(esperPos);
			
			double distance = distanceVector.length();

			Vec3 vectorShieldStart = distanceVector.scale((distance - 1.1) / distance);
			
			return vector.length() >= vectorShieldStart.length();
		}
			
		return false;
	}
	
	private boolean isVectorPointingTowardsEsper(Vec3 esperPos, Vec3 attackerPos, Vec3 vector)
	{
		return attackerPos.vectorTo(esperPos).normalize().dot(vector) > 0;
	}
}
