package my.notalonelyday.oop.draw.ddr.view;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import my.notalonelyday.oop.draw.ddr.model.ShapesFactory;

public class CreatePluginDialogue extends CreateDialogue {
	public CreatePluginDialogue(View view, String text, ArrayList<String> filters) {
		super(view, text, filters);
	}

	@Override
	public void addPosition() {
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Position - X");
		//JTextField textfield = new JTextField(5);
		NumberFormat numFormat = NumberFormat.getNumberInstance();
		numFormat.setMaximumFractionDigits(3);
		JTextField textfield = new JFormattedTextField(numFormat);
		textfield.setColumns(5);
		getLabels().add(label);
		panel.add(label);
		getTextFields().add(textfield);
		panel.add(textfield);
		getContentPane().add(panel);
		panel = new JPanel();
		label = new JLabel("Position - Y");
		//textfield = new JTextField(5);
		textfield = new JFormattedTextField(numFormat);
		textfield.setColumns(5);
		getLabels().add(label);
		panel.add(label);
		getTextFields().add(textfield);
		panel.add(textfield);
		getContentPane().add(panel);
	}

	@Override
	public void addButtonListener() {
		getDraw().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				get_Shape().setPosition(
						new Point(new Double(Double.parseDouble(getTextFields().get(0).getText())).intValue(),
								new Double(Double.parseDouble(getTextFields().get(1).getText())).intValue()));
				get_Shape().getProperties().put("position-x",
						new Double(Double.parseDouble(getTextFields().get(0).getText())));
				get_Shape().getProperties().put("position-y",
						new Double(Double.parseDouble(getTextFields().get(1).getText())));
				get_Shape().setColor(getColor());
				get_Shape().setFillColor(getFill_color());
				get_Shape().getProperties().put("color", getColor().getRGB() * 1.0);
				get_Shape().getProperties().put("fill_color", getFill_color().getRGB() * 1.0);
				int i = 2;
				for (String x : getSet()) {
					if (!getFilters().contains(x)) {
						get_Shape().getProperties().put(x, Double.parseDouble(getTextFields().get(i).getText()));
						i++;
					}
				}
				get_Shape().getProperties().put("selected", 0.0);
				getView().getController().draw(get_Shape());
				dispose();

			}
		});

	}

	@Override
	public void setupKeySetandShape() {
		setShape(ShapesFactory.CreateShape(getClass_text()));
		setSet(get_Shape().getProperties().keySet());
	}
}
