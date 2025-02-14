#pragma once
#include <math.h>

//Initialize standard values

#define ASSET_PATH "assets\\"
#define WINDOW_WIDTH 1200
#define WINDOW_LENGTH 600
#define CANVAS_WIDTH 1000
#define CANVAS_LENGTH 500

inline float distance(float x1, float y1, float x2, float y2) {
	float dx = x1 - x2;
	float dy = y1 - y2;
	return sqrtf(dx * dx + dy * dy);
}