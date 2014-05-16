package com.travellog;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

public class IconTableView extends TableLayout {
	ArrayList<ImageView> icons;
	Context context;
	
	public IconTableView(Context context) {
		super(context);
		this.context = context;
		System.out.println("icon table view starts");
		addRows();
		setIcons();
		setIconSizes();
		System.out.println("set icon stuff");
		System.out.println("number of icons"+icons.size());
	
		//this.addView(findViewById(R.id.tableRow1));
		//this.addView(findViewById(R.id.tableRow2));
	}
	
	public void addRows() {
		TableRow row1 = new TableRow(context);
		ImageView camera = new ImageView(context);
		row1.addView(camera);
	}
	
	public IconTableView(Context context, AttributeSet attrs) {
		super(context);
		this.context = context;
		System.out.println("icon table view starts");
		addRows();
		setIcons();
		setIconSizes();
		System.out.println("set icon stuff");
		System.out.println("number of icons"+icons.size());
		//this.addView(findViewById(R.id.tableRow1));
		//this.addView(findViewById(R.id.tableRow2));
	}
	
	/*public IconTableView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}*/
	
	public void setIcons() {
		icons = new ArrayList<ImageView>();
		//IconTableView v = (IconTableView) ((Activity) context).findViewById(R.id.icon_table);
		int table_children_count =  getChildCount();
		//int table_children_count = 0;
		System.out.println("table children count is"+table_children_count);
		
		for(int i = 0; i < table_children_count; i++){
			TableRow row = (TableRow) getChildAt(i);
			int row_child_count = row.getChildCount();
			for(int j = 0; j < row_child_count; j++){
				if(row.getChildAt(j).getClass() == ImageView.class)
					icons.add((ImageView) row.getChildAt(j));
			}
		
		}
		
		//hard coding b/c above is not working
		//Activity parent = (Activity) context;
		//icons.add((ImageView) ((TableRow ) parent.findViewById(R.id.tableRow1)).getChildAt(0));
	}
	
	//to keep icons square and in different screen sizes
	public void setIconSizes() {
		Activity activity = (Activity) context;
		Display display = activity.getWindowManager().getDefaultDisplay(); 
		int screen_width = display.getWidth();
		if(icons.size() > 0) {
		int target_image_width = (screen_width-100) * this.getChildCount() / icons.size();
		for(int i = 0; i < icons.size(); i++) {
			icons.get(i).getLayoutParams().width = target_image_width;
		}
	}
	}

}
