import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class CacheMemory {
    
    static ArrayList<Integer> a = new ArrayList<>();

    static ArrayList<Integer> a2 = new ArrayList<>();

    static Scanner s = new Scanner(System.in);

    public static int findone(int[] a) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] == 1) {
                return i;
            }
        }
        return -1;
    }

    public static boolean findint(int n) {
        for (int i = 0; i < a.size(); i++) {
            if (a.get(i).equals(n)) {
                return true;
            }
        }
        return false;
    }

    public static boolean findint2(int n) {
        for (int i = 0; i < a2.size(); i++) {
            if (a2.get(i).equals(n)) {
                return true;
            }
        }
        return false;
    }

    public static boolean find2(int n, ArrayList<Integer> a) {
        for (int i = 0; i < a.size(); i++) {
            if (a.get(i) == n) {
                return true;
            }
        }
        return false;
    }

    static int pow(int n, int p) {
        if (p == 0) {
            return 1;
        }
        if (p == 1) {
            return n;
        }
        return n * pow(n, p - 1);
    }

    static int BinaryTodec(String s) {
        int n = 0;
        for (int i = 0; i < s.length(); i++) {
            n += Integer.parseInt(Character.toString(s.charAt(i))) * pow(2, i);
        }
        return n;
    }

    static String reverse(String s) {
        String ns = "";
        for (int i = s.length() - 1; i >= 0; i--) {
            ns += s.charAt(i);
        }
        return ns;
    }

    public static int log2(int N) {

        // calculate log2 N indirectly
        // using log() method
        int result = (int) (Math.log(N) / Math.log(2));

        return result;
    }

    public static int find(String[] arr, String n) {
//        System.out.println(arr.length);
//        System.out.println(arr[0]);
        for (int i = 0; i < arr.length; i++) {
//            System.out.println(i);
            if (arr[i] != null) {
                if (arr[i].equals(n)) {
                    return i;
                }
            }
        }
        return -1;
    }

    static void associativemapping(){
        System.out.println("Enter cache size in KB");
        float n=s.nextFloat();
        System.out.println("Block size");
        int c=s.nextInt();
//        for calculating entries
        int nn=(int)(n*1024);
        int entries=nn/c;
//        int wordlength=c/4;
//        int wordlog=log2(wordlength);
//        System.out.println(entries);

//        entries are of 26 bit length
        String[] tagarry=new String[entries];
        int[] replacementarray=new int[entries];
        String[][] blockarray=new String[entries][c/4];
        int iforreplace=1;
        boolean moreinput=true;
        while(moreinput) {
            System.out.println("Enter 1 for write and and 2 for read and 3 print tag array and block data array");
            int option = s.nextInt();
            if (option == 1) {
                System.out.println("Enter address ");
                String input = s.next();
                System.out.println("input data");
                String data = s.next();
                String rinuput = reverse(input);
//            64 =2^6 blockoffsetnumber =6
                int blockoffsetnumber = log2(c/4);
                String blockoffsetinput= rinuput.substring(0, blockoffsetnumber);
                String taginput = rinuput.substring(blockoffsetnumber);
//                checking if tag already present
                String newtaginput=reverse(taginput);
                int finifpresent=find(tagarry,newtaginput);
                if(finifpresent!=-1){
//                    System.out.println(1);
                    int index = BinaryTodec(blockoffsetinput);
                    blockarray[finifpresent][index] = data;
                }
                else {
                    Random rand = new Random();
                    int randtagindex = rand.nextInt(entries);
//                    for cache full
                    if(iforreplace>entries) {

                        int indexofone=findone(replacementarray);
                        replacementarray[indexofone]=entries+1;
                        tagarry[indexofone] = reverse(taginput);
                        for (int i = 0; i < c/4; i++) {
                            blockarray[indexofone][i]=null;
                        }
                        for (int i = 0; i <replacementarray.length ; i++) {
                            replacementarray[i]=replacementarray[i]-1;
                        }


                        randtagindex=indexofone;

                    }
                    else{
                        if(!findint(randtagindex)){
                            a.add(randtagindex);

                            replacementarray[randtagindex]=iforreplace;
                            iforreplace++;
                            tagarry[randtagindex] = reverse(taginput);
                        }
                        else{
                            while(findint(randtagindex)) {
                                randtagindex=rand.nextInt(entries);
                            }
                            a.add(randtagindex);

                            replacementarray[randtagindex]=iforreplace;
                            iforreplace++;
                            tagarry[randtagindex] = reverse(taginput);
                        }

                    }
//            input in block array
                    int index = BinaryTodec(blockoffsetinput);
                    blockarray[randtagindex][index] = data;
                }
            }
            else if (option == 3) {
//                System.out.println("Enter address ");
//                String input = s.next();
                System.out.println("tag array");
                for (int i = 0; i < tagarry.length; i++) {
                    if (tagarry[i] == null) {
                        System.out.print(0 + " ");
                    } else {
                        System.out.print(tagarry[i] + " ");
                    }
                }
                System.out.println();
                System.out.println("data array");
                for (int i = 0; i < tagarry.length; i++) {
                    for (int j = 0; j < c/4; j++) {
                        if (blockarray[i][j] == null) {
                            System.out.print(0 + " ");
                        } else {
                            System.out.print(blockarray[i][j] + " ");
                        }
                    }
                    System.out.println();
                }
            }
            else if(option==2){
                System.out.println("Enter address ");
                String input = s.next();
                String rinuput = reverse(input);
                int blockoffsetnumber = log2(c/4);
                String taginput = reverse(rinuput.substring(blockoffsetnumber));
                int check=find(tagarry,taginput);
                if(check==-1){
                    System.out.println("address not found");
                }
                else {
                    String blockoffsetinput = rinuput.substring(0, blockoffsetnumber);
                    int index = BinaryTodec(blockoffsetinput);
                    System.out.println("cache hit ");
                    if(blockarray[check][index]==null){
                        System.out.println("data= 0");
                    }
                    else{
                        System.out.println("data= "+blockarray[check][index]);
                    }
                }
            }
//            System.out.println(a);
            System.out.println("Press 1 for more use otherwise 0 ");
            int m=s.nextInt();
            if(m==0){
                moreinput=false;
            }
        }

    }

    static void directmapping(){
        System.out.println("Enter cache size in KB for cache level1");
        float n=s.nextFloat();
        System.out.println("Block size");
        int c=s.nextInt();
//        for calculating entries
        int nn=(int)(n*1024);
        int entries=nn/c;
//        System.out.println(entries);

//        entries are of 26 bit length
        String[] tagarry=new String[entries];
        String[][] blockarray=new String[entries][c/4];

        boolean moreinput=true;
        while(moreinput){
            System.out.println("Enter 1 for write and and 2 for read and 3 print tag array and block data array");
            int option = s.nextInt();
            if (option == 1) {
                System.out.println("Enter address ");
                String input = s.next();
                System.out.println("input data");
                String data = s.next();
//            invert string for slecting offset
                String rinuput = reverse(input);
//            64 =2^6 blockoffsetnumber =6
                int blockoffsetnumber = log2(c/4);
                String blockoffsetinput = rinuput.substring(0, blockoffsetnumber);
                String taginput = rinuput.substring(blockoffsetnumber);
                int in=log2(entries);
                String indexinput=taginput.substring(0,in);
                taginput=taginput.substring(in);
                int index=BinaryTodec(indexinput);
//                for replacemet
                if(tagarry[index]==null || tagarry[index].equals(reverse(taginput))) {
                    tagarry[index] = reverse(taginput);
                    int blockoffsextindex = BinaryTodec(blockoffsetinput);
                    blockarray[index][blockoffsextindex] = data;
                }
                else {
                    tagarry[index]=reverse(taginput);
                    for (int i = 0; i <c/4 ; i++) {
                        blockarray[index][i]=null;
                    }
                    int blockoffsextindex=BinaryTodec(blockoffsetinput);
                    blockarray[index][blockoffsextindex]=data;
                }
            }
            else if(option==2){
                System.out.println("Enter address ");
                String input = s.next();
                String rinuput = reverse(input);
//            64 =2^6 blockoffsetnumber =6
                int blockoffsetnumber = log2(c/4);
                String blockoffsetinput = rinuput.substring(0, blockoffsetnumber);
                String taginput = rinuput.substring(blockoffsetnumber);
                int in=log2(entries);
                String indexinput=taginput.substring(0,in);
                taginput=taginput.substring(in);
                taginput=reverse(taginput);
                int index=BinaryTodec(indexinput);
                int blockoffsextindex=BinaryTodec(blockoffsetinput);
//                System.out.println(blockoffsextindex);
//                if(blockarray[index][blockoffsextindex]==null){
//                    System.out.println("address not found");
//                }
//                else{
//                    System.out.println("cache hit");
//                }
                if(tagarry[index].equals(taginput)){
                    System.out.println("cache hit");
                    if(blockarray[index][blockoffsextindex]==null){
                        System.out.println("data= 0");
                    }
                    else{
                        System.out.println("data= "+blockarray[index][blockoffsextindex]);
                    }
                }
                else{
                    System.out.println("address not found");
                }
            }
            else if (option==3){
                System.out.println("tag array");
                for (int i = 0; i < tagarry.length; i++) {
                    if (tagarry[i] == null) {
                        System.out.print(0 + " ");
                    } else {
                        System.out.print(tagarry[i] + " ");
                    }
                }
                System.out.println();
                System.out.println("data array");
                for (int i = 0; i < entries; i++) {
                    for (int j = 0; j < c/4; j++) {
                        if (blockarray[i][j] == null) {
                            System.out.print(0 + " ");
                        } else {
                            System.out.print(blockarray[i][j] + " ");
                        }
                    }
                    System.out.println();
                }
            }
            System.out.println("1 for more use otherwise 0");
            int nnn=s.nextInt();
            if(nnn==0){
                moreinput=false;
            }
        }
    }

    static void setassociativemapping(){
        System.out.println("Enter cache size in KB");
        float n=s.nextFloat();
        System.out.println("Block size");
        int c=s.nextInt();
        System.out.println("Enter value of n");
        int nofelementsinset=s.nextInt();
//        for calculating entries
        int nn=(int)(n*1024);
        int entries=nn/c;
//        System.out.println(entries);
        int noofset=entries/nofelementsinset;
        int index=log2(noofset);
//        entries are of 26 bit length
        String[][] tagarry=new String[noofset][nofelementsinset];
        int[][] replacemnet=new int[noofset][nofelementsinset];
        int[] highestinset=new int[noofset];
        String[][] blockarray=new String[entries][c/4];

        boolean moreinput=true;
        ArrayList<Integer> aa=new ArrayList<>();
        while(moreinput){
            System.out.println("Enter 1 for write and and 2 for read and 3 print tag array and block data array");
            int option = s.nextInt();
            if (option == 1) {
                System.out.println("Enter address ");
                String input = s.next();
                System.out.println("input data");
                String data = s.next();
                String rinuput = reverse(input);
//            64 =2^6 blockoffsetnumber =6
                int blockoffsetnumber = log2(c/4);
                String blockoffsetinput = rinuput.substring(0, blockoffsetnumber);
                String taginput = rinuput.substring(blockoffsetnumber);
//                taking last n bit from taginput as index
                String indexinput=taginput.substring(0,index);
                taginput=taginput.substring(index);
                int indexTag=BinaryTodec((indexinput));
//                System.out.println(indexTag);
//                aadding in random position
                Random rand = new Random();
                int randnum=rand.nextInt(nofelementsinset);

                String newtaginput=reverse(taginput);
                int finifpresent=find(tagarry[indexTag],newtaginput);
                if(finifpresent!=-1){
                    System.out.println(1);
                    int index11 = BinaryTodec(blockoffsetinput);
                    blockarray[finifpresent][index11] = data;
                }
                else{
//                    Random rand = new Random();
//                    int randtagindex = rand.nextInt(entries);
//                    for cache full
                    if(highestinset[indexTag]==nofelementsinset) {

                        int indexofone=findone(replacemnet[indexTag]);
                        replacemnet[indexTag][indexofone]=nofelementsinset+1;
                        tagarry[indexTag][indexofone] = reverse(taginput);
                        for (int i = 0; i < c/4; i++) {
                            blockarray[indexofone][i]=null;
                        }
                        for (int i = 0; i <replacemnet[indexTag].length ; i++) {
                            replacemnet[indexTag][i]=replacemnet[indexTag][i]-1;
                        }

                        randnum=indexofone;

                    }
                    else{
                        if(!find2(randnum,aa)){
//                            System.out.println(2);
                            aa.add(randnum);
                            highestinset[indexTag]=highestinset[indexTag]+1;
                            replacemnet[indexTag][randnum]=highestinset[indexTag];
                            tagarry[indexTag][randnum] = reverse(taginput);
                        }
                        else{
                            while(find2(randnum,aa)) {
                                randnum=rand.nextInt(nofelementsinset);
                            }
                            aa.add(randnum);

                            highestinset[indexTag]=highestinset[indexTag]+1;
                            replacemnet[indexTag][randnum]=highestinset[indexTag];
                            tagarry[indexTag][randnum] = reverse(taginput);
                        }

                    }
//            input in block array

                    tagarry[indexTag][randnum]=reverse(taginput);
                    int blocki=(indexTag*nofelementsinset)+randnum;
                    int blockj=BinaryTodec(blockoffsetinput);
                    blockarray[blocki][blockj]=data;
                }
//                System.out.println(aa);
            }
            else if(option==2){
                System.out.println("Enter address ");
                String input = s.next();
                String rinuput = reverse(input);
//            64 =2^6 blockoffsetnumber =6
                int blockoffsetnumber = log2(c/4);
                String blockoffsetinput = rinuput.substring(0, blockoffsetnumber);
                String taginput = rinuput.substring(blockoffsetnumber);
//                taking last n bit from taginput as index
                String indexinput=taginput.substring(0,index);
                taginput=taginput.substring(index);
                int indexTag=BinaryTodec((indexinput));
//                System.out.println(indexTag);
                String[] temp=tagarry[indexTag];
                int newindex=find(temp,reverse(taginput));
                if(newindex==-1){
                    System.out.println("address not found");
                }
                else{
                    int iforblock=(indexTag*nofelementsinset)+newindex;
                    int jforblockoffset=BinaryTodec(blockoffsetinput);
                    System.out.println("cache hit");
                    if(blockarray[iforblock][jforblockoffset]==null){
                        System.out.println("data= 0");
                    }
                    else{
                        System.out.println("data= "+blockarray[iforblock][jforblockoffset]);
                    }
                }
            }
            if(option==3){
                System.out.println("tag array");
                for (int i = 0; i < noofset; i++) {
                    for (int j = 0; j <nofelementsinset ; j++) {
                        if(tagarry[i][j]==null){
                            System.out.print(0+" ");
                        }
                        else {
                            System.out.print(tagarry[i][j]+" ");
                        }
                    }
                }
                System.out.println();
                System.out.println("data array");
                for (int i = 0; i < entries; i++) {
                    for (int j = 0; j < c/4; j++) {
                        if (blockarray[i][j] == null) {
                            System.out.print(0 + " ");
                        } else {
                            System.out.print(blockarray[i][j] + " ");
                        }
                    }
                    System.out.println();
                }

            }
//            System.out.println(a);
            System.out.println("1 for more use otherwise 0");
            int nnnn=s.nextInt();
            if(nnnn==0){
                moreinput=false;
            }
        }
    }

    static void directmapping2level(){
        System.out.println("Enter cache size in KB");
        float n=s.nextFloat();
        System.out.println("Block size");
        int c=s.nextInt();
//        for calculating entries
        int nn=(int)(n*1024);
        int entries=nn/c;
        int entries2=entries*2;
//        System.out.println(entries);

//        entries are of 26 bit length
        String[] tagarry=new String[entries];
        String[][] blockarray=new String[entries][c/4];

        String[] tagarry2=new String[entries2];
        String[][] blockarray2=new String[entries2][c/4];

        boolean moreinput=true;
        while(moreinput){
            System.out.println("Enter 1 for write and and 2 for read and 3 print tag array and block data array");
            int option = s.nextInt();
            if (option == 1) {
                System.out.println("Enter address ");
                String input = s.next();
                System.out.println("input data");
                String data = s.next();
//            invert string for selecting offset
//                for level1
                String rinuput = reverse(input);
//            64 =2^6 blockoffsetnumber =6
                int blockoffsetnumber = log2(c/4);
                String blockoffsetinput = rinuput.substring(0, blockoffsetnumber);
                String taginput = rinuput.substring(blockoffsetnumber);
                int in=log2(entries);
                String indexinput=taginput.substring(0,in);
                taginput=taginput.substring(in);
                int index=BinaryTodec(indexinput);
//                for replacemet
                if(tagarry[index]==null || tagarry[index].equals(reverse(taginput))) {
                    tagarry[index] = reverse(taginput);
                    int blockoffsextindex = BinaryTodec(blockoffsetinput);
                    blockarray[index][blockoffsextindex] = data;
                }
                else {
                    tagarry[index]=reverse(taginput);
                    for (int i = 0; i <c/4 ; i++) {
                        blockarray[index][i]=null;
                    }
                    int blockoffsextindex=BinaryTodec(blockoffsetinput);
                    blockarray[index][blockoffsextindex]=data;
                }
//                for level2
                String rinuput2 = reverse(input);
//            64 =2^6 blockoffsetnumber =6
                int blockoffsetnumber2 = log2(c/4);
                String blockoffsetinput2 = rinuput2.substring(0, blockoffsetnumber2);
                String taginput2 = rinuput2.substring(blockoffsetnumber2);
                int in2=log2(entries2);
                String indexinput2=taginput2.substring(0,in2);
                taginput2=taginput2.substring(in2);
                int index2=BinaryTodec(indexinput2);
//                for replacemet
                if(tagarry2[index2]==null || tagarry2[index2].equals(reverse(taginput2))) {
                    tagarry2[index2] = reverse(taginput2);
                    int blockoffsextindex2 = BinaryTodec(blockoffsetinput2);
                    blockarray2[index2][blockoffsextindex2] = data;
                }
                else {
                    tagarry2[index2]=reverse(taginput2);
                    for (int i = 0; i <c /4; i++) {
                        blockarray2[index2][i]=null;
                    }
                    int blockoffsextindex2=BinaryTodec(blockoffsetinput2);
                    blockarray2[index2][blockoffsextindex2]=data;
                }

            }
            else if(option==2){
                System.out.println("Enter address ");
                String input = s.next();
                String rinuput = reverse(input);
//            64 =2^6 blockoffsetnumber =6
                int blockoffsetnumber = log2(c/4);
                String blockoffsetinput = rinuput.substring(0, blockoffsetnumber);
                String taginput = rinuput.substring(blockoffsetnumber);
                int in=log2(entries);
                String indexinput=taginput.substring(0,in);
                taginput=taginput.substring(in);
                taginput=reverse(taginput);
                int index=BinaryTodec(indexinput);
                int blockoffsextindex=BinaryTodec(blockoffsetinput);
//                System.out.println(blockoffsextindex);
//                if(blockarray[index][blockoffsextindex]==null){
//                    System.out.println("address not found");
//                }
//                else{
//                    System.out.println("cache hit");
//                }
                if(tagarry[index].equals(taginput)){
                    System.out.println("cache hit");
                    if(blockarray[index][blockoffsextindex]==null){
                        System.out.println("data= 0");
                    }
                    else{
                        System.out.println("data= "+blockarray[index][blockoffsextindex]);
                    }
                }
                else{
                    String rinuput2 = reverse(input);
//            64 =2^6 blockoffsetnumber =6
                    int blockoffsetnumber2 = log2(c/4);
                    String blockoffsetinput2 = rinuput2.substring(0, blockoffsetnumber2);
                    String taginput2 = rinuput2.substring(blockoffsetnumber2);
                    int in2=log2(entries2);
                    String indexinput2=taginput2.substring(0,in2);
                    taginput2=taginput2.substring(in2);
                    taginput2=reverse(taginput2);
                    int index2=BinaryTodec(indexinput2);
                    int blockoffsextindex2=BinaryTodec(blockoffsetinput2);
//                System.out.println(blockoffsextindex);
//                if(blockarray[index][blockoffsextindex]==null){
//                    System.out.println("address not found");
//                }
//                else{
//                    System.out.println("cache hit");
//                }
                    if(tagarry2[index2].equals(taginput2)){
                        System.out.println("cache hit");
                        if(blockarray2[index2][blockoffsextindex2]==null){
                            System.out.println("data= 0");
                        }
                        else{
                            System.out.println("data= "+blockarray2[index2][blockoffsextindex2]);
                        }
//                        taking block of l2 in l1
                        tagarry[index]=taginput;
                        for (int i = 0; i <c /4; i++) {
                            if(blockarray2[index2][i]==null){
                                blockarray[index][i]=null;
                            }
                            else {
                                blockarray[index][i]=blockarray2[index2][i];
                            }
                        }
                    }
                    else{
                        System.out.println("address not found");
                    }
                }
            }
            else if(option==3){
                System.out.println("cache level1");
                System.out.println("tag array");
                for (int i = 0; i < tagarry.length; i++) {
                    if (tagarry[i] == null) {
                        System.out.print(0 + " ");
                    } else {
                        System.out.print(tagarry[i] + " ");
                    }
                }
                System.out.println();
                System.out.println("data array");
                for (int i = 0; i < entries; i++) {
                    for (int j = 0; j < c/4; j++) {
                        if (blockarray[i][j] == null) {
                            System.out.print(0 + " ");
                        } else {
                            System.out.print(blockarray[i][j] + " ");
                        }
                    }
                    System.out.println();
                }
                System.out.println();
                System.out.println();
                System.out.println("cache level2");
                System.out.println("tag array");
                for (int i = 0; i < tagarry2.length; i++) {
                    if (tagarry2[i] == null) {
                        System.out.print(0 + " ");
                    } else {
                        System.out.print(tagarry2[i] + " ");
                    }
                }
                System.out.println();
                System.out.println("data array");
                for (int i = 0; i < entries2; i++) {
                    for (int j = 0; j < c/4; j++) {
                        if (blockarray2[i][j] == null) {
                            System.out.print(0 + " ");
                        } else {
                            System.out.print(blockarray2[i][j] + " ");
                        }
                    }
                    System.out.println();
                }
            }
            System.out.println("1 for more use otherwise 0");
            int nnn=s.nextInt();
            if(nnn==0){
                moreinput=false;
            }
        }

    }

    static void associativemapping2(){
        System.out.println("Enter cache size in KB for cache level1");
        float n=s.nextFloat();
        System.out.println("Block size");
        int c=s.nextInt();
//        for calculating entries
        int nn=(int)(n*1024);
        int entries=nn/c;
        int entries2=entries*2;
//        System.out.println(entries);

//        entries are of 26 bit length
        String[] tagarry=new String[entries];
        int[] replacementarray=new int[entries];
        String[][] blockarray=new String[entries][c/4];

        String[] tagarry2=new String[entries2];
        int[] replacementarray2=new int[entries2];
        String[][] blockarray2=new String[entries2][c/4];
        int iforreplace=1;
        int iforreplace2=1;
        boolean moreinput=true;
        while (moreinput){
            System.out.println("Enter 1 for write and and 2 for read and 3 print tag array and block data array");
            int option = s.nextInt();
            if (option == 1) {
                System.out.println("Enter address ");
                String input = s.next();
                System.out.println("input data");
                String data = s.next();
//                for level 1
                String rinuput = reverse(input);
//            64 =2^6 blockoffsetnumber =6
                int blockoffsetnumber = log2(c/4);
                String blockoffsetinput= rinuput.substring(0, blockoffsetnumber);
                String taginput = rinuput.substring(blockoffsetnumber);
//                checking if tag already present
                String newtaginput=reverse(taginput);
                int finifpresent=find(tagarry,newtaginput);
                if(finifpresent!=-1){
//                    System.out.println(1);
                    int index = BinaryTodec(blockoffsetinput);
                    blockarray[finifpresent][index] = data;
                }
                else {
                    Random rand = new Random();
                    int randtagindex = rand.nextInt(entries);
//                    for cache full
                    if(iforreplace>entries) {

                        int indexofone=findone(replacementarray);
                        replacementarray[indexofone]=entries+1;
                        tagarry[indexofone] = reverse(taginput);
                        for (int i = 0; i < c/4; i++) {
                            blockarray[indexofone][i]=null;
                        }
                        for (int i = 0; i <replacementarray.length ; i++) {
                            replacementarray[i]=replacementarray[i]-1;
                        }


                        randtagindex=indexofone;

                    }
                    else{
                        if(!findint(randtagindex)){
                            a.add(randtagindex);

                            replacementarray[randtagindex]=iforreplace;
                            iforreplace++;
                            tagarry[randtagindex] = reverse(taginput);
                        }
                        else{
                            while(findint(randtagindex)) {
                                randtagindex=rand.nextInt(entries);
                            }
                            a.add(randtagindex);

                            replacementarray[randtagindex]=iforreplace;
                            iforreplace++;
                            tagarry[randtagindex] = reverse(taginput);
                        }

                    }
//            input in block array
                    int index = BinaryTodec(blockoffsetinput);
                    blockarray[randtagindex][index] = data;
                }
//                for level 2
                String rinuput2 = reverse(input);
//            64 =2^6 blockoffsetnumber =6
                int blockoffsetnumber2 = log2(c/4);
                String blockoffsetinput2= rinuput2.substring(0, blockoffsetnumber2);
                String taginput2 = rinuput2.substring(blockoffsetnumber2);
//                checking if tag already present
                String newtaginput2=reverse(taginput2);
                int finifpresent2=find(tagarry2,newtaginput2);
                if(finifpresent2!=-1){
//                    System.out.println(1);
                    int index2 = BinaryTodec(blockoffsetinput2);
                    blockarray2[finifpresent2][index2] = data;
                }
                else {
                    Random rand2 = new Random();
                    int randtagindex2 = rand2.nextInt(entries2);
//                    for cache full
                    if(iforreplace2>entries2) {

                        int indexofone2=findone(replacementarray2);
                        replacementarray2[indexofone2]=entries2+1;
                        tagarry2[indexofone2] = reverse(taginput2);
                        for (int i = 0; i < c/4; i++) {
                            blockarray2[indexofone2][i]=null;
                        }
                        for (int i = 0; i <replacementarray2.length ; i++) {
                            replacementarray2[i]=replacementarray2[i]-1;
                        }


                        randtagindex2=indexofone2;

                    }
                    else{
                        if(!findint2(randtagindex2)){
                            a2.add(randtagindex2);

                            replacementarray2[randtagindex2]=iforreplace2;
                            iforreplace2++;
                            tagarry2[randtagindex2] = reverse(taginput2);
                        }
                        else{
                            while(findint2(randtagindex2)) {
                                randtagindex2=rand2.nextInt(entries2);
                            }
                            a2.add(randtagindex2);

                            replacementarray2[randtagindex2]=iforreplace2;
                            iforreplace2++;
                            tagarry2[randtagindex2] = reverse(taginput2);
                        }

                    }
//            input in block array
                    int index2 = BinaryTodec(blockoffsetinput2);
                    blockarray2[randtagindex2][index2] = data;
                }

            }
            else if (option == 3) {
//                System.out.println("Enter address ");
//                String input = s.next();
                System.out.println("cache level1");
                System.out.println("tag array");
                for (int i = 0; i < tagarry.length; i++) {
                    if (tagarry[i] == null) {
                        System.out.print(0 + " ");
                    } else {
                        System.out.print(tagarry[i] + " ");
                    }
                }
                System.out.println();
                System.out.println("data array");
                for (int i = 0; i < tagarry.length; i++) {
                    for (int j = 0; j < c/4; j++) {
                        if (blockarray[i][j] == null) {
                            System.out.print(0 + " ");
                        } else {
                            System.out.print(blockarray[i][j] + " ");
                        }
                    }
                    System.out.println();
                }
                System.out.println();
                System.out.println();
                System.out.println("cache level2");
                System.out.println("tag array");
                for (int i = 0; i < tagarry2.length; i++) {
                    if (tagarry2[i] == null) {
                        System.out.print(0 + " ");
                    } else {
                        System.out.print(tagarry2[i] + " ");
                    }
                }
                System.out.println();
                System.out.println("data array");
                for (int i = 0; i < tagarry2.length; i++) {
                    for (int j = 0; j < c/4; j++) {
                        if (blockarray2[i][j] == null) {
                            System.out.print(0 + " ");
                        } else {
                            System.out.print(blockarray2[i][j] + " ");
                        }
                    }
                    System.out.println();
                }
            }
            else if(option==2){
                System.out.println("Enter address ");
                String input = s.next();
                String rinuput = reverse(input);
                int blockoffsetnumber = log2(c/4);
                String taginput = reverse(rinuput.substring(blockoffsetnumber));
                int check=find(tagarry,taginput);
//                not present in cachle level 1
                if(check==-1){
//                    System.out.println(1);
                    String rinuput2 = reverse(input);
                    int blockoffsetnumber2 = log2(c/4);
                    String taginput2 =reverse(rinuput2.substring(blockoffsetnumber2));
                    int check2=find(tagarry2,taginput2);
                    if(check2==-1){
                        System.out.println("address not found");
                    }
                    else {
                        String blockoffsetinput2 = rinuput2.substring(0, blockoffsetnumber2);
                        int index2 = BinaryTodec(blockoffsetinput2);
                        System.out.println("cache hit ");
                        if(blockarray2[check2][index2]==null){
                            System.out.println("data= 0");
                        }
                        else{
                            System.out.println("data= "+blockarray2[check2][index2]);
                        }
//                        taking/copying block of l2 in l1
                        int indexofone=findone(replacementarray);
//                        System.out.println(indexofone);
                        replacementarray[indexofone]=entries+1;
                        tagarry[indexofone] = taginput2;
                        for (int i = 0; i <c/4 ; i++) {
                            if(blockarray2[check2][i]==null){
                                blockarray[indexofone][i]=null;
                            }
                            else {
                                blockarray[indexofone][i]=blockarray2[check2][i];
                            }
                        }
                        for (int i = 0; i <replacementarray.length ; i++) {
                            replacementarray[i]=replacementarray[i]-1;
                        }
                    }
                }
                else {
                    String blockoffsetinput = rinuput.substring(0, blockoffsetnumber);
                    int index = BinaryTodec(blockoffsetinput);
                    System.out.println("cache hit ");
                    if(blockarray[check][index]==null){
                        System.out.println("data= 0");
                    }
                    else{
                        System.out.println("data= "+blockarray[check][index]);
                    }
                }
            }
            System.out.println("1 for more input otherwise 0");
            int nnn=s.nextInt();
            if(nnn==0){
                moreinput=false;
            }
        }
    }
