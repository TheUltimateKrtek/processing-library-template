package projectaria.niri.data;

import projectaria.niri.Console.ConsoleColor;
import projectaria.niri.structures.Network;
import projectaria.niri.structures.Tree;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

/*
  Format:
  a    XXXXXXXX    1-byte number to define character length
  b    XXXX....    header
  c    0           End of header
  d    XXXX....    Encoded data
*/

public class HuffmanCoding{
  public static class Encoding{
    public static boolean[] encode(String string, int byteCountPerCharacter){
      boolean[] input = new boolean[string.length() * 8 * byteCountPerCharacter];
      
      for(int i = 0; i < string.length(); i ++){
        int value = (int)string.charAt(i);
        int end = i * 8 * byteCountPerCharacter + 8 * byteCountPerCharacter - 1;
        for(int j = 0; j < 8 * byteCountPerCharacter; j++) input[end - j] = (value & (1 << j)) != 0;
      }
      return encode(input, byteCountPerCharacter * 8);
    }
    public static boolean[] encode(boolean[] data, int bitCount){
      if(data == null) return null;

      boolean[][] segments = getSegments(data, bitCount);
      ArrayList<HuffmanTree> nodeList = getNodes(segments, bitCount);
      mergeSort(nodeList);
      HuffmanTree tree = createTree(nodeList);
      tree.fetchTranslationKeysForEntireSubtree();
      boolean[][] translatedData = translate(segments, tree);
      ArrayList<Boolean> resultList = new ArrayList<Boolean>();
      for(int i = 0; i < translatedData.length; i ++){
        if(translatedData[i] == null) continue;
        for(int j = 0; j < translatedData[i].length; j ++) resultList.add(translatedData[i][j]);
      }
      
      boolean[] header = tree.getHeader();
      resultList.add(0, false);
      for(int i = 0; i < header.length; i ++) resultList.add(0, header[header.length - i - 1]);
      /*for(int i = 0; i < header.length; i ++){
        System.out.print(header[i] ? 1 : 0);
      }
      System.out.println();*/
      
      for(int j = 0; j < 8; j++) resultList.add(0, (bitCount & (1 << j)) != 0);

      boolean[] returnArray = new boolean[resultList.size()];
      for(int i = 0; i < returnArray.length; i ++) returnArray[i] = resultList.get(i);
      return returnArray;
    }
    
