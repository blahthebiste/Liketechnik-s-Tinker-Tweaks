package liketechnik.tinkertweaks.config;

import net.minecraft.item.Item;

import java.io.*;
// import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import slimeknights.mantle.config.AbstractConfig;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.modifiers.IModifier;

public class Config extends AbstractConfig {

  public static Config INSTANCE = new Config();

  public ConfigFile configFile;

  public void load(File file) {
    ConfigFile.init();

    configFile = this.load(new ConfigFile(file), ConfigFile.class);
  }

  public static int getBaseXpForTool(Item item) {
    ConfigFile.ToolXP toolXP = INSTANCE.configFile.toolxp;
    return toolXP.baseXpForTool.getOrDefault(item, toolXP.defaultBaseXP);
  }

  public static float getLevelMultiplier() {
    return INSTANCE.configFile.toolxp.levelMultiplier;
  }

  public static int getStartingModifiers() {
    return INSTANCE.configFile.general.newToolMinModifiers;
  }

  public static boolean canLevelUp(int currentLevel) {
    return INSTANCE.configFile.general.maximumLevels < 0 || INSTANCE.configFile.general.maximumLevels >= currentLevel;
  }
  
  public static List<IModifier> getModifiers(Item item) {
    ConfigFile.Modifier modifier = INSTANCE.configFile.modifier;
    List<IModifier> modifiers = new ArrayList<>();
    modifier.modifiersForTool.getOrDefault(item, modifier.modifiers).stream().forEach(mod -> modifiers.add(TinkerRegistry.getModifier(mod)));
    return modifiers;
  }
  
  public static boolean modifierAndFree() {
    return INSTANCE.configFile.modifier.both;
  }
  
  // Newly added for getting message strings:
  public static boolean shouldUseConfigLevelTitles() {
	  return INSTANCE.configFile.messages.configLevelTitles;
  }
  public static boolean shouldUseConfigLevelupMessages() {
	  return INSTANCE.configFile.messages.configLevelupMessages;
  }
  public static boolean shouldUseConfigModifierMessages() {
	  return INSTANCE.configFile.messages.configModifierMessages;
  }
  public static String getGenericLevelupMessage() {
	  return INSTANCE.configFile.messages.genericLevelupMessage;
  }
  public static String getGenericModifierMessage() {
	  return INSTANCE.configFile.messages.genericModifierMessage;
  }
  public static String getLevelTitle(int currentLevel) {
	  ConfigFile.Messages messages = INSTANCE.configFile.messages;
	  if (messages.levelTitles.containsKey(currentLevel)) {
		return messages.levelTitles.get(currentLevel);
	  }
	  String levelStr = Integer.toString(currentLevel);
	  return levelStr;
  }
  public static String getLevelupMessage(int currentLevel) {
	  ConfigFile.Messages messages = INSTANCE.configFile.messages;
	  if (messages.levelupMessages.containsKey(currentLevel)) {
		return messages.levelupMessages.get(currentLevel);
	  }
	  return messages.genericLevelupMessage;
  }
  public static String getModifierMessage(String mod) {
	  ConfigFile.Messages messages = INSTANCE.configFile.messages;
	  if (messages.modifierMessages.containsKey(mod)) {
		// Remove excess unicode C2 characters
		String message = messages.modifierMessages.get(mod);
		String recoded_message = "";
		recoded_message = message.replaceAll("\u00C2", "");
		return recoded_message;
	  }
	  return messages.genericModifierMessage;
  }
}
