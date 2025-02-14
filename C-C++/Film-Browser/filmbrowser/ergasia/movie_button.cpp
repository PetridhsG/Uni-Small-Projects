#include "movie_button.h"
#include "config.h"
Movie_button::Movie_button() {
				
}

Movie_button::Movie_button(float draw_position_x, float draw_position_y, short widget_width, short widget_length,
	const graphics::Brush & br, const Movie &movie) :Button(draw_position_x, draw_position_y,widget_width,  widget_length, br, path) {
	this->movie = movie;
}

void Movie_button::draw() {
	Button::draw();
	br.texture = std::string(ASSET_PATH) + movie.getMoviePath() + "\\" + movie.getMoviePath() + ".png";	 //draw the texture of current movie	
	drawRect(draw_position_x, draw_position_y,widget_width, widget_length, br);
}

void Movie_button::drawInfo() {
	br.outline_opacity = 0.7f;
	br.outline_width = 2.0f;
	br.outline_color[0] = 0.f;
	br.outline_color[1] = 0.f;
	br.outline_color[2] = 0.f;

	br.fill_color[0] = 1.f;
	br.fill_color[1] = 1.f;
	br.fill_color[2] = 1.f;

	drawText(CANVAS_WIDTH / 2 - 450, CANVAS_LENGTH / 2, 35, movie.getMovieTitle(), br);	//print movie Title
	drawText(CANVAS_WIDTH / 2 - 450, CANVAS_LENGTH / 2 + 25, 18, "Year:" + to_string(movie.getMovieYear()), br);	//print movie Year
	drawText(CANVAS_WIDTH / 2 - 450, CANVAS_LENGTH / 2 + 50, 18, "Rating:" + to_string(movie.getMovieRating()).substr(0, 3), br);	//print movie Rating
	drawText(CANVAS_WIDTH / 2 - 450, CANVAS_LENGTH / 2 + 75, 18, "Duration:" + to_string(movie.getMovieDuration()) + "min", br);	//print movie Duration
	string x;
	short j = 0;
	for (auto const& i : movie.getMovieGenres()) {		//create a string that contains all movie genres
		j++;
		if (j == 1) {
			x += i;
		}
		else {
			x += "," + i;
		}

	}
	drawText(CANVAS_WIDTH / 2 - 450, CANVAS_LENGTH / 2 + 100, 18, "Genres:" + x, br);	//print movie Genres
	x.clear();
	j = 0;
	for (auto const& i : movie.getMovieDirectors()) {	//create a string that contains all movie Directors
		j++;
		if (j == 1) {
			x += i;
		}
		else {
			x += "," + i;
		}
	}
	drawText(CANVAS_WIDTH / 2 - 450, CANVAS_LENGTH / 2 + 125, 18, "Directors:" + x, br);	//print movie Directors
	x.clear();
	j = 0;
	for (auto const& i : movie.getMovieStars()) {		//create a string that contains all movie Stars
		j++;
		if (j == 1) {
			x += i;
		}
		else {
			x += "," + i;
		}
	}
	drawText(CANVAS_WIDTH / 2 - 450, CANVAS_LENGTH / 2 + 150, 18, "Stars:" + x, br);	//print movie Stars
	short space_counter = 0;
	short char_counter = 0;
	short count = 0;
	string st;
	string s = movie.getMovieSummary();
	short line = 175;
	for (short i = 0; i < movie.getMovieSummary().size(); i++) {		//split summary string,draw the substring and change line
		if (s[char_counter++] == ' ') {
			space_counter++;
		}
		if (space_counter == 10) {
			count++;
			space_counter = 0;
			st = s.substr(0, char_counter);
			s = s.substr(char_counter, s.size());
			if (count == 1) {
				drawText(CANVAS_WIDTH / 2 - 450, CANVAS_LENGTH / 2 + line, 18, "Summary:" + st, br);
			}
			else {
				drawText(CANVAS_WIDTH / 2 - 450, CANVAS_LENGTH / 2 + line, 18, st, br);
			}
			char_counter = 0;
			line += 20;
		}
	}
	drawText(CANVAS_WIDTH / 2 - 450, CANVAS_LENGTH / 2 + line, 18, s, br);
}


void Movie_button::change_movie(const Movie& movie) {
	this->movie = movie;
}

