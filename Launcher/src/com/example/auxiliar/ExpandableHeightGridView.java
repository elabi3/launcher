package com.example.auxiliar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.GridView;

/**
 * ExpandableHeightGridView es la clase para poder reprensentar un GridView en una ScrollView. Hereda de GridView. Clase
 * copiada de internet.
 * 
 * @version 1.0
 * @since 2014-04-08
 */
public class ExpandableHeightGridView extends GridView {

  /**
   * Boolean para determinar si se puede expandir.
   * 
   */
  boolean expanded = true;

  /**
   * Constructor.
   * 
   * @param context
   */
  public ExpandableHeightGridView(Context context) {
    super(context);
  }

  /**
   * Constructor.
   * 
   * @param context
   * @param attrs
   */
  public ExpandableHeightGridView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  /**
   * Constructor.
   * 
   * @param context
   * @param attrs
   * @param defStyle
   */
  public ExpandableHeightGridView(Context context, AttributeSet attrs,
      int defStyle) {
    super(context, attrs, defStyle);
  }

  /**
   * Devuelve si esta en modo expandido.
   * 
   * @return boolean
   */
  public boolean isExpanded() {
    return expanded;
  }

  /**
   * Si esta expandido crea un gridView al máximo de altura, y después lo reduce hasta que se encuentre un elemento,
   * para conseguir la altura minima posible. Si no, usa el onMeasure del padre.
   * 
   * @param widthMeasureSpec
   * @param heightMeasureSpec
   */
  @Override
  public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    // HACK! TAKE THAT ANDROID!
    if (isExpanded()) {
      // Calculate entire height by providing a very large height hint.
      // But do not use the highest 2 bits of this integer; those are
      // reserved for the MeasureSpec mode.
      int expandSpec = MeasureSpec.makeMeasureSpec(
          Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
      super.onMeasure(widthMeasureSpec, expandSpec);

      ViewGroup.LayoutParams params = getLayoutParams();
      params.height = getMeasuredHeight();
    }
    else
      super.onMeasure(widthMeasureSpec, heightMeasureSpec);
  }

  /**
   * Permite cambiar a modo expandido o no.
   * 
   * @param expanded
   */
  public void setExpanded(boolean expanded) {
    this.expanded = expanded;
  }
}