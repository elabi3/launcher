package com.example.controllers.main;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.support.v4.view.ViewPager;
import android.view.View;

public class ViewPagerTransitions implements ViewPager.PageTransformer {
	public static String[] transitions = { "Default", "Cube Out", "Scale",
			"Tablet", "Rotate Up", "Rotate Down", "Zoom Out Slide",
			"Depth Page" };
	public static final int TRANSITION_DEFAULT = 0;
	public static final int TRANSITION_CUBE_OUT = 1;
	public static final int TRANSITION_SCALE = 2;
	public static final int TRANSITION_TABLET = 3;
	public static final int TRANSITION_ROTATE_UP = 4;
	public static final int TRANSITION_ROTATE_DOWN = 5;
	public static final int TRANSITION_ZOOM_OUT_SLIDE = 6;
	public static final int TRANSITION_DEPTH_PAGE = 7;

	private static final Matrix OFFSET_MATRIX = new Matrix();
	private static final Camera OFFSET_CAMERA = new Camera();
	private static final float[] OFFSET_TEMP_FLOAT = new float[2];

	private static int selected = TRANSITION_DEFAULT;

	public ViewPagerTransitions() {

	}

	public static String getSelectedTransition() {
		return transitions[selected];
	}
	
	public static void setSelectedTransition(int transition) {
		selected = transition;
	}

	@Override
	public void transformPage(View view, float position) {
		onPreTransform(view, position);
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
		case TRANSITION_ZOOM_OUT_SLIDE: {
			zoomOutSlideTransform(view, position);
			break;
		}
		case TRANSITION_DEPTH_PAGE: {
			depthPageTransform(view, position);
			break;
		}
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

	private void depthPageTransform(View view, float position) {
		final float MIN_SCALE = 0.75f;

		if (position <= 0f) {
			view.setTranslationX(0f);
			view.setScaleX(1f);
			view.setScaleY(1f);
		} else if (position <= 1f) {
			final float scaleFactor = MIN_SCALE + (1 - MIN_SCALE)
					* (1 - Math.abs(position));
			view.setAlpha(1 - position);
			view.setPivotY(0.5f * view.getHeight());
			view.setTranslationX(view.getWidth() * -position);
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
