#include <QWidget>
#include <QVBoxLayout>
#include <QLabel>
#include <QLineEdit>
#include <QTextEdit>
#include <QPushButton>
#include <QListWidget>
#include <QSpinBox>

#include <string>
#include <vector>

#include "Dictionary.h"
#include "Board.h"
#include "Bag.h"

class PlayerNamesWindow : public QWidget
{
	Q_OBJECT
public:
	PlayerNamesWindow();
	~PlayerNamesWindow();
	void setNumPlayers(int playernumber, Dictionary* a, Board* b, Bag* c, int nt);

private slots:
	void done();

private:
	QGridLayout* overallLayout;

	QPushButton* doneButton;

	QLabel* instructionLabel;

	std::vector<QLabel*> playerLabels;
	std::vector<QLineEdit*> playerNameInputs;

	std::vector<std::string> names;
	int numPlayers;

	Dictionary* dictionary;
	Board* board;
	Bag* bag;
	int numTiles;
};