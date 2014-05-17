package ch.zahw.students.sudokuhelper.solve;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TableLayout;

public class Tabletest extends View {
	  public Tabletest(Context context) {
	    super(context);
	  }

	  public Tabletest(Context context, AttributeSet attrs) {
	    super(context, attrs);
	  }

	  public Tabletest(Context context, AttributeSet attrs, int defStyle) {
	    super(context, attrs, defStyle);
	  }

	  public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	    int size = Math.min(getMeasuredWidth(), getMeasuredHeight());
	    setMeasuredDimension(size, size);
	  }
	}