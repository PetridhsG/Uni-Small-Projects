#include "text_button.h"

Text_button::Text_button()
{
}

Text_button::Text_button(float draw_position_x, float draw_position_y, short widget_width, short widget_length,
	const graphics::Brush& br):Button(draw_position_x, draw_position_y, widget_width, widget_length, br, path)
{
}
