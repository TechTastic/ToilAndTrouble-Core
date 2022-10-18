package net.techtastic.tatcore.block.entity;

import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.techtastic.tatcore.recipe.CastIronOvenRecipe;
import net.techtastic.tatcore.screen.CastIronOvenScreen;
import net.techtastic.tatcore.screen.CastIronOvenScreenHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Random;

public class CastIronOvenBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory =
            DefaultedList.ofSize(5, ItemStack.EMPTY);

    protected final PropertyDelegate propertyDelegate;
    private int progress = 0;
    private int maxProgress = 200;
    private int fuelTime = 0;
    private int maxFuelTime = 0;

    public CastIronOvenBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CAST_IRON_OVEN_BLOCK_ENTITY, pos, state);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                switch (index) {
                    case 0:
                        return CastIronOvenBlockEntity.this.progress;
                    case 1:
                        return CastIronOvenBlockEntity.this.maxProgress;
                    case 2:
                        return CastIronOvenBlockEntity.this.fuelTime;
                    case 3:
                        return CastIronOvenBlockEntity.this.maxFuelTime;
                    default:
                        return 0;
                }
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0:
                        CastIronOvenBlockEntity.this.progress = value; break;
                    case 1:
                        CastIronOvenBlockEntity.this.maxProgress = value; break;
                    case 2:
                        CastIronOvenBlockEntity.this.fuelTime = value; break;
                    case 3:
                        CastIronOvenBlockEntity.this.maxFuelTime = value; break;
                }
            }

            @Override
            public int size() {
                return 4;
            }
        };
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("block.tatcore.cast_iron_oven");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new CastIronOvenScreenHandler(syncId, inv, this, this.propertyDelegate);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.inventory;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
        nbt.putInt("TATCore$CIOprogress", progress);
        nbt.putInt("TATCore$CIOfuelTime", fuelTime);
        nbt.putInt("TATCore$CIOmaxFuelTime", maxFuelTime);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        Inventories.readNbt(nbt, inventory);
        super.readNbt(nbt);
        progress = nbt.getInt("TATCore$CIOprogress");
        fuelTime = nbt.getInt("TATCore$CIOfuelTime");
        maxFuelTime = nbt.getInt("TATCore$CIOmaxFuelTime");
    }

    public static void tick(World world, BlockPos pos, BlockState state, CastIronOvenBlockEntity entity) {
        if (world.isClient()) {
            return;
        }

        if(entity.isConsumingFuel(entity)) {
            entity.fuelTime--;
        }

        if (hasRecipe(entity)) {
            if(hasFuelInFuelSlot(entity) && !entity.isConsumingFuel(entity)) {
                entity.consumeFuel();
            }
            if(entity.isConsumingFuel(entity)) {
                entity.progress++;
                if(entity.progress > entity.maxProgress) {
                    craftItem(entity);
                }
            }
        } else {
            entity.resetProgress();
        }

        markDirty(world, pos, state);
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private void consumeFuel() {
        if(!getStack(0).isEmpty()) {
            this.fuelTime = FuelRegistry.INSTANCE.get(this.removeStack(1, 1).getItem());
            this.maxFuelTime = this.fuelTime;
        }
    }

    private static boolean hasFuelInFuelSlot(CastIronOvenBlockEntity entity) {
        return !entity.getStack(1).isEmpty();
    }

    public boolean isConsumingFuel(CastIronOvenBlockEntity entity) {
        return entity.fuelTime > 0;
    }

    private static boolean hasRecipe(CastIronOvenBlockEntity entity) {
        SimpleInventory inventory = new SimpleInventory(entity.size());
        for (int i = 0; i < entity.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }

        Optional<CastIronOvenRecipe> match = entity.getWorld().getRecipeManager()
                .getFirstMatch(CastIronOvenRecipe.Type.INSTANCE, inventory, entity.getWorld());

        return match.isPresent() && canInsertAmountIntoOutputSlot(inventory, 1, 2) &&
                canInsertItemIntoOutputSlot(inventory, match.get().getOutput(), 2);
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleInventory inventory, ItemStack stack, int slot) {
        return inventory.getStack(slot).isEmpty() || inventory.getStack(slot).getItem().equals(stack.getItem());
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleInventory inventory, int count, int slot) {
        return inventory.getStack(slot).getMaxCount() >= inventory.getStack(slot).getCount() + count;
    }

    private static void craftItem(CastIronOvenBlockEntity entity) {
        SimpleInventory inventory = new SimpleInventory(entity.size());
        for (int i = 0; i < entity.size(); i++) {
            inventory.setStack(i, entity.getStack(i));
        }

        if (hasRecipe(entity)) {

            Optional<CastIronOvenRecipe> recipe = entity.getWorld().getRecipeManager()
                    .getFirstMatch(CastIronOvenRecipe.Type.INSTANCE, inventory, entity.getWorld());

            ItemStack output = recipe.get().getOutput();
            ItemStack secondaryOutput = recipe.get().getSecondaryOutput();

            entity.removeStack(0, 1);
            if (entity.getStack(2).isEmpty()) {
                entity.setStack(2, output);
            } else {
                entity.getStack(2).increment(1);
            }

            if (hasJar(inventory)) {
                float chance = jarChance(entity);
                float ran = new Random().nextFloat();
                if (ran < chance) {
                    if (canInsertItemIntoOutputSlot(inventory, secondaryOutput, 4)
                            && canInsertAmountIntoOutputSlot(inventory, 1, 4)) {
                        entity.removeStack(3, 1);
                        if (entity.getStack(4).isEmpty()) {
                            entity.setStack(4, secondaryOutput);
                        } else {
                            entity.getStack(4).increment(1);
                        }
                    }
                }
            }

            entity.resetProgress();
        }
    }

    private static float jarChance(CastIronOvenBlockEntity entity) {
        World world = entity.getWorld();
        BlockPos pos = entity.getPos();
        BlockState state = world.getBlockState(pos);
        // GET FACING
        // FACING NORTH/SOUTH, TEST FOR EAST/WEST FOR OvenFilter INSTANCE
        // FACING EAST/WEST, TEST FOR NORTH/SOUTH FOR OvenFilter INSTANCE

        return 0.1f;
    }

    private static boolean hasJar(SimpleInventory inventory) {
        return !inventory.getStack(3).equals(ItemStack.EMPTY);
    }
}
