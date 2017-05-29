/*
 * Board.cpp
 *
 *  Created on: Sep 18, 2016
 *      Author: kempe
 */

// The following is a suggestion for how to proceed.
// Feel free to make any changes you like.

#include <string>
#include <fstream>
#include <iostream>
#include <sstream>
#include <stdexcept>
#include <vector>

#include "Board.h"

using namespace std;

Board::Board (string board_file_name)
{
	ifstream boardFile (board_file_name.c_str());
	string row;

	_x = _y = _startx = _starty = 0; // to appease compiler
	if (!boardFile.is_open())
		throw invalid_argument("Cannot open file: " + board_file_name);
	getline (boardFile, row);
	stringstream s1 (row);
	s1 >> _x >> _y;
	getline (boardFile, row);
	stringstream s2 (row);
	s2 >> _startx >> _starty;
	_startx --; _starty --;  // coordinates from 0 in array

	// Anything else you need to initialize?

	started = false;
	for(int i=0;i<_x;i++){
		vector<string> temp;
		vector<Tile*> temp2;
		for(int j=0;j<_y;j++){
			temp.push_back("");
			temp2.push_back(NULL);
		}
		boardstate.push_back(temp);
		tileboard.push_back(temp2);
	}

	for (int i = 0 ; i < _y; ++ i)
	{
		getline (boardFile, row);
		for (int j = 0; j < _x; ++ j)
		{
			// Fill in the following based on how you store the board.
			if (i == _starty && j == _startx) {
				boardstate[i][j]="***";
			}
			else switch (row[j]) {
			case '.' :
			boardstate[i][j]="...";
			break;
			case '2' :
			boardstate[i][j]="2 L";
			break;
			case '3' :
			boardstate[i][j]="3 L";
			break;
			case 'd' :
			boardstate[i][j]="2 W";
			break;
			case 't' :
			boardstate[i][j]="3 W";
			break;
			default:
				string error = "Improper character in Board file: ";
				throw invalid_argument(error + row[j]);
			}
		}
	}
	boardFile.close ();
}

Board::~Board(){
	unsigned int boardstatesize = boardstate.size();
	for(unsigned int i=0;i<boardstatesize;i++){
		boardstate[i].clear();
		for(unsigned int j=0;j<tileboard[i].size();j++){
			delete tileboard[i][j];
		}
	}
}

void Board::showBoard(){
	cout << "    ";
	for(int i=0;i<_y;i++){
		if(i<9){
			cout << i+1 << "   ";
		} else{
			cout << i+1 << "  ";
		}
	}
	cout << endl;
	for(int i=0;i<_x;i++){
		if(i<9){
			cout << i+1 << "   ";
		} else{
			cout << i+1 << "  ";
		}
		for(int j=0;j<_y;j++){
			cout << boardstate[i][j] << " ";
		}
		cout << endl;
	}
}

