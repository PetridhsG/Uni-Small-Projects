#include "movie.h"

std::string Movie::getMoviePath()
{
	return path;
}

short Movie::getMovieDuration()
{
 	return duration;
}

short Movie::getMovieYear()
{
	return year;
}
float Movie::getMovieRating()
{
	return rating;
}

std::string Movie::getMovieTitle()
{
	return title;
}

std::string Movie::getMovieSummary()
{
	return summary;
}

std::list<string> Movie::getMovieGenres()
{
	return genres;
}

std::list<string> Movie::getMovieDirectors()
{
	return directors;
}

std::list<string> Movie::getMovieStars() {
	return stars;
}

Movie::Movie(const string& title, short year,const list<string>& stars,const list<string>& directors,
	const list<string>& genres, short duration, float rating, const string& summary, const string& path)
{
	this->duration = duration;
	this->year = year;
	this->rating = rating;
	this->stars = stars;
	this->genres = genres;
	this->directors = directors;
	this->title = title;
	this->summary = summary;
	this->path = path;
}

Movie::Movie() {

}
