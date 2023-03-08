package liketechnik.tinkertweaks.config;

import net.minecraft.item.Item;

import java.io.*;
import java.io.File;
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

  
   public static boolean addRandomModifierOnLevelup() {
    return INSTANCE.configFile.general.bonusRandomModifier;
  }
   public static boolean addModifierSlotOnLevelup() {
    return INSTANCE.configFile.general.bonusModifierSlot;
  }
   public static boolean addBonusStatsOnLevelup() {
    return INSTANCE.configFile.general.bonusStats;
  }
  
  
   public static Map<String, Float> statBonusValues() {
    Map<String, Float> statsMap = new HashMap<String, Float>() {{
	  put("damageBonus",Float.valueOf(INSTANCE.configFile.bonusstats.damageBonus));
	  put("durabilityMultiplier",Float.valueOf(INSTANCE.configFile.bonusstats.durabilityMultiplier));
	  put("miningSpeedBonus",Float.valueOf(INSTANCE.configFile.bonusstats.miningSpeedBonus));
	  put("attackSpeedMultiplier",Float.valueOf(INSTANCE.configFile.bonusstats.attackSpeedMultiplier));
	  put("drawSpeedBonus",Float.valueOf(INSTANCE.configFile.bonusstats.drawSpeedBonus));
	  put("projectileSpeedBonus",Float.valueOf(INSTANCE.configFile.bonusstats.projectileSpeedBonus));
	}};
	
	return statsMap;
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
  public static String getStatsUpMessage() {
	  return INSTANCE.configFile.messages.genericStatsUpMessage;
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
  public static String getModifierMessage(String modifier) {
	  ConfigFile.Messages messages = INSTANCE.configFile.messages;
	  if (messages.modifierMessages.containsKey(modifier)) {
		// Remove excess unicode C2 characters
		String message = messages.modifierMessages.get(modifier);
		String recoded_message = "";
		recoded_message = message.replaceAll("\u00C2", "");
		return recoded_message;
	  }
	  return messages.genericModifierMessage;
  }
}
