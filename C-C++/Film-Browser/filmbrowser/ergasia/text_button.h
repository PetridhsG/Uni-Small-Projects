#pragma once
#ifndef TEXT_BUTTON_H
#define TEXT_BUTTON_H
#include "button.h"

//This class inherits Button and represents a text field 
class Text_button :public Button {
public:
	Text_button();
	Text_button(float draw_position_x, float draw_position_y, short widget_width,
		short widget_length, const graphics::Brush& br);
};

#endif