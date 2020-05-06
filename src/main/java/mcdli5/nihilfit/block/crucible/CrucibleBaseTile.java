package mcdli5.nihilfit.block.crucible;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.model.ModelDataManager;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.client.model.data.ModelProperty;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.extensions.IForgeBlockState;
import net.minecraftforge.common.util.Constants;
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

import static java.lang.Math.round;

public abstract class CrucibleBaseTile extends TileEntity implements ITickableTileEntity {
    private static final int CAPACITY = 4;

    public static final ModelProperty<Integer> LEVEL = new ModelProperty<>();
    public static final ModelProperty<BlockState> CONTENT = new ModelProperty<>();

    protected final ItemStack validItemStack;
    protected final FluidStack validFluidStack;
    protected final ItemStackHandler itemStackHandler;
    protected final CrucibleFluidTank fluidTank;

    private int level = 0;
    private BlockState content = null;

    private int solidAmount = 0;
    private int amountUsed = 0;
    private int ticksSinceLast = 0;

    public CrucibleBaseTile(TileEntityType<?> tileEntityTypeIn, ItemStack validItemStack, FluidStack validFluidStack) {
        super(tileEntityTypeIn);
        this.validItemStack = validItemStack;
        this.validFluidStack = validFluidStack;
        itemStackHandler = createItemStackHandler();
        fluidTank = createFluidTank();
    }

    private ItemStackHandler createItemStackHandler() {
        return new ItemStackHandler(1) {
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return stack.isItemEqual(validItemStack);
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

    private CrucibleFluidTank createFluidTank() {
        int fluidCapacity = CAPACITY * FluidAttributes.BUCKET_VOLUME;

        return new CrucibleFluidTank(fluidCapacity, validFluidStack::isFluidEqual);
    }

    @Override
    public void tick() {
        if (world.isRemote) return;

        if (++ticksSinceLast >= 10) {
            ticksSinceLast = 0;

            int heatRate = getHeatRate();
            if (heatRate <= 0) {
                setContentLevel();
                return;
            }

            if (solidAmount <= 0) {
                if (amountUsed >= 250) {
                    itemStackHandler.getStackInSlot(0).shrink(1);
                    amountUsed = 0;
                }

                if (itemStackHandler.getStackInSlot(0).isEmpty()) {
                    setContentLevel();
                    return;
                }

                solidAmount = 250;
            }

            // Never take more than we have left
            if (heatRate > solidAmount) {
                heatRate = solidAmount;
            }

            FluidStack toFill = new FluidStack(validFluidStack.getFluid(), heatRate);
            int filled = fluidTank.fillInternal(toFill, IFluidHandler.FluidAction.EXECUTE);
            solidAmount -= filled;
            amountUsed += filled;

            setContentLevel();
        }
    }

    protected IForgeBlockState getStateBelow() {
        final BlockPos posBelowBLock = pos.add(0, -1, 0);
        return world.getBlockState(posBelowBLock);
    }

    protected abstract int getHeatRate();

    private void setContentLevel() {
        float items = itemStackHandler.getStackInSlot(0).getCount() * 3.0f;
        float fluid = fluidTank.getFluidAmount() / (1000/3.0f);

        boolean is_fluid = (fluid >= items);
        int roundedResult = MathHelper.clamp(round(Math.max(items, fluid)), 1, 12);
        level = (items == 0 && fluid == 0) ? 0 : roundedResult;

        if (level > 0) {
            if (is_fluid) {
                content = fluidTank.getFluidInTank(0).getFluid().getDefaultState().getBlockState();
            } else {
                content = Block.getBlockFromItem(itemStackHandler.getStackInSlot(0).getItem()).getDefaultState();
            }
        }

        world.setBlockState(pos, this.getBlockState().with(CrucibleBaseBlock.LIGHT_LEVEL, getLightLevel()));
        markDirty();
        world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), Constants.BlockFlags.BLOCK_UPDATE + Constants.BlockFlags.NOTIFY_NEIGHBORS);
    }

    private int getLightLevel() {
        FluidStack fluidStack = fluidTank.getFluid();

        if (!fluidStack.isEmpty()) {
            FluidAttributes fluidAttributes = fluidStack.getFluid().getAttributes();
            return Math.max(round((fluidStack.getAmount() / 4000.0f) * fluidAttributes.getLuminosity(fluidStack)), 1);
        } else {
            return 0;
        }
    }

    @Nonnull
    @Override
    public IModelData getModelData() {
        return new ModelDataMap.Builder()
            .withInitial(CONTENT, content)
            .withInitial(LEVEL, level)
            .build();
    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT compoundNBT = super.getUpdateTag();

        if (content != null) compoundNBT.put("content", NBTUtil.writeBlockState(content));
        compoundNBT.putInt("level", level);

        return compoundNBT;
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(pos, 1, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        CompoundNBT compoundNBT = pkt.getNbtCompound();

        if (compoundNBT.contains("content")) {
            int oldLevel = level;
            BlockState oldContent = content;

            content = NBTUtil.readBlockState(compoundNBT.getCompound("content"));
            level = compoundNBT.getInt("level");

            if (oldLevel != level || (content != null && oldContent != content)) {
                ModelDataManager.requestModelDataRefresh(this);
                world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), Constants.BlockFlags.BLOCK_UPDATE + Constants.BlockFlags.NOTIFY_NEIGHBORS);
            }
        }
    }

    @Override
    public void read(CompoundNBT compoundNBT) {
        itemStackHandler.deserializeNBT(compoundNBT.getCompound("inv"));
        fluidTank.readFromNBT(compoundNBT);

        content = NBTUtil.readBlockState(compoundNBT.getCompound("content"));

        level = compoundNBT.getInt("level");
        solidAmount = compoundNBT.getInt("solidAmount");
        amountUsed = compoundNBT.getInt("amountUsed");

        super.read(compoundNBT);
    }

    @Override
    public CompoundNBT write(CompoundNBT compoundNBT) {
        compoundNBT.put("inv", itemStackHandler.serializeNBT());
        fluidTank.writeToNBT(compoundNBT);

        if (content != null) compoundNBT.put("content", NBTUtil.writeBlockState(content));

        compoundNBT.putInt("level", level);
        compoundNBT.putInt("solidAmount", solidAmount);
        compoundNBT.putInt("amountUsed", amountUsed);

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

    class CrucibleFluidTank extends FluidTank {
        public CrucibleFluidTank(int capacity, Predicate<FluidStack> validator) {
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
