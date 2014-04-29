package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public class State extends Canvas {		
	int value=0;
	
	public State(Composite parent, int style) {
		super(parent,style);				
		Font font = new Font(getDisplay(), getFont().getFontData()[0].getName(),26,SWT.BORDER);
		setFont(font);		
		
		addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {				
				int stringWidth = e.gc.getFontMetrics().getAverageCharWidth();
				int x = (getSize().x)/2  - (("" + value).length())*stringWidth/2;
				int y = (getSize().y)/2 - (("" + value).length())*stringWidth/2;
				if (value > 0 )
					e.gc.drawString("" + value, x, y);				
			}			
		});
	}
	

	public void setValue(int value) {
		this.value=value;
		changeBackground(value);
		redraw();
		
	}
	
	
	public void changeBackground(int val) {
		switch(val) {
		case 0: 
			Color bgColor0 = new Color(getDisplay(),238, 228, 218);
			setBackground(bgColor0);
			break;
		case 2:
			Color bgColor2 = new Color(getDisplay(),238,228,218);
			setBackground(bgColor2);	
			break;
		case 4:
			Color bgColor4 = new Color(getDisplay(),237,224,200);
			setBackground(bgColor4);	
			break;
		case 8: 
			Color bgColor8 = new Color(getDisplay(),242, 177, 121);
			setBackground(bgColor8);	
			break;
		case 16:
			Color bgColor16 = new Color(getDisplay(),245, 149, 99);
			setBackground(bgColor16);	
			break;
		case 32:
			Color bgColor32 = new Color(getDisplay(),246, 124, 95);
			setBackground(bgColor32);	
			break;
		case 64:
			Color bgColor64 = new Color(getDisplay(),246, 94, 59);
			setBackground(bgColor64);	
			break;
		case 128:
			Color bgColor128 = new Color(getDisplay(),237, 207, 114);
			setBackground(bgColor128);	
			break;
		case 256:
			Color bgColor256 = new Color(getDisplay(),237, 204, 97);
			setBackground(bgColor256);	
			break;
		case 512:
			Color bgColor512 = new Color(getDisplay(),237, 200, 80);
			setBackground(bgColor512);	
			break;
		case 1024:
			Color bgColor1024 = new Color(getDisplay(),237, 197, 63);
			setBackground(bgColor1024);	
			break;
		case 2048:
			Color bgColor2048 = new Color(getDisplay(),237, 194, 46);
			setBackground(bgColor2048);	
			break;
		case -1:
			setBackground(getDisplay().getSystemColor(SWT.COLOR_BLACK));
			break;
		case 1:
			setBackground(getDisplay().getSystemColor(SWT.COLOR_GREEN));
			break;
		}
	}
	
	public State getState() {
		return this;
	}

}
