package com.client.definitions;

import java.io.FileWriter;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import com.client.AnimFrame;
import com.client.Client;
import com.client.MRUNodes;
import com.client.Model;
import com.client.OnDemandFetcher;
import com.client.Buffer;
import com.client.StreamLoader;

public final class ObjectDefinition {


	public static ObjectDefinition forID(int i) {
		if (i > streamIndices.length)
			i = streamIndices.length - 2;

		if (i == 25913 || i == 25916 || i == 25917)
			i = 15552;

		for (int j = 0; j < 20; j++)
			if (cache[j].id == i)
				return cache[j];

		cacheIndex = (cacheIndex + 1) % 20;
		ObjectDefinition objectDef = cache[cacheIndex];
		stream.currentOffset = streamIndices[i];
		objectDef.id = i;
		objectDef.setDefaults();
		objectDef.readValues(stream);
		if (i >= 26281 && i <= 26290) {
			objectDef.actions = new String[] { "Choose", null, null, null, null };
		}

//		if (i == 31561) { // Rev agility shortcut
//			objectDef.xLength = 2;
//			objectDef.yLength = 3;
//		}
		switch (i) {
		case 36201: // Raids 1 lobby entrance
			objectDef.actions = new String[]{ "Enter", null, null, null, null};
			break;
			case 591:
				objectDef.name = "Item Upgrades Table";
				objectDef.actions = new String[]{"Forge", null, null, null, null};
				break;
		case 36062:
			objectDef.description = "Teleports you anywhere around Exilius.";
			objectDef.actions = new String[] { "Activate", "Previous Location", null, null, null };
			break;
		case 4152:
			objectDef.name = "Skilling Portal";
			objectDef.description = "Teleports you to various skilling areas.";
			objectDef.actions = new String[] { "Teleport", null, null, null, null };
			break;
		case 1206:
			objectDef.name = "Hespori Vines";
			objectDef.description = "The vines of Hespori.";
			objectDef.actions = new String[] { "Pick", null, null, null, null };
			break;
		case 33222:
			objectDef.name = "Burning Ore";
			objectDef.description = "I should try heating this up.";
			objectDef.actions = new String[] { "Mine", null, null, null, null };
			break;
		case 8880:
			objectDef.name = "Tool Box";
			objectDef.description = "Useful tools for resources in the area.";
			objectDef.actions = new String[] { "Take-tools", null, null, null, null };
			break;
		case 29771:
			objectDef.name = "Tools";
			objectDef.description = "Useful tools for resources in the area.";
			objectDef.actions = new String[] { null , null, null, null, null };
			break;
		case 33223:
			objectDef.name = "Enchanted stone";
			objectDef.description = "A fragile ancient stone.";
			objectDef.actions = new String[] { "Mine", null, null, null, null };
			break;

		case 33311:
			objectDef.name = "Fire";
			objectDef.description = "Looks very hot.";
			objectDef.actions = new String[] { "Burn-essence", "Burn-runes", null, null, null };
			break;
			case 12768:
				objectDef.name = "@gre@Nature Chest";
				objectDef.description = "Requires a Hespori key to open.";
				break;
				case 4121:
				objectDef.name = "@bla@Nex @red@ Chest";
				objectDef.description = "Requires a Nex key to open.";
				break;
		case 37743: // nightmare good flower
			objectDef.animation = 8617;
			break;
		case 37740: // nightmare bad flower
			objectDef.animation = 8623;
			break;
		case 37738: // nightmare spore spawn
			objectDef.animation = 8630;
			break;
		case 35914:
			objectDef.name = "Ahrim The Blighted";
			objectDef.actions = new String[] { "Awaken", null, null, null, null };
			break;
		case 9362:
			objectDef.name = "Dharok The Wretched";
			objectDef.actions = new String[] { "Awaken", null, null, null, null };
			break;
		case 14766:
			objectDef.name = "Verac The Defiled";
			objectDef.actions = new String[] { "Awaken", null, null, null, null };
			break;
		case 9360:
			objectDef.name = "Torag The Corrupted";
			objectDef.actions = new String[] { "Awaken", null, null, null, null };
			break;
		case 28723:
			objectDef.name = "Karil The Tainted";
			objectDef.actions = new String[] { "Awaken", null, null, null, null };
			break;
		case 31716:
			objectDef.name = "Guthan The Infested";
			objectDef.actions = new String[] { "Awaken", null, null, null, null };
			break;
		case 31622:
			objectDef.name = "Outlast Entrance";
			objectDef.actions = new String[] { "Enter", "Check Players", "Check Active", null, null };
			break;
		case 31624:
			objectDef.name = "@pur@Platinum Altar";
			break;
		case 29064:
			objectDef.name = "Exilius Leaderboards";
			objectDef.actions = new String[] { "View", null, null, null, null };
			break;
		case 33320:
				return copy(objectDef, 33320, 33321, "Fire of Exchange", new String[] { "Burn", "Burn Rates", null, null, null });
		case 33318:
			objectDef.name = "Fire of Domination";
			objectDef.modelIds = new int[] { 65467, 65468, 65469 };
			objectDef.actions = new String[] { "Sacrifice", null, null, null, null };
			break;
		case 32508:
			objectDef.name = "Hunllef's Chest";
			objectDef.actions = new String[] { "Unlock", null, null, null, null };
			break;
		case 6097:
			objectDef.actions = new String[] { "Donate", null, null, null, null };
			break;
		case 14888:
		    objectDef.name = "Jewelry Oven";
			break;
		case 29165:
		    objectDef.name = "Coin Stack";
		    objectDef.actions = new String[] { null, "Steal From", null, null, null };
			break;
		case 13681:
		    objectDef.name = "Animal Cage";
		    objectDef.actions = new String[] { null, null, null, null, null };
			break;
		case 30720:
			objectDef.name = "@red@Corrupt Chest";
			objectDef.actions = new String[] { "Open", null, null, null, null };
			break;
		case 34662:
			objectDef.actions = new String[] { "Open", "Teleport", null, null, null };
			break;
		case 12202:
			objectDef.actions = new String[] { "Dig", null, null, null, null };
			break;
		case 30107:
		    objectDef.name = "Raids Reward Chest";
		    objectDef.actions = new String[] { "Open", null, null, null, null };
			break;
		case 36197:
		    objectDef.name = "Home Teleport";
		   
			break;
		case 10562:
			objectDef.actions = new String[] { "Open", null, null, null, null };
			break;
			case 8720:
				objectDef.name = "Vote shop";
				break;
			case 29150:
				objectDef.actions = new String[] { "Venerate", null, null, null, null };
				break;
			case 6764:
				objectDef.name = null;
				objectDef.actions = new String[] { null, null, null, null, null };
				break;
		case 2341:
			objectDef.actions = new String[] { null, null, null, null, null };
			break;
		case 14217:
			objectDef.actions = new String[5];
			break;
		case 3840:
			objectDef.actions = new String[5];
			objectDef.actions[0] = "Fill";
			objectDef.actions[1] = "Empty-From";
			objectDef.name = "Compost Bin";
			break;
		case 172:
			objectDef.name = "Ckey chest";
		break;
		case 31925:
		    objectDef.name = "Max Island";
		    objectDef.actions = new String[] { "Tele to", null, null, null, null };
			break;
			case 31722:
				objectDef.name = "Bond fire";
				objectDef.actions = new String[] { "Burn logs", null, null, null, null };
				break;
		case 2996:
		    objectDef.name = "Vote Chest";
		    objectDef.actions = new String[] { "Unlock", null, null, null, null };
			break;

		case 12309:
			objectDef.actions = new String[5];
			objectDef.actions[0] = "Bank";
			objectDef.actions[1] = "Buy gloves";
			objectDef.actions[2] = null;
			objectDef.name = "Chest";
			break;
		case 32572:
			objectDef.actions = new String[5];
			objectDef.actions[0] = "Bank";
			objectDef.name = "Group chest";
			break;
		case 1750:
			objectDef.modelIds = new int[] { 8131, };
			objectDef.name = "Willow";
			objectDef.xLength = 2;
			objectDef.yLength = 2;
			objectDef.aByte737 = 25;
			objectDef.actions = new String[] { "Chop down", null, null, null, null };
			objectDef.mapSceneId = 3;
			break;
			
		case 26782:
			objectDef.actions = new String[] { "Recharge", null, null, null, null };
			break;

		case 1751:
			objectDef.modelIds = new int[] { 8037, 8040, };
			objectDef.name = "Oak";
			objectDef.xLength = 3;
			objectDef.yLength = 3;
			objectDef.aByte737 = 25;
			objectDef.actions = new String[] { "Chop down", null, null, null, null };
			objectDef.mapSceneId = 1;
			break;

		case 7814:
			objectDef.actions = new String[] { "Teleport", null, null, null, null };
			break;

		case 8356:
			objectDef.actions = new String[] { "Teleport", "Mt. Quidamortem", null, null, null };
			break;

		case 28900:
			objectDef.actions = new String[] { "Teleport", "Recharge Crystals", null, null, null };
			break;
		case 26740:
			objectDef.name = "Player Outlast";
			objectDef.actions = new String[] { "Join", "Setup", null, null, null };
			break;

		case 28837:
			objectDef.actions = new String[] { "Set Destination", null, null, null, null };
			break;

		case 7811:
			objectDef.name = "District Supplies";
			objectDef.actions = new String[] { "Blood Money", "Free", "Quick-Sets", null, null };
			break;
		case 10061:
		case 10060:
			objectDef.name = "Trading Post";
			objectDef.actions = new String[] { "Bank", "Open", "Collect", null, null };
			break;
		case 13287:
			objectDef.name = "Storage chest (UIM)";
			objectDef.description = "A chest to store items for UIM.";
			break;
		case 1752:
			objectDef.modelIds = new int[] { 4146, };
			objectDef.name = "Hollow tree";
			objectDef.aByte737 = 25;
			objectDef.actions = new String[] { "Chop down", null, null, null, null };
			objectDef.originalModelColors = new int[] { 13592, 10512, };
			objectDef.modifiedModelColors = new int[] { 7056, 6674, };
			objectDef.mapSceneId = 0;
			break;
		case 4873:
			objectDef.name = "Wilderness Lever";
			objectDef.xLength = 3;
			objectDef.yLength = 3;
			objectDef.aByte737 = 25;
			objectDef.actions = new String[] { "Enter Deep Wildy", null, null, null, null };
			objectDef.mapSceneId = 3;
			break;
		case 29735:
			objectDef.name = "Basic Slayer Dungeon";
			break;
		case 2544:
			objectDef.name = "Dagannoth Manhole";
			break;
		case 29345:
			objectDef.name = "Training Teleports Portal";
			objectDef.actions = new String[] { "Teleport", null, null, null, null };
			break;
		case 29346:
			objectDef.name = "Wilderness Teleports Portal";
			objectDef.actions = new String[] { "Teleport", null, null, null, null };
			break;
		case 29347:
			objectDef.name = "Boss Teleports Portal";
			objectDef.actions = new String[] { "Teleport", null, null, null, null };
			break;
		case 29349:
			objectDef.name = "Mini-Game Teleports Portal";
			objectDef.actions = new String[] { "Teleport", null, null, null, null };
			break;
		case 7127:
			objectDef.name = "Leaderboards";
			objectDef.actions = new String[] { "Open", "Wins", "Kills", "KDR", null };
			break;
		case 4155:
			objectDef.name = "Zul Andra Portal";
			break;
		case 2123:
			objectDef.name = "Mt. Quidamortem Slayer Dungeon";
			break;
		case 4150:
			objectDef.name = "Warriors Guild Mini-game Portal";
			break;
		case 11803:
			objectDef.name = "Donator Slayer Dungeon";
			break;
		case 4151:
			objectDef.name = "Barrows Mini-game Portal";
			break;
		case 1753:
			objectDef.modelIds = new int[] { 8157, };
			objectDef.name = "Yew";
			objectDef.xLength = 3;
			objectDef.yLength = 3;
			objectDef.aByte737 = 25;
			objectDef.actions = new String[] { "Chop down", null, null, null, null };
			objectDef.mapSceneId = 3;
			break;

		case 6943:
			objectDef.modelIds = new int[] { 1270, };
			objectDef.name = "Bank booth";
			objectDef.aBoolean757 = false;
			objectDef.aByte737 = 25;
			objectDef.aByte742 = 25;
			objectDef.actions = new String[] { null, "Bank", "Collect", null, null };
			break;

		case 25016:
		case 25017:
		case 25018:
		case 25029:
			objectDef.actions = new String[] { "Push-Through", null, null, null, null };
			break;

		case 19038:
			objectDef.actions = new String[] { null, null, null, null, null };
			objectDef.xLength = 3;
			objectDef.yLength = 3;
			objectDef.width = 340; // Width
			objectDef.thickness = 500; // Thickness
			objectDef.height = 400; // Height
			break;

		case 18826:
		case 18819:
		case 18818:
			objectDef.xLength = 3;
			objectDef.yLength = 3;
			objectDef.width = 200; // Width
			objectDef.thickness = 200; // Thickness
			objectDef.height = 100; // Height
			break;

		case 27777:
			objectDef.name = "Gangplank";
			objectDef.actions = new String[] { "Travel to CrabClaw Isle", null, null, null, null };
			objectDef.xLength = 1;
			objectDef.yLength = 1;
			objectDef.width = 80; // Width
			objectDef.thickness = 80; // Thickness
			objectDef.height = 250; // Height
			break;
		case 13641:
			objectDef.name = "Teleportation Device";
			objectDef.actions = new String[] { "Quick-Teleport", null, null, null, null };
			objectDef.xLength = 1;
			objectDef.yLength = 1;
			objectDef.width = 80; // Width
			objectDef.thickness = 80; // Thickness
			objectDef.height = 250; // Height
			break;
			
		case 29333:
			objectDef.name = "Trading post";
			objectDef.actions = new String[] { "Open", null, "Collect", null, null };
			objectDef.modelIds = new int[] { 60884 };
			objectDef.aByte737 = 25;
			objectDef.aBoolean769 = false;
			objectDef.description = "Buy and sell items with players here!";
			break;

		case 11700:
			objectDef.modelIds = new int[] { 4086 };
			objectDef.name = "Venom";
			objectDef.xLength = 3;
			objectDef.yLength = 3;
			objectDef.aBoolean767 = false;
			objectDef.aBoolean762 = true;
			objectDef.animation = 1261;
			objectDef.modifiedModelColors = new int[] { 31636 };
			objectDef.originalModelColors = new int[] { 10543 };
			objectDef.thickness = 160;
			objectDef.height = 160;
			objectDef.width = 160;
			objectDef.actions = new String[5];
			// objectDef.description = new String(
			// "It's a cloud of venomous smoke that is extremely toxic.");
			break;
			case 46324:
				objectDef.modelIds = new int[] { 39095 };
				objectDef.name = "Christmas Tree";
				objectDef.aBoolean757 = false;
				objectDef.xLength = 7;
				objectDef.yLength = 7;
				objectDef.aBoolean767 = true;
				objectDef.aByte737 = 25;
				objectDef.aByte742 = 25;
				objectDef.thickness = 112;
				objectDef.height = 112;
				objectDef.width = 112;
				break;
		case 11601: // 11601
			objectDef.originalTexture = new short[] { 2 };
			objectDef.modifiedTexture = new short[] { 46 };
			break;
		}
		if (Client.debugModels) {

			if (objectDef.name == null || objectDef.name.equalsIgnoreCase("null"))
				objectDef.name = "test";

			objectDef.hasActions = true;
		}
		return objectDef;
	}

