
import java.io.*;
import java.util.*;

public class Distribute_commits_into_ref_types
{

    public static void check(String project_name) throws FileNotFoundException, IOException
    {
        /*
        This method checks the count of refactorings of particular type
        Not important method*/
        String input2="C:/RefTypeExtractorData/ExtractedData/RefactoringMinerResult/"+project_name+"_RefactoringMiner_modified.doc";
        BufferedReader br = new BufferedReader(new FileReader(input2));
        String line;
        int count =0;
            while ((line = br.readLine()) != null)
            {
                if(line.startsWith("0 refactorings"))
                {
                    count++;
                }
            }
            System.out.println("Count is==  "+count);
    }



    public static void search_in_refminer(String project_name) throws FileNotFoundException, IOException
    {


        /*
        This method searches for types of refactorings that are detected by refactoring Miner
        */
        String input="C:/RefTypeExtractorData/ExtractedData/RefactoringMinerResult/"+project_name+"_RefactoringMiner.doc";
        BufferedReader br = new BufferedReader(new FileReader(input));

        String output="C:/RefTypeExtractorData/ExtractedData/RefactoringMinerResult/search_in.doc";
        BufferedWriter fw = new BufferedWriter(new FileWriter(output));
        String line;
            while ((line = br.readLine()) != null)
            {
                if(line.startsWith("and is"))
                {
                    if(!line.contains("Extract Method") && !line.contains("Extract Superclass") && !line.contains("Extract Interface") &&
                            !line.contains("Rename Method") && !line.contains("Rename Class") && !line.contains("Inline Method") &&
                            !line.contains("Push Down Method") && !line.contains("Extract And Move Method") && !line.contains("Move Attribute") &&
                            !line.contains("Move Method") && !line.contains("Move Class") &&
                            !line.contains("Move Class Folder")
                        && !line.contains("Push Down Attribute") && !line.contains("Pull Up Method") &&  !line.contains("Pull Up Attribute"))
                    {
                        fw.write(line);
                        fw.write("\n");
                    }

                }




            }
            fw.flush();
            fw.close();

    }

    public static void modify_refactoringMinner_result(String project_name) throws FileNotFoundException, IOException
    {
        /*
        This method is modifying the result file given by refactoring miner.
        Basically this project removes that lines which are not required and which do not give any infromarion about type of refactoring performed.
        */

        String input="C:/RefTypeExtractorData/ExtractedData/RefactoringMinerResult/"+project_name+"_RefactoringMiner.doc";
        BufferedReader br = new BufferedReader(new FileReader(input));

        String output="C:/RefTypeExtractorData/ExtractedData/RefactoringMinerResult/"+project_name+"_RefactoringMiner_modified.doc";
        BufferedWriter fw = new BufferedWriter(new FileWriter(output));

        String line;
        int flag =0;
            while ((line = br.readLine()) != null)
            {
                String split_result[]= line.split(" ");
                if(split_result[1].equals("refactorings") && flag==0)
                {
                    fw.write("\n");
                    fw.write(line);
                    flag=1;
                    continue;
                }
                if(flag==1)
                {
                    if(line.contains("...") && line.contains("Checking out"))
                    {}
                    else
                        fw.write(line+"\n");
                }


            }
            fw.flush();
            fw.close();
    }

