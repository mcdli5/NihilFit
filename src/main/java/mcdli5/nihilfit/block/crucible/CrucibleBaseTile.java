package mcdli5.nihilfit.block.crucible;

import mcdli5.nihilfit.init.ModTiles;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.extensions.IForgeBlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Predicate;

public class CrucibleBaseTile extends TileEntity implements ITickableTileEntity {
    private static final int CAPACITY = 4;
    private final ItemStackHandler itemStackHandler;
    private final MyFluidTank fluidTank;
    private int solidAmount = 0;
    private int amountUsed = 0;
    private int ticksSinceLast = 0;
    private int heatRate = 0;

    public CrucibleBaseTile() {
        super(ModTiles.CRUCIBLE.get());
        itemStackHandler = createItemStackHandler();
        fluidTank = createFluidTank();
    }

    private ItemStackHandler createItemStackHandler() {
        return new ItemStackHandler(1) {
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return stack.isItemEqual(Items.COBBLESTONE.getDefaultInstance());
            }

            @Nonnull
            @Override
            public ItemStack extractItem(int slot, int amount, boolean simulate) {
                return ItemStack.EMPTY;
            }

            @Override
            public int getSlotLimit(int slot) {
                return CAPACITY;
            }

            @Override
            protected void onContentsChanged(final int slot) {
                super.onContentsChanged(slot);
                markDirty();
            }
        };
    }

    private MyFluidTank createFluidTank() {
        int fluidCapacity = CAPACITY * FluidAttributes.BUCKET_VOLUME;
        FluidStack lava = new FluidStack(Fluids.LAVA, 1000);

        return new MyFluidTank(fluidCapacity, lava::isFluidEqual);
    }

    @Override
    public void tick() {
        if (world.isRemote) return;

        if (++ticksSinceLast >= 10) {
            ticksSinceLast = 0;

            heatRate = getHeatRate();
            if (heatRate <= 0) return;

            if (solidAmount <= 0) {
                if (amountUsed >= 250) {
                    itemStackHandler.getStackInSlot(0).shrink(1);
                    amountUsed = 0;
                }
                if (itemStackHandler.getStackInSlot(0).isEmpty()) return;
                solidAmount = 250;
            }

            // Never take more than we have left
            if (heatRate > solidAmount) {
                heatRate = solidAmount;
            }

            if (heatRate > 0) {
                FluidStack toFill = new FluidStack(Fluids.LAVA, heatRate);
                int filled = fluidTank.fillInternal(toFill, IFluidHandler.FluidAction.EXECUTE);
                solidAmount -= filled;
                amountUsed += filled;
            }
        }
    }

    private int getHeatRate() {
        final BlockPos posBelowBLock = pos.add(0, -1, 0);
        final IForgeBlockState stateBelow = getWorld().getBlockState(posBelowBLock);

        // TODO: Base this on a HeatRegistry;
        // TODO: Change back to 3 before next upload to Curse;
        if (stateBelow == Blocks.TORCH.getDefaultState()) return 25;

        return 0;
    }

    @Override
    public void read(CompoundNBT compoundNBT) {
        itemStackHandler.deserializeNBT(compoundNBT.getCompound("inv"));
        fluidTank.readFromNBT(compoundNBT);

        solidAmount = compoundNBT.getInt("solidAmount");
        amountUsed = compoundNBT.getInt("amountUsed");
        heatRate = compoundNBT.getInt("heatRate");

        super.read(compoundNBT);
    }

    @Override
    public CompoundNBT write(CompoundNBT compoundNBT) {
        compoundNBT.put("inv", itemStackHandler.serializeNBT());
        fluidTank.writeToNBT(compoundNBT);

        compoundNBT.putInt("solidAmount", solidAmount);
        compoundNBT.putInt("amountUsed", amountUsed);
        compoundNBT.putInt("heatRate", heatRate);

        return super.write(compoundNBT);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return LazyOptional.of(() -> itemStackHandler).cast();
        }

        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return LazyOptional.of(() -> fluidTank).cast();
        }

        return super.getCapability(cap, side);
    }

    private class MyFluidTank extends FluidTank {
        public MyFluidTank(int capacity, Predicate<FluidStack> validator) {
            super(capacity, validator);
        }

        // No filling from outside
        @Override
        public int fill(FluidStack resource, IFluidHandler.FluidAction action) {
            return 0;
        }

        public int fillInternal(FluidStack resource, IFluidHandler.FluidAction action) {
            return super.fill(resource, action);
        }

        @Override
        protected void onContentsChanged() {
            super.onContentsChanged();
            markDirty();
        }
    }
}
