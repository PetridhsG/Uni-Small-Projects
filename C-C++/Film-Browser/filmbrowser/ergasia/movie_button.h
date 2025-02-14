#pragma once
#ifndef MOVIE_BUTTON_H
#define MOVIE_BUTTON_H
#include "button.h"
#include "movie.h"

//This class represents a Movie object 
class Movie_button : public Button {
	Movie movie;
public:
	Movie_button();
	Movie_button(float draw_position_x, float draw_position_y, short widget_width,
		short widget_length, const graphics::Brush& br, const Movie& movie);
	void draw();
	void change_movie(const Movie& movie);		//change current movie
	void drawInfo();							//draw current movie info
};
#endif