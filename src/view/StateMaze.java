package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

public class StateMaze extends Canvas implements State {		
	int value;
	
	public StateMaze(Composite parent, int style) {
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
	
	@Override
	public void setValue(int value) {
		this.value=value;
		changeBackground(value);
		redraw();
		
	}
	
	@Override
	public void changeBackground(int val) {
		switch(val) {
		case 0: 
			setBackground(getDisplay().getSystemColor(SWT.COLOR_WHITE));
			break;
		case -1:
			setBackground(getDisplay().getSystemColor(SWT.COLOR_BLACK));
			break;
		case 1:
			setBackground(getDisplay().getSystemColor(SWT.COLOR_GREEN));
			break;
	}
			
}
	public StateMaze getState() {
		return this;
	}

}
