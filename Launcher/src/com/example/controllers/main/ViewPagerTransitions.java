package com.example.controllers.main;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.support.v4.view.ViewPager;
import android.view.View;

public class ViewPagerTransitions implements ViewPager.PageTransformer {
	public static final int TRANSITION_DEFAULT = 0;
	public static final int TRANSITION_CUBE_OUT = 1;
	public static final int TRANSITION_SCALE = 3;
	public static final int TRANSITION_TABLET = 4;
	public static final int TRANSITION_ROTATE_UP = 5;
	public static final int TRANSITION_ROTATE_DOWN = 6;

	private static final Matrix OFFSET_MATRIX = new Matrix();
	private static final Camera OFFSET_CAMERA = new Camera();
	private static final float[] OFFSET_TEMP_FLOAT = new float[2];

	private int selected;

	public ViewPagerTransitions(int transition) {
		this.selected = transition;
	}

	@Override
	public void transformPage(View view, float position) {
		// Alpha
		final float normalizedposition = Math.abs(Math.abs(position) - 1);
		view.setAlpha(normalizedposition);

		switch (selected) {
		case TRANSITION_CUBE_OUT: {
			view.setPivotX(position < 0f ? view.getWidth() : 0f);
			view.setPivotY(view.getHeight() * 0.5f);
			view.setRotationY(90f * position);
			break;
		}
		case TRANSITION_SCALE: {
			view.setScaleX(normalizedposition / 2 + 0.5f);
			view.setScaleY(normalizedposition / 2 + 0.5f);
			break;
		}
		case TRANSITION_TABLET: {
			final float rotation = (position < 0 ? 30f : -30f)
					* Math.abs(position);
			view.setTranslationX(getOffsetXForRotation(rotation,
					view.getWidth(), view.getHeight()));
			view.setPivotX(view.getWidth() * 0.5f);
			view.setPivotY(0);
			view.setRotationY(rotation);
			break;
		}
		case TRANSITION_ROTATE_UP: {
			final float width = view.getWidth();
			final float rotation = -15f * position;

			view.setPivotX(width * 0.5f);
			view.setPivotY(0f);
			view.setTranslationX(0f);
			view.setRotation(rotation);
			break;
		}
		case TRANSITION_ROTATE_DOWN: {
			final float width = view.getWidth();
			final float height = view.getHeight();
			final float rotation = -15f * position * -1.25f;

			view.setPivotX(width * 0.5f);
			view.setPivotY(height);
			view.setRotation(rotation);
			break;
		}
		default: {
			break;
		}
		}
	}

	private static final float getOffsetXForRotation(float degrees, int width,
			int height) {
		OFFSET_MATRIX.reset();
		OFFSET_CAMERA.save();
		OFFSET_CAMERA.rotateY(Math.abs(degrees));
		OFFSET_CAMERA.getMatrix(OFFSET_MATRIX);
		OFFSET_CAMERA.restore();

		OFFSET_MATRIX.preTranslate(-width * 0.5f, -height * 0.5f);
		OFFSET_MATRIX.postTranslate(width * 0.5f, height * 0.5f);
		OFFSET_TEMP_FLOAT[0] = width;
		OFFSET_TEMP_FLOAT[1] = height;
		OFFSET_MATRIX.mapPoints(OFFSET_TEMP_FLOAT);
		return (width - OFFSET_TEMP_FLOAT[0]) * (degrees > 0.0f ? 1.0f : -1.0f);
	}
}
