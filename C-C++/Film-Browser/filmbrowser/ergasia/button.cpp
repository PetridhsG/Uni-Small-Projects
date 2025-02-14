#include "button.h"
#include "config.h"

//this method draw a rectangle,that contains a texture,basis of a brush given
void Button::draw()
{
	br.texture = std::string(ASSET_PATH) + path;
	graphics::Brush brs = br;
	if (highlighted) {					//draw the widget highlighted if highlighted is true
		brs.outline_color[0] = 1.f;
		brs.outline_color[1] = 1.f;
		brs.outline_color[2] = 1.f;
		brs.outline_width = 4.f;
		drawRect(draw_position_x, draw_position_y, widget_width, widget_length, brs);
	}
	else {
		drawRect(draw_position_x, draw_position_y, widget_width, widget_length, br);
	}
	
}

Button::Button( float draw_position_x, float draw_position_y, short widget_width,		//initialize button variables
	short widget_length,const graphics::Brush& br,const std::string& path)
{
	this->widget_width = widget_width;
	this->widget_length = widget_length;
	this->draw_position_x = draw_position_x;
	this->draw_position_y = draw_position_y;
	this->br = br;
	this->path = path;
}

Button::Button() {

}

//this method checks if current mouse coordinates if inside the rectangle that draw() painted
bool Button::contains(float x ,float y) {			
	return ((x > draw_position_x - widget_width / 2) && (x < draw_position_x + widget_width / 2) && (y > draw_position_y - widget_length / 2) && (y < draw_position_y + widget_length / 2));
}