    public static void distribute_commits_into_refTypes(String project_name) throws FileNotFoundException, IOException {
        /*
        1. Extract Method
2. Inline Method
3. Move Method/Attribute
4. Pull Up Method/Attribute
5. Push Down Method/Attribute
6. Extract Superclass/Interface
7. Move Class
8. Rename Class
9. Rename Method

         */

        ArrayList Extract_Method = new ArrayList();
        ArrayList Extract_Superclass = new ArrayList();
        ArrayList Extract_Interface = new ArrayList();
        ArrayList Rename_Method = new ArrayList();
        ArrayList Rename_Class = new ArrayList();
        ArrayList Inline_Method = new ArrayList();
        ArrayList Push_Down_Method = new ArrayList();
        ArrayList Push_Down_Attribute = new ArrayList();
        ArrayList Pull_Up_Method = new ArrayList();
        ArrayList Pull_Up_Attribute = new ArrayList();
        ArrayList Extract_And_Move_Method = new ArrayList();
        ArrayList Move_Attribute = new ArrayList();
        ArrayList Move_Method = new ArrayList();
        ArrayList Move_Class = new ArrayList();
        ArrayList Move_Class_Folder = new ArrayList();
        ArrayList Not_Refactoring = new ArrayList();
        String input1 = "C:/RefTypeExtractorData/ExtractedData/Refactoring_Commits/" + project_name + "_refactoring_commits_keywords.doc";
        BufferedReader br1 = new BufferedReader(new FileReader(input1));
        String input2 = "C:/RefTypeExtractorData/ExtractedData/RefactoringMinerResult/" + project_name + "_Ref.doc";
        String commit = "";
        String author = "";
        String date = "";
        String read_line1 = "";
        String commit_no = "";
        String commit_id = "";
        int commits_analysed = 0;
        while ((read_line1 = br1.readLine()) != null)
        {
            if (read_line1.isEmpty() )
            {
                System.out.println("Empty");
                continue;
            }
            System.out.println(read_line1);
            String ar1[] = read_line1.split(" ");
            if(ar1.length<3)
            {
                continue;
            }
//System.out.println("0=   "+ar1[0]+" 1=   "+ar1[1]);
            if (ar1[1].equals("commit"))
            {
                commit_id = ar1[2];
                commit_no = ar1[0];
                //System.out.println("Commit");
                commit = read_line1;
                continue;
            }
            else if (ar1[1].equals("author:"))
            {
                //System.out.println("Author");
                author = read_line1;
                continue;
            }
            else if (ar1[1].equals("date:"))
            {
                //System.out.println("Date");
                author = read_line1;
                String msg1 = "";
                msg1 = commit_no + " commit " + commit_id;//+"\n"+msg1;
                read_line1 = br1.readLine();
                //System.out.println("LINE after date: " + read_line1);
                read_line1 = br1.readLine();
                //System.out.println("LINE after date+ 1 line: " + read_line1);

                //This loop is used to extract one message
                String ar3[] = new String[3];
                while (true)
                {
                    //System.out.println("SUBSTRING IS HERE"+read_line1.substring(2, 8));
                    //System.out.println("INSIDE MSG  " + read_line1);
                    msg1 = msg1 + " " + read_line1;
                    read_line1 = br1.readLine();

                    //if file ends then stop reading
                    if (read_line1 == null)
                    {
                        break;
                    }
                    //System.out.println("NEXT LINE READED  " + read_line1);

                    if (read_line1.isEmpty())
                    {
                        //System.out.println("LINE IS EMPTY");
                        read_line1 = br1.readLine();

                        //if file ends then stop reading
                        if (read_line1 == null)
                        {
                            break;
                        }
                    }

                    if (read_line1.length() < 8)
                    {
                        msg1 = msg1 + " " + read_line1;
                        read_line1 = br1.readLine();
                        //if file ends then stop reading
                        if (read_line1 == null) {
                            break;
                        }
                    }

                    if (read_line1.isEmpty())
                    {
                        //System.out.println("LINE IS EMPTY");
                        read_line1 = br1.readLine();
                        //if file ends then stop reading
                        if (read_line1 == null)
                        {
                            break;
                        }
                    }
                    ar3 = read_line1.split(" ");
                    // Commit message completed
                    if (ar3[1].equals("commit")) {
                        commit = read_line1;
                        //System.out.println("COMMIT FOUND");
                        break;
                    }
                }

                msg1 = msg1 + "\n";
                //System.out.println("MESSAGE IS       " + msg1);
                //Now we have commit message in msg1 variable and commit id in ar1[2]. Now we have to
                //1. find result line of corresponding commit id in RefactoringMiner Result file.
                //2. after that add commit message into corresponding array of corressponding refactoring type(using "and is")

                //Step1.
                String read_line2;
                BufferedReader br2 = new BufferedReader(new FileReader(input2));

                while ((read_line2 = br2.readLine()) != null)
                {
                    if (read_line2.isEmpty()) {
                        //System.out.println("Empty || less than 5 length");
                        continue;
                    }
                    String c_ar[] = read_line2.split(" ");
                    if (c_ar.length < 6)
                    {
                        //System.out.println("NEW ADDED");
                        continue;
                    }

                    String ar2[] = read_line2.split(" ");
                    //System.out.println("ar[0]          = " + ar2[0]);
                    //System.out.println("ar2[1] =  "+ar2[1]+"  commit_id  ="+ commit_id+"  ar2[5]  = "+ ar2[5]);
                    //System.out.println("RESULT OF REFMINER IS       "+ read_line2 );
                    //for(int i=0;i<ar2.length;i++)
                    //{
                    //    System.out.println(ar2[i]);
                    //}

                    if (ar2[1].equals("refactorings") && ((commit_id).equals(ar2[5]) || (commit_id+":").equals(ar2[5])))
                    {
                        if (read_line2.startsWith("No refactorings"))
                        {
                            //  System.out.println(" O REfactorings");
                            //length=Not_Refactoring.length;
                            System.out.println("NO REFACTORING FOUND");
                            Not_Refactoring.add(msg1);
                            commits_analysed++;
                            break;
                        }
                        else
                        {
                            int number = Integer.parseInt(ar2[0]);
                            int count = 0;
                            int flags[] = new int[16];
                            for (int i = 0; i <= 15; i++)
                            {
                                flags[i] = 0;
                            }
                            read_line2=br2.readLine();
                            while (count != number && read_line2 != null)
                            {

                                if (read_line2.isEmpty())
                                {

                                    read_line2 = br2.readLine();
                                    continue;
                                }
                                read_line2 = read_line2.substring(2);
                                //System.out.println("Substrng is"+read_line2);
                                if (read_line2.startsWith("Extract Method"))
                                {
                                    if(flags[1] != 0)
                                    {
                                       System.out.println("ALREADY ADDED");
                                       count++;
                                       //continue;
                                    }
                                    else
                                    {
                                    System.out.println(commit_id+"   added to  Extract Method");
                                    Extract_Method.add(msg1);
                                    flags[1]++;
                                    count++;
                                    }
                                }
                                else if (read_line2.startsWith("Extract Superclass") )//&& flags[2] == 0)
                                {
                                   if(flags[2] != 0)
                                    {
                                       System.out.println("ALREADY ADDED");
                                       count++;
                                       //continue;
                                    }
                                    else
                                    {

                                    System.out.println(commit_id+"   added to  Extract Superclass");
                                    Extract_Superclass.add(msg1);
                                    flags[2]++;
                                    count++;
                                }
                                } else if (read_line2.startsWith("Extract Interface"))// && flags[3] == 0)
                                {
                                      if(flags[3] != 0)
                                    {
                                       System.out.println("ALREADY ADDED");
                                       count++;
                                       //continue;
                                    }
                                    else
                                    {

                                    System.out.println(commit_id+"   added to  Extract Interface");
                                    Extract_Interface.add(msg1);
                                    flags[3]++;
                                    count++;
                                    }
                                } else if (read_line2.startsWith("Rename Method"))// && flags[4] == 0)
                                {
                                      if(flags[4] != 0)
                                    {
                                       System.out.println("ALREADY ADDED");
                                       count++;
                                       //continue;
                                    }
                                    else
                                    {

                                    System.out.println(commit_id+"   added to  Rename Method");
                                    Rename_Method.add(msg1);
                                    flags[4]++;
                                    count++;
                                    }
                                }
                                else if (read_line2.startsWith("Rename Class"))// && flags[5] == 0)
                                {
                                      if(flags[5] != 0)
                                    {
                                       System.out.println("ALREADY ADDED");
                                       count++;
                                       //continue;
                                    }
                                    else
                                    {

                                    System.out.println(commit_id+"   added to  Rename class");
                                    Rename_Class.add(msg1);
                                    flags[5]++;
                                    count++;
                                    }
                                } else if (read_line2.startsWith("Inline Method"))// && flags[6] == 0) {
                                {if(flags[6] != 0)
                                    {
                                       System.out.println("ALREADY ADDED");
                                       count++;
                                       //continue;
                                    }
                                    else
                                    {
                                     System.out.println(commit_id+"   added to  INLINE Method");
                                    Inline_Method.add(msg1);
                                    flags[6]++;
                                    count++;
                                    }
                                }
                                else if (read_line2.startsWith("Push Down Method"))// && flags[7] == 0)
                                {
                                      if(flags[7] != 0)
                                    {
                                       System.out.println("ALREADY ADDED");
                                       count++;
                                       //continue;
                                    }
                                    else
                                    {

                                    System.out.println(commit_id+"   added to  PUSH DOWN Method");
                                    Push_Down_Method.add(msg1);
                                    flags[7]++;
                                    count++;
                                }
                                } else if (read_line2.startsWith("Push Down Attribute")) //&& flags[8] == 0)
                                {  if(flags[8] != 0)
                                    {
                                       System.out.println("ALREADY ADDED");
                                       count++;
                                       //continue;
                                    }
                                    else
                                    {

                                    System.out.println(commit_id+"   added to  PUSH UP ATTRIBUTE");
                                    Push_Down_Attribute.add(msg1);
                                    flags[8]++;
                                    count++;
                                    }
                                }
                                else if (read_line2.startsWith("Pull Up Method"))// && flags[9] == 0)
                                {
                                   if(flags[9] != 0)
                                    {
                                       System.out.println("ALREADY ADDED");
                                       count++;
                                       //continue;
                                    }
                                    else
                                    {
                                     System.out.println(commit_id+"   added to  PULL UP Method");
                                    Pull_Up_Method.add(msg1);
                                    flags[9]++;
                                    count++;
                                }
                                } else if (read_line2.startsWith("Pull Up Attribute"))// && flags[10] == 0)
                                {
                                   if(flags[10] != 0)
                                    {
                                       System.out.println("ALREADY ADDED");
                                       count++;
                                       //continue;
                                    }
                                    else
                                    {
                                     System.out.println(commit_id+"   added to  PULL DOWN ATTRIBUTE");
                                    Pull_Up_Attribute.add(msg1);
                                    flags[10]++;
                                    count++;
                                    }
                                }
                                else if (read_line2.startsWith("Extract And Move Method"))// && flags[11] == 0)
                                {
                                   /* if (flags[1] == 0) {
                                        Extract_Method.add(msg1);
                                        flags[1]++;
                                    }
                                    if (flags[13] == 0) {
                                        Move_Method.add(msg1);
                                        flags[13]++;
                                    }

*/                                if(flags[11] != 0)
                                    {
                                       System.out.println("ALREADY ADDED");
                                       count++;
                                       //continue;
                                    }
                                    else
                                    {

                                    Extract_And_Move_Method.add(msg1);
                                    flags[11]++;
                                    count++;
                                    }
                                }
                                else if (read_line2.startsWith("Move Attribute"))// && flags[12] == 0)
                                {
                                   if(flags[12] != 0)
                                    {
                                       System.out.println("ALREADY ADDED");
                                       count++;
                                       //continue;
                                    }
                                    else
                                    {
                                      System.out.println(commit_id+"   Move Attributre");

                                    Move_Attribute.add(msg1);
                                    flags[12]++;
                                    count++;
                                }
                                } else if (read_line2.startsWith("Move Method")) //&& flags[13] == 0)
                                        {
                                      if(flags[13] != 0)
                                    {
                                       System.out.println("ALREADY ADDED");
                                       count++;
                                       //continue;
                                    }
                                    else
                                    {

                                     System.out.println(commit_id+"   added to  MOve method");

                                    Move_Method.add(msg1);
                                    flags[13]++;
                                    count++;
                                    }
                                }
                                else if (read_line2.startsWith("Move Class Folder") )//&& flags[15] == 0)
                                {
                                  if(flags[15] != 0)
                                    {
                                       System.out.println("ALREADY ADDED");
                                       count++;
                                       //continue;
                                    }
                                    else
                                    {
                                       System.out.println(commit_id+"   added to  MOVE CLASS FOLDER");

                                    Move_Class_Folder.add(msg1);
                                    flags[15]++;
                                    count++;
                                }
                                }
                                else if (read_line2.startsWith("Move Class"))// && flags[14] == 0)
                                {
                                  if(flags[14] != 0)
                                    {
                                       System.out.println("ALREADY ADDED");
                                       count++;
                                       //continue;
                                    }
                                    else
                                    {

                                    System.out.println(commit_id+"   added to  Move class");

                                    Move_Class.add(msg1);
                                    flags[14]++;
                                    count++;
                                    }
                                }
                                read_line2 = br2.readLine();

                            }
                            msg1="";
                            break;
                        }

                    }

                }
                br2.close();
                commit_no = ar3[0];
                commit_id = ar3[2];
            }
  //          msg="";
        }

        //1.    Extract Method
        //2.    Extract Interface
        //3.    Extract Superclass
        //4.    Rename Method
        //5.    Rename Class
        //6.    Inline Method
        //7.    Push Down Attribute
        //8.    Push Down Method
        //9.    Pull Up Attribute
        //10.   Pull Up Method
        //11.   Extract And Move Method
        //12.   Move Attribute
        //13.   Move Method
        //14.   Move Class
        //15.   Move Class Folder
        //16.   No refactoring
        //Create New Folder
        String directoryPathtooutpurfolder1 = "C:/RefTypeExtractorData/ExtractedData/Refactoring_Commits/" + project_name;//+project_name;
        File out_directory1 = new File(String.valueOf(directoryPathtooutpurfolder1));
        if (!out_directory1.exists()) {
            out_directory1.mkdir();
        }

        String path = "C:/RefTypeExtractorData/ExtractedData/Refactoring_Commits/" + project_name + "/";
        System.out.println("PATH IS"+ path);
            //BufferedWriter summary=new BufferedWriter(new File(path+"/"+summary+"_"+project_name+".doc"));
        String[] out1 = new String[Extract_Method.size()];
        Extract_Method.toArray(out1);
        System.out.println("LENGTH of  Extract_Method   " + Extract_Method.size());
        //show_array(out1);
        Make_file(out1, path, 1);

        String[] out2 = new String[Extract_Interface.size()];
        Extract_Interface.toArray(out2);
        System.out.println("LENGTH of  Extract_Interface    " + Extract_Interface.size());
        //show_array(out2);
        Make_file(out2, path, 2);

        String[] out3 = new String[Extract_Superclass.size()];
        Extract_Superclass.toArray(out3);
        System.out.println("LENGTH of  Extract_Superclass   " + Extract_Superclass.size());
        //show_array(out3);
        Make_file(out3, path, 3);

        String[] out4 = new String[Rename_Method.size()];
        Rename_Method.toArray(out4);
        System.out.println("LENGTH of  Rename_Method    " + Rename_Method.size());
        //show_array(out4);
        Make_file(out4, path, 4);

        String[] out5 = new String[Rename_Class.size()];
        Rename_Class.toArray(out5);
        System.out.println("LENGTH of  Rename_Class     " + Rename_Class.size());
        //show_array(out5);
        Make_file(out5, path, 5);

        String[] out6 = new String[Inline_Method.size()];
        Inline_Method.toArray(out6);
        System.out.println("LENGTH of  Inline_Method    " + Inline_Method.size());
        //show_array(out6);
        Make_file(out6, path, 6);

        String[] out7 = new String[Push_Down_Attribute.size()];
        Push_Down_Attribute.toArray(out7);
        System.out.println("LENGTH of Push_Down_Attribute   " + Push_Down_Attribute.size());
        //show_array(out7);
        Make_file(out7, path, 7);

        String[] out8 = new String[Push_Down_Method.size()];
        Push_Down_Method.toArray(out8);
        System.out.println("LENGTH of Push_Down_Method  " + Push_Down_Method.size());
        //show_array(out8);
        Make_file(out8, path, 8);

        String[] out9 = new String[Pull_Up_Attribute.size()];
        Pull_Up_Attribute.toArray(out9);
        System.out.println("LENGTH of  Pull_Up_Attribute    " + Pull_Up_Attribute.size());
        //show_array(out9);
        Make_file(out9, path, 9);

        String[] out10 = new String[Pull_Up_Method.size()];
        Pull_Up_Method.toArray(out10);
        System.out.println("LENGTH of Pull_Up_Method    " + Pull_Up_Method.size());
        //show_array(out9);
        Make_file(out10, path, 10);

        String[] out11 = new String[Extract_And_Move_Method.size()];
        Extract_And_Move_Method.toArray(out11);
        System.out.println("LENGTH of  Extract_And_Move_Method  " + Extract_And_Move_Method.size());
        //show_array(out11);
        Make_file(out11, path, 11);

        String[] out12 = new String[Move_Attribute.size()];
        Move_Attribute.toArray(out12);
        System.out.println("LENGTH of Move_Attribute    " + Move_Attribute.size());
        //show_array(out12);
        Make_file(out12, path, 12);

        String[] out13 = new String[Move_Method.size()];
        Move_Method.toArray(out13);
        System.out.println("LENGTH of Move_Method   " + Move_Method.size());
        //show_array(out13);
        Make_file(out13, path, 13);

        String[] out14 = new String[Move_Class.size()];
        Move_Class.toArray(out14);
        System.out.println("LENGTH of Move_Class    " + Move_Class.size());
        //show_array(out14);
        Make_file(out14, path, 14);

        String[] out15 = new String[Move_Class_Folder.size()];
        Move_Class_Folder.toArray(out15);
        System.out.println("LENGTH of Move_Class_Folder  " + Move_Class_Folder.size());
        //show_array(out15);
        Make_file(out15, path, 15);

        String[] out16 = new String[Not_Refactoring.size()];
        Not_Refactoring.toArray(out16);
        System.out.println("LENGTH IS  " + Not_Refactoring.size());
        //show_array(out16);
        Make_file(out16, path, 16);

        int total = out1.length + out2.length + out3.length + out4.length + out5.length + out6.length + out7.length + out8.length + out9.length
                + out10.length + out11.length + out12.length + out13.length + out14.length + out15.length + out16.length;
        System.out.println(out1.length + "  " + out2.length + "  " + out3.length + "  " + out4.length + "  " + out5.length + "  " + out6.length + "  "
                + out7.length + "  " + out8.length + "  " + out9.length
                + "  " + out10.length + "  " + out11.length + "  " + out12.length + "  " + out13.length + "  " + out14.length + "  " + out15.length + "  " + out16.length);

        System.out.println("TOTAL REFACTORING COMMITS ANALYSED ARE  :" + total);
        System.out.println("commits_analysed=   " + commits_analysed);

    }


    public static void Make_file(String ar[],String path,int index) throws IOException
    {
        String names[]={" ","Extract Method","Extract Interface","Extract Superclass" ,"Rename Method","Rename Class","Inline Method",
                            "Push Down Attribute","Push Down Method","Pull Up Attribute", "Pull Up Method","Extract and Move Method", "Move Attribute",
        "Move Method","Move Class","Move Class Folder", "No Refactoring"};
        path= path+names[index]+".doc";
        if(ar.length==0)
        {
        }
        else{
        BufferedWriter fw = new BufferedWriter(new FileWriter(path));
        for(int i=0;i<ar.length;i++)
        {
            fw.write(ar[i]);
            fw.write("\n");
        }
        fw.flush();
        fw.close();
        }

    }

        public static void show_array(String ar[])
        {
            System.out.println("ARRAY IS");
            for(int i=0;i<ar.length;i++)
            {
                System.out.println(ar[i]);

            }
        }


    public static void main(String[] args) throws IOException
    {
        String project_name="";
        project_name=args[0];
	System.out.println("PROJECT IS   "+project_name);
        //search_in_refminer(project_name);
        //modify_refactoringMinner_result(project_name);
        distribute_commits_into_refTypes(project_name);
        //check(project_name);
    }
}
