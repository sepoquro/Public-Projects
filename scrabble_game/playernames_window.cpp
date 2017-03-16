#include "playernames_window.h"
#include "main_window.h"
#include <QString>
#include <QApplication>
#include <string>
#include <sstream>

#include <iostream>

using namespace std;

void PlayerNamesWindow::setNumPlayers(int playernumber, Dictionary* a, Board* b, Bag* c, int nt)
{
	dictionary = a;
	board = b;
	bag = c;
	numTiles = nt;

	numPlayers = playernumber;
	for(int i=1;i<=numPlayers;i++){
		stringstream ss;
		string temp;
		ss << i;
		ss >> temp;
		temp = "Player" + temp;
		names.push_back(temp);
		temp = temp + ": ";
		QLabel* pLabel = new QLabel(QString::fromStdString(temp));
		QLineEdit* pNameInput = new QLineEdit();
		playerLabels.push_back(pLabel);
		playerNameInputs.push_back(pNameInput);
		overallLayout->addWidget(playerLabels[i-1],i,0,1,2);
		overallLayout->addWidget(playerNameInputs[i-1],i,2,1,2);
	}
	doneButton = new QPushButton("Done");
	connect(doneButton,SIGNAL(clicked()),this,SLOT(done()));
	overallLayout->addWidget(doneButton,numPlayers+1,0,1,4);
	setLayout(overallLayout);
}

void PlayerNamesWindow::done()
{
	for(int i=0;i<numPlayers;i++){
		if(playerNameInputs[i]->text().toStdString()!=""){
			names[i] = playerNameInputs[i]->text().toStdString();
		}
		//cout << names[i] << endl;
	}
	close();
	MainWindow* mainwindow = new MainWindow();
	mainwindow->initialize(names,dictionary,board,bag,numTiles);
	mainwindow->show();
}

PlayerNamesWindow::PlayerNamesWindow()
{
	setWindowTitle("Scrabble Game");

	overallLayout = new QGridLayout();

	instructionLabel = new QLabel(QString::fromStdString("Please enter the names of the players"));
	overallLayout->addWidget(instructionLabel, 0, 0, 1, 4);

	setLayout(overallLayout);
}

PlayerNamesWindow::~PlayerNamesWindow()
{
	delete overallLayout;
	delete doneButton;
	delete instructionLabel;

	for(unsigned int i=0;i<playerLabels.size();i++){
		delete playerLabels[i];
		delete playerNameInputs[i];
	}
}