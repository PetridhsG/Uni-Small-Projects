#include "app.h"
#include "config.h"
#include <fstream>
#include <iostream>
#include "scenes_button.h"
#include "menu_button.h"
#include "genre_button.h"
#include "date_button.h"
#include "text_button.h"

using namespace std;
void App::draw()
{
	if (status == INIT) {
		drawLoadingScreen();
	}
	else if (status == BROWSING) {
		drawBrowsingScreen();
	}
	else if (status == SEARCHING) {
		drawSearchingScreen();
	}
}

void App::update()
{
	if (status == INIT) {
		updateLoadingScreen();
	}
	else if (status == BROWSING) {
		updateBrowsingScreen();
	}
	else if (status == SEARCHING) {
		updateSearchingScreen();
	}
}

void App:: read() {
	fstream is(std::string(ASSET_PATH) + "movies.txt");
	fstream newfile;
	newfile.open(string(ASSET_PATH) + "movies.txt", ios::in);
	if (newfile.is_open()) {
		string tp;
		short counter = 0;
		short duration;
		short year = 0;
		float rating;
		list<string> stars;
		list<string> directors;
		list<string> genres;
		string title;
		string summary;
		string path;
		string split_char = ",";		//seperate genres,stars,directors by comma
		int i = 0;
		while (getline(newfile, tp)&&(!tp.empty())) {
			if (counter == 1) {				
				title = tp;
			}
			else if (counter == 2) {
				year = stoi(tp);
			}
			else if (counter == 3) {
				size_t position = 0;
				string token;
				while ((position = tp.find(split_char)) != string::npos) {
					token = tp.substr(0, position);
					stars.push_back(token);
					tp.erase(0, position + split_char.length());
				}
			}
			else if (counter == 4) {
				size_t position = 0;
				string token;
				while ((position = tp.find(split_char)) != string::npos) {
					token = tp.substr(0, position);
					directors.push_back(token);
					tp.erase(0, position + split_char.length());
				}
			}
			else if (counter == 5) {
				size_t position = 0;
				string token;
				while ((position = tp.find(split_char)) != string::npos) {
					token = tp.substr(0, position);
					genres.push_back(token);
					tp.erase(0, position + split_char.length());
				}
			}
			else if (counter == 6) {
				duration = stoi(tp);
			}
			else if (counter == 7) {
				rating = stoi(tp);
			}
			else if (counter == 8) {
				summary = tp;
			}
			else if (counter == 9) {
				path = tp;
			}
			counter++;
			if (counter == 10) {
				counter = 0;
				movies[i] = Movie(title, year, stars, directors, genres, duration, rating / 10, summary, path);
				i++;
				stars.clear();
				directors.clear();
				genres.clear();
			}
		}
	}
	for (auto &t: movies) {
		filtered_movie.push_back(t);
	}
	newfile.close();
}
void App::init()
{
	for (int i = 0; i <= 7; i++) {
		text_opacity[i] = 1.f;				//initial opacity for every letter(fade in animation)
	}
	left_movie = 7;
	center_movie = 0;
	right_movie = 1;
	scene_counter = 1;
	left_an = 3;
	right_an = 4;
	opacity = 0.0f;
	year_counter[0] = 1994;
	year_counter[1] = 2014;
	read();
	playMusic(std::string(ASSET_PATH) + "intro.mp3", 0.5f, false, 0);
	//Initialize all Buttons
	br.outline_opacity = 0.7f;
	br.outline_width = 0.5f;
	br.outline_color[0] = 0.f;
	br.outline_color[1] = 0.f;
	br.outline_color[2] = 0.f;
	buttons.push_back(new Movie_button(CANVAS_WIDTH / 2 + 80, CANVAS_LENGTH / 2 - 60, CANVAS_LENGTH / 2.6f, CANVAS_WIDTH / 3.2f, br, filtered_movie[center_movie]));
	buttons.push_back(new Movie_button(CANVAS_WIDTH / 2.63f, CANVAS_LENGTH /4.0f, CANVAS_LENGTH / 5.2f, CANVAS_WIDTH / 6.4f, br, filtered_movie[left_movie]));
	buttons.push_back(new Movie_button(CANVAS_WIDTH / 1.28f, CANVAS_LENGTH / 4.0f, CANVAS_LENGTH / 5.2f, CANVAS_WIDTH / 6.4f, br, filtered_movie[right_movie]));
	buttons.push_back(new Scenes_button(CANVAS_WIDTH / 2 + 208, CANVAS_LENGTH / 2 + 120, CANVAS_WIDTH / 6.f, CANVAS_LENGTH / 5.f, br, "left_arrow.png"));
	buttons.push_back(new Scenes_button(CANVAS_WIDTH / 2 + 440, CANVAS_LENGTH / 2 + 120, CANVAS_WIDTH / 6.f, CANVAS_LENGTH / 5.f, br, "right_arrow.png"));
	buttons.push_back(new Menu_button(20, 20, CANVAS_WIDTH / 30, CANVAS_WIDTH / 30, br, "menu_button.png"));
	br.fill_color[0] = 0.83f;
	br.fill_color[1] = 0.68f;
	br.fill_color[2] = 0.21f;
	br.outline_opacity = 1.f;
	br.outline_color[0] = 0.60f;
	br.outline_color[1] = 0.55f;
	br.outline_color[2] = 0.15f;
	br.outline_width = 3.0f;
	buttons.push_back(new Genre_button(70,70,	CANVAS_WIDTH / 9,	CANVAS_LENGTH / 20, br, "Action"));
	buttons.push_back(new Genre_button(70, 110,	CANVAS_WIDTH / 9,	CANVAS_LENGTH / 20, br, "Adventure"));
	buttons.push_back(new Genre_button(70, 150, CANVAS_WIDTH / 9,	CANVAS_LENGTH / 20, br, "Crime"));
	buttons.push_back(new Genre_button(70, 190, CANVAS_WIDTH / 9,	CANVAS_LENGTH / 20, br, "Comedy"));
	buttons.push_back(new Genre_button(200, 70, CANVAS_WIDTH / 9,	CANVAS_LENGTH / 20, br, "Drama"));
	buttons.push_back(new Genre_button(200, 110, CANVAS_WIDTH /9,	CANVAS_LENGTH / 20, br, "Horror"));
	buttons.push_back(new Genre_button(200, 150, CANVAS_WIDTH /9,	CANVAS_LENGTH / 20, br, "Sci-Fi"));
	buttons.push_back(new Genre_button(200, 190, CANVAS_WIDTH /9,   CANVAS_LENGTH / 20, br, "Clear"));
	buttons.push_back(new Date_button(65, 260, CANVAS_LENGTH / 40, CANVAS_WIDTH / 40, br));
	buttons.push_back(new Date_button(275, 320, CANVAS_LENGTH / 40, CANVAS_WIDTH / 40, br));
	Brush b;
	b.fill_opacity = 0.2f;
	b.outline_width = 1.f;
	b.outline_color[0] = 1.f;
	b.outline_color[1] = 1.f;
	b.outline_color[2] = 1.f;
	buttons.push_back(new Text_button(170, 385, CANVAS_WIDTH / 4.5f, CANVAS_LENGTH / 25, b));
	buttons.push_back(new Text_button(170, 420, CANVAS_WIDTH / 4.5f, CANVAS_LENGTH / 25, b));
	buttons.push_back(new Text_button(170, 455, CANVAS_WIDTH / 4.5f, CANVAS_LENGTH / 25, b));
	Brush br;
	br.outline_opacity = 0.7f;
	br.outline_width = 0.5f;
	br.outline_color[0] = 0.f;
	br.outline_color[1] = 0.f;
	br.outline_color[2] = 0.f;
	float x = 0;
	float y = 0;
	for (int i = 0; i < filtered_movie.size(); i++) {
		non_filtered_movies.push_back(new Movie_button(CANVAS_WIDTH / 2.6f + x, CANVAS_LENGTH / 3.5f + y, CANVAS_LENGTH / 3.4f, CANVAS_WIDTH / 5.f, br, filtered_movie[i]));
		x += 150;
		if (i == 3) {
			y += 210;
			x = 0;
		}
	}
	filtered_movies = non_filtered_movies; //at the beggining there is no filters applied so filtered_movies = non_filtered_movies
}


