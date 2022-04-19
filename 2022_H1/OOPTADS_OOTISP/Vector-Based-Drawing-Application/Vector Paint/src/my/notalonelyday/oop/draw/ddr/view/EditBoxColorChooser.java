package my.notalonelyday.oop.draw.ddr.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditBoxColorChooser extends ColorChoser {
	CreateDialogue dialogue;

	public EditBoxColorChooser(View view, CreateDialogue dialogue) {
		super(view);
		this.dialogue = dialogue;
		addSelectCancelListeners();
	}

	private void addSelectCancelListeners() {
		getCancel().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		getSelect().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dialogue.setColor(getColor());
				dialogue.setFillColor(getFill_color());
				dispose();
			}
		});
	}
}
