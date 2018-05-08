**Build Status**

[![Build Status](https://travis-ci.org/byrash/NumberToWords.svg?branch=master)](https://travis-ci.org/byrash/NumberToWords)


**1-800-CODING-CHALLENGE or Number to Words**

Many companies like to list their phone numbers using the letters printed on most telephones. This makes the number easier to remember for customers. 

An example may be 1-800-FLOWERS


This coding challenge is to write a program that will show a user possible matches for a list of provided phone numbers.
Your program should be a command line application that reads from files specified as command-line arguments or STDIN when no files are given. Each line of these files will contain a single phone number.
For each phone number read, your program should output all possible word replacements from a dictionary. Your program should try to replace every digit of the provided phone number with a letter from a dictionary word; however, if no match can be made, a single digit can be left as is at that point. No two consecutive digits can remain unchanged and the program should skip over a number (producing no output) if a match cannot be made.
Your program should allow the user to set a dictionary with the -d command-line option, but it's fine to use a reasonable default for your system. The dictionary is expected to have one word per line.

All punctuation and whitespace should be ignored in both phone numbers and the dictionary file. The program should not be case sensitive, letting "a" == "A". Output should be capital letters and digits separated at word boundaries with a single dash (-), one possible word encoding per line. For example, if your program is fed the number:
2255.63

One possible line of output is
CALL-ME

_According to my dictionary._

The number encoding on the phone the program will use is:


DIGIT->CHARACTERS

2->ABC

3->DEF

4->GHI

5->JKL

6->MNO

7->PQRS

8->TUV

9->WXYZ

**Technical Stack  & Frameworks Used**

1) Java 1.8
2) Maven
3) Junit 5

**How it runs? / Algorithm**

0. Start program
1. On System start up, check if there are any command line arguments passed
2. If cmd args passed, parse them and see if an option -d followed by a **valid path** is supplied or not
    
  1. If dictionary **valid pat**h is supplied with -d option, load that path and use it as dictionary files
  
  2. If -d option is not passed **or** if -d supplied path is not valid, default to system default dictionary

3. Has any other **valid input number files** passed through command line st system start ?? hold those file paths as the input files to be processed
4. For any reasons if the dictionary file cant be loaded either user supplied or system default, System exits to Step 13
5. If Step 7 is success and system finds and input number files from Step 6, then continue; else go to step 11
  1. For each input number file supplied do below
     1. Read the file content line by line
     2. For each line, clean the line to be just a number
     3. For the cleaned number get the words from the dictionary.
     4. If words exists print those words and continue with next line, go to Step 9.ii
     5. If words does not exists for the full match of the number
        1. Get the number patterns to consider i.e. for example for a give number 123, possible
           number patterns to consider will be 123,(1)23,1(2)3 and 12(3) where the number with in
           brackets represents the number that does NOT requires to be replaced with a word
        2. For each pattern found from step 9.v.a
           1. Find all replacement words from dictionary for each individual number that needs replacement
           2. If all replacement words found then do generate words for the pattern number
           3. If not all numbers have replacement continue with next pattern, goto 9.v.b
  2. Is there any problem with any individual input number file skip that file and continue to Step 9
6. No input files received ? wait for user manual entry on console
7. For each and every number user enters do from Step 9.ii and once done, wait for user input again
8. Graceful exit of system

**How do I get this running ??**

Checkout code base from git, build yourself ( using below steps ) and double click teh jar file produced in target folder or use java -jar command

**Build produces a self executable Jar**

```text
git clone https://github.com/byrash/NumberToWords.git ./NumberToWords

cd NumberToWords

mvn clean install

** Maven produces a self executable jar, you 
could run with multiple options as below **

java -jar target/NumberToWords-1.0-SNAPSHOT.jar 

or

double click NumberToWords-1.0-SNAPSHOT.jar file produced in target folder 
( Assuming you jar launcher is set to run jar files )

```

_If you want to pass a dictionary file use -d option and use exit command to exit gracefully on command line mode_


**Example Usages:**

_With Single input file and dict_

1. <PATH>/NumberToWords  java -jar target/NumberToWords-1.0-SNAPSHOT.jar <ABSOLUTE_PATH>/src/main/resources/input.txt -d<ABSOLUTE_PATH>/src/main/resources/test-dictionary.txt

_with no input file and no dict_

2. <PATH>/NumberToWords  java -jar target/NumberToWords-1.0-SNAPSHOT.jar

_with no input file and Only dict_

3. <PATH>/NumberToWords  java -jar target/NumberToWords-1.0-SNAPSHOT.jar -d<ABSOLUTE_PATH>/src/main/resources/test-dictionary.txt

_with multiple input files and dict_

4. <PATH>/NumberToWords  java -jar target/NumberToWords-1.0-SNAPSHOT.jar <ABSOLUTE_PATH>/src/main/resources/input.txt <ABSOLUTE_PATH>/src/main/resources/input1.txt -d<ABSOLUTE_PATH>/src/main/resources/test-dictionary.txt

_with multiple input file and no dict_

5. <PATH>/NumberToWords  java -jar target/NumberToWords-1.0-SNAPSHOT.jar <ABSOLUTE_PATH>/src/main/resources/input.txt <ABSOLUTE_PATH>/src/main/resources/input1.txt


**_Note: Wanted detail logging ? enable java util logging level to fine level_**