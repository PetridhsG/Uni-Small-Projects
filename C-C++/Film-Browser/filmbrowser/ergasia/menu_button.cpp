#include "menu_button.h"
#include "config.h"

Menu_button::Menu_button() {

}

Menu_button::Menu_button(float draw_position_x, float draw_position_y, short widget_width,short widget_length,
	const graphics::Brush &br,const std::string &path):Button(draw_position_x, draw_position_y,widget_width, widget_length, br, path){

}

void Menu_button::draw_menu() {
	graphics::Brush br;			
	br.fill_color[0] = 0.4f;
	br.fill_color[1] = 0.4f;
	br.fill_color[2] = 0.4f;
	br.fill_opacity = 0.8f;
	br.outline_opacity = 1.f;
	br.outline_width = 3.f;
	br.outline_color[0] = 0.f;
	br.outline_color[1] = 0.f;
	br.outline_color[2] = 0.f;
	drawRect(CANVAS_WIDTH / 8, CANVAS_LENGTH / 2, CANVAS_WIDTH /3, CANVAS_LENGTH, br);
}