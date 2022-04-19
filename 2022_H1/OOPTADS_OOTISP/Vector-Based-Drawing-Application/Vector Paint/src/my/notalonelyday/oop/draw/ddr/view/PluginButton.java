package my.notalonelyday.oop.draw.ddr.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PluginButton extends CustomButton {
	View view;

	public PluginButton(final View view, String text) {
		super(view, text);
		this.view = view;
		addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new CreatePluginDialogue(view, getText(), view.getController().getDialogue_filters());
			}
		});
	}

}
