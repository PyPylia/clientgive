package dev.pypylia.clientgive;

import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.command.argument.ItemStackArgument;
import net.minecraft.command.argument.ItemStackArgumentType;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.CreativeInventoryActionC2SPacket;

public class ClientGiveMod implements ClientModInitializer {

    public static Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "clientgive";
    public static final String MOD_NAME = "clientgive";

    private static final SimpleCommandExceptionType NO_EMPTY_SLOTS_EXCEPTION = new SimpleCommandExceptionType(new LiteralMessage("No empty slots in hotbar."));

    @Override
    public void onInitializeClient() {
        log(Level.INFO, "Initializing...");

        ClientCommandManager.DISPATCHER.register(
            ClientCommandManager.literal("clientgive")
            .requires(source -> source.getPlayer().getAbilities().creativeMode)
            .then(ClientCommandManager.argument("item", ItemStackArgumentType.itemStack())
                .executes(ctx -> command(ctx, 1))
            .then(ClientCommandManager.argument("count", IntegerArgumentType.integer(1, 64))
                .executes(ctx -> command(ctx, ctx.getArgument("count", int.class)))
            .then(ClientCommandManager.argument("slot", IntegerArgumentType.integer(0))
                .executes(ctx -> command(ctx, ctx.getArgument("count", int.class), ctx.getArgument("slot", int.class)))
            )))
        );

        log(Level.INFO, "Initialized.");
    }

    private int command(CommandContext<FabricClientCommandSource> ctx, int count) throws CommandSyntaxException {
        PlayerInventory inventory = ctx.getSource().getPlayer().getInventory();
        for(int i = 0; i < 9; i++) {
            if(!inventory.getStack(i).isEmpty()) continue;

            command(ctx, count, i + 36);
            return 0;
        }
        
        throw NO_EMPTY_SLOTS_EXCEPTION.create();
    }

    private int command(CommandContext<FabricClientCommandSource> ctx, int count, int slot) throws CommandSyntaxException {
        ItemStackArgument item = ctx.getArgument("item", ItemStackArgument.class);

        ItemStack stack = item.createStack(count, false);
        ClientPlayerEntity player = ctx.getSource().getPlayer();

        player.networkHandler.sendPacket(new CreativeInventoryActionC2SPacket(slot, stack));

        return 0;
    }

    public static void log(Level level, String message){
        LOGGER.log(level, "["+MOD_NAME+"] " + message);
    }

}