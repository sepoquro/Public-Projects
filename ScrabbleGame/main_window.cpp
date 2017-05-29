#include "main_window.h"
#include <QString>
#include <QApplication>

#include <iostream>

using namespace std;

void MainWindow::initialize(vector<string> n, Dictionary* a, Board* b, Bag* c, int nt){
	//cout << "entered initialize" << endl;
	dict = a;
	board = b;
	bag = c;
	numTiles = nt;
	//cout << numTiles << endl;
	//board->showBoard();

	names = n;
	numPlayers = names.size();

	for(unsigned int i=0;i<names.size();i++){ //initialize a score of 0 for each player
		scores.push_back(0);
	}

	for(int i=0;i<numPlayers;i++){ //initialize number of players specified
		Player* temp = new Player(bag->drawTiles(numTiles));
		players[i] = *temp;
	}

	for(int i=0;i<numTiles;i++){
		QPushButton* playerTile = new QPushButton(" ");
		playerTile->setStyleSheet("background-color: navy; color: ivory");
		playerTile->setMinimumWidth(40);
		playerTile->setMaximumWidth(40);
		playerTile->setMinimumHeight(40);
		playerTile->setMaximumHeight(40);
		playerTiles.push_back(playerTile);
		tilesLayout->addWidget(playerTiles[i],1,i,1,1);
	}

	for(unsigned int i=0;i<board->getBoardState().size();i++){
		vector<QPushButton*> temp;
		for(unsigned int j=0;j<board->getBoardState()[i].size();j++){
			QPushButton* boardTileButton;
			if(board->getBoardState()[i][j]=="***"){
				boardTileButton = new QPushButton("S");
				boardTileButton->setStyleSheet("background-color: brown; color: ivory");
				boardTileButton->setMinimumWidth(30);
				boardTileButton->setMaximumWidth(30);
				boardTileButton->setMinimumHeight(30);
				boardTileButton->setMaximumHeight(30);
				starty = i;
				startx = j;
			} else if(board->getBoardState()[i][j]=="..."){
				boardTileButton = new QPushButton(" ");
				boardTileButton->setStyleSheet("background-color: beige; color: black");
				boardTileButton->setMinimumWidth(30);
				boardTileButton->setMaximumWidth(30);
				boardTileButton->setMinimumHeight(30);
				boardTileButton->setMaximumHeight(30);
			} else if(board->getBoardState()[i][j]=="2 L"){
				boardTileButton = new QPushButton("2L");
				boardTileButton->setStyleSheet("background-color: pink; color: maroon");
				boardTileButton->setMinimumWidth(30);
				boardTileButton->setMaximumWidth(30);
				boardTileButton->setMinimumHeight(30);
				boardTileButton->setMaximumHeight(30);
			} else if(board->getBoardState()[i][j]=="3 L"){
				boardTileButton = new QPushButton("3L");
				boardTileButton->setStyleSheet("background-color: salmon; color: maroon");
				boardTileButton->setMinimumWidth(30);
				boardTileButton->setMaximumWidth(30);
				boardTileButton->setMinimumHeight(30);
				boardTileButton->setMaximumHeight(30);
			} else if(board->getBoardState()[i][j]=="2 W"){
				boardTileButton = new QPushButton("2W");
				boardTileButton->setStyleSheet("background-color: khaki; color: maroon");
				boardTileButton->setMinimumWidth(30);
				boardTileButton->setMaximumWidth(30);
				boardTileButton->setMinimumHeight(30);
				boardTileButton->setMaximumHeight(30);
			} else if(board->getBoardState()[i][j]=="3 W"){
				boardTileButton = new QPushButton("3W");
				boardTileButton->setStyleSheet("background-color: gold; color: maroon");
				boardTileButton->setMinimumWidth(30);
				boardTileButton->setMaximumWidth(30);
				boardTileButton->setMinimumHeight(30);
				boardTileButton->setMaximumHeight(30);
			}
			connect(boardTileButton, SIGNAL(clicked( )), this, SLOT(position()));
			temp.push_back(boardTileButton);
		}
		boardButtons.push_back(temp);
	}

	for(unsigned int i=0;i<names.size();i++){
		QLabel* tempName = new QLabel(QString::fromStdString(names[i]+": "));
		QLabel* tempScore = new QLabel(QString::number(scores[i]));
		nameLabels.push_back(tempName);
		scoreLabels.push_back(tempScore);
	}

	boardGridLayout->setSpacing(0);
	for(unsigned int i=0;i<boardButtons.size();i++){
		for(unsigned int j=0;j<boardButtons[i].size();j++){
			boardGridLayout->addWidget(boardButtons[i][j],i+1,j,1,1);
		}
	}

	sbPlayersLayout->setSpacing(0);
	for(unsigned int i=0;i<names.size();i++){
		sbPlayersLayout->addWidget(nameLabels[i],i,0,1,1);
		sbPlayersLayout->addWidget(scoreLabels[i],i,2,1,1);
	}

	displayBoard();
}

