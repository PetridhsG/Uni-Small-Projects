#pragma once
#ifndef MOVIE_H
#define MOVIE_H
#include <string>
#include <list>
using namespace std;
class Movie {
private:
	short duration;				//these are the instance variables
	short year;					 
	float rating;				
	list<string> stars;			
	list<string> directors;		
	list<string> genres;		
	string title;				
	string summary;				
	string path;				
public:
	string getMoviePath();				//these methods return instance variables
	string getMovieTitle();
	string getMovieSummary();
	list<string> getMovieGenres();
	list<string> getMovieDirectors();
	list<string> getMovieStars();
	short getMovieDuration();
	short getMovieYear();
	float getMovieRating();
	Movie(const string& title, short year,const list<string>& stars,const list<string>& directors,
		const list<string>& genres, short duration,float rating, const string& summary, const string& path);
	Movie();
};


#endif