    private static boolean[][] getSegments(boolean[] data, int bitCount){
      boolean[][] segments = new boolean[data.length / bitCount + (data.length % bitCount > 0 ? 1 : 0)][bitCount];
      for(int i = 0; i < segments.length; i ++){
        if(i == segments.length - 1) for(int j = 0; j < bitCount; j ++) segments[i][j] = false;
        for(int j = 0; j < bitCount; j ++) segments[i][j] = data[i * bitCount + j];
      }
      if(data.length % bitCount > 0){
        
        for(int i = 0; i < data.length % bitCount; i ++){
          int index = data.length / bitCount * data.length + i;
          if(index > data.length) break;
          segments[segments.length - 1][i] = data[index];
        }
      }
      return segments;
    }
    private static ArrayList<HuffmanTree> getNodes(boolean[][] segments, int bitCount){
      ArrayList<HuffmanTree> nodes = new ArrayList<HuffmanTree>();
      for(int i = 0; i < segments.length; i ++){
        boolean[] current = segments[i];
        boolean found = false;
        for(HuffmanTree node:nodes) if(node.equalsValue(current)){
          found = true;
          node.count ++;
          break;
        }
        if(!found){
          HuffmanTree node = new HuffmanTree(current);
          nodes.add(node);
          node.count ++;
        }
      }

      /*
      boolean[] endCharacter = new boolean[bitCount];
      for(int i = 0; i < bitCount; i ++) endCharacter[i] = false;
      nodes.add(new HuffmanTree(endCharacter));
      */

      return nodes;
    }
    private static HuffmanTree createTree(ArrayList<HuffmanTree> nodeList){
      while(nodeList.size() > 1){
        HuffmanTree res = new HuffmanTree(null);
        res.addOutput(nodeList.get(nodeList.size() - 2));
        res.addOutput(nodeList.get(nodeList.size() - 1));
        nodeList.remove(nodeList.size() - 1);
        nodeList.remove(nodeList.size() - 1);
        int index = 0;
        if(nodeList.size() > 0)
        for(index = nodeList.size() - 1; index >= 0; index --){
          if(nodeList.get(index).count >= res.count){
            index ++;
            break;
          }
        }
        if(index < 0) index = 0;
        nodeList.add(index, res);
      }
      return nodeList.get(0);
    }
    private static boolean[][] translate(boolean[][] segments, HuffmanTree tree){
      boolean[][] translatedData = new boolean[segments.length][0];
      Tree[] treeLeaves = tree.getLeaves(true);
      HuffmanTree[] huffmanLeaves = new HuffmanTree[treeLeaves.length];
      for(int i = 0; i < treeLeaves.length; i ++) huffmanLeaves[i] = (HuffmanTree)treeLeaves[i];
      for(int i = 0; i < segments.length; i ++){
        for(int nodeIndex = 0; nodeIndex < huffmanLeaves.length; nodeIndex ++){
          if(huffmanLeaves[nodeIndex].equalsValue(segments[i])){
            translatedData[i] = huffmanLeaves[nodeIndex].translationKey;
            break;
          }
        }
      }
      return translatedData;
    }
    private static void mergeSort(ArrayList<HuffmanTree> ar){
      mergeSort(ar, 0, ar.size() - 1);
    }
    private static void mergeSort(ArrayList<HuffmanTree> ar, int start, int end){
      if(start == end) return;
      int mid = start + (end - start) / 2;
      mergeSort(ar, start, mid);
      mergeSort(ar, mid + 1, end);
      merge(ar, start, mid, end);
    }
    private static void merge(ArrayList<HuffmanTree> ar, int start, int mid, int end){
      HuffmanTree[] left = new HuffmanTree[mid - start + 1];
      for(int i = 0; i < left.length; i ++) left[i] = ar.get(start + i);
      HuffmanTree[] right = new HuffmanTree[end - mid];
      for(int i = 0; i < right.length; i ++) right[i] = ar.get(mid + 1 + i);
      HuffmanTree[] res = new HuffmanTree[left.length + right.length];
      int index = 0;
      int leftIndex = 0;
      int rightIndex = 0;
      try{
        while(rightIndex < right.length && leftIndex < left.length){
          if(left[leftIndex].count < right[rightIndex].count){
            res[index] = right[rightIndex];
            rightIndex ++;
          }
          else{
            res[index] = left[leftIndex];
            leftIndex ++;
          }
          index ++;
        }
        if(leftIndex < left.length){
          for(int i = 0; i < left.length - leftIndex; i ++){
            res[index + i] = left[leftIndex + i];
          }
        }
        else{
          for(int i = 0; i < right.length - rightIndex; i ++){
            res[index + i] = right[rightIndex + i];
          }
        }
        for(int i = 0; i < res.length; i ++) ar.set(i + start, res[i]);
      }
      catch(Exception e){
        System.out.println(start + ", " + mid + ", " + end + ", " + leftIndex + ", " + left.length + ", " + rightIndex + ", " + right.length);
      }
    }
    
    final private static class HuffmanTree extends Tree{
      public boolean[] value;
      public int count;
      public boolean[] translationKey;
  
      public HuffmanTree(boolean[] value){
        this.value = value;
        this.count = 0;
        this.translationKey = null;
      }
  
      public boolean equalsValue(boolean[] data){
        for(int i = 0; i < data.length; i ++) if(data[i] != this.value[i]) return false;
        return true;
      }
      public void fetchTranslationKeysForEntireSubtree(){
        if(this.isRoot()) this.translationKey = new boolean[0];
        else{
          boolean[] parentTK = ((HuffmanTree)this.getParent()).translationKey;
          this.translationKey = new boolean[parentTK.length + 1];
          for(int i = 0; i < parentTK.length; i ++) this.translationKey[i] = parentTK[i];
          this.translationKey[this.translationKey.length - 1] = ((HuffmanTree)this.getParent()).getOutputIndex(this) == 1;
        }
        for(int i = 0; i < this.getOutputCount(); i ++){
          ((HuffmanTree)this.getOutput(i)).fetchTranslationKeysForEntireSubtree();
        }
      }
      public String toString(){
        String rs = "[value: ";
        if(this.value != null){
          for(int i = 0; i < this.value.length; i ++) rs += this.value[i] ? 1 : 0;
          if(this.value.length / 8 == 1){
            int charValue = 0;
            for(int i = 0; i < this.value.length; i ++){
              if(!this.value[i]) continue;
              int p = 1;
              for(int j = 0; j < this.value.length - i - 1; j ++) p *= 2;
              charValue += p;
            }
  
            rs += " (" + (charValue == 0 ? " " : (char)charValue) + ")";
          }
        }
        else rs += "null";
  
        rs += ", count: " + this.count + ", translationKey: ";
        if(this.translationKey != null)
          for(int i = 0; i < this.translationKey.length; i ++) rs += this.translationKey[i] ? 1 : 0;
        else rs += "null";
        return rs + "]";
      }
      
