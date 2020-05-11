package mcdli5.nihilfit.block.crucible;

import mcdli5.nihilfit.init.NF_Tiles;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.model.ModelDataManager;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.client.model.data.ModelProperty;
import net.minecraftforge.common.capabilities.Capability;
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

public final class CrucibleTile extends TileEntity implements ITickableTileEntity {
    private static final int CAPACITY = 4;

    public static final ModelProperty<Integer> LEVEL = new ModelProperty<>();
    public static final ModelProperty<BlockState> CONTENT = new ModelProperty<>();

    private CrucibleRegistry registry;
    private String registryName;
    protected ItemStackHandler itemStackHandler;
    private LazyOptional<ItemStackHandler> itemStackHandlerLazyOptional;
    protected CrucibleFluidTank fluidTank;
    private LazyOptional<CrucibleFluidTank> fluidTankLazyOptional;

    private int level = 0;
    private BlockState content = null;

    private int solidAmount = 0;
    private int amountUsed = 0;
    private int currentItemAmount = 0;
    private int ticksSinceLast = 0;

    public CrucibleTile() {
        super(NF_Tiles.CRUCIBLE.get());
    }

    public CrucibleTile(CrucibleRegistry registry) {
        super(NF_Tiles.CRUCIBLE.get());
        this.registry = registry;
        this.registryName = registry.getName();
        createItemStackHandler();
        createFluidTank();
    }

    private void createItemStackHandler() {
        itemStackHandler = new ItemStackHandler(1) {
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return registry.isItemValid(stack);
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

        itemStackHandlerLazyOptional = LazyOptional.of(() -> itemStackHandler);
    }

    private void createFluidTank() {
        int fluidCapacity = CAPACITY * FluidAttributes.BUCKET_VOLUME;
        fluidTank = new CrucibleFluidTank(fluidCapacity, registry.VALID_FLUID::isFluidEqual);
        fluidTankLazyOptional = LazyOptional.of(() -> fluidTank);
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
                if (amountUsed >= currentItemAmount && currentItemAmount != 0) {
                    itemStackHandler.getStackInSlot(0).shrink(1);
                    amountUsed = 0;
                }

                if (itemStackHandler.getStackInSlot(0).isEmpty()) {
                    setContentLevel();
                    return;
                } else {
                    Item currentItem = itemStackHandler.getStackInSlot(0).getItem();
                    currentItemAmount = solidAmount = registry.getMeltable(currentItem).getAmount();
                }
            }

            // Never take more than we have left
            if (heatRate > solidAmount) {
                heatRate = solidAmount;
            }

            FluidStack toFill = new FluidStack(registry.VALID_FLUID.getFluid(), heatRate);
            int filled = fluidTank.fillInternal(toFill, IFluidHandler.FluidAction.EXECUTE);
            solidAmount -= filled;
            amountUsed += filled;

            setContentLevel();
        }
    }

    protected int getHeatRate() {
        final BlockPos posBelowBLock = pos.add(0, -1, 0);
        final BlockState blockState = world.getBlockState(posBelowBLock);
        final Block blockBelow = blockState.getBlock();

        return registry.getHeatAmount(blockBelow);
    }

    private void setContentLevel() {
        float items = itemStackHandler.getStackInSlot(0).getCount() * 3.0f;
        float fluid = fluidTank.getFluidAmount() / (1000 / 3.0f);

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

        world.setBlockState(pos, this.getBlockState().with(CrucibleBlock.LIGHT_LEVEL, getLightLevel()));
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
        registryName = compoundNBT.getString("registryName");
        if (registry == null) {
            try {
                registry = (CrucibleRegistry) CrucibleRegistry.class.getField(registryName).get(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (itemStackHandler == null) createItemStackHandler();
        itemStackHandler.deserializeNBT(compoundNBT.getCompound("inv"));

        if (fluidTank == null) createFluidTank();
        fluidTank.readFromNBT(compoundNBT);

        content = NBTUtil.readBlockState(compoundNBT.getCompound("content"));

        level = compoundNBT.getInt("level");
        solidAmount = compoundNBT.getInt("solidAmount");
        currentItemAmount = compoundNBT.getInt("currentItemAmount");
        amountUsed = compoundNBT.getInt("amountUsed");

        super.read(compoundNBT);
    }

    @Override
    public CompoundNBT write(CompoundNBT compoundNBT) {
        compoundNBT.putString("registryName", registryName);

        compoundNBT.put("inv", itemStackHandler.serializeNBT());
        fluidTank.writeToNBT(compoundNBT);

        if (content != null) compoundNBT.put("content", NBTUtil.writeBlockState(content));

        compoundNBT.putInt("level", level);
        compoundNBT.putInt("solidAmount", solidAmount);
        compoundNBT.putInt("currentItemAmount", currentItemAmount);
        compoundNBT.putInt("amountUsed", amountUsed);

        return super.write(compoundNBT);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return itemStackHandlerLazyOptional.cast();
        }

        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return fluidTankLazyOptional.cast();
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
