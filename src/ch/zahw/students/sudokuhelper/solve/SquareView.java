package ch.zahw.students.sudokuhelper.solve;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;

public class SquareView extends LinearLayout {
	public SquareView(Context context) {
		super(context);
	}

	public SquareView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	

	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int size = Math.min(getMeasuredWidth(), getMeasuredHeight());
		setMeasuredDimension(size, size);
	}
}