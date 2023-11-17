package net.povstalec.sgjourney.common.items;

import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.Nullable;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.povstalec.sgjourney.common.blocks.stargate.AbstractStargateBlock;

public class StargateVariantItem extends Item
{
	public static final String VARIANT = "Variant";
	
	public StargateVariantItem(Properties properties)
	{
		super(properties);
	}

	public static Optional<String> getVariantString(ItemStack stack)
	{
		Optional<String> variant = Optional.empty();
		
		if(stack.getItem() instanceof StargateVariantItem)
		{
			if(stack.getOrCreateTag().contains(VARIANT))
			{
				String variantString = stack.getTag().getString(VARIANT);
				variant = Optional.of(variantString);
			}
		}
		
		return variant;
	}

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag isAdvanced)
    {
        if(stack.hasTag())
        {
            if(stack.getOrCreateTag().contains(VARIANT))
            {
            	String variant = stack.getOrCreateTag().getString(VARIANT);
            	
				tooltipComponents.add(Component.translatable("tooltip.sgjourney.variant").append(Component.literal(": " + variant)).withStyle(ChatFormatting.GREEN));
            }
        }

        super.appendHoverText(stack, level, tooltipComponents, isAdvanced);
    }
	
	public static <StargateBlock extends AbstractStargateBlock> ItemStack stargateVariant(StargateVariantItem item, String variant)
	{
		ItemStack stack = new ItemStack(item);
        CompoundTag compoundtag = new CompoundTag();
        compoundtag.putString(VARIANT, variant);
		stack.setTag(compoundtag);
		
		return stack;
	}
}
