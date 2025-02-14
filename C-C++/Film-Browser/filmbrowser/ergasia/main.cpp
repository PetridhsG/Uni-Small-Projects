#include <graphics.h>
#include "config.h"
#include "app.h"

using namespace graphics;

void update(float ms)
{
	App* my_app = reinterpret_cast<App*>(getUserData());
	my_app->update();
		
}

void draw()
{
	App* my_app = reinterpret_cast<App*>(getUserData());
	my_app->draw();
}

int main()
{
	App my_app;
	createWindow(WINDOW_WIDTH, WINDOW_LENGTH, "Pontflix");

	setUserData(&my_app);

	setDrawFunction(draw);
	setUpdateFunction(update);

	setCanvasSize(CANVAS_WIDTH, CANVAS_LENGTH);
	setCanvasScaleMode(graphics::CANVAS_SCALE_FIT);

	my_app.init();
	startMessageLoop();

	return 0;
}