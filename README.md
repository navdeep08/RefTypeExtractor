# RefTypeExtractor

RefTypeExtractor is a Java API that distribute commit messages of java projects into their respective refactoring activities.
Currently, it detects following refactorings activities with the help of RefactoringMiner tool:
				
1. Extract Method
2. Extract Interface
3. Extract Superclass
4. Rename Method
5. Rename Class
6. Inline Method
7. Push Down Attribute
8. Push Down Method
9. Pull Up Attribute
10. Pull Up Method
11. Extract And Move Method
12. Move Attribute
13. Move Method
14. Move Class
15. Move Class Folder


How to Run RefTypeExtractor:
---	
	1. Clone this project and save at location "c:\"
	2. As this tool uses the services of RefactoringMiner tool, So install and configure RefactoringMiner tool from  https://github.com/tsantalis/RefactoringMiner  
	3. After installing RefactoringMiner, prepare a list of GitHub projects on which RefTypeExtractor is to be applied. This list of projects should be given in file named Project_list.txt in the root directory of ReftypeExtractor. Information about each project includes the URL of project separated by its name.
	4. Run RefTypeExtractor.bat file to start RefTypeExtractor


Working of RefTypeExtractor:
---
After taking a list of input projects this tool performs the following operations:
* Clone each project using project’s URL in C:\ReftypeExtractorData\GitProjects.
* Extracts and Store Commit Messages of each project  in C:\RefTypeExtracterData\ExtractedData\commits folder.
* Assigns serail numbers to each commit message starting from 1 to a total number of commit messages and store them at C:\RefTypeExtracterData\ExtractedData\Commits_with_numbers.
* It mine commit messages related to refactoring by searching 14 keywords  (such as “refactor” and “code clean”) in each commit message and store them separately at C:\RefTypeExtracterData\ExtractedData\Refactoring_Commits. 
* RefactoringMiner tool then detects the refactoring activities performed in each commit message and results are stored in C:\RefTypeExtracterData\ExtractedData\RefactoringMinerResult
* Finally, it distribute commits into their respective refactoring types and results are stored at C:\RefTypeExtracterData\ExtractedData\Refactoring_Commits.

