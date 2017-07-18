
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author NAVDEEP
 */
public class Automate_refminer
 {

    public static void func(String project_name) throws FileNotFoundException, IOException
    {
        int count=0;
        String directoryPathtooutpurfolder1="C:/RefTypeExtractorData/ExtractedData/RefactoringMinerInput";//+project_name;
                File out_directory1 = new File(String.valueOf(directoryPathtooutpurfolder1));
                if (! out_directory1.exists())
                {
                    out_directory1.mkdir();
                }

        String refactoring_file_name="C:/RefTypeExtractorData/ExtractedData/Refactoring_Commits/"+project_name+"_refactoring_commits_keywords.doc";
        BufferedReader br = new BufferedReader(new FileReader(refactoring_file_name));
        String refactoring_only_cid="C:/RefTypeExtractorData/ExtractedData/RefactoringMinerInput/"+project_name+"_refminer_input.doc";
        BufferedWriter w1 = new BufferedWriter(new FileWriter(refactoring_only_cid));
        String line="";
        while ((line = br.readLine()) != null)
            {
                if(line.isEmpty())
                {   continue;
                }
                        line=line.toLowerCase().trim();

                        String ar[]=line.split(" ");
                        if(ar.length>=3)
                        {
                        //System.out.println("line is  "+line+"  length is  "+line.length());
                        //System.out.println("JUST THERE");
                            if(ar[1].equals("commit") )
                            {
                                if(ar[2].length()==40)
                                {
                                count++;
                                    w1.write(ar[2]+"\n");
                                }
                            }
                    }
            }
        System.out.println("COUNT IN REF INPUT IS  "+count);
            br.close();
            w1.flush();
            w1.close();
    }

    public static void main(String[] args) throws IOException
    {
        Scanner sc=new Scanner(System.in);
        String project_name="";
        //System.out.println("Enter project name");
        project_name=args[0];
		//project_name=args[0];
		System.out.println("Project name is inside auto  "+project_name);
        func(project_name);
        // TODO code application logic here
    }
}
