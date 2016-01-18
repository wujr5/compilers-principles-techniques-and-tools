import java.io.*;
import java.util.*;

public class UnLinker {
  private String[] link_prefix = {"http://", "http://www", "www"};
  private String[] link_postfixStrings = {".com", ".org", ".edu", ".info", ".tv"};

  public int getIndexOfPrefix(int pos, String str) {
    int res = 100;
    for (int i = 0; i < link_prefix.length; i++) {
      int tem = str.indexOf(link_prefix[i], pos);
      if (tem != -1 && res > tem) res = tem;
    }
    return res;
  }
  
  public int getIndexOfPostfix(int pos, String str) {
    int res = -1;
    for (int i = 0; i < link_postfixStrings.length; i++) {
      int tem = str.lastIndexOf(link_postfixStrings[i], pos);
      if (tem != -1 && res < tem) res = tem;
    }
    return res;
  }
  
  public int getIndexOfPostfixLength(int pos, String str) {
    int res = -1;
    int type = 0;
    for (int i = 0; i < link_postfixStrings.length; i++) {
      int tem = str.lastIndexOf(link_postfixStrings[i], pos);
      if (tem != -1 && res < tem) {
        res = tem;
        type = i;
      }
    }
    return link_postfixStrings[type].length();
  }
  
  public String[] splitLinkString(String text) {
    int pos = 0;
    String[] strs = new String[100];
    int count = 0;
    while (text.indexOf(' ', pos + 1) != -1 || text.indexOf("http://", pos + 1) != -1) {
      int tem1 = text.indexOf(' ', pos + 1);
      int tem2 = text.indexOf("http://", pos + 1);
      int tem3 = 0;
      
      if (tem1 == -1 && tem2 != -1) {
        tem3 = tem2;
      }
      else if (tem1 != -1 && tem2 == -1) {
        tem3 = tem1;
      }
      else if (tem1 != -1 && tem2 != -1) {
        tem3 = tem1 < tem2 ? tem1 : tem2;
      }
      
      strs[count++] = text.substring(pos, tem3);
      
      pos = tem3;
    }
    
    strs[count++] = text.substring(pos, text.length());
    
    String[] new_strs = new String[count];
    for (int i = 0; i < count; i++) {
      new_strs[i] = strs[i];
    }
    
    return new_strs;
  }
  
  String clean(String text) {
    String[] strs = splitLinkString(text);
    int pre_pos = 0;
    int post_pos = 0;
    int count = 0;
    String result = "";
    
    for (int i = 0; i < strs.length; i++) {
      pre_pos = getIndexOfPrefix(0, strs[i]);
      post_pos = getIndexOfPostfix(strs[i].length(), strs[i]);
      
      if (pre_pos == 100 || post_pos == -1) {
        result += strs[i];
        continue;
      }
      
      int len = getIndexOfPostfixLength(strs[i].length(), strs[i]);
      count++;
      result += strs[i].substring(0, pre_pos);
      result += "OMIT" + count;
      String tem4 = strs[i].substring(post_pos + len);
      result += tem4;
    }
    
    return result;
  }
  
  public static void main(String[] args) throws IOException {
    Scanner in = new Scanner(new File("UnLinker.in"));
//    Scanner in = new Scanner(new File(".//src//UnLinker.in"));
    String text;
    UnLinker ul = new UnLinker();
    
    while (in.hasNextLine()) {
      text = in.nextLine();
      System.out.println(ul.clean(text));
    }
    in.close();
  }
}
