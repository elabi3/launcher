package com.spaces.controllers.main;

import com.spaces.auxiliar.Prefs;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.support.v4.view.ViewPager;
import android.view.View;

public class ViewPagerTransitions implements ViewPager.PageTransformer {
	public static String[] transitions = { "Default", "Cube Out", "Scale",
			"Tablet", "Rotate Up", "Rotate Down", "Zoom Out Slide"};
	public static final int TRANSITION_DEFAULT = 0;
	public static final int TRANSITION_CUBE_OUT = 1;
	public static final int TRANSITION_SCALE = 2;
	public static final int TRANSITION_TABLET = 3;
	public static final int TRANSITION_ROTATE_UP = 4;
	public static final int TRANSITION_ROTATE_DOWN = 5;
	public static final int TRANSITION_ZOOM_OUT_SLIDE = 6;

	private static final Matrix OFFSET_MATRIX = new Matrix();
	private static final Camera OFFSET_CAMERA = new Camera();
	private static final float[] OFFSET_TEMP_FLOAT = new float[2];

	private static int selected;
	private static Context mContext;

	public ViewPagerTransitions(Context context) {
		mContext = context;
		selected = Prefs.getFromPrefs(mContext, Prefs.PREFS_TRANSITION,
				TRANSITION_DEFAULT);
	}

	public static String getSelectedTransition() {
		return transitions[selected];
	}

	public static void setSelectedTransition(int transition) {
		selected = transition;
		Prefs.saveToPrefs(mContext, Prefs.PREFS_TRANSITION, transition);
	}

	@Override
	public void transformPage(View view, float position) {
		onPreTransform(view, position);
		final float normalizedposition = Math.abs(Math.abs(position) - 1);
		view.setAlpha(normalizedposition);

		switch (selected) {
		case TRANSITION_CUBE_OUT:
			cubeOut(view, position);
			break;
		case TRANSITION_SCALE:
			scale(view, normalizedposition);
			break;
		case TRANSITION_TABLET:
			tablet(view, position);
			break;
		case TRANSITION_ROTATE_UP:
			rotateUp(view, position);
			break;
		case TRANSITION_ROTATE_DOWN:
			rotateDown(view, position);
			break;
		case TRANSITION_ZOOM_OUT_SLIDE:
			zoomOutSlideTransform(view, position);
			break;
		default: {

			break;
		}
		}
	}

	private void onPreTransform(View page, float position) {
		page.setRotationX(0);
		page.setRotationY(0);
		page.setRotation(0);
		page.setScaleX(1);
		page.setScaleY(1);
		page.setPivotX(0);
		page.setPivotY(0);
		page.setTranslationY(0);
		page.setTranslationX(0);
		page.setAlpha(position <= -1f || position >= 1f ? 0f : 1f);
	}

	private void cubeOut(View view, float position) {
		view.setPivotX(position < 0f ? view.getWidth() : 0f);
		view.setPivotY(view.getHeight() * 0.5f);
		view.setRotationY(90f * position);
	}

	private void scale(View view, float position) {
		view.setScaleX(position / 2 + 0.5f);
		view.setScaleY(position / 2 + 0.5f);
	}

	private void tablet(View view, float position) {
		final float rotation = (position < 0 ? 30f : -30f) * Math.abs(position);
		view.setTranslationX(getOffsetXForRotation(rotation, view.getWidth(),
				view.getHeight()));
		view.setPivotX(view.getWidth() * 0.5f);
		view.setPivotY(0);
		view.setRotationY(rotation);
	}

	private void rotateUp(View view, float position) {
		final float width = view.getWidth();
		final float rotation = -15f * position;

		view.setPivotX(width * 0.5f);
		view.setPivotY(0f);
		view.setTranslationX(0f);
		view.setRotation(rotation);
	}

	private void rotateDown(View view, float position) {
		final float width = view.getWidth();
		final float height = view.getHeight();
		final float rotation = -15f * position * -1.25f;

		view.setPivotX(width * 0.5f);
		view.setPivotY(height);
		view.setRotation(rotation);
	}

	private void zoomOutSlideTransform(View view, float position) {
		final float MIN_SCALE = 0.85f;

		if (position >= -1 || position <= 1) {
			// Modify the default slide transition to shrink the page as
			// well
			final float height = view.getHeight();
			final float scaleFactor = Math.max(MIN_SCALE,
					1 - Math.abs(position));
			final float vertMargin = height * (1 - scaleFactor) / 2;
			final float horzMargin = view.getWidth() * (1 - scaleFactor) / 2;

			// Center vertically
			view.setPivotY(0.5f * height);

			if (position < 0) {
				view.setTranslationX(horzMargin - vertMargin / 2);
			} else {
				view.setTranslationX(-horzMargin + vertMargin / 2);
			}

			// Scale the page down (between MIN_SCALE and 1)
			view.setScaleX(scaleFactor);
			view.setScaleY(scaleFactor);
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
