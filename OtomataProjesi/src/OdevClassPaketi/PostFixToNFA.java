
package OdevClassPaketi;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
public class PostFixToNFA { 
    /*
    Σ={a,b,c,d,e,...} epsilon(E) 
    Q= Set of all states -{a,b}
    q0= start state/initial state  {a}
    F= set of final states {b}
    S=Q x E = 2^Q
    
    a1|b1 =  union
    a* = kleene star
    r1r2= r1&r2
    
    
    Q: finite set of states  
    ∑: finite set of the input symbol  
    q0: initial state   
    F: final state  
    δ: Transition function
    */
    // precedencelar var | union en az -- ? concat orta -- * en yüksek precedence a sahip
    
    public static class TransSymbol{ 
        // trans sembolleri şu anki durum, next state ve trans sembolüne sahip olabilirler
        // transsymbol class ı arraylistlerde transitionları saklamak için oluşturuldu bu sebeple stacklerde arraylistler depolanabilir
        public int state_from,state_to; 
        public char trans_Symbol; 
        public TransSymbol(int st1,int st2, char symbol){ 
            this.state_from=st1;
            this.state_to=st2;
            this.trans_Symbol=symbol;
        }
    }
    public static class NFA{ // STACK'e veri eklerken NFA1 NFA2 diye NFA class ı oluşturmak için
        public ArrayList <Integer> states; //Stateleri gösterecek
        public ArrayList<TransSymbol> transitions; // transitionları gösterecek
        public int final_state;
        
        public NFA(){
            this.states=new ArrayList<Integer>();
            this.transitions = new ArrayList<TransSymbol>();
            this.final_state=0; 
            //basitçe final state'i belirlemek için oluşturuldu
            
        }
        public NFA(char c){ // eğer NFA e char c ile gelirse yani a ile 2 tane state açılmalı direkt, yani state size lazım 2 lik o zaman state size fonksiyonu lazım
            this.states=new ArrayList<Integer>();
            this.transitions = new ArrayList<TransSymbol>();
            this.stateSize(2);
            this.final_state=1;
            this.transitions.add(new TransSymbol(0,1,c)); // 0 initial state 1 final state c gelen character a yani 0'dan 1'e a ile geçecek
            
        }
        public NFA(int size){ // fonksiyonlarda kullanılmak için oluşturuldu
            this.states=new ArrayList<Integer>();
            this.transitions = new ArrayList<TransSymbol>();
            this.stateSize(size);
            this.final_state=0; //  içeriye int değer constructordan dönecek,  sonra final state belirlenecek
            
        }
        
        
        
        
        
      public void stateSize(int size){
          for(int i=0;i<size;i++){
              this.states.add(i);
          }
      }
      public void display(){ // transition arraylisti display üzerinde gezinmek için oluşturuldu
          for(TransSymbol t:transitions){
              
              System.out.println(t.state_from + "-->" + t.trans_Symbol + "-->" + t.state_to);
          }
      }
      
      //gui de kullanmak için
      public String display_to_string(){
          String basılacak="";
          for(TransSymbol t:transitions){
              
              basılacak+=("S"+t.state_from + "-->" + t.trans_Symbol + "-->" + "S"+t.state_to + "\n");
          }
          return basılacak;
      }
      
