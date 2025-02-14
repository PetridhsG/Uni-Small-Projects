#pragma once
#ifndef WIDGET_H
#define WIDGET_H
#include <graphics.h>
#include <string>

//Abstract class widget that will represent a generic GUI element that will define common methods and variables etc.draw,contains

class Widget {
protected:
	bool active = false;
	bool highlighted = false;	//if a widget is highlighted
	short widget_width;			//these two variables represent
	short widget_length;		//the size of the widget
	float draw_position_x;		//these two vairables represent
	float draw_position_y;		//the center of the widget
	graphics::Brush br;			//this brush will paint the widget
	std::string path;			//texture path
public:
	void setActive(bool x) { active = x; }
	bool isActive() { return active; }
	std::string getPath() { return path; }          			//this method returns the texture path of a button
	float getPositionX() { return draw_position_x; }			//this method return the X position of a widget
	void setPositionX(float x) { draw_position_x = x; }		    //this method changes the X position of a widget
	void setHighlighted(bool x) { highlighted = x; }	        //this method sets highlight to the widget
	virtual void draw() = 0;									//this method paints the widget in the Browsing Screen
	virtual bool contains(float x, float y) = 0;				//this method checks if the mouse is in the draw area of the widget
};

#endif 