std::string Board::checkMove(std::string dir, int row, int col, std::string til, Dictionary dict){
	int tilSize = til.size();
	std::string combinedWord, newWord;
	row--;
	col--;
	int startingRow = row;
	int startingCol = col;
	if(row>_y||col>_x){
		return "~";
	}
	if(boardstate[row][col]!="2 W"&&boardstate[row][col]!="3 W"&&boardstate[row][col]!="2 L"
	&&boardstate[row][col]!="3 L"&&boardstate[row][col]!="..."&&boardstate[row][col]!="***"){
		return "~";
	}
	bool adjacent = false;
	for(int i=0;i<tilSize;i++){ //get rid of question marks and brackets
		if(til[i]!='?'&&til[i]!='['&&til[i]!=']'){
			newWord += til[i];
		}
	}
	til = newWord;
	tilSize = til.size();
	//cout << tilSize << endl;
	if(!started){ //if on the first move
		//cout << "entered" << endl;
		if((dir=="|"&&col==_startx&&(row+tilSize)>_starty&&row<=_starty&&(row+tilSize)<=_y)
		||(dir=="-"&&row==_starty&&(col+tilSize)>_startx&&col<=_startx&&(col+tilSize)<=_x)){
			started = true;
			return til;
		} else{
			return "~"; //means invalid placement
		}
	} else{
		if(dir=="|"){
			if(row+tilSize>_y){
				return "~";
			}
			//back up to start of word
			while((row-1)>=0&&boardstate[row-1][col]!="2 W"&&boardstate[row-1][col]!="3 W"&&boardstate[row-1][col]!="2 L"
			&&boardstate[row-1][col]!="3 L"&&boardstate[row-1][col]!="..."){
				adjacent = true;
				row--;
			}
			int counter=0;
			while(row>=0&&col>=0&&row<_y&&col<_x&&counter>=0&&counter<tilSize&&row<=_y&&(boardstate[row][col]=="2 W"||boardstate[row][col]=="3 W"||boardstate[row][col]=="2 L"
			||boardstate[row][col]=="3 L"||boardstate[row][col]=="...")){
				combinedWord += til[counter];
				counter++;
				row++;
			}
			while(row>=0&&col>=0&&row<_y&&col<_x&&boardstate[row][col]!="2 W"&&boardstate[row][col]!="3 W"&&boardstate[row][col]!="2 L"
			&&boardstate[row][col]!="3 L"&&boardstate[row][col]!="..."){
				adjacent = true;
				combinedWord += boardstate[row][col];
				row++;
			}
			if(counter!=0){
				row = startingRow;
				row--;
			}
			//cout << "cW: " << combinedWord << endl;
			//cout << "Counter: " << counter << endl;
			for(int i =0;i<tilSize;i++){
				while(row>=0&&col>=0&&row<=_y&&boardstate[row][col]!="2 W"&&boardstate[row][col]!="3 W"&&boardstate[row][col]!="2 L"
				&&boardstate[row][col]!="3 L"&&boardstate[row][col]!="..."){
					adjacent = true;
					combinedWord += boardstate[row][col];
					row++;
				}
				if(i>=counter){
					combinedWord += til[i];
				}
				row++;
				//check adjacencies and their legality
				if((col-1)>=0&&boardstate[row][col-1]!="2 W"&&boardstate[row][col-1]!="3 W"&&boardstate[row][col-1]!="2 L"
				&&boardstate[row][col-1]!="3 L"&&boardstate[row][col-1]!="..."){
					adjacent = true;
					int adjRow = row, adjCol = col;
					string adjWord;
					//back up to start of word
					while((adjCol-1)>=0&&boardstate[adjRow][adjCol-1]!="2 W"&&boardstate[adjRow][adjCol-1]!="3 W"&&boardstate[adjRow][adjCol-1]!="2 L"
					&&boardstate[adjRow][adjCol-1]!="3 L"&&boardstate[adjRow][adjCol-1]!="..."){
						adjacent = true;
						adjCol--;
					}
					while((adjCol<=_x&&boardstate[adjRow][adjCol]!="2 W"&&boardstate[adjRow][adjCol]!="3 W"&&boardstate[adjRow][adjCol]!="2 L"
					&&boardstate[adjRow][adjCol]!="3 L"&&boardstate[adjRow][adjCol]!="...")||(adjRow==row&&adjCol==col)){
						if(adjRow==row&&adjCol==col&&(boardstate[row][col]=="2 W"||boardstate[row][col]=="3 W"||boardstate[row][col]=="2 L"
						||boardstate[row][col]=="3 L"||boardstate[row][col]=="...")){
							adjWord += til[i];
						} else{
							adjWord += boardstate[adjRow][adjCol];
						}
						adjCol++;
					}
					if(!dict.isLegal(adjWord)){
						//cout << "1. Adjacent word is not legal!" << endl;
						return "~";
					}
				} else if(boardstate[row][col+1]!="2 W"&&boardstate[row][col+1]!="3 W"&&boardstate[row][col+1]!="2 L"
				&&boardstate[row][col+1]!="3 L"&&boardstate[row][col+1]!="..."){
					adjacent = true;
					int adjRow = row, adjCol = col+1;
					string adjWord;
					while(adjCol<=_x&&boardstate[adjRow][adjCol]!="2 W"&&boardstate[adjRow][adjCol]!="3 W"&&boardstate[adjRow][adjCol]!="2 L"
					&&boardstate[adjRow][adjCol]!="3 L"&&boardstate[adjRow][adjCol]!="..."){
						adjWord += boardstate[adjRow][adjCol];
						adjCol++;
					}
					if(!dict.isLegal(adjWord)){
						//cout << "2. Adjacent word is not legal!" << endl;
						return "~";
					}
				}
			}
		} else if(dir=="-"){
			if(col+tilSize>_x){
				return "~";
			}
			while((col-1)>=0&&boardstate[row][col-1]!="2 W"&&boardstate[row][col-1]!="3 W"&&boardstate[row][col-1]!="2 L"
			&&boardstate[row][col-1]!="3 L"&&boardstate[row][col-1]!="..."){
				adjacent = true;
				col--;
			}
			int counter=0;
			//cout << row << ":" << col << endl;
			while(row>=0&&col>=0&&row<_y&&col<_x&&counter>=0&&counter<tilSize&&col<=_x&&(boardstate[row][col]=="2 W"||boardstate[row][col]=="3 W"||boardstate[row][col]=="2 L"
			||boardstate[row][col]=="3 L"||boardstate[row][col]=="...")){
				combinedWord += til[counter];
				counter++;
				col++;
			}
			while(row>=0&&col>=0&&row<_y&&col<_x&&boardstate[row][col]!="2 W"&&boardstate[row][col]!="3 W"&&boardstate[row][col]!="2 L"
			&&boardstate[row][col]!="3 L"&&boardstate[row][col]!="..."){
				adjacent = true;
				combinedWord += boardstate[row][col];
				col++;
			}
			if(counter!=0){
				col = startingCol;
				col--;
			}
			for(int i =0;i<tilSize;i++){
				while(col>=0&&row>=0&&col<=_x&&boardstate[row][col]!="2 W"&&boardstate[row][col]!="3 W"&&boardstate[row][col]!="2 L"
				&&boardstate[row][col]!="3 L"&&boardstate[row][col]!="..."){
					adjacent = true;
					combinedWord += boardstate[row][col];
					col++;
				}
				if(i>=counter){
					combinedWord += til[i];
				}
				col++;
				//check adjacencies and their legality
				if((row-1)>=0&&boardstate[row-1][col]!="2 W"&&boardstate[row-1][col]!="3 W"&&boardstate[row-1][col]!="2 L"
				&&boardstate[row-1][col]!="3 L"&&boardstate[row-1][col]!="..."){
					adjacent = true;
					int adjRow = row, adjCol = col;
					string adjWord;
					//back up to start of word
					while((adjRow-1)>=0&&boardstate[adjRow-1][adjCol]!="2 W"&&boardstate[adjRow-1][adjCol]!="3 W"&&boardstate[adjRow-1][adjCol]!="2 L"
					&&boardstate[adjRow-1][adjCol]!="3 L"&&boardstate[adjRow-1][adjCol]!="..."){
						adjacent = true;
						adjRow--;
					}
					while((adjRow<=_y&&boardstate[adjRow][adjCol]!="2 W"&&boardstate[adjRow][adjCol]!="3 W"&&boardstate[adjRow][adjCol]!="2 L"
					&&boardstate[adjRow][adjCol]!="3 L"&&boardstate[adjRow][adjCol]!="...")||(adjRow==row&&adjCol==col)){
						if(adjRow==row&&adjCol==col&&(boardstate[row][col]=="2 W"||boardstate[row][col]=="3 W"||boardstate[row][col]=="2 L"
						||boardstate[row][col]=="3 L"||boardstate[row][col]=="...")){
							adjWord += til[i];
						} else{
							adjWord += boardstate[adjRow][adjCol];
						}
						adjRow++;
					}
					if(!dict.isLegal(adjWord)){
						//cout << "3. Adjacent word is not legal!" << endl;
						return "~";
					}
				} else if(boardstate[row+1][col]!="2 W"&&boardstate[row+1][col]!="3 W"&&boardstate[row+1][col]!="2 L"
				&&boardstate[row+1][col]!="3 L"&&boardstate[row+1][col]!="..."){
					adjacent = true;
					int adjRow = row+1, adjCol = col;
					string adjWord;
					while(adjRow<=_y&&boardstate[adjRow][adjCol]!="2 W"&&boardstate[adjRow][adjCol]!="3 W"&&boardstate[adjRow][adjCol]!="2 L"
					&&boardstate[adjRow][adjCol]!="3 L"&&boardstate[adjRow][adjCol]!="..."){
						adjWord += boardstate[adjRow][adjCol];
						adjRow++;
					}
					if(!dict.isLegal(adjWord)){
						//cout << "4. Adjacent word is not legal!" << endl;
						return "~";
					}
				}
			}
		} else{
			return "~";
		}
	}
	if(adjacent){ //don't allow the move if the new word won't be adjacent to previous words
		return combinedWord;
	} else{
		cout << "Not adjacent!" << endl;
		return "~";
	}
}

