/*
 * Scrabble.cpp
 *
 *  Created on: Sep 18, 2016
 *      Author: kempe
 */

// The following is a suggestion for how to proceed.
// Feel free to make any changes you like.

#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <cstdlib>
#include <stdexcept>
#include <vector>

#include "Tile.h"
#include "Dictionary.h"
#include "Bag.h"
#include "Board.h"

#include "main_window.h"
#include "playernumber_window.h"
#include "playernames_window.h"
#include <QApplication>

using namespace std;

void readConfigFile (string config_file_name,
					 string & dictionary_file_name,
					 string & board_file_name,
					 string & bag_file_name,
					 unsigned int & hand_size)
{
	ifstream configFile (config_file_name.c_str());
	string line;
    bool number = false, board = false, tiles = false, dictionary = false;

	if (!configFile.is_open())
		throw invalid_argument("Cannot open file: " + config_file_name);
	while (getline (configFile, line))
	{
		stringstream ss (line);
		string parameter;
		ss >> parameter;
		if (parameter == "NUMBER:")
			{ ss >> hand_size; number = true; }
		else if (parameter == "BOARD:")
		    { ss >> board_file_name; board = true; }
		else if (parameter == "TILES:")
			{ ss >> bag_file_name; tiles = true; }
		else if (parameter == "DICTIONARY:")
			{ ss >> dictionary_file_name; dictionary = true; }
	}
	if (!number)
		throw invalid_argument("Hand size not specified in config file");
	if (!board)
		throw invalid_argument("Board file name not specified in config file");
	if (!tiles)
		throw invalid_argument("Bag file name not specified in config file");
	if (!dictionary)
		throw invalid_argument("Dictionary file name not specified in config file");
}

int main (int nargs, char **args)
{
	if (nargs < 2 || nargs > 2)
		cout << "Usage: Scrabble <config-filename>\n";
	try {
		string dictionaryFileName, boardFileName, bagFileName;
		unsigned int numTiles;

		readConfigFile (args[1],
						dictionaryFileName, boardFileName, bagFileName,
						numTiles);

		Dictionary dict (dictionaryFileName);
		Board board (boardFileName);
		Bag bag (bagFileName, 10); // second argument is random seed

		// Good luck!

		QApplication app(nargs, args);

		PlayerNumberWindow* playernumberwindow = new PlayerNumberWindow();
		playernumberwindow->setup(&dict, &board, &bag, numTiles);
		playernumberwindow->show();
		return app.exec();
		} catch (invalid_argument & e)
		{ cout << "Fatal Error! " << e.what(); }
}

/*
		int numPlayers = 0;
		int currPlayerNum = 0;
		unsigned int currTiles;
		bool validPlay = true;
		bool gameOver = false;
		string input;
		string command;
		string til, dir;
		int row, col;
		string combinedWord;
		Player players[8];
		vector<Tile*> discardPile;
		int numPassed = 0;

		getline(cin,input);
		while(!gameOver){
			MainWindow mainwindow;
			board.showBoard(); //display the state of the board
			players[currPlayerNum].showTiles(); //display the tiles a player has
			for(int i=0;i<numPlayers;i++){
				cout << "Player" << i+1 << "'s score: " << players[i].getScore() << " ";
			}
			cout << endl;
			cout << "Player" << currPlayerNum+1 << "'s turn!" << endl;
			cout << "What will be your move?" << endl;
			getline(cin,input);
			stringstream currMove;
			currMove << input;
			currMove >> command;
			//cout << "Input: " << input << currMove << command << endl;
			if(command=="PASS"){ //pass command
				numPassed++;
			} else if(command=="EXCHANGE"){ //exchange command
				currMove >> til;
				if(players[currPlayerNum].tilesExist(til)){ //check to see if tiles are available
					bag.addTiles(players[currPlayerNum].removeTiles(til));
					players[currPlayerNum].addTiles(bag.drawTiles(til.size()));
					//players[currPlayerNum].showTiles();
				} else{
					cout << "You do not have these tiles!" << endl;
					validPlay = false;
				}
			} else if(command=="PLACE"){ //place command
				currMove >> dir;
				currMove >> row;
				currMove >> col;
				currMove >> til;
				if(players[currPlayerNum].tilesExist(til)){ //check to see if tiles are available
					cout << "Tiles exist!" << endl;
					combinedWord = board.checkMove(dir,row,col,til,dict);
					//cout << combinedWord << endl;
					if(combinedWord!="~"){ //check to see if board placement is valid
						cout << "Can place on board!" << endl;
						if(dict.isLegal(combinedWord)){ //check dictionary for word legality
						cout << "Word is legal!" << endl;
						vector<Tile*> tempvector;
						tempvector = players[currPlayerNum].removeTiles(til);
						discardPile.insert(discardPile.end(),tempvector.begin(),tempvector.end()); 
						players[currPlayerNum].addScore(board.useMove(dir,row,col,tempvector));
						//players[currPlayerNum].showTiles();
						} else{
							cout << "Word is not legal!" << endl;
							validPlay = false;
						}
					} else{
						cout << "Cannot place on board!" << endl;
						validPlay = false;
					}
				} else{
					cout << "You do not have these tiles!" << endl;
					validPlay = false;
				}
			} else {
				validPlay = false;
				cout << "Invalid move command" << endl;
			}
			currTiles = players[currPlayerNum].tilesRemaining();
			//cout << currTiles << endl;
			while(currTiles<numTiles&&bag.tilesRemaining()!=0){
				players[currPlayerNum].addTiles(bag.drawTiles(1));
				currTiles++;
			}
			if(players[currPlayerNum].tilesRemaining()==0||numPassed==numPlayers){
				gameOver = true; //run game until a player has no more tiles or all passed
			}
			//cout << "Play valid: " << validPlay << endl;
			if(validPlay){
				players[currPlayerNum].showTiles();
				currPlayerNum++;
				if(command!="PASS"){
					numPassed = 0;
				}
			}
			validPlay = true;
			if(currPlayerNum==numPlayers){
				currPlayerNum = 0;
			}
		}
		//determine the winner after the game ends
		int maxScore = 0;
		for(int i=0;i<numPlayers;i++){
			if(players[i].getScore()>maxScore){
				maxScore = players[i].getScore();
			}
		}
		cout << "The winners are: ";
		for(int i=0;i<numPlayers;i++){
			if(players[i].getScore()==maxScore){
				cout << "Player" << i+1 << " ";
			}
		}
		cout << endl;
		//cleanup
		for(int i=0;i<numPlayers;i++){
			Player* toDelete = &players[i];
			delete toDelete;
		}
		return 0;
	}
	return 1;
}
*/