void MainWindow::displayBoard(){
	//cout << "entered display" << endl;
	playerTurnLabel->setText(QString::fromStdString(names[currPlayerNum]+", it is your turn!"));
	
	for(unsigned int i=0;i<boardButtons.size();i++){
		for(unsigned int j=0;j<boardButtons[i].size();j++){
			if(board->getBoardState()[i][j]!="***"&&board->getBoardState()[i][j]!="..."&&board->getBoardState()[i][j]!="2 L"&&board->getBoardState()[i][j]!="3 L"
			&&board->getBoardState()[i][j]!="2 W"&&board->getBoardState()[i][j]!="3 W"){
				string noBrackets = board->getBoardState()[i][j];
				noBrackets = noBrackets[1];
				boardButtons[i][j]->setText(QString::fromStdString(noBrackets));
				boardButtons[i][j]->setStyleSheet("background-color: navy; color: ivory");
			}
		}
	}

	//cout << "board complete" << endl;
	//cout << nameLabels.size() << endl;
	//cout << scoreLabels.size() << endl;

	for(unsigned int i=0;i<names.size();i++){
		scoreLabels[i]->setText(QString::number(players[i].getScore()));
	}

	//cout << "scoreboard complete" << endl;
	//cout << numTiles << " " << currPlayerTiles.size() << endl;
	vector <Tile*> currPlayerTiles;
	currPlayerTiles = players[currPlayerNum].getTiles();
	for(int i=0;i<numTiles;i++){
		string tempString;
		tempString += currPlayerTiles[i]->getLetter();
		QString tilePrint = QString::fromStdString(tempString)+QString::number(currPlayerTiles[i]->getPoints());
		playerTiles[i]->setText(tilePrint);
		//cout << i << endl;
	}

	row = 0;
	col = 0;
	positionLabel->setText(QString::fromStdString("Row: ")
	+QString::number(row)+QString::fromStdString(", Col: ")+QString::number(col));

	setLayout(overallLayout);
}

void MainWindow::place()
{
	command = "PLACE";
	//cout << "row:" << row << " " << "col:" << col << endl;
	string til = tileInput->text().toStdString();
	string combinedWord;
	vector<Tile*> discardPile;
	if(row!=0&&col!=0){
		if(til.length()!=0){
			if(players[currPlayerNum].tilesExist(til)){ //check to see if tiles are available
				//cout << "Tiles exist!" << endl;
				combinedWord = board->checkMove(dir,row,col,til,*dict);
				//cout << combinedWord << endl;
				if(combinedWord!="~"){ //check to see if board placement is valid
					//cout << "Can place on board!" << endl;
					if(dict->isLegal(combinedWord)){ //check dictionary for word legality
					//cout << "Word is legal!" << endl;
					vector<Tile*> tempvector;
					tempvector = players[currPlayerNum].removeTiles(til);
					discardPile.insert(discardPile.end(),tempvector.begin(),tempvector.end()); 
					players[currPlayerNum].addScore(board->useMove(dir,row,col,tempvector));
					msg = "You have placed the word: " + combinedWord;
					showPopup();
					//players[currPlayerNum].showTiles();
					} else{
						msg = "Word is not legal!";
						validPlay = false;
						showPopup();
					}
				} else{
					msg = "Cannot place on board!";
					validPlay = false;
					showPopup();
				}
			} else{
				msg = "You do not have these tiles!";
				validPlay = false;
				showPopup();
			}
		} else{
			msg = "You need to specify the tile(s) to place!";
			validPlay = false;
			showPopup();
		}
	} else {
		msg = "You need to select a location to place!";
		validPlay = false;
		showPopup();
	}
	endTurnProcess();
	displayBoard();
}

