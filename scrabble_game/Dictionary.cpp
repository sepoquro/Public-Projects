/*
 * Dictionary.cpp
 *
 *  Created on: Sep 18, 2016
 *      Author: kempe
 */

// The following is a suggestion for how to proceed.
// Feel free to make any changes you like.

#include <string>
#include <fstream>
#include <iostream>
#include <stdexcept>
#include "Dictionary.h"

using namespace std;

Dictionary::Dictionary (string dictionary_file_name)
{
	ifstream dictFile (dictionary_file_name.c_str());
	string word;

	if (dictFile.is_open())
	{
		while (getline (dictFile, word))
		{
		  //word.erase(word.length()-1); 
		  /* removes end-of-line character; 
		     uncomment if your dictionary file has Windows style line breaks */
			
			// What do you want to do with the word?
			wordbank.insert(word);
		}
		dictFile.close ();
	}
	else throw invalid_argument("Cannot open file: " + dictionary_file_name);
}

Dictionary::~Dictionary(){
	wordbank.clear();
}

bool Dictionary::isLegal(std::string word){
	int wordSize = word.size();
	string newWord, lowerCaseWord;
	for(int i=0;i<wordSize;i++){ //get rid of question marks and brackets
		if(word[i]!='?'&&word[i]!='['&&word[i]!=']'){
			newWord += word[i];
		}
	}
	int newWordSize = newWord.size();
	for(int i=0;i<newWordSize;i++){ //change word to lowercase
		lowerCaseWord += std::tolower(newWord[i]);
	}
	//cout << "Lowercase word: " << lowerCaseWord << endl;
	if(wordbank.count(lowerCaseWord)){
		return true;
	} else{
		return false;
	}
}
