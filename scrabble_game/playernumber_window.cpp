#include "playernumber_window.h"
#include "playernames_window.h"
#include <QString>
#include <QApplication>

#include <iostream>

using namespace std;

void PlayerNumberWindow::setup(Dictionary* a, Board* b, Bag* c, int nt)
{
	dictionary = a;
	board = b;
	bag = c;
	numTiles = nt;
}

void PlayerNumberWindow::continueButtonPressed()
{
	playernumber = numberSelect->value();
	close();
	PlayerNamesWindow* playernameswindow = new PlayerNamesWindow();
	playernameswindow->setNumPlayers(playernumber, dictionary, board, bag, numTiles);
	playernameswindow->show();
}

PlayerNumberWindow::PlayerNumberWindow()
{
	setWindowTitle("Scrabble Game");

	overallLayout = new QHBoxLayout();

	welcomeLabel = new QLabel(QString::fromStdString("Welcome! How many players will be playing today?"));
	overallLayout->addWidget(welcomeLabel);

	numberSelect = new QSpinBox();
	numberSelect->setRange(1,8);
	overallLayout->addWidget(numberSelect);

	continueButton = new QPushButton("Continue");
	connect(continueButton, SIGNAL(clicked( )), this, SLOT(continueButtonPressed( )));
	overallLayout->addWidget(continueButton);

	setLayout(overallLayout);

}

PlayerNumberWindow::~PlayerNumberWindow()
{
	delete welcomeLabel;
	delete numberSelect;
	delete continueButton;
	delete overallLayout;
}