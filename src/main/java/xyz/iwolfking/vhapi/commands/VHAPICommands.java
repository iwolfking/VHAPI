package xyz.iwolfking.vhapi.commands;

import com.mojang.brigadier.CommandDispatcher;
import iskallia.vault.init.ModConfigs;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
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

        // Example command: /greet <name> <times>
        dispatcher.register(
                Commands.literal("vhapi")
                        .executes(context -> {
                            CommandSourceStack source = context.getSource();
                            ModConfigs.CONFIGS.forEach(config -> {
                                try {
                                    config.writeConfig();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                            return 0;
                        }));
        return;
    }
}
