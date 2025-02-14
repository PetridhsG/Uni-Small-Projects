#pragma once
#ifndef BUTTON_H
#define BUTTON_H
#include "widget.h"

//This class inherits Widget and represents a button 

class Button :public Widget {
public:
	virtual void draw();															//this method may change for differents buttons
	virtual bool contains(float x, float y);										//this method may change for differents buttons
	Button(float draw_position_x, float draw_position_y,short widget_width,			//Button constructor
		short widget_length,const graphics::Brush& br,const std::string& path);
	Button();																		//Button default constructor
};

#endif