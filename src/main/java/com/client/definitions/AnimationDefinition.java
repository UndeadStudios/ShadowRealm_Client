package com.client.definitions;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;
import java.util.Map;

import com.client.*;
import com.client.definitions.custom.AnimationDefinitionCustom;
import com.client.model.rt7_anims.AnimKeyFrameSet;
import com.google.common.collect.Lists;

public final class AnimationDefinition {
	public Model animate_iftype_model(Model model, int primary_index) {
		if(using_keyframes()) {
			return this.animate_either(model, primary_index);
		}
		int regular_frame = frames[primary_index];
		AnimFrameSet regular_frameset = AnimFrameSet.get_frameset(regular_frame >> 16);
		int regular_frameindex = regular_frame & 0xffff;
		if (regular_frameset == null) {
			return model.bake_shared_model(true);
		}
		AnimFrameSet if_frameset = null;
		int if_frame = 0;
		int if_frameindex = 0;
		if (iftype_frames != null && primary_index < iftype_frames.length) {
			if_frame = iftype_frames[primary_index];
			if_frameset = AnimFrameSet.get_frameset(if_frame >> 16);
			if_frameindex = if_frame & 0xffff;
		}

		Model animated_model;
		if (if_frameset == null || if_frame == 0xffff) {
			animated_model = model.bake_shared_model(!regular_frameset.has_transparency_mod(regular_frameindex));
			animated_model.animate_frame_new(regular_frameset, regular_frameindex);
		} else {
			animated_model = model.bake_shared_model(!regular_frameset.has_transparency_mod(regular_frameindex) & !if_frameset.has_transparency_mod(if_frameindex));
			animated_model.animate_frame_new(regular_frameset, regular_frameindex);
			animated_model.animate_frame_new(if_frameset, if_frameindex);
		}
		return animated_model;
	}

	public Model animate_either(Model model, int index) {
		Model var5;

		if (this.using_keyframes()) {
			AnimKeyFrameSet var4 = get_keyframeset();
			if (null == var4) {
				return model.bake_shared_animation_model(true);
			} else {
				var5 = model.bake_shared_animation_model(!var4.modifies_alpha());
				var5.animate_skeletal_keyframe(var4, index);
				return var5;
			}
		} else {
			int frame = this.frames[index];
			AnimFrameSet var8 = AnimFrameSet.get_frameset(frame >> 16);
			int frame_index = frame & 0xffff;
			if (null == var8) {
				return model.bake_shared_animation_model(true);
			} else {
				var5 = model.bake_shared_animation_model(!var8.has_transparency_mod(frame_index));
				var5.animate_frame_new(var8, frame_index);
				return var5;
			}
		}
	}
	public Model animate_multiple(Model second_model, int primary_index, AnimationDefinition secondary_seq, int secondary_index) {
		Model var6 = second_model.bake_shared_animation_model(false);
		AnimKeyFrameSet keyframeset;
		AnimFrameSet frameset_ref = null;
		boolean skeletal = false;
		AnimBase var9 = null;
		if(using_keyframes()) {
			keyframeset = get_keyframeset();
			if (keyframeset == null)
				return var6;

			if (secondary_seq.using_keyframes() && mergedbonegroups == null) {
				var6.animate_skeletal_keyframe(keyframeset, primary_index);
				return var6;
			}

			var9 = keyframeset.base;
			var6.animate_skeletal_keyframe_tween(keyframeset.base, keyframeset, primary_index, mergedbonegroups, false, !secondary_seq.using_keyframes());
		} else {
			int frame = frames[primary_index];
			frameset_ref = AnimFrameSet.get_frameset(frame >> 16);
			primary_index = frame & 0xffff;
			if (frameset_ref == null) {
				return secondary_seq.animate_either(var6, secondary_index);
			}

			if (!secondary_seq.using_keyframes() && (mergedseqgroups == null || secondary_index == -1)) {
				var6.animate_frame_new(frameset_ref, primary_index);
				return var6;
			}

			if (mergedseqgroups == null || secondary_index == -1) {
				var6.animate_frame_new(frameset_ref, primary_index);
				return var6;
			}

			skeletal = secondary_seq.using_keyframes();
			if (!skeletal) {
				var6.animate_frames(frameset_ref, primary_index, mergedseqgroups, false);
			}
		}

		if(secondary_seq.using_keyframes()) {
			keyframeset = secondary_seq.get_keyframeset();
			if (keyframeset == null) {
				return var6;
			}

			if (var9 == null) {
				var9 = keyframeset.base;
			}

			var6.animate_skeletal_keyframe_tween(var9, keyframeset, secondary_index, mergedbonegroups, true, true);
		} else {
			int var12 = secondary_seq.frames[secondary_index];
			AnimFrameSet var14 = AnimFrameSet.get_frameset(var12 >> 16);
			int var13 = var12 & 0xffff;
			if (var14 == null) {
				return animate_either(var6, primary_index);
			}

			var6.animate_frames(var14, var13, mergedseqgroups, true);
		}

		if (skeletal && frameset_ref != null) {
			var6.animate_frames(frameset_ref, primary_index, mergedseqgroups, false);
		}

		var6.resetBounds();
		var6.invalidate();// TODO keep?
		return var6;
	}

