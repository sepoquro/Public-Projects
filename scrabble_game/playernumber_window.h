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

class PlayerNumberWindow : public QWidget
{
	Q_OBJECT
public:
	PlayerNumberWindow();
	~PlayerNumberWindow();
	void setup(Dictionary* a, Board* b, Bag* c, int nt);

private slots:
	void continueButtonPressed();

private:
	QHBoxLayout* overallLayout;
	QLabel* welcomeLabel;
	QSpinBox* numberSelect;
	QPushButton* continueButton;
	int playernumber;

	Dictionary* dictionary;
	Board* board;
	Bag* bag;
	int numTiles;
};