void App::updateLoadingScreen()
{
	if (getGlobalTime() >= 5400){		//fade in animation time
		status = BROWSING;
	}
}

void App::updateBrowsingScreen()
{
	MouseState ms;
	getMouseState(ms);
	float mx = windowToCanvasX(ms.cur_pos_x);
	float my = windowToCanvasY(ms.cur_pos_y);	
	//update scene counter
	if (buttons[3]->contains(mx, my) && ms.button_left_pressed){		
		playMusic(std::string(ASSET_PATH) + "widget_sound.mp3", 0.5f, false, 0);
		if (scene_counter == 1) {
			scene_counter = 4;
		}
		scene_counter--;
	}
	if (buttons[4]->contains(mx, my) && ms.button_left_pressed) {
		playMusic(std::string(ASSET_PATH) + "widget_sound.mp3", 0.5f, false, 0);
		if (scene_counter == 3) {
			scene_counter = 0;
		}
		scene_counter++;
	}
	//update movies slider
	if (buttons[1]->contains(mx, my) && ms.button_left_pressed) {
		playMusic(std::string(ASSET_PATH) + "widget_sound.mp3", 0.5f, false, 0);
		left_movie--;
		if (left_movie == -1) {
			left_movie = filtered_movie.size()-1;
		}
		center_movie--;
		if (center_movie == -1) {
			center_movie = filtered_movie.size()-1;
		}
		right_movie--;
		if (right_movie == -1) {
			right_movie = filtered_movie.size()-1;
		}
		reinterpret_cast<Movie_button *>(buttons[0])->change_movie(filtered_movie[center_movie]);
		reinterpret_cast<Movie_button *>(buttons[1])->change_movie(filtered_movie[left_movie]);
		reinterpret_cast<Movie_button *>(buttons[2])->change_movie(filtered_movie[right_movie]);
	}
	if (buttons[2]->contains(mx, my) && ms.button_left_pressed) {
		playMusic(std::string(ASSET_PATH) + "widget_sound.mp3", 0.5f, false, 0);
		left_movie++;
		if (left_movie == filtered_movie.size()) {
			left_movie = 0;
		}
		center_movie++;
		if (center_movie == filtered_movie.size()) {
			center_movie = 0;
		}
		right_movie++;
		if (right_movie == filtered_movie.size()) {
			right_movie = 0;
		}
		reinterpret_cast<Movie_button *>(buttons[0])->change_movie(filtered_movie[center_movie]);
		reinterpret_cast<Movie_button *>(buttons[1])->change_movie(filtered_movie[left_movie]);
		reinterpret_cast<Movie_button *>(buttons[2])->change_movie(filtered_movie[right_movie]);
	}
	//if menu button is pressed change state
	if (buttons[5]->contains(mx, my) && ms.button_left_pressed) {
		status = SEARCHING;
		playMusic(std::string(ASSET_PATH) + "menu_sound.mp3", 0.5f, false, 0);
	}
	//set highlight to all browsing menu buttons
	for (auto& x : buttons) {
		if (x->contains(mx, my)) {
			x->setHighlighted(true);
		}
		else {
			x->setHighlighted(false);
		}
	}
}

