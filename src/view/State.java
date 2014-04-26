package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public class State extends Canvas {		
	int value;
	
	public State(Composite parent, int style) {
		super(parent,style);		
		value=0;
		Font font = new Font(getDisplay(), getFont().getFontData()[0].getName(),14,SWT.BORDER);
		setFont(font);		
		
		addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {				
				int stringWidth = e.gc.getFontMetrics().getAverageCharWidth();
				int x = (getSize().x)/2  - (("" + value).length())*stringWidth/2;
				int y = (getSize().y)/2 - (("" + value).length())*stringWidth/2;
				e.gc.drawString("" + value, x, y);				
			}			
		});
	}
		
	public void setValue(int value) {
		this.value=value;
		changeBackground(value);
		redraw();
		
	}
	
	private void changeBackground(int value) {
		switch(value) {
		case 0: 
			setBackground(getDisplay().getSystemColor(SWT.COLOR_DARK_GRAY));
		case 2:
			setBackground(getDisplay().getSystemColor(SWT.COLOR_GRAY));
		case 4:
			setBackground(getDisplay().getSystemColor(SWT.COLOR_CYAN));
		case 8: 
			setBackground(getDisplay().getSystemColor(SWT.COLOR_YELLOW));
		case 16:
			setBackground(getDisplay().getSystemColor(SWT.COLOR_DARK_MAGENTA));
		case 64:
			setBackground(getDisplay().getSystemColor(SWT.COLOR_RED));
		case 128:
			setBackground(getDisplay().getSystemColor(SWT.COLOR_CYAN));
		case 256:
		case 512:
		case 1024:
		case 2048:
	}
			
}
	public State getState() {
		return this;
	}

}
