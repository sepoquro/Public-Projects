#include <QWidget>
#include <QVBoxLayout>
#include <QLabel>
#include <QLineEdit>
#include <QTextEdit>
#include <QPushButton>
#include <QListWidget>
#include <QSpinBox>
#include <QMessageBox>

#include <string>
#include <vector>

#include "Dictionary.h"
#include "Board.h"
#include "Bag.h"

#include "Player.h"
#include "Tile.h"

class MainWindow : public QWidget
{
	Q_OBJECT
public:
	MainWindow();
	~MainWindow();
	void initialize(std::vector<std::string> n, Dictionary* a, Board* b, Bag* c, int nt);
	void displayBoard();

private slots:
	void place();
	void exchange();
	void pass();
	void position();
	void endTurnProcess();
	void showPopup();
	void postgameReport();

private:
	QHBoxLayout* overallLayout;

	QGridLayout* boardGridLayout;
	QVBoxLayout* boardLayout;

	QLabel* playerTurnLabel;
	QLabel* directionLabel;
	QLabel* positionLabel;

	QLabel* scoreboardLabel;

	QVBoxLayout* menuLayout;
	QVBoxLayout* scoreboardLayout;
	QGridLayout* sbPlayersLayout;
	QVBoxLayout* actionLayout;
	QGridLayout* tilesLayout;
	QHBoxLayout* buttonsLayout;
	QLineEdit* tileInput;

	QPushButton* placeButton;
	QPushButton* exchangeButton;
	QPushButton* passButton;

	std::vector <QPushButton*> playerTiles;
	std::vector < std::vector<QPushButton*> > boardButtons;
	std::vector <QLabel*> nameLabels;
	std::vector <QLabel*> scoreLabels;

	int numPlayers;
	std::vector<std::string> names;
	std::vector<int> scores;

	Dictionary* dict;
	Board* board;
	Bag* bag;
	int numTiles;

	int startx;
	int starty;

	std::string msg; //message to display in the message box

	//for running the game
	std::string dir;
	int row;
	int col;
	int currPlayerNum;
	int numPassed;
	Player players[8];
	std::string command;
	bool validPlay;
	bool gameOver;
};