	public Model bake_and_animate_spotanim(Model model, int frameindex) {
		Model var5;
		if (this.using_keyframes()) {
			AnimKeyFrameSet var4 = get_keyframeset();
			if (var4 == null) {
				return model.bake_shared_model(true);
			} else {
				var5 = model.bake_shared_model(!var4.modifies_alpha());
				var5.animate_skeletal_keyframe(var4, frameindex);
				return var5;
			}
		} else {
			int frame = this.frames[frameindex];
			AnimFrameSet var8 = AnimFrameSet.get_frameset(frame >> 16);
			int frame_index = frame & 0xffff;
			if (var8 == null) {
				return model.bake_shared_model(true);
			} else {
				var5 = model.bake_shared_model(!var8.has_transparency_mod(frame_index));
				var5.animate_frame_new(var8, frame_index);
				return var5;
			}
		}

	}
	public static void unpackConfig(StreamLoader streamLoader) {
		Buffer stream = new Buffer(streamLoader.getArchiveData("seq.dat"));
		int length = stream.readUShort();
		if (anims == null)
			anims = new AnimationDefinition[length+5000];

		for (int j = 0; j < length; j++) {
			if (anims[j] == null)
				anims[j] = new AnimationDefinition();
			anims[j].id = j;
			anims[j].readValues(stream);
			AnimationDefinitionCustom.custom(j, anims);

			if (Configuration.dumpAnimationData) {

			if (anims[j].frame_durations != null && anims[j].frame_durations.length > 0) {
					int sum = 0;
					for (int i = 0; i < anims[j].frame_durations.length; i++) {
						if (anims[j].frame_durations[i] < 100) {
							sum += anims[j].frame_durations[i];
						}
					}

					System.out.println(j + ":" + sum);
				}
			}
		}

		if (Configuration.dumpAnimationData) {
			System.out.println("Dumping animation lengths..");

			try (BufferedWriter writer = new BufferedWriter(new FileWriter("./temp/animation_lengths.cfg"))) {
				for (int j = 0; j < length; j++) {
					if (anims[j].frame_durations != null && anims[j].frame_durations.length > 0) {
						int sum = 0;
						for (int i = 0; i < anims[j].frame_durations.length; i++) {
							if (anims[j].frame_durations[i] < 100) {
								sum += anims[j].frame_durations[i];
							}
						}
						writer.write(j + ":" + sum);
						writer.newLine();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try (BufferedWriter writer = new BufferedWriter(new FileWriter("./temp/208animation_ids.cfg"))) {
				for (int j = 0; j < length; j++) {
					if (anims[j].weapon != -1) {
						writer.write("ID:" + j + ", Right Hand ItemID:" + (anims[j].weapon -512));
						writer.newLine();
					}
					if (anims[j].shield != -1) {
						writer.write("ID:" + j + ", left Hand ItemID:" + (anims[j].shield -512));
						writer.newLine();
					}
				}
			} catch(Exception e){
				e.printStackTrace();
			}
			System.out.println("Dumping animation sounds..");
			for (int j = 0; j < length; j++) {
				if (anims[j].frameSounds != null) {
					System.out.println(j + ":" + Arrays.toString(anims[j].frameSounds));
				}
			}
			System.out.println("Dumping animation fields to /temp/animation_dump.txt");
			dump();
		}
	}

	public int getFrameSound(int frameIndex) {
		if (frameSounds != null && frameIndex < frameSounds.length && frameSounds[frameIndex] != 0) {
			return frameSounds[frameIndex];
		} else {
			return -1;
		}
	}
	public int get_keyframe_soundeffect(int keyframe) {
		if (frameSounds != null && keyframe < frameSounds.length && frameSounds[keyframe] != 0) {
			return frameSounds[keyframe];
		} else {
			return -1;
		}
	}

	private void readValues(Buffer stream) {
		int opdcode;
		while ((opdcode = stream.readUnsignedByte()) != 0) {

			if (opdcode == 1) {
				framecount = stream.readUShort();
				frames = new int[framecount];
				frame_durations = new int[framecount];
				for (int j = 0; j < framecount; j++)
					frame_durations[j] = stream.readUShort();

				for (int j = 0; j < framecount; j++) {
					frames[j] = stream.readUShort();
				}
				for (int j = 0; j < framecount; j++) {
					frames[j] += stream.readUShort() << 16;
				}
			} else if (opdcode == 2)
				loop_delay = stream.readUShort();
			else if (opdcode == 3) {
				int k = stream.readUnsignedByte();
				mergedseqgroups = new int[k + 1];
				for (int l = 0; l < k; l++)
					mergedseqgroups[l] = stream.readUnsignedByte();
				mergedseqgroups[k] = 9999999;
			} else if (opdcode == 4)
				stretches = true;
			else if (opdcode == 5)
				anInt359 = stream.readUnsignedByte();
			else if (opdcode == 6)
				shield = stream.readUShort();
			else if (opdcode == 7)
				weapon = stream.readUShort();
			else if (opdcode == 8) {
				replaycount = stream.readUnsignedByte();
				replay = true;
			} else if (opdcode == 9)
				exactmove = stream.readUnsignedByte();
			else if (opdcode == 10)
				movetype = stream.readUnsignedByte();
			else if (opdcode == 11)
				looptype = stream.readUnsignedByte();
			else if (opdcode == 12) {
				int len = stream.readUnsignedByte();
				iftype_frames = new int[len];
				for (int i2 = 0; i2 < len; i2++) {
					iftype_frames[i2] = stream.readUShort();
				}

				for (int i2 = 0; i2 < len; i2++) {
					iftype_frames[i2] += stream.readUShort() << 16;
				}
			} else if (opdcode == 13) {
				int var3 = stream.readUnsignedByte();
				frameSounds = new int[var3];
				for (int var4 = 0; var4 < var3; ++var4)
				{
					frameSounds[var4] = stream.read24BitInt();
					if (0 != frameSounds[var4]) {
						int var6 = frameSounds[var4] >> 8;
						int var8 = frameSounds[var4] >> 4 & 7;
						int var9 = frameSounds[var4] & 15;
						frameSounds[var4] = var6;
					}
				}
			} else if (opdcode == 14) {
				keyframe_id = stream.readInt();
			} else if (opdcode == 15) {
				int count = stream.readUShort();
				this.keyframe_soundeffects = new HashMap();

				for(int i = 0; i < count; ++i) {
					int var6 = stream.readUShort();
					int var7 = stream.read24BitInt();
					this.keyframe_soundeffects.put(var6, var7);
				}
			} else if (opdcode == 16) {
				keyframe_start = stream.readUShort();
				keyframe_end = stream.readUShort();
			} else if (opdcode == 17) {
				this.mergedbonegroups = new boolean[256];

				for(int i = 0; i < this.mergedbonegroups.length; ++i) {
					this.mergedbonegroups[i] = false;
				}

				int count = stream.readUnsignedByte();

				for(int i = 0; i < count; ++i) {
					this.mergedbonegroups[stream.readUnsignedByte()] = true;
				}
			} else if (opdcode == 127){
			} else System.out.println("Error unrecognised seq config code: " + opdcode);
		}
		if (framecount == 0) {
			framecount = 1;
			frames = new int[1];
			frames[0] = -1;
			secondary_frames = new int[1];
			secondary_frames[0] = -1;
			frame_durations = new int[1];
			frame_durations[0] = -1;
		}
		if (exactmove == -1)
			if (mergedseqgroups != null)
				exactmove = 2;
			else
				exactmove = 0;
		if (movetype == -1) {
			if (mergedseqgroups != null) {
				movetype = 2;
				return;
			}
			movetype = 0;
		}
	}

	public AnimationDefinition() {
		loop_delay = -1;
		stretches = false;
		anInt359 = 5;
		shield = -1;
		weapon = -1;
		replaycount = 99;
		exactmove = -1;
		movetype = -1;
		looptype = 2;
	}

	public static AnimationDefinition anims[];
	public int id;
	public int framecount;
	public int frames[];
	public int secondary_frames[];
	public int frameSounds[];
	public int[] frame_durations;
	public int loop_delay;
	public int mergedseqgroups[];
	public boolean stretches;
	public int anInt359;
	public int shield;
	public int weapon;
	public int replaycount;
	public boolean replay = false;
	public int exactmove;
	public int movetype;
	public int looptype;

	public static void dump() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("./temp/animation_dump.txt"))) {
			for (int index = 0; index < anims.length; index++) {
				AnimationDefinition anim = anims[index];
				if (anim != null) {
					writer.write("\tcase " + index + ":");
					writer.newLine();
					writer.write("\t\tanim.anInt352 = " + anim.framecount + ";");
					writer.newLine();
					writer.write("\t\tanim.anInt356 = " + anim.loop_delay + ";");
					writer.newLine();
					writer.write("\t\tanim.aBoolean358 = " + anim.stretches + ";");
					writer.newLine();
					writer.write("\t\tanim.anInt359 = " + anim.anInt359 + ";");
					writer.newLine();
					writer.write("\t\tanim.shield = " + anim.shield + ";");
					writer.newLine();
					writer.write("\t\tanim.weapon = " + anim.weapon + ";");
					writer.newLine();
					writer.write("\t\tanim.anInt362 = " + anim.replaycount + ";");
					writer.newLine();
					writer.write("\t\tanim.anInt363 = " + anim.exactmove + ";");
					writer.newLine();
					writer.write("\t\tanim.anInt364 = " + anim.movetype + ";");
					writer.newLine();
					writer.write("\t\tanim.anInt352 = " + anim.framecount + ";");
					writer.newLine();
					writeArray(writer, "anIntArray353", anim.frames);
					writeArray(writer, "anIntArray354", anim.secondary_frames);
					writeArray(writer, "frameLengths", anim.frame_durations);
					writeArray(writer, "anIntArray357", anim.mergedseqgroups);
					writeArray(writer, "class36Ids", anim.using_keyframes() ? anim.get_keyframe_fileids() : anim.get_frame_fileids());
					writer.write("\t\tbreak;");
					writer.newLine();
					writer.newLine();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private int[] get_keyframe_fileids() {
		return new int[] {AnimFrame.get_fileid(keyframe_id) };
	}
	private int[] get_frame_fileids() {
		List<Integer> ids = Lists.newArrayList();
		for (int frameId : frames) {
			if (!ids.contains(AnimFrame.get_fileid(frameId))) {
				ids.add(AnimFrame.get_fileid(frameId));
			}
		}
		int[] idsArray = new int[ids.size()];
		for (int index = 0; index < idsArray.length; index++)
			idsArray[index] = ids.get(index);
		return idsArray;
	}

	private static void writeArray(BufferedWriter writer, String name, int[] array) throws IOException {
		writer.write("\t\tanim." + name + " = ");

		if (array == null) {
			writer.write("null;");
		} else {
			writer.write("new int[] {");
			for (int value : array) {
				writer.write(value + ", ");
			}
			writer.write("};");
		}

		writer.newLine();
	}
	public int iftype_frames[];
	private int keyframe_start = -1;
	private int keyframe_end = -1;
	private int keyframe_id = -1;
	public Map<Integer, Integer> keyframe_soundeffects;
	public boolean[] mergedbonegroups;


	public boolean using_keyframes() {
		return this.keyframe_id >= 0;
	}

	public int get_keyframe_duration() {
		return this.keyframe_end - this.keyframe_start;
	}

	public AnimKeyFrameSet get_keyframeset() {
		return this.using_keyframes() ? AnimKeyFrameSet.get_keyframeset(this.keyframe_id) : null;
	}

	public int get_keyframe_id() {
		return keyframe_id;
	}
}