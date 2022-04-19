package my.notalonelyday.oop.draw.ddr.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class ColorPicker extends JButton {

	public ColorPicker(final View view) {
		setFocusPainted(false);
		setContentAreaFilled(false);
		setOpaque(true);

		setBackground(view.getController().getFill_color());

		addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new ColorChoser(view, false);
			}
		});
	}
}
