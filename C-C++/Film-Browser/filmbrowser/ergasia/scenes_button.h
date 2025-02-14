#pragma once
#ifndef SCENES_BUTTON_H
#define SCENES_BUTTON_H
#include "button.h"

//This class inherits Button and represents a scene button (click on arrows on the right corner to change movie scene)
class Scenes_button : public Button {
public:
	Scenes_button();
	Scenes_button(float draw_position_x, float draw_position_y, short widget_width, short widget_length,const graphics::Brush& br,const std::string& path);
	bool contains(float x, float y);
	void setHighlighted(bool x);
};

#endif 