#pragma once
#ifndef MENU_BUTTON_H
#define MENU_BUTTON_H
#include "button.h"

//This class inherits Button and represents a menu button (click on menu button to apply filters)
class Menu_button : public Button {
public:
	Menu_button();
	Menu_button(float draw_position_x, float draw_position_y, short widget_width, short widget_length,
				const graphics::Brush & br,const std::string &path);
	void draw_menu();	//this method draw menu background
};

#endif 