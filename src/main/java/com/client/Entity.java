package com.client;

import com.client.definitions.AnimationDefinition;
import com.client.sound.Sound;
import com.client.sound.SoundType;

public class Entity extends Renderable {

	public boolean instant_facing;

	public boolean isLocalPlayer() {
		return this == Client.myPlayer;
	}

	public int getAbsoluteX() {
		int x = Client.baseX + (this.x - 6 >> 7);
		if (this instanceof NPC) {
			return x - ((NPC) this).desc.size / 2;
		}
		return x;
	}

	public int getAbsoluteY() {
		int y = Client.baseY + (this.y - 6 >> 7);
		if (this instanceof NPC) {
			return y - ((NPC) this).desc.size / 2;
		}
		return y;
	}

	public int getDistanceFrom(Entity entity) {
		return getDistanceFrom(entity.getAbsoluteX(), entity.getAbsoluteY());
	}

	public int getDistanceFrom(int x2, int y2) {
		int x = (int) Math.pow(getAbsoluteX() - x2, 2.0D);
		int y = (int) Math.pow(getAbsoluteY() - y2, 2.0D);
		return (int) Math.floor(Math.sqrt(x + y));
	}

	public void makeSound(int soundId) {
		double distance = getDistanceFrom(Client.myPlayer);
//		if (Configuration.developerMode) {
//			System.out.println("entity sound: id " + id + " x" + getAbsoluteX() + " y" + getAbsoluteY() + " d" + distance);
//		}
		Sound.getSound().playSound(soundId, isLocalPlayer() || this instanceof NPC ? SoundType.SOUND : SoundType.AREA_SOUND, distance);
	}

	public final void setPos(int i, int j, boolean flag) {
		if (primaryanim != -1 && AnimationDefinition.anims[primaryanim].movetype == 1)
			primaryanim = -1;
		if (!flag) {
			int k = i - waypoint_x[0];
			int l = j - waypoint_y[0];
			if (k >= -8 && k <= 8 && l >= -8 && l <= 8) {
				if (waypoint_count < 9)
					waypoint_count++;
				for (int i1 = waypoint_count; i1 > 0; i1--) {
					waypoint_x[i1] = waypoint_x[i1 - 1];
					waypoint_y[i1] = waypoint_y[i1 - 1];
					aBooleanArray1553[i1] = aBooleanArray1553[i1 - 1];
				}

				waypoint_x[0] = i;
				waypoint_y[0] = j;
				aBooleanArray1553[0] = (byte)1;
				return;
			}
		}
		waypoint_count = 0;
		anim_delay = 0;
		walkanim_pause = 0;
		waypoint_x[0] = i;
		waypoint_y[0] = j;
		x = waypoint_x[0] * 128 + anInt1540 * 64;
		y = waypoint_y[0] * 128 + anInt1540 * 64;
	}

	public final void method446() {
		waypoint_count = 0;
		anim_delay = 0;
	}

	public final void updateHitData(int j, int k, int l) {
		for (int i1 = 0; i1 < 4; i1++)
			if (hitsLoopCycle[i1] <= l) {
				hitArray[i1] = k;
				hitMarkTypes[i1] = j;
				hitsLoopCycle[i1] = l + 70;
				return;
			}
	}

	public final void moveInDir(byte flag, int i) {
		int j = waypoint_x[0];
		int k = waypoint_y[0];
		if (i == 0) {
			j--;
			k++;
		}
		if (i == 1)
			k++;
		if (i == 2) {
			j++;
			k++;
		}
		if (i == 3)
			j--;
		if (i == 4)
			j++;
		if (i == 5) {
			j--;
			k--;
		}
		if (i == 6)
			k--;
		if (i == 7) {
			j++;
			k--;
		}
		if (primaryanim != -1 && AnimationDefinition.anims[primaryanim].movetype == 1)
			primaryanim = -1;
		if (waypoint_count < 9)
			waypoint_count++;
		for (int l = waypoint_count; l > 0; l--) {
			waypoint_x[l] = waypoint_x[l - 1];
			waypoint_y[l] = waypoint_y[l - 1];
			aBooleanArray1553[l] = aBooleanArray1553[l - 1];
		}
		waypoint_x[0] = j;
		waypoint_y[0] = k;
		aBooleanArray1553[0] = flag;
	}

	public int entScreenX;
	public int entScreenY;

	public boolean isVisible() {
		return false;
	}

	Entity() {
		waypoint_x = new int[10];
		waypoint_y = new int[10];
		interactingEntity = -1;
		turnspeed = 32;
		runanim = -1;
		height = 200;
		readyanim = -1;
		readyanim_l = -1;
		hitArray = new int[4];
		hitMarkTypes = new int[4];
		hitsLoopCycle = new int[4];
		secondaryanim = -1;
		secondaryanim_loops_remaining = -1;
		primaryanim = -1;
		loopCycleStatus = -1000;
		textCycle = 100;
		anInt1540 = 1;
		is_walking = false;
		aBooleanArray1553 = new byte[10];
		anInt1554 = -1;
		anInt1555 = -1;
		anInt1556 = -1;
		anInt1557 = -1;
	}

	public final int[] waypoint_x;
	public final int[] waypoint_y;
	public int interactingEntity;
	int walkanim_pause;
	int turnspeed;
	int runanim;
	public String textSpoken;
	public String lastForceChat;
	public int height;
	private int turnDirection;
	int readyanim;
	int readyanim_l;
	int readyanim_r;
	int runanim_b = -1;
	int runanim_r = -1;
	int runanim_l = -1;
	int crawlanim = -1;
	int crawlanim_b = -1;
	int crawlanim_l = -1;
	int crawlanim_r = -1;
	int anInt1513;
	final int[] hitArray;
	final int[] hitMarkTypes;
	final int[] hitsLoopCycle;
	int secondaryanim;
	int secondaryanim_replaycount;
	int secondaryanim_frameindex;
	int secondaryanim_loops_remaining;
	int spotanim;
	int spotanimframe_index;
	int spotanim_loop;
	int spotanim_start_loop;
	int spotanim_height;
	int waypoint_count;
	public int primaryanim;
	int primaryanim_frameindex;
	int primaryanim_loops_remaining;
	int primaryanim_pause;
	int primaryanim_replaycount;
	int anInt1531;
	public int loopCycleStatus;
	public int currentHealth;
	public int maxHealth;
	int textCycle;
	int anInt1537;
	int anInt1538;
	int anInt1539;
	int anInt1540;
	boolean is_walking;
	int anim_delay;
	int anInt1543;
	int anInt1544;
	int anInt1545;
	int anInt1546;
	int exactmove_start;
	int exactmove_end;
	int forceMovementDirection;
	public int x;
	public int y;
	int anInt1552;
	final byte[] aBooleanArray1553;
	int anInt1554;
	int anInt1555;
	int anInt1556;
	int anInt1557;

	public int getTurnDirection() {
		return turnDirection;
	}

	public void setTurnDirection(int turnDirection) {
		this.turnDirection = turnDirection;
	}
}