      public String display_to_string2(){
          String basılacak="";
          for(TransSymbol t:transitions){
              
              basılacak+=(t.state_from + "-->" + t.trans_Symbol + "-->" + t.state_to + "#");
          }
          return basılacak;
      }
      
      
      
      
    }
    public static boolean letterOrDigit(char c){ // c stands for character
            if(Character.isLetter(c)){
                return true;
            }
            else{
                return false;
            }
        }
    
    
    public static NFA kleeneStar(NFA a){ 
        NFA result=new NFA(a.states.size()+2);  //result NFA inin sizeı oluşturuldu
        result.transitions.add(new TransSymbol(0, 1,'E')); //0 dan 1 e geçişte 'E' durumu
        for(TransSymbol  t:a.transitions){
            result.transitions.add(new TransSymbol(t.state_from+1, t.state_to+1, t.trans_Symbol));
        }   //nfa a nın durumları 1 arttırıldı
        
        result.transitions.add(new TransSymbol(a.states.size(), a.states.size()+1, 'E')); // a dan yeni gelen durumdan son duruma geçme

        
        result.transitions.add(new TransSymbol(a.states.size(), 1, 'E'));
        //a nın kendi durumu içinde 1 e dönerek 'E' alması
        
        result.transitions.add(new TransSymbol(0, a.states.size()+1, 'E'));  //0 dan son duruma 'E' geçişi
        result.final_state=a.states.size()+1;  //final state kleene fonksiyonuna göre belirlendi
        return result;
    }
    
    
    public static NFA concat(NFA a,NFA b){ 
               
        NFA result = new NFA(a.states.size()+b.states.size()-1); //state belirlendi
        a.states.remove(1);
        b.states.remove(0);
        for(TransSymbol t:a.transitions){
            result.transitions.add(new TransSymbol(t.state_from, t.state_to, t.trans_Symbol));
        }  //a nın transition ları result a kopyalandı
        
        for(TransSymbol t:b.transitions){
            result.transitions.add(new TransSymbol(t.state_from+a.states.size(), t.state_to+a.states.size(), t.trans_Symbol));            
        }  //b nin transitionları a ya göre arttırıldı
        result.final_state=a.states.size()+b.states.size()-1;  //final state belirlendi

        return result;
    } 
    
    public static NFA union(NFA a,NFA b){
        NFA result =new NFA(a.states.size()+b.states.size()+2); //state belirlendi
        result.transitions.add(new TransSymbol(0,1, 'E')); //0 dan 1'e 'E' geçişi
        for(TransSymbol t: a.transitions){
            result.transitions.add(new TransSymbol(t.state_from+1, t.state_to+1,t.trans_Symbol));
        } //a nın transitionları 1 arttırıldı
        result.transitions.add(new TransSymbol(a.states.size(), a.states.size()+b.states.size()+1, 'E')); 
        
        result.transitions.add(new TransSymbol(0, a.states.size()+1, 'E'));
        for(TransSymbol t:b.transitions){
            
            result.transitions.add(new TransSymbol(t.state_from + a.states.size()+1,t.state_to + a.states.size()+1, t.trans_Symbol));
        } //b nin transitionları a ya göre arttırıldı
        result.transitions.add(new TransSymbol(b.states.size()+a.states.size(), b.states.size()+a.states.size()+1, 'E'));
        //final state NFA constructor ından dolayı 0 onu değiştirelim
        result.final_state=a.states.size()+b.states.size()+1;  //final state fonksiyona göre belirlendi
        return result; 
    }
    
    
    public static boolean isRegexTrue(String regex){ // kullanılmadı kullanılacak gerek kalmadı buna altta else durumu aynı şeyi yapıyor
        if(regex.isEmpty()){
            return false;
        }else{
            return true;
        }
    }
    public static NFA PostFixtonfaa(String postfix){ 
       
        
        
        Stack<NFA> harfler = new Stack<NFA>(); 
        
        NFA nfa1,nfa2; 
        
        char CurrentChar,operator; 
        for(int i=0;i<postfix.length();i++){
            CurrentChar=postfix.charAt(i);
            if(letterOrDigit(CurrentChar)){ 
                harfler.push(new NFA(CurrentChar)); // harfleri NFA stackinde topluyoruz ve NFA classınıda harfleri çağrıldığında 0->a->1 olması için oluşturduk zaten
            }
            else if(CurrentChar=='|' || CurrentChar=='+'){ // union bitti
                nfa2=harfler.pop();
                nfa1=harfler.pop();
                NFA union1 = union(nfa2, nfa1);
                harfler.push(union1);
                
            }
            else if(CurrentChar=='?' || CurrentChar=='&'){
                nfa2=harfler.pop();
                nfa1=harfler.pop();
                NFA concat1 = concat(nfa1, nfa2);
                harfler.push(concat1);
            }
            else if(CurrentChar=='*'){
                harfler.push(kleeneStar(harfler.pop()));
            }
            else{
                return new NFA();
            }
           
        }   
        return harfler.pop();  // sonda en son 1 tane NFA kalacağı için stackde return bu olacak yani harfler.pop() olacak  

    }



    }
