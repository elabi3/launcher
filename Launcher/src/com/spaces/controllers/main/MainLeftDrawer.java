package com.spaces.controllers.main;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.spaces.auxiliar.ActionsIntents;
import com.spaces.auxiliar.ContactsManager;
import com.spaces.auxiliar.ContactsManager.People;
import com.spaces.controllers.Settings;
import com.spaces.launcher.R;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainLeftDrawer implements OnClickListener {
    private DrawerLayout mLayout;
	private Context mContext;
	private Handler mHandler = new Handler();

	private List<TextView> textViews;
	private int[] textViewsIds = { R.id.senEmail, R.id.newEvent, R.id.newAlarm,
			R.id.settings };

	private List<LinearLayout> linearLayouts;
	private int[] linearLayoutsIds = { R.id.contact_0, R.id.contact_1,
			R.id.contact_2, R.id.contact_3, R.id.contact_4, R.id.contact_5 };

	private List<People> people;

	public MainLeftDrawer(DrawerLayout layout, Context context) {
		this.mLayout = layout;
		this.mContext = context;
		setupElements();
		setupContacts();
	}

	private void setupElements() {
		textViews = new ArrayList<TextView>();
		for (int i = 0; i < textViewsIds.length; i++) {
			textViews.add((TextView) mLayout.findViewById(textViewsIds[i]));
			textViews.get(i).setOnClickListener(this);
		}
		ImageView image = (ImageView) mLayout.findViewById(R.id.new_phone_call);
		image.setOnClickListener(this);
	}

	private void setupContacts() {
		linearLayouts = new ArrayList<LinearLayout>();
		for (int i = 0; i < linearLayoutsIds.length; i++) {
			linearLayouts.add((LinearLayout) mLayout
					.findViewById(linearLayoutsIds[i]));
			linearLayouts.get(i).setOnClickListener(this);
		}

		// FavoriteContacts
		ContactsManager cM = new ContactsManager(mContext);
		people = cM.favoritesContacts();
        if (people == null || people.size() < 6) {
            cM.recentsContacts();
        }

        if (people != null && people.size() > 0) {
            int i = 0;
            for (People p : people) {
                ((TextView) linearLayouts.get(i).getChildAt(1)).setText(p.name);
                if (p.photo != null) {
                    InputStream image_stream = null;
                    try {
                        image_stream = mContext.getContentResolver().openInputStream(Uri.parse(p.photo));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Bitmap bitmap = BitmapFactory.decodeStream(image_stream);
                    ((ImageView) linearLayouts.get(i).getChildAt(0)).setImageBitmap(getCircleBitmap(bitmap));
                }
                i++;
            }
        }
	}

    private Bitmap getCircleBitmap(Bitmap bm) {
        int sice = Math.min((bm.getWidth()), (bm.getHeight()));

        Bitmap bitmap = ThumbnailUtils.extractThumbnail(bm, sice, sice);
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(output);

        final int color = 0xffff0000;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setFilterBitmap(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);

        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth((float) 4);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.senEmail: {
			ActionsIntents.senEmail(mContext);
			break;
		}
		case R.id.newEvent: {
			ActionsIntents.newEvent(mContext);
			break;
		}
		case R.id.newAlarm: {
			ActionsIntents.newAlarm(mContext);
			break;
		}
		case R.id.new_phone_call: {
			ActionsIntents.newPhoneCall(mContext);
			break;
		}
		case R.id.settings: {
			mContext.startActivity(new Intent(mContext, Settings.class));
			break;
		}
		default: {
			int position = -1;
			for (int i = 0; i < linearLayoutsIds.length; i++) {
				if (v.getId() == linearLayoutsIds[i]) {
					position = i;
				}
			}
			openPerson(position);
			break;
		}
		}
		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				mLayout.closeDrawers();
			}
		}, 200);
	}

	private void openPerson(int position) {
		if (position == -1) {
			return;
		}

		final Dialog dialog = new Dialog(mContext);
		// hide to default title for Dialog
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
		dialog.setCanceledOnTouchOutside(true);

		// inflate the layout dialog_layout.xml and set it as contentView
		LayoutInflater inflater = LayoutInflater.from(mContext);
		View view = inflater.inflate(
				R.layout.controllers_main_left_drawer_contact_detail, null,
				false);
		
		((TextView) view.findViewById(R.id.personName))
				.setText(people.get(position).name);

        if (people.get(position).photo != null) {
            InputStream image_stream = null;
            try {
                image_stream = mContext.getContentResolver().openInputStream(Uri.parse(people.get(position).photo));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Bitmap bitmap= BitmapFactory.decodeStream(image_stream);
            ((ImageView) view.findViewById(R.id.personIcon)).setImageBitmap(getCircleBitmap(bitmap));
        }

		dialog.setContentView(view);
		dialog.show();
	}

}
