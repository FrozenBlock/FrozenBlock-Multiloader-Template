package net.frozenblock.ml_template.fabric;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.frozenblock.ml_template.FrozenBlockMLTemplate;
import net.frozenblock.ml_template.debug.item.FrozenLibDevItems;
import net.frozenblock.ml_template.event.event.RegisterCommandEvents;
import net.frozenblock.ml_template.event.event.ServerLevelEvents;
import net.frozenblock.ml_template.platform.api.PlatformHelper;
import org.quiltmc.qsl.frozenblock.fabric.core.registry.api.sync.ModProtocol;
import org.quiltmc.qsl.frozenblock.fabric.core.registry.impl.sync.server.ServerRegistrySync;
import org.quiltmc.qsl.frozenblock.misc.datafixerupper.impl.ServerFreezer;

public class FrozenBlockMultiloaderTemplateFabric implements ModInitializer, DedicatedServerModInitializer {

	@Override
	public void onInitializeServer() {
		FrozenBlockMLTemplate.onInitializeServer();
	}

	@Override
	public void onInitialize() {
		FrozenBlockMLTemplate.onInitialize();

		// QUILT INIT

		ServerFreezer.onInitialize();
		ModProtocol.loadVersions();
		ServerRegistrySync.registerHandlers();

		if (PlatformHelper.isDevelopmentEnvironment()) {
			FrozenLibDevItems.register();
		}

		// Redirect Fabric -> FrozenLib events
		CommandRegistrationCallback.EVENT.register(
			(dispatcher, registryAccess, environment) -> RegisterCommandEvents.REGISTER.invoke(e -> e.register(dispatcher, registryAccess, environment))
		);

		ServerLifecycleEvents.SERVER_STARTING.register(
			server -> net.frozenblock.ml_template.event.event.ServerLifecycleEvents.SERVER_STARTING.invoke(e -> e.onServerStarting(server))
		);
		ServerLifecycleEvents.SERVER_STARTED.register(
			server -> net.frozenblock.ml_template.event.event.ServerLifecycleEvents.SERVER_STARTED.invoke(e -> e.onServerStarted(server))
		);
		ServerLifecycleEvents.SERVER_STOPPING.register(
			server -> net.frozenblock.ml_template.event.event.ServerLifecycleEvents.SERVER_STOPPING.invoke(e -> e.onServerStopping(server))
		);
		ServerLifecycleEvents.SERVER_STOPPED.register(
			server -> net.frozenblock.ml_template.event.event.ServerLifecycleEvents.SERVER_STOPPED.invoke(e -> e.onServerStopped(server))
		);
		ServerLifecycleEvents.SYNC_DATA_PACK_CONTENTS.register(
			(player, joined) -> net.frozenblock.ml_template.event.event.ServerLifecycleEvents.SYNC_DATA_PACK_CONTENTS.invoke(event -> event.onSyncDataPackContents(player, joined))
		);
		ServerLifecycleEvents.START_DATA_PACK_RELOAD.register(
			(server, manager) -> net.frozenblock.ml_template.event.event.ServerLifecycleEvents.START_DATA_PACK_RELOAD.invoke(event -> event.startDataPackReload(server, manager))
		);
		ServerLifecycleEvents.END_DATA_PACK_RELOAD.register(
			(server, manager, success) -> net.frozenblock.ml_template.event.event.ServerLifecycleEvents.END_DATA_PACK_RELOAD.invoke(event -> event.endDataPackReload(server, manager, success))
		);
		ServerLifecycleEvents.BEFORE_SAVE.register(
			(server, flush, force) -> net.frozenblock.ml_template.event.event.ServerLifecycleEvents.BEFORE_SAVE.invoke(e -> e.onBeforeSave(server, flush, force))
		);
		ServerLifecycleEvents.AFTER_SAVE.register(
			(server, flush, force) -> net.frozenblock.ml_template.event.event.ServerLifecycleEvents.AFTER_SAVE.invoke(e -> e.onAfterSave(server, flush, force))
		);

		ServerWorldEvents.LOAD.register(
			(minecraftServer, serverLevel) -> ServerLevelEvents.LOAD.invoke(e -> e.onWorldLoad(minecraftServer, serverLevel))
		);
		ServerWorldEvents.UNLOAD.register(
			(minecraftServer, serverLevel) -> ServerLevelEvents.UNLOAD.invoke(e -> e.onWorldUnload(minecraftServer, serverLevel))
		);

		ServerTickEvents.START_SERVER_TICK.register(
			server -> net.frozenblock.ml_template.event.event.ServerTickEvents.START_SERVER_TICK.invoke(e -> e.onStartTick(server))
		);
		ServerTickEvents.END_SERVER_TICK.register(
			server -> net.frozenblock.ml_template.event.event.ServerTickEvents.END_SERVER_TICK.invoke(e -> e.onEndTick(server))
		);
		ServerTickEvents.START_WORLD_TICK.register(
			serverLevel -> net.frozenblock.ml_template.event.event.ServerTickEvents.START_LEVEL_TICK.invoke(e -> e.onStartTick(serverLevel))
		);
		ServerTickEvents.END_WORLD_TICK.register(
			serverLevel -> net.frozenblock.ml_template.event.event.ServerTickEvents.END_LEVEL_TICK.invoke(e -> e.onEndTick(serverLevel))
		);

		ServerPlayConnectionEvents.JOIN.register(
			(handler, sender, server) -> net.frozenblock.ml_template.event.event.ServerPlayConnectionEvents.JOIN.invoke(e -> e.onPlayReady(handler.getPlayer()))
		);
		ServerPlayConnectionEvents.DISCONNECT.register(
			(serverGamePacketListener, server) -> net.frozenblock.ml_template.event.event.ServerPlayConnectionEvents.DISCONNECT.invoke(e -> e.onPlayDisconnect(serverGamePacketListener.getPlayer()))
		);
	}
}
