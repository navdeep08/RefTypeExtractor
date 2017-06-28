@echo off
setlocal EnableDelayedExpansion
set file= C:\RefTypeExtractor\Project_list.txt
echo "hi"
Rem Create folder projects if not created. This folder will hold projects cloned from Github
if not exist "C:\RefTypeExtractorData" mkdir C:\RefTypeExtractorData
if not exist "C:\RefTypeExtractorData\GitProjects" mkdir C:\RefTypeExtractorData\GitProjects
if not exist "C:\RefTypeExtractorData\ExtractedData" mkdir C:\RefTypeExtractorData\ExtractedData
if not exist "C:\RefTypeExtractorData\ExtractedData\commits" mkdir C:\RefTypeExtractorData\ExtractedData\commits
if not exist "C:\RefTypeExtractorData\ExtractedData\RefactoringMinerResult" mkdir C:\RefTypeExtractorData\ExtractedData\RefactoringMinerResult
if not exist "C:\RefTypeExtractorData\ExtractedData\Commits_with_numbers" mkdir C:\RefTypeExtractorData\ExtractedData\Commits_with_numbers
Rem Now For each project that is present in the file
for /f "tokens=1,2 delims= " %%a in (%file%)do ( 

Rem Project URL
set x=%%a
set y=%%b
echo %x% 
echo %y%  

Rem change directory to Projects because we have to clone the project into projects folder

cd/
cd c:\RefTypeExtractorData\GitProjects
Rem clone project
git clone %%a

cd %%b
echo "CHECKOUT"
git checkout master

echo "Extracting Commits"
git log> C:\RefTypeExtractorData\ExtractedData\commits\%%b.doc

cd../../../../RefTypeExtractor/src
echo "directory is:"
ls

echo "var is: %%b"
javac number_commits.java 
java number_commits %%b
javac Automate_refminer.java
java Automate_refminer %%b

Rem for applying refactoring miner to our project. We have to go to parent directory of project. Now We are in src directory.
cd ..
echo "directory is:"
ls

echo "var is: %%b"

echo "RefactoringMiner working"
set rfile=c:\RefTypeExtractorData\ExtractedData\RefactoringMinerInput\%%b_refminer_input.doc
echo file is !rfile!
for /f "tokens=1" %%h in (!rfile!)do (
cd../RefTypeExtractorData/GitProjects
ls
Rem echo here %%h
Refactoringminer %%b %%h>>C:\RefTypeExtractorData\ExtractedData\RefactoringMinerResult\%%b_Ref.doc
)
echo ref ends


cd../../RefTypeExtractor/src
echo "LIST DIRECTORIES ARE"
ls
javac Distribute_commits_into_ref_types.java
java Distribute_commits_into_ref_types %%b

)
