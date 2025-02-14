#include "scenes_button.h"
#include "config.h"

Scenes_button::Scenes_button(float draw_position_x, float draw_position_y, short widget_width, short widget_length, const graphics::Brush& br,
	const std::string& path) : Button(draw_position_x, draw_position_y, widget_width, widget_length, br, path)
{
	this->br.outline_opacity = 0.f;		//outline opacity of the rectangle that include arrows must be always zero
}

Scenes_button::Scenes_button()
{
}

//this method checks if the current mouse coordinates is inside the radius of the arrow
bool Scenes_button::contains(float x, float y) {
	return distance(x, y, draw_position_x, draw_position_y) < widget_width / 10;
}

void Scenes_button::setHighlighted(bool x)
{
	highlighted = false;				//arrows cannot be highlighted so highlighted is always false
}
