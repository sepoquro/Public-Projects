#include <vector>
#include <string>
#include <iostream>

#include "Player.h"

using namespace std;

Player::Player(){

}

Player::Player(std::set<Tile*> s){
	for(set<Tile*>::iterator i=s.begin();i!=s.end();i++){
		tiles.push_back(*i);
	}
	score = 0;
}

Player::~Player(){
	for(unsigned int i=0;i<tiles.size();i++){
		delete tiles[i];
	}
}

void Player::addScore(int i){
	score+=i;
}

int Player::getScore(){
	return score;
}

int Player::tilesRemaining(){
	int numTiles = tiles.size();
	return numTiles;
}

void Player::showTiles(){
	cout << "Your tiles are: ";
	for(int i=0;i<tilesRemaining();i++){
		cout << tiles[i]->getLetter() << tiles[i]->getPoints() << " ";
	}
	cout << endl;
}

bool Player::tilesExist(std::string s){
/*
   cout << s << endl;
   for(unsigned int i=0;i<this->getTiles().size();i++){
		cout << this->getTiles()[i]->getLetter();
	}
	cout << endl;
	for(unsigned int i=0;i<this->getTiles().size();i++){
		cout << s[i];
	}
	cout << endl;
	for(unsigned int i=0;i<this->getTiles().size();i++){
		cout << this->getTiles()[i];
	}
	cout << endl;
*/
	int stringSize = s.size();
	bool allcharFound = true;
	bool charFound = false;
	bool blankFound = false;
	bool used [tilesRemaining()];
	for(int i=0;i<tilesRemaining();i++){
		used[i] = false;
	}
	for(int i=0;i<stringSize;i++){
		if(s[i]=='?'){ //if using a blank tile
			//cout << "Entered: ?" << endl;
			for(int j=0;j<tilesRemaining();j++){
				if(this->getTiles()[j]->getLetter()=='?'){
					this->getTiles()[j]->useAs(s[i+1]);
					blankFound = true;
				}
			}
			if(!blankFound){
				return false;
			}
			//cout << this->getTiles()[i]->getUse() << endl;
			i+=2;
			if(i>=stringSize){
				return allcharFound;
			}
		}
		for(int j=0;j<tilesRemaining();j++){
			if(s[i]==this->getTiles()[j]->getLetter()&&!used[j]&&!charFound){
				used[j] = true;
				charFound = true;
			}
		}
		if(!charFound){
			allcharFound = false;
		}
		charFound = false;
	}
	return allcharFound;
}

std::vector<Tile*> Player::removeTiles(std::string s){
	int stringSize = s.size();
	string newWord;
	for(int i=0;i<stringSize;i++){ //get rid of question marks and brackets
		if(s[i]!='?'&&s[i]!='['&&s[i]!=']'){
			newWord += s[i];
		}
	}
	//cout << newWord << endl;
	s = newWord;
	//cout << s << endl;
	stringSize = s.size();
	std::vector<Tile*> discardPile;
	bool charFound = false;
	for(int i=0;i<stringSize;i++){
		for(int j=0;j<tilesRemaining();j++){
			//cout << this->getTiles()[j]->getUse() << endl;
			if(s[i]==this->getTiles()[j]->getUse()&&!charFound){
				discardPile.push_back(this->getTiles()[j]);
				tiles.erase(tiles.begin()+j);
				charFound = true;
			}
		}
		charFound = false;
	}
	return discardPile;
}

void Player::addTiles(std::set<Tile*> s){
	for(set<Tile*>::iterator i=s.begin();i!=s.end();i++){
		tiles.push_back(*i);
	}
}

std::vector<Tile*> Player::getTiles(){
   return tiles;
}
