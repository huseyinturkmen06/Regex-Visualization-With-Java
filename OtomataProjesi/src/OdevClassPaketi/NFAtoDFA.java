/*

NFA turunu Dfa turuna donusturen bu classın calismalari tam olarak bitmememistir
ve gui ye eklenmemistir





*/
package OdevClassPaketi;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import jdk.nashorn.internal.ir.ContinueNode;


public class NFAtoDFA extends PostFixToNFA { 
   
    
    
    public static class DFA{
        //public ArrayList<Integer> degerler;
        Set<Integer> degerler=new LinkedHashSet<Integer>(); // aynı elementi 1 defa depoluyorlar bu nedenle arraylist yerine buna geçtik
       // public ArrayList<TransSymbol> transitions; // buna da ihtiyacımız olmayabilir
        public DFA(){
            Set<Integer> degerler = new LinkedHashSet<Integer>();
        }
        // HASHET KULLANMAKTANSA SETİ SIRAYLA ATAN Bİ SET YAPISI KULLANSAK VE BU SETİN EN SON ELEMANINI ALTTA BASTIRIRKEN EKLERİZ BU DA FİNAL STATE OLUR
        // linkedhashset kullandım
        
        public void display(){
            for(Integer t:degerler){
                System.out.print(t + " ");
            }
        }
        public void display_nonspace(){
            for(Integer t:degerler){
                System.out.print(t);
            }
        }
    }
         
    
    public static DFA baslangıc_durumu_hesaplama(String postfix_notasyon) {
        
        PostFixToNFA.NFA nfa1= new PostFixToNFA.NFA();
        
        Character CurrentChar;
        int temp; // geçici değer current charın o anlık değerini tutsun
        DFA dfa2=new DFA();
       
        nfa1=PostFixToNFA.PostFixtonfaa(postfix_notasyon);
        String dfa_cevir=nfa1.display_to_string2();
        
        for(int i=0;i<dfa_cevir.length();i++){
            temp=i; // 0
            CurrentChar=dfa_cevir.charAt(i);
            if(!Epsilon_or_not(CurrentChar) && CurrentChar=='0'){ // sadece 0 dayken çalışacak
                dfa2.degerler.add(CurrentChar-'0');  // 0 ı 2 defa ekliyo 0->E->1 0->E->3 0,1,0,3
                CurrentChar=dfa_cevir.charAt(i+4);   
                // 0 DAN SONRA E DURUMU GELİYOR MU KONTROLÜ
                if(Epsilon_or_not(CurrentChar)){
                    CurrentChar=dfa_cevir.charAt(temp+8);
                    dfa2.degerler.add(CurrentChar-'0'); // 0-->E-->1 burdaki 8.indeks 1 oluyor örneğin
                    CurrentChar=dfa_cevir.charAt(temp);
                }
                CurrentChar=dfa_cevir.charAt(temp); // eğer 4. indekste epsilon yoksa tekrar eski indeksine dönsün

            }
 
            else if(Epsilon_or_not(CurrentChar) || CurrentChar!='0' || !Epsilon_or_not(CurrentChar)){ // 0-->E-->1 mesela - - > ilerleyecek E de de ilerleyecek 1 2 gibi şeylerde de ilerleyecek
                continue;
                // BURASI SADECE 0 LI İŞLEMLERİ YAPMASI İÇİN YAPILIYOR
            }                        
                    
        } 

        return dfa2;
    }
    
