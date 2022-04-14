<?php

class FormBuilder
{
    const METHOD_POST = 'post';
    const METHOD_GET = 'get';
    const METHOD_PUT = 'put';
    const METHOD_DELETE = 'delete';

    private $form = "";
    private $form_end = "";

    public function __construct($method, $target, $buttonText)
    {
        $this->form = "<form method='$method' action='$target'>";
        $this->form_end = "<button type='submit'>$buttonText</button>";
        $this->form_end .= "</form>";
    }

    public function addTextField($name, $value = "")
    {
        $this->form .= "<input type='text' name='$name' value='$value'>";
    }

    public function addRadioGroup($name, $values, $checked = "")
    {
        foreach ($values as $value) {
            $this->form .= "<input type='radio' name='$name' value='$value'";
            if ($value == $checked) {
                $this->form .= " checked";
            }
            $this->form .= ">$value";
        }
    }

    public function addCheckBox($name, $value, $checked = "")
    {
        $this->form .= "<input type='checkbox' name='$name' value='$value'";
        if ($value == $checked) {
            $this->form .= " checked";
        }
        $this->form .= ">$value";
    }

    public function addEmail($name, $value = "")
    {
        $this->form .= "<input type='email' name='$name' value='$value'>";
    }

    public function addPassword($name, $value = "")
    {
        $this->form .= "<input type='password' name='$name' value='$value'>";
    }

    public function getForm()
    {
        return $this->form . $this->form_end;
    }


}