void App::updateSearchingScreen()
{
	MouseState ms;
	getMouseState(ms);
	float mx = windowToCanvasX(ms.cur_pos_x);
	float my = windowToCanvasY(ms.cur_pos_y);	
	//if a movie button or menu button is pressed goto browsing screen and update movies slider
	for (int i = 0; i < filtered_movies.size();i++) {
		filtered_movies[i]->setHighlighted(filtered_movies[i]->contains(mx, my));
		if (filtered_movies[i]->contains(mx, my) && ms.button_left_pressed|| buttons[5]->contains(mx, my) && ms.button_left_pressed) {
			if (buttons[5]->contains(mx, my) && ms.button_left_pressed) {
				playMusic(std::string(ASSET_PATH) + "menu_sound.mp3", 0.5f, false, 0);
			}
			else {
				playMusic(std::string(ASSET_PATH) + "widget_sound.mp3", 0.5f, false, 0);
			}
			if (filtered_movies.size() == 1) {
				center_movie = left_movie = right_movie = 0;
			}
			else if (filtered_movies.size() == 2) {
				center_movie = 0;
				left_movie = right_movie = 1;
			}
			left_movie = i - 1;
			center_movie = i ;
			right_movie = i + 1;
			if (left_movie == -1) {
				left_movie = filtered_movies.size()-1;
			}
			if (right_movie == filtered_movies.size()) {
				right_movie = 0;
			}
			reinterpret_cast<Movie_button*>(buttons[0])->change_movie(filtered_movie[center_movie]);
			reinterpret_cast<Movie_button*>(buttons[1])->change_movie(filtered_movie[left_movie]);
			reinterpret_cast<Movie_button*>(buttons[2])->change_movie(filtered_movie[right_movie]);
			status = BROWSING;
		}
	}
	//at this loop filters is applied by movie genre
	int counter = 0;
	Brush br;
	br.outline_color[0] = 0.f;
	br.outline_color[1] = 0.f;
	br.outline_color[2] = 0.f;
	for (int i = 6; i <= 13; i++) {
		if (buttons[i]->contains(mx, my) && ms.button_left_pressed) {
			filtered_movies.clear();
			//if clear button is pressed return to initial state
			if (i == 13) {
				playMusic(std::string(ASSET_PATH) + "clear_sound.mp3", 0.5f, false, 0);
				filtered_movie.clear();
				filtered_movies.clear();
				left_movie = 7;
				center_movie = 0;
				right_movie = 1;
				filtered_movies = non_filtered_movies;
				for (int i = 0; i < 8; i++) {
					filtered_movie.push_back(movies[i]);
				}
				year_counter[0] = 1994;
				year_counter[1] = 2014;
				buttons[14]->setPositionX(65);
				buttons[15]->setPositionX(275);
			}
			//if a filter button is pressed apply filters to filtered movies
			else {
				playMusic(std::string(ASSET_PATH) + "widget_sound.mp3", 0.5f, false, 0);
				float x = 0;
				float y = 0;
				std::vector<Movie> temp;
				for (int j = 0; j < filtered_movie.size(); j++) {
					for (auto& t : filtered_movie[j].getMovieGenres()) {
						if (t == buttons[i]->getPath()) {
							temp.push_back(filtered_movie[j]);
							filtered_movies.push_back(new Movie_button(CANVAS_WIDTH / 2.6f + x, CANVAS_LENGTH / 3.5f + y, CANVAS_LENGTH / 3.4f, CANVAS_WIDTH / 5.f, br, filtered_movie[j]));
							x += 150;
							if (counter == 3) {
								y += 210;
								x = 0;
							}
							counter++;
						}
					}
				}
				filtered_movie = temp;
			}
		}
	}
	//this loop perfoms bar dragging in date filter
	for (int i = 14; i <= 15; i++) {
		if (buttons[i]->contains(mx, my) && ms.dragging) {
			if (buttons[i]->getPositionX() >= 64 && buttons[i]->getPositionX() <= 280) {
				if (ms.cur_pos_x > ms.prev_pos_x) {
					buttons[i]->setPositionX(buttons[i]->getPositionX() + 10.5f);
					if (year_counter[i - 14] < 2014) {
						year_counter[i - 14]++;
					}
				}
				else {
					buttons[i]->setPositionX(buttons[i]->getPositionX() - 10.5f);
					if (year_counter[i - 14] > 1994) {
						year_counter[i - 14]--;
					}

				}
				if (buttons[i]->getPositionX() <= 60) {
					buttons[i]->setPositionX(buttons[i]->getPositionX() + 10.5f);
				}
				if (buttons[i]->getPositionX() >= 280) {
					buttons[i]->setPositionX(buttons[i]->getPositionX() - 10.5f);
				}
				//apply filters by date
				counter = 0;
				float x = 0;
				float y = 0;
				filtered_movie.clear();
				filtered_movies.clear();
				for (int j = 0; j < 8; j++) {
					if (movies[j].getMovieYear() >= year_counter[0] && movies[j].getMovieYear() <= year_counter[1]) {
						filtered_movie.push_back(movies[j]);
						filtered_movies.push_back(new Movie_button(CANVAS_WIDTH / 2.6f + x, CANVAS_LENGTH / 3.5f + y, CANVAS_LENGTH / 3.4f, CANVAS_WIDTH / 5.f, br, filtered_movie[counter]));
						x += 150;
						if (counter == 3) {
							y += 210;
							x = 0;
						}
						counter++;
					}
				}
			}
		}
	}
	//this loop perfoms search by text
	int type = 0;
	counter = 0;
	for (int i = 16; i < buttons.size(); i++) {
		if (buttons[i]->contains(mx, my) && ms.button_left_pressed) {
			playMusic(std::string(ASSET_PATH) + "widget_sound.mp3", 0.5f, false, 0);
			buttons[i]->setActive(true);
			year_counter[0] = 1994;
			year_counter[1] = 2014;
			buttons[14]->setPositionX(65);
			buttons[15]->setPositionX(275);
		}
		if (!buttons[i]->contains(mx, my) && ms.button_left_pressed) {
			text.clear();
			buttons[i]->setActive(false);
		}
		if (buttons[i]->isActive()) {
			filtered_movie.clear();
			filtered_movies.clear();
			type = i;
			buttons[i]->setHighlighted(true);
			static float delay = 0.0f;			//typematic delay
			static int prev = 0;				
			static bool trig = true;			//trigger upper or lower case letters
			static bool capsed = false;
			static short caps_counter = 0;
			float x = 0;
			float y = 0;
			if (type == 16) {					//apply filter by movie title
				for (auto& t : movies) {
					if (t.getMovieTitle().find(text) != string::npos) {
						filtered_movie.push_back(t);
						filtered_movies.push_back(new Movie_button(CANVAS_WIDTH / 2.6f + x, CANVAS_LENGTH / 3.5f + y, CANVAS_LENGTH / 3.4f, CANVAS_WIDTH / 5.f, br, filtered_movie[counter]));
						x += 150;
						if (counter == 3) {
							y += 210;
							x = 0;
						}
						counter++;
					}
				}
			}
			if (type == 17) {					//apply filter by movie stars
				for (auto& t : movies) {
					for (auto& s : t.getMovieStars()) {
						if (s.find(text) != string::npos) {
							filtered_movie.push_back(t);
							filtered_movies.push_back(new Movie_button(CANVAS_WIDTH / 2.6f + x, CANVAS_LENGTH / 3.5f + y, CANVAS_LENGTH / 3.4f, CANVAS_WIDTH / 5.f, br, filtered_movie[counter]));
							x += 150;
							if (counter == 3) {
								y += 210;
								x = 0;
							}
							counter++;
							break;			
						}
					}
				}
			}
			if (type == 18) {					//apply filter by movie directors
				for (auto& t : movies) {
					for (auto& s : t.getMovieDirectors()) {
						if (s.find(text) != string::npos) {
							filtered_movie.push_back(t);
							filtered_movies.push_back(new Movie_button(CANVAS_WIDTH / 2.6f + x, CANVAS_LENGTH / 3.5f + y, CANVAS_LENGTH / 3.4f, CANVAS_WIDTH / 5.f, br, filtered_movie[counter]));
							x += 150;
							if (counter == 3) {
								y += 210;
								x = 0;
							}
							counter++;
							break;
						}
					}
				}
			}
			delay += getDeltaTime();
			bool shift = getKeyState(SCANCODE_LSHIFT);	
			if (getKeyState(SCANCODE_CAPSLOCK)) {
				if (delay > 200.0f) {
					caps_counter++;
					delay = 0.0f;
				}
			}
			if (caps_counter % 2 == 0) {
				capsed = false;
			}
			else {
				capsed = true;
			}
			if (capsed && shift) {	//if caps lock and shift are pressed (lowercase)
				trig = true;
			}
			else if (capsed) {		//if only caps lock is pressed (uppercase)
				trig = false;
			}
			else if (shift) {		//if only shift is pressed (uppercase)
				trig = false;
			}
			else if (!capsed && !shift) {	//if no one is pressed (lowercase)
				trig = true;
			}
			for (int i = SCANCODE_A; i <= SCANCODE_Z; i++) {		//check every keyboard letter
				if (getKeyState((scancode_t)i)) {					//cast i to scancode
					if (prev == i && delay < 200.0f) {
						continue;
					}
					else {
						prev = i;
						delay = 0.0f;
					}
					text += static_cast<char>(i + 61 + trig * 32);		//change to ASCII code and add to text
					break;															 
				}
			}
			if (getKeyState(SCANCODE_SPACE)) {				//add space character to text
				if (delay > 200.0f) {
					delay = 0.0f;
					text += ' ';					
				}
			}
			if (getKeyState(SCANCODE_BACKSPACE)) {			//remove a character from the text
				if (delay > 120.0f) {
					delay = 0.0f;
					if (!text.empty()) {
						text.pop_back();
					}
				}
			}
		}
	}
	//set highlight for every button
	for (auto& x : buttons) {
		if (x->isActive()) {
			continue;
		}
		if (x->contains(mx, my)) {
			x->setHighlighted(true);

		}
		else {
			x->setHighlighted(false);
		}
	}
	for (auto& x : filtered_movies) {
		if (x->contains(mx, my)) {
			x->setHighlighted(true);
		}
		else {
			x->setHighlighted(false);
		}
	}
}

