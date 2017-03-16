/*
 * Board.h
 *
 *  Created on: Sep 18, 2016
 *      Author: kempe
 */

// The following is a suggestion for how to proceed.
// Feel free to make any changes you like.

#ifndef BOARD_H_
#define BOARD_H_

#include <string>
#include <vector>

#include "Dictionary.h"
#include "Tile.h"

class Board {
public:
	Board (std::string board_file_name);
	~Board();

	// What else will this need?

	void showBoard();
	std::string checkMove(std::string dir, int row, int col, std::string til, Dictionary dict);
	int useMove(std::string dir, int row, int col, std::vector<Tile*> til);
	std::vector< std::vector<std::string> > getBoardState();

private:
	int _x, _y;
	int _startx, _starty;

	// What else will this need?

	std::vector< std::vector<std::string> > boardstate;
	std::vector< std::vector<Tile*> > tileboard;
	bool started;
};


#endif /* BOARD_H_ */
