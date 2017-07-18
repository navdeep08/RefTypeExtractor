
import java.io.*;
import java.util.*;

class number_commits
{

    public static void give_numbers_to_commits(String project_name) throws FileNotFoundException, IOException
    {

    		String directoryPathtooutpurfolder1="C:/RefTypeExtractorData/ExtractedData/Commits_with_numbers";
                File out_directory1 = new File(String.valueOf(directoryPathtooutpurfolder1));
                if (! out_directory1.exists())
                {
                    out_directory1.mkdir();
                }

		String directoryPathtooutpurfolder2="C:/RefTypeExtractorData/ExtractedData/Refactoring_Commits";
                File out_directory2 = new File(String.valueOf(directoryPathtooutpurfolder2));
                if (! out_directory2.exists())
                {
                    out_directory2.mkdir();
                }

        String input_file_name="C:/RefTypeExtractorData/ExtractedData/Commits/"+project_name+".doc";
        String commits_with_numbers="C:/RefTypeExtractorData/ExtractedData/Commits_with_numbers/"+project_name+".doc";
        String keywords_file_name="C:/RefTypeExtractor/keywords.doc";

        try
        {
        BufferedReader br = new BufferedReader(new FileReader(input_file_name));
        LinkedHashMap<String, String> hm=new LinkedHashMap<String,String>();
        //Create_Excel_File obj=new Create_Excel_File();
        BufferedWriter br2= new BufferedWriter(new FileWriter(commits_with_numbers));
        String refactoring_file_name="C:/RefTypeExtractorData/ExtractedData/Refactoring_Commits/"+project_name+"_refactoring_commits_keywords.doc";
         BufferedWriter br1= new BufferedWriter(new FileWriter(refactoring_file_name));
        BufferedReader read_keyword1 = new BufferedReader(new FileReader(keywords_file_name));
            String keyword1="";
            int total_refactoring_commits=0;
            while((keyword1 = read_keyword1.readLine())!= null)
            {
            hm.put(keyword1.toLowerCase(),"0");
            }

            //obj.showhashmap(hm);
            hm.remove("?");
            hm.remove("");
            //obj.showhashmap(hm);


            String commit="";
            String author="";
            String date="";
            String merge="";
            String line="";
            int count=1;
            String msg="";

            while ((line = br.readLine()) != null)
            {
                line=line.toLowerCase();
                    if(line.startsWith("commit"))
                    {
                            if(commit!="" )
                            {
                            Set<Map.Entry<String, String>> s= hm.entrySet();
                                for(Map.Entry<String, String> i:s)
                                {
                                    if(msg.contains(i.getKey()) && i.getValue()!=null)
                                    {
                                    //System.out.println("MATCH");
                                    br1.write("\n");

                                    br1.write(String.valueOf(count));
                                    br1.write(" " +commit);
                                    br1.write("\n");
                                    br1.write(" " +author);
                                    br1.write("\n");
                                    br1.write(" " +date);
                                    br1.write("\n");
                                    msg=msg.trim().replaceAll("\\s+", " ") + " ";

                                    br1.write("\n"+msg);
                                    br1.write("\n");
                                    //br1.write("\n");
                                    total_refactoring_commits++;
                                    break;
                                    }
                            else if(msg.contains(i.getKey()) && i.getValue()==null)
                            {
                            br1.write(String.valueOf(count));
                            br1.write(commit);
                            br1.write("\n");
                            br1.write(author);
                            br1.write("\n");
                            br1.write(date);
                            br1.write("\n");
                            br1.write(msg);
                            br1.write("\n");
                            //br1.write("\n");
                            System.out.println("MATCH");
                            System.out.println(msg);
                            total_refactoring_commits++;
                            break;
                            }
                        }
                        br2.write(String.valueOf(count)+"   ");
                        br2.write(commit);
                        br2.write("\n");
                        if(merge!="")
                        {
                        br2.write(merge+"\n");
                        }
                        br2.write(author);
                        br2.write("\n");
                        br2.write(date);
                        br2.write("\n");
                        br2.write(msg);
                        br2.write("\n");
                        //br2.write("\n");

                            /*    System.out.println(count);
                                System.out.println(commit);
                                System.out.println(author);
                                System.out.println(date);
                                System.out.println(msg);
                                System.out.println();
                                System.out.println();
                            */
                        author="";
                        date="";
                        merge="";
                        count++;
                        }
                  commit=line;
                  msg="";
                  continue;
                  }

                    else if(line.startsWith("Merge:"))
                    {
                        merge=line;
                        continue;
                    }
                    else if(line.startsWith("author"))
                    {
                        author=line;
                        continue;
                    }
                    else if(line.startsWith("date"))
                    {
                        date=line;
                        continue;
                    }

                    if(line.contains("git-svn-id:"))
                    {
                        line = br.readLine();
                        continue;
                    }


                    msg=msg+"\n"+line;

                }

                       Set<Map.Entry<String, String>> s= hm.entrySet();
                                for(Map.Entry<String, String> i:s)
                                {
                                    if(msg.contains(i.getKey()) && i.getValue()!=null)
                                    {
                                    System.out.println("MATCH");
                                    br1.write("\n");

                                    br1.write(String.valueOf(count));
                                    br1.write(" " +commit);
                                    br1.write("\n");
                                    br1.write(" " +author);
                                    br1.write("\n");
                                    br1.write(" " +date);
                                    br1.write("\n");
                                    msg=msg.trim().replaceAll("\\s+", " ") + " ";

                                    br1.write("\n"+msg);
                                    br1.write("\n");
                                    //br1.write("\n");
                                    total_refactoring_commits++;
                                    }
                                    else if(msg.contains(i.getKey()) && i.getValue()==null)
                                    {
                                    br1.write(String.valueOf(count));
                                    br1.write(commit);
                                    br1.write("\n");
                                    br1.write(author);
                                    br1.write("\n");
                                    br1.write(date);
                                    br1.write("\n");
                                    br1.write(msg);
                                    br1.write("\n");
                                    //br1.write("\n");
                                    System.out.println("MATCH");
                                    System.out.println(msg);
                                    total_refactoring_commits++;
                                    }
                                }
                            br2.write(String.valueOf(count)+"   ");
                            br2.write(commit);
                            br2.write("\n");
                            if(merge!="")
                            {
                            br2.write(merge+"\n");
                            }
                        br2.write(author);
                        br2.write("\n");
                        br2.write(date);
                        br2.write("\n");
                        br2.write(msg);
                        br2.write("\n");
                        //br2.write("\n");

                            /*    System.out.println(count);
                                System.out.println(commit);
                                System.out.println(author);
                                System.out.println(date);
                                System.out.println(msg);
                                System.out.println();
                                System.out.println();
                            */
                        author="";
                        date="";
                        merge="";
                        count++;

                br2.flush();
				 BufferedWriter br3= new BufferedWriter(new FileWriter("c:/RefTypeExtractorData/ExtractedData/Refactoring_Commits/Summary.doc"));

                br3.write(project_name+"  "+"      TOTAL COMMITS OF REFACTORING ARE:   "+total_refactoring_commits);
            br3.flush();
			br3.close();
			br2.close();

            br1.flush();
            br1.close();
        }
        catch(Exception e)
        {
            //total_commits=count-1;
            //System.out.println("*****************************************************************************************************************************TOTAL NUMBER OF COMMITS ARE   :  "+ total_commits);

            System.out.println("EXCEPTION IS       "+e);
        }
    }

    public static void main(String[] args) throws IOException
    {
        String project_name="";
        project_name=args[0];
		System.out.println("PROJECT IS   "+project_name);
        give_numbers_to_commits(project_name);
	}
}
