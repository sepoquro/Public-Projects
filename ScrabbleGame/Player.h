#include <vector>
#include <string>
#include <set>

#include "Tile.h"

class Player {

public:
	Player();
	Player(std::set<Tile*> s);
	~Player();
	void addScore(int i);
	int getScore();
	int tilesRemaining();
	void showTiles();
	bool tilesExist(std::string s);
	std::vector<Tile*> removeTiles(std::string s);
	void addTiles(std::set<Tile*> s);
	std::vector<Tile*> getTiles();

private:
	std::vector<Tile*> tiles;
	int score;
};