    public static void move_to_state(DFA a,String postfix_not){ 
       Character CurrentChar,degerlericinde_dongu,CurrentChar2,CurrentChar4indeks,Chartut,CurrenChar8indeks,InsideCurrent,InsideCurrent2;
       DFA dfa1= new DFA();
       DFA dfa2= new DFA();
       int temp,temp2,temp3;//belki currentchar için kullanılır
       String degerler_icinde="";
       String dfaokuyacak_dosya=okuma(postfix_not);// dfa_okuyacak.txt içindekileri veriyor
       a=baslangıc_durumu_hesaplama(postfix_not); // 0, 1, 3
       for(Integer t:a.degerler){ // a nın degerlerini gezecek 0-->E-->1
            
           degerler_icinde+=t; // 0 1 3
       }
           
       for(int i=0;i<degerler_icinde.length();i++){
               temp=i;
               degerlericinde_dongu=degerler_icinde.charAt(i);
                              
           for(int j=0;j<dfaokuyacak_dosya.length();j++){
               temp2=j;
               CurrentChar=dfaokuyacak_dosya.charAt(j);
               if(temp2 != dfaokuyacak_dosya.length() -1 ){ 
                   // temp2 == dfaokuyacak_dosya.length()-1 ise dosya sonunda
               if(CurrentChar==degerlericinde_dongu){
                   CurrentChar2=dfaokuyacak_dosya.charAt(j+1);
                    if(CurrentChar2=='#'){ //satır sonuna geldik direkt döngü devam etsin
                        continue;
                    }

                    else{ // burasnın da else if olması gerek, else değil çünkü - - > bunlardan birini de alabilir
                        CurrentChar4indeks=dfaokuyacak_dosya.charAt(j+4);
                        
                        if(CurrentChar4indeks=='a'){
                            Chartut=CurrentChar; // 1
                            CurrenChar8indeks=dfaokuyacak_dosya.charAt(j+8);
                            dfa1.degerler.add(CurrenChar8indeks-'0'); // 2
                            // şimdi eklenen 2 değeri için program nasıl devam edecek kontrol
                            for(int k=0;k<dfaokuyacak_dosya.length();k++){
                                temp3=k;
                                InsideCurrent=dfaokuyacak_dosya.charAt(k);
                                if(temp3 != dfaokuyacak_dosya.length()-1){
                                InsideCurrent2=dfaokuyacak_dosya.charAt(k+1);
                                if(InsideCurrent2=='#' && temp3 < dfaokuyacak_dosya.length()-1){
                                    continue;
                                }
                                else if(CurrenChar8indeks==InsideCurrent){ // 2=2 olursa
                                    InsideCurrent=dfaokuyacak_dosya.charAt(k+4);
                                    if(Epsilon_or_not(InsideCurrent)){
                                        InsideCurrent=dfaokuyacak_dosya.charAt(k+8);
                                        dfa1.degerler.add(InsideCurrent-'0');
                                        //InsideCurrent=dfaokuyacak_dosya.charAt(temp3);
                                    }
                                    else continue;
                                }

                                }
                            }
                            System.out.print("DFA 1: ");
                            dfa1.display();
                            System.out.println("\n");
                            System.out.print(Chartut + "-->a-->");
                            dfa1.display();
                            System.out.println("\n");
                        }
                        
                        else if(CurrentChar4indeks=='b'){
                            Chartut=CurrentChar;
                            CurrenChar8indeks=dfaokuyacak_dosya.charAt(j+8);
                            dfa2.degerler.add(CurrenChar8indeks-'0');
                            // 2.for buraya gelecek
                            for(int k=0;k<dfaokuyacak_dosya.length();k++){
                                temp3=k;
                                InsideCurrent=dfaokuyacak_dosya.charAt(k);
                                if(temp3 != dfaokuyacak_dosya.length() -1 ){// dosya okuma bitti mi kontrol
                                InsideCurrent2=dfaokuyacak_dosya.charAt(k+1);
                                 // direkt lengthden küçük olması lazım
                                if(InsideCurrent2=='#'){
                                    continue; 
                                }
                                else if(CurrenChar8indeks==InsideCurrent){
                                    InsideCurrent=dfaokuyacak_dosya.charAt(k+4);
                                    if(Epsilon_or_not(InsideCurrent)){
                                        InsideCurrent=dfaokuyacak_dosya.charAt(k+8);
                                        dfa2.degerler.add(InsideCurrent-'0');
                                    }
                                    else continue;
                                }
                                
                                }
                            }
                            System.out.print("DFA 2: ");
                            dfa2.display();
                            System.out.println("\n");
                            System.out.print(Chartut + "-->b-->");
                            dfa2.display();
                            System.out.println("\n");
                                                            
                        }
                    }
                   

               }
               }

           }
          
       }

    }
    
    
    
    public static boolean is_character(char c){
        if(c=='a') return true;
        else if(c=='b') return true;
        else return false;
    }
    
    
    public static String okuma(String postfix_ifade){
        
        PostFixToNFA.NFA nfa2= new PostFixToNFA.NFA();
        
        
        nfa2=PostFixToNFA.PostFixtonfaa(postfix_ifade);
        String dfa_cevir=nfa2.display_to_string2();
        return dfa_cevir;
    }

    
    public static void nfa_to_dfa(String postfix_notasyon){ 
        DFA dfa_move_to_state=baslangıc_durumu_hesaplama(postfix_notasyon);
        // DFA2 ELİMDE ŞU AN 0 1 3 VAR İÇİNDE
        System.out.print("DFA A = ");
        dfa_move_to_state.display();
        //bazen doğru, bazen yanlış değer dönebiliyor
        System.out.println("\n");
        
        move_to_state(dfa_move_to_state, postfix_notasyon); // hatalı değer dönüyor
 
        }

    public static boolean Epsilon_or_not(char c){
        if(c=='E'){
            return true;
        }else{
            return false;
        }
    }
    
    
    
    public static void main(String[] args) {
       Scanner scanner = new Scanner(System.in);
        System.out.println("Postfix ifade giriniz: \n");
        String kullanıcı_postfix = scanner.nextLine();
       nfa_to_dfa(kullanıcı_postfix);
       
       //ab| 
    }


}