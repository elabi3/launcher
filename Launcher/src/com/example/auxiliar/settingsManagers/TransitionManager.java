package com.example.auxiliar.settingsManagers;

import com.jfeinstein.jazzyviewpager.JazzyViewPager.TransitionEffect;

public class TransitionManager {
	private static TransitionManager instance;
	private TransitionEffect currentEffect = TransitionEffect.Standard;

	public static TransitionManager getInstance() {
		if (instance == null) {
			instance = new TransitionManager();
		}
		return instance;
	}

	public CharSequence[] getAviableEffects() {
		final CharSequence[] items = new CharSequence[TransitionEffect.values().length];
		int i = 0;
		for (TransitionEffect transitionEffect : TransitionEffect.values()) {
			items[i++] = transitionEffect.name();
		}
		return items;
	}
	
	public TransitionEffect getSelectedEffect() {
		return currentEffect;
	}

	public void setSelectedEffect(TransitionEffect newEffect) {
		currentEffect = newEffect;
	}
}