      boolean[] getHeader(){
        if(this.isLeaf()){
          boolean[] ra = new boolean[this.value.length + 1];
          for(int i = 0; i < this.value.length; i ++) ra[i + 1] = this.value[i];
          ra[0] = true;
          return ra;
        }
  
        boolean[] resultLeft = ((HuffmanTree)this.getOutput(0)).getHeader();
        boolean[] resultRight = ((HuffmanTree)this.getOutput(1)).getHeader();
        boolean[] ra = new boolean[resultLeft.length + resultRight.length + 1];
        for(int i = 0; i < resultLeft.length; i ++) ra[i] = resultLeft[i];
        for(int i = 0; i < resultRight.length; i ++) ra[resultLeft.length + i] = resultRight[i];
        ra[ra.length - 1] = false;
        return ra;
      }
  
      @Override protected void onOutputAdded(Network o, int index) {
        this.count += ((HuffmanTree)o).count;
      }
    }
  }
  public static class Decoding{
    public static boolean[] decode(boolean[] data){
      int characterLength = getCharacterLength(data);
      TreeBuildReturnStruct treeBuildData = buildTree(data, characterLength);
      HeaderValueTree treeRoot = treeBuildData.tree;
      int dataStart = treeBuildData.index + 1;

      ArrayList<Boolean> output = new ArrayList<Boolean>();
      HeaderValueTree currentTree = treeRoot;
      for(int i = dataStart; i < data.length; i ++){
        currentTree = (HeaderValueTree)currentTree.getOutput(data[i] ? 1 : 0);
        if(currentTree.isLeaf()){
          for(int j = 0; j < currentTree.value.length; j ++) output.add(currentTree.value[j]);
          currentTree = treeRoot;
        }
      }

      boolean[] returnData = new boolean[output.size()];
      for(int i = 0; i < returnData.length; i ++) returnData[i] = output.get(i);
      return returnData;
    }

    private static int getCharacterLength(boolean[] data){
      int characterLength = 0;
      for(int i = 0; i < 8; i ++) if(data[i]) characterLength += 1 << (8 - i - 1);
      return characterLength;
    }
    private static TreeBuildReturnStruct buildTree(boolean[] data, int characterLength){
      int index = 8;
      ArrayList<HeaderValueTree> treeNodes = new ArrayList<HeaderValueTree>();
      while(true){
        if(data[index]){
          boolean[] segment = new boolean[characterLength];
          for(int i = 0; i < characterLength; i ++) segment[i] = data[index + 1 + i];
          treeNodes.add(new HeaderValueTree(segment));
          index += 1 + characterLength;
        }
        else{
          if(treeNodes.size() > 1){
            HeaderValueTree node = new HeaderValueTree(treeNodes.get(treeNodes.size() - 2), treeNodes.get(treeNodes.size() - 1));
            treeNodes.remove(treeNodes.size() - 1);
            treeNodes.remove(treeNodes.size() - 1);
            treeNodes.add(node);
            index ++;
          }
          else{
            break;
          }
        }
      }

      return new TreeBuildReturnStruct(treeNodes.get(0), index);
    }
    
    private static class TreeBuildReturnStruct{
      HeaderValueTree tree;
      int index;

      public TreeBuildReturnStruct(HeaderValueTree tree, int index){
        this.tree = tree;
        this.index = index;
      }
    }
    private static class HeaderValueTree extends Tree{
      boolean[] value;

      HeaderValueTree(boolean[] value){
        this.value = value;
      }
      HeaderValueTree(HeaderValueTree a, HeaderValueTree b){
        this.value = null;
        this.addOutput(a);
        this.addOutput(b);
      }

      public boolean equalsValue(boolean[] data){
        for(int i = 0; i < data.length; i ++) if(data[i] != this.value[i]) return false;
        return true;
      }

      public String toString(){
        String data = "";
        String valueString = "";
        if(this.value != null) for(int i = 0; i < this.value.length; i ++) valueString += this.value[i] ? "1" : "0";
        else valueString = "null";
        
        if(this.isRoot()) data += "root ";
        if(!this.isLeaf()) data += "parent[" + this.getOutputCount() + "]";
        if(this.isLeaf()) data += "leaf[" + valueString + "]";

        return data;
      }
    }
  
  
  }
}
