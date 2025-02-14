#pragma once
#ifndef GENRE_BUTTON_H
#define GENRE_BUTTON_H
#include "button.h"

//This class inherits Button and represents a genre button (filter movie by genre)
class Genre_button :public Button {
public:
	Genre_button(float draw_position_x, float draw_position_y, short widget_width,
		short widget_length, const graphics::Brush& br, const std::string& path);
	void draw();
};

#endif