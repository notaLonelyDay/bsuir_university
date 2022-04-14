<?php

include 'FormBuilder.php';


$form_builder = new FormBuilder(FormBuilder::METHOD_POST, '/destination.php', 'Send!');
$form_builder->addTextField('name', 'John Doe');
$form_builder->addRadioGroup('RadioGroup', ['A', 'B', 'C']);
$form_builder->addCheckbox('Checkbox', 'Checkbox');
$form_builder->addEmail('email', 'test@gmail.com');
echo $form_builder->getForm();
