package xyz.iwolfking.vhapi.commands;

import com.mojang.brigadier.CommandDispatcher;
import iskallia.vault.init.ModConfigs;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.iwolfking.vhapi.VHAPI;

import java.io.IOException;

@Mod.EventBusSubscriber(modid = VHAPI.MODID)
public class VHAPICommands {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

//        dispatcher.register(
//                Commands.literal("vhapi_save")
//                        .executes(context -> {
//                            try {
//                                ServerPlayer player = context.getSource().getPlayerOrException();
//
//                                if(!player.hasPermissions(4)) {
//                                    player.displayClientMessage(new TextComponent("You cannot use this command."), false);
//                                }
//
//                                ModConfigs.MOD_BOX.writeConfig();
//                            } catch (Exception e) {
//                                System.out.println(e.toString());
//                            }
//                            return 0;
//                        }));
    }
}
