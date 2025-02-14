#pragma once
#ifndef DATE_BUTTON_H
#define DATE_BUTTON_H
#include "button.h"

//This class inherits Button and represents a date button (filter Movie by date)

class Date_button:public Button {
public:
	Date_button();
	Date_button(float draw_position_x, float draw_position_y, short widget_width,
		short widget_length, const graphics::Brush& br);
};

#endif