void MainWindow::exchange()
{
	/*
	for(unsigned int i=0;i<players[currPlayerNum].getTiles().size();i++){
		cout << players[currPlayerNum].getTiles()[i]->getLetter() << endl;
	}
	*/
	command = "EXCHANGE";
	string til = tileInput->text().toStdString();
	if(til.length()==0){
		msg = "You need to specify the tile(s) to exchange!";
		validPlay = false;
		showPopup();
	} else{
	//cout << til.length() << endl;
	//players[currPlayerNum].showTiles();
	//cout << til << endl;
	//cout << players[currPlayerNum].tilesExist(til) << endl;
		if(players[currPlayerNum].tilesExist(til)){ //check to see if tiles are available
			bag->addTiles(players[currPlayerNum].removeTiles(til));
			players[currPlayerNum].addTiles(bag->drawTiles(til.size()));
			//players[currPlayerNum].showTiles();
		} else{
			msg = "You do not have these tiles!";
			validPlay = false;
			showPopup();
		}
	}
	endTurnProcess();
	displayBoard();
}

void MainWindow::pass()
{
	command = "PASS";
	numPassed++;
	endTurnProcess();
	displayBoard();
}

void MainWindow::position()
{
	if(dir=="-"){
		dir = "|";
		directionLabel->setText("Direction of word placement: vertical");
	} else{
		dir = "-";
		directionLabel->setText("Direction of word placement: horizontal");
	}
	QPushButton* tempButton = (QPushButton*)QObject::sender();
	for(unsigned int i=0;i<boardButtons.size();i++){
		for(unsigned int j=0;j<boardButtons[i].size();j++){
			if(boardButtons[i][j]==tempButton){
				row = i + 1;
				col = j + 1;
			}
		}
	}
	positionLabel->setText(QString::fromStdString("Row: ")
	+QString::number(row)+QString::fromStdString(", Col: ")+QString::number(col));
	//cout << "row:" << row << " " << "col:" << col << endl;
}

void MainWindow::endTurnProcess()
{
	int currTiles = players[currPlayerNum].tilesRemaining();
	//cout << currTiles << endl;
	while(currTiles<numTiles&&bag->tilesRemaining()!=0){
		players[currPlayerNum].addTiles(bag->drawTiles(1));
		currTiles++;
	}
	if(players[currPlayerNum].tilesRemaining()==0||numPassed==numPlayers){
		gameOver = true; //run game until a player has no more tiles or all passed
		msg = "Game over!";
		showPopup();
		postgameReport();
	} else{
		//cout << "Play valid: " << validPlay << endl;
		if(validPlay){
			//cout << "Play valid" << endl;
			//players[currPlayerNum].showTiles();
			if(command!="PASS"){
				numPassed = 0;
				string tempStringTiles = "Your new hand is: ";
				vector <Tile*> currPlayerTiles;
				currPlayerTiles = players[currPlayerNum].getTiles();
				for(int i=0;i<numTiles;i++){
					tempStringTiles += currPlayerTiles[i]->getLetter();
					tempStringTiles += " ";
				}
				msg = tempStringTiles;
				showPopup();
				tileInput->setText("");
			} else{
				msg = "You have passed!";
				showPopup();
			}
			currPlayerNum++;
		}
	}
	validPlay = true;
	if(currPlayerNum==numPlayers){
		currPlayerNum = 0;
	}
	command = "";
}

void MainWindow::showPopup()
{
	QMessageBox msgBox;

	msgBox.setWindowTitle("Alert!");
	msgBox.setText(QString::fromStdString(msg));
	msg = "";

	msgBox.setStandardButtons(QMessageBox::Ok);

	msgBox.exec();
}