void App::drawLoadingScreen()
{
	setFont(std::string(ASSET_PATH) + "Vogue.ttf");
	Brush brs;	
	brs.texture = std::string(ASSET_PATH) + "background.png";
	brs.outline_opacity = 0.0f;
	drawRect(CANVAS_WIDTH / 2, CANVAS_LENGTH / 2, CANVAS_WIDTH, CANVAS_LENGTH, brs);		
	if (getGlobalTime() <=3000) {
		opacity += 0.005f;					//fade in animation
		for (int i = 0; i <= 7; i++) {
			text_opacity[i] = opacity;		//change opacity for every letter (single fade in)
		}
	}
	else {
		if (left_an > 0 && right_an <7) {
			if (left_an == 3) {							//start from the center of text and goes to left
				text_opacity[3] -= 0.025f;				//fade out animation
				br.fill_opacity = text_opacity[3];
			}
			if (text_opacity[left_an] <= 0.f) {				//if previous letter is now invisible then go to next letter
				text_opacity[left_an - 1] -= 0.025f;			//left - 1 is next letter
				br.fill_opacity = text_opacity[left_an - 1];
			}
			if (text_opacity[left_an - 1] <= 0.f) {			//if previous letter is now invisible then go to next letter
				left_an--;										//left - 1 is next letter
			}
			if (right_an == 4) {							//start from the center of text and goes to right
				text_opacity[4] -= 0.025f;				//fade out animation
				br.fill_opacity = text_opacity[4];
			}
			if (text_opacity[right_an] <= 0.f) {				//if previous letter is now invisible then go to next letter
				text_opacity[right_an + 1] -= 0.025f;			//right + 1 is next letter
				br.fill_opacity = text_opacity[right_an + 1];
			}
			if (text_opacity[right_an + 1] <= 0.f) {		//if previous letter is now invisible then go to next letter
				right_an++;								//right + 1 is next letter
			}
		}
	}
	br.fill_color[0] = 1.f;
	br.fill_color[1] = 0.2f;
	br.fill_color[2] = 0.2f;
	br.fill_opacity = text_opacity[0];											//every letter have different opacity at different times
	drawText(CANVAS_WIDTH / 2 - 200, CANVAS_LENGTH / 2 - 90, 100, "P", br);
	br.fill_opacity = text_opacity[1];
	drawText(CANVAS_WIDTH / 2 - 145, CANVAS_LENGTH / 2 - 90, 100, "O", br);
	br.fill_opacity = text_opacity[2];
	drawText(CANVAS_WIDTH / 2 - 90, CANVAS_LENGTH / 2 - 90, 100, "N", br);
	br.fill_opacity = text_opacity[3];
	drawText(CANVAS_WIDTH / 2 - 25, CANVAS_LENGTH / 2 - 90, 100, "T", br);
	br.fill_opacity = text_opacity[4];
	drawText(CANVAS_WIDTH / 2 + 35, CANVAS_LENGTH / 2 - 90, 100, "F", br);
	br.fill_opacity = text_opacity[5];
	drawText(CANVAS_WIDTH / 2 + 90, CANVAS_LENGTH / 2 - 90, 100, "L", br);
	br.fill_opacity = text_opacity[6];
	drawText(CANVAS_WIDTH / 2 + 150, CANVAS_LENGTH / 2 - 90, 100, "I", br);
	br.fill_opacity = text_opacity[7];
	drawText(CANVAS_WIDTH / 2 + 185, CANVAS_LENGTH / 2 - 90, 100, "X", br);
}

