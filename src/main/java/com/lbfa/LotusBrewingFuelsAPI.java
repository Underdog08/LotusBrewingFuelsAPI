package com.lbfa;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LotusBrewingFuelsAPI implements ModInitializer {
	public static final String MOD_ID = "lbfa";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ResourceManagerHelper.get(ResourceType.SERVER_DATA)
				.registerReloadListener(new BrewingFuelsLoader());
	}
}