//done
    static void setassociativemapping2(){
        System.out.println("Enter cache size in KB");
        float n=s.nextFloat();
        System.out.println("Block size");
        int c=s.nextInt();
        System.out.println("Enter value of n");
        int nofelementsinset=s.nextInt();
        int nofelementsinset2=nofelementsinset*2;
//        for calculating entries
        int nn=(int)(n*1024);
        int entries=nn/c;
        int entries2=entries*2;
//        System.out.println(entries);
        int noofset=entries/nofelementsinset;
        int noofset2=entries2/nofelementsinset2;
        int index=log2(noofset);
        int index2=log2(noofset2);
//        entries are of 26 bit length
        String[][] tagarry=new String[noofset][nofelementsinset];
        int[][] replacemnet=new int[noofset][nofelementsinset];
        int[] highestinset=new int[noofset];
        String[][] blockarray=new String[entries][c/4];

        String[][] tagarry2=new String[noofset2][nofelementsinset2];
        int[][] replacemnet2=new int[noofset2][nofelementsinset2];
        int[] highestinset2=new int[noofset2];
        String[][] blockarray2=new String[entries2][c/4];

        boolean moreinput=true;
        ArrayList<Integer> aa=new ArrayList<>();
        ArrayList<Integer> aa2=new ArrayList<>();
        while (moreinput){
            System.out.println("Enter 1 for write and and 2 for read and 3 print tag array and block data array");
            int option = s.nextInt();
            if (option == 1) {
                System.out.println("Enter address ");
                String input = s.next();
                System.out.println("input data");
                String data = s.next();
                String rinuput = reverse(input);
//            64 =2^6 blockoffsetnumber =6
                int blockoffsetnumber = log2(c/4);
                String blockoffsetinput = rinuput.substring(0, blockoffsetnumber);
                String taginput = rinuput.substring(blockoffsetnumber);
//                taking last n bit from taginput as index
                String indexinput=taginput.substring(0,index);
                taginput=taginput.substring(index);
                int indexTag=BinaryTodec((indexinput));
//                System.out.println(indexTag);
//                aadding in random position
                Random rand = new Random();
                int randnum=rand.nextInt(nofelementsinset);

                String newtaginput=reverse(taginput);
                int finifpresent=find(tagarry[indexTag],newtaginput);
                if(finifpresent!=-1){
//                    System.out.println(1);
                    int index11 = BinaryTodec(blockoffsetinput);
                    blockarray[finifpresent][index11] = data;
                }
                else{
//                    Random rand = new Random();
//                    int randtagindex = rand.nextInt(entries);
//                    for cache full
                    if(highestinset[indexTag]==nofelementsinset) {

                        int indexofone=findone(replacemnet[indexTag]);
                        replacemnet[indexTag][indexofone]=nofelementsinset+1;
                        tagarry[indexTag][indexofone] = reverse(taginput);
                        for (int i = 0; i < c/4; i++) {
                            blockarray[indexofone][i]=null;
                        }
                        for (int i = 0; i <replacemnet[indexTag].length ; i++) {
                            replacemnet[indexTag][i]=replacemnet[indexTag][i]-1;
                        }

                        randnum=indexofone;

                    }
                    else{
                        if(!find2(randnum,aa)){
//                            System.out.println(2);
                            aa.add(randnum);
                            highestinset[indexTag]=highestinset[indexTag]+1;
                            replacemnet[indexTag][randnum]=highestinset[indexTag];
                            tagarry[indexTag][randnum] = reverse(taginput);
                        }
                        else{
                            while(find2(randnum,aa)) {
                                randnum=rand.nextInt(nofelementsinset);
                            }
                            aa.add(randnum);

                            highestinset[indexTag]=highestinset[indexTag]+1;
                            replacemnet[indexTag][randnum]=highestinset[indexTag];
                            tagarry[indexTag][randnum] = reverse(taginput);
                        }

                    }
//            input in block array

                    tagarry[indexTag][randnum]=reverse(taginput);
                    int blocki=(indexTag*nofelementsinset)+randnum;
                    int blockj=BinaryTodec(blockoffsetinput);
                    blockarray[blocki][blockj]=data;
                }
//                System.out.println(aa);

//                for level2
                String rinuput2 = reverse(input);
//            64 =2^6 blockoffsetnumber =6
                int blockoffsetnumber2 = log2(c/4);
                String blockoffsetinput2= rinuput2.substring(0, blockoffsetnumber2);
                String taginput2 = rinuput2.substring(blockoffsetnumber2);
//                taking last n bit from taginput as index
                String indexinput2=taginput2.substring(0,index2);
                taginput2=taginput2.substring(index2);
                int indexTag2=BinaryTodec((indexinput2));
//                System.out.println(indexTag);
//                aadding in random position
                Random rand2 = new Random();
                int randnum2=rand2.nextInt(nofelementsinset2);

                String newtaginput2=reverse(taginput2);
                int finifpresent2=find(tagarry2[indexTag2],newtaginput2);
                if(finifpresent2!=-1){
//                    System.out.println(1);
                    int index11 = BinaryTodec(blockoffsetinput2);
                    blockarray2[finifpresent2][index11] = data;
                }
                else{
//                    Random rand = new Random();
//                    int randtagindex = rand.nextInt(entries);
//                    for cache full
                    if(highestinset2[indexTag2]==nofelementsinset2) {

                        int indexofone2=findone(replacemnet2[indexTag2]);
                        replacemnet2[indexTag2][indexofone2]=nofelementsinset2+1;
                        tagarry2[indexTag2][indexofone2] = reverse(taginput2);
                        for (int i = 0; i < c/4; i++) {
                            blockarray2[indexofone2][i]=null;
                        }
                        for (int i = 0; i <replacemnet2[indexTag2].length ; i++) {
                            replacemnet2[indexTag2][i]=replacemnet2[indexTag2][i]-1;
                        }

                        randnum2=indexofone2;

                    }
                    else{
                        if(!find2(randnum2,aa2)){
//                            System.out.println(2);
                            aa2.add(randnum2);
                            highestinset2[indexTag2]=highestinset2[indexTag2]+1;
                            replacemnet2[indexTag2][randnum2]=highestinset2[indexTag2];
                            tagarry2[indexTag2][randnum2] = reverse(taginput2);
                        }
                        else{
                            while(find2(randnum2,aa2)) {
                                randnum2=rand2.nextInt(nofelementsinset2);
                            }
                            aa2.add(randnum2);

                            highestinset2[indexTag2]=highestinset2[indexTag2]+1;
                            replacemnet2[indexTag2][randnum2]=highestinset2[indexTag2];
                            tagarry2[indexTag2][randnum2] = reverse(taginput2);
                        }

                    }
//            input in block array

                    tagarry2[indexTag2][randnum2]=reverse(taginput2);
                    int blocki=(indexTag2*nofelementsinset2)+randnum2;
                    int blockj=BinaryTodec(blockoffsetinput2);
                    blockarray2[blocki][blockj]=data;
                }
//                System.out.println(aa);
            }
            else if(option==2){
                System.out.println("Enter address ");
                String input = s.next();
                String rinuput = reverse(input);
//            64 =2^6 blockoffsetnumber =6
                int blockoffsetnumber = log2(c/4);
                String blockoffsetinput = rinuput.substring(0, blockoffsetnumber);
                String taginput = rinuput.substring(blockoffsetnumber);
//                taking last n bit from taginput as index
                String indexinput=taginput.substring(0,index);
                taginput=taginput.substring(index);
                int indexTag=BinaryTodec((indexinput));
//                System.out.println(indexTag);
                String[] temp=tagarry[indexTag];
                int newindex=find(temp,reverse(taginput));
                if(newindex==-1){
                    String rinuput2 = reverse(input);
//            64 =2^6 blockoffsetnumber =6
                    int blockoffsetnumber2 = log2(c/4);
                    String blockoffsetinput2 = rinuput2.substring(0, blockoffsetnumber2);
                    String taginput2 = rinuput2.substring(blockoffsetnumber2);
//                taking last n bit from taginput as index
                    String indexinput2=taginput2.substring(0,index2);
                    taginput2=taginput2.substring(index2);
                    int indexTag2=BinaryTodec((indexinput2));
//                    System.out.println(indexTag);
                    String[] temp2=tagarry2[indexTag2];
                    int newindex2=find(temp2,reverse(taginput2));
                    if(newindex2==-1){
                        System.out.println("address not found");
                    }
                    else{
                        int iforblock2=(indexTag2*nofelementsinset2)+newindex2;
                        int jforblockoffset2=BinaryTodec(blockoffsetinput2);
                        System.out.println("cache hit");
                        if(blockarray2[iforblock2][jforblockoffset2]==null){
                            System.out.println("data= 0");
                        }
                        else{
                            System.out.println("data= "+blockarray2[iforblock2][jforblockoffset2]);
                        }
//                        indexTag2=iforblock2;
//                        int indexTag333=
                        int indexofone=findone(replacemnet[indexTag]);
                        replacemnet[indexTag][indexofone]=nofelementsinset+1;
//                    System.out.println(indexTag);
//                    System.out.println(indexTag2);
                        tagarry[indexTag][indexofone] = reverse(taginput2);
//                        for (int i = 0; i <c/4 ; i++) {
//                            System.out.print(blockarray2[indexTag2][i]);
//                        }
                        for (int i = 0; i < c/4; i++) {
                            if(blockarray2[indexTag][i]==null){
                                blockarray[(indexTag*nofelementsinset)+newindex][i]=null;
                            }
                            else{
                                blockarray[(indexTag*nofelementsinset)+newindex2][i]=blockarray2[indexTag2][i];
                            }
                        }
                        for (int i = 0; i <replacemnet[indexTag].length ; i++) {
                            replacemnet[indexTag][i]=replacemnet[indexTag][i]-1;
                        }
                    }

                }
                else{
                    int iforblock=(indexTag*nofelementsinset)+newindex;
                    int jforblockoffset=BinaryTodec(blockoffsetinput);
                    System.out.println("cache hit");
                    if(blockarray[iforblock][jforblockoffset]==null){
                        System.out.println("data= 0");
                    }
                    else{
                        System.out.println("data= "+blockarray[iforblock][jforblockoffset]);
                    }
                }
            }
            if(option==3){
                System.out.println("cache level1");
                System.out.println("tag array");
                for (int i = 0; i < noofset; i++) {
                    for (int j = 0; j <nofelementsinset ; j++) {
                        if(tagarry[i][j]==null){
                            System.out.print(0+" ");
                        }
                        else {
                            System.out.print(tagarry[i][j]+" ");
                        }
                    }
                }
                System.out.println();
                System.out.println("data array");
                for (int i = 0; i < entries; i++) {
                    for (int j = 0; j < c/4; j++) {
                        if (blockarray[i][j] == null) {
                            System.out.print(0 + " ");
                        } else {
                            System.out.print(blockarray[i][j] + " ");
                        }
                    }
                    System.out.println();
                }
                System.out.println();
                System.out.println();
                System.out.println("cache level 2");
                System.out.println("tag array");
                for (int i = 0; i < noofset2; i++) {
                    for (int j = 0; j <nofelementsinset2 ; j++) {
                        if(tagarry2[i][j]==null){
                            System.out.print(0+" ");
                        }
                        else {
                            System.out.print(tagarry2[i][j]+" ");
                        }
                    }
                }
                System.out.println();
                System.out.println("data array");
                for (int i = 0; i < entries2; i++) {
                    for (int j = 0; j < c/4; j++) {
                        if (blockarray2[i][j] == null) {
                            System.out.print(0 + " ");
                        } else {
                            System.out.print(blockarray2[i][j] + " ");
                        }
                    }
                    System.out.println();
                }
            }
            System.out.println("1 for more input otherwise 0");
            int nnnn=s.nextInt();
            if(nnnn==0){
                moreinput=false;
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Choose Mapping ");
        System.out.println("1 for Associative Mapping");
        System.out.println("2 for direct Mapping");
        System.out.println("3 for set associative Mapping");
        System.out.println("4 for level2 direct Mapping");
        System.out.println("5 for level2 Associative Mapping");
        System.out.println("6 for level3 set associative Mapping");
        int option=s.nextInt();
        if(option==1){
            associativemapping();
        }
        else if(option==2){
            directmapping();
        }
        else if(option==3){
            setassociativemapping();
        }
        else if(option==4){
            directmapping2level();
        }
        else if(option==5){
            associativemapping2();
        }
        else if(option==6){
            setassociativemapping2();
        }
    }
}