void App::drawBrowsingScreen()
{
	Brush br;
	setFont(std::string(ASSET_PATH) + "coolvetica.ttf");
	br.texture = std::string(ASSET_PATH) + "background.png";
	br.outline_opacity = 0.0f;
	drawRect(CANVAS_WIDTH / 2, CANVAS_LENGTH / 2, CANVAS_WIDTH, CANVAS_LENGTH, br);
	if (filtered_movies.size()) {			//if there is a filtered movie 
		br.outline_opacity = 0.7f;
		br.outline_width = 2.0f;
		br.outline_color[0] = 0.f;
		br.outline_color[1] = 0.f;
		br.outline_color[2] = 0.f;
		drawText(CANVAS_WIDTH / 2 + 275, CANVAS_LENGTH / 2 + 50, 30, "Scenes", br);
		br.texture = std::string(ASSET_PATH) + filtered_movie[center_movie].getMoviePath() + "\\" + filtered_movie[center_movie].getMoviePath() + "_v" + to_string(scene_counter) + ".png";
		drawRect(CANVAS_WIDTH / 2 + 325, CANVAS_LENGTH / 2 + 120, CANVAS_WIDTH / 5.f, CANVAS_LENGTH / 4.f, br);
		reinterpret_cast<Movie_button*>(buttons[0])->drawInfo();		//draw central movie information
		//draw Browsing screen buttons
		if (filtered_movies.size() == 1) {		//if there is only one filtered movie
			buttons[0]->draw();
			buttons[3]->draw();
			buttons[4]->draw();
		}
		else {
			for (int i = 0; i < 5; i++) {
				buttons[i]->draw();
			}
		}
	}
	buttons[5]->draw();
}

