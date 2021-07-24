//kullanıcının girdiği veriye & gibi semboller koyarak pars eden program
package OdevClassPaketi;



//class tanımlamasının başı
public class RegexToInfıx {

    private String regex = "";
    

    public RegexToInfıx(String gelen_regex) {
        //gui den regex1 referansı ile gelen kullanici_girisi Stringi bu constructor u kullanarak  String gelen_regex değişkeni yerine geçer
        //daha sonra aşağıdaki RegexParser metodu ile pars edilir
	
        regex = "";
	RegexParser(gelen_regex);         
    }
    
    
    public String getRegex() {
	return regex;
    }
 
   
    
	private void RegexParser(String gelen_regex) {
		char[] regexs = gelen_regex.replaceAll(" ", "").toCharArray();     //guiden gelen String'in boşluklarını silip char dizisine dönüştür ve bu diziyi regex isimli char dizisine eşitle 
		for (int i = 0; i < regexs.length; i++) {       
			if (i == 0){
                            regex += regexs[i];     // gelen eleman ilk elemansa diziye ekle
                        }				
			else {  
                            if (regexs[i] == '|' || regexs[i] == '*' || regexs[i] == ')') { // o anki | * veya ) ise  
                                regex += regexs[i]; //bu elemanları da regex isimli String e ekliyoruz çünkü infix gösterimde | * ) elmanlarının görünmesi gerek
                            } 
                            
                            else {    // şu anki elemanlar | * veya )  değilse
                                if (regexs[i - 1] == '('   || regexs[i - 1] == '|'){   // bir önceki gezilen eleman ( veya | ise
                                    regex += regexs[i]; 
                                }
                                else{
                                    regex += ("&" + regexs[i]);  // eğer üsttekilerden hiçbirisi değilse yani letter sa (abcd....)  bu harfin harfin önüne & sembolünü koyarak stringe ekle
                                }				
			    
                            }
			}
		}
	}    
        
}