	public static void dumpList() {
		try {
			FileWriter fw = new FileWriter("./temp/" + "object_data.json");
			fw.write("[\n");
			for (int i = 0; i < totalObjects; i++) {
				ObjectDefinition def = ObjectDefinition.forID(i);
				String output = "[\"" + StringUtils.join(def.actions, "\", \"") + "\"],";

				String finalOutput = "	{\n" + "		\"id\": " + def.id + ",\n		" + "\"name\": \"" + def.name
						+ "\",\n		\"models\": " + Arrays.toString(def.modelIds) + ",\n		\"actions\": "
						+ output.replaceAll(", \"\"]", ", \"Examine\"]").replaceAll("\"\"", "null")
								.replace("[\"null\"]", "[null, null, null, null, \"Examine\"]")
								.replaceAll(", \"Remove\"", ", \"Remove\", \"Examine\"")
						+ "	\n		\"width\": " + def.width + "\n	},";
				fw.write(finalOutput.replaceAll("\"name\": \"null\",", "\"name\": null,"));
				fw.write(System.getProperty("line.separator"));
			}
			fw.write("]");
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private void setDefaults() {
		modelIds = null;
		models = null;
		name = null;
		description = null;
		modifiedModelColors = null;
		originalModelColors = null;
		 originalTexture = null;
		 modifiedTexture = null;
		xLength = 1;
		yLength = 1;
		this.interactType = 2; // L: 35
		aBoolean767 = true;
		aBoolean757 = true;
		hasActions = false;
		aBoolean762 = false;
		aBoolean769 = false;
		aBoolean764 = false;
		animation = -1;
		anInt775 = 16;
		aByte737 = 0;
		aByte742 = 0;
		actions = null;
		mapFunctionId = -1;
		mapSceneId = -1;
		inverted = false;
		clipped = true;
		thickness = 128;
		height = 128;
		width = 128;
		anInt768 = 0;
		anInt738 = 0;
		anInt745 = 0;
		anInt783 = 0;
		aBoolean736 = false;
		aBoolean766 = false;
		supportItems = -1;
		varpId = -1;
		configId = -1;
		childrenIDs = null;
	}

	public void method574(OnDemandFetcher class42_sub1) {
		if (modelIds == null)
			return;
		for (int j = 0; j < modelIds.length; j++)
			class42_sub1.method560(modelIds[j] & 0xffff, 0);
	}

	public static void nullLoader() {
		mruNodes1 = null;
		mruNodes2 = null;
		streamIndices = null;
		cache = null;
		stream = null;
	}

	public static int totalObjects;

	public static void unpackConfig(StreamLoader streamLoader) {
		stream = new Buffer(streamLoader.getArchiveData("loc.dat"));
		Buffer stream = new Buffer(streamLoader.getArchiveData("loc.idx"));
		totalObjects = stream.readUShort();
		streamIndices = new int[totalObjects];
		int i = 2;
		for (int j = 0; j < totalObjects; j++) {
			streamIndices[j] = i;
			i += stream.readUShort();
		}
		cache = new ObjectDefinition[20];
		for (int k = 0; k < 20; k++)
			cache[k] = new ObjectDefinition();
	}

	public boolean method577(int i) {
		if (models == null) {
			if (modelIds == null)
				return true;
			if (i != 10)
				return true;
			boolean flag1 = true;
			Model model = (Model) mruNodes2.insertFromCache(id);
			for (int k = 0; k < modelIds.length; k++)
				flag1 &= model.isCached(modelIds[k]);
			return flag1;
		}
		Model model = (Model) mruNodes2.insertFromCache(id);
		for (int j = 0; j < models.length; j++)
			if (models[j] == i)
				return model.isCached(modelIds[j]);
		return true;
	}

	public Model modelAt(int i, int j, int k, int l, int i1, int j1, int k1, AnimationDefinition seqtype) {
		Model model = model(i, k1, j, seqtype);
		if (model == null)
			return null;
		if (aBoolean762 || aBoolean769)
			model = new Model(aBoolean762, aBoolean769, model);
		if (aBoolean762) {
			int l1 = (k + l + i1 + j1) / 4;
			for (int i2 = 0; i2 < model.vertex_count; i2++) {
				int j2 = model.verticesX[i2];
				int k2 = model.verticesZ[i2];
				int l2 = k + ((l - k) * (j2 + 64)) / 128;
				int i3 = j1 + ((i1 - j1) * (j2 + 64)) / 128;
				int j3 = l2 + ((i3 - l2) * (k2 + 64)) / 128;
				model.verticesY[i2] += j3 - l1;
			}
			model.normalise();
			model.resetBounds();
		}
		return model;
	}

	public boolean method579() {
		if (modelIds == null)
			return true;
		boolean flag1 = true;
		for (int i = 0; i < modelIds.length; i++)
			flag1 &= Model.isCached(modelIds[i]);
		return flag1;
	}

	public ObjectDefinition method580() {
		int i = -1;
		if (varpId != -1) {
			VarBit varBit = VarBit.cache[varpId];
			int j = varBit.configID;
			int k = varBit.lsb;
			int l = varBit.msb;
			int i1 = Client.anIntArray1232[l - k];
			i = clientInstance.variousSettings[j] >> k & i1;
		} else if (configId != -1)
			i = clientInstance.variousSettings[configId];
		int var3;
		if (i >= 0 && i < childrenIDs.length)
			var3 = childrenIDs[i];
		else
			var3 = childrenIDs[childrenIDs.length - 1];
		return var3 == -1 ? null : forID(var3);
	}

	private Model model(int type, int frame, int orientation, AnimationDefinition seqtype) {
		Model model = null;
		long key;
		if (models == null) {
			if (type != 10)
				return null;

			key = frame + 1L << 32 | ((inverted ? 1 : 0) << 16)| id << 6 | orientation;
			Model model_1 = (Model) mruNodes2.insertFromCache(key);
			if (model_1 != null) {
				return model_1;
			}
			if (modelIds == null)
				return null;
			boolean flag1 = inverted ^ (orientation > 3);
			int k1 = modelIds.length;
			for (int i2 = 0; i2 < k1; i2++) {
				int l2 = modelIds[i2];
				if (flag1)
					l2 += 0x10000;
				model = (Model) mruNodes1.insertFromCache(l2);
				if (model == null) {
					model = Model.getModel(l2 & 0xffff);
					if (model == null)
						return null;
					if (flag1)
						model.mirror();
					mruNodes1.removeFromCache(model, l2);
				}
				if (k1 > 1)
					aModelArray741s[i2] = model;
			}

			if (k1 > 1)
				model = new Model(k1, aModelArray741s);
		} else {
			int index = -1;
			for (int j1 = 0; j1 < models.length; j1++) {
				if (models[j1] != type)
					continue;
				index = j1;
				break;
			}

			if (index == -1)
				return null;

			key = frame + 1L << 32 | ((inverted ? 1 : 0) << 16) | id << 6 | index << 3 | orientation;
			Model model_2 = (Model) mruNodes2.insertFromCache(key);
			if (model_2 != null)
				return model_2;
			int j2 = modelIds[index];
			boolean flag3 = inverted ^ (orientation > 3);
			if (flag3)
				j2 += 0x10000;
			model = (Model) mruNodes1.insertFromCache(j2);
			if (model == null) {
				model = Model.getModel(j2 & 0xffff);
				if (model == null)
					return null;
				if (flag3)
					model.mirror();
				mruNodes1.removeFromCache(model, j2);
			}
		}
		boolean flag;
		flag = thickness != 128 || height != 128 || width != 128;
		boolean flag2;
		flag2 = anInt738 != 0 || anInt745 != 0 || anInt783 != 0;
		Model model_3 = new Model(modifiedModelColors == null,
				AnimFrame.noAnimationInProgress(frame), orientation == 0 && frame == -1 && !flag
				&& !flag2, modifiedTexture == null, model);
		if (frame != -1) {
			model_3.apply_label_groups();
			model_3.animate_either(seqtype, frame);
			model_3.face_label_groups = null;
			model_3.vertex_label_groups = null;
		}
		while (orientation-- > 0)
			model_3.rotate90Degrees();

		if (modifiedModelColors != null) {
			for (int k2 = 0; k2 < modifiedModelColors.length; k2++) {
				model_3.recolor(modifiedModelColors[k2], originalModelColors[k2]);
			}

		}
		if (originalTexture != null) {
			for (int k2 = 0; k2 < originalTexture.length; k2++) {
				model_3.retexture(originalTexture[k2], modifiedTexture[k2]);
			}

		}
		if (flag)
			model_3.scale(thickness, width, height);
		if (flag2)
			model_3.offsetBy(anInt738, anInt745, anInt783);

		model_3.light(85 + aByte737, 768 + aByte742 * 25, -50, -10, -50, !aBoolean769);

		if (supportItems == 1)
			model_3.itemDropHeight = model_3.modelHeight;
		mruNodes2.removeFromCache(model_3, key);
		return model_3;
	}
	public static ObjectDefinition copy(ObjectDefinition objectDefinition, int newId, int copyingobjId, String newName, String...actions) {
		ObjectDefinition copyobjectDefinition = forID(copyingobjId);
		objectDefinition.id = newId;
		objectDefinition.name = newName;
		objectDefinition.description = copyobjectDefinition.description;
		objectDefinition.modifiedModelColors = copyobjectDefinition.modifiedModelColors;
		objectDefinition.originalModelColors = copyobjectDefinition.originalModelColors;
		objectDefinition.modelIds = copyobjectDefinition.modelIds;
		objectDefinition.actions = new String[5];
		if (actions != null) {
			for (int index = 0; index < actions.length; index++) {
				objectDefinition.actions[index] = actions[index];
			}
		}
		return objectDefinition;
	}
	public void readValues(Buffer stream) {
		int flag = -1;
		do {
			int type = stream.readUnsignedByte();
			if (type == 0)
				break;
			if (type == 1) {
				int var3 = stream.readUnsignedByte();
				if (var3 > 0) {
					if (this.modelIds != null && !lowMem) {
						stream.currentOffset += 3 * var3;
					} else {
						this.models = new int[var3];
						this.modelIds = new int[var3];

						for(int var4 = 0; var4 < var3; ++var4) {
							this.modelIds[var4] = stream.readUShort();
							this.models[var4] = stream.readUnsignedByte();
						}
					}
				}
			} else if (type == 2)
				name = stream.readString();
			else if (type == 3)
				description = stream.readString();
			else if (type == 5) {
			int var3 = stream.readUnsignedByte();
			if (var3 > 0) {
				if (this.modelIds != null && !lowMem) {
					stream.currentOffset += 2 * var3;
				} else {
					this.models = null;
					this.modelIds = new int[var3];

					for(int var4 = 0; var4 < var3; ++var4) {
						this.modelIds[var4] = stream.readUShort();
					}
				}
			}
			} else if (type == 14)
				xLength = stream.readUnsignedByte();
			else if (type == 15)
				yLength = stream.readUnsignedByte();
			else if (type == 17) {
				this.interactType = 0; // L: 135
				aBoolean767 = false;
			} else if (type == 18)
				aBoolean757 = false;
			else if (type == 19)
				hasActions = (stream.readUnsignedByte() == 1);
			else if (type == 21)
				aBoolean762 = true;
			else if (type == 22)
				aBoolean769 = true;
			else if (type == 23)
				aBoolean764 = true;
			else if (type == 24) { // Object Animations
				animation = stream.readUShort();
				if (animation == 65535)
					animation = -1;
			} else if (type == 27) { // L: 147
				this.interactType = 1;
			} else if (type == 28)
				anInt775 = stream.readUnsignedByte();
			else if (type == 29)
				aByte737 = stream.readSignedByte();
			else if (type == 39)
				aByte742 = stream.readSignedByte();
			else if (type >= 30 && type < 39) {
				if (actions == null)
					actions = new String[9];
				actions[type - 30] = stream.readString();
				if (actions[type - 30].equalsIgnoreCase("hidden"))
					actions[type - 30] = null;
			} else if (type == 40) {
				int i1 = stream.readUnsignedByte();
				modifiedModelColors = new int[i1];
				originalModelColors = new int[i1];
				for (int i2 = 0; i2 < i1; i2++) {
					modifiedModelColors[i2] = stream.readUShort();
					originalModelColors[i2] = stream.readUShort();
				}
			} else if (type == 41) {
				int i1 = stream.readUnsignedByte();
				originalTexture = new short[i1];
				modifiedTexture = new short[i1];
				for (int i2 = 0; i2 < i1; i2++) {
					originalTexture[i2] = (short) stream.readUShort();
					modifiedTexture[i2] = (short) stream.readUShort();
				}
			} else if (type == 61)
					stream.readUShort();
			else if (type == 62)
				inverted = true;
			else if (type == 64)
				clipped = false;
			else if (type == 65)
				thickness = stream.readUShort();
			else if (type == 66)
				height = stream.readUShort();
			else if (type == 67)
				width = stream.readUShort();
			else if (type == 68) {
				mapSceneId = stream.readUShort();
			} else if (type == 69)
				anInt768 = stream.readUnsignedByte();
			else if (type == 70)
				anInt738 = stream.readSignedWord();
			else if (type == 71)
				anInt745 = stream.readSignedWord();
			else if (type == 72)
				anInt783 = stream.readSignedWord();
			else if (type == 73)
				aBoolean736 = true;
			else if (type == 74)
				aBoolean766 = true;
			else if (type == 75)
				supportItems = stream.readUnsignedByte();
			else if (type == 77 || type == 92) {
				varpId = stream.readUShort();
				if (varpId == 65535)
					varpId = -1;
				configId = stream.readUShort();
				if (configId == 65535)
					configId = -1;
				int var3 = -1;
				if(type == 92)
					var3 = stream.readUShort();
				int j1 = stream.readUnsignedByte();
				childrenIDs = new int[j1 + 2];
				for (int j2 = 0; j2 <= j1; j2++) {
					childrenIDs[j2] = stream.readUShort();
					if (childrenIDs[j2] == 65535)
						childrenIDs[j2] = -1;
				}
				childrenIDs[j1 + 1] = var3;
			} else if(type == 78) {//ambient sound
				stream.readUShort();
				stream.readUnsignedByte();
			} else if(type == 79) {
				stream.currentOffset += 5;
				int len = stream.readUnsignedByte();
				stream.currentOffset += len * 2;
			} else if(type == 81) {
				interactType = stream.readUnsignedByte() * 256;
			} else if(type == 82) {
				mapFunctionId = stream.readUShort();
			} else if (type == 89) {
				this.boolean3 = false;
			} else if (type == 249) {
				int length = stream.readUnsignedByte();

				for (int i = 0; i < length; i++) {
					boolean isString = stream.readUnsignedByte() == 1;
					int key = stream.read24BitInt();

					if (isString) {
						stream.readOSRSString();
					} else {
						stream.read24BitInt();
					}
				}
			}
		} while (true);
		if (flag == -1 && name != "null" && name != null) {
			hasActions = modelIds != null && (models == null || models[0] == 10);
			if (actions != null)
				hasActions = true;
		}
		if (aBoolean766) {
			interactType = 0;
			aBoolean757 = false;
		}
		if (supportItems == -1)
			supportItems = interactType != 0 ? 1 : 0;
	}

	private ObjectDefinition() {
		id = -1;
	}

	private short[] originalTexture;
	private short[] modifiedTexture;
	public boolean aBoolean736;
	@SuppressWarnings("unused")
	private byte aByte742;
	@SuppressWarnings("unused")
	private byte aByte737;
	private int anInt738;
	public String name;
	public boolean boolean3 = true;
	private int width;
	private static final Model[] aModelArray741s = new Model[4];
	public int xLength;
	private int anInt745;
	public int mapFunctionId;
	private int[] originalModelColors;
	private int thickness;
	public int configId;
	private boolean inverted;
	public static boolean lowMem;
	private static Buffer stream;
	public int id;
	public static int[] streamIndices;
	public boolean aBoolean757;
	public int mapSceneId;
	public int childrenIDs[];
	public int supportItems;
	public int yLength;
	public boolean aBoolean762;
	public boolean aBoolean764;
	public static Client clientInstance;
	private boolean aBoolean766;
	public boolean aBoolean767;
	public int anInt768;
	private boolean aBoolean769;
	private static int cacheIndex;
	private int height;
	public int[] modelIds;
	public int varpId;
	public int anInt775;
	private int[] models;
	public String description;
	public boolean hasActions;
	public boolean clipped;
	public static MRUNodes mruNodes2 = new MRUNodes(30);
	public int animation;
	private static ObjectDefinition[] cache;
	private int anInt783;
	private int[] modifiedModelColors;
	public static MRUNodes mruNodes1 = new MRUNodes(500);
	public String actions[];
	public int interactType;

}