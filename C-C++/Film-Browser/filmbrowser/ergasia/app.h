#pragma once
#ifndef APP_H
#define APP_H
#include <graphics.h>
#include "movie.h"
#include <vector>
#include "button.h"
#include "movie_button.h"
using namespace graphics;

//This class represent the Application Gui
class App {
	void read();										//this method reads movies values from a txt
	typedef enum {INIT,BROWSING,SEARCHING} status_t;	//app states
	status_t status = INIT;								//initial status
	Brush br;											//app Brush
	short scene_counter ;								//scene counter (changes by right down arrows)
	int center_movie, left_movie, right_movie;			//slider movies
	void updateLoadingScreen();							//these methods update app current state
	void updateBrowsingScreen();
	void updateSearchingScreen();
	void drawLoadingScreen();							//these methods draw app current screen
	void drawBrowsingScreen();
	void drawSearchingScreen();
	Movie movies[8];									//the movies that the app handles
	std::vector<Movie> filtered_movie;					//filtered movies in each moment
	float text_opacity[8];								//these variables are for opening animation
	float opacity;										
	int left_an;										
	int right_an;										
	std::vector<Button *> buttons;						//this vector has all the buttons handled by the app
	std::vector<Movie_button *> non_filtered_movies;	//this vector has all the movie buttons the app handles
	std::vector<Movie_button *> filtered_movies;		//filtered movies buttons in each moment
	short year_counter[2];								//initialized values for movie dates filter
	string text;										//this variable is for text field

public:
	void draw();										//this method set which drawScreen() method will be used
	void update();										//this method set which updateScreen() method will be used
	void init();										//this method initialize all app variables
	App();
	~App();
};

#endif 