void App::drawSearchingScreen() {
	Brush br;
	br.texture = std::string(ASSET_PATH) + "background.png";
	br.outline_opacity = 0.0f;
	drawRect(CANVAS_WIDTH / 2, CANVAS_LENGTH / 2, CANVAS_WIDTH, CANVAS_LENGTH, br);
	reinterpret_cast<Menu_button*>(buttons[5])->draw_menu(); //draw menu background
	Brush brs;
	brs.fill_opacity = 0.2f;
	brs.outline_width = 1.f;
	brs.outline_color[0] = 1.f;
	brs.outline_color[1] = 1.f;
	brs.outline_color[2] = 1.f;
	//draw date dragging bar
	drawRect(170, 260, CANVAS_WIDTH /4.5f , CANVAS_LENGTH / 55, brs);
	drawRect(170, 320, CANVAS_WIDTH / 4.5f, CANVAS_LENGTH / 55, brs);
	drawText(10, 264, CANVAS_WIDTH / 70, "From:", br);
	drawText(20, 324, CANVAS_WIDTH / 70, "To:", br);
	drawText(50, 285, CANVAS_WIDTH / 70, "1994", br);
	drawText(50, 345, CANVAS_WIDTH / 70, "1994", br);
	drawText(260, 285, CANVAS_WIDTH / 70, "2014", br);
	drawText(260, 345, CANVAS_WIDTH / 70, "2014", br);
	drawText(buttons[14]->getPositionX()- 10,245 , CANVAS_WIDTH / 70,to_string(year_counter[0]), br);
	drawText(buttons[15]->getPositionX() - 10, 305, CANVAS_WIDTH / 70, to_string(year_counter[1]), br);
	drawText(100, 40, CANVAS_WIDTH / 50, "Genre:", br);
	drawText(110,230, CANVAS_WIDTH / 50, "Year:", br);
	drawText(20, 390, CANVAS_WIDTH / 65, "Title:", br);
	drawText(20, 425, CANVAS_WIDTH / 65, "Star:", br);
	drawText(2, 460, CANVAS_WIDTH / 65, "Director:", br);
	//draw genre buttons text
	int y = 0;
	for (int i = 16; i < buttons.size(); i++) {
		if (text.size() <= 25) {
			if (buttons[i]->isActive()) {
				drawText(62, 390 + y, CANVAS_WIDTH / 60.f, text, br);
			}
		}
		else {
			if (buttons[i]->isActive()) {
				drawText(62, 390 + y, CANVAS_WIDTH / 60.f, text.substr(0,25), br);
			}
		}
		y += 35;
	}
	//draw filter buttons
	for (int i = 5; i < buttons.size(); i++) {
		buttons[i]->draw();
	}
	//draw filtered movies
	for (auto& x : filtered_movies) {
		x->draw();
	}
}

App::App()
{
}

App::~App() {
	//release used memory
	for (auto& x : buttons) {
		delete x;
	}
	buttons.clear();
	for (auto& x : non_filtered_movies) {
		delete x;
	}
	non_filtered_movies.clear();
	for (auto& x : filtered_movies) {
		delete x;
	}
	filtered_movies.clear();
}