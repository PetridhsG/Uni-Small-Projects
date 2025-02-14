#include "genre_button.h"

Genre_button::Genre_button(float draw_position_x, float draw_position_y, short widget_width, short widget_length, 
	const graphics::Brush& br,const std::string& path):Button(draw_position_x, draw_position_y, widget_width, widget_length, br, path)
{
}

void Genre_button::draw() {
	Button::draw();			//draw a rectangle like Button
	graphics::Brush br;
	graphics::drawText(draw_position_x-33, draw_position_y+7,18, path, br);	//draw movie genre inside the draw() rectangle
}