int Board::useMove(std::string dir, int row, int col, std::vector<Tile*> til){
	int tilSize = til.size();
	row--;
	col--;
	int j = row;
	int k = col;
	int letterMultiplier = 1;
	int wordMultiplier = 1;
	int wordScore = 0;
	int totalScore = 0;
	int adjacentScore = 0;
	int wordLength = tilSize;
	if(dir=="|"){
		while(j>=0&&boardstate[j-1][k]!="2 W"&&boardstate[j-1][k]!="3 W"&&boardstate[j-1][k]!="2 L"
		&&boardstate[j-1][k]!="3 L"&&boardstate[j-1][k]!="..."&&boardstate[j-1][k]!="***"){
			j--;
			//cout << "Adding: " << tileboard[j][k]->getPoints() << endl;
			wordScore += tileboard[j][k]->getPoints();
			row = j;
			wordLength++;
		}
		for(int i=0;i<tilSize;i++){
			letterMultiplier = 1;
			while(j<=_y&&boardstate[j][k]!="2 W"&&boardstate[j][k]!="3 W"&&boardstate[j][k]!="2 L"
			&&boardstate[j][k]!="3 L"&&boardstate[j][k]!="..."&&boardstate[j][k]!="***"){
				j++;
			}
			string temp;
			temp += til[i]->getUse();
			temp = "[" + temp;
			temp = temp + "]";
			if(boardstate[j][k]=="2 W"){
				wordMultiplier *= 2;
			} else if(boardstate[j][k]=="3 W"){
				wordMultiplier *= 3;
			} else if(boardstate[j][k]=="2 L"){
				letterMultiplier *= 2;
			} else if(boardstate[j][k]=="3 L"){
				letterMultiplier *= 3;
			} else if(boardstate[j][k]=="..."){
				//normal multiplier, nothing happens
			} else if(boardstate[j][k]=="***"){
				wordMultiplier *= 2; //starting location gives 2x bonus for word
			}
			//cout << "Placing: " << temp << " on " << boardstate[j][k] << endl;
			//cout << "Adding: " << letterMultiplier*til[i]->getPoints() << endl;
			wordScore += letterMultiplier*til[i]->getPoints();
			boardstate[j][k] = temp;
			tileboard[j][k] = til[i];
		}
		while(j>=_y&&boardstate[j+1][k]!="2 W"&&boardstate[j+1][k]!="3 W"&&boardstate[j+1][k]!="2 L"
		&&boardstate[j+1][k]!="3 L"&&boardstate[j+1][k]!="..."&&boardstate[j+1][k]!="***"){
			j++;
			//cout << "Adding: " << tileboard[j][k]->getPoints() << endl;
			wordScore += tileboard[j][k]->getPoints();
			row = j;
			wordLength++;
		}
		wordScore = wordMultiplier*wordScore;
	} else if(dir=="-"){
		while(k>=0&&boardstate[j][k-1]!="2 W"&&boardstate[j][k-1]!="3 W"&&boardstate[j][k-1]!="2 L"
		&&boardstate[j][k-1]!="3 L"&&boardstate[j][k-1]!="..."&&boardstate[j][k-1]!="***"){
			k--;
			//cout << "Adding: " << tileboard[j][k]->getPoints() << endl;
			wordScore += tileboard[j][k]->getPoints();
			col = k;
			wordLength++;
		}
		for(int i=0;i<tilSize;i++){
			letterMultiplier = 1;
			while(k<=_x&&boardstate[j][k]!="2 W"&&boardstate[j][k]!="3 W"&&boardstate[j][k]!="2 L"
			&&boardstate[j][k]!="3 L"&&boardstate[j][k]!="..."&&boardstate[j][k]!="***"){
				k++;
			}
			string temp;
			temp += til[i]->getUse();
			temp = "[" + temp;
			temp = temp + "]";
			if(boardstate[j][k]=="2 W"){
				wordMultiplier *= 2;
			} else if(boardstate[j][k]=="3 W"){
				wordMultiplier *= 3;
			} else if(boardstate[j][k]=="2 L"){
				letterMultiplier *= 2;
			} else if(boardstate[j][k]=="3 L"){
				letterMultiplier *= 3;
			} else if(boardstate[j][k]=="..."){
				//normal multiplier, nothing happens
			} else if(boardstate[j][k]=="***"){
				wordMultiplier *= 2; //starting location gives 2x bonus for word
			}
			//cout << "Placing: " << temp << " on " << boardstate[j][k] << endl;
			//cout << "Adding: " << letterMultiplier*til[i]->getPoints() << endl;
			wordScore += letterMultiplier*til[i]->getPoints();
			boardstate[j][k] = temp;
			tileboard[j][k] = til[i];
		}
		while(k<=_x&&boardstate[j][k+1]!="2 W"&&boardstate[j][k+1]!="3 W"&&boardstate[j][k+1]!="2 L"
		&&boardstate[j][k+1]!="3 L"&&boardstate[j][k+1]!="..."&&boardstate[j][k+1]!="***"){
			k++;
			//cout << "Adding: " << tileboard[j][k]->getPoints() << endl;
			wordScore += tileboard[j][k]->getPoints();
			col = k;
			wordLength++;
		}
		wordScore = wordMultiplier*wordScore;
	}

	//finding the adjacent score
	if(dir=="|"){
		//cout << row << ":" << col << ":" << boardstate[row][col] << endl;
		for(int i=0;i<wordLength;i++){
			while(col>=0&&tileboard[row][col-1]!=NULL){
				col--;
			}
			while(col<=_x&&tileboard[row][col]!=NULL){
				if(col>0&&col<_x&&!(tileboard[row][col-1]==NULL&&tileboard[row][col+1]==NULL)){
					//cout << "Adding: " << tileboard[row][col]->getPoints() << endl;
					adjacentScore += tileboard[row][col]->getPoints();
				}
				col++;
			}
			row++;
		}
	} else if(dir=="-"){
		//cout << row << ":" << col << ":" << boardstate[row][col] << endl;
		for(int i=0;i<wordLength;i++){
			while(col>=0&&tileboard[row-1][col]!=NULL){
				row--;
			}
			while(col<=_y&&tileboard[row][col]!=NULL){
				if(row>0&&row<_y&&!(tileboard[row-1][col]==NULL&&tileboard[row+1][col]==NULL)){
					//cout << "Adding: " << tileboard[row][col]->getPoints() << endl;
					adjacentScore += tileboard[row][col]->getPoints();
				}
				row++;
			}
			col++;
		}
	}
	//cout << wordScore << " " << adjacentScore << endl;
	totalScore = wordScore + adjacentScore;
	return totalScore;
}

vector< vector<string> > Board::getBoardState(){
   return boardstate;
}