void MainWindow::postgameReport()
{
	//determine winner(s)
	int maxScore = 0;
	msg = "";
	for(int i=0;i<numPlayers;i++){
		if(players[i].getScore()>maxScore){
			maxScore = players[i].getScore();
		}
	}
	msg = "The winners are: ";
	for(int i=0;i<numPlayers;i++){
		if(players[i].getScore()==maxScore){
			msg += names[i];
			msg += ", ";
		}
	}
	msg += "with a score of ";

	//open postgame report
	QMessageBox gameOverBox;

	gameOverBox.setWindowTitle("Postgame Report");
	gameOverBox.setText(QString::fromStdString(msg)+QString::number(maxScore));
	msg = "";

	gameOverBox.setStandardButtons(QMessageBox::Ok);

	gameOverBox.exec();
	QApplication::exit();
}

MainWindow::MainWindow()
{
	currPlayerNum = 0;
	numPassed = 0;
	validPlay = true;
	gameOver = false;
	dir = "-";
	row = 0;
	col = 0;
	setWindowTitle("Scrabble Game");

	overallLayout = new QHBoxLayout();

	boardGridLayout = new QGridLayout();
	boardLayout = new QVBoxLayout();

	playerTurnLabel = new QLabel();
	directionLabel = new QLabel("Direction of word placement: horizontal");
	positionLabel = new QLabel(QString::fromStdString("Row: ")
	+QString::number(row)+QString::fromStdString(", Col: ")+QString::number(col));

	scoreboardLabel = new QLabel(QString::fromStdString("Scoreboard"));

	menuLayout = new QVBoxLayout();
	scoreboardLayout = new QVBoxLayout();
	sbPlayersLayout = new QGridLayout();
	tilesLayout = new QGridLayout();
	actionLayout = new QVBoxLayout();
	buttonsLayout = new QHBoxLayout();
	//cout << "passed1" << endl;
	tileInput = new QLineEdit();

	placeButton = new QPushButton("Place");
	exchangeButton = new QPushButton("Exchange");
	passButton = new QPushButton("Pass");
	//cout << "passed2" << endl;

	boardLayout->addLayout(boardGridLayout);
	overallLayout->addLayout(boardLayout);

	scoreboardLayout->addWidget(scoreboardLabel);
	scoreboardLayout->addLayout(sbPlayersLayout);
	menuLayout->addLayout(scoreboardLayout);
	menuLayout->addWidget(playerTurnLabel);

	//cout << numTiles << endl;
	
	actionLayout->addLayout(tilesLayout);
	actionLayout->addWidget(tileInput);

	connect(placeButton, SIGNAL(clicked( )), this, SLOT(place( )));
	connect(exchangeButton, SIGNAL(clicked( )), this, SLOT(exchange( )));
	connect(passButton, SIGNAL(clicked( )), this, SLOT(pass( )));
	buttonsLayout->addWidget(placeButton);
	buttonsLayout->addWidget(exchangeButton);
	buttonsLayout->addWidget(passButton);
	actionLayout->addLayout(buttonsLayout);
	actionLayout->addWidget(directionLabel);
	actionLayout->addWidget(positionLabel);
	menuLayout->addLayout(actionLayout);

	overallLayout->addLayout(menuLayout);

	//setLayout(overallLayout);
}

MainWindow::~MainWindow()
{
	names.clear();
	scores.clear();
	for(int i=0;i<numTiles;i++){
		delete playerTiles[i];
	}
	for(unsigned int i=0;i<board->getBoardState().size();i++){
		for(unsigned int j=0;j<board->getBoardState()[i].size();j++){
			delete boardButtons[i][j];
		}
	}
	for(int i=0;i<numPlayers;i++){
		delete nameLabels[i];
		delete scoreLabels[i];
	}
	for(int i=0;i<numPlayers;i++){
		Player* toDelete = &players[i];
		delete toDelete;
	}


	delete dict;
	delete board;
	delete bag;


	delete boardGridLayout;
	delete boardLayout;

	delete playerTurnLabel;
	delete directionLabel;
	delete positionLabel;

	delete scoreboardLabel;

	delete menuLayout;
	delete scoreboardLayout;
	delete sbPlayersLayout;
	delete actionLayout;
	delete tilesLayout;
	delete buttonsLayout;
	delete tileInput;

	delete placeButton;
	delete exchangeButton;
	delete passButton;

